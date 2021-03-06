package ch.epfl.cs107.play.game.actor.bikeGame;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.bikeGame.Wheel;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bike extends GameEntity implements Actor {
	// Attributs des graphics du cycliste (celui de la hitbox est conservé en cas
	// d'utilisation éventuelle plus tard)
	private ShapeGraphics graphics;
	private ShapeGraphics headGraphics;
	private ShapeGraphics leftArmGraphics;
	private ShapeGraphics rightArmGraphics;
	private ShapeGraphics backGraphics;
	private ShapeGraphics leftThighGraphics;
	private ShapeGraphics rightThighGraphics;
	private ShapeGraphics leftFootGraphics;
	private ShapeGraphics rightFootGraphics;
	// Attributs des deuw roues attachées
	private Wheel rightWheel;
	private Wheel leftWheel;
	// Indique vers ou le cycliste regarde (true = droite)
	private boolean look;
	// Indique si le cycliste est touché
	private boolean hit;
	private float MAX_WHEEL_SPEED;
	// Indique quel type de contrôle est utilisé (true = celui de base)
	private boolean control;

	public Bike(ActorGame game, Vector position) {
		super(game, false, position);
		PartBuilder partBuilder = getEntity().createPartBuilder();
		Polygon polygon = new Polygon(new Vector(0.0f, 0.5f), new Vector(0.5f, 1.0f), new Vector(0.0f, 2.0f),
				new Vector(-0.5f, 1.0f));
		// La hitbox est bien sur un ghost
		partBuilder.setGhost(true);
		partBuilder.setShape(polygon);
		// Cela permettra au drapeau de détecter la hitbox
		partBuilder.setCollisionGroup(2);
		partBuilder.build();

		// On initialise la vitesse moteur max ainsi que les contrôles et le regard
		MAX_WHEEL_SPEED = 20.0f;
		look = true;
		control = true;

		// On crée les graphics du cycliste
		BikerGraphics();

		graphics = new ShapeGraphics(polygon, Color.ORANGE, Color.RED, 0.1f);
		// On ne souhaite pas voir la hitbox, on la rend donc transparente
		graphics.setAlpha(0);
		graphics.setParent(getEntity());

		// On crée les deux roues du vélo et les attaches
		leftWheel = new Wheel(game, false, new Vector(-51.0f, 3.0f), 0.5f, "explosive.11.png");
		rightWheel = new Wheel(game, false, new Vector(-49.0f, 3.0f), 0.5f, "explosive.11.png");
		leftWheel.attach(getEntity(), new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f));
		rightWheel.attach(getEntity(), new Vector(1.0f, 0.0f), new Vector(0.5f, -1.0f));

		ContactListener listener = new ContactListener() {
			@Override
			// On signale que le cycliste est touché si ce qui l'a touché n'est ni une roue ni
			// un ghost
			public void beginContact(Contact contact) {
				if (contact.getOther().isGhost())
					return;
				if (contact.getOther().getCollisionGroup() == 1) {
					return;
				}
				hit = true;
			}

			@Override
			// On signale que le contact est fini
			public void endContact(Contact contact) {
				hit = false;
			}
		};
		getEntity().addContactListener(listener);
	}

	public void update(float deltaTime) {
		rightWheel.relax();
		leftWheel.relax();
		// Vérifie quels contrôles sont actifs et les appliquent
		if (control) {
			// Les contrôles classiques
			if (getOwner().getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
				look = !look;
				BikerGraphics();
			}
			if (getOwner().getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
				leftWheel.power(0.0f);
				rightWheel.power(0.0f);
			}
			if ((look) && (leftWheel.getSpeed() >= -MAX_WHEEL_SPEED)) {
				if (getOwner().getKeyboard().get(KeyEvent.VK_UP).isDown()) {
					leftWheel.power(-MAX_WHEEL_SPEED);
				}
			}
			if ((!look) && (rightWheel.getSpeed() <= MAX_WHEEL_SPEED)) {
				if (getOwner().getKeyboard().get(KeyEvent.VK_UP).isDown()) {
					rightWheel.power(MAX_WHEEL_SPEED);
				}
			}
			if (getOwner().getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
				getEntity().applyAngularForce(30.0f);
			}
			if (getOwner().getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
				getEntity().applyAngularForce(-30.0f);
			}
		}
		if (!control) {
			// Les contrôles "alternatifs"
			if (getOwner().getKeyboard().get(KeyEvent.VK_SPACE).isDown()) {
				leftWheel.power(0.0f);
				rightWheel.power(0.0f);
			}
			if (leftWheel.getSpeed() >= -MAX_WHEEL_SPEED) {
				if (getOwner().getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
					leftWheel.power(-MAX_WHEEL_SPEED);
					// On signifie que le regard change et on ne redessine le cycliste qu'une fois par pression
				}
				if (getOwner().getKeyboard().get(KeyEvent.VK_RIGHT).isPressed()) {
					look = true;
					BikerGraphics();
				}	
			}
			if ((rightWheel.getSpeed() <= MAX_WHEEL_SPEED)) {
				if (getOwner().getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
					rightWheel.power(MAX_WHEEL_SPEED);
				}
				if (getOwner().getKeyboard().get(KeyEvent.VK_LEFT).isPressed()) {
					look = false;
					BikerGraphics();
				}
			}
			// Les roues se lèvent en fonction de l'orientation du cycliste
			if (getOwner().getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
				if (!look)
					getEntity().applyAngularForce(30.0f);
				if (look)
					getEntity().applyAngularForce(-30.0f);
			}
			if (getOwner().getKeyboard().get(KeyEvent.VK_UP).isDown()) {
				if (!look)
					getEntity().applyAngularForce(-30.0f);
				if (look)
					getEntity().applyAngularForce(30.0f);
			}
		}
		// La commande de saut commune
		// On verifie que les roue touchent un non-ghost et on fait sauter le vélo
		if ((getOwner().getKeyboard().get(KeyEvent.VK_SHIFT).isPressed())
				&& (leftWheel.getGround() || rightWheel.getGround())) {
			getEntity().applyImpulse(new Vector(0, 3), null);
			leftWheel.getEntity().applyImpulse(new Vector(0, 3), null);
			rightWheel.getEntity().applyImpulse(new Vector(0, 3), null);
		}
		
		// Simule l'action de pédalage.
		pedal(deltaTime);
		
		leftWheel.update(deltaTime);
		rightWheel.update(deltaTime);
	}

	// Méthode permettant de dessiner le cycliste
	private void BikerGraphics() {
		Circle head = new Circle(0.2f, getHeadLocation());
		Polyline leftArm = new Polyline(getShoulderLocation(), getLeftHandLocation());
		Polyline rightArm = new Polyline(getShoulderLocation(), getRightHandLocation());
		Polyline back = new Polyline(getShoulderLocation(), getBottomLocation());

		headGraphics = new ShapeGraphics(head, Color.WHITE, Color.WHITE, 0.15f);
		leftArmGraphics = new ShapeGraphics(leftArm, Color.WHITE, Color.WHITE, 0.15f);
		rightArmGraphics = new ShapeGraphics(rightArm, Color.WHITE, Color.WHITE, 0.15f);
		backGraphics = new ShapeGraphics(back, Color.WHITE, Color.WHITE, 0.15f);

		headGraphics.setParent(getEntity());
		leftArmGraphics.setParent(getEntity());
		rightArmGraphics.setParent(getEntity());
		backGraphics.setParent(getEntity());
	}

	// Méthode permettant de l'effacer 
	public void deleteGraphics() {
		Circle circle = new Circle(0.0f);
		headGraphics = new ShapeGraphics(circle, Color.WHITE, Color.WHITE, 0.2f);
		leftArmGraphics = new ShapeGraphics(circle, Color.WHITE, Color.WHITE, 0.2f);
		rightArmGraphics = new ShapeGraphics(circle, Color.WHITE, Color.WHITE, 0.2f);
		backGraphics = new ShapeGraphics(circle, Color.WHITE, Color.WHITE, 0.2f);
	}

	
	private Vector getHeadLocation() {
		return new Vector(0.0f, 1.75f);
	}

	private Vector getShoulderLocation() {
		return new Vector(0.0f, 1.55f);
	}

	private Vector getLeftHandLocation() {
		if (look) {
			return new Vector(0.5f, 1.0f);
		} else {
			return new Vector(-0.5f, 1.0f);
		}
	}

	private Vector getRightHandLocation() {
		if (look) {
			return new Vector(0.5f, 1.0f);
		} else {
			return new Vector(-0.5f, 1.0f);
		}
	}

	private Vector getVictoryLeftHandLocation() {
		return new Vector(0.5f, 2.1f);
	}

	private Vector getVictoryRightHandLocation() {
		return new Vector(-0.5f, 2.1f);
	}

	private Vector getBottomLocation() {
		if (look) {
			return new Vector(-0.5f, 1.0f);
		} else {
			return new Vector(0.5f, 1.0f);
		}
	}
	
	// Méthodes permettant de simuler de manière approximative le mouvement des genoux le long d'une
	// diagonale en fonction de la vitesse des roues (donc du cycliste en général) et de sa direction.
	private Vector getUpdateLeftKneeLocation(float deltaTime) {
		if (look) {
			return new Vector(0.2f * ((float) Math.sin((double) leftWheel.getEntity().getAngularPosition())),
					0.6f + (0.2f * ((float) Math.sin((double) leftWheel.getEntity().getAngularPosition()))));
		} else {
			return new Vector(-0.2f * ((float) Math.sin((double) rightWheel.getEntity().getAngularPosition())),
					0.6f + (0.2f * ((float) Math.sin((double) rightWheel.getEntity().getAngularPosition()))));
		}
	}

	private Vector getUpdateRightKneeLocation(float deltaTime) {
		if (look) {
			return new Vector(0.2f * (-((float) Math.sin((double) leftWheel.getEntity().getAngularPosition()))),
					0.6f + (0.2f * (-((float) Math.sin((double) leftWheel.getEntity().getAngularPosition())))));
		} else {
			return new Vector(-0.2f * (-((float) Math.sin((double) rightWheel.getEntity().getAngularPosition()))),
					0.6f + (0.2f * (-((float) Math.sin((double) rightWheel.getEntity().getAngularPosition())))));
		}
	}

	// Méthode permettant de simuler le mouvement des pieds autour d'un cercle (simulation 
	// d'un pédalier).
	private Vector getUpdateLeftFootLocation(float deltaTime) {
		if (look) {
			return new Vector((float) (0.3f * Math.cos((double) leftWheel.getEntity().getAngularPosition())),
					(float) (0.3f * Math.sin((double) leftWheel.getEntity().getAngularPosition())));
		} else {
			return new Vector((float) (0.3f * Math.cos((double) rightWheel.getEntity().getAngularPosition())),
					(float) (0.3f * Math.sin((double) rightWheel.getEntity().getAngularPosition())));
		}
	}

	private Vector getUpdateRightFootLocation(float deltaTime) {
		if (look) {
			return new Vector((float) (-0.3f * Math.cos((double) leftWheel.getEntity().getAngularPosition())),
					(float) (-0.3f * Math.sin((double) leftWheel.getEntity().getAngularPosition())));
		} else {
			return new Vector((float) (-0.3f * Math.cos((double) rightWheel.getEntity().getAngularPosition())),
					(float) (-0.3f * Math.sin((double) rightWheel.getEntity().getAngularPosition())));
		}
	}

	// Permet de savoir si le vélo est touché
	public boolean getHit() {
		return hit;
	}

	// Permet de changer les contrôles 
	public void setControl(boolean control) {
		this.control = control;
	}

	// Permet de savoir quels contrôles sont utilisés
	public boolean getControl() {
		return control;
	}

	// Permet d'optenir la position du vélo
	public Vector getPosition() {
		return getEntity().getPosition();
	}

	// Permet de savoir vers où le cycliste regarde
	public boolean getRegard() {
		return look;
	}

	@Override
	public void destroy() {
		getEntity().destroy();
		leftWheel.destroy();
		rightWheel.destroy();
		deleteGraphics();
	}

	@Override
	public Transform getTransform() {
		return getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return getEntity().getVelocity();
	}

	@Override
	public void draw(Canvas canvas) {
		headGraphics.draw(canvas);
		leftArmGraphics.draw(canvas);
		rightArmGraphics.draw(canvas);
		backGraphics.draw(canvas);
		leftThighGraphics.draw(canvas);
		rightThighGraphics.draw(canvas);
		leftFootGraphics.draw(canvas);
		rightFootGraphics.draw(canvas);
		rightWheel.draw(canvas);
		leftWheel.draw(canvas);
	}

	// Méthode faisant lever les bras du cycliste en l'air en signe de joie ultime.
	public void victoryArms() {
		Polyline leftArm = new Polyline(getShoulderLocation(), getVictoryLeftHandLocation());
		Polyline rightArm = new Polyline(getShoulderLocation(), getVictoryRightHandLocation());
		leftArmGraphics = new ShapeGraphics(leftArm, Color.WHITE, Color.WHITE, 0.15f);
		rightArmGraphics = new ShapeGraphics(rightArm, Color.WHITE, Color.WHITE, 0.15f);
		leftArmGraphics.setParent(getEntity());
		rightArmGraphics.setParent(getEntity());
	}

	// Méthode créant les cuisses et les avant-jambes du cycliste en simulant l'action de pédaler.
	public void pedal(float deltaTime) {
		Polyline leftFoot = new Polyline(getUpdateLeftKneeLocation(deltaTime), getUpdateLeftFootLocation(deltaTime));
		Polyline rightFoot = new Polyline(getUpdateRightKneeLocation(deltaTime), getUpdateRightFootLocation(deltaTime));
		Polyline leftThigh = new Polyline(getBottomLocation(), getUpdateLeftKneeLocation(deltaTime));
		Polyline rightThigh = new Polyline(getBottomLocation(), getUpdateRightKneeLocation(deltaTime));
		leftThighGraphics = new ShapeGraphics(leftThigh, Color.WHITE, Color.WHITE, 0.15f);
		rightThighGraphics = new ShapeGraphics(rightThigh, Color.WHITE, Color.WHITE, 0.15f);
		leftThighGraphics.setParent(getEntity());
		rightThighGraphics.setParent(getEntity());
		leftFootGraphics = new ShapeGraphics(leftFoot, Color.WHITE, Color.WHITE, 0.15f);
		rightFootGraphics = new ShapeGraphics(rightFoot, Color.WHITE, Color.WHITE, 0.15f);
		leftFootGraphics.setParent(getEntity());
		rightFootGraphics.setParent(getEntity());

	}
}

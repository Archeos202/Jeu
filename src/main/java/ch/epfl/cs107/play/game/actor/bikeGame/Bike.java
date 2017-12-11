package ch.epfl.cs107.play.game.actor.bikeGame;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Bike extends GameEntity implements Actor {
	private ShapeGraphics headGraphics;
	private ShapeGraphics leftArmGraphics;
	private ShapeGraphics rightArmGraphics;
	private ShapeGraphics backGraphics;
	private ShapeGraphics leftThighGraphics;
	private ShapeGraphics rightThighGraphics;
	private ShapeGraphics leftFootGraphics;
	private ShapeGraphics rightFootGraphics;
	private Wheel rightWheel;
	private Wheel leftWheel;
	private boolean regard;
	private boolean hit;
	private float MAX_WHEEL_SPEED;

	// MAX_WHEEL_SPEED et le boolean pour le regard
	// setviewcandidate ????

	public Bike(ActorGame game, Vector position) {
		super(game, false, position);

		PartBuilder partBuilder = getEntity().createPartBuilder(); 
        
        Polygon polygon = new Polygon(
        		new Vector(0.0f, 0.5f),
        		new Vector(0.5f, 1.0f),
        		new Vector(0.0f, 2.0f),
        		new Vector(-0.5f, 1.0f) ); 
        partBuilder.setGhost(true);
        partBuilder.setShape(polygon);
        partBuilder.setCollisionGroup(2);
        partBuilder.build();
		
        MAX_WHEEL_SPEED = 20.0f;
        regard = true;
        
        BikerGraphics();
        
        leftWheel = new Wheel(game, false, new Vector (-51.0f, 9.0f),0.5f, "explosive.11.png", 1);
		rightWheel = new Wheel(game, false, new Vector (-49.0f, 9.0f),0.5f, "explosive.11.png", 1);
        leftWheel.attach(getEntity(), new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f)); 
		rightWheel.attach(getEntity(), new Vector(1.0f, 0.0f), new Vector(0.5f, -1.0f));

		ContactListener listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if (contact.getOther().isGhost())
					return;
				if (contact.getOther().getCollisionGroup() == 1) {
					System.out.println("roue");
					return;
				}
				hit = true;
			}

			@Override
			public void endContact(Contact contact) {
				hit = false;
			}
		};
		getEntity().addContactListener(listener);
	}

	public void update(float deltaTime) {
		if (getOwner().getKeyboard().get(KeyEvent.VK_SPACE).isPressed()) {
			regard = !regard;
			BikerGraphics();
		}
		rightWheel.relax();
		leftWheel.relax();
		if (getOwner().getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
			leftWheel.power(0.0f);
			rightWheel.power(0.0f);
		}
		if ((regard) && (leftWheel.getSpeed() >= -MAX_WHEEL_SPEED)) {
			if (getOwner().getKeyboard().get(KeyEvent.VK_UP).isDown()) {
				leftWheel.power(-MAX_WHEEL_SPEED);
			}
		}
		if ((!regard) && (rightWheel.getSpeed() <= MAX_WHEEL_SPEED)) {
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
		pedal(deltaTime);
	}

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
		if (regard) {
			return new Vector(0.5f, 1.0f);
		} else {
			return new Vector(-0.5f, 1.0f);
		}
	}

	private Vector getRightHandLocation() {
		if (regard) {
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
		if (regard) {
			return new Vector(-0.5f, 1.0f);
		} else {
			return new Vector(0.5f, 1.0f);
		}
	}

	private Vector getUpdateLeftKneeLocation(float deltaTime) {
		if (regard) {
			return new Vector(0.2f * ((float) Math.sin((double) leftWheel.getEntity().getAngularPosition())),
					0.6f + (0.2f * ((float) Math.sin((double) leftWheel.getEntity().getAngularPosition()))));
		} else {
			return new Vector(-0.2f * ((float) Math.sin((double) rightWheel.getEntity().getAngularPosition())),
					0.6f + (0.2f * ((float) Math.sin((double) rightWheel.getEntity().getAngularPosition()))));
		}
	}

	private Vector getUpdateRightKneeLocation(float deltaTime) {
		if (regard) {
			return new Vector(0.2f * (-((float) Math.sin((double) leftWheel.getEntity().getAngularPosition()))),
					0.6f + (0.2f * (-((float) Math.sin((double) leftWheel.getEntity().getAngularPosition())))));
		} else {
			return new Vector(-0.2f * (-((float) Math.sin((double) rightWheel.getEntity().getAngularPosition()))),
					0.6f + (0.2f * (-((float) Math.sin((double) rightWheel.getEntity().getAngularPosition())))));
		}
	}

	private Vector getUpdateLeftFootLocation(float deltaTime) {
		if (regard) {
			return new Vector((float) (0.3f * Math.cos((double) leftWheel.getEntity().getAngularPosition())),
					(float) (0.3f * Math.sin((double) leftWheel.getEntity().getAngularPosition())));
		} else {
			return new Vector((float) (0.3f * Math.cos((double) rightWheel.getEntity().getAngularPosition())),
					(float) (0.3f * Math.sin((double) rightWheel.getEntity().getAngularPosition())));
		}
	}

	private Vector getUpdateRightFootLocation(float deltaTime) {
		if (regard) {
			return new Vector((float) (-0.3f * Math.cos((double) leftWheel.getEntity().getAngularPosition())),
					(float) (-0.3f * Math.sin((double) leftWheel.getEntity().getAngularPosition())));
		} else {
			return new Vector((float) (-0.3f * Math.cos((double) rightWheel.getEntity().getAngularPosition())),
					(float) (-0.3f * Math.sin((double) rightWheel.getEntity().getAngularPosition())));
		}
	}

	public boolean getHit() {
		return hit;
	}

	// supprimer des actors ???
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

	public void victoryArms() {
		Polyline leftArm = new Polyline(getShoulderLocation(), getVictoryLeftHandLocation());
		Polyline rightArm = new Polyline(getShoulderLocation(), getVictoryRightHandLocation());
		leftArmGraphics = new ShapeGraphics(leftArm, Color.WHITE, Color.WHITE, 0.15f);
		rightArmGraphics = new ShapeGraphics(rightArm, Color.WHITE, Color.WHITE, 0.15f);
		leftArmGraphics.setParent(getEntity());
		rightArmGraphics.setParent(getEntity());
	}

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

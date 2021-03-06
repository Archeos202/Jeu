package ch.epfl.cs107.play.game.actor.bikeGame;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor {
	private ImageGraphics graphics;
	// Les roues ont leur contraintes, le véhicule auquel elles sont attachées et sa part comme attribut
	private WheelConstraint constraint;
	private Entity vehicle;
	// Ground indique si les roues touchent le sol
	private boolean ground;
	
	public Wheel(ActorGame game,boolean fixed, Vector position, float radius, String name) {
		super(game, fixed, position);
		
		// On crée la représentation graphique en fonction des parametres
		graphics = new ImageGraphics(name, radius*2, radius*2, new Vector(0.5f , 0.5f));
		
		PartBuilder partBuilder = getEntity().createPartBuilder();
		
		// On construit les roues de la taille indiquée dans les paramètres
		Circle circle = new Circle (radius);
		partBuilder.setShape(circle);
		partBuilder.setFriction(10.0f);
		// On assigne au roues les groupes de collisions qui permettent de :
		// Groupe 1 : ne pas tuer le vélo
		partBuilder.setCollisionGroup(1);
		// Groupe 2 : être détecter par le drapeau
		partBuilder.setCollisionGroup(2);
		Part part = partBuilder.build();
		graphics.setParent(getEntity());
	}
	
	// Permet d'attacher les roues au cycliste
	public void attach(Entity vehicle, Vector anchor, Vector axis) {
		WheelConstraintBuilder constraintBuilder = getOwner().createWheelConstraint();
		constraintBuilder.setFirstEntity(vehicle);
		// Point d'ancrage du véhicule :
		constraintBuilder.setFirstAnchor(anchor);
		// Entity associée à la roue :
		constraintBuilder.setSecondEntity(this.getEntity());
		// Point d'ancrage de la roue (son centre) :
		constraintBuilder.setSecondAnchor(Vector.ZERO);
		// Axe le long duquel la roue peut se déplacer :
		constraintBuilder.setAxis(axis);
		// Fréquence du ressort associé
		constraintBuilder.setFrequency(3.0f);
		constraintBuilder.setDamping(0.5f);
		// Force angulaire maximale pouvant être appliquée 
		// à la roue pour la faire tourner :
		constraintBuilder.setMotorMaxTorque(10.0f);
		constraint = constraintBuilder.build();
		this.vehicle = vehicle;
	}
	
	
	public void update(float deltaTime) {
	ContactListener listener = new ContactListener() {
		@Override
		// Ppermet d'indiquer si les roues touchent quelque chose ou non 
		public void beginContact(Contact contact) {
			// Bien sûr on ne peut pas sauter en s'appuyant sur un ghost
			if (contact.getOther().isGhost())
				return;
				ground = true;
		}

		@Override
		public void endContact(Contact contact) {
			ground = false;
		}
	};
	getEntity().addContactListener(listener);
	}

	// Permet de savoir si les roues touchent quelque chose ou non 
	public boolean getGround() {
		return ground;
	}
	
	// Permet de faire tourner les roues a une force donnée
	public void power(float speed) {
		constraint.setMotorEnabled(true);
		constraint.setMotorSpeed(speed);
	}
	
	// Désactive la motorisation
	public void relax() {
		constraint.setMotorEnabled(false);
	}
	
	// Permet d'obtenir la vitesse des roues
	public float getSpeed() {
		return constraint.getMotorSpeed() ;
	}
	
	public void destroy() {
		getEntity().destroy();
		graphics = new ImageGraphics("", 0, 0, new Vector(0.5f , 0.5f));
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
		graphics.draw(canvas);
		
	}

}

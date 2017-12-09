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

public class Bike extends GameEntity implements Actor  {
	private ShapeGraphics graphics;
	private ShapeGraphics headGraphics;
	private ShapeGraphics armGraphics;
	private ShapeGraphics backGraphics;
	private ShapeGraphics thighGraphics;
	private ShapeGraphics leftFootGraphics;
	private ShapeGraphics rightFootGraphics;
	private Wheel rightWheel;
	private Wheel leftWheel;
	private boolean regard;
	private boolean hit;
	private float MAX_WHEEL_SPEED;
	
	//MAX_WHEEL_SPEED et le boolean pour le regard
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
        
    	graphics = new ShapeGraphics(polygon, Color.ORANGE, Color.RED, 0.1f);
        graphics.setParent(getEntity());
        
        leftWheel = new Wheel(game, false, new Vector (3.0f, 5.0f),0.5f, "explosive.11.png", 1);
		rightWheel = new Wheel(game, false, new Vector (5.0f, 5.0f),0.5f, "explosive.11.png", 1);
        leftWheel.attach(getEntity(), new Vector(-1.0f, 0.0f), new Vector(-0.5f, -1.0f)); 
		rightWheel.attach(getEntity(), new Vector(1.0f, 0.0f), new Vector(0.5f, -1.0f));
		
		ContactListener listener = new ContactListener() {
			@Override 
			public void beginContact(Contact contact) {
				if (contact.getOther().isGhost()) 
					return; 
				if (contact.getOther().getCollisionGroup()==1) {
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
			regard = !regard ;
			BikerGraphics();
			}
		rightWheel.relax();
		leftWheel.relax();
		if (getOwner().getKeyboard().get(KeyEvent.VK_DOWN).isDown()) {
			leftWheel.power(0.0f);
			rightWheel.power(0.0f);
		}
		if ((regard) && (leftWheel.getSpeed()>= -MAX_WHEEL_SPEED)) {
			if (getOwner().getKeyboard().get(KeyEvent.VK_UP).isDown()) {
			leftWheel.power(-MAX_WHEEL_SPEED);
			}
		}
		if ((!regard) && (rightWheel.getSpeed()<= MAX_WHEEL_SPEED)) {
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
	private void BikerGraphics() {
		Circle head = new Circle(0.2f, getHeadLocation());
		Polyline  arm = new Polyline (
				getShoulderLocation(),
				getHandLocation());
		Polyline back = new Polyline (
				getShoulderLocation(),
				getBottomLocation());
		Polyline  thigh = new Polyline (
				getBottomLocation(),
				getKneeLocation());
		Polyline  leftFoot = new Polyline (
				getKneeLocation(),
				getLeftFootLocation());
		Polyline  rightFoot = new Polyline (
				getKneeLocation(),
				getRightFootLocation());
		
		headGraphics = new ShapeGraphics(head,Color.WHITE, Color.WHITE, 0.2f);
		armGraphics = new ShapeGraphics(arm,Color.WHITE, Color.WHITE, 0.2f);
    	backGraphics = new ShapeGraphics(back,Color.WHITE, Color.WHITE, 0.2f);
    	thighGraphics = new ShapeGraphics(thigh,Color.WHITE, Color.WHITE, 0.2f);
    	leftFootGraphics = new ShapeGraphics(leftFoot,Color.WHITE, Color.WHITE, 0.2f);
    	rightFootGraphics = new ShapeGraphics(rightFoot,Color.WHITE, Color.WHITE, 0.2f);
    	
    	headGraphics.setParent(getEntity());
    	armGraphics.setParent(getEntity());
        backGraphics.setParent(getEntity());
        thighGraphics.setParent(getEntity());
        leftFootGraphics.setParent(getEntity());
        rightFootGraphics.setParent(getEntity());
	}
	
	public void deleteGraphics() {
		Circle circle = new Circle(0.0f);
		headGraphics = new ShapeGraphics(circle,Color.WHITE, Color.WHITE, 0.2f);
		armGraphics = new ShapeGraphics(circle,Color.WHITE, Color.WHITE, 0.2f);
		backGraphics = new ShapeGraphics(circle,Color.WHITE, Color.WHITE, 0.2f);
		thighGraphics = new ShapeGraphics(circle,Color.WHITE, Color.WHITE, 0.2f);
		leftFootGraphics = new ShapeGraphics(circle,Color.WHITE, Color.WHITE, 0.2f);
		rightFootGraphics = new ShapeGraphics(circle,Color.WHITE, Color.WHITE, 0.2f);
		graphics = new ShapeGraphics(circle, Color.ORANGE, Color.RED, 0.0f);
	}
	
	private Vector getHeadLocation() {
		return new Vector(0.0f, 1.75f);
	}
	private Vector getShoulderLocation() {
		return new Vector(0.0f, 1.55f);
	}
	private Vector getHandLocation() {
		if (regard) {
			return new Vector(0.5f, 1.0f);
		}
		else {
			return new Vector(-0.5f, 1.0f);
		}
	}
	private Vector getBottomLocation() {
		if (regard) {
			return new Vector(-0.5f, 1.0f);
		}
		else { 
			return new Vector (0.5f, 1.0f);
		}
	}
	private Vector getKneeLocation() {
			return new Vector(0.0f, 0.6f);
	}
	private Vector getLeftFootLocation() {
		if (regard) {
			return new Vector(-0.3f, 0.0f);
		}
		else { 
			return new Vector (0.3f, 0.0f);
		}
	}
	private Vector getRightFootLocation() {
		if (regard) {
			return new Vector(0.3f, 0.0f);
		}
		else { 
			return new Vector (-0.3f, 0.0f);
		}
	}
	
	public boolean getHit() {
		return hit;
	}
	//supprimer des actors ???
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
		graphics.draw(canvas);
		headGraphics.draw(canvas);
        armGraphics.draw(canvas);
        backGraphics.draw(canvas);
        thighGraphics.draw(canvas);
        leftFootGraphics.draw(canvas);
        rightFootGraphics.draw(canvas);
        rightWheel.draw(canvas);
        leftWheel.draw(canvas);
	}

}

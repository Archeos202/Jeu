package ch.epfl.cs107.play.game.actor.bikeGame;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Finish extends GameEntity implements Actor {

	private ImageGraphics graphics;
	private BasicContactListener contactListener;
	private Boolean hit;

	public Finish(ActorGame game, Vector position) { 
		super(game, true, position);
		
        PartBuilder partBuilder = getEntity().createPartBuilder(); 
        Polyline polyline = new Polyline(
        		new Vector (0.0f, 0.0f),
        		new Vector (0.0f, 2.0f)
        		);
        partBuilder.setShape(polyline);
        partBuilder.setGhost(true);
        partBuilder.build();
        
        contactListener = new BasicContactListener();
        getEntity().addContactListener(contactListener);
        
        graphics = new ImageGraphics("flag.red.png",2.0f, 2.0f);
        
        graphics.setParent(getEntity());
        
        hit = false;
        ContactListener listener = new ContactListener() {
			@Override 
			public void beginContact(Contact contact) {
				if (contact.getOther().getCollisionGroup()==2) 
				hit = true;
				} 
			@Override 
			public void endContact(Contact contact) {
				hit = false;
			} 
				};
		getEntity().addContactListener(listener);
	}
	
	public boolean getHit() {
		return hit;
	}
	
	//supprimer des actors ???
	@Override
	public void destroy() {
		getEntity().destroy();
		// PENSER A FAIRE DISPARAITRE GRAPHICS	
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


package ch.epfl.cs107.play.game.actor.bikeGame;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Part;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class BoutonCrate extends GameEntity implements Actor {

	private ImageGraphics graphics;
	private Boolean hit;

	public BoutonCrate(ActorGame game, boolean fixed, Vector position, float width, float height, String name) {
		super(game, fixed, position);
		graphics = new ImageGraphics(name, width, height);

        PartBuilder partBuilder = getEntity().createPartBuilder(); 
         
        Polygon polygon = new Polygon(
        		new Vector(0.0f, 0.0f),
        		new Vector(width, 0.0f),
        		new Vector(width,height),
        		new Vector(0.0f, height ) ); 
        
        partBuilder.setShape(polygon);
        partBuilder.setGhost(true);
        partBuilder.build();
        
        graphics.setParent(getEntity());
        
        hit = false;
        ContactListener listener = new ContactListener() {
			@Override 
			public void beginContact(Contact contact) { 
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
		graphics = new ImageGraphics("",0f, 0f);
		hit = false;
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

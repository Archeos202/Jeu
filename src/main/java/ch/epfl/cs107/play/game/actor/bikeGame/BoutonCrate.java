package ch.epfl.cs107.play.game.actor.bikeGame;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class BoutonCrate extends GameEntity implements Actor {

	private ImageGraphics graphics;
	//indique si le bouton est touché ou non
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
        //le bouton est bien sur un ghost
        partBuilder.setGhost(true);
        partBuilder.build();
        
        graphics.setParent(getEntity());
        
        //on initialise le contact comme n'ayant pas lieu
        hit = false;
        
        ContactListener listener = new ContactListener() {
			@Override 
			//le contactListener change la valeur de hit quand le contact a lieu
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
	
	//permet de savoir si le bouton est touché
	public boolean getHit() {
		return hit;
	}
	
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

package ch.epfl.cs107.play.game.actor.bikeGame;



import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
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
	private Boolean hit;

	public Finish(ActorGame game, Vector position) { 
		super(game, true, position);
		
        PartBuilder partBuilder = getEntity().createPartBuilder(); 
        Polyline polyline = new Polyline(
        		new Vector (0.0f, 0.0f),
        		new Vector (0.0f, 2.0f)
        		);
        partBuilder.setShape(polyline);
        //on ne veut pas percuter le drapeau
        partBuilder.setGhost(true);
        partBuilder.build();
        
        graphics = new ImageGraphics("flag.red.png",2.0f, 2.0f);
        
        graphics.setParent(getEntity());
        
        //on initialise le contact a false
        hit = false;
        
        //le contact listener indique quand le velo ou les roues touche le finish
        ContactListener listener = new ContactListener() {
			@Override 
			public void beginContact(Contact contact) {
				//on ne veut signaler le contact du velo ou des roues
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
	
	//permet de savoir si le drapeau est touché
	public boolean getHit() {
		return hit;
	}
	
	@Override
	public void destroy() {
		getEntity().destroy();	
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


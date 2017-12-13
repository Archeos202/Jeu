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
        // On ne veut pas percuter le drapeau
        partBuilder.setGhost(true);
        partBuilder.build();
        
        graphics = new ImageGraphics("flag.red.png",2.0f, 2.0f);
        
        graphics.setParent(getEntity());
        
        // On initialise le contact à false
        hit = false;
        
        // Le contact listener indique quand le vélo ou les roues touchent le finish
        ContactListener listener = new ContactListener() {
			@Override 
			public void beginContact(Contact contact) {
				// On ne veut signaler le contact du vélo ou des roues
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
	
	// Permet de savoir si le drapeau est touché
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


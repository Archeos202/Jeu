package ch.epfl.cs107.play.game.actor.bikeGame;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Arrow extends GameEntity implements Actor {

	private ImageGraphics graphics;
	//booléen disant si la fleche a été tirée (false) ou non (true)
	private boolean arc;

	public Arrow(ActorGame game, Vector position, float width, float height, String name) {
		super(game, false, position);
		graphics = new ImageGraphics(name, width, height);

        PartBuilder partBuilder = getEntity().createPartBuilder(); 
         
        Polygon polygon = new Polygon(
        		new Vector(0.0f, 0.0f),
        		new Vector(width, 0.0f),
        		new Vector(width,height),
        		new Vector(0.0f, height ) ); 
        
        partBuilder.setShape(polygon);
        partBuilder.build();
        //on initialise la valeur de arc a true car evidemment elle n'a pas été tirée
        arc = true;
        
        graphics.setParent(getEntity());
	}
	
	public void update(float deltaTime) {
		//on "tire" la fleche si UP est pressé, puis on indique qu'on a tiré
		if (getOwner().getKeyboard().get(KeyEvent.VK_UP).isPressed() && arc) {
			getEntity().applyImpulse(new Vector(20.0f, 0.0f), null);
			arc = false;
		}
	}
	@Override
	public void destroy() {
		getEntity().destroy();
		graphics = new ImageGraphics("",0f, 0f);	
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


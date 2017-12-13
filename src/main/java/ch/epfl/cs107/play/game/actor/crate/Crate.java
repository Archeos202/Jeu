package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Crate extends GameEntity implements Actor {

	private ImageGraphics graphics;

	public Crate(ActorGame game, boolean fixed, Vector position, float width, float height, String name, boolean ghost) {
		super(game, fixed, position);
		//on construit la représentation graphique en fonction des parametres donnés
		graphics = new ImageGraphics(name, width, height);

        PartBuilder partBuilder = getEntity().createPartBuilder(); 
        
        //on constuit le part avec les parametres donnés
        Polygon polygon = new Polygon(
        		new Vector(0.0f, 0.0f),
        		new Vector(width, 0.0f),
        		new Vector(width,height),
        		new Vector(0.0f, height ) ); 
        
        partBuilder.setShape(polygon);
        //la crate peut etre ghost ou non
        partBuilder.setGhost(ghost);
        partBuilder.build();
        
        graphics.setParent(getEntity());
	}
	
	
	//supprimer des actors ???
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

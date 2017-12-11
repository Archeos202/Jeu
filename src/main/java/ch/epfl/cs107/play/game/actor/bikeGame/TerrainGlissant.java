package ch.epfl.cs107.play.game.actor.bikeGame;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class TerrainGlissant extends GameEntity implements Actor {

		private ShapeGraphics graphics;

		public TerrainGlissant(ActorGame game, Vector position) { 
			super(game, true, position);
			
	        PartBuilder partBuilder = getEntity().createPartBuilder(); 
	        Polyline polyline = new Polyline(0.0f, 0.f,
	        		100.0f,0.0f,
	        		100.0f, -100.0f
	        		);
	        partBuilder.setShape(polyline);
	        partBuilder.setFriction(0.01f);
	        partBuilder.build();
	        graphics = new ShapeGraphics(polyline, Color.BLACK, Color.BLUE , 0.05f);
	        
	        graphics.setParent(getEntity());
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


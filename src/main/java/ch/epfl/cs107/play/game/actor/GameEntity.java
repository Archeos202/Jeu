package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Vector;

public abstract class GameEntity {

	private ActorGame game;
	private Entity entity;

	public GameEntity(ActorGame game, boolean fixed, Vector position) {
		if (((game) == null) || (position == null)) {
			
			try {
				if (game == null) {
					throw new NullPointerException("Il faut un jeu.");
				} else if (position == null) {
					throw new NullPointerException("Position nulle pas acceptée.");
				}
			}

			catch (NullPointerException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}


		}
		this.game = game;
		entity = this.game.createEntity(entity, fixed, position);
	}

	public GameEntity(ActorGame game, boolean fixed) {
		this.game = game;
		entity = this.game.createEntity(entity, fixed, Vector.ZERO);
	}
	
	protected Entity getEntity() {
		return entity;
	}

	protected ActorGame getOwner() {
		return game;
	}

	public void destroy() {
		entity.destroy();
	}

}

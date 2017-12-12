package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Vector;

public abstract class GameEntity {

	private ActorGame game;
	private Entity entity;

	public GameEntity(ActorGame game, boolean fixed, Vector position) {
		try {
			if (game == null) {
				throw new NullPointerException("Il faut un jeu!");
			} else if (position == null) {
				throw new NullPointerException("Il faut une position!");
			}
		}

		catch (NullPointerException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		this.game = game;
		entity = this.game.createEntity(entity, fixed, position);
	}

	public GameEntity(ActorGame game, boolean fixed) {
		this.game = game;
		entity = this.game.createEntity(entity, fixed, Vector.ZERO);
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	protected ActorGame getOwner() {
		return game;
	}

	public void destroy() {
		entity.destroy();
	}

}

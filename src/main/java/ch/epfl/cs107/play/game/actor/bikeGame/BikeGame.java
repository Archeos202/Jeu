package ch.epfl.cs107.play.game.actor.bikeGame;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Window;

public class BikeGame extends ActorGame {

	private Finish finish;
	private Bike bike;
	private TextGraphics message;
	private boolean collision;
	private boolean victoire;

	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		Terrain terrain = new Terrain(this, new Vector(0f, 0f));
		addActor(terrain);
		Crate crate1 = new Crate(this, false, new Vector(0.0f, 5.0f), 1, 1, "crate.1.png");
		addActor(crate1);
		Crate crate2 = new Crate(this, false, new Vector(0.2f, 7.0f), 1, 1, "crate.1.png");
		addActor(crate2);
		Crate crate3 = new Crate(this, false, new Vector(2.0f, 6.0f), 1, 1, "crate.1.png");
		addActor(crate3);
		bike = new Bike(this, new Vector(4.0f, 5.0f));
		addActor(bike);
		setViewCandidate(bike);
		// ActorGame.setViewCandidate(bike); ??????????
		finish = new Finish(this, new Vector(15.0f, 3.0f));
		addActor(finish);

		collision = false;
		victoire = false;
		message = new TextGraphics("", 0.3f, Color.RED, Color.WHITE, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f,
				100.0f);
		message.setParent(getCanvas());
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));

		return true;
	}

	public void update(float deltaTime) {
		super.update(deltaTime);
		if (bike.getHit()) {
			collision = true;
			victoire = false;
		}
		if (getKeyboard().get(KeyEvent.VK_R).isPressed()) {
			this.end();
			this.begin(getWindow(), getFileSystem());
		}
		for (int i = 0; i < ActorList.size(); i++) {
			if (!((GameEntity) ActorList.get(i)).getEntity().isAlive()) {
				deleteActor(ActorList.get(i));
			}
		}
		if (collision) {
			message.setText("sa fé tré mal");
			message.draw(getCanvas());
			bike.destroy();
			deleteActor(bike);
		}
		if (finish.getHit()) {
			victoire = true;
		}
		if (victoire) {
			message.setText("c gagner");
			message.draw(getCanvas());
			bike.victoryArms();

		}
	}

	public void end() {
		for (Actor actor : ActorList) {
			actor.destroy();
		}
	}
}

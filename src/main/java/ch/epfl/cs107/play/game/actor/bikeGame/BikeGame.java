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
import ch.epfl.cs107.play.math.RopeConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Window;

public class BikeGame extends ActorGame {

	private Finish finish;
	private Bike bike;
	private BoutonCrate bouton;
	private BoutonCrate bouton2;
	private TextGraphics message;
	private boolean collision;
	private boolean victoire;

	private boolean boutonhit;
	private boolean boutonhit2;
	
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		Terrain terrain = new Terrain(this, new Vector(0f, 0f));
		addActor(terrain);

		Crate crate1 = new Crate(this , false, new Vector(3.0f ,5.0f), 1, 1, "crate.1.png");
		addActor(crate1);
		Crate crate2 = new Crate(this , false, new Vector(5.0f ,7.0f), 1, 1, "crate.1.png");
		addActor(crate2);
		Crate crate3 = new Crate(this , false, new Vector(6.0f ,6.0f), 1, 1, "crate.1.png");

		addActor(crate3);
		Crate crate4 = new Crate(this , true, new Vector(-55.0f ,0.0f), 1, 2, "");
		addActor(crate4);
		GhostCrate crate5 = new GhostCrate(this,true,new Vector(-55.5f, 1.75f),0.5f ,1f, "bow.png");
		addActor(crate5);
		Crate crate6 = new Crate(this ,true , new Vector(-40.0f ,0.0f), 1, 1, "crate.2.png");
		addActor(crate6);
		bouton = new BoutonCrate(this, true,new Vector(-25.0f, 0.0f), 1, 1,"button.red.png");
		addActor(bouton);
		bouton2 = new BoutonCrate(this, true,new Vector(90.0f, 0.0f), 1, 1,"button.red.png");
		addActor(bouton2);
		bike = new Bike(this, new Vector(-50.0f, 10.0f));
		addActor(bike);
		setViewCandidate(bike);

		finish = new Finish(this, new Vector(76.0f, 0.0f));
		TerrainGlissant terrainGlissant = new TerrainGlissant(this, new Vector(77.0f, 0.0f));
		addActor(terrainGlissant);
		addActor(finish);
		Arrow arrow = new Arrow(this, new Vector(-55f, 3.0f), 1f, 0.5f , "arrow.png");
		addActor(arrow);
		collision = false;
		victoire = false;
		message = new TextGraphics("", 0.3f, Color.RED, Color.WHITE, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
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
		if (bouton.getHit()) {
			deleteActor(bouton);
			bouton.destroy();
			boutonhit = true;
		}
			
		if (boutonhit) {
			Crate crate1 = new Crate(this , false, new Vector(-25.0f ,4.0f), 1, 1, "crate.1.png");
			addActor(crate1);
			Crate crate2 = new Crate(this , false, new Vector(-24.0f ,4.5f), 1, 1, "crate.1.png");
			addActor(crate2);
			Crate crate3 = new Crate(this , false, new Vector(-23.0f ,5.0f), 1, 1, "crate.1.png");
			addActor(crate3);
			Crate crate4 = new Crate(this , false, new Vector(-22.0f ,5.5f), 1, 1, "crate.1.png");
			addActor(crate4);
			Crate crate5 = new Crate(this , false, new Vector(-21.0f ,6.0f), 1, 1, "crate.1.png");
			addActor(crate5);
			Crate crate6 = new Crate(this , false, new Vector(-20.0f ,6.5f), 1, 1, "crate.1.png");
			addActor(crate6);
			Crate crate7 = new Crate(this , false, new Vector(-19.0f ,7.0f), 1, 1, "crate.1.png");
			addActor(crate7);
			Crate crate8 = new Crate(this , false, new Vector(-18.0f ,7.5f), 1, 1, "crate.1.png");
			addActor(crate8);
			Crate crate9 = new Crate(this , false, new Vector(-17.0f ,8.0f), 1, 1, "crate.1.png");
			addActor(crate9);
			GhostCrate bouton2 = new GhostCrate(this, true, new Vector(-25.0f, 0.0f), 1, 1,"button.red.pressed.png");
			addActor(bouton2);
			boutonhit = false;
		}
		if (bouton2.getHit()) {
			deleteActor(bouton2);
			bouton2.destroy();
			boutonhit2 = true;
		}
			
		if (boutonhit2) {
			Crate crate1 = new Crate(this , false, new Vector(85.0f ,3.0f), 10, 10, "duck.png");
			addActor(crate1);
			GhostCrate bouton2 = new GhostCrate(this, true, new Vector(90.0f, 0.0f), 1, 1,"button.red.pressed.png");
			addActor(bouton2);
			boutonhit2 = false;
			
		}
		if (getKeyboard().get(KeyEvent.VK_C).isPressed()) {
			if (bike.getControl()) {
				bike.setControlTwo();
				System.out.print("hello");
			}
			else if (!bike.getControl()) {
				bike.setControlOne();
			}
		}
		
		if (getKeyboard().get(KeyEvent.VK_R).isPressed()) {
				this.end();
				this.begin(getWindow(), getFileSystem());
		} 
		for(int i = 0; i < ActorList.size(); i++) {
			if(!((GameEntity)ActorList.get(i)).getEntity().isAlive()) {
				deleteActor(ActorList.get(i));
			}
		}
	}

	public void end() {
		for (Actor actor : ActorList) {
			actor.destroy();
		}
	}
}

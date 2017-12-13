package ch.epfl.cs107.play.game.actor.bikeGame;

import java.awt.Color;
import java.awt.event.KeyEvent;
import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Arrow;
import ch.epfl.cs107.play.game.actor.general.BoutonCrate;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.game.actor.general.TerrainGlissant;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGame extends ActorGame {

	private Finish finish;
	private Bike bike;
	private BoutonCrate bouton;
	private BoutonCrate bouton2;
	private Crate frog;
	private TextGraphics message;
	//indique si le cycliste est touché
	private boolean collision;
	//indique que le joueur a gagné
	private boolean victoire;

	//indique si le premier bouton est touché
	private boolean boutonhit;
	//indique si le deuxieme bouton est touché
	private boolean boutonhit2;
	
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		//On crée tous les acteurs et on les ajoute a la liste des acteurs
		Terrain terrain = new Terrain(this, new Vector(0f, 0f));
		addActor(terrain);
		Crate crate1 = new Crate(this , false, new Vector(3.0f ,5.0f), 1, 1, "crate.1.png", false);
		addActor(crate1);
		Crate crate2 = new Crate(this , false, new Vector(5.0f ,7.0f), 1, 1, "crate.1.png", false);
		addActor(crate2);
		Crate crate3 = new Crate(this , false, new Vector(6.0f ,6.0f), 1, 1, "crate.1.png", false);
		addActor(crate3);
		Crate crate4 = new Crate(this , true, new Vector(-55.0f ,0.0f), 1, 2, "ghost.right.1.png", false);
		addActor(crate4);
		Crate crate5 = new Crate(this , true, new Vector(176.0f ,0.0f), 1, 2, "ghost.left.1.png", false);
		addActor(crate5);
		Crate crate6 = new Crate(this,true,new Vector(-55.5f, 1.75f),0.5f ,1f, "bow.png", true);
		addActor(crate6);
		Crate crate7 = new Crate(this ,true , new Vector(-40.0f ,0.0f), 1, 1, "crate.2.png", false);
		addActor(crate7);
		bouton = new BoutonCrate(this, true,new Vector(-25.0f, 0.0f), 1, 1,"button.red.png");
		addActor(bouton);
		bouton2 = new BoutonCrate(this, true,new Vector(90.0f, 0.0f), 1, 1,"button.blue.png");
		addActor(bouton2);
		frog = new Crate(this, true, new Vector(120, 0), 2, 2, "frog.left.png", true);
		addActor(frog);
		TerrainGlissant terrainGlissant = new TerrainGlissant(this, new Vector(77.0f, 0.0f));
		addActor(terrainGlissant);
		finish = new Finish(this, new Vector(76.0f, 0.0f));
		addActor(finish);
		Arrow arrow = new Arrow(this, new Vector(-55f, 3.0f), 1f, 0.5f , "arrow.png");
		addActor(arrow);
		bike = new Bike(this, new Vector(-50.0f, 3.0f));
		addActor(bike);
		//On met la vue sur le cycliste
		setViewCandidate(bike);
		
		//On indique que le cycliste n'est pas touché et n'a pas gagné (utile quand reset)
		collision = false;
		victoire = false;
		//On crée le message (vide)
		message = new TextGraphics("", 0.3f, Color.RED, Color.WHITE, 0.02f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		message.setParent(getCanvas()); 
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		return true;
	}

	public void update(float deltaTime) {
		super.update(deltaTime);
		//en premier, on regarde si le velo a été touché et on le signale si oui
		if (bike.getHit()) {
			collision = true;
			//la victoire n'est plus quand le cyliste est touché
			victoire = false;
		}
		//si le velo est touché on le supprime et on affiche un message
		if (collision) {
			message.setText("Ouch, restart!");
			message.draw(getCanvas());					
			bike.destroy();
			deleteActor(bike);
		}
		
		//on vérifie que les acteurs sont en vie, on les suprime dans le cas contraire
		for (int i = 0; i < ActorList.size(); i++) {
			if (!((GameEntity) ActorList.get(i)).getEntity().isAlive()) {
				deleteActor(ActorList.get(i));
			}
		}
		
		//On crée et lance une banane a l'arriere du velo quand b est pressé
		if (getKeyboard().get(KeyEvent.VK_B).isPressed()) {
			Banana banana = new Banana(this, false, (bike.getPosition()), 0.5f, 0.5f, "banana.png");
			addActor(banana);
			banana.launch(bike.getRegard());
		}
		
		//On verifie si le drapeau est touché, on le signale le cas échéant
		if (finish.getHit()) {
			victoire = true;
		}
		
		//si c'est une victoire, un message s'affiche, le cycliste leve les bras et la grenouille se transforme en princesse
		if (victoire) {
			message.setText("Congrats!");
			message.draw(getCanvas());
			bike.victoryArms();
			deleteActor(frog);
			Crate princesse = new Crate(this, true, new Vector(120.0f, 0.0f), 3, 3, "frog.princesse.png", true);
			addActor(princesse);
		}
		
		//On verifie si le premier bouton est touché, si oui on le signal et on le détruit
		if (bouton.getHit()) {
			deleteActor(bouton);
			bouton.destroy();
			boutonhit = true;
		}
		
		//si le premier bouton est touché, on crée les caisses correspondante et on remplace le bouton par le meme pressé
		if (boutonhit) {
			Crate crate1 = new Crate(this , false, new Vector(-25.0f ,4.0f), 1, 1, "crate.1.png", false);
			addActor(crate1);
			Crate crate2 = new Crate(this , false, new Vector(-24.0f ,4.5f), 1, 1, "crate.1.png", false);
			addActor(crate2);
			Crate crate3 = new Crate(this , false, new Vector(-23.0f ,5.0f), 1, 1, "crate.1.png", false);
			addActor(crate3);
			Crate crate4 = new Crate(this , false, new Vector(-22.0f ,5.5f), 1, 1, "crate.1.png", false);
			addActor(crate4);
			Crate crate5 = new Crate(this , false, new Vector(-21.0f ,6.0f), 1, 1, "crate.1.png", false);
			addActor(crate5);
			Crate crate6 = new Crate(this , false, new Vector(-20.0f ,6.5f), 1, 1, "crate.1.png", false);
			addActor(crate6);
			Crate crate7 = new Crate(this , false, new Vector(-19.0f ,7.0f), 1, 1, "crate.1.png", false);
			addActor(crate7);
			Crate crate8 = new Crate(this , false, new Vector(-18.0f ,7.5f), 1, 1, "crate.1.png", false);
			addActor(crate8);
			Crate crate9 = new Crate(this , false, new Vector(-17.0f ,8.0f), 1, 1, "crate.1.png", false);
			addActor(crate9);
			Crate bouton2 = new Crate(this, true, new Vector(-25.0f, 0.0f), 1, 1,"button.red.pressed.png", true);
			addActor(bouton2);
			//on change en false pour ne faire l'action qu'une fois par partie
			boutonhit = false;
		}
		
		//On verifie si le deuxieme bouton est touché, si oui on le signale
		//et on le remplace par un bouton pressé
		if (bouton2.getHit()) {
			deleteActor(bouton2);
			bouton2.destroy();
			boutonhit2 = true;
		}
		
		//si le deuxieme bouton est touché, on crée un canard géant 
		if (boutonhit2) {
			Crate crate1 = new Crate(this , false, new Vector(85.0f ,3.0f), 10, 10, "duck.png", false);
			addActor(crate1);
			Crate bouton2 = new Crate(this, true, new Vector(90.0f, 0.0f), 1, 1,"button.blue.pressed.png", true);
			addActor(bouton2);
			//on change en false pour ne faire l'action qu'une fois par partie
			boutonhit2 = false;	
		}
		
		//On change les controles quand c est pressé
		if (getKeyboard().get(KeyEvent.VK_C).isPressed()) {
			//on vérifie quels controles sont actifs et on applique les autres
			if (bike.getControl()) 
			bike.setControl(false);			
			
			else if (!bike.getControl()) 
			bike.setControl(true);
		}
		
		//Le jeu est reset (on le detruit pour le reconstruire) quand r est pressé
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

	//la methode end détruit tout les acteurs
	public void end() {
		for (Actor actor : ActorList) {
			actor.destroy();
		}
	}
}

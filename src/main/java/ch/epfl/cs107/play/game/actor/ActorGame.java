package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.DefaultFileSystem;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.io.FolderFileSystem;
import ch.epfl.cs107.play.io.ResourceFileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

public abstract class ActorGame implements Game {

	private ImageGraphics backGround;

	// Store context
	private Window window;

	// We need our physics engine
	private World world;

	public Keyboard getKeyboard() {
		return window.getKeyboard();
	}

	public Canvas getCanvas() {
		return window;
	}

	public Window getWindow() {
		return window;
	}

	public FileSystem getFileSystem() {
		return fileSystem;
	}

	// Define cascading file system
	FileSystem fileSystem = new FolderFileSystem(new ResourceFileSystem(DefaultFileSystem.INSTANCE));

	protected ArrayList<Actor> ActorList = new ArrayList<Actor>();

	public void addActor(Actor actor) {
		ActorList.add(actor);
	}

	public void deleteActor(Actor actor) {
		ActorList.remove(actor);
	}

	// Viewport properties
	private Vector viewCenter;
	private Vector viewTarget;
	private Positionable viewCandidate;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
	private static final float VIEW_SCALE = 15.0f;

	public void setViewCandidate(Positionable viewCandidate) {
		this.viewCandidate = viewCandidate;
	}

	public Entity createEntity(Entity entity, boolean fixed, Vector position) {
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(fixed);
		entityBuilder.setPosition(position);
		entity = entityBuilder.build();
		return entity;
	}

	public WheelConstraintBuilder createWheelConstraint() {
		return world.createWheelConstraintBuilder();
	}

	// This event is raised when game has just started
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {

		// Store context
		this.window = window;

		// Create physics engine
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));

		backGround = new ImageGraphics("sun.1.png", 1, 1, new Vector( 0.5f, 0.5f), 1f, 1f);
		backGround.setDepth(-5);
		backGround.setParent(window);

		viewCenter = Vector.ZERO;
		viewTarget = Vector.ZERO;

		return true;
	}

	@Override
	public void update(float deltaTime) {

		// Simulate physics
		// Our body is fixed , though , nothing will move
		world.update(deltaTime);

		for (Actor actor : ActorList) {
			actor.update(deltaTime);
		}

		// we must place the camera where we want
		// We will look at the origin (identity) and increase the view size a bit
		window.setRelativeTransform(Transform.I.scaled(10.0f));

		// Update expected viewport center
		if (viewCandidate != null) {
			viewTarget = viewCandidate.getPosition()
					.add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION));
		}
		// Interpolate with previous location
		float ratio = (float) Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
		viewCenter = viewCenter.mixed(viewTarget, ratio);
		// Compute new viewport
		Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
		window.setRelativeTransform(viewTransform);

		backGround.draw(window);

		for (Actor actor : ActorList) {
			actor.draw(window);
		}

		// The actual rendering will be done now, by the program loop
	}

	@Override
	public void end() {
		// Empty on purpose, no cleanup required yet
	}

}

import java.util.LinkedList;
import java.util.Random;

public class Game {
	private Ghost ghost;
	private LinkedList<Element> elements;
	private boolean paused;
	private boolean debugMode;
	private int score;
	private int speed; // Scrolling speed in pixels per second
	private Controller controller;
	private int passedCounter;
	private long lastTimeSpawned;
	
	public Game(Controller controller) {
		this.speed = 120;
		this.controller = controller;
		
		// Get canvas size
		double[] canvasSize = controller.getCanvasSize();

		// Initiate ghost at middle X minus its radius and bottom Y minus its diameter
		Ghost ghost = new Ghost(
			30,
			canvasSize[0] / 2 - 30,
			canvasSize[1] - 120,
			canvasSize[1]
		);
		this.ghost = ghost;
		this.elements = new LinkedList<Element>();
		this.elements.addFirst(ghost);
		//spawnObstacle(0);
	}
	
	public void spawnObstacle(long now) {
		if ((now - getLastTimeSpawned()) >= (3 / 1e-9)) {
			Random random = new Random();
			int randInt = random.nextInt(3); // Randomize int between 0 and 2 to pick Obstacle
			int randRadius = random.nextInt((45 - 10) + 1) + 10; // Size of obstacle radius
			
			double[] canvasSize = getController().getCanvasSize();
			double randY = random.nextInt(((int) canvasSize[1]) + 1); // Random starting Y

			switch (randInt) {
				case 0 : getElements().add(new SimpleObstacle(randRadius,  canvasSize[0], randY));
					break;
				case 1 : getElements().add(new SinusObstacle(randRadius,  canvasSize[0], randY, 50));
					break;
				case 2 : getElements().add(new SimpleObstacle(randRadius,  canvasSize[0], randY));
				//new QuantiqueObstacle();
					break;
				default: break;
			}
			
			setLastTimeSpawned(now);
		}
	}
	
	public Ghost getGhost() {
		return ghost;
	}

	public void setGhost(Ghost ghost) {
		this.ghost = ghost;
	}

	public LinkedList<Element> getElements() {
		return elements;
	}

	public void setElements(LinkedList<Element> elements) {
		this.elements = elements;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void incrementScore(int increment) {
		this.score += increment;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void incrementSpeed(int increment) {
		this.speed += increment;
	}

	public int getPassedCounter() {
		return passedCounter;
	}

	public void setPassedCounter(int passedCounter) {
		this.passedCounter = passedCounter;
	}
	
	public void incrementPassedCounter(int increment) {
		this.passedCounter += increment;
	}

	public long getLastTimeSpawned() {
		return lastTimeSpawned;
	}

	public void setLastTimeSpawned(long lastTimeSpawned) {
		this.lastTimeSpawned = lastTimeSpawned;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}

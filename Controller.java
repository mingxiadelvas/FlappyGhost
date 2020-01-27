import java.util.LinkedList;

public class Controller {
	private View view;
	private Game game;
	
	/*
	 *	Controller function 
	 */
	public Controller(View view) {
		this.view = view;
		this.game = new Game(this);
	}
	
	public void handleGhostJump() {
		getGame().getGhost().jump();
	}
	
	public void handleFrame(double deltaTime) {
		view.drawBackground(getGame().getSpeed() * deltaTime); // Draw background offset from game speed
		LinkedList<Element> elements = getGame().getElements(); // Get elements
				
		elements.forEach((element) -> {
			element.move(getGame().getSpeed(), deltaTime); // Ghost always moves first
			
			// Check for score and collision
			if (element instanceof Obstacle) {
				checkScore((Obstacle) element);
				checkCollision((Obstacle) element);
			}
						
			// Draw element
			drawElement(element);	
		});
	}
	
	public void handleSpawn(long now) {
		getGame().spawnObstacle(now);
	}
	
	public void checkCollision(Obstacle obstacle) {
		Ghost ghost = getGame().getGhost();
		if (ghost.intersects(obstacle)) {
			obstacle.setColliding(true);
			
			if (getGame().isDebugMode() == false) {
				newGame(); // Game lost, so start a new game
				getView().updateScore(0); // Reset score
			}
		} else if (obstacle.isColliding() == true) {
			obstacle.setColliding(false);
		}
	}
	
	public void checkScore(Obstacle obstacle) {
		if (getGame().getGhost().isPastGhost(obstacle) == true && obstacle.isPassed() == false) {
			obstacle.setPassed(true);
			getGame().incrementPassedCounter(1);
			getGame().incrementScore(5);
			view.updateScore(getGame().getScore()); // Update score for user
			
			// Accelerate speed if second object passed
			if (getGame().getPassedCounter() == 2) {
				getGame().setPassedCounter(0);
				getGame().incrementSpeed(15);
				getGame().getGhost().incrementGravity(15);
			}	
		}
	}
	
	public void drawElement(Element element) {
		if (getGame().isDebugMode()) {
			// Draw circle if in debug mode
			String color = "black"; // Black by default
			
			if (element instanceof Obstacle) {
				Obstacle obstacle = (Obstacle) element;
				if (obstacle.isColliding()) {
					color = "red";
				} else {
					color = "yellow";
				}
			}
				
			view.drawCircle(element.getRadius(), element.getX(), element.getY(), color);
		} else {
			// Draw images if not in debug mode
			view.drawImage(element.getImgLocation(), element.getRadius(), element.getX(), element.getY());
		}
	}
	
	public void handlePauseButton() {
		if(getGame().isPaused()) {
			getGame().setPaused(false);
			getView().updatePauseButton("Pause");
			getView().setlastTimeRendered(0);
			getView().getTimer().start();
		} else {
			getGame().setPaused(true);
			getView().updatePauseButton("Resume");
			getView().getTimer().stop();
		}
	}
	
	public void handleDebugCheckbox() {
		if (getGame().isDebugMode()) {
			getGame().setDebugMode(false);
		} else {
			getGame().setDebugMode(true);
		}
		handleFrame(0);
	}
	
	public void newGame() {
		this.game = new Game(this);
	}

	public View getView() {
		return view;
	}
	
	public double[] getCanvasSize() {
		double[] viewSize = { view.getWidth(), view.getCanvasHeight() }; 
		return viewSize;
	}

	public Game getGame() {
		return game;
	}
}

public class SimpleObstacle extends Obstacle {
	public SimpleObstacle(int radius, double x, double y) {
		super(radius, x, y);
	}
	
	public void move(int gameSpeed, double deltaTime) {
		setVx(-gameSpeed); // Update speed
		setX(getX() + deltaTime * getVx()); // Calculate new position
	}
}

import java.util.Random;

public abstract class Obstacle extends Element {
	private boolean passed; // Has the ghost passed or not
	private boolean colliding; // Colliding with ghost
	
	public Obstacle(int radius, double x, double y) {
		super(radius, x, y);
		setImgLocation(pickRandomImage());
	};
	
	private String pickRandomImage() {
		Random r = new Random();
		int index = r.nextInt(27);
		return "./assets/obstacles/" + index + ".png";
	}

	public boolean isPassed() {
		return passed;
	}

	public void setPassed(boolean passed) {
		this.passed = passed;
	}

	public boolean isColliding() {
		return colliding;
	}

	public void setColliding(boolean colliding) {
		this.colliding = colliding;
	}
}
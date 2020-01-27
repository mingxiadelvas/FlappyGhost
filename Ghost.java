
public class Ghost extends Element {
	private double jumpSpeed;
	private double gravity;
	private double ay; // Acceleration on Y axis
	private double maxHeight;
	
	public Ghost(int radius, double x, double y, double maxHeight) {
		super(radius, x, y);
		this.maxHeight = maxHeight;
		this.gravity = 500;
		this.jumpSpeed = -300;
		setAy(gravity); // Set acceleration to gravity
		setImgLocation("./assets/ghost.png");
	}
	
	public void move(int gameSpeed, double deltaTime) {
		setVy(getVy() + deltaTime * getAy()); // Update speed
		double newY = getY() + deltaTime * getVy(); // Calculate new position
	
		// Bounce on Y edges
		if(newY + getRadius() * 2 > getMaxHeight()) {
			jump();
		} else if (newY < 0) {
			setVy(Math.abs(getJumpSpeed())); // Reverse jump speed
		} else {
			setY(newY); // Update position	
		}		
	}
	
	public boolean intersects(Obstacle obstacle) {
		double dx = getX() - obstacle.getX();
		double dy = getY() - obstacle.getY();
		double dCarre = dx * dx + dy * dy;
		return dCarre < (getRadius() + obstacle.getRadius()) * (getRadius() + obstacle.getRadius());
	}
	
	public void jump() {
		setVy(getJumpSpeed());
	}
	
	// Check if Obstacle is completely past a ghost X position
	public boolean isPastGhost(Obstacle obstacle) {
		return (obstacle.getX() + 2 * obstacle.getRadius() < this.getX());
	};
	
	public double getGravity() {
		return gravity;
	}
	
	public void incrementGravity(double increment) {
		this.gravity += increment;
	}

	public double getJumpSpeed() {
		return jumpSpeed;
	}

	public void setJumpSpeed(double jumpSpeed) {
		this.jumpSpeed = jumpSpeed;
	}

	public double getAy() {
		return ay;
	}

	public void setAy(double ay) {
		this.ay = ay;
	}
	
	public double getMaxHeight() {
		return this.maxHeight;
	}
	
	@Override
	public void setVy(double vy) {
		if (vy > 300) {
			vy = 300;
		}
		
		super.setVy(vy);
	}
}

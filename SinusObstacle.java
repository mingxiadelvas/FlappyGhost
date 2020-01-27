
public class SinusObstacle extends Obstacle{
	private double amplitude;
	private double maxHeight;
	private double minHeight;
	private double randomVy;
	
	public SinusObstacle(int radius, double x, double y, double amplitude) {
		super(radius, x, y);
		this.amplitude = amplitude;
		this.maxHeight = getY() + amplitude;
		this.minHeight = getMaxHeight() - amplitude;
		this.randomVy = Math.sin(Math.random() * getVy());
	}

	@Override
	public void move(int gameSpeed, double deltaTime) {
		setY(getY() + deltaTime * getVy());
		setVy(getVy() + deltaTime * getRandomVy());
	
		setVx(-gameSpeed); // Update speed
		setX(getX() + deltaTime * getVx()); // Calculate new position;
		
		if (getY() + getRadius() * 2 > getMaxHeight()) {
			setVy(-getY());
		} else if (getY() + getRadius() * 2 < getMinHeight()) {
			setVy(getY());
		} else {
			setY(getY());
		}
	}

	public double getRandomVy() {
		return randomVy;
	}

	public void setRandomVy(double randomVy) {
		this.randomVy = randomVy;
	}	
	public double getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(double maxHeight) {
		this.maxHeight = maxHeight;
	}

	public double getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(double minHeight) {
		this.minHeight = minHeight;
	}

}


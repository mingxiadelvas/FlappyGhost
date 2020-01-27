
public class QuantiqueObstacle extends Obstacle {
	private double distance;
	private double minDistanceX;
	private double maxDistanceX;
	private double minDistanceY;
	private double maxDistanceY;
	
	public QuantiqueObstacle(int radius, double x, double y, double distance) {
		super(radius, x, y);
		this.distance = distance;
		this.minDistanceX = getX() - distance; 
		this.maxDistanceX = getX() + distance;
		this.minDistanceY = getY() - distance;
		this.maxDistanceY = getY() + distance;
	}


	public double getDistance() {
		return distance;
	}


	public void setDistance(double distance) {
		this.distance = distance;
	}


	@Override
	public void move(int gameSpeed, double deltaTime) {
		setX(getX() + deltaTime * getVx());
		setVx(-gameSpeed);
		double random = Math.floor(Math.random()*30)+1;
		setY(getY() + deltaTime * getVy());
		setVy(getVy() + deltaTime * getVy());
		//System.out.println(random);
		if(getX() + getRadius() * 2 < getMinDistanceX()) {
			setVx(random);
		} else if (getX() + deltaTime * 2 > getMaxDistanceX()) {
			setVx(-random);
		} else if(getY() + getRadius() * 2 < getMinDistanceY()) {
			setVy(random);
		} else {
			setVy(-random);
		}
		
	
	}


	public double getMinDistanceX() {
		return minDistanceX;
	}


	public void setMinDistanceX(double minDistanceX) {
		this.minDistanceX = minDistanceX;
	}


	public double getMaxDistanceX() {
		return maxDistanceX;
	}


	public void setMaxDistanceX(double maxDistanceX) {
		this.maxDistanceX = maxDistanceX;
	}


	public double getMinDistanceY() {
		return minDistanceY;
	}


	public void setMinDistanceY(double minDistanceY) {
		this.minDistanceY = minDistanceY;
	}


	public double getMaxDistanceY() {
		return maxDistanceY;
	}


	public void setMaxDistanceY(double maxDistanceY) {
		this.maxDistanceY = maxDistanceY;
	}

}

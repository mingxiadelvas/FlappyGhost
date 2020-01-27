
public abstract class Element {
	private int radius;
	private double x;
	private double y;
	private double vx; // speed on X axis
	private double vy; // speed on Y axis
	private String imgLocation;
	
	public Element(int radius, double x, double y) {
		this.radius = radius;
		this.x = x;
		this.y = y;
	};
	
	public abstract void move(int gameSpeed, double deltaTime);

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getVx() {
		return vx;
	}

	public void setVx(double vx) {
		this.vx = vx;
	}

	public double getVy() {
		return vy;
	}

	public void setVy(double vy) {
		this.vy = vy;
	}
	
	public String getImgLocation() {
		return imgLocation;
	}

	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}
}

package miniBiblioteca;

public class Ponto {

	private float x;
	private float y;
	private float z;
	private boolean dimension;

	public Ponto(float x, float y, float z, boolean dim) {
		super();
		this.x = x;
		this.y = y;
		if (dim) {
			this.z = z;
		}
		this.dimension = dim;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public boolean isDimension() {
		return dimension;
	}
	
	public void print(){
		System.out.print("(");
		System.out.print(this.getX() + ", ");
		System.out.print(this.getY());
		if(this.isDimension())
			System.out.println(", " + this.getZ() + ")");
		else 
			System.out.println(")");
	}
}

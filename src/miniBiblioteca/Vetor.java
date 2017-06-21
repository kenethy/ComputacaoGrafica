package miniBiblioteca;

public class Vetor {

	private float x;
	private float y;
	private float z;

	public Vetor(float x, float y, float z) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
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
	
	public void print(){
		System.out.print("(");
		System.out.print(this.getX() + ", ");
		System.out.print(this.getY() + ", ");
		System.out.println(this.getZ() + ")");
	}

}

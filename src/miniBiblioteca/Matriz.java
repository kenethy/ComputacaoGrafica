package miniBiblioteca;

public class Matriz {
	private int linha;
	private int coluna;
	private float[][] matriz;

	public Matriz(int linha, int coluna) {
		super();
		this.linha = linha;
		this.coluna = coluna;
		this.matriz = new float[linha][coluna];
	}

	public int getLinha() {
		return linha;
	}

	public void setLinha(int linha) {
		this.linha = linha;
	}

	public int getColuna() {
		return coluna;
	}

	public void setColuna(int coluna) {
		this.coluna = coluna;
	}

	public float[][] getMatriz() {
		return matriz;
	}

	public void setMatrizLine(int l, Vetor V) {
		this.matriz[l][0] = V.getX();
		this.matriz[l][1] = V.getY();
		this.matriz[l][2] = V.getZ();
	}

	public void setMatriz(float[][] matriz) {
		this.matriz = matriz;
	}

	public void print() {
		for (int i = 0; i < this.getLinha(); i++) {
			for (int j = 0; j < this.getColuna(); j++) {
				System.out.print(this.getMatriz()[i][j] + "\t ");
			}
			System.out.println();
		}
	}
}

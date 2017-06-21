package malhaDeTriangulos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import miniBiblioteca.Ponto;
import miniBiblioteca.Vetor;

public class CameraVirtual {

	private Ponto C;
	private Vetor N, V;
	private float d, hx, hy;

	public void load() throws IOException {
		File read = new File("imagens/parametrosCamera.byu");
		BufferedReader in = new BufferedReader(new FileReader(read));

		String str = in.readLine();
		String[] values = str.split(" ");
		this.C = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]), true);

		str = in.readLine();
		values = str.split(" ");
		this.N = new Vetor(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));

		str = in.readLine();
		values = str.split(" ");
		this.V = new Vetor(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));

		str = in.readLine();
		values = str.split(" ");
		this.setEscalarD(Float.parseFloat(values[0]));
		this.setEscalarHx(Float.parseFloat(values[1]));
		this.setEscalarHy(Float.parseFloat(values[2]));
	}

	public Ponto getPontoC() {
		return C;
	}

	public void setPontoC(Ponto c) {
		C = c;
	}

	public Vetor getVetorV() {
		return V;
	}

	public void setVetorV(Vetor v) {
		V = v;
	}

	public Vetor getVetorN() {
		return N;
	}

	public void setVetorN(Vetor n) {
		N = n;
	}

	public float getEscalarD() {
		return d;
	}

	public void setEscalarD(float d) {
		this.d = d;
	}

	public float getEscalarHy() {
		return hy;
	}

	public void setEscalarHy(float hy) {
		this.hy = hy;
	}

	public float getEscalarHx() {
		return hx;
	}

	public void setEscalarHx(float hx) {
		this.hx = hx;
	}

}

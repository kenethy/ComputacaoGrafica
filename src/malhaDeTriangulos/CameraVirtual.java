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

	private int[] Iamb;
	private int[] Il;
	private float Ka, Ks, n;
	private Vetor Kd, Od;
	private Ponto Pl;

	@SuppressWarnings("resource")
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

		str = in.readLine();
		values = str.split(" ");
		this.Iamb = new int[3];
		this.Iamb[0] = Integer.parseInt(values[0]);
		this.Iamb[1] = Integer.parseInt(values[1]);
		this.Iamb[2] = Integer.parseInt(values[2]);

		str = in.readLine();
		values = str.split(" ");
		this.Il = new int[3];
		this.Il[0] = Integer.parseInt(values[0]);
		this.Il[1] = Integer.parseInt(values[1]);
		this.Il[2] = Integer.parseInt(values[2]);

		str = in.readLine();
		values = str.split(" ");
		this.Ka = Float.parseFloat(values[0]);
		this.Ks = Float.parseFloat(values[1]);
		this.n = Float.parseFloat(values[2]);

		str = in.readLine();
		values = str.split(" ");
		this.Kd = new Vetor(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));

		str = in.readLine();
		values = str.split(" ");
		this.Od = new Vetor(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));

		str = in.readLine();
		values = str.split(" ");
		this.Pl = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]),
				true);
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

	public Ponto getC() {
		return C;
	}

	public void setC(Ponto c) {
		C = c;
	}

	public Vetor getN() {
		return N;
	}

	public void setN(Vetor n) {
		N = n;
	}

	public Vetor getV() {
		return V;
	}

	public void setV(Vetor v) {
		V = v;
	}

	public float getD() {
		return d;
	}

	public void setD(float d) {
		this.d = d;
	}

	public float getHx() {
		return hx;
	}

	public void setHx(float hx) {
		this.hx = hx;
	}

	public float getHy() {
		return hy;
	}

	public void setHy(float hy) {
		this.hy = hy;
	}

	public int[] getIamb() {
		return Iamb;
	}

	public void setIamb(int[] iamb) {
		Iamb = iamb;
	}

	public int[] getIl() {
		return Il;
	}

	public void setIl(int[] il) {
		Il = il;
	}

	public float getKa() {
		return Ka;
	}

	public void setKa(float ka) {
		Ka = ka;
	}

	public float getKs() {
		return Ks;
	}

	public void setKs(float ks) {
		Ks = ks;
	}

	public float getn() {
		return n;
	}

	public void setN(float n) {
		this.n = n;
	}

	public Vetor getKd() {
		return Kd;
	}

	public void setKd(Vetor kd) {
		Kd = kd;
	}

	public Vetor getOd() {
		return Od;
	}

	public void setOd(Vetor od) {
		Od = od;
	}

	public Ponto getPl() {
		return Pl;
	}

	public void setPl(Ponto pl) {
		Pl = pl;
	}
}

package miniBiblioteca;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TesteDeOperacoes {

	public static void main(String[] args) throws IOException {

		File read = new File("testeDeOperacoes.txt");
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(read));

		// MULTIPLICAÇÃO DE MATRIZES
		System.out.println("MULTIPLICAÇÃO DE MATRIZES");

		String str = in.readLine();
		String[] values = str.split(" ");
		Matriz matA = new Matriz(Integer.parseInt(values[0]), Integer.parseInt(values[1]));

		for (int i = 0; i < matA.getLinha(); i++) {
			str = in.readLine();
			values = str.split(" ");
			for (int j = 0; j < matA.getColuna(); j++) {
				matA.getMatriz()[i][j] = Float.parseFloat(values[j]);
			}
		}

		str = in.readLine();
		values = str.split(" ");
		Matriz matB = new Matriz(Integer.parseInt(values[0]), Integer.parseInt(values[1]));

		for (int i = 0; i < matB.getLinha(); i++) {
			str = in.readLine();
			values = str.split(" ");
			for (int j = 0; j < matB.getColuna(); j++) {
				matB.getMatriz()[i][j] = Float.parseFloat(values[j]);
			}
		}

		(Biblioteca.multMatriz(matA, matB)).print();
		System.out.println();

		// SUBTRAÇÃO DE PONTOS
		System.out.println("SUBTRAÇÃO DE PONTOS");

		str = in.readLine();
		values = str.split(" ");
		Ponto A = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]),
				true);

		str = in.readLine();
		values = str.split(" ");
		Ponto B = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]),
				true);

		Biblioteca.subPontos(A, B).print();
		System.out.println();

		// PRODUTO ESCALAR
		System.out.println("PRODUTO ESCALAR");
		str = in.readLine();
		values = str.split(" ");
		Vetor V = new Vetor(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));
		str = in.readLine();
		values = str.split(" ");
		Vetor U = new Vetor(Float.parseFloat(values[0]), Float.parseFloat(values[1]), Float.parseFloat(values[2]));

		System.out.println(Biblioteca.prodEscalar(U, V));
		System.out.println();

		// PRODUTO VETORIAL
		System.out.println("PRODUTO VETORIAL");
		Biblioteca.prodVetorial(U, V).print();
		System.out.println();

		// NORMA DE UM VETOR
		System.out.println("NORMA DE UM VETOR");
		System.out.println((Biblioteca.norma(U)));
		System.out.println((Biblioteca.norma(V)));
		System.out.println();

		// NORMALIZAÇÃO DE UM VETOR
		System.out.println("NORMALIZAÇÃO DE UM VETOR");
		Biblioteca.normalizacao(U).print();
		Biblioteca.normalizacao(V).print();
		System.out.println();

		// COORDENADORA BARICÊNTRICA
		System.out.println("COORDENADA BARICÊNTRICA");
		str = in.readLine();
		values = str.split(" ");
		Ponto P = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), 0, false);

		str = in.readLine();
		values = str.split(" ");
		A = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), 0, false);

		str = in.readLine();
		values = str.split(" ");
		B = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), 0, false);

		str = in.readLine();
		values = str.split(" ");
		Ponto C = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), 0, false);

		Biblioteca.coordBaricentrica(P, A, B, C).print();
		System.out.println();

		// COORDENADA CARTESIANA
		System.out.println("COORDENADA CARTESIANA");
		str = in.readLine();
		values = str.split(" ");
		A = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), 0, false);

		str = in.readLine();
		values = str.split(" ");
		B = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), 0, false);

		str = in.readLine();
		values = str.split(" ");
		C = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), 0, false);

		str = in.readLine();
		values = str.split(" ");
		P = new Ponto(Float.parseFloat(values[0]), Float.parseFloat(values[1]), 0, false);

		Biblioteca.coordCartesiana(A, B, C, P).print();

	}
}

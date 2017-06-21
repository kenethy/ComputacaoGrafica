package malhaDeTriangulos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;

import miniBiblioteca.Biblioteca;
import miniBiblioteca.Matriz;
import miniBiblioteca.Ponto;
import miniBiblioteca.Vetor;

public class Malha {

	private static Scanner arquivo;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		arquivo = new Scanner(System.in);
		String nomeArquivo;

		/**
		 * DEFININDO IMAGEM A SER LIDA NA HORA DA EXECUÇÃO
		 */
		// System.out.println("NOME DO ARQUIVO: ");
		// nomeArquivo = arquivo.next();
		// File read = new File("imagens/" + nomeArquivo + ".byu");

		/**
		 * LEITURA DIRETA
		 */
		File read = new File("imagens/testepiramide.byu");
		BufferedReader in = new BufferedReader(new FileReader(read));

		/**
		 * DEFINÇÃO DE LARGURA DA TELA NA HORA DA EXECUÇÃO
		 */
		// System.out.println("ALTURA: ");
		// int H = arquivo.nextInt();

		// System.out.println("LARGURA: ");
		// int W = arquivo.nextInt();

		/**
		 * LARGURA DEFINIDA EM 320 X 320
		 */
		int H = 320;
		int W = 320;

		String str = in.readLine();
		String[] values = str.split(" ");
		int nVertices = Integer.parseInt(values[0]);
		int kTriangulos = Integer.parseInt(values[1]);

		int[][] coordenadas = new int[nVertices][3];
		int[][] indices = new int[kTriangulos][3];

		int xMin = Integer.MAX_VALUE;
		int xMax = Integer.MIN_VALUE;
		int yMin = Integer.MAX_VALUE;
		int yMax = Integer.MIN_VALUE;
		char[][] tela = new char[H][W];
		int[][] coordNorm = new int[nVertices][2];

		for (int i = 0; i < nVertices; i++) {
			str = in.readLine();
			values = str.split(" ");
			for (int j = 0; j < 3; j++)
				coordenadas[i][j] = Integer.parseInt(values[j]);
		}

		for (int i = 0; i < kTriangulos; i++) {
			str = in.readLine();
			values = str.split(" ");
			for (int j = 0; j < 3; j++)
				indices[i][j] = Integer.parseInt(values[j]);
		}

		for (int i = 0; i < coordenadas.length; i++) {
			if (coordenadas[i][0] < xMin)
				xMin = coordenadas[i][0];
			if (coordenadas[i][0] > xMax)
				xMax = coordenadas[i][0];
			if (coordenadas[i][1] < yMin)
				yMin = coordenadas[i][1];
			if (coordenadas[i][1] > yMax)
				yMax = coordenadas[i][1];
		}

		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				tela[i][j] = 'P';
			}
		}

		for (int i = 0; i < coordNorm.length; i++) {
			for (int j = 0; j < coordNorm[i].length; j++) {
				coordNorm[i][j] = ((coordenadas[i][j] - xMin) / (xMax - xMin)) * (H - 1);
				j++;
				coordNorm[i][j] = ((coordenadas[i][j] - yMin) / (yMax - yMin)) * (W - 1);
			}
		}

		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				for (int k = 0; k < nVertices; k++) {
					if (i == coordNorm[k][0] && j == coordNorm[k][1]) {
						tela[i][j] = 'B';
					}
				}
			}
		}

		CameraVirtual cv = new CameraVirtual();
		int key;
		cv.load();

		while (true) {
			key = 0;
			/**
			 * ORTOGONOLIZAR V
			 */
			float prodEscalarVN = Biblioteca.prodEscalar(cv.getVetorV(), cv.getVetorN());
			float prodEscalarNN = Biblioteca.prodEscalar(cv.getVetorN(), cv.getVetorN());
			Vetor v = new Vetor(cv.getVetorN().getX() * (prodEscalarVN / prodEscalarNN),
					cv.getVetorN().getY() * (prodEscalarVN / prodEscalarNN),
					cv.getVetorN().getZ() * (prodEscalarVN / prodEscalarNN));

			v.setX(cv.getVetorV().getX() - v.getX());
			v.setY(cv.getVetorV().getY() - v.getY());
			v.setZ(cv.getVetorV().getZ() - v.getZ());

			cv.setVetorV(v);

			/**
			 * CALCULAR VETOR U = N x V'
			 */
			Vetor U = Biblioteca.prodVetorial(cv.getVetorN(), cv.getVetorV());

			/**
			 * NORMALIZAR VETORES U, V, N
			 */
			U = Biblioteca.normalizacao(U);
			cv.setVetorV(Biblioteca.normalizacao(cv.getVetorV()));
			cv.setVetorN(Biblioteca.normalizacao(cv.getVetorN()));

			/**
			 * MATRIZ MUDANÇA DE BASE
			 */
			Matriz matMB = new Matriz(3, 3);
			matMB.setMatrizLine(0, U);
			matMB.setMatrizLine(1, cv.getVetorV());
			matMB.setMatrizLine(2, cv.getVetorN());

			/**
			 * PARA CADA TRIANGULO REALIZAR MUDANÇA MUNDIAL PARA VISTA
			 */
			Matriz pontos = new Matriz(kTriangulos, 3);
			Matriz M;

			for (int i = 0; i < nVertices; i++) {
				Matriz p = new Matriz(3, 1);
				Vetor vetorP = Biblioteca.subPontos(
						new Ponto(coordenadas[i][0], coordenadas[i][1], coordenadas[i][2], true), cv.getPontoC());

				p.getMatriz()[0][0] = vetorP.getX();
				p.getMatriz()[1][0] = vetorP.getY();
				p.getMatriz()[2][0] = vetorP.getZ();

				M = Biblioteca.multMatriz(matMB, p);

				pontos.setMatrizLine(i, new Vetor(M.getMatriz()[0][0], M.getMatriz()[1][0], M.getMatriz()[2][0]));
			}

			pontos.print();

			/**
			 * PROJEÇÃO EM PERSPECTIVA
			 */
			Matriz projecao = new Matriz(nVertices, 2);

			for (int i = 0; i < nVertices; i++) {
				projecao.getMatriz()[i][0] = cv.getEscalarD() * pontos.getMatriz()[i][0] / pontos.getMatriz()[i][2];
				projecao.getMatriz()[i][1] = cv.getEscalarD() * pontos.getMatriz()[i][1] / pontos.getMatriz()[i][2];
			}

			projecao.print();

			/**
			 * COORDENADAS NORMALIZADAS
			 */
			for (int i = 0; i < nVertices; i++) {
				projecao.getMatriz()[i][0] = projecao.getMatriz()[i][0] / cv.getEscalarHx();
				projecao.getMatriz()[i][1] = projecao.getMatriz()[i][1] / cv.getEscalarHy();
			}

			/**
			 * COORDENADAS DE TELA
			 */
			Matriz coordTela = new Matriz(nVertices, 2);
			for (int i = 0; i < nVertices; i++) {
				coordTela.getMatriz()[i][0] = (float) (((Math.sqrt(projecao.getMatriz()[i][0]) + 1) / 2) * W + 0.5);
				coordTela.getMatriz()[i][1] = (float) (H - ((Math.sqrt(projecao.getMatriz()[i][1]) + 1) / 2) * H + 0.5);
			}
			
			/**
			 * RASTERIZAR CADA TRIANGULO USANDO SCANLINE
			 */
			
			

			JFrame frame = new JFrame("tela");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(H + 100, W + 100);
			frame.add(new Pixels(H, W, tela));
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			while (key != 1) {
				key = arquivo.nextInt();
				if (key == 1)
					cv.load();
			}
		}
	}
}
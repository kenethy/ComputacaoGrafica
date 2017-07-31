package malhaDeTriangulos;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;

import miniBiblioteca.Biblioteca;
import miniBiblioteca.Matriz;
import miniBiblioteca.Ponto;
import miniBiblioteca.Vetor;

public class Malha {

	private static Scanner arquivo;

	public static void main(String[] args) throws IOException {
		arquivo = new Scanner(System.in);
		String nomeArquivo;

		/**
		 * DEFININDO IMAGEM A SER LIDA NA HORA DA EXECUCAO
		 */
		System.out.println("NOME DO ARQUIVO: ");
		nomeArquivo = arquivo.next();
		File read = new File("imagens/" + nomeArquivo + ".byu");

		/**
		 * LEITURA DIRETA
		 */
		// File read = new File("imagens/testePiramide.byu");
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(read));

		/**
		 * DEFINICAO DE LARGURA DA TELA NA HORA DA EXECUCAO
		 */
		// System.out.println("ALTURA: ");
		// int H = arquivo.nextInt();

		// System.out.println("LARGURA: ");
		// int W = arquivo.nextInt();

		/**
		 * LARGURA DEFINIDA EM 320 X 320
		 */
		int H = 600;
		int W = 600;

		String str = in.readLine();
		String[] values = str.split(" ");
		int nVertices = Integer.parseInt(values[0]);
		int kTriangulos = Integer.parseInt(values[1]);

		float[][] coordenadas = new float[nVertices][3];
		int[][] indices = new int[kTriangulos][3];

		float xMin = Float.MAX_VALUE;
		float xMax = Float.MIN_VALUE;
		float yMin = Float.MAX_VALUE;
		float yMax = Float.MIN_VALUE;
		char[][] tela = new char[H][W];
		float[][] coordNorm = new float[nVertices][2];

		for (int i = 0; i < nVertices; i++) {
			str = in.readLine();
			values = str.split(" ");
			for (int j = 0; j < 3; j++)
				coordenadas[i][j] = Float.parseFloat(values[j]);
		}

		for (int i = 0; i < kTriangulos; i++) {
			str = in.readLine();
			values = str.split(" ");
			for (int j = 0; j < 3; j++)
				indices[i][j] = Integer.parseInt(values[j]) - 1;
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

		CameraVirtual cv = new CameraVirtual();
		int key;
		cv.load();

		while (true) {
			key = 0;

			tela = new char[H][W];

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
			 * MATRIZ MUDANCA DE BASE
			 */
			Matriz matMB = new Matriz(3, 3);
			matMB.setMatrizLine(0, U);
			matMB.setMatrizLine(1, cv.getVetorV());
			matMB.setMatrizLine(2, cv.getVetorN());

			// matMB.print();

			/**
			 * PARA CADA TRIANGULO REALIZAR MUDANCA MUNDIAL PARA VISTA
			 */
			Matriz pontos = new Matriz(nVertices, 3);
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

			// pontos.print();

			/**
			 * PROJECAO EM PERSPECTIVA
			 */
			Matriz projecao = new Matriz(nVertices, 2);

			for (int i = 0; i < nVertices; i++) {
				projecao.getMatriz()[i][0] = cv.getEscalarD() * pontos.getMatriz()[i][0] / pontos.getMatriz()[i][2];
				projecao.getMatriz()[i][1] = cv.getEscalarD() * pontos.getMatriz()[i][1] / pontos.getMatriz()[i][2];
			}

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
				coordTela.getMatriz()[i][0] = (int) (((projecao.getMatriz()[i][0] + 1) / 2.0) * W + 0.5);
				coordTela.getMatriz()[i][1] = (int) (H - ((projecao.getMatriz()[i][1] + 1) / 2.0) * H + 0.5);
			}

			// coordTela.print();

			/**
			 * PINTAR PIXELS DAS COORDENADAS DE TELA
			 */

			for (int i = 0; i < H; i++) {
				for (int j = 0; j < W; j++) {
					for (int k = 0; k < nVertices; k++) {
						if (i == coordTela.getMatriz()[k][0] && j == coordTela.getMatriz()[k][1]) {
							tela[i][j] = 'B';
						}
					}
				}
			}
			// Color[][] color1 = new Color[H][W];
			//
			// for (int i = 0; i < H; i++) {
			// for (int j = 0; j < W; j++) {
			// color1[i][j] = Color.white;
			// }
			// }
			//
			// JFrame frame = new JFrame("Pixels Coordenadas de Tela");
			// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			// frame.setSize(H + 50, W + 50);
			// frame.add(new Pixels(H, W, tela, color1));
			// frame.setLocationRelativeTo(null);
			// frame.setVisible(true);

			/**
			 * 3ª PARTE DO PROJETO COMPUTAÇÃO GRÁFICA
			 */

			/**
			 * CALCULAR NORMAL DE CADA TRIANGULO
			 */
			float[] vertex1;
			float[] vertex2;
			float[] vertex3;

			Vetor[] normalTriangulo = new Vetor[kTriangulos];
			for (int i = 0; i < kTriangulos; i++) {
				vertex1 = pontos.getMatriz()[indices[i][0]];
				vertex2 = pontos.getMatriz()[indices[i][1]];
				vertex3 = pontos.getMatriz()[indices[i][2]];

				Vetor v2v1 = Biblioteca.subPontos(new Ponto(vertex2[0], vertex2[1], vertex2[2], true),
						new Ponto(vertex1[0], vertex1[1], vertex1[2], true));

				Vetor v3v1 = Biblioteca.subPontos(new Ponto(vertex3[0], vertex3[1], vertex3[2], true),
						new Ponto(vertex1[0], vertex1[1], vertex1[2], true));

				normalTriangulo[i] = Biblioteca.normalizacao(Biblioteca.prodVetorial(v2v1, v3v1));
			}

			// System.out.println("TRIANGULO");
			// for (int vertex = 0; vertex < kTriangulos; vertex++) {
			// normalTriangulo[vertex].print();
			// }

			/**
			 * CALCULAR NORMAL DE CADA VERTICE
			 */
			Vetor[] normalVertice = new Vetor[nVertices];
			for (int vertex = 0; vertex < nVertices; vertex++) {
				float[] somaNormal = new float[3];
				somaNormal[0] = somaNormal[1] = somaNormal[2] = 0;
				for (int triangulo = 0; triangulo < kTriangulos; triangulo++) {

					if ((vertex) == indices[triangulo][0] || (vertex) == indices[triangulo][1]
							|| (vertex) == indices[triangulo][2]) {
						somaNormal[0] += normalTriangulo[triangulo].getX();
						somaNormal[1] += normalTriangulo[triangulo].getY();
						somaNormal[2] += normalTriangulo[triangulo].getZ();
					}
				}
				normalVertice[vertex] = Biblioteca.normalizacao(new Vetor(somaNormal[0], somaNormal[1], somaNormal[2]));
			}

			// System.out.println("VERTICE");
			// for (int vertex = 0; vertex < nVertices; vertex++) {
			// normalVertice[vertex].print();
			// }

			/**
			 * PREPARAR Z-BUFFER
			 */

			float[][] zBuffer = new float[H][W];

			for (int i = 0; i < zBuffer.length; i++) {
				for (int j = 0; j < zBuffer[i].length; j++)
					zBuffer[i][j] = Float.MAX_VALUE;
			}

			/**
			 * RASTERIZAR CADA TRIANGULO USANDO SCANLINE
			 */
			Color[][] color = new Color[H][W];
			for (int i = 0; i < H; i++)
				for (int j = 0; j < W; j++)
					color[i][j] = Color.BLACK;

			char[][] tela2 = new char[H][W];
			Scanline.scanline(kTriangulos, coordTela, indices, tela2, W, H, cv, normalTriangulo, normalVertice, zBuffer,
					pontos, color);

			JFrame frame2 = new JFrame("Rasterização com Scanline");
			frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame2.setSize(H + 10, W + 10);
			frame2.add(new Pixels(H, W, tela2, color));
			frame2.setLocationRelativeTo(null);
			frame2.setVisible(true);

			while (key != 1) {
				System.out.println("Para recarregar o arquivo digite 1: ");
				key = arquivo.nextInt();
				if (key == 1) {
					cv.load();
				}
			}
		}
	}
}
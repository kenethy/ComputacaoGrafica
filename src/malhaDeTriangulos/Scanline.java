package malhaDeTriangulos;

import java.awt.Color;
import java.io.IOException;

import miniBiblioteca.Biblioteca;
import miniBiblioteca.Matriz;
import miniBiblioteca.Ponto;
import miniBiblioteca.Vetor;

public class Scanline {

	public static void scanline(int kTriangulos, Matriz coordTela, int[][] index, char[][] tela, int resolucaoX,
			int resolucaoY, CameraVirtual cv, Vetor[] normalTriangulo, Vetor[] normalVertice, float[][] zBuffer,
			Matriz pontos, Color[][] c) throws IOException {

		float aMinimo, aMaximo;
		float xMaximo, xMinimo;

		for (int i = 0; i < kTriangulos; i++) {

			/**
			 * VERIFICAÇÃO DOS MAXIMOS E MINIMOS Y
			 */
			float[] vertexMaximo = coordTela.getMatriz()[index[i][0]];
			float[] vertexMedio = coordTela.getMatriz()[index[i][1]];
			float[] vertexMinimo = coordTela.getMatriz()[index[i][2]];

			Ponto A = new Ponto(vertexMaximo[0], vertexMaximo[1], cv.getD(), true);
			Ponto B = new Ponto(vertexMedio[0], vertexMedio[1], cv.getD(), true);
			Ponto C = new Ponto(vertexMinimo[0], vertexMinimo[1], cv.getD(), true);

			/**
			 * COMPARAÇÃO DE VERTICES PARA SABER QUAL É O MAIOR, MENOR E MÉDIO
			 */

			if (vertexMaximo[1] < vertexMedio[1]) {
				float[] aux = vertexMaximo;
				vertexMaximo = vertexMedio;
				vertexMedio = aux;
			}

			if (vertexMaximo[1] < vertexMinimo[1]) {
				float[] aux = vertexMaximo;
				vertexMaximo = vertexMinimo;
				vertexMinimo = aux;
			}

			if (vertexMedio[1] < vertexMinimo[1]) {
				float[] aux = vertexMedio;
				vertexMedio = vertexMinimo;
				vertexMinimo = aux;
			}

			/**
			 * CALCULO DO MEIO DO TRIANGULO AO DIVIDI-LO EM DOIS
			 */
			float[] vertexAux = new float[2];
			vertexAux[0] = vertexMaximo[0] + ((vertexMedio[1] - vertexMaximo[1]) / (vertexMinimo[1] - vertexMaximo[1]))
					* (vertexMinimo[0] - vertexMaximo[0]);
			vertexAux[1] = vertexMedio[1];

			/**
			 * CALCULO DA CONSTANTE A DE INCREMENTO DO ANGULO DO TRIANGULO
			 */
			if (vertexMedio[0] >= vertexAux[0]) {
				aMaximo = (vertexMedio[1] - vertexMaximo[1]) / (vertexMedio[0] - vertexMaximo[0]);
				aMinimo = (vertexAux[1] - vertexMaximo[1]) / (vertexAux[0] - vertexMaximo[0]);
			} else {
				aMinimo = (vertexMedio[1] - vertexMaximo[1]) / (vertexMedio[0] - vertexMaximo[0]);
				aMaximo = (vertexAux[1] - vertexMaximo[1]) / (vertexAux[0] - vertexMaximo[0]);
			}

			/**
			 * INICIALIZAÇÃO DAS VARIAVEIS X_MINIMO E X_MAXIMO
			 */
			xMinimo = vertexMaximo[0];
			xMaximo = xMinimo;
			int yMaximo = (int) (vertexMedio[1] + 0.5);

			/**
			 * RASTERIZAÇÃO DO PRIMEIRO TRIANGULO
			 */
			for (int y = (int) (vertexMaximo[1] + 0.5); y >= yMaximo; y--) {

				int xLeft = (int) (xMinimo + 0.5);
				int xRight = (int) (xMaximo + 0.5);

				while (xLeft <= xRight) {
					if (xLeft >= 0 && xLeft < resolucaoX && y >= 0 && y < resolucaoY) {
						/**
						 * ENCONTRA P ORIGINAL
						 */
						Ponto p = new Ponto(xLeft, y, cv.getD(), true);
						Ponto coordBaricentricas = Biblioteca.coordBaricentrica(p, A, B, C);
						Vetor P = pOriginal(coordBaricentricas, index, i, pontos);

						if (P.getZ() < zBuffer[y][xLeft]) {
							zBuffer[y][xLeft] = P.getZ();
							int yColor = y;
							int xColor = xLeft;
							color(cv, coordBaricentricas, pontos, index, i, normalVertice, c, yColor, xColor, P);
							tela[y][xLeft] = 'B';
						}
					}
					xLeft++;
				}

				xMinimo -= (1.0 / aMinimo);
				xMaximo -= (1.0 / aMaximo);
			}

			/**
			 * CALCULO DA CONSTANTE A DE INCREMENTO DO ANGULO DO TRIANGULO
			 */
			if (vertexMedio[0] >= vertexAux[0]) {
				aMaximo = (vertexMedio[1] - vertexMinimo[1]) / (vertexMedio[0] - vertexMinimo[0]);
				aMinimo = (vertexAux[1] - vertexMinimo[1]) / (vertexAux[0] - vertexMinimo[0]);
			} else {
				aMinimo = (vertexMedio[1] - vertexMinimo[1]) / (vertexMedio[0] - vertexMinimo[0]);
				aMaximo = (vertexAux[1] - vertexMinimo[1]) / (vertexAux[0] - vertexMinimo[0]);
			}

			/**
			 * INICIALIZAÇÃO DAS VARIAVEIS X_MINIMO E X_MAXIMO
			 */
			xMaximo = xMinimo = vertexMinimo[0];

			/**
			 * RASTERIZAÇÃO DOS TRIANGULOS DE CIMA PARA BAIXO
			 */
			for (int y = (int) (vertexMinimo[1] + 0.5); y < yMaximo; y++) {

				int xLeft = (int) (xMinimo + 0.5);
				int xRight = (int) (xMaximo + 0.5);

				while (xLeft <= xRight) {
					if (xLeft >= 0 && xLeft < resolucaoX && y >= 0 && y < resolucaoY) {
						/**
						 * ENCONTRA P ORIGINAL
						 */
						Ponto p = new Ponto(xLeft, y, cv.getD(), true);
						Ponto coordBaricentricas = Biblioteca.coordBaricentrica(p, A, B, C);
						Vetor P = pOriginal(coordBaricentricas, index, i, pontos);

						if (P.getZ() < zBuffer[y][xLeft]) {
							zBuffer[y][xLeft] = P.getZ();
							int yColor = y;
							int xColor = xLeft;
							color(cv, coordBaricentricas, pontos, index, i, normalVertice, c, yColor, xColor, P);
							tela[y][xLeft] = 'B';
						}
					}
					xLeft++;
				}
				xMinimo += (1.0 / aMinimo);
				xMaximo += (1.0 / aMaximo);
			}
		}
	}

	public static Vetor pOriginal(Ponto coordBaricentricas, int[][] index, int i, Matriz pontos) {
		float P1x = coordBaricentricas.getX() * pontos.getMatriz()[index[i][0]][0];
		float P1y = coordBaricentricas.getX() * pontos.getMatriz()[index[i][0]][1];
		float P1z = coordBaricentricas.getX() * pontos.getMatriz()[index[i][0]][2];

		float P2x = coordBaricentricas.getY() * pontos.getMatriz()[index[i][1]][0];
		float P2y = coordBaricentricas.getY() * pontos.getMatriz()[index[i][1]][1];
		float P2z = coordBaricentricas.getY() * pontos.getMatriz()[index[i][1]][2];

		float P3x = coordBaricentricas.getZ() * pontos.getMatriz()[index[i][2]][0];
		float P3y = coordBaricentricas.getZ() * pontos.getMatriz()[index[i][2]][1];
		float P3z = coordBaricentricas.getZ() * pontos.getMatriz()[index[i][2]][2];

		P1x = P1x + P2x + P3x;
		P1y = P1y + P2y + P3y;
		P1z = P1z + P2z + P3z;

		Vetor P = new Vetor(P1x, P1y, P1z);

		return P;
	}

	public static void color(CameraVirtual cv, Ponto coordBaricentricas, Matriz pontos, int[][] index, int i,
			Vetor[] normalVertice, Color[][] color, int y, int x, Vetor P) throws IOException {

		/**
		 * ENCONTRA N NORMAL DE P, NORMALIZA N
		 */
		float N1x = coordBaricentricas.getX() * normalVertice[index[i][0]].getX();
		float N1y = coordBaricentricas.getX() * normalVertice[index[i][0]].getY();
		float N1z = coordBaricentricas.getX() * normalVertice[index[i][0]].getZ();

		float N2x = coordBaricentricas.getY() * normalVertice[index[i][1]].getX();
		float N2y = coordBaricentricas.getY() * normalVertice[index[i][1]].getY();
		float N2z = coordBaricentricas.getY() * normalVertice[index[i][1]].getZ();

		float N3x = coordBaricentricas.getZ() * normalVertice[index[i][2]].getX();
		float N3y = coordBaricentricas.getZ() * normalVertice[index[i][2]].getY();
		float N3z = coordBaricentricas.getZ() * normalVertice[index[i][2]].getZ();

		N1x = N1x + N2x + N3x;
		N1y = N1y + N2y + N3y;
		N1z = N1z + N2z + N3z;

		Vetor N = new Vetor(N1x, N1y, N1z);
		N = Biblioteca.normalizacao(N);

		/**
		 * ENCONTRA V, NORMALIZA V
		 */
		Vetor V = new Vetor(-P.getX(), -P.getY(), -P.getZ());
		V = Biblioteca.normalizacao(V);

		/**
		 * ENCONTRA L = Pl - P, NORMALIZA L
		 */
		Vetor L = new Vetor(cv.getPl().getX() - P.getX(), cv.getPl().getY() - P.getY(), cv.getPl().getZ() - P.getZ());
		L = Biblioteca.normalizacao(L);

		/**
		 * ENCONTRA R (JÁ ESTÁ NORMALIZADO) R = 2 (N, L) * N - L
		 */
		float prodEscalarNL = Biblioteca.prodEscalar(N, L);
		float prodEscalarVN = Biblioteca.prodEscalar(V, N);

		boolean isId = true;
		boolean isIs = true;

		if (prodEscalarNL < 0) {
			if (prodEscalarVN < 0) {
				N.setX(-N.getX());
				N.setY(-N.getY());
				N.setZ(-N.getZ());
			} else {
				isId = false;
				isIs = false;
			}
		}

		prodEscalarNL = Biblioteca.prodEscalar(N, L);
		Vetor R = new Vetor(2 * prodEscalarNL * N.getX() - L.getX(), 2 * prodEscalarNL * N.getY() - L.getY(),
				2 * prodEscalarNL * N.getZ() - L.getZ());

		/**
		 * VERIFICAR CASOS ESPECIAIS
		 */
		float prodEscalarRV = Biblioteca.prodEscalar(R, V);
		if (prodEscalarRV < 0) {
			isIs = false;
		}

		/**
		 * CALCULO DA COR
		 */
		/**
		 * Ia = Ka * Iamb
		 */
		int[] Ia = new int[3];
		Ia[0] = (int) (cv.getKa() * cv.getIamb()[0]);
		Ia[1] = (int) (cv.getKa() * cv.getIamb()[1]);
		Ia[2] = (int) (cv.getKa() * cv.getIamb()[2]);

		/**
		 * Id = (N, L) * Kd * Od * Il
		 */
		int[] Id = new int[3];
		if (isId) {
			Id[0] = (int) (prodEscalarNL * cv.getKd().getX() * cv.getOd().getX() * cv.getIl()[0]);
			Id[1] = (int) (prodEscalarNL * cv.getKd().getY() * cv.getOd().getY() * cv.getIl()[1]);
			Id[2] = (int) (prodEscalarNL * cv.getKd().getZ() * cv.getOd().getZ() * cv.getIl()[2]);
		}

		/**
		 * Is = (R, V) ^ n * Ks * Il
		 */
		int[] Is = new int[3];
		if (isIs) {
			Is[0] = (int) (Math.pow(prodEscalarRV, cv.getn()) * cv.getKs() * cv.getIl()[0]);
			Is[1] = (int) (Math.pow(prodEscalarRV, cv.getn()) * cv.getKs() * cv.getIl()[1]);
			Is[2] = (int) (Math.pow(prodEscalarRV, cv.getn()) * cv.getKs() * cv.getIl()[2]);
		}

		int[] I = new int[3];
		I[0] = Ia[0] + Id[0] + Is[0];
		if (I[0] > 255)
			I[0] = 255;
		I[1] = Ia[1] + Id[1] + Is[1];
		if (I[1] > 255)
			I[1] = 255;
		I[2] = Ia[2] + Id[2] + Is[2];
		if (I[2] > 255)
			I[2] = 255;

		color[y][x] = new Color(I[0], I[1], I[2]);
	}
}

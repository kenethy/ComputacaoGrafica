package malhaDeTriangulos;

import java.awt.Color;

import miniBiblioteca.Biblioteca;
import miniBiblioteca.Matriz;
import miniBiblioteca.Ponto;
import miniBiblioteca.Vetor;

public class Scanline {

	public static void scanline(int kTriangulos, Matriz coordTela, int[][] index, char[][] tela, int resolucaoX,
			int resolucaoY, CameraVirtual cv, Vetor[] normalTriangulo, Vetor[] normalVertice, Ponto[] zBuffer,
			Matriz pontos) {

		float aMinimo, aMaximo;
		float xMaximo;
		float xMinimo;
		Color[][] c = new Color[resolucaoY][resolucaoX];

		for (int i = 0; i < kTriangulos; i++) {

			/**
			 * VERIFICAÇÃO DOS MAXIMOS E MINIMOS Y
			 */
			float[] vertexMaximo = coordTela.getMatriz()[index[i][0] - 1];
			float[] vertexMedio = coordTela.getMatriz()[index[i][1] - 1];
			float[] vertexMinimo = coordTela.getMatriz()[index[i][2] - 1];

			Ponto A = new Ponto(vertexMaximo[0], vertexMaximo[1], 0, false);
			Ponto B = new Ponto(vertexMedio[0], vertexMedio[1], 0, false);
			Ponto C = new Ponto(vertexMinimo[0], vertexMinimo[1], 0, false);

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

			System.out.println(vertexAux[0] + " " + vertexAux[1]);

			/**
			 * CALCULO DA CONSTANTE A DE INCREMENTO DO ANGULO DO TRIANGULO
			 */
			if (vertexMedio[0] > vertexAux[0]) {
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
			int yMaximo = (int) ((int) vertexMedio[1] + 0.5);

			/**
			 * RASTERIZAÇÃO DO PRIMEIRO TRIANGULO
			 */
			for (int y = (int) ((int) vertexMaximo[1] + 0.5); y >= yMaximo; y--) {

				int xLeft = (int) (xMinimo + 0.5);
				int xRight = (int) (xMaximo + 0.5);

				while (xLeft <= xRight) {
					if (xLeft < 0 || xLeft >= resolucaoX || xRight < 0 || xRight >= resolucaoX || y < 0
							|| y >= resolucaoX || y >= resolucaoY)
						break;

					/**
					 * ENCONTRA P ORIGINAL
					 */
					Ponto p = new Ponto(xLeft, y, 0, false);
					Ponto coordBaricentricas = Biblioteca.coordBaricentrica(p, A, B, C);

					float P1x = pontos.getMatriz()[index[i][0] - 1][0] * coordBaricentricas.getX();
					float P2x = pontos.getMatriz()[index[i][1] - 1][0] * coordBaricentricas.getX();
					float P3x = pontos.getMatriz()[index[i][2] - 1][0] * coordBaricentricas.getX();

					float P1y = pontos.getMatriz()[index[i][0] - 1][1] * coordBaricentricas.getY();
					float P2y = pontos.getMatriz()[index[i][1] - 1][1] * coordBaricentricas.getY();
					float P3y = pontos.getMatriz()[index[i][2] - 1][1] * coordBaricentricas.getY();

					float P1z = pontos.getMatriz()[index[i][0] - 1][2] * coordBaricentricas.getZ();
					float P2z = pontos.getMatriz()[index[i][1] - 1][2] * coordBaricentricas.getZ();
					float P3z = pontos.getMatriz()[index[i][2] - 1][2] * coordBaricentricas.getZ();

					Vetor P = new Vetor(P1x + P2x + P3x, P1y + P2y + P3y, P1z + P2z + P3z);

					/**
					 * ENCONTRA N NORMAL DE P, NORMALIZA N
					 */
					float N1x = coordBaricentricas.getX() * normalVertice[index[i][0] - 1].getX();
					float N2x = coordBaricentricas.getX() * normalVertice[index[i][0] - 1].getX();
					float N3x = coordBaricentricas.getX() * normalVertice[index[i][0] - 1].getX();

					float N1y = coordBaricentricas.getX() * normalVertice[index[i][0] - 1].getX();
					float N2y = coordBaricentricas.getX() * normalVertice[index[i][0] - 1].getX();
					float N3y = coordBaricentricas.getX() * normalVertice[index[i][0] - 1].getX();

					float N1z = coordBaricentricas.getX() * normalVertice[index[i][0] - 1].getX();
					float N2z = coordBaricentricas.getX() * normalVertice[index[i][0] - 1].getX();
					float N3z = coordBaricentricas.getX() * normalVertice[index[i][0] - 1].getX();

					Vetor N = new Vetor(N1x + N2x + N3x, N1y + N2y + N3y, N1z + N2z + N3z);
					N = Biblioteca.normalizacao(N);

					/**
					 * ENCONTRA V, NORMALIZA V
					 */
					Vetor V = new Vetor(-P.getX(), -P.getY(), -P.getZ());
					V = Biblioteca.normalizacao(V);

					/**
					 * ENCONTRA L = Pl - P, NORMALIZA L
					 */
					Vetor L = new Vetor(cv.getPl().getX() - P.getX(), cv.getPl().getY() - P.getY(),
							cv.getPl().getZ() - P.getZ());
					L = Biblioteca.normalizacao(L);

					/**
					 * ENCONTRA R (JÁ ESTÁ NORMALIZADO) R = 2 (N, L) * N - L
					 */
					float prodEscalarNL = Biblioteca.prodEscalar(N, L);
					Vetor R = new Vetor(2 * prodEscalarNL * cv.getN().getX() - L.getX(),
							2 * prodEscalarNL * cv.getN().getY() - L.getY(),
							2 * prodEscalarNL * cv.getN().getZ() - L.getZ());

					/**
					 * VERIFICAR CASOS ESPECIAIS
					 */

					/**
					 * CALCULO DA COR
					 */

					tela[(int) (y + 0.5)][(int) (xLeft + 0.5)] = 'B';
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
			;

			/**
			 * RASTERIZAÇÃO DOS TRIANGULOS DE CIMA PARA BAIXO
			 */
			for (int y = (int) ((int) vertexMinimo[1] + 0.5); y < yMaximo; y++) {

				int xLeft = (int) (xMinimo + 0.5);
				int xRight = (int) (xMaximo + 0.5);

				while (xLeft <= xRight) {
					if (xLeft < 0 || xLeft >= resolucaoX || xRight < 0 || xRight >= resolucaoX || y < 0
							|| y >= resolucaoX || y >= resolucaoY)
						break;

					/**
					 * CALCULO DA COR
					 */
					tela[(int) (y + 0.5)][(int) (xLeft + 0.5)] = 'B';
					xLeft++;
				}

				xMinimo += (1.0 / aMinimo);
				xMaximo += (1.0 / aMaximo);
			}
		}
	}

	public static Color color(CameraVirtual cv) {

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
		Vetor L = new Vetor(cv.getPl().getX(), cv.getPl().getY(), cv.getPl().getZ());
		float prodEscalarNL = Biblioteca.prodEscalar(cv.getN(), L);
		Id[0] = (int) (prodEscalarNL * cv.getKd().getX() * cv.getOd().getX() * cv.getIl()[0]);
		Id[1] = (int) (prodEscalarNL * cv.getKd().getY() * cv.getOd().getY() * cv.getIl()[1]);
		Id[2] = (int) (prodEscalarNL * cv.getKd().getZ() * cv.getOd().getZ() * cv.getIl()[2]);

		/**
		 * CALCULAR R = 2 (N, L) * N - L
		 */
		Vetor R = new Vetor(2 * prodEscalarNL * cv.getN().getX() - L.getX(),
				2 * prodEscalarNL * cv.getN().getY() - L.getY(), 2 * prodEscalarNL * cv.getN().getZ() - L.getZ());

		/**
		 * Is = (R, V) ^ n * Ks * Il >>>>>>>>>>>>>>>>>> V (valor -P)
		 */
		int[] Is = new int[3];
		// Vetor V = new Vetor(x, y, z);
		float prodEscalarRV = Biblioteca.prodEscalar(R, new Vetor(0, 0, 0));
		Is[0] = (int) (float) (Math.pow(prodEscalarRV, cv.getn()) * cv.getKs() * cv.getIl()[0]);
		Is[1] = (int) (float) (Math.pow(prodEscalarRV, cv.getn()) * cv.getKs() * cv.getIl()[1]);
		Is[2] = (int) (float) (Math.pow(prodEscalarRV, cv.getn()) * cv.getKs() * cv.getIl()[2]);

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

		return (new Color(I[0], I[1], I[2]));
	}
}

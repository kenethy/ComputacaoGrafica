package malhaDeTriangulos;

import miniBiblioteca.Biblioteca;
import miniBiblioteca.Matriz;
import miniBiblioteca.Vetor;

public class Scanline {

	public static void scanline(int kTriangulos, Matriz coordTela, int[][] index, char[][] frame, int resolucaoX,
			int resolucaoY, CameraVirtual cv) {

		float aMinimo, aMaximo;
		float xMaximo;
		float xMinimo;
		float xMedio;

		for (int i = 0; i < kTriangulos; i++) {
			
			/**
			 * CALCULOS PARA TONALIZA플O E ILUMINA플O DE PHONG
			 */
			
			/**
			 * CALCULAR NORMAL DE CADA TRIANGULO
			 */

			/**
			 * CALCULAR NORMAL DE CADA VERTICE
			 */

			/**
			 * PREPARAR Z-BUFFER
			 */

			/**
			 * SCANLINE
			 */
			
			/**
			 * TONALIZA플O E ILUMINA플O DE PHONG
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
			 * Is = (R, V) ^ n * Ks * Il >>>>>>>>>>>>>>>>>> V???
			 */
			int[] Is = new int[3];
			//Vetor V = new Vetor(x, y, z);
			float prodEscalarRV = Biblioteca.prodEscalar(R, new Vetor(0,0,0));
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

			/**
			 * VERIFICA플O DOS MAXIMOS E MINIMOS Y
			 */

			float[] vertexMaximo = coordTela.getMatriz()[index[i][0] - 1];
			float[] vertexMedio = coordTela.getMatriz()[index[i][1] - 1];
			float[] vertexMinimo = coordTela.getMatriz()[index[i][2] - 1];

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
			 * 
			 */
			if (vertexMedio[0] > vertexMinimo[0]) {
				aMaximo = (vertexMedio[1] - vertexMaximo[1]) / (vertexMedio[0] - vertexMaximo[0]);
				aMinimo = (vertexMinimo[1] - vertexMaximo[1]) / (vertexMinimo[0] - vertexMaximo[0]);
			} else {
				aMinimo = (vertexMedio[1] - vertexMaximo[1]) / (vertexMedio[0] - vertexMaximo[0]);
				aMaximo = (vertexMinimo[1] - vertexMaximo[1]) / (vertexMinimo[0] - vertexMaximo[0]);
			}

			/**
			 * 
			 */
			xMinimo = vertexMaximo[0];
			xMaximo = xMinimo;
			xMedio = xMinimo;

			/**
			 * 
			 */
			for (int y = (int) vertexMaximo[1]; y >= vertexMedio[1]; y--) {
				if (xMinimo < 0 || xMinimo >= resolucaoX || xMaximo < 0 || xMaximo >= resolucaoX || y < 0
						|| y >= resolucaoX || y >= resolucaoY)
					break;

				while (xMedio <= xMaximo) {
					frame[(int) (y + 0.5)][(int) (xMedio + 0.5)] = 'B';
					xMedio++;
				}

				xMinimo -= (1.0 / aMinimo);
				xMaximo -= (1.0 / aMaximo);
				xMedio = xMinimo;
			}

			/**
			 * 
			 */
			if (vertexMedio[0] >= vertexMinimo[0]) {
				aMaximo = (vertexMedio[1] - vertexMinimo[1]) / (vertexMedio[0] - vertexMinimo[0]);
				aMinimo = (vertexMaximo[1] - vertexMinimo[1]) / (vertexMaximo[0] - vertexMinimo[0]);
			} else {
				aMinimo = (vertexMedio[1] - vertexMinimo[1]) / (vertexMedio[0] - vertexMinimo[0]);
				aMaximo = (vertexMaximo[1] - vertexMinimo[1]) / (vertexMaximo[0] - vertexMinimo[0]);
			}

			/**
			 * 
			 */
			xMinimo = vertexMinimo[0];
			xMaximo = xMinimo;
			xMedio = xMinimo;

			/**
			 * RASTERIZA플O DOS TRINAGULOS DE CIMA PARA BAIXO
			 */
			for (int y = (int) vertexMinimo[1]; y < vertexMedio[1]; y++) {
				if (xMinimo < 0 || xMinimo >= resolucaoX || xMaximo < 0 || xMaximo >= resolucaoX || y < 0
						|| y >= resolucaoX || y >= resolucaoY)
					break;
				
				while (xMedio <= xMaximo) {
					frame[(int) (y + 0.5)][(int) (xMedio + 0.5)] = 'B';
					xMedio++;
				}

				xMinimo += (1.0 / aMinimo);
				xMaximo += (1.0 / aMaximo);
				xMedio = xMinimo;
			}
		}
	}
}

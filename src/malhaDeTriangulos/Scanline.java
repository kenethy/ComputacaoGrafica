package malhaDeTriangulos;

import miniBiblioteca.Matriz;

public class Scanline {

	public static void scanline(int kTriangulos, Matriz coordTela, int[][] index, char[][] frame, int resolucaoX,
			int resolucaoY) {

		float aMinimo, aMaximo;
		float xMaximo;
		float xMinimo;
		float xMedio;

		for (int i = 0; i < kTriangulos; i++) {

			/**
			 * VERIFICAÇÃO DOS MAXIMOS E MINIMOS Y
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
			if (vertexMedio[0] >= vertexMinimo[0]) {
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
			for (float y = vertexMaximo[1]; y >= vertexMedio[1]; --y) {
				if (xMinimo < 0 || xMinimo >= resolucaoX || xMinimo >= resolucaoY || xMaximo < 0
						|| xMaximo >= resolucaoX || xMaximo >= resolucaoY || y < 0 || y >= resolucaoX
						|| y >= resolucaoY)
					break;

				while (xMedio <= xMaximo) {
					frame[(int) y][(int) xMedio] = 'B';
					++xMedio;
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
			 * 
			 */
			for (float y = vertexMinimo[1]; y <= vertexMedio[1]; ++y) {
				if (xMinimo < 0 || xMinimo >= resolucaoX || xMinimo >= resolucaoY || xMaximo < 0
						|| xMaximo >= resolucaoX || xMaximo >= resolucaoY || y < 0 || y >= resolucaoX
						|| y >= resolucaoY)
					break;

				while (xMedio <= xMaximo) {
					frame[(int) y][(int) xMedio] = 'B';
					++xMedio;
				}

				xMinimo += (1.0 / aMinimo);
				xMaximo += (1.0 / aMaximo);
				xMedio = xMinimo;
			}
		}
	}
}

package malhaDeTriangulos;

import miniBiblioteca.Matriz;

public class Scanline {

	public static void scanline(int kTriangulos, Matriz coordTela, int[][] index, char[][] frame, int resX, int resY) {

		char[][] tela = frame;
		float aMin, aMax;
		float xMinAux = 0;
		float xMaxAux = 0;
		float xMedAux = 0;
		int indiceYMin = 0;
		int indiceYMax = 0;

		for (int i = 0; i < kTriangulos; i++) {
			float yMax = Float.MIN_VALUE;
			float yMin = Float.MAX_VALUE;
			float xMax = Float.MIN_VALUE;
			float xMin = Float.MAX_VALUE;
			float yMed = 0;
			float xMed = 0;

			/**
			 * VERIFICAÇÃO DOS MAXIMOS E MINIMOS Y
			 */
			for (int j = 0; j < 3; j++) {

				if (yMax < coordTela.getMatriz()[index[i][j] - 1][1]) {
					yMax = coordTela.getMatriz()[index[i][j] - 1][1];
					indiceYMax = index[i][j];
				}

				else if (yMin > coordTela.getMatriz()[index[i][j] - 1][1]) {
					yMin = coordTela.getMatriz()[index[i][j] - 1][1];
					indiceYMin = index[i][j];
				}

				if (xMax < coordTela.getMatriz()[index[i][j] - 1][0])
					xMax = coordTela.getMatriz()[index[i][j] - 1][0];

				else if (xMin > coordTela.getMatriz()[index[i][j] - 1][0])
					xMin = coordTela.getMatriz()[index[i][j] - 1][0];
			}

			for (int j = 0; j < 3; j++) {
				if (indiceYMin != index[i][j] - 1 && indiceYMax != index[i][j] - 1) {
					yMed = coordTela.getMatriz()[index[i][j] - 1][1];
					xMed = coordTela.getMatriz()[index[i][j] - 1][0];
				}
			}

			if (xMed >= xMin) {
				aMax = (yMed - yMax) / (xMed - xMax);
				aMin = (yMin - yMax) / (xMin - xMax);
			} else {
				aMin = (yMed - yMax) / (xMed - xMax);
				aMax = (yMin - yMax) / (xMin - xMax);
			}

			xMinAux = xMax;
			xMaxAux = xMax;

			for (float yScan = yMax; yScan >= yMed; yScan--) {
				for (xMedAux = xMinAux; xMedAux <= xMaxAux; xMedAux++) {
					tela[(int) yScan][(int) xMedAux] = 'B';
				}

				xMinAux += (1 / aMin);
				xMaxAux += (1 / aMax);
			}

			if (xMed > xMin) {
				aMax = (yMed - yMin) / (xMed - xMin);
				aMin = (yMax - yMin) / (xMax - xMin);
			} else {
				aMin = (yMed - yMin) / (xMed - xMin);
				aMax = (yMax - yMin) / (xMax - xMin);
			}

			xMinAux = xMin;
			xMaxAux = xMin;

			for (float j = yMin; j < yMed; j++) {
				for (xMedAux = xMinAux; xMedAux <= xMax; xMedAux++) {
					tela[(int) j][(int) xMedAux] = 'B';
				}

				xMinAux += (1 / aMin);
				xMaxAux += (1 / aMax);
			}

		}
	}
}

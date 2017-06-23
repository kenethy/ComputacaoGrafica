package malhaDeTriangulos;

import miniBiblioteca.Matriz;

public class Scanline {

	public static char[][] scanline(int kTriangulos, Matriz coordTela, int[][] index, char[][] frame, int resX,
			int resY) {

		char[][] tela = frame;
		float aMin, aMax;
		float xMinAux = 0;
		float xMaxAux = 0;
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

				if (yMin > coordTela.getMatriz()[index[i][j] - 1][1]) {
					yMin = coordTela.getMatriz()[index[i][j] - 1][1];
					indiceYMin = index[i][j];
				}

				if (xMax < coordTela.getMatriz()[index[i][j] - 1][0])
					xMax = coordTela.getMatriz()[index[i][j] - 1][0];

				if (xMin > coordTela.getMatriz()[index[i][j] - 1][0])
					xMin = coordTela.getMatriz()[index[i][j] - 1][0];
			}

			for (int j = 0; j < 3; j++) {
				if (indiceYMin != index[i][j] - 1 && indiceYMax != index[i][j] - 1) {
					yMed = coordTela.getMatriz()[index[i][j] - 1][1];
					xMed = coordTela.getMatriz()[index[i][j] - 1][0];
				}
			}

//			System.out.println(yMax + " " + yMin + " " + yMed);
//			System.out.println(xMax + " " + xMin + " " + xMed);
//			System.out.println();

			aMin = (yMed - yMax) / (xMed - xMax);
			aMax = (yMin - yMax) / (xMin - xMax);
			xMinAux = xMin;
			xMaxAux = xMax;

//			System.out.println(yMax + " " + yMin + " " + yMed);
//			System.out.println(xMax + " " + xMin + " " + xMed);
//			System.out.println();
	
			/*
			for (int j = (int) yMax; j > yMed; j--) {
				if (j > resX)
					j = resX - 1;

				for (int k = (int) xMin; k < xMaxAux && k < resY; k++) {
					tela[j][k] = 'B';
					xMinAux = 1 / aMin + xMinAux;
				}
				xMaxAux = 1 / aMax + xMaxAux;
			}

			xMinAux = xMin;
			xMaxAux = xMax;

			for (int j = (int) yMin; j < yMed && j < resX; j++) {
				for (int k = (int) xMin; k < xMaxAux && k < resY; k++) {
					tela[j][k] = 'B';
					xMinAux = 1 / aMin + xMinAux;
				}
				xMaxAux = 1 / aMax + xMaxAux;
			}
			*/
		}
		return tela;
	}
}

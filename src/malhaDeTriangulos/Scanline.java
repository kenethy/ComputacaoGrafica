package malhaDeTriangulos;

import miniBiblioteca.Matriz;

public class Scanline {

	public static void scanline(int kTriangulos, Matriz coordTela, int[][] index, char[][] frame, int resX, int resY) {

		float aMin, aMax;
		float xMax;
		float xMin;
		float xMed;

		for (int i = 0; i < kTriangulos; i++) {
			
			/**
			 * VERIFICAÇÃO DOS MAXIMOS E MINIMOS Y
			 */

			float[] vertexMax = coordTela.getMatriz()[index[i][0] - 1];
			float[] vertexMed = coordTela.getMatriz()[index[i][1] - 1];
			float[] vertexMin = coordTela.getMatriz()[index[i][2] - 1];

			if (vertexMax[1] < vertexMed[1]) {
				float[] aux = vertexMax;
				vertexMax = vertexMed;
				vertexMed = aux;
			}

			if (vertexMax[1] < vertexMin[1]) {
				float[] aux = vertexMax;
				vertexMax = vertexMin;
				vertexMin = aux;
			}

			if (vertexMed[1] < vertexMin[1]) {
				float[] aux = vertexMed;
				vertexMed = vertexMin;
				vertexMin = aux;
			}

			if (vertexMed[0] >= vertexMin[0]) {
				aMax = (vertexMed[1] - vertexMax[1]) / (vertexMed[0] - vertexMax[0]);
				aMin = (vertexMin[1] - vertexMax[1]) / (vertexMin[0] - vertexMax[0]);
			} else {
				aMin = (vertexMed[1] - vertexMax[1]) / (vertexMed[0] - vertexMax[0]);
				aMax = (vertexMin[1] - vertexMax[1]) / (vertexMin[0] - vertexMax[0]);
			}

			xMin = vertexMax[0];
			xMax = xMin;
			xMed = xMin;

			for (float yScan = vertexMax[1]; yScan >= vertexMed[1]; --yScan) {
				if (xMin < 0 || xMin >= resX || xMin >= resY || xMax < 0 || xMax >= resX || xMax >= resY || yScan < 0
						|| yScan >= resX || yScan >= resY)
					break;

				while (xMed <= xMax) {
					frame[(int) yScan][(int) xMed] = 'B';
					++xMed;
				}

				xMin -= (1.0 / aMin);
				xMax -= (1.0 / aMax);
				xMed = xMin;
			}

			if (vertexMed[0] >= vertexMin[0]) {
				aMax = (vertexMed[1] - vertexMin[1]) / (vertexMed[0] - vertexMin[0]);
				aMin = (vertexMax[1] - vertexMin[1]) / (vertexMax[0] - vertexMin[0]);
			} else {
				aMin = (vertexMed[1] - vertexMin[1]) / (vertexMed[0] - vertexMin[0]);
				aMax = (vertexMax[1] - vertexMin[1]) / (vertexMax[0] - vertexMin[0]);
			}

			xMin = vertexMin[0];
			xMax = xMin;
			xMed = xMin;

			for (float yScan = vertexMin[1]; yScan <= vertexMed[1]; ++yScan) {
				if (xMin < 0 || xMin >= resX || xMin >= resY || xMax < 0 || xMax >= resX || xMax >= resY || yScan < 0
						|| yScan >= resX || yScan >= resY)
					break;

				while (xMed <= xMax) {
					frame[(int) yScan][(int) xMed] = 'B';
					++xMed;
				}

				xMin += (1.0 / aMin);
				xMax += (1.0 / aMax);
				xMed = xMin;
			}
		}
	}
}

package miniBiblioteca;

/**
 * MINI BIBLIOTECA DE FUNÇÕES MATEMÁTICAS
 * 
 * @author KENEDY FELIPE
 */

public class Biblioteca {

	/**
	 * MULTIPLICAÇÃO DE MATRIZES
	 */
	public static Matriz multMatriz(Matriz A, Matriz B) {
		Matriz C = null;

		// VERIFICAÇÃO SE LINHA E COLUNA DAS MATRIZES POSSUEM O MESMO VALOR
		if (A.getColuna() != B.getLinha()) {
			throw new RuntimeException("Dimensões inconsistentes!");
		} else {
			// CRIAÇÃO DA MATRIZ
			C = new Matriz(A.getLinha(), B.getColuna());

			// MULTIPLICAÇÃO DAS MATRIZES
			for (int i = 0; i < A.getLinha(); i++) {
				for (int j = 0; j < B.getColuna(); j++) {
					float soma = 0;
					for (int k = 0; k < A.getColuna(); k++) {
						float prod = A.getMatriz()[i][k] * B.getMatriz()[k][j];
						soma += prod;
					}
					C.getMatriz()[i][j] = soma;
				}
			}
		}
		return C;
	}

	/**
	 * SUBTRAÇÃO DE PONTOS 3D (RETORNA UM VETOR 3D)
	 * COORDENADAS (XB - XA, YB - YA, ZB - ZA)
	 */
	public static Vetor subPontos(Ponto A, Ponto B) {
		Vetor v = new Vetor(B.getX() - A.getX(), B.getY() - A.getY(), B.getZ() - A.getZ());
		return v;
	}

	/**
	 * PRODUTO ESCALAR DE DOIS VETORES 3D
	 */
	public static float prodEscalar(Vetor u, Vetor v) {
		return (u.getX() * v.getX() + u.getY() * v.getY() + u.getZ() * v.getZ());
	}

	/**
	 * PRODUTO VETORIAL DE DOIS VETORES 3D
	 */
	public static Vetor prodVetorial(Vetor u, Vetor v) {
		float x, y, z;

		x = u.getY() * v.getZ() - (u.getZ() * v.getY());
		y = u.getZ() * v.getX() - (u.getX() * v.getZ());
		z = u.getX() * v.getY() - (u.getY() * v.getX());

		Vetor w = new Vetor(x, y, z);

		return w;
	}

	/**
	 * NORMA DE UM VETOR 3D
	 */
	public static float norma(Vetor v) {
		return (float) Math.sqrt(prodEscalar(v, v));
	}

	/**
	 * NORMALIZAÇÃO DE UM VETOR 3D (RETORNA VETOR 3D COM NORMA UNITÁRIA)
	 */
	public static Vetor normalizacao(Vetor v) {
		float x, y, z;
		float n = norma(v);

		x = v.getX() / n;
		y = v.getY() / n;
		z = v.getZ() / n;

		Vetor u = new Vetor(x, y, z);

		return u;
	}

	/**
	 * CALCULA COORDENADA BARICÊNTIRCA DE UM PONTO 2D COM RELAÇÃO A OUTROS TRÊS
	 * PONTOS 2D NÃO-COLINEARES
	 */
	public static Ponto coordBaricentrica(Ponto P, Ponto A, Ponto B, Ponto C) {
		float alpha, beta, gama;
		float a, b, c, d, e, f;

		// CALCULO DAS COORDENADAS DA MATRIZ QUE MULTIPLICA ALPHA E BETA
		a = A.getX() - C.getX();
		b = B.getX() - C.getX();
		c = A.getY() - C.getY();
		d = B.getY() - C.getY();
		e = P.getX() - C.getX();
		f = P.getY() - C.getY();

		// CALCULO DA TRANSPOSTA
		a = 1 / (a * d - b * c) * d;
		b = 1 / (a * d - b * c) * (-b);
		c = 1 / (a * d - b * c) * (-c);
		d = 1 / (a * d - b * c) * a;

		// CALCULO DE ALPHA, BETA E GAMA
		alpha = a * e + b * f;
		beta = c * e + d * f;
		gama = 1 - alpha - beta;

		Ponto coordenadaBaricentrica = new Ponto(alpha, beta, gama, true);

		return coordenadaBaricentrica;
	}

	/**
	 * CALCULA COORDENADA CARTESIANA DE UM PONTO 2D A PARTIR DE SUA COORDENADA
	 * BARICÊNTRICA
	 */
	public static Ponto coordCartesiana(Ponto A, Ponto B, Ponto C, Ponto coord) {
		float x, y;

		x = coord.getX() * A.getX() + coord.getY() * B.getX() + coord.getZ() * C.getX();
		y = coord.getX() * A.getY() + coord.getY() * B.getY() + coord.getZ() * C.getY();

		Ponto P = new Ponto(x, y, 0, false);
		return P;
	}
}

package malhaDeTriangulos;

import java.util.Comparator;

import miniBiblioteca.Ponto;

public class ZBuffer implements Comparator<Ponto> {

	@Override
	public int compare(Ponto p1, Ponto p2) {
		if (p1.getZ() < p2.getZ())
			return -1;
		else if (p1.getZ() > p2.getZ())
			return +1;
		else
			return 0;
	}
}
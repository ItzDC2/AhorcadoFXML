package puntuaciones;

import java.util.Comparator;

public class ComparadorPuntuacion implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		try {
			int i1 = Integer.parseInt(o1);
			int i2 = Integer.parseInt(o2);
			return Integer.compare(i1, i2);
		} catch(NumberFormatException ex) {
			throw new RuntimeException(ex);
		}
	}

}

package puntuaciones;

import java.util.Comparator;

public class ComparadorPuntuacion implements Comparator<String> {

	@Override
	public int compare(String o1, String o2) {
		return extraerInt(o1) - extraerInt(o2);
	}

	private int extraerInt(String s) {
		String num = s.replaceAll("\\D", ""); //Si contiene algún dígito numérico lo reemplaza por nada
		return num.isEmpty() ? 0 : Integer.parseInt(num); //Si la cadena está vacía la descarta y devuelve 0 si no, devuelve el número
	}

}

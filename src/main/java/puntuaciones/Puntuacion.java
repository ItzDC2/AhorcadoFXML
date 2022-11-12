package puntuaciones;

public class Puntuacion implements Comparable<Puntuacion> {

	private String nombre;
	private int puntos;
	
	public Puntuacion(String nombre, int puntos) {
		this.puntos = puntos;
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	@Override
	public String toString() {
		return nombre + ": " + puntos;
	}

	@Override
	public int compareTo(Puntuacion o) {
		int res = 0;
		if(this.puntos > o.puntos)
			res = -1;
		else if(this.puntos < o.puntos)
			res = 1;
		return res;
	}
	
}

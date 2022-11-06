package partida;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PartidaModel {

	private StringProperty palabraSeleccionada = new SimpleStringProperty();
	private IntegerProperty puntuacion = new SimpleIntegerProperty();
	private IntegerProperty errores = new SimpleIntegerProperty();
	private IntegerProperty letrasPalabraSeleccionada = new SimpleIntegerProperty();
	private IntegerProperty letrasAcertadas = new SimpleIntegerProperty();
	
	private ListProperty<Character> letrasIntroducidas = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public StringProperty palabraSeleccionadaProperty() {
		return this.palabraSeleccionada;
	}
	
	public String getPalabraSeleccionada() {
		return this.palabraSeleccionadaProperty().get();
	}

	public void setPalabraSeleccionada(final String palabraSeleccionada) {
		this.palabraSeleccionadaProperty().set(palabraSeleccionada);
	}

	public IntegerProperty puntuacionProperty() {
		return this.puntuacion;
	}

	public int getPuntuacion() {
		return this.puntuacionProperty().get();
	}

	public void setPuntuacion(final int puntuacion) {
		this.puntuacionProperty().set(puntuacion);
	}

	public IntegerProperty erroresProperty() {
		return this.errores;
	}
	

	public int getErrores() {
		return this.erroresProperty().get();
	}
	

	public void setErrores(final int errores) {
		this.erroresProperty().set(errores);
	}

	public IntegerProperty letrasPalabraSeleccionadaProperty() {
		return this.letrasPalabraSeleccionada;
	}
	

	public int getLetrasPalabraSeleccionada() {
		return this.letrasPalabraSeleccionadaProperty().get();
	}
	

	public void setLetrasPalabraSeleccionada(final int letrasPalabraSeleccionada) {
		this.letrasPalabraSeleccionadaProperty().set(letrasPalabraSeleccionada);
	}

	public IntegerProperty letrasAcertadasProperty() {
		return this.letrasAcertadas;
	}
	
	public int getLetrasAcertadas() {
		return this.letrasAcertadasProperty().get();
	}
	
	public void setLetrasAcertadas(final int letrasAcertadas) {
		this.letrasAcertadasProperty().set(letrasAcertadas);
	}

	public ListProperty<Character> letrasIntroducidasProperty() {
		return this.letrasIntroducidas;
	}

	public ObservableList<Character> getLetrasIntroducidas() {
		return this.letrasIntroducidasProperty().get();
	}
	
	public void setLetrasIntroducidas(final ObservableList<Character> letrasIntroducidas) {
		this.letrasIntroducidasProperty().set(letrasIntroducidas);
	}

}

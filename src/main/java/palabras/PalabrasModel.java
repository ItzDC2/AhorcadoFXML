package palabras;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PalabrasModel {

	private ListProperty<String> palabras = new SimpleListProperty<>(FXCollections.observableArrayList());
	private StringProperty palabraSeleccionada = new SimpleStringProperty();
	
	public ListProperty<String> palabrasProperty() {
		return this.palabras;
	}
	
	public ObservableList<String> getPalabras() {
		return this.palabrasProperty().get();
	}
	
	public void setPalabras(final ObservableList<String> palabras) {
		this.palabrasProperty().set(palabras);
	}

	public StringProperty palabraSeleccionadaProperty() {
		return this.palabraSeleccionada;
	}
	

	public String getPalabraSeleccionada() {
		return this.palabraSeleccionadaProperty().get();
	}
	

	public void setPalabraSeleccionada(final String palabraSeleccionada) {
		this.palabraSeleccionadaProperty().set(palabraSeleccionada);
	}
	
}
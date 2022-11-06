package puntuaciones;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PuntuacionesModel {

	private ListProperty<String> puntuaciones = new SimpleListProperty<>(FXCollections.observableArrayList());

	public ListProperty<String> puntuacionesProperty() {
		return this.puntuaciones;
	}

	public ObservableList<String> getPuntuaciones() {
		return this.puntuacionesProperty().get();
	}
	
	public void setPuntuaciones(final ObservableList<String> puntuaciones) {
		this.puntuacionesProperty().set(puntuaciones);
	}
	
	
	
	
}

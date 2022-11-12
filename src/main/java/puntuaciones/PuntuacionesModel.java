package puntuaciones;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

public class PuntuacionesModel {

	private ListProperty<Puntuacion> puntuaciones = new SimpleListProperty<>(FXCollections.observableArrayList());
	
	public PuntuacionesModel() {
		puntuaciones.addListener(new ListChangeListener<Puntuacion>() {

			@Override
			public void onChanged(Change<? extends Puntuacion> c) {
				while (c.next()) {
					for (Puntuacion p : c.getAddedSubList()) {
						System.out.println("añadiendo " + p);
					}
					for (Puntuacion p : c.getRemoved()) {
						System.out.println("eliminando " + p);
					}
				}
			}
			
		});
	}

	public ListProperty<Puntuacion> puntuacionesProperty() {
		return this.puntuaciones;
	}

	public ObservableList<Puntuacion> getPuntuaciones() {
		return this.puntuacionesProperty().get();
	}
	
	public void setPuntuaciones(final ObservableList<Puntuacion> puntuaciones) {
		this.puntuacionesProperty().set(puntuaciones);
	}
	
}

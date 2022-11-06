package root;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RootModel {

	private ListProperty<String> palabras = new SimpleListProperty<>(FXCollections.observableArrayList());

	public ListProperty<String> palabrasProperty() {
		return this.palabras;
	}

	public ObservableList<String> getPalabras() {
		return this.palabrasProperty().get();
	}
	
	public void setPalabras(final ObservableList<String> palabras) {
		this.palabrasProperty().set(palabras);
	}
	
	
}

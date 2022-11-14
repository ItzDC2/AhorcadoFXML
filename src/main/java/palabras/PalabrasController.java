package palabras;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Optional;
import java.util.ResourceBundle;

import app.AhorcadoApp;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PalabrasController implements Initializable {

	private PalabrasModel model = new PalabrasModel();
	private final File file = new File("palabras.txt");
	
	@FXML
	private Button agregarButton;
	
	@FXML
	private ListView<String> palabrasList;
	
	@FXML
	private Button quitarButton;
	
	@FXML 
	private BorderPane view;
	
	public PalabrasController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PalabrasView.fxml"));
			loader.setController(this);
			loader.load();
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//Bindings
		
		
		palabrasList.itemsProperty().bind(model.palabrasProperty());
		model.palabraSeleccionadaProperty().bind(palabrasList.getSelectionModel().selectedItemProperty());
		quitarButton.disableProperty().bind(model.palabraSeleccionadaProperty().isNull());
		
		//Cargar datos
		cargarPalabras();
		
	}
	
	public BorderPane getView() {
		return view;
	}
	
	@FXML
	public void onAgregarAction(ActionEvent e) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(AhorcadoApp.primaryStage);
		dialog.setTitle("Nueva palabra:");
		dialog.setHeaderText("Añade una nueva palabra a la lista:");
		dialog.setContentText("Escribe la palabra que quieras agregar:");
		Optional<String> input = dialog.showAndWait();
		if(input.isPresent() && !input.get().isBlank())
			agregarPalabra(input.get());
		else if(input.get().isBlank()) {
			Alert a = new Alert(AlertType.ERROR);
			Stage s = (Stage) a.getDialogPane().getScene().getWindow();
			s.getIcons().add(new Image(getClass().getResourceAsStream("/hangman/error.png")));
			a.setTitle("Error");
			a.setHeaderText("La palabra introducida es inválida.");
			a.setContentText("Debes introducir una palabra válida.");
			a.show();
		}
			
	}
	
	@FXML
	public void onQuitarAction(ActionEvent e) {
		model.palabrasProperty().remove(model.palabrasProperty().indexOf(model.palabraSeleccionadaProperty().get()));
		try (FileWriter fw = new FileWriter(file)){
			for(int i = 0; i < model.palabrasProperty().getSize(); i++) {
				if(i == 0)
					fw.write('\n' + model.palabrasProperty().get(i) + '\n');
				fw.write(model.palabrasProperty().get(i) + '\n');
			}
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private void cargarPalabras() {
		BufferedReader br;
		FileWriter fw;
		if(file.exists()) {
			try {
				br = new BufferedReader(new FileReader(file));
				if(br.readLine() != null)
					model.getPalabras().addAll(Files.readAllLines(file.toPath(), StandardCharsets.UTF_8));
				else { // Escribir datos por defecto si no encuentra palabas en el archivo.
					fw = new FileWriter(file, true);
					fw.write("Atletismo".toUpperCase() + '\n');
					fw.write("Secuestrar".toUpperCase() + '\n');
					fw.write("Enchufar".toUpperCase() + '\n');
					fw.write("Inteligencia".toUpperCase() + '\n');
					fw.write("Mesas".toUpperCase() + '\n');
					fw.write("Invitado".toUpperCase() + '\n');
					fw.write("Patrullero".toUpperCase() + '\n');
					fw.write("Carruaje".toUpperCase() + '\n');
					fw.close();
					model.palabrasProperty().addAll("Atletismo".toUpperCase(), "Secuestrar".toUpperCase(),
							"Enchufar".toUpperCase(), "Inteligencia".toUpperCase(), "Mesas".toUpperCase(), "Invitado".toUpperCase(), "Patrullero".toUpperCase(),
							"Carruaje".toUpperCase());
				}
				br.close();	
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else
			throw new RuntimeException("El archivo de palabras no existe o no se encuentra.");
	}

	private void agregarPalabra(String palabra) {
		if(palabra != null && !palabra.isBlank()) {		
			model.palabrasProperty().add(palabra.toUpperCase());
			try (FileWriter fw = new FileWriter(file, true)){
				fw.write(palabra.toUpperCase() + '\n');
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} 
	}
	
	public ListProperty<String> palabrasProperty() {
		return model.palabrasProperty();
	}
	
	public StringProperty palabraSeleccionadaProperty() {
		return model.palabraSeleccionadaProperty();
	}
}

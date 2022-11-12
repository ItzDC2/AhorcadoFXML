package partida;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import app.AhorcadoApp;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.NumberStringConverter;
import palabras.PalabrasController;
import puntuaciones.PuntuacionesController;

public class PartidaController implements Initializable {

	private PuntuacionesController puntuacionesController = new PuntuacionesController();
	private PalabrasController palabrasController = new PalabrasController();
	private PartidaModel model = new PartidaModel();
	
	private static PartidaController instance;
	
	private StringBuffer res;
	
	@FXML
	private ImageView imagenAhorcado;	
	
	@FXML
	private Label guionesPalabra;
	
	@FXML
	private Label letrasMal;
	
	@FXML
	private BorderPane view;
	
	@FXML
	private Label puntuacionJugador;
	
	@FXML
	private TextField input;
	
	@FXML
	private Button letraButton;
	
	@FXML
	private Button resolverButton;
	
	public PartidaController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PartidaView.fxml"));
			loader.setController(this);
			loader.load();	
			instance = this;
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Bindings
		palabrasController.palabraSeleccionadaProperty().bind(model.palabraSeleccionadaProperty());
		puntuacionJugador.textProperty().bindBidirectional(model.puntuacionProperty(), new NumberStringConverter());
		letraButton.disableProperty().bind(input.textProperty().isEmpty());
		resolverButton.disableProperty().bind(input.textProperty().isEmpty());
		
		//Actions
		if(!AhorcadoApp.enPartida)
			init(false);
		
	}
	
	@FXML
	private void onLetraAction(ActionEvent e) {
		Alert a;
		if(input.getText().length() <= 0 || input.getText().isBlank()) {
			a = new Alert(AlertType.ERROR);
			a.initOwner(AhorcadoApp.primaryStage);
			a.setTitle("Error");
			a.setHeaderText("La letra o palabra introducida no es válida.");
			a.setContentText("La letra o palabra introducida no es válida o está vacía.");
			a.show();
		} else {
			if(input.getText().length() == 1) {
				if(model.getPalabraSeleccionada().contains(input.getText().toUpperCase())) 
					desvelarLetra(Character.toUpperCase(input.getText().charAt(0)));
				else {
					if(!model.letrasIntroducidasProperty().contains(Character.toUpperCase(input.getText().charAt(0)))) {
						marcarError(Character.toUpperCase(input.getText().charAt(0)));						
					} else {
						a = new Alert(AlertType.ERROR);
						a.initOwner(AhorcadoApp.primaryStage);
						a.setTitle("¡Error!");
						a.setHeaderText("¡Ha habido un error!");
						a.setContentText("La letra introducida ya está puesta");
						a.show();
						input.setText("");
					}
				}
			} else {
				a = new Alert(AlertType.ERROR);
				a.initOwner(AhorcadoApp.primaryStage);
				a.setTitle("¡Error!");
				a.setHeaderText("¡Ha habido un error!");
				a.setContentText("Para resolver la palabra usa el botón resolver.");
				a.show();
				input.setText("");
			}
		}
	}
	
	@FXML
	private void onResolverAction(ActionEvent e) {
		Alert a;
		if(input.getText().length() > 1 && input.getText().toUpperCase().equals(model.getPalabraSeleccionada()))
			desvelarPalabra();
		else if(input.getText().length() <= 1 ) {
			a = new Alert(AlertType.ERROR);
			a.initOwner(AhorcadoApp.primaryStage);
			a.setTitle("Error");
			a.setHeaderText("¡Se ha detectado un error!");
			a.setContentText("Para usar la función resolver es necesario introducir más de un dígito.");
			a.show();
		} else 
			marcarError();
	}
	
	private void init(boolean perdio) {
		if(perdio) {
			model.setPuntuacion(0);
			model.setErrores(0);
			model.setLetrasAcertadas(0);
		}
		AhorcadoApp.enPartida = true;
		input.setText("");
		seleccionarPalabra();
		censurarPalabra(model.getPalabraSeleccionada());
		model.setLetrasIntroducidas(new SimpleListProperty<Character>(FXCollections.observableArrayList()));
	}
	
	private void desvelarPalabra() {
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.initOwner(AhorcadoApp.primaryStage);
		a.setTitle("¡Has ganado!");
		a.setHeaderText("¡Felicidades has ganado!");
		a.setContentText("¡Has podido resolver la palabra oculta con " + model.getErrores() + " errores!\n"
				+ "¡Haz click en aceptar para generar otra palabra!");
		guionesPalabra.setText(model.getPalabraSeleccionada().replace("", " " + " "));
		model.setPuntuacion(model.getPuntuacion() + (model.getLetrasPalabraSeleccionada() - model.getLetrasAcertadas()));
		a.show();
		init(false);
	}

	private void desvelarLetra(char c) {
		Alert a;
		if(!model.letrasIntroducidasProperty().contains(c)) {
			for(int x = 0; x < res.length(); x++) {
				if(model.getPalabraSeleccionada().charAt(x) == c) {
					res.setCharAt(x, c);
					model.setLetrasAcertadas(model.getLetrasAcertadas() + 1);
					model.setPuntuacion(model.getPuntuacion() + 1);
				}
			}
			model.letrasIntroducidasProperty().add(c);
			input.setText("");
			guionesPalabra.setText(res.toString().replace("", " " + " "));	
		} else {
			a = new Alert(AlertType.ERROR);
			a.initOwner(AhorcadoApp.primaryStage);
			a.setTitle("¡Error!");
			a.setHeaderText("¡Ha habido un error!");
			a.setContentText("La letra introducida ya está puesta");
			a.show();
			input.setText("");
		}
	}
	
	private void marcarError() {
		model.setErrores(model.getErrores() + 1);
		if(model.getErrores() == 8)
			partidaTerminada(); 
		imagenAhorcado.setImage(new Image(getClass().getResourceAsStream("/hangman/" + model.getErrores() + ".png")));
		input.setText("");
	}
	
	private void partidaTerminada() {
		TextInputDialog dialog;
		AhorcadoApp.enPartida = false;
		imagenAhorcado.setImage(new Image(getClass().getResourceAsStream("/hangman/" + model.getErrores() + ".png")));
		dialog = new TextInputDialog();
		dialog.initOwner(AhorcadoApp.primaryStage);
		dialog.setTitle("¡Has perdido!");
		dialog.setHeaderText("Has perdido, ¡más suerte la próxima vez!");
		dialog.setContentText("Introduce tu nombre de usuario para guardar tu puntuación en el fichero:");
		Optional<String> opc = dialog.showAndWait();
		puntuacionesController.guardarPuntuacion(model.getPuntuacion(), opc.get());
		if(opc.isPresent())
			init(true);
	}

	private void marcarError(char c) {
		if(letrasMal.getText().length() == 0)
			letrasMal.setText(Character.toString(c) + " ");
		else
			letrasMal.setText(letrasMal.getText() + Character.toString(c) + " ");
		model.setErrores(model.getErrores() + 1);
		if(model.getErrores() == 8)
			partidaTerminada();
		model.letrasIntroducidasProperty().add(Character.toUpperCase(c));
		imagenAhorcado.setImage(new Image(getClass().getResourceAsStream("/hangman/" + model.getErrores() + ".png")));
		input.setText("");
	}	
	
	private void seleccionarPalabra() {
		Random r = new Random();
		model.setPalabraSeleccionada(palabrasController.palabrasProperty().get(r.nextInt(palabrasController.palabrasProperty().getSize())));
		model.setLetrasPalabraSeleccionada(model.getPalabraSeleccionada().length());
		model.setLetrasAcertadas(0);
	}
	
	private void censurarPalabra(String s) {
		res = new StringBuffer(s);
		res.delete(0, res.length());
		for(int i = 0; i < s.length(); i++) {
			res.append('_');
		}
		guionesPalabra.setText(res.toString().replace("", " " + " "));
		letrasMal.setText("");
		puntuacionJugador.setText(String.valueOf(model.getPuntuacion()));
	}
	
	public Label getGuionesPalabra() {
		return guionesPalabra;
	}

	public BorderPane getView() {
		return view;
	}
	
	public int getPuntuacion() {
		return model.getPuntuacion();
	}
	
	public static PartidaController getInstance() {
		return instance;
	}
}
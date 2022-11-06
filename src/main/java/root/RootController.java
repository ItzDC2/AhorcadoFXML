package root;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import palabras.PalabrasController;
import partida.PartidaController;
import puntuaciones.PuntuacionesController;

public class RootController implements Initializable {

	public static boolean partidaCargada = false;
	
	@FXML
	private Tab palabrasTab;
	
	@FXML 
	private Tab partidaTab;
	
	@FXML
	private Tab puntuacionesTab;
	
	@FXML
	private TabPane view;
	
	private PalabrasController palabrasController = new PalabrasController();
	private PuntuacionesController puntuacionesController = new PuntuacionesController();
	private PartidaController partidaController = new PartidaController();	
	
	public RootController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RootView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		palabrasTab.setContent(palabrasController.getView());
		puntuacionesTab.setContent(puntuacionesController.getView());
		partidaTab.setContent(partidaController.getView());
		
	}
	
	public TabPane getView() {
		return view;
	}
}

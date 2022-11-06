package app;

import java.util.Optional;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import partida.PartidaController;
import puntuaciones.PuntuacionesController;
import root.RootController;

enum Antonio {
	HOLA, ADIOS, PENE, PEPA
}

public class AhorcadoApp extends Application {

	public static Stage primaryStage;
	
	private RootController controller = new RootController();
	private PuntuacionesController puntuacionesController = new PuntuacionesController();
	
	public static boolean enPartida = false;
	
	//adasdasd
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		AhorcadoApp.primaryStage = primaryStage;
		
		primaryStage.setTitle("Ahorcado");
		primaryStage.setScene(new Scene(controller.getView()));
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/hangman/8.png")));
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(this::terminarApp);
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void terminarApp(WindowEvent e) {
		e.consume();
		Alert a = new Alert(AlertType.CONFIRMATION);
		a.initOwner(AhorcadoApp.primaryStage);
		a.setTitle("Salir");
		a.setHeaderText("¿Seguro que quieres salir?");
		a.setContentText("¡Hay una partida en juego!");
		Optional<ButtonType> res = a.showAndWait();
		if(res.get() == ButtonType.OK) {
			if(PartidaController.getInstance().getPuntuacion() != 0)
				puntuacionesController.guardarPuntuacion(PartidaController.getInstance().getPuntuacion());
			primaryStage.close();
		} 
		
	}
	
}

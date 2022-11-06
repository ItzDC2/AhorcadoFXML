package puntuaciones;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class PuntuacionesController implements Initializable {
	
	private PuntuacionesModel model = new PuntuacionesModel();
	private File f;
	
	private ArrayList<Integer> puntuaciones = new ArrayList<>();
	
	@FXML
	private BorderPane view;
	
	@FXML
	private ListView<String> listaView;
	
	public PuntuacionesController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Puntuaciones.fxml"));
			loader.setController(this);
			loader.load();
			cargarPuntuaciones(new File("puntuaciones.txt"));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//bindings
		listaView.itemsProperty().bindBidirectional(model.puntuacionesProperty());
		
	}

	public BorderPane getView() {
		return view;
	}
	
	
	public void cargarPuntuaciones(File f) {
		BufferedReader br;
		String linea;
		if(!f.exists()) 
			throw new RuntimeException("El archivo no existe o no es accesible.");	
		try {
			this.f = f;
			br = new BufferedReader(new FileReader(f));
			while((linea = br.readLine()) != null)
				puntuaciones.add(Integer.valueOf(linea));
			Collections.sort(puntuaciones);
			Collections.reverse(puntuaciones);
			model.puntuacionesProperty().clear();
			for(int i = 0; i < puntuaciones.size(); i++)
				model.puntuacionesProperty().add(i+1 + ": " + puntuaciones.get(i));
			br.close();
			listaView.setItems(model.puntuacionesProperty());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void guardarPuntuacion(int puntuacion) {
		model.puntuacionesProperty().add(String.valueOf(puntuacion));
		try (FileWriter fw = new FileWriter(f, true)){
			fw.write(String.valueOf(puntuacion) + '\n');
			cargarPuntuaciones(f);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
}

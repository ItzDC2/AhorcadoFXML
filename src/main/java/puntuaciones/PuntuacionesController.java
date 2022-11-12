package puntuaciones;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class PuntuacionesController implements Initializable {
	
	private PuntuacionesModel model = new PuntuacionesModel();
	private final File file = new File("puntuaciones.txt");
	
	@FXML
	private BorderPane view;
	
	@FXML
	private ListView<Puntuacion> listaView;
	
	public PuntuacionesController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Puntuaciones.fxml"));
			loader.setController(this);
			loader.load();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//bindings
		listaView.itemsProperty().bind(new SimpleListProperty<Puntuacion>(model.puntuacionesProperty().sorted()));
		
		//actions
		cargarPuntuaciones();
		
	}

	public BorderPane getView() {
		return view;
	}
	
	private void cargarPuntuaciones() {
		BufferedReader br;
		String linea;
		if(file.exists()) {
			System.out.println("Existo");
			try {
				br = new BufferedReader(new FileReader(file));
				
				model.puntuacionesProperty().clear();
				while((linea = br.readLine()) != null) {
					String parts[] = linea.split(": ");
					model.getPuntuaciones().add(new Puntuacion(parts[0], Integer.parseInt(parts[1])));								
				}
				
				br.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public void guardarPuntuacion(int puntuacion, String usuario) {
		Puntuacion p = new Puntuacion(usuario, puntuacion);
		model.getPuntuaciones().add(p);
		try (FileWriter fw = new FileWriter(file, true)) {
			fw.write(p.toString() + '\n');
			fw.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
//	private void ordenarArray(List<String> array) {
//		List<String> ordenado = new ArrayList<>(array);
//		
//		
//		for(int i = 0; i < ordenado.size() - 1; i++) {
//			
//			String act = ordenado.get(i);
//			String sig = ordenado.get(i+1);
//			int index = act.indexOf(':');
//			int indexSig = sig.indexOf(':');
//			int numAct = Integer.parseInt(act.substring(index+2, act.length()));
//			int numSig = Integer.parseInt(sig.substring(indexSig+2, sig.length()));
//
//			if(numAct > numSig) {
//				ordenado.set(i, sig);
//				ordenado.set(i+1, act);
////				System.out.println("NumAct = " + ordenado.get(i));
////				System.out.println("NumSig = " + ordenado.get(i+1));
//			}			
//			System.out.println(ordenado);
//			
//		}
//		
//		this.puntuaciones = new ArrayList<>(ordenado);
//	}
}

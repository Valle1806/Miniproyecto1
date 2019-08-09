package controles;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import clases.Principal;
import clases.TTS;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControlAyuda {
	
	private Stage escenarioPrincipal;
	private double x, y;

    @FXML
    private Pane panelRaiz;

    @FXML
    private JFXButton botonMinimizar;

    @FXML
    private JFXButton botonCerrar;

    @FXML
    private JFXButton botonInicio;

    @FXML
    private ImageView imagenCarta;

    @FXML
    private Label labeltexto;
    

	private TTS voz = new TTS();
	
	public void initialize() {
		
		voz.speak(labeltexto.getText());
	}

    @FXML
	void regresarInicio(ActionEvent event) {
		try {
			voz.stop();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/principal.fxml"));
			Parent raiz = (Parent) cargador.load();
			ControlVPrincipal control = cargador.getController();
			control.setStage(escenarioPrincipal);
			Pane panelCentral = (Pane) ((Button) event.getSource()).getParent();
			panelCentral.getChildren().clear();
			panelCentral.getChildren().add(raiz);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   
    
    public void setStage(Stage escenario) {
 		this.escenarioPrincipal = escenario;
 	}

 	@FXML
 	void copiarCoordenadas(MouseEvent event) {
 		x = event.getSceneX();
 		y = event.getSceneY();
 	}

 	@FXML
 	void moverPanel(MouseEvent event) {
 		escenarioPrincipal.setX(event.getScreenX() - x);
 		escenarioPrincipal.setY(event.getScreenY() - y);
 	}

 	// Minimizar la aplicación
 	@FXML
 	void minimizar(ActionEvent event) {
 		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
 		stage.setIconified(true);
 	}

 //Cerrar la aplicación
 	@FXML
 	void cerrar(ActionEvent event) {
 		//voz.stop();
 		Alert alert = new Alert(AlertType.WARNING);
 		alert.setHeaderText("Está a punto de cerrar la aplicación");
 		alert.setContentText("¿Está seguro de que desea salir?");

 		Optional<ButtonType> result = alert.showAndWait();
 		if (result.get() == ButtonType.OK) {
 			System.exit(1);
 		}
 	}


}

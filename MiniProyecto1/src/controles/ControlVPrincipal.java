package controles;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import clases.Principal;
import clases.TTS;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControlVPrincipal {
	private Stage escenarioPrincipal;
	private double x, y;
	@FXML
    private Pane panelRaiz;
    @FXML
    private JFXButton botonMinimizar;
    @FXML
    private JFXButton botonA;
    @FXML
    private JFXButton botonCerrar;
    
    @FXML
    private void initialize(){
    	TTS.speak("Bienvenido al juego Cultura muisca al descubierto, "
				+ "Si desea entrar al juego de preguntas coloque la carta de la opción A,"
				+ "Si desea jugar piedra papel o tijera coloque la carta de la opción B");
    }

    @FXML
    void mostrarEsperarCarta(ActionEvent event) {
    	try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/esperarCarta.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlEsperaCarta control = cargador.getController();
			Scene escenario = new Scene(raiz); 
			escenarioPrincipal.setScene(escenario);
			control.setStage(escenarioPrincipal);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    
    
    
  //Minimizar la aplicación
    @FXML
    void minimizar(ActionEvent event) {
    	Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
//Cerrar la aplicación
    @FXML
    void cerrar(ActionEvent event) {
    	Alert alert = new Alert(AlertType.WARNING);
    	alert.setHeaderText("Está a punto de cerrar la aplicación");
    	alert.setContentText("¿Está seguro de que desea salir?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
    	    System.exit(1);
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

  
    

}

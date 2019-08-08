package controles;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import clases.Pregunta;
import clases.Principal;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ControlRespuesta {
	private Stage escenarioPrincipal;
	
	private double x, y;	
	
    @FXML
    private Pane panelRaiz;

    @FXML
    private ImageView imagenRespuesta;
    @FXML
    private JFXButton botonMinimizar;

    @FXML
    private JFXButton botonCerrar;

    @FXML
    private Label respuesta;

    @FXML
    private JFXButton botonInicio;

    @FXML
    private Label puntos;
    private int contador;
    private int tipo_carta;

    public void cargar(String puntoss,String respuestaa,String fondo,int contadorr,int tipo_carta) {
    	String estilo = "-fx-background-color:"+ fondo+";";
    	panelRaiz.setStyle(estilo);
    	puntos.setText(puntoss);
    	respuesta.setText(respuestaa);
    	this.contador= contadorr;
    	this.tipo_carta= tipo_carta;
    	this.tipo_carta++;
    	
    	if(contador>=3) {
    		PauseTransition delay = new PauseTransition(Duration.seconds(5));
    		delay.setOnFinished( event -> mostrarEsperarCarta() );
    		delay.play();
    		
    	}else {
    		contador++;
    		PauseTransition delay = new PauseTransition(Duration.seconds(5));
    		delay.setOnFinished( event -> cambiarAIPregunta(this.tipo_carta,contador));
    		delay.play();
    		
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



    @FXML
    void regresarInicio(ActionEvent event) {
    	try {
    		String estilo = "-fx-background-color: #fff;";
        	panelRaiz.setStyle(estilo);
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/principal.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlVPrincipal control= cargador.getController();
			control.setStage(escenarioPrincipal);
			Pane panelCentral = (Pane)((Button)event.getSource()).getParent();
			panelCentral.getChildren().clear();
			panelCentral.getChildren().add(raiz);
		
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    void cambiarAIPregunta(int tipo_carta, int contador ) {
    	try {
    		
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/pregunta.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlPregunta control =cargador.getController();
			control.inicio(tipo_carta,contador);
			Scene escenario = new Scene(raiz); 
			escenarioPrincipal.setScene(escenario);
			control.setStage(escenarioPrincipal);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
   
    void mostrarEsperarCarta() {
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

}

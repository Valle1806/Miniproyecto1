package controles;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import clases.Pregunta;
import clases.Principal;
import clases.Respuesta;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ControlPregunta {

	private Stage escenarioPrincipal;
	
	private double x, y;	
	private String correcta,puntos=" ",respuesta= " ",colorFondo;
    @FXML
    private Label pregunta;
    @FXML
    private Pane panelRaiz;
    @FXML
    private JFXButton botonMinimizar;
    @FXML
    private JFXButton botonCerrar;
    @FXML
    private JFXButton botonInicio;
    @FXML
    private JFXButton respuestaA;
    @FXML
    private JFXButton respuestaC;
    @FXML
    private JFXButton respuestaB;
    @FXML
    private JFXButton respuestaD;
    
    
    
    public void initialize(Pregunta preguntas) {  
    	pregunta.setText(preguntas.getPregunta());
    	Respuesta respuestas[]=preguntas.getRespuestas();
    	for(int i=0; i<respuestas.length; i++) {
    		if(respuestas[i].getCorrecto()) {
    			correcta=respuestas[i].getRespuesta();
    		}
    	}
    	respuestaA.setText(respuestas[0].getRespuesta());
    	respuestaB.setText(respuestas[1].getRespuesta());
    	respuestaC.setText(respuestas[2].getRespuesta());
    	respuestaD.setText(respuestas[3].getRespuesta());
    }
    @FXML
    void verificarRespuestaA(ActionEvent event) {
    	if(respuestaA.getText().equals(correcta)) {
    		System.out.println("CorrectaA");
    		puntos= "10pts";
    		respuesta= "Respuesta Correcta"; 
    		colorFondo="#3FF500";
    	}else {
    		puntos= "0pts";
    		respuesta= "Respuesta Incorrecta";
    		colorFondo="#E72323";
    	}
    	cargarInterfazRespuesta(event);
    }

    @FXML
    void verificarRespuestaB(ActionEvent event) {
    	if(respuestaB.getText().equals(correcta)) {
    		System.out.println("CorrectaB");
    		puntos= "10pts";
    		respuesta= "Respuesta Correcta";
    		colorFondo="#3FF500";
    	}else {
    		puntos= "0pts";
    		respuesta= "Respuesta Incorrecta";
    		colorFondo="#E72323";
    	}
    	cargarInterfazRespuesta(event);
    }

    @FXML
    void verificarRespuestaC(ActionEvent event) {
    	if(respuestaC.getText().equals(correcta)) {
    		System.out.println("CorrectaC");
    		puntos= "10pts";
    		respuesta= "Respuesta Correcta";
    		colorFondo="#3FF500";
    	}
    	else {
    		puntos= "0pts";
    		respuesta= "Respuesta Incorrecta";
    		colorFondo="#E72323";
    	}
    	cargarInterfazRespuesta(event);
    }

    @FXML
    void verificarRespuestaD(ActionEvent event) {
    	if(respuestaD.getText().equals(correcta)) {
    		System.out.println("CorrectaD");
    		puntos= "10pts";
    		respuesta= "Respuesta Correcta";
    		colorFondo="#3FF500";
    	}
    	else {
    		puntos= "0pts";
    		respuesta= "Respuesta Incorrecta";
    		colorFondo="#E72323";
    	}
    	cargarInterfazRespuesta(event);
    }
    @FXML
    public void cargarInterfazRespuesta(ActionEvent event) {
    	try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/respuesta.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlRespuesta control= cargador.getController();
			control.setStage(escenarioPrincipal);
			control.cargar(puntos, respuesta, colorFondo);
			Pane panelCentral = (Pane)((Button)event.getSource()).getParent();
			panelCentral.getChildren().clear();
			panelCentral.getChildren().add(raiz);
			//EFECTO
			/*Scene escenario = raiz.getScene();
			raiz.translateXProperty().set(escenario.getWidth());
	    	Timeline timeline = new Timeline();
	    	KeyValue rango = new KeyValue(raiz.translateXProperty(), 0, Interpolator.EASE_BOTH);
	    	KeyFrame duracion = new KeyFrame(Duration.seconds(0.3), rango);
	    	timeline.getKeyFrames().add(duracion);
	    	timeline.play();	*/
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void regresarInicio(ActionEvent event) {
    	try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/principal.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlVPrincipal control= cargador.getController();
			control.setStage(escenarioPrincipal);
			Pane panelCentral = (Pane)((Button)event.getSource()).getParent();
			panelCentral.getChildren().clear();
			panelCentral.getChildren().add(raiz);
			//EFECTO
			/*Scene escenario = raiz.getScene();
			raiz.translateXProperty().set(escenario.getWidth());
	    	Timeline timeline = new Timeline();
	    	KeyValue rango = new KeyValue(raiz.translateXProperty(), 0, Interpolator.EASE_BOTH);
	    	KeyFrame duracion = new KeyFrame(Duration.seconds(0.3), rango);
	    	timeline.getKeyFrames().add(duracion);
	    	timeline.play();	*/
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
   //___________________________________________________________________________________________________ 
    
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

}
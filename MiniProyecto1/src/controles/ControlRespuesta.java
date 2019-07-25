package controles;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;

import clases.Principal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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

    public void cargar(String puntoss,String respuestaa,String fondo) {
    	String estilo = "-fx-background-color:"+ fondo+";";
    	panelRaiz.setStyle(estilo);
    	puntos.setText(puntoss);
    	respuesta.setText(respuestaa);
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

}

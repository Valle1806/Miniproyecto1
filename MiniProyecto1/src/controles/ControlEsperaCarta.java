package controles;

import java.io.File;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

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
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ControlEsperaCarta {
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
    private JFXButton botonIPregunta;
    @FXML
    private Label mensajeUbicaCarta;
    @FXML
    private Label nombreCarta;
    @FXML
    private MediaView video;
    private Media me;
    @FXML
    private ImageView imagenCarta;
    private Pregunta pregunta;
    private PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
    
    public void initialize() {  
    	pregunta= new Pregunta("nel?", new Respuesta[] {
    			new Respuesta("estemen", false),
    			new Respuesta("tugfa", false),
    			new Respuesta("swebok", true),
    			new Respuesta("crazyneck", false)
    	});
    	
    	video.setVisible(true);
    	/*Media media = new Media(Principal.class.getResource("/imagenes/Saguamanchica.mp4").toExternalForm());
    	MediaPlayer player = new MediaPlayer(media);
    	player.play();
*/      
        final File f = new File("src/imagenes/Saguamanchica.mp4");
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(f.toURI().toString()));
        mediaPlayer.setAutoPlay(true);
        video.setMediaPlayer(mediaPlayer);
        try {
			arduino.arduinoRX("COM12", 9600, comListener);
		} catch (ArduinoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    private SerialPortEventListener comListener = new SerialPortEventListener() {
		@Override
		public void serialEvent(SerialPortEvent arg0) {
			// TODO Auto-generated method stub
			
			try {
				if(arduino.isMessageAvailable()) {
					String opcion = arduino.printMessage();
					int op = Integer.parseInt(opcion);
					//fondo.setText(opcion);
					switch(op) {
					  case 1: imagenCarta.setVisible(true);
					  		  mensajeUbicaCarta.setVisible(false);
					  		  System.out.println("1");
					          break;
					  case 2: imagenCarta.setVisible(true);
					  		  mensajeUbicaCarta.setVisible(false);
					  		  System.out.println("2");
						      break;
					  case 3: imagenCarta.setVisible(true);
			  		          mensajeUbicaCarta.setVisible(false);
			  		        System.out.println("3");
						      break;
					  case 4: imagenCarta.setVisible(true);
					  		  mensajeUbicaCarta.setVisible(false);
					  		System.out.println("4");
					          break;
					  case 5: imagenCarta.setVisible(true);
					  		  mensajeUbicaCarta.setVisible(false);
					  		System.out.println("5");
					  		  break;
					  case 6: imagenCarta.setVisible(true);
					  		  mensajeUbicaCarta.setVisible(false);
					  		System.out.println("6");
			  		  		  break;
					  case 7: imagenCarta.setVisible(true);
			  		  		  mensajeUbicaCarta.setVisible(false);
			  		  		System.out.println("7");
						  	  break;
					  case 8: imagenCarta.setVisible(true);
			  		  		  mensajeUbicaCarta.setVisible(false);
			  		  		  break;
					}//
					
				}
			} catch (SerialPortException | ArduinoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	};
	
	
    
    	
    
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

    @FXML
    void cambiarAIPregunta(ActionEvent event) {
    	try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/pregunta.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlPregunta control =cargador.getController();
			arduino.killArduinoConnection();
			control.inicio(pregunta);
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

package controles;

import java.io.File;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import clases.Pregunta;
import clases.Principal;
import clases.Respuesta;
import clases.TTS;
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
    private  ImageView mensajeUbicaCarta;
    @FXML
    private Label nombreCarta;
   
    @FXML
    private ImageView imagenCarta;
   
 
    private PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
    @FXML
    private MediaView video;
    
    private MediaPlayer mediaPlayer;
    
    private boolean reproducion_video=false;
    
    public void initialize() {  
    	video.setVisible(true);
       	TTS.speak("Coloca la carta de un personaje para conocer acerca de él");

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
    
    void mostrarVideo(String nombre_video) {
    	
    	reproducion_video=true;
    	final File f = new File("src/videos/"+ nombre_video+ ".mp4");
		mediaPlayer = new MediaPlayer(new Media(f.toURI().toString()));
        video.setMediaPlayer(mediaPlayer); 
        mediaPlayer.play();
   
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
            	cambiarAIPregunta(0);
            }
        });
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
					  			mostrarVideo("ayuda");
					  		  mensajeUbicaCarta.setVisible(false);
					  		arduino.killArduinoConnection();
					  		  System.out.println("1");
					  		  
					          break;
					  case 2: imagenCarta.setVisible(true);
					  			mostrarVideo("ayuda");
					  		  mensajeUbicaCarta.setVisible(false);
					  		arduino.killArduinoConnection();
					  		  System.out.println("2");
						      break;
					  case 3: imagenCarta.setVisible(true);
					  			mostrarVideo("ayuda");
			  		          mensajeUbicaCarta.setVisible(false);
			  		        arduino.killArduinoConnection();
			  		        System.out.println("3");
						      break;
					  case 4: imagenCarta.setVisible(true);
					  			mostrarVideo("ayuda");
					  		  mensajeUbicaCarta.setVisible(false);
					  		arduino.killArduinoConnection();
					  		System.out.println("4");
					          break;
					  case 5: imagenCarta.setVisible(true);
					  			mostrarVideo("ayuda");
					  		  mensajeUbicaCarta.setVisible(false);
					  		arduino.killArduinoConnection();
					  		System.out.println("5");
					  		  break;
					  case 6: imagenCarta.setVisible(true);
					  			mostrarVideo("ayuda");
					  		  mensajeUbicaCarta.setVisible(false);
					  		arduino.killArduinoConnection();
					  		System.out.println("6");
			  		  		  break;
					  case 7: imagenCarta.setVisible(true);
			  		  		  mensajeUbicaCarta.setVisible(false);
			  		  		arduino.killArduinoConnection();
			  		  		System.out.println("7");
						  	  break;
					  case 8: imagenCarta.setVisible(true);
			  		  		  mensajeUbicaCarta.setVisible(false);
			  		  		arduino.killArduinoConnection();
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
	    	if(reproducion_video) {
	    		mediaPlayer.stop();
	    	}
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

   
    void cambiarAIPregunta(int tipo_carta) {
    	try {
    		mediaPlayer.stop();
    		video.setMediaPlayer(null);
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/pregunta.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlPregunta control =cargador.getController();
			control.inicio(tipo_carta,1);
			Scene escenario = new Scene(raiz); 
			escenarioPrincipal.setScene(escenario);
			control.setStage(escenarioPrincipal);
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

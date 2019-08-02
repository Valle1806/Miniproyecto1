package controles;

import java.io.File;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import clases.Pregunta;
import clases.Principal;
import clases.Respuesta;
import javafx.application.Platform;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

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
    
    private int contadorAux;


    private PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
    
    private Pregunta[] preguntas= new Pregunta[18];
    
    private int tipo_carta;
    
    
    public void inicio(int tipo_carta, int contador) {
    	iniciarPreguntas();
    	contadorAux=contador;
    	this.tipo_carta=tipo_carta;
    	pregunta.setText(preguntas[0].getPregunta());
    	Respuesta respuestas[]=preguntas[0].getRespuestas();
    	for(int i=0; i<respuestas.length; i++) {
    		if(respuestas[i].getCorrecto()) {
    			correcta=respuestas[i].getRespuesta();
    		}
    	}
    	respuestaA.setText(respuestas[0].getRespuesta());
    	respuestaB.setText(respuestas[1].getRespuesta());
    	respuestaC.setText(respuestas[2].getRespuesta());
    	respuestaD.setText(respuestas[3].getRespuesta());
    	
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
    
    void iniciarPreguntas() {
    	preguntas[0]= new Pregunta("¿Qué era Saguamanchica?", new Respuesta[] {
    			new Respuesta("un Dios", false),
    			new Respuesta("un Zaque", true),
    			new Respuesta("un Zipa", false),
    			new Respuesta("un Cacique", false)
    	});
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
    	cargarInterfazRespuesta();
    }

   
    private SerialPortEventListener comListener = new SerialPortEventListener() {
		@Override
		public void serialEvent(SerialPortEvent arg0) {
			// TODO Auto-generated method stub
			
			try {
				if(arduino.isMessageAvailable()) {
					String opcion = arduino.printMessage();
					int op = Integer.parseInt(opcion);
					switch(op) {
					  case 1: if(respuestaA.getText().equals(correcta)) {
						  		puntos= "10pts";
						  		respuesta= "Respuesta Correcta"; 
						  		colorFondo="#3FF500";
					  			}else {
					  	    		puntos= "0pts";
					  	    		respuesta= "Respuesta Incorrecta";
					  	    		colorFondo="#851A1A";
					  	    	}
					  			Platform.runLater(new Runnable() {
					  				@Override public void run() {
					  					cargarInterfazRespuesta();						  				}
					  			});
					  		  
					  			
					          break;
					  case 2: if(respuestaB.getText().equals(correcta)) {
					  			puntos= "10pts";
					  			respuesta= "Respuesta Correcta"; 
					  			colorFondo="#3FF500";
				  			   }else {
				  	    		puntos= "0pts";
				  	    		respuesta= "Respuesta Incorrecta";
				  	    		colorFondo="#851A1A";
				  			   }
					  			Platform.runLater(new Runnable() {
					  				@Override public void run() {
					  					cargarInterfazRespuesta();						  				}
					  			});
						      break;
					  case 3: if(respuestaC.getText().equals(correcta)) {
					  			puntos= "10pts";
					  			respuesta= "Respuesta Correcta"; 
					  			colorFondo="#3FF500";
				  			   }else {
				  	    		puntos= "0pts";
				  	    		respuesta= "Respuesta Incorrecta";
				  	    		colorFondo="#851A1A";
				  	    	   }
							  	Platform.runLater(new Runnable() {
					  				@Override public void run() {
					  					cargarInterfazRespuesta();						  				}
					  			});
				  			
						      break;
					  case 4: if(respuestaD.getText().equals(correcta)) {
					  			puntos= "10pts";
					  			respuesta= "Respuesta Correcta"; 
					  			colorFondo="#3FF500";
				  			  }else {
				  	    		puntos= "0pts";
				  	    		respuesta= "Respuesta Incorrecta";
				  	    		colorFondo="#851A1A";
				  			  }
							  Platform.runLater(new Runnable() {
					  				@Override public void run() {
					  					cargarInterfazRespuesta();						  				}
					  			});
				  			
					          break;
					  default:
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
    	cargarInterfazRespuesta();
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
    	cargarInterfazRespuesta();
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
    	cargarInterfazRespuesta();
    }
    @FXML
    public void cargarInterfazRespuesta() {
    	try {
    		//arduino.killArduinoConnection();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/respuesta.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlRespuesta control= cargador.getController();
			control.setStage(escenarioPrincipal);
			control.cargar(puntos, respuesta, colorFondo,contadorAux,tipo_carta);
			panelRaiz.getChildren().clear();
			panelRaiz.getChildren().add(raiz);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void regresarInicio(ActionEvent event) {
    	try {
    		//arduino.killArduinoConnection();
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
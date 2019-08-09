package controles;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import clases.Pregunta;
import clases.Principal;
import clases.TTS;
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
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ControlPPT {

	private Stage escenarioPrincipal;
	private double x, y;
	@FXML
    private Label labelTurnoJ2;

    @FXML
    private Pane panelRaiz;

    @FXML
    private Label labelTurnoJ1;

    @FXML
    private JFXButton botonMinimizar;

    @FXML
    private JFXButton botonCerrar;

    @FXML
    private JFXButton botonInicio;
    
    //Para mostrar el resultado
    @FXML
    private Label labeLJ1;
    @FXML
    private Label labelGanador;
    @FXML
    private Label labelLJ2;
    @FXML
    private ImageView imagenJ2;
    @FXML
    private ImageView imagenJ1;
    @FXML
    private Label labelvs;


    private PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();

	private TTS voz = new TTS();
	
	private int turno=1;
	
	private String eleccionJ1,eleccionJ2;
	

    private void connectArduino() {
		try {
			arduino.arduinoRX(Parameters.COM_PORT, 9600, comListener);
		} catch (ArduinoException e) {
			System.out.println("Arduino connection error");
		} catch (SerialPortException e) {
			System.out.println("Arduino connection error");
		}
	}

    public void initialize() {
    	voz.speak("Turno Jugador 1");
    	connectArduino();
    }
    
    public void mostrarResultado(String ganador) {
    	voz.speak("jugador uno eligió "+ eleccionJ1 +" y jugador 2 eligió "+ eleccionJ2+ " " + ganador);
    	labelTurnoJ2.setVisible(false);
    	labelTurnoJ1.setVisible(false);
        labeLJ1.setVisible(true);
        labelLJ2.setVisible(true);
        labelGanador.setText(ganador);
        labelGanador.setVisible(true);
        imagenJ2.setVisible(true);
        imagenJ1.setVisible(true);
        labelvs.setVisible(true);;
        PauseTransition delay = new PauseTransition(Duration.seconds(6));
		delay.setOnFinished( event -> reiniciarJuego() );
		delay.play();
        
    }
    public void compararElecciones(String eleccion1,String eleccion2) {
    	if(eleccion1.equals("papel")) {
    		if(eleccion2.equals("piedra")) {
    			mostrarResultado("Gana jugador 1");
    		}else {
    			if(eleccion2.equals("tijera")){
    				mostrarResultado("Gana jugador 2");
    			}else {
    				mostrarResultado("Empate");
    			}
    		}
    	}else {
    		if(eleccion1.equals("tijera")) {
        		if(eleccion2.equals("piedra")) {
        			mostrarResultado("Gana jugador 2");
        		}else {
        			if(eleccion2.equals("papel")){
        				mostrarResultado("Gana jugador 1");
        			}else {
        				mostrarResultado("Empate");
        			}
        		}
        	}else {
        		if(eleccion1.equals("piedra")) {
            		if(eleccion2.equals("papel")) {
            			mostrarResultado("Gana jugador 2");
            		}else {
            			if(eleccion2.equals("tijera")){
            				mostrarResultado("Gana jugador 1");
            			}else {
            				mostrarResultado("Empate");
            			}
            		}
            	}
        	}
    	}
    }
    public void asignarEleccion(int turno1, String tipo) {
    	System.out.println(turno1);
    	if(turno1==1) {
    		voz.speak("jugador uno colocó tarjeta, turno jugador 2");
    		labelTurnoJ1.setText("Jugador 1 colocó tarjeta");
    		labelTurnoJ2.setVisible(true);
    		eleccionJ1=tipo;
    		turno++;
    	}else {
    		try {
				arduino.killArduinoConnection();
			} catch (ArduinoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		voz.speak("jugador dos colocó tarjeta");
    		
    		eleccionJ2=tipo;
    		PauseTransition delay = new PauseTransition(Duration.seconds(6));
    		delay.setOnFinished( event -> compararElecciones(eleccionJ1,eleccionJ2) );
    		delay.play();
    		
    		
    	}
    }
    
    private SerialPortEventListener comListener = new SerialPortEventListener() {
		@Override
		public void serialEvent(SerialPortEvent arg0) {
			// TODO Auto-generated method stub

			try {

			 boolean accepted = true;
				while (accepted) {
					if (arduino.isMessageAvailable()) {
						String opcion = arduino.printMessage();
						int op = Integer.parseInt(opcion);

						switch (op) {
							case 5://PAPEL saguamanchica
								voz.stop();
								accepted=false;
								Platform.runLater(new Runnable() {
						            @Override public void run() {
						            	asignarEleccion(turno,"papel");
						            }
						        });
								
								break;
							case 6://TIJERA bachue
								voz.stop();
								accepted=false;
								Platform.runLater(new Runnable() {
						            @Override public void run() {
						            	asignarEleccion(turno,"tijera");
						            }
						        });
								break;
							case 7://PIEDRA hunzahua
								voz.stop();
								accepted=false;
								Platform.runLater(new Runnable() {
						            @Override public void run() {
						            	asignarEleccion(turno,"piedra");
						            }
						        });
								break;
							case 8://PAPEL goranchacha
								voz.stop();
								accepted=false;
								Platform.runLater(new Runnable() {
						            @Override public void run() {
						            	asignarEleccion(turno,"papel");
						            }
						        });
								break;
							case 9://PIEDRA nemequene
								voz.stop();
								accepted=false;
								Platform.runLater(new Runnable() {
						            @Override public void run() {
						            	asignarEleccion(turno,"piedra");
						            }
						        });
								break;
							case 10://TIJERA bochica
								voz.stop();
								accepted=false;
								Platform.runLater(new Runnable() {
						            @Override public void run() {
						            	asignarEleccion(turno,"tijera");
						            }
						        });
								break;
						default:
							voz.speak("Carta equivocada");
							// arduino.killArduinoConnection();
							break;
						}//

					}
//
				}
			} catch (SerialPortException | ArduinoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};
 // ___________________________________________________________________________________________________

	@FXML
	void regresarInicio(ActionEvent event) {
		try {
			voz.stop();
			if(turno!=2) {
			arduino.killArduinoConnection();
			}
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

	
	void reiniciarJuego() {
		try {
			voz.stop();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/piedraPapelTijera.fxml"));
			Parent raiz = (Parent) cargador.load();
			ControlPPT control = cargador.getController();
			Scene escenario = new Scene(raiz);
			escenarioPrincipal.setScene(escenario);
			control.setStage(escenarioPrincipal);

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

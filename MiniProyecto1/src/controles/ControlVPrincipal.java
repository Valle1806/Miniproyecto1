package controles;

import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import clases.Principal;
import clases.TTS;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
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
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ControlVPrincipal {
	private Stage escenarioPrincipal;
	private double x, y;
	private PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
	private TTS voz = new TTS();

	@FXML
	private Pane panelRaiz;
	@FXML
	private JFXButton botonMinimizar;
	@FXML
	private JFXButton botonA;
    @FXML
    private JFXButton botonB;
    @FXML
    private JFXButton botonC;
	@FXML
	private JFXButton botonCerrar;

	private void connectArduino() {
		try {
			arduino.arduinoRX(Parameters.COM_PORT, 9600, comListener);
		} catch (ArduinoException e) {
			System.out.println("Arduino connection error");
		} catch (SerialPortException e) {
			System.out.println("Arduino connection error");
		}
	}

	@FXML
	private void initialize() {
		voz.speak("Bienvenido al juego Cultura muisca al descubierto, "
				+ "Si desea entrar al juego de preguntas coloque la carta de la opción A,"
				+ "Si desea jugar piedra papel o tijera coloque la carta de la opción B");

		
		connectArduino();
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
						System.out.println(opcion);
						int op = Integer.parseInt(opcion);

						switch (op) {
						case 1:
							accepted = false;
							voz.stop();
							voz.speak("Opción A seleccionada");
							arduino.killArduinoConnection();
							PauseTransition delay = new PauseTransition(Duration.seconds(2));
							delay.setOnFinished(event -> mostrarEsperarCartaAux());
							delay.play();

							break;
						case 2:
							voz.speak("Opción B seleccionada");
							arduino.killArduinoConnection();
							PauseTransition delay1 = new PauseTransition(Duration.seconds(2));
							delay1.setOnFinished(event -> mostrarPPTAux());
							delay1.play();
							accepted = false;
							break;
						case 3:
							voz.speak("Opción C seleccionada");
							arduino.killArduinoConnection();
							PauseTransition delay2 = new PauseTransition(Duration.seconds(2));
							delay2.setOnFinished(event -> mostrarInformacionAux());
							delay2.play();
							accepted = false;
							break;
						default:
							voz.speak("Carta equivocada, por favor use la carta A o B");
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

	@FXML
    void mostrarPPT(ActionEvent event) {
		voz.stop();
		try {
			arduino.killArduinoConnection();
		} catch (ArduinoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mostrarPPTAux();
    }
	private void mostrarPPTAux() {
		try {
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
	
	private void mostrarInformacionAux() {
		try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/ayuda.fxml"));
			Parent raiz = (Parent) cargador.load();
			ControlAyuda control = cargador.getController();
			Scene escenario = new Scene(raiz);
			escenarioPrincipal.setScene(escenario);
			control.setStage(escenarioPrincipal);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML
	void mostrarInformacion(ActionEvent event) {
		voz.stop();
		try {
			arduino.killArduinoConnection();
		} catch (ArduinoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mostrarInformacionAux();
	}
	private void mostrarEsperarCartaAux() {
		try {
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/esperarCarta.fxml"));
			Parent raiz = (Parent) cargador.load();
			ControlEsperaCarta control = cargador.getController();
			Scene escenario = new Scene(raiz);
			escenarioPrincipal.setScene(escenario);
			control.setStage(escenarioPrincipal);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void mostrarEsperarCarta(ActionEvent event) {
		voz.stop();
		try {
			arduino.killArduinoConnection();
		} catch (ArduinoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mostrarEsperarCartaAux();
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
		voz.stop();
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Está a punto de cerrar la aplicación");
		alert.setContentText("¿Está seguro de que desea salir?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
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

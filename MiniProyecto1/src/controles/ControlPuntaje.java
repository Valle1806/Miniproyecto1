package controles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import clases.Pregunta;
import clases.Principal;
import clases.TTS;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ControlPuntaje {

	private Stage escenarioPrincipal;
	private double x, y;
	private String correcta, puntos = " ", respuesta = " ", colorFondo;

	@FXML
	private Pane panelRaiz;
	@FXML
	private JFXButton botonMinimizar;
	@FXML
	private JFXButton botonCerrar;
	@FXML
	private JFXButton botonInicio;

	@FXML
	private TableView table;
	
	private PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();

	private TTS voz = new TTS();

	@FXML
	private void initialize() {
		voz.speak("A continuación se leerán los puntajes registrados en las partidas jugadas, "
				+ "recuerde que en cualquier puede regresar al menú inicial usando " + "la carta de la opción A");

		connectArduino();

		try {
			final File f = new File("C:\\Users\\sebas\\Desktop\\Miniproyecto1\\MiniProyecto1\\src\\puntajes.txt");
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			for (int i = 0; i < 2; i++) {
				System.out.println(br.readLine());
			}
			
			
		} catch (Exception e) {
			System.out.println("Error lectura archivo");
		}

	}

	private void connectArduino() {
		try {
			arduino.arduinoRX(Parameters.COM_PORT, 9600, comListener);
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
			PauseTransition delay = new PauseTransition(Duration.seconds(4));
			try {
				boolean accepted = true;
				while (accepted) {
					if (arduino.isMessageAvailable()) {
						String opcion = arduino.printMessage();
						int op = Integer.parseInt(opcion);
						// fondo.setText(opcion);
						voz.stop();
						switch (op) {
						case 1:
							voz.speak("Carta A seleccionada, dirigiendose al menú principal");
							delay.setOnFinished((actionEvent) -> {
								auxRegresarAlInicio(actionEvent);
							});
							accepted = true;
							delay.play();
						default:
							voz.speak("Carta equivocada");

							break;
						}//

					}
				}
			} catch (SerialPortException | ArduinoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	};

	private void auxRegresarAlInicio(ActionEvent event) {
		try {
			voz.stop();
			arduino.killArduinoConnection();
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

	@FXML
	void regresarInicio(ActionEvent event) {
		auxRegresarAlInicio(event);
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
		voz.stop();
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Está a punto de cerrar la aplicación");
		alert.setContentText("¿Está seguro de que desea salir?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.exit(1);
		}
	}

}

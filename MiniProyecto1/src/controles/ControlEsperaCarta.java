package controles;

import java.awt.ImageCapabilities;
import java.io.File;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;

import clases.Pregunta;
import clases.Principal;
import clases.Respuesta;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
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
	private ImageView mensajeUbicaCarta;
	@FXML
	private Label nombreCarta;
	@FXML
	private ImageView gifAyuda;
	@FXML
	private ImageView imagenCarta;

	private PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
	@FXML
	private MediaView video;

	private MediaPlayer mediaPlayer;

	private boolean reproducion_video = false;

	private TTS voz = new TTS();

	public void initialize() {
		video.setVisible(true);
		File file = new File("src/imagenes/ayuda.gif");
		Image image = new Image(file.toURI().toString());
		gifAyuda.setImage(image);
		voz.speak("Ubica la carta deseada sobre el sensor");
 
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

	void mostrarVideo(String nombre_video,int cartNumber) {
		voz.stop();
		reproducion_video = true;
		//final File f = new File("src/videos/" + nombre_video + ".mp4");
		final File f = new File("src/videos/ayuda.mp4");
		mediaPlayer = new MediaPlayer(new Media(f.toURI().toString()));
		video.setMediaPlayer(mediaPlayer);
		mediaPlayer.play();

		mediaPlayer.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				cambiarAIPregunta(cartNumber);
			}
		});
	}

	private void putCart(String cartName) {
		gifAyuda.setVisible(false);
		File file = new File("src/imagenes/cartas/" + cartName + ".png");
		Image image = new Image(file.toURI().toString());
		imagenCarta.setImage(image);
		imagenCarta.setVisible(true);
		mensajeUbicaCarta.setVisible(false);
		
//		imagenCarta = new ImageView(image);
//		imagenCarta.setVisible(true);
//		
	}

	private void playVideoAfterCard(String cartName,int cartNumber) {
		voz.stop();
		try {
			arduino.killArduinoConnection();
		} catch (Exception e) {
			System.out.println("arduino doesn't stoped");
		}
		putCart(cartName);
		voz.speak("Personaje " + cartName + "selecionado, Escucha el siguiente video con atención");
		PauseTransition delay = new PauseTransition(Duration.seconds(6));
		delay.setOnFinished( event -> mostrarVideo(cartName, cartNumber) );
		delay.play();
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
						// fondo.setText(opcion);
						voz.stop();
						switch (op) {
						case 5:
							playVideoAfterCard("saguamanchica",0);
							accepted=false;
							break;
						case 6:
							playVideoAfterCard("bachue",3);
							accepted=false;
							break;
						case 7:
							playVideoAfterCard("hunzahua",6);
							accepted=false;
							break;
						case 8:
							playVideoAfterCard("goranchacha",9);
							accepted=false;
							break;
						case 9:
							playVideoAfterCard("nemequene",12);
							accepted=false;
							break;
						case 10:
							playVideoAfterCard("bochica",15);
							accepted=false;
							break;
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

	@FXML
	void regresarInicio(ActionEvent event) {
		try {
			if (reproducion_video) {
				mediaPlayer.stop();
			}
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

	void cambiarAIPregunta(int tipo_carta) {
		try {
			voz.stop();
			mediaPlayer.stop();
			video.setMediaPlayer(null);
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/pregunta.fxml"));
			Parent raiz = (Parent) cargador.load();
			ControlPregunta control = cargador.getController();
			control.inicio(tipo_carta, 1);
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
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Está a punto de cerrar la aplicación");
		alert.setContentText("¿Está seguro de que desea salir?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.exit(1);
		}
	}
}

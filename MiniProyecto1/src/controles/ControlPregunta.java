package controles;


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class ControlPregunta {

	private Stage escenarioPrincipal;
	private double x, y;
	private String correcta, puntos = " ", respuesta = " ", colorFondo;

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

	private Pregunta[] preguntas = new Pregunta[18];

	private int tipo_carta;
	private TTS voz = new TTS();
	
	

	public void inicio(int tipo_carta, int contador) {
		iniciarPreguntas();
		contadorAux = contador;
		this.tipo_carta = tipo_carta;

		pregunta.setWrapText(true);
		pregunta.setText(preguntas[tipo_carta].getPregunta());
		pregunta.setCenterShape(true);
		
		//Conversion de arreglo a lista
		List <Respuesta> respuestasList = new ArrayList <Respuesta> ();
		Respuesta respuestas[] = preguntas[tipo_carta].getRespuestas();
		respuestasList= Arrays.asList(respuestas);
		
		java.util.Collections.shuffle(respuestasList);
		for (int i = 0; i < respuestas.length; i++) {
			if (respuestasList.get(i).getCorrecto()) {
				correcta = respuestasList.get(i).getRespuesta();
			}
		}

		voz.speak(preguntas[tipo_carta]);

		respuestaA.setWrapText(true);
		respuestaA.setText(respuestasList.get(0).getRespuesta());
		respuestaA.setTextFill(Color.WHITE);
		respuestaA.setFont(new Font("Cambria", 18));

		respuestaB.setWrapText(true);
		respuestaB.setText(respuestasList.get(1).getRespuesta());
		respuestaB.setFont(new Font("Cambria", 18));
		respuestaB.setTextFill(Color.WHITE);

		respuestaC.setWrapText(true);
		respuestaC.setText(respuestasList.get(2).getRespuesta());
		respuestaC.setFont(new Font("Cambria", 18));
		respuestaC.setTextFill(Color.WHITE);

		respuestaD.setWrapText(true);
		respuestaD.setText(respuestasList.get(3).getRespuesta());
		respuestaD.setFont(new Font("Cambria", 18));
		respuestaD.setTextFill(Color.WHITE);

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

	void iniciarPreguntas() {
		//Saguamanchica
		preguntas[0] = new Pregunta("�Qu� tierra atac� Saguamanchica junto a su ej�rcito?",
				new Respuesta[] { new Respuesta("Fusagasusa", true), new Respuesta("Bogot�", false),
						new Respuesta("Tunja", false), new Respuesta("Bacat�", false) });
		
		preguntas[1] = new Pregunta("�Cu�ntos a�os dur� la guerra?",
				new Respuesta[] { new Respuesta("16 a�os", true), new Respuesta("100 d�as",false),
						new Respuesta("40 a�os", false), new Respuesta("5 a�os", false) });
		
		preguntas[2] = new Pregunta("�A qu� cacique captur� Saguamanchica?",
				new Respuesta[] { new Respuesta("Goranchach�", false), new Respuesta("Hunzahua", false),
						new Respuesta("Usatama", true), new Respuesta("Nemequene", false) });
		//Bachue
		preguntas[3] = new Pregunta("�En qu� lugar naci� Bachu�?",
				new Respuesta[] { new Respuesta("Laguna de Iguaque", true), new Respuesta("Laguna de Guatavita", false),
						new Respuesta("Lago de la tota", false), new Respuesta("Laguna de Tibatiquica", false) });
		
		preguntas[4] = new Pregunta("�Qui�n es Bachu�?",
				new Respuesta[] { new Respuesta("La diosa de la luna", false),
						new Respuesta("La diosa del trigo", false), new Respuesta("La madre del pueblo Muisca", true),
						new Respuesta("Una cacique", false) });
		
		preguntas[5] = new Pregunta("�Qu� habilidades transmiti� Bachu� a sus descendientes?",
				new Respuesta[] { new Respuesta("Trabajar las piedras, manipular el fuego, recolectar y cazar alimentos", true), new Respuesta("Ciencia y astronom�a", false),
						new Respuesta("Agricultura y pesca", false), new Respuesta("Pintar y esculpir", false) });
		//Hunzaua
		preguntas[6] = new Pregunta("Hunzahua fue el primer zaque de:",
				new Respuesta[] { new Respuesta("Tunja", true), new Respuesta("Bogot�",false),
						new Respuesta("Tequendama", false), new Respuesta("Cali", false) });
		
		preguntas[7] = new Pregunta("�De qui�n se enamor� Hunzahua?",
				new Respuesta[] { new Respuesta("De su madre", false), new Respuesta("De una diosa", false),
						new Respuesta("De su hermana", true), new Respuesta("De una serpiente", false) });
		
		preguntas[8] = new Pregunta("�En qu� se convirtieron al final Hunzahua y su esposa?",
				new Respuesta[] { new Respuesta("En �rboles", false), new Respuesta("En dioses", false),
						new Respuesta("En piedras", true), new Respuesta("En animales", false) });
		//Goranchancha
				preguntas[9] = new Pregunta("�De qu� piedra preciosa proviene Goranchacha?",
						new Respuesta[] {new Respuesta("Rub�",
										false),new Respuesta("Esmeralda", true),new Respuesta("Zafiro", false),
								new Respuesta("Diamante", false) });
				
				preguntas[10] = new Pregunta("�Por qu� destronaron a Goranchancha?",
						new Respuesta[] {
								new Respuesta("fue acusado de ser el culpable de traer la hambruna y la sequ�a al valle",
										false),new Respuesta("Porque era un mal gobernante", true),
								new Respuesta("Porque los hijos de los dioses no pueden gobernar", false),
								new Respuesta("Porque el pueblo se revel� ante �l", false) });
				preguntas[11] = new Pregunta("Goranchacha es...",
						new Respuesta[] {
								new Respuesta("El hijo del sol",true),new Respuesta("Un Dios", false),new Respuesta("El hijo de la luna", false),
								new Respuesta("Un viejo", false) });
				
		//Nemequene
		preguntas[12] = new Pregunta("�Qu� quiere decir Nemequene?",
				new Respuesta[] { new Respuesta("Hueso de le�n", true), new Respuesta("Gran valent�a",false),
						new Respuesta("Furia del tigre", false), new Respuesta("Reino Pac�fico", false) });
		
		preguntas[13] = new Pregunta("�Cu�l fue la causa de la muerte de Nemequene?",
				new Respuesta[] { new Respuesta("Muri� en medio de una confrontaci�n con el zaque tunjano", true), new Respuesta("Muri� por una enfermedad",false),
						new Respuesta("Muri� al caer por un precipicio", false), new Respuesta("Se desconocen las causas de su muerte", false) });
		
		preguntas[14] = new Pregunta("�Qu� zipasgo hered� Nemequene?",
				new Respuesta[] { new Respuesta("Zipasgo de Bacat�", true), new Respuesta("Zipasgo de Guatavita",false),
						new Respuesta("Zipasgo de Ubat�", false), new Respuesta("Zipasgo de Ubaque", false) });
		//Bochica
		preguntas[15] = new Pregunta("�De qu� manera Bochica salv� al pueblo muizca de la inundaci�n?",
				new Respuesta[] { new Respuesta("Creando el salto de Tequendama", true), new Respuesta("Hizo que el agua se sacara por medio del sol",false),
						new Respuesta("Cre� continentes encima del agua", false), new Respuesta("Convirti� el agua en alimento", false) });
		
		preguntas[16] = new Pregunta("�Qu� semilla le regal� Bochica a la humanidad?",
				new Respuesta[] { new Respuesta("El ma�z", true), new Respuesta("El trigo",false),
						new Respuesta("El fr�jol", false), new Respuesta("El arroz", false) });
		
		preguntas[17] = new Pregunta("�A qui�n confront� Bochica por haber sembrado la semilla del mal?",
				new Respuesta[] { new Respuesta("Chia", true), new Respuesta("Bachue",false),
						new Respuesta("Goranchach�", false), new Respuesta("Nemequene", false) });
		
		
	}

	private SerialPortEventListener comListener = new SerialPortEventListener() {
		@Override
		public void serialEvent(SerialPortEvent arg0) {
			// TODO Auto-generated method stub
			PauseTransition delay = new PauseTransition(Duration.seconds(3));
			try {
				if (arduino.isMessageAvailable()) {
					String opcion = arduino.printMessage();
					int op = Integer.parseInt(opcion);
					voz.stop();
					switch (op) {
					case 1:
						voz.speak("Opci�n A seleccionada");
						delay.setOnFinished(actionEvent -> {
							if (respuestaA.getText().equals(correcta)) {
								puntos = "10pts";
								respuesta = "Respuesta Correcta";
								colorFondo = "#3FF500";
								voz.speak("Respuesta Correcta");
							} else {
								puntos = "0pts";
								respuesta = "Respuesta Incorrecta";
								colorFondo = "#851A1A";
								voz.speak("Respuesta Incorrecta");
							}
							cargarInterfazRespuesta();
						});
						delay.play();

						break;
					case 2:
						voz.speak("Opci�n B seleccionada");
						delay.setOnFinished(actionEvent -> {
							if (respuestaB.getText().equals(correcta)) {
								puntos = "10pts";
								respuesta = "Respuesta Correcta";
								colorFondo = "#3FF500";
								voz.speak("Respuesta Correcta");
							} else {
								puntos = "0pts";
								respuesta = "Respuesta Incorrecta";
								colorFondo = "#851A1A";
								voz.speak("Respuesta Incorrecta");
							}
							cargarInterfazRespuesta();
						});
						delay.play();

						break;
					case 3:
						voz.speak("Opci�n C seleccionada");
						delay.setOnFinished((actionEvent) -> {
							if (respuestaC.getText().equals(correcta)) {
								puntos = "10pts";
								respuesta = "Respuesta Correcta";
								colorFondo = "#3FF500";
								voz.speak("Respuesta Correcta");
							} else {
								puntos = "0pts";
								respuesta = "Respuesta Incorrecta";
								colorFondo = "#851A1A";
								voz.speak("Respuesta Incorrecta");
							}
							cargarInterfazRespuesta();
						});
						delay.play();

						break;
					case 4:
						voz.speak("Opci�n D seleccionada");
						delay.setOnFinished(actionEvent -> {
							if (respuestaD.getText().equals(correcta)) {
								puntos = "10pts";
								respuesta = "Respuesta Correcta";
								colorFondo = "#3FF500";
								voz.speak("Respuesta Correcta");
							} else {
								puntos = "0pts";
								respuesta = "Respuesta Incorrecta";
								colorFondo = "#851A1A";
								voz.speak("Respuesta Incorrecta");
							}
							cargarInterfazRespuesta();
						});
						delay.play();
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
	void verificarRespuestaA(ActionEvent event) {
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		voz.speak("Opci�n A seleccionada");
		delay.setOnFinished((actionEvent) -> {

			if (respuestaA.getText().equals(correcta)) {
				System.out.println("CorrectaA");
				puntos = "10pts";
				respuesta = "Respuesta Correcta";
				colorFondo = "#3FF500";
			} else {
				puntos = "0pts";
				respuesta = "Respuesta Incorrecta";
				colorFondo = "#E72323";
			}
			cargarInterfazRespuesta();
		});
		delay.play();

	}

	@FXML
	void verificarRespuestaB(ActionEvent event) {

		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		voz.speak("Opci�n B seleccionada");
		delay.setOnFinished((actionEvent) -> {

			if (respuestaB.getText().equals(correcta)) {
				System.out.println("CorrectaB");
				puntos = "10pts";
				respuesta = "Respuesta Correcta";
				colorFondo = "#3FF500";
			} else {
				puntos = "0pts";
				respuesta = "Respuesta Incorrecta";
				colorFondo = "#E72323";
			}
			cargarInterfazRespuesta();
		});
		delay.play();

	}

	@FXML
	void verificarRespuestaC(ActionEvent event) {
		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		voz.speak("Opci�n C seleccionada");
		delay.setOnFinished((actionEvent) -> {
			if (respuestaC.getText().equals(correcta)) {
				System.out.println("CorrectaC");
				puntos = "10pts";
				respuesta = "Respuesta Correcta";
				colorFondo = "#3FF500";
			} else {
				puntos = "0pts";
				respuesta = "Respuesta Incorrecta";
				colorFondo = "#E72323";
			}
			cargarInterfazRespuesta();
		});
		delay.play();
		cargarInterfazRespuesta();

	}

	@FXML
	void verificarRespuestaD(ActionEvent event) {

		PauseTransition delay = new PauseTransition(Duration.seconds(3));
		voz.speak("Opci�n D seleccionada");
		delay.setOnFinished((actionEvent) -> {

			if (respuestaD.getText().equals(correcta)) {
				System.out.println("CorrectaD");
				puntos = "10pts";
				respuesta = "Respuesta Correcta";
				colorFondo = "#3FF500";
			} else {
				puntos = "0pts";
				respuesta = "Respuesta Incorrecta";
				colorFondo = "#E72323";
			}
			cargarInterfazRespuesta();
		});
		delay.play();

	}

	@FXML
	public void cargarInterfazRespuesta() {
		try {
			voz.stop();
			arduino.killArduinoConnection();
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/respuesta.fxml"));
			Parent raiz = (Parent) cargador.load();
			ControlRespuesta control = cargador.getController();
			control.setStage(escenarioPrincipal);
			control.cargar(puntos, respuesta, colorFondo, contadorAux, tipo_carta);
			panelRaiz.getChildren().clear();
			panelRaiz.getChildren().add(raiz);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void regresarInicio(ActionEvent event) {
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

	// ___________________________________________________________________________________________________

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

	// Minimizar la aplicaci�n
	@FXML
	void minimizar(ActionEvent event) {
		Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
		stage.setIconified(true);
	}

//Cerrar la aplicaci�n
	@FXML
	void cerrar(ActionEvent event) {
		voz.stop();
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("Est� a punto de cerrar la aplicaci�n");
		alert.setContentText("�Est� seguro de que desea salir?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			System.exit(1);
		}
	}

}
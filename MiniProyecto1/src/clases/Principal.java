package clases;

import controles.ControlVPrincipal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Principal extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			TTS.speak("Bienvenido al juego Cultura muisca al descubierto, "
					+ "Si desea entrar al juego de preguntas coloque la carta de la opción A,"
					+ "Si desea jugar piedra papel o tijera coloque la carta de la opción B");
			FXMLLoader cargador = new FXMLLoader();
			cargador.setLocation(Principal.class.getResource("/vistas/principal.fxml"));
			Parent raiz = (Parent)cargador.load();
			ControlVPrincipal control= cargador.getController();
			Scene escenario = new Scene(raiz); 
			primaryStage.setScene(escenario);
			primaryStage.initStyle(StageStyle.UNDECORATED);	
			control.setStage(primaryStage);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}




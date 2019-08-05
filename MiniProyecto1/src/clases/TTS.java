package clases;

import java.io.IOException;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class TTS {
	private static void speakAux(String text){
		Thread thread = new Thread(() -> {
			try {
				AdvancedPlayer player = new AdvancedPlayer(
						new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw").getMP3Data(text));
						player.play();
			} catch (IOException | JavaLayerException e) {
				e.printStackTrace(); 
			}
		});
		thread.setDaemon(false);
		thread.start();
	}
	
	public static void speak(String text) {
		speakAux(text);
	}
	
	public static void speak(Pregunta p) {
		speakAux(
				"Sección de preguntas, " + p.getPregunta()+ 
				"Opción A. "+ p.getRespuestas()[0] +
				"Opción B. "+ p.getRespuestas()[1] +
				"Opción C. "+ p.getRespuestas()[2] +
				"Opción D. "+ p.getRespuestas()[3] +
				"Use las cartas de respuesta"
		);
	}
}

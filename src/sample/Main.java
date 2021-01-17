package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import sample.enums.Giocatore;
import sample.net.Net;
import sample.scenes.EntryPoint;

public class Main extends Application {
	public static Giocatore giocatore; // Bianco o nero
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		Main.stage = primaryStage;
		
		Main.stage.setTitle("Schaccolati");
		Main.stage.setScene(new Scene(new EntryPoint(), Casella.size * 8.3, Casella.size * 8.3));
		Main.stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
		
		// quando chiudo il programma devo chiudere la connessione col database e/o il processo di stockfish
		Net.resetta();
		StockfishEngine.close();
		if(EntryPoint.scene != null)
			EntryPoint.scene.close();
	}
}

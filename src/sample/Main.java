package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import sample.enums.Giocatore;
import sample.net.Net;
import sample.scenes.BaseScene;
import sample.scenes.EntryPoint;

import javax.sound.midi.Soundbank;
import javax.sound.midi.SoundbankResource;

public class Main extends Application {
	public static Giocatore giocatore;
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage){
		Main.stage = primaryStage;
		
		Main.stage.setTitle("Schaccolati");
		Main.stage.setScene(new Scene(new EntryPoint(), Casella.size * 8.4, Casella.size * 8.4));
		Main.stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
		
		Net.resetta();
		StockfishEngine.close();
		if(EntryPoint.scene != null)
			EntryPoint.scene.close();
	}
}

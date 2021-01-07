package sample.scenes;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

import sample.Casella;
import sample.forme.Switch;
import sample.enums.Giocatore;
import sample.Main;

public class EntryPoint extends Group {
	public static boolean pveOn = false;
	public static BaseScene scene;
	
	public EntryPoint(){
		Switch sw = new Switch();
		sw.setMaxSize(180, 40);
		sw.setMinSize(180, 40);
		sw.setTranslateY(140);
		sw.setTranslateX(35);
		
		// Bottoni
		Button b1 = new Button("Bianco");
		Button b2 = new Button("Nero");
		
		b1.setFont(Font.font(30));
		b2.setFont(Font.font(30));
		
		b1.setMinSize(300, 150);
		b2.setMinSize(300, 150);
		
		b1.setOnMouseClicked((MouseEvent e) -> {
			Main.giocatore = Giocatore.BIANCO;
			if(pveOn){
				EntryPoint.scene = new PvE();
				Main.stage.setScene(new Scene(EntryPoint.scene, Casella.size * 8.4, Casella.size * 8.4));
			}
			else {
				EntryPoint.scene = new PvP();
				Main.stage.setScene(new Scene(EntryPoint.scene, Casella.size * 8.4, Casella.size * 8.4));
			}
		});
		b2.setOnMouseClicked((MouseEvent e) -> {
			Main.giocatore = Giocatore.NERO;
			if(pveOn){
				EntryPoint.scene = new PvE();
				Main.stage.setScene(new Scene(EntryPoint.scene, Casella.size * 8.4, Casella.size * 8.4));
			}
			else {
				EntryPoint.scene = new PvP();
				Main.stage.setScene(new Scene(EntryPoint.scene, Casella.size * 8.4, Casella.size * 8.4));
			}
		});
		
		HBox box = new HBox();
		box.getChildren().add(b1);
		box.getChildren().add(b2);
		box.setAlignment(Pos.CENTER);
		box.setSpacing(50);
		box.setTranslateX(35);
		box.setTranslateY(200);
		// *****************************
		
		this.getChildren().add(sw);
		this.getChildren().add(box);
	}
}


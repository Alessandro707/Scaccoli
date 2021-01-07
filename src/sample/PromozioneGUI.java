package sample;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import sample.Pezzi.*;
import sample.enums.Colore;
import sample.enums.TipoPezzo;
import sample.scenes.BaseScene;
import sample.scenes.EntryPoint;

public class PromozioneGUI extends VBox { // TODO: quando avviene una promozione devo mandarlo all'altro giocatore
	
	public PromozioneGUI(@NotNull Pezzo pezzo){
		this.setBackground(new Background(new BackgroundFill(Paint.valueOf("#ffffffff"), CornerRadii.EMPTY, Insets.EMPTY)));
		this.setMaxSize(Casella.size * 4, Casella.size * 1.2);
		this.setMinSize(Casella.size * 4, Casella.size * 1.2);
		
		Text text = new Text("Promozione: ");
		text.setFont(new Font(15));
		this.getChildren().add(text);
		
		HBox hBox = new HBox();

		int colore = Main.giocatore.ordinal();
		ImageView regina = new ImageView(Pezzo.imgs[colore][Pezzo.indRegina]); // REGINA
		regina.setOnMouseClicked((MouseEvent e) -> {
			for(int i = 0; i < BaseScene.pezzi.length; i++){
				if(BaseScene.pezzi[i].equals(pezzo)) {
					BaseScene.pezzi[i] = new Regina(pezzo.getColore(), pezzo.getPezzoX(), pezzo.getPezzoY());
					BaseScene.caselle[BaseScene.pezzi[i].getPezzoY()][BaseScene.pezzi[i].getPezzoX()].setPezzo(BaseScene.pezzi[i]);
					BaseScene.calcMinacce();
					break;
				}
			}
			EntryPoint.scene.getChildren().remove(this);
		});
		hBox.getChildren().add(regina);
		ImageView torre = new ImageView(Pezzo.imgs[colore][Pezzo.indTorre]); // TORRE
		torre.setOnMouseClicked((MouseEvent e) -> {
			for(int i = 0; i < BaseScene.pezzi.length; i++){
				if(BaseScene.pezzi[i].equals(pezzo)) {
					BaseScene.pezzi[i] = new Torre(pezzo.getColore(), pezzo.getPezzoX(), pezzo.getPezzoY());
					BaseScene.caselle[BaseScene.pezzi[i].getPezzoY()][BaseScene.pezzi[i].getPezzoX()].setPezzo(BaseScene.pezzi[i]);
					BaseScene.calcMinacce();
					break;
				}
			}
			EntryPoint.scene.getChildren().remove(this);
		});
		hBox.getChildren().add(torre);
		ImageView alfiere = new ImageView(Pezzo.imgs[colore][Pezzo.indAlfiere]); // ALFIERE
		alfiere.setOnMouseClicked((MouseEvent e) -> {
			for(int i = 0; i < BaseScene.pezzi.length; i++){
				if(BaseScene.pezzi[i].equals(pezzo)) {
					BaseScene.pezzi[i] = new Alfiere(pezzo.getColore(), pezzo.getPezzoX(), pezzo.getPezzoY());
					BaseScene.caselle[BaseScene.pezzi[i].getPezzoY()][BaseScene.pezzi[i].getPezzoX()].setPezzo(BaseScene.pezzi[i]);
					BaseScene.calcMinacce();
					break;
				}
			}
			EntryPoint.scene.getChildren().remove(this);
		});
		hBox.getChildren().add(alfiere);
		ImageView cavallo = new ImageView(Pezzo.imgs[colore][Pezzo.indCavallo]); // CAVALLO
		cavallo.setOnMouseClicked((MouseEvent e) -> {
			for(int i = 0; i < BaseScene.pezzi.length; i++){
				if(BaseScene.pezzi[i].equals(pezzo)) {
					BaseScene.pezzi[i] = new Cavallo(pezzo.getColore(), pezzo.getPezzoX(), pezzo.getPezzoY());
					BaseScene.caselle[BaseScene.pezzi[i].getPezzoY()][BaseScene.pezzi[i].getPezzoX()].setPezzo(BaseScene.pezzi[i]);
					BaseScene.calcMinacce();
					break;
				}
			}
			EntryPoint.scene.getChildren().remove(this);
		});
		hBox.getChildren().add(cavallo);
		
		this.getChildren().add(hBox);
	}
	
}

package sample;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sample.scenes.EntryPoint;

public class StockfishPathInputGUI extends VBox {
	public boolean shouldClose = false;
	
	public StockfishPathInputGUI(){
		TextField textField = new TextField();
		textField.setPromptText("Inserisci percorso del motore scacchistico");
		textField.setOnKeyPressed((KeyEvent e) ->{
			if(e.getCode().equals(KeyCode.ENTER)){
				if(!textField.getText().equals("")) {
					StockfishEngine.setPath(textField.getText());
					this.shouldClose = true;
					EntryPoint.scene.getChildren().remove(this);
				}
			}
		});
		this.getChildren().add(textField);
		
		Text text = new Text("Motori supportati:\n-Stockfish 1.0");
		this.getChildren().add(text);
	}
}

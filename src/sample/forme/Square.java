package sample.forme;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import sample.Casella;

public class Square extends Rectangle {
	public Square(){
		this.setWidth(Casella.size - 2);
		this.setHeight(Casella.size - 2);
		this.setArcWidth(15);
		this.setArcHeight(15);
		this.setStrokeWidth(2);
		
		this.setStroke(Color.RED);
		this.setFill(Paint.valueOf("#00000000"));
	}
}

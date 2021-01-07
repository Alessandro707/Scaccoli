package sample.forme;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Dot extends Circle {
	public Dot(Color color){
		this.setRadius(7);
		this.setFill(color);
	}
}

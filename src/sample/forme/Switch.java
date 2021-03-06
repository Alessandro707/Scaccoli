package sample.forme;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import sample.scenes.EntryPoint;

// preso da Github che non ho voglia di far la grafica e volevo sapere come si facessero gli interruttori in javafx
public class Switch extends HBox {
	private final Label label = new Label();
	private final Button button = new Button();
	
	private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(false);
	public SimpleBooleanProperty switchOnProperty() { return switchedOn; }
	
	private void init() {
		
		label.setText("PvP");
		
		getChildren().addAll(label, button);
		button.setOnAction((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		label.setOnMouseClicked((e) -> {
			switchedOn.set(!switchedOn.get());
		});
		setStyle();
		bindProperties();
	}
	
	private void setStyle() {
		//Default Width
		setWidth(80);
		label.setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: grey; -fx-text-fill:black; -fx-background-radius: 4;");
		setAlignment(Pos.CENTER_LEFT);
	}
	
	private void bindProperties() {
		label.prefWidthProperty().bind(widthProperty().divide(2));
		label.prefHeightProperty().bind(heightProperty());
		button.prefWidthProperty().bind(widthProperty().divide(2));
		button.prefHeightProperty().bind(heightProperty());
	}
	
	public Switch() {
		init();
		switchedOn.addListener((a,b,c) -> {
			if (c) {
				label.setText("PvE");
				
				EntryPoint.pveOn = true;
				
				label.toFront();
			}
			else {
				label.setText("PvP");
				
				EntryPoint.pveOn = false;
				
				button.toFront();
			}
		});
	}
}
package sample;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import sample.Pezzi.Pezzo;
import sample.enums.Colonna;
import sample.enums.TipoPezzo;
import sample.forme.Dot;
import sample.forme.Square;
import sample.scenes.BaseScene;
import sample.scenes.EntryPoint;

public class Casella extends StackPane {
	static public final int size = 90;
	
	public final int riga; public final Colonna colonna;
	public final Rectangle bg = new Rectangle();
	public final Dot dot;
	public final Square contrno = new Square();
	private Pezzo pezzo = null;
	
	public static Casella lastCasella = null; // ultima casella selezionata
	
	public Casella(int riga, int colonna){
		this.riga = riga;
		this.colonna = Colonna.values()[colonna];
		this.bg.setHeight(Casella.size);
		this.bg.setWidth(Casella.size);
		
		if((riga + colonna) % 2 == 0){
			this.bg.setFill(Color.BLACK);
			this.dot = new Dot(Color.LIGHTGRAY);
		}
		else{
			this.bg.setFill(Color.WHITE);
			this.dot = new Dot(Color.GRAY);
		}
		
		this.getChildren().add(this.bg);
		
		this.setOnMouseClicked((MouseEvent e)->{
			if(!BaseScene.scaccoMatto) // se non è scacco matto chiamo la funzione che gestisce il click, che dipende se siamo in PvP o PvE
				EntryPoint.scene.mouseCallback(this);
		});
	}
	
	public Pezzo getPezzo(){
		return this.pezzo;
	}
	
	// imposto il pezzo su questa casella, cambiando anche le coordinate del pezzo e
	// se si tratta di un re specifico che il re è ora in questa casella
	public void setPezzo(Pezzo pezzo){
		this.getChildren().remove(this.pezzo);
		this.pezzo = pezzo;
		
		if(pezzo == null){
			return;
		}
		
		pezzo.setPezzoX(this.colonna.ordinal());
		pezzo.setPezzoY(this.riga);
		
		this.getChildren().add(this.pezzo); // ERROR: pezzo non è null, ne il getColore(), getChildren ha size 1, in ogni caso non adda il pezzo, pezzo di merda
		
		if(this.pezzo.getColore().ordinal() == Main.giocatore.ordinal() && this.pezzo.getTipoPezzo().equals(TipoPezzo.RE))
			BaseScene.re = this;
	}
	
	// debug
	public String infoCasella(){
		String res = "Minacce pedoniche: \n";
		
		for(Pezzo p : BaseScene.pezzi)
			for(Casella c : p.getMinacciaPedone())
				if(this.equals(c))
					res = res.concat("    " + p.getTipoPezzo().toString() + " " + p.getColore().toString() + " " + p.getPezzoX() + "," + p.getPezzoY() + "\n");
				
		if(res.length() == "Minacce: \n".length())
			res = res.concat("------\n");
		
		res = res.concat("Can Go: \n");
		for(Pezzo p : BaseScene.pezzi)
			for(Casella c : p.getCanGo())
				if(this.equals(c))
					res = res.concat("    " + p.getTipoPezzo().toString() + " " + p.getColore().toString() + " " + p.getPezzoX() + "," + p.getPezzoY() + "\n");
		
		res = res.concat("------\n");
		
		return res;
	}
}

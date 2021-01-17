package sample.Pezzi;

import org.jetbrains.annotations.NotNull;
import sample.Casella;
import sample.PromozioneGUI;
import sample.enums.Colore;
import sample.enums.TipoPezzo;
import sample.scenes.BaseScene;
import sample.scenes.EntryPoint;

import java.util.ArrayList;

public class Pedone extends Pezzo {
	
	public Pedone(@NotNull Colore colore, int x, int y){
		super(x, y);
		this.setTipoPezzo(TipoPezzo.PEDONE);
		this.setImage(Pezzo.imgs[colore.ordinal()][this.getTipoPezzo().ordinal()]);
		this.setColore(colore);
	}
	
	@Override
	public ArrayList<Casella> calcCanGo(){
		ArrayList<Casella> res = new ArrayList<>();
		
		this.getMinacciaPedone().clear();
		
		// variabili per dire se il pezzo si muove verso l'alto o il basso (relativamente alla matrice di
		// caselle, non al modo in cui vengono visualizzate)
		int v = -1; // per semplice movimento
		int v2 = 6; // per avanzamento di 2 alla prima mossa (TODO: sostituire con Pezzo.moved)
		if(this.getColore().equals(Colore.BIANCO)) {
			v = 1;
			v2 = 1;
		}
		
		if (this.getPezzoY() + v >= 8 || this.getPezzoY() + v < 0)
			return res;
		
		if(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX()].getPezzo() == null) {
			res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX()]);
			if (this.getPezzoY() == v2 && BaseScene.caselle[this.getPezzoY() + 2 * v][this.getPezzoX()].getPezzo() == null)
				res.add(BaseScene.caselle[this.getPezzoY() + 2 * v][this.getPezzoX()]);
		}
		
		if(this.getPezzoX() - 1 >= 0 && BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() - 1].getPezzo() != null &&
				!BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() - 1].getPezzo().getColore().equals(this.getColore()))
			res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() - 1]);
		
		if(this.getPezzoX() + 1 < 8 && BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() + 1].getPezzo() != null &&
				!BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() + 1].getPezzo().getColore().equals(this.getColore()))
			res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() + 1]);
		
		// TODO: enpassate!1!1!
		
		return res;
	}
	
	@Override
	public void onMove() {
		super.onMove();
		if(this.getPezzoY() == 7 || this.getPezzoY() == 0){
			EntryPoint.scene.getChildren().add(new PromozioneGUI(this));
		}
	}
}

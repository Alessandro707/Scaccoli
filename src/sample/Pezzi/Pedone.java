package sample.Pezzi;

import org.jetbrains.annotations.NotNull;
import sample.Casella;
import sample.Main;
import sample.Mossa;
import sample.PromozioneGUI;
import sample.enums.Colore;
import sample.enums.Giocatore;
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
		int v = -1; // per dire se il movimento avviene verso l'alto o il basso (della matrice)
		if(this.getColore().equals(Colore.BIANCO)) {
			v = 1;
		}
		
		if (this.getPezzoY() + v >= 8 || this.getPezzoY() + v < 0)
			return res;
		
		if(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX()].getPezzo() == null) {
			res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX()]);
			if (!this.mosso && BaseScene.caselle[this.getPezzoY() + 2 * v][this.getPezzoX()].getPezzo() == null)
				res.add(BaseScene.caselle[this.getPezzoY() + 2 * v][this.getPezzoX()]);
		}
		
		
		if(this.getPezzoX() - 1 >= 0 && BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() - 1].getPezzo() != null &&
				!BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() - 1].getPezzo().getColore().equals(this.getColore()))
			res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() - 1]);
		
		if(this.getPezzoX() + 1 < 8 && BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() + 1].getPezzo() != null &&
				!BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() + 1].getPezzo().getColore().equals(this.getColore()))
			res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() + 1]);
		
		// TODO: enpassate!1!1!
		/*
		if(BaseScene.mosse.size() == 0)
			return res;
		// non funziona troppo bene questo enpassant (TODO: da manadre all'altro player)
		Mossa m = BaseScene.mosse.get(BaseScene.mosse.size() - 1);
		if(this.getPezzoX() < 7 && BaseScene.caselle[this.getPezzoY()][getPezzoX() + 1].getPezzo() != null &&
				BaseScene.caselle[this.getPezzoY()][this.getPezzoX() + 1].getPezzo().equals(m.getPezzo()) &&
				m.getPezzo().getTipoPezzo().equals(TipoPezzo.PEDONE) && Math.abs(m.getStartY() - m.getDestY()) == 2)
			res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() + 1]);
		if(this.getPezzoX() > 0 && BaseScene.caselle[this.getPezzoY()][getPezzoX() - 1].getPezzo() != null &&
				BaseScene.caselle[this.getPezzoY()][this.getPezzoX() - 1].getPezzo().equals(m.getPezzo()) &&
				m.getPezzo().getTipoPezzo().equals(TipoPezzo.PEDONE) && Math.abs(m.getStartY() - m.getDestY()) == 2)
			res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() - 1]);
		*/
		
		return res;
	}
	
	@Override
	public ArrayList<Casella> calcMinaccePedone() {
		ArrayList<Casella> res = new ArrayList<>();
		
		int v = -1; // per semplice movimento
		if(this.getColore().equals(Colore.BIANCO)) {
			v = 1;
		}
		
		if(this.getPezzoX() > 0)
			res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() - 1]);
		if(this.getPezzoX() < 7)
		res.add(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX() + 1]);
		
		return res;
	}
	
	@Override
	public void onMove() {
		super.onMove();
		if(this.getPezzoY() == 7 || this.getPezzoY() == 0){
			EntryPoint.scene.getChildren().add(new PromozioneGUI(this));
		}
		
		/*
		// la mossa appena fatta da questo pedone
		Mossa m = BaseScene.mosse.get(BaseScene.mosse.size() - 1);
			System.out.println("1");
		if(!m.getStartX().equals(m.getDestX()) && m.getPezzoMangiato() == null){
			System.out.println("2");
			int v = 1;
			if(Main.giocatore.equals(Giocatore.BIANCO)){
				v = -1;
			}
			m.setPezzoMangiato(BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX()].getPezzo());
			System.out.println("3");
			BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX()].getPezzo().mangiato = true;
			System.out.println("4");
			BaseScene.caselle[this.getPezzoY() + v][this.getPezzoX()].setPezzo(null);
			System.out.println("5");
		}
		*/
	}
}

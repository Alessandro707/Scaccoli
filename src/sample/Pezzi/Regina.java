package sample.Pezzi;

import org.jetbrains.annotations.NotNull;
import sample.Casella;
import sample.enums.Colore;
import sample.enums.TipoPezzo;
import sample.scenes.BaseScene;

import java.util.ArrayList;

public class Regina extends Pezzo{
	
	public Regina(@NotNull Colore colore, int x, int y){
		super(x, y);
		this.setTipoPezzo(TipoPezzo.REGINA);
		this.setImage(Pezzo.imgs[colore.ordinal()][this.getTipoPezzo().ordinal()]);
		this.setColore(colore);
	}
	
	
	@Override
	public ArrayList<Casella> calcCanGo() {
		ArrayList<Casella> res = this.alfiere(); // la regina è Torre U Alfiere
		res.addAll(this.torre());
		return res;
	}
	
	private ArrayList<Casella> alfiere(){
		ArrayList<Casella> res = new ArrayList<>();
		
		boolean flagL = true, flagR = true, flagU = true, flagD = true;
		for (int i = 1; i < 8; i++) {
			if (flagL) {
				if (this.getPezzoX() - i >= 0 && this.getPezzoY() - i >= 0) {
					if (BaseScene.caselle[this.getPezzoY() - i][this.getPezzoX() - i].getPezzo() != null) {
						res.add(BaseScene.caselle[this.getPezzoY() - i][this.getPezzoX() - i]);
						flagL = false;
					} else
						res.add(BaseScene.caselle[this.getPezzoY() - i][this.getPezzoX() - i]);
				} else
					flagL = false;
			}
			if (flagR) {
				if (this.getPezzoX() + i < 8 && this.getPezzoY() + i < 8) {
					if (BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX() + i].getPezzo() != null) {
						res.add(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX() + i]);
						flagR = false;
					} else
						res.add(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX() + i]);
				} else
					flagR = false;
			}
			
			if (flagU) {
				if (this.getPezzoY() - i >= 0 && this.getPezzoX() + i < 8) {
					if (BaseScene.caselle[this.getPezzoY() - i][this.getPezzoX() + i].getPezzo() != null) {
						res.add(BaseScene.caselle[this.getPezzoY() - i][this.getPezzoX() + i]);
						flagU = false;
					} else
						res.add(BaseScene.caselle[this.getPezzoY() - i][this.getPezzoX() + i]);
				} else
					flagU = false;
			}
			if (flagD) {
				if (this.getPezzoY() + i < 8 && this.getPezzoX() - i >= 0) {
					if (BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX() - i].getPezzo() != null) {
						res.add(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX() - i]);
						flagD = false;
					} else
						res.add(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX() - i]);
				} else
					flagD = false;
			}
			
			if (!(flagD || flagL || flagR || flagU))
				break;
		}
		return res;
	}
	
	private ArrayList<Casella> torre(){
		ArrayList<Casella> res = new ArrayList<>();
		
		boolean flagL = true, flagR = true, flagU = true, flagD = true;
		for(int i = 1; i < 8; i++){
			if(flagL){
				if(this.getPezzoX() - i >= 0){
					if(BaseScene.caselle[this.getPezzoY()][this.getPezzoX() - i].getPezzo() != null){
						res.add(BaseScene.caselle[this.getPezzoY()][this.getPezzoX() - i]);
						flagL = false;
					}
					else
						res.add(BaseScene.caselle[this.getPezzoY()][this.getPezzoX() - i]);
				}
				else
					flagL = false;
			}
			if(flagR){
				if(this.getPezzoX() + i < 8){
					if(BaseScene.caselle[this.getPezzoY()][this.getPezzoX() + i].getPezzo() != null){
						res.add(BaseScene.caselle[this.getPezzoY()][this.getPezzoX() + i]);
						flagR = false;
					}
					else
						res.add(BaseScene.caselle[this.getPezzoY()][this.getPezzoX() + i]);
				}
				else
					flagR = false;
			}
			
			if(flagU){
				if(this.getPezzoY() - i >= 0){
					if(BaseScene.caselle[this.getPezzoY() - i][this.getPezzoX()].getPezzo() != null){
						res.add(BaseScene.caselle[this.getPezzoY() - i][this.getPezzoX()]);
						flagU = false;
					}
					else
						res.add(BaseScene.caselle[this.getPezzoY() - i][this.getPezzoX()]);
				}
				else
					flagU = false;
			}
			if(flagD){
				if(this.getPezzoY() + i < 8){
					if(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX()].getPezzo() != null){
						res.add(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX()]);
						flagD = false;
					}
					else
						res.add(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX()]);
				}
				else
					flagD = false;
			}
			
			if(!(flagD || flagL || flagR || flagU))
				break;
		}
		return res;
	}
}

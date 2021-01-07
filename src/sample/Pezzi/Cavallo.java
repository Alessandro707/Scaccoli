package sample.Pezzi;

import org.jetbrains.annotations.NotNull;
import sample.Casella;
import sample.enums.Colore;
import sample.enums.TipoPezzo;
import sample.scenes.BaseScene;

import java.util.ArrayList;

public class Cavallo extends Pezzo {
	
	public Cavallo(@NotNull Colore colore, int x, int y){
		super(x, y);
		this.setTipoPezzo(TipoPezzo.CAVALLO);
		this.setImage(Pezzo.imgs[colore.ordinal()][this.getTipoPezzo().ordinal()]);
		this.setColore(colore);
	}
	
	@Override
	public ArrayList<Casella> calcCanGo() {
		ArrayList<Casella> res = new ArrayList<>();
		
		if(this.getPezzoY() - 2 >= 0 && this.getPezzoX() - 1 >= 0)
			res.add(BaseScene.caselle[this.getPezzoY() - 2][this.getPezzoX() - 1]);
		if(this.getPezzoY() - 2 >= 0 && this.getPezzoX() + 1 < 8)
			res.add(BaseScene.caselle[this.getPezzoY() - 2][this.getPezzoX() + 1]);
		if(this.getPezzoY() + 2 < 8 && this.getPezzoX() - 1 >= 0)
			res.add(BaseScene.caselle[this.getPezzoY() + 2][this.getPezzoX() - 1]);
		if(this.getPezzoY() + 2 < 8 && this.getPezzoX() + 1 < 8)
			res.add(BaseScene.caselle[this.getPezzoY() + 2][this.getPezzoX() + 1]);
		
		if(this.getPezzoY() - 1 >= 0 && this.getPezzoX() - 2 >= 0)
			res.add(BaseScene.caselle[this.getPezzoY() - 1][this.getPezzoX() - 2]);
		if(this.getPezzoY() - 1 >= 0 && this.getPezzoX() + 2 < 8)
			res.add(BaseScene.caselle[this.getPezzoY() - 1][this.getPezzoX() + 2]);
		if(this.getPezzoY() + 1 < 8 && this.getPezzoX() - 2 >= 0)
			res.add(BaseScene.caselle[this.getPezzoY() + 1][this.getPezzoX() - 2]);
		if(this.getPezzoY() + 1 < 8 && this.getPezzoX() + 2 < 8)
			res.add(BaseScene.caselle[this.getPezzoY() + 1][this.getPezzoX() + 2]);
		
		return res;
	}
}

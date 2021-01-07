package sample.Pezzi;

import org.jetbrains.annotations.NotNull;
import sample.Casella;
import sample.enums.Colore;
import sample.enums.TipoPezzo;
import sample.scenes.BaseScene;

import java.util.ArrayList;

public class Re extends Pezzo {
	
	public Re(@NotNull Colore colore, int x, int y){
		super(x, y);
		this.setTipoPezzo(TipoPezzo.RE);
		this.setImage(Pezzo.imgs[colore.ordinal()][this.getTipoPezzo().ordinal()]);
		this.setColore(colore);
	}
	
	@Override
	public ArrayList<Casella> calcCanGo() {
		ArrayList<Casella> res = new ArrayList<>();
		
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(i == 0 && j == 0)
					continue;
				if(this.getPezzoY() + i < 0 || this.getPezzoY() + i >= 8)
					continue;
				if(this.getPezzoX() + j < 0 || this.getPezzoX() + j >= 8)
					continue;
				
				boolean flag = false;
				for(Pezzo p : BaseScene.pezzi){
					if(!p.getColore().equals(this.getColore())){
						if((p.getTipoPezzo().equals(TipoPezzo.PEDONE) && p.getMinacciaPedone().contains(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX() + j])) ||
								(!p.getTipoPezzo().equals(TipoPezzo.PEDONE) && p.getCanGo().contains(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX() + j]))) {
							flag = true;
							break;
						}
					}
				}
				if(flag)
					continue;
				
				res.add(BaseScene.caselle[this.getPezzoY() + i][this.getPezzoX() + j]);
			}
		}
		
		return res;
	}
	
}

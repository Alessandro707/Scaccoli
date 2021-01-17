package sample.Pezzi;

import org.jetbrains.annotations.NotNull;
import sample.Casella;
import sample.Mossa;
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
		
		// parte dell'arrocco:
		// controllo che non ci siano pezzi tra il re e la torre e che nessun pezzo avversario minacci le caselle attraversate dal re
		if(!this.mosso && !BaseScene.sottoScacco){
			if(this.getColore().equals(Colore.BIANCO)) {
				Casella c = BaseScene.caselle[0][0];
				if (c.getPezzo() != null && c.getPezzo().getTipoPezzo().equals(TipoPezzo.TORRE) && c.getPezzo().getColore().equals(this.getColore()) && !c.getPezzo().mosso){
					if(BaseScene.caselle[0][1].getPezzo() == null && BaseScene.caselle[0][2].getPezzo() == null && BaseScene.caselle[0][3].getPezzo() == null){
						boolean b = true;
						for(Pezzo p : BaseScene.pezzi){
							if(!p.getColore().equals(this.getColore())){
								if(p.getCanGo().contains(BaseScene.caselle[0][1]) || p.getCanGo().contains(BaseScene.caselle[0][2])){
									b = false;
									break;
								}
							}
						}
						if(b)
							res.add(BaseScene.caselle[0][2]);
					}
				}
				c = BaseScene.caselle[0][7];
				if (c.getPezzo() != null && c.getPezzo().getTipoPezzo().equals(TipoPezzo.TORRE) && c.getPezzo().getColore().equals(this.getColore()) && !c.getPezzo().mosso){
					if(BaseScene.caselle[0][5].getPezzo() == null && BaseScene.caselle[0][6].getPezzo() == null){
						boolean b = true;
						for(Pezzo p : BaseScene.pezzi){
							if(!p.getColore().equals(this.getColore())){
								if(p.getCanGo().contains(BaseScene.caselle[0][5]) || p.getCanGo().contains(BaseScene.caselle[0][6])){
									b = false;
									break;
								}
							}
						}
						if(b)
							res.add(BaseScene.caselle[0][6]);
					}
				}
			}
			else {
				Casella c = BaseScene.caselle[7][0];
				if (c.getPezzo() != null && c.getPezzo().getTipoPezzo().equals(TipoPezzo.TORRE) && c.getPezzo().getColore().equals(this.getColore()) && !c.getPezzo().mosso){
					if(BaseScene.caselle[7][1].getPezzo() == null && BaseScene.caselle[7][2].getPezzo() == null && BaseScene.caselle[7][3].getPezzo() == null){
						boolean b = true;
						for(Pezzo p : BaseScene.pezzi){
							if(!p.getColore().equals(this.getColore())){
								if(p.getCanGo().contains(BaseScene.caselle[7][1]) || p.getCanGo().contains(BaseScene.caselle[7][2])){
									b = false;
									break;
								}
							}
						}
						if(b)
							res.add(BaseScene.caselle[7][2]);
					}
				}
				c = BaseScene.caselle[7][7];
				if (c.getPezzo() != null && c.getPezzo().getTipoPezzo().equals(TipoPezzo.TORRE) && c.getPezzo().getColore().equals(this.getColore()) && !c.getPezzo().mosso){
					if(BaseScene.caselle[7][5].getPezzo() == null && BaseScene.caselle[7][6].getPezzo() == null){
						boolean b = true;
						for(Pezzo p : BaseScene.pezzi){
							if(!p.getColore().equals(this.getColore())){
								if(p.getCanGo().contains(BaseScene.caselle[7][5]) || p.getCanGo().contains(BaseScene.caselle[7][6])){
									b = false;
									break;
								}
							}
						}
						if(b)
							res.add(BaseScene.caselle[7][6]);
					}
				}
			}
		}
		
		return res;
	}
	
	@Override
	public void onMove() {
		super.onMove();
		
		// se la mossa è di due caselle significa che è stato fatto un arrocco e devo spostare la torre
		Mossa m = BaseScene.mosse.get(BaseScene.mosse.size() - 1);
		if(Math.abs(m.getStartX().ordinal() - m.getDestX().ordinal()) == 2){
			if(this.getColore().equals(Colore.BIANCO)){
				if(m.getStartX().ordinal() > m.getDestX().ordinal()){
					BaseScene.caselle[0][0].getPezzo().onMove(); // torre
					BaseScene.caselle[0][3].setPezzo(BaseScene.caselle[0][0].getPezzo());
					BaseScene.caselle[0][0].setPezzo(null);
				}
				else{
					BaseScene.caselle[0][7].getPezzo().onMove();
					BaseScene.caselle[0][5].setPezzo(BaseScene.caselle[0][7].getPezzo());
					BaseScene.caselle[0][7].setPezzo(null);
				}
			}
			else {
				if(m.getStartX().ordinal() > m.getDestX().ordinal()){
					BaseScene.caselle[7][0].getPezzo().onMove();
					BaseScene.caselle[7][3].setPezzo(BaseScene.caselle[7][0].getPezzo());
					BaseScene.caselle[7][0].setPezzo(null);
				}
				else{
					BaseScene.caselle[7][7].getPezzo().onMove();
					BaseScene.caselle[7][5].setPezzo(BaseScene.caselle[7][7].getPezzo());
					BaseScene.caselle[7][7].setPezzo(null);
				}
			}
		}
	}
}

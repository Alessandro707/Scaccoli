package sample;

import sample.Pezzi.Pezzo;
import sample.enums.Colonna;
import sample.enums.Colore;
import sample.enums.TipoPezzo;
import sample.scenes.PvE;

public class Mossa {
	private final TipoPezzo tipoPezzo;
	private final Pezzo pezzo;
	private final Colore colore;
	private final int destY;
	private final int startY;
	private final Colonna destX;
	private final Colonna startX;
	private Pezzo pezzoMangiato;
	
	public Mossa(Pezzo pezzo, Colonna startX, int startY, Colonna destX, int destY, Pezzo pezzoMangiato){
		this.pezzo = pezzo;
		this.tipoPezzo = pezzo.getTipoPezzo();
		this.colore = pezzo.getColore();
		this.startX = startX;
		this.startY = startY;
		this.destX = destX;
		this.destY = destY;
		this.pezzoMangiato = pezzoMangiato;
	}
	
	public String toString(){
		return this.pezzo.getTipoPezzo().toString() + " " + this.colore.toString() + " da: (" + this.startX.toString() + " " + (this.startY + 1) + ") a: (" +
				this.destX.toString() + " " + (this.destY + 1) + ")";
	}
	
	// formatto la stringa della mossa in modo comprensibile da Stockfish (per dirgli che la mossa è stata fatta)
	public String toStockfish(){
		String res = "position moves ";
		
		Mossa m = PvE.mosse.get(PvE.mosse.size() - 1);
		res = res.concat(m.getStartX().toString().toLowerCase() + (m.getStartY() + 1) + m.getDestX().toString().toLowerCase() + (m.getDestY() + 1));
		
		return res;
	}
	
	public Pezzo getPezzo() {
		return pezzo;
	}
	
	public TipoPezzo getTipoPezzo() {
		return tipoPezzo;
	}
	
	public Colore getColore() {
		return colore;
	}
	
	public int getDestY() {
		return destY;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public Colonna getDestX() {
		return destX;
	}
	
	public Colonna getStartX() {
		return startX;
	}
	
	public Pezzo getPezzoMangiato() { return pezzoMangiato; }
	
	public void setPezzoMangiato(Pezzo pezzo) {
		if(this.tipoPezzo.equals(TipoPezzo.PEDONE))
			this.pezzoMangiato = pezzo;
	}
}

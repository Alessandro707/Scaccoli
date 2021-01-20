package sample.scenes;

import sample.Casella;
import sample.Main;
import sample.Mossa;
import sample.StockfishEngine;
import sample.enums.Giocatore;

import java.util.Timer;
import java.util.TimerTask;

public class PvE extends BaseScene {
	private final Timer timer = new Timer();
	
	public PvE(){
		super();
		
		// se io sono nero Stockfish deve fare la prima mossa
		if(Main.giocatore.equals(Giocatore.NERO)) {
			this.timer.schedule(new TimerTask() {
				@Override
				public void run() {
					StockfishEngine.get().elaboraMossa();
				}
			}, 1000);
		}
	}
	
	@Override
	public void mouseCallback(Casella casella){
		if(!BaseScene.playerTurn) {
			return;
		}
		// non ha senso cliccare una casella vuota se non ho prima selezionato un pezzo
		if((Casella.lastCasella == null || Casella.lastCasella.getPezzo() == null) && casella.getPezzo() == null)
			return;
		
		// click sulla casella di partenza
		if(Casella.lastCasella == null || Casella.lastCasella.getPezzo() == null) {
			if(casella.getPezzo().getColore().ordinal() != Main.giocatore.ordinal())
				return;
			
			Casella.lastCasella = casella;
			
			// aggiungo dei puntini lungo il percorso del pezzo, o dei riquadri nelle caselle occupate da un pezzo avversario
			for(Casella c : Casella.lastCasella.getPezzo().getCanGo()){
				if(c.getPezzo() == null && !c.getChildren().contains(c.dot))
					c.getChildren().add(c.dot);
				else if(!c.getPezzo().getColore().equals(casella.getPezzo().getColore()) && !c.getChildren().contains(c.contrno))
					c.getChildren().add(c.contrno);
			}
			
			Casella.lastCasella.setOpacity(0.75);
		}
		else {
			// cliccata la casella di destinazione verifico che sia una mossa fattibile dal pezzo
			if (Casella.lastCasella.getPezzo().getCanGo().contains(casella) && (casella.getPezzo() == null ||
					!Casella.lastCasella.getPezzo().getColore().equals(casella.getPezzo().getColore()))) {
				BaseScene.mosse.add(new Mossa(Casella.lastCasella.getPezzo(), Casella.lastCasella.colonna, Casella.lastCasella.riga, casella.colonna, casella.riga, casella.getPezzo()));
				
				// rimuovo i puntini e i riquadri
				for (Casella c : Casella.lastCasella.getPezzo().getCanGo()) {
					if (c.getPezzo() == null)
						c.getChildren().remove(c.dot);
					else if (!c.getPezzo().getColore().equals(Casella.lastCasella.getPezzo().getColore()))
						c.getChildren().remove(c.contrno);
				}
				
				Casella.lastCasella.setOpacity(1);
				
				if (BaseScene.sottoScacco) { // se ero sotto scacco e ho fatto questa mossa significa che mi sono tolto dallo scacco
					BaseScene.sottoScacco = false;
					BaseScene.re.getChildren().remove(re.contrno);
				}
				
				if(casella.getPezzo() != null)
					casella.getPezzo().mangiato = true;
				
				casella.setPezzo(Casella.lastCasella.getPezzo());
				Casella.lastCasella.setPezzo(null);
				casella.getPezzo().onMove();
				BaseScene.playerTurn = false;
				
				StockfishEngine.get().scriviMossa();
				
				this.timer.schedule(new TimerTask() {
					@Override
					public void run() {
						StockfishEngine.get().elaboraMossa();
					}
				}, 1000);
				
				BaseScene.calcMinacce();
			}
			else { // se la mossa non è valida deseleziono comunque il pezzo che si voleva muovere
				for (Casella c : Casella.lastCasella.getPezzo().getCanGo()) {
					if (c.getPezzo() == null)
						c.getChildren().remove(c.dot);
					else if (!c.getPezzo().getColore().equals(Casella.lastCasella.getPezzo().getColore()))
						c.getChildren().remove(c.contrno);
				}
				Casella.lastCasella.setOpacity(1);
				Casella.lastCasella = null;
			}
		}
	}
}
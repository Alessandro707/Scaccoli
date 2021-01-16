package sample.scenes;

import sample.*;
import sample.enums.Giocatore;
import sample.net.Net;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PvP extends BaseScene {
	private final Timer timer = new Timer(1000, (ActionEvent e) -> Net.checkMossa());
	
	public PvP(){
		super();
		
		if(Main.giocatore.equals(Giocatore.NERO))
			timer.start();
	}
	
	@Override
	public void close(){
		if(timer.isRunning())
			timer.stop();
	}
	
	@Override
	public void mouseCallback(Casella casella){
		if(!BaseScene.playerTurn)
			return;
		if((Casella.lastCasella == null || Casella.lastCasella.getPezzo() == null) && casella.getPezzo() == null)
			return;
		if(Casella.lastCasella == null || Casella.lastCasella.getPezzo() == null) {
			if(casella.getPezzo().getColore().ordinal() != Main.giocatore.ordinal())
				return;
			
			Casella.lastCasella = casella;
			
			for(Casella c : Casella.lastCasella.getPezzo().getCanGo()){
				if(c.getPezzo() == null && !c.getChildren().contains(c.dot))
					c.getChildren().add(c.dot);
				else if(!c.getPezzo().getColore().equals(casella.getPezzo().getColore()) && !c.getChildren().contains(c.contrno))
					c.getChildren().add(c.contrno);
			}
			
			Casella.lastCasella.setOpacity(0.75);
		}
		else {
			if (Casella.lastCasella.getPezzo().getCanGo().contains(casella) && (casella.getPezzo() == null ||
						!Casella.lastCasella.getPezzo().getColore().equals(casella.getPezzo().getColore()))) {
				
				BaseScene.mosse.add(new Mossa(Casella.lastCasella.getPezzo(), Casella.lastCasella.colonna, Casella.lastCasella.riga, casella.colonna, casella.riga));
				
				for (Casella c : Casella.lastCasella.getPezzo().getCanGo()) {
					if (c.getPezzo() == null)
						c.getChildren().remove(c.dot);
					else if (!c.getPezzo().getColore().equals(Casella.lastCasella.getPezzo().getColore()))
						c.getChildren().remove(c.contrno);
				}
				
				Casella.lastCasella.setOpacity(1);
				
				if (BaseScene.sottoScacco) {
					BaseScene.sottoScacco = false;
					BaseScene.re.getChildren().remove(re.contrno);
				}
				
				if(casella.getPezzo() != null)
					casella.getPezzo().mangiato = true;
				
				casella.setPezzo(Casella.lastCasella.getPezzo());
				Casella.lastCasella.setPezzo(null);
				casella.getPezzo().onMove();
				BaseScene.playerTurn = false;
				BaseScene.playerTurn = true;
				
				Net.scriviSuWeb(Casella.lastCasella.colonna, Casella.lastCasella.riga, casella.colonna, casella.riga, casella.getPezzo().getTipoPezzo());
				timer.start();
				
				BaseScene.calcMinacce();
			}
			else {
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

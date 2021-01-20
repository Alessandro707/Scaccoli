package sample.scenes;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import sample.Casella;
import sample.Main;
import sample.Mossa;
import sample.Pezzi.*;
import sample.enums.Colonna;
import sample.enums.Colore;
import sample.enums.Giocatore;
import sample.enums.TipoPezzo;
import sample.net.Net;

import java.util.ArrayList;

//Classe che contine le informazioni in comune tra PvP e PvE (schacciera, turno, pezzi...)
public class BaseScene extends StackPane {
	public static final GridPane gridPane = new GridPane();
	public static boolean playerTurn;
	public static boolean sottoScacco = false;
	public static Casella[][] caselle = new Casella[8][8];
	public static ArrayList<Mossa> mosse = new ArrayList<>();
	public static Pezzo[] pezzi = new Pezzo[32];
	public static Casella re;
	public static boolean scaccoMatto = false;
	
	public BaseScene(){
		BaseScene.playerTurn = Main.giocatore.equals(Giocatore.BIANCO);
		int ind = 0;
		
		// instanziazione dei pezzi e inserimento nel gridpane
		for(int i = 0; i < 8; i++){
			caselle[i] = new Casella[8];
			for(int j = 0; j < 8; j++){
				caselle[i][j] = new Casella(i, j);
				
				if(i == 1) {
					pezzi[ind] = new Pedone(Colore.BIANCO, j, i);
					caselle[i][j].setPezzo(pezzi[ind++]);
				}
				else if(i == 6) {
					pezzi[ind] = new Pedone(Colore.NERO, j, i);
					caselle[i][j].setPezzo(pezzi[ind++]);
				}
				
				if(Main.giocatore.equals(Giocatore.BIANCO))
					BaseScene.gridPane.add(caselle[i][j], j, 7 - i);
				else
					BaseScene.gridPane.add(caselle[i][j], 7 - j, i);
			}
		}
		pezzi[ind] = new Torre(Colore.BIANCO, 0, 0);
		caselle[0][0].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Torre(Colore.BIANCO, 7, 0);
		caselle[0][7].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Torre(Colore.NERO, 0, 7);
		caselle[7][0].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Torre(Colore.NERO, 7, 7);
		caselle[7][7].setPezzo(pezzi[ind++]);
		
		pezzi[ind] = new Cavallo(Colore.BIANCO, 1, 0);
		caselle[0][1].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Cavallo(Colore.BIANCO, 6, 0);
		caselle[0][6].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Cavallo(Colore.NERO, 1, 7);
		caselle[7][1].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Cavallo(Colore.NERO, 6, 7);
		caselle[7][6].setPezzo(pezzi[ind++]);
		
		pezzi[ind] = new Alfiere(Colore.BIANCO, 2, 0);
		caselle[0][2].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Alfiere(Colore.BIANCO, 5, 0);
		caselle[0][5].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Alfiere(Colore.NERO, 2, 7);
		caselle[7][2].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Alfiere(Colore.NERO, 5, 7);
		caselle[7][5].setPezzo(pezzi[ind++]);
		
		pezzi[ind] = new Re(Colore.BIANCO, 4, 0);
		caselle[0][4].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Re(Colore.NERO, 4, 7);
		caselle[7][4].setPezzo(pezzi[ind++]);
		
		pezzi[ind] = new Regina(Colore.BIANCO, 3, 0);
		caselle[0][3].setPezzo(pezzi[ind++]);
		pezzi[ind] = new Regina(Colore.NERO, 3, 7);
		caselle[7][3].setPezzo(pezzi[ind]);
		
		if(Main.giocatore.equals(Giocatore.BIANCO))
			BaseScene.re = caselle[0][4];
		else
			BaseScene.re = caselle[7][4];
		// ****************************************************
		
		// numeri delle righe e lettere delle colonne
		for(int i = 0; i < 8; i++){
			Text text1 = new Text("         " + (Main.giocatore.equals(Giocatore.BIANCO) ? Colonna.values()[i].toString() : Colonna.values()[7 - i].toString()));
			text1.setFont(new Font(15));
			text1.setTextAlignment(TextAlignment.CENTER);
			gridPane.add(text1 , i, 8);
			
			Text text2 = new Text((Main.giocatore.equals(Giocatore.BIANCO) ? (7 - i + 1) : (i + 1)) + "");
			text2.setFont(new Font(15));
			gridPane.add(text2, 8, i);
		}
		this.getChildren().add(BaseScene.gridPane);
		
		BaseScene.calcMinacce();
	}
	
	// calcola dove può andare ogni pezzo, per sapere dove il giocatore può muovere o per sapere se si è sotto scacco
	public static void calcMinacce(){
		Pezzo reSottoScacco = null;
		for(Pezzo p : BaseScene.pezzi){
			if(p.mangiato)
				continue;
			p.setCanGo();
			
			// se il pezzo è nemico controllo se sta minacciando il mio re
			if(p.getColore().ordinal() != Main.giocatore.ordinal()) {
				for (Casella c : p.getCanGo()) {
					if (c.getPezzo() != null && c.getPezzo().getTipoPezzo().equals(TipoPezzo.RE) &&
							!c.getPezzo().getColore().equals(p.getColore())) { // se il pezzo è nero mi interessa il re bianco e viceversa
						
						c.getChildren().add(c.contrno); // aggiungo un contorno rosse alla casella del mio re
						BaseScene.sottoScacco = true;
						reSottoScacco = c.getPezzo();
						break;
					}
				}
				
				if (BaseScene.sottoScacco)
					continue;
				for (Casella c : p.getMinacciaPedone()) { // i pedoni minacciano di lato ma non ci possono "andare"
					if (c.getPezzo() != null && c.getPezzo().getTipoPezzo().equals(TipoPezzo.RE) &&
							!c.getPezzo().getColore().equals(p.getColore())) {
						
						c.getChildren().add(c.contrno);
						BaseScene.sottoScacco = true;
						reSottoScacco = c.getPezzo();
						break;
					}
				}
			}
		}
		
		if(!BaseScene.sottoScacco)
			return;
		
		// se il re è sotto scacco devo controllare quali delle possibili (da comportamento del pezzo) mosse sono effettivamente accettabili
		boolean mate = true;
		for(Pezzo p1 : BaseScene.pezzi){ // per ogni pezzo alleato
			if(p1.getColore().ordinal() != Main.giocatore.ordinal())
				continue;
			if(p1.mangiato) // ancora in game
				continue;
			
			ArrayList<Casella> toRemove = new ArrayList<>();
			Casella start = BaseScene.caselle[p1.getPezzoY()][p1.getPezzoX()];
			for(Casella c1 : p1.getCanGo()){ // per ogni casella in cui può andare ipotizzo che ci vada e vedo se il re è ancora sotto scacco
				Pezzo dest = c1.getPezzo();
				if(dest != null && dest.getColore().equals(p1.getColore())) // se nella destinazione c'è un pezzo alleato la ignoro
					continue;
				if(dest != null && !dest.getColore().equals(p1.getColore())) // se c'era un pezzo poi mi ricordo di rimettercelo
					c1.getPezzo().mangiato = true;
				
				c1.setPezzo(p1);
				start.setPezzo(null);
				
				for(Pezzo p2 : BaseScene.pezzi){ // per ogni pezzo nemico
					if(p2.getColore().ordinal() == Main.giocatore.ordinal())
						continue;
					if(p2.mangiato) // ancora in game
						continue;
					
					// prendo l'elenco delle caselle in cui può andare, senza dire al pezzo di salvarsi l'array
					ArrayList<Casella> nuovoCanGO = p2.calcCanGo();
					
					boolean flag = false;
					for(Casella c2 : nuovoCanGO){ // per tutte le caselle in cui può andare cerco se c'è il mio re
						if(c2.getPezzo() != null && c2.getPezzo().getTipoPezzo().equals(TipoPezzo.RE) &&
								c2.getPezzo().getColore().equals(p1.getColore())){
							flag = true;
							break;
						}
					}
					if(flag) { // se il re è ancora minacciato devo togliere questa mossa del mio pezzo da quelle che può fare
						toRemove.add(c1);
						break;
					}
				}
				
				if(dest != null)
					dest.mangiato = false;
				c1.setPezzo(dest);
				start.setPezzo(p1);
			}
			for(Casella c : toRemove) // rimuvo le caselle in cui non può andare
				p1.getCanGo().remove(c);
			toRemove.clear();
			
			boolean flag = false;
			for(Casella c : p1.getCanGo()){ // se l'array delle caselle in cui può andare contiene una casella effettivamente valida non è matto
				if(c.getPezzo() == null || !c.getPezzo().getColore().equals(p1.getColore())) {
					flag = true;
					break;
				}
			}
			if(flag)
				mate = false;
		}
		if(mate) {
			BaseScene.scaccoMatto = true;
			Text text = new Text("SCACCO MATTACCHIONE");
			text.setTextAlignment(TextAlignment.CENTER);
			text.setFont(new Font(40));
			text.setFill(Color.RED);
			EntryPoint.scene.getChildren().add(text);
			
			// dico al server che ho perso
			Net.scriviSuWeb(BaseScene.re.colonna, BaseScene.re.riga, BaseScene.re.colonna, -1, TipoPezzo.RE);
		}
		if(reSottoScacco != null && reSottoScacco.getColore().ordinal() == Main.giocatore.ordinal()) {
			BaseScene.re.getChildren().remove(re.contrno); // si buggava il contorno quindi lo toglo e lo rimetto così torna sopra
			BaseScene.re.getChildren().add(re.contrno);
		}
	}
	
	public void mouseCallback(Casella casella) {}
	
	public void close() {}
}

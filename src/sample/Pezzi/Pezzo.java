package sample.Pezzi;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Casella;
import sample.enums.Colore;
import sample.enums.TipoPezzo;

import java.util.ArrayList;

public class Pezzo extends ImageView {
	public static final Image[][] imgs = new Image[][]{ // grazie Mura per le immagini (tranne quella del pedone).
			new Image[]{
					new Image("res/PB.jpg", Casella.size, Casella.size, true, true),
					new Image("res/AlfiereB.png", Casella.size, Casella.size, true, true),
					new Image("res/CavalloB.png", Casella.size, Casella.size, true, true),
					new Image("res/TorreB.png", Casella.size, Casella.size, true, true),
					new Image("res/ReginaB.png", Casella.size, Casella.size, true, true),
					new Image("res/ReB.png", Casella.size, Casella.size, true, true),
			},
			new Image[]{
					new Image("res/PN.jpg", Casella.size, Casella.size, true, true),
					new Image("res/AlfiereN.png", Casella.size, Casella.size, true, true),
					new Image("res/CavalloN.png", Casella.size, Casella.size, true, true),
					new Image("res/TorreN.png", Casella.size, Casella.size, true, true),
					new Image("res/ReginaN.png", Casella.size, Casella.size, true, true),
					new Image("res/ReN.png", Casella.size, Casella.size, true, true),
			}
	};
	// elenco di indici delle immagini nell'array di immagini per le promozioni
	public static final int bianco = 0;
	public static final int nero = 1;
	public static final int indPedone = 0;
	public static final int indAlfiere = 1;
	public static final int indCavallo = 2;
	public static final int indTorre = 3;
	public static final int indRegina = 4;
	public static final int indRe = 5;
	
	private int x, y;
	private Colore colore;
	private TipoPezzo tipoPezzo;
	// elenco di caselle dove il pezzo può andare (vuote o con pezzi anche alleati per impedire al re avversario di mangiarle e mettersi sotto scacco)
	private ArrayList<Casella> canGo = new ArrayList<>();
	private ArrayList<Casella> minacciaPedone = new ArrayList<>();
	public boolean mangiato = false;
	public boolean mosso = false; // per arrocco
	
	protected Pezzo(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	// calcolo le caselle in cui può andare
	public ArrayList<Casella> calcCanGo() {
		return null;
	}
	
	// mi salvo nel pezzo l'elenco di caselle in cui può andare
	public void setCanGo() {
		this.canGo.clear();
		this.canGo = this.calcCanGo();
	}
	
	public int getPezzoX() {
		return x;
	}
	
	public int getPezzoY() {
		return y;
	}
	
	public Colore getColore() {
		return colore;
	}
	
	public TipoPezzo getTipoPezzo() {
		return tipoPezzo;
	}
	
	public ArrayList<Casella> getCanGo() {
		return canGo;
	}
	
	public ArrayList<Casella> getMinacciaPedone() {
		return minacciaPedone;
	}
	
	public void setPezzoX(int x) {
		this.x = x;
	}
	
	public void setPezzoY(int y) {
		this.y = y;
	}
	
	public void setColore(Colore colore) {
		this.colore = colore;
	}
	
	public void setTipoPezzo(TipoPezzo tipoPezzo) {
		if (this.tipoPezzo != null)
			this.setImage(Pezzo.imgs[this.colore.ordinal()][tipoPezzo.ordinal()]);
		this.tipoPezzo = tipoPezzo;
	}
	
	// mi serve per arroccare (in cui devo muovere 2 mezzi con 1 input)
	public void onMove() {
		this.mosso = true;
	}
	
	@Override
	public String toString() {
		return (this.tipoPezzo + " " + this.colore + " " + this.x + " " + this.y + " " + this.mangiato);
	}
	
}
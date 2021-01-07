package sample.Pezzi;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Casella;
import sample.enums.Colore;
import sample.enums.TipoPezzo;

import java.util.ArrayList;

public class Pezzo extends ImageView {
	public static final Image[][] imgs = new Image[][]{
			new Image[]{
				new Image("res/PB.jpg", Casella.size, Casella.size, true, true),
				new Image("res/AB.jpg", Casella.size, Casella.size, true, true),
				new Image("res/CB.jpg", Casella.size, Casella.size, true, true),
				new Image("res/TB.jpg", Casella.size, Casella.size, true, true),
				new Image("res/REGB.jpg", Casella.size, Casella.size, true, true),
				new Image("res/REB.jpg", Casella.size, Casella.size, true, true),
			},
			new Image[]{
				new Image("res/PN.jpg", Casella.size, Casella.size, true, true),
				new Image("res/AN.jpg", Casella.size, Casella.size, true, true),
				new Image("res/CN.jpg", Casella.size, Casella.size, true, true),
				new Image("res/TN.jpg", Casella.size, Casella.size, true, true),
				new Image("res/REGN.jpg", Casella.size, Casella.size, true, true),
				new Image("res/REN.jpg", Casella.size, Casella.size, true, true),
			}
	};
	
	private int x, y;
	private Colore colore;
	private TipoPezzo tipoPezzo;
	private ArrayList<Casella> canGo = new ArrayList<>(); // elenco di caselle dove il pezzo può andare (vuote o con pezzi anche alleati)
	private ArrayList<Casella> minacciaPedone = new ArrayList<>();
	public boolean mangiato = false;
	
	protected Pezzo(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public ArrayList<Casella> calcCanGo() {
		return null;
	}
	public void setCanGo(){
		this.canGo.clear();
		this.canGo = this.calcCanGo();
		
		if(this.tipoPezzo.equals(TipoPezzo.PEDONE))
			this.calcMinaccePedone();
	}
	public ArrayList<Casella> calcMinaccePedone(){
		return null;
	}
	public void  setMinacciaPedone(){
		this.minacciaPedone.clear();
		this.minacciaPedone = calcMinaccePedone();
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
		if(this.tipoPezzo != null)
			this.setImage(Pezzo.imgs[this.colore.ordinal()][tipoPezzo.ordinal()]);
		this.tipoPezzo = tipoPezzo;
	}
	
	public void onMove() {}
	
	@Override
	public String toString(){
		return (this.tipoPezzo + " " + this.colore + " " + this.x + " " + this.y + " " + this.mangiato);
	}
	
	public static final int bianco = 0;
	public static final int nero = 1;
	public static final int indPedone = 0;
	public static final int indAlfiere = 1;
	public static final int indCavallo = 2;
	public static final int indTorre = 3;
	public static final int indRegina = 4;
	public static final int indRe = 5;
}

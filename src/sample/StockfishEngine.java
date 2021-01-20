package sample;

import javafx.application.Platform;
import sample.enums.Colonna;
import sample.scenes.PvE;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

// Stockfish 1.0
public class StockfishEngine {
	private static StockfishEngine instance;
	
	private Process engine;
	private BufferedReader eReader;
	private BufferedWriter eWriter;
	
	private static String path = null;
	
	private StockfishEngine(){
		//non sapendo come dire al programma di prendere il percorso in cui si trova il codice uso un TextField in cui chiedo all'utente di specificare il percorso di
		//Stockfish, che in ogni caso è sotto src/engine/stockfish.exe. Il percorso verrà salvato su un file di testo.
		if(StockfishEngine.path == null && StockfishEngine.readPath() == null) {
			System.out.println("PERCORSO STOCKFISH NON SPECIFICATO");
			return;
		}
		try {
			this.engine = new ProcessBuilder(path).start();
			this.eReader = new BufferedReader(new InputStreamReader(engine.getInputStream()));
			this.eWriter = new BufferedWriter(new OutputStreamWriter(engine.getOutputStream()));
		} catch (IOException ioException) {
			StockfishEngine.setPath(""); // se il percorso è sbagliato metto il path vuoto sul file di testo in cui salvo il percorso
			ioException.printStackTrace();
		}
	}
	
	public static StockfishEngine get(){
		if(StockfishEngine.instance == null)
			StockfishEngine.instance = new StockfishEngine();
		
		return StockfishEngine.instance;
	}
	
	// scrivo l'ultima mossa fatta
	public void scriviMossa() {
		if(PvE.mosse.size() == 0)
			return;
		
		try {
			// prendo la mossa in formato stockfishiano
			String res = PvE.mosse.get(PvE.mosse.size() - 1).toStockfish() + "\n";
			StockfishEngine.get().eWriter.write(res, 0, res.length());
			StockfishEngine.get().eWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// chiamo la funzione go di stockfish che elabora la mossa migliore
	public void go() {
		try {
			String res = "go\n";
			StockfishEngine.get().eWriter.write(res, 0, res.length());
			StockfishEngine.get().eWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// chiedo a stockfish la mossa migliore e poi la leggo
	public void elaboraMossa() {
		StockfishEngine.get().go();
		
		// attendo che elabori con un timer per 0.1 secondi
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				
				String line;
				String[] mossa = new String[0];
				
				/*
					leggo le righe dal punto in cui si era interrotta la lettura alla chiamata precedente della funzione eaboraMossa
					(che corrisponde all'ultima mossa di Stockfish) e cerco l'ultima elaborata (cioè la nuova mossa).
					la riga che cerco è: bestmove a1a2 ponder b1b2, dove a1a2 è la mossa del giocatore che deve muovere,
					mentre b1b2 è una predizione della mossa avversaria in risposta.
				*/
				while(true){
					try {
						line = StockfishEngine.get().eReader.readLine();
						if(line == null) break;
						mossa = line.split(" ");
						if(mossa.length == 4 && mossa[0].equals("bestmove"))
							break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if(mossa[0].equals("bestmove") && mossa[1].length() == 4){
					mossa[1] = mossa[1].toUpperCase();
					Colonna x1 = Colonna.valueOf(mossa[1].charAt(0) + "");    // a
					int y1 = Integer.parseInt(mossa[1].charAt(1) + "") - 1;// 1
					Colonna x2 = Colonna.valueOf(mossa[1].charAt(2) + "");    // a
					int y2 = Integer.parseInt(mossa[1].charAt(3) + "") - 1;// 2
					PvE.mosse.add(new Mossa(PvE.caselle[y1][x1.ordinal()].getPezzo(), x1, y1, x2, y2, PvE.caselle[y2][x2.ordinal()].getPezzo()));
					StockfishEngine.get().scriviMossa();
					
					//sposto i pezzi sulla scaccoliera
					Runnable runnable = () -> {
						if(PvE.caselle[y2][x2.ordinal()].getPezzo() != null)
							PvE.caselle[y2][x2.ordinal()].getPezzo().mangiato = true;
						PvE.caselle[y2][x2.ordinal()].setPezzo(PvE.caselle[y1][x1.ordinal()].getPezzo());
						PvE.caselle[y1][x1.ordinal()].setPezzo(null);
						PvE.playerTurn = true;
						
						PvE.calcMinacce();
					};
					
					Platform.runLater(runnable);
				}
			}
		}, 100);
	}
	
	// chiudo i processi per evitare che il mio programma non termini (non termina comunque ma vabbé, perchè?)
	public static void close() {
		if(StockfishEngine.path == null || StockfishEngine.path.length() == 0) {
			System.out.println("Percorso stockfish non specificato");
			return;
		}
		try {
			StockfishEngine.get().eWriter.close();
			StockfishEngine.get().eReader.close();
			StockfishEngine.get().engine.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// scrivo sul file di testo il percorso di stockfish / lo cancello se errato
	public static void setPath(String path) {
		StockfishEngine.path = path;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("src/res/StockfishPath.txt"));
			writer.write(StockfishEngine.path);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getPath(){
		return StockfishEngine.path;
	}
	
	// leggo da file in percorso di stockfish
	public static String readPath(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("src/res/StockfishPath.txt"));
			StockfishEngine.path = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return StockfishEngine.path;
	}
}

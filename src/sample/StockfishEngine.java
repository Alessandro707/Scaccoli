package sample;

import javafx.application.Platform;
import sample.enums.Colonna;
import sample.scenes.EntryPoint;
import sample.scenes.PvE;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class StockfishEngine {
	private static StockfishEngine instance;
	
	private Process engine;
	private BufferedReader eReader;
	private BufferedWriter eWriter;
	
	private static String path;
	
	private StockfishEngine(){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("res/StockfishPath.txt"));
			StockfishEngine.path = reader.readLine();
			if(StockfishEngine.path == null || StockfishEngine.path.equals("")) {
				StockfishPathInputGUI gui = new StockfishPathInputGUI();
				EntryPoint.scene.getChildren().add(gui);
				
				while(!gui.shouldClose){
				
				}
				BufferedWriter writer = new BufferedWriter(new FileWriter("res/StockfishPath.tat"));
				writer.write(StockfishEngine.path);
				writer.flush();
				writer.close();
			}
			reader.close();
			
			this.engine = new ProcessBuilder(StockfishEngine.path).start();
			this.eReader = new BufferedReader(new InputStreamReader(engine.getInputStream()));
			this.eWriter = new BufferedWriter(new OutputStreamWriter(engine.getOutputStream()));
		} catch (IOException ioException) {
			StockfishEngine.path = "";
			ioException.printStackTrace();
		}
	}
	
	public static StockfishEngine get(){
		if(StockfishEngine.instance == null)
			StockfishEngine.instance = new StockfishEngine();
		
		return StockfishEngine.instance;
	}
	
	public void scriviMossa() {
		if(PvE.mosse.size() == 0)
			return;
		
		try {
			String res = PvE.mosse.get(PvE.mosse.size() - 1).toStockfish() + "\n";
			StockfishEngine.get().eWriter.write(res, 0, res.length());
			StockfishEngine.get().eWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void go() {
		try {
			String res = "go\n";
			StockfishEngine.get().eWriter.write(res, 0, res.length());
			StockfishEngine.get().eWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void elaboraMossa() {
		StockfishEngine.get().go();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				
				String line;
				String[] mossa = new String[0];
				
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
					Colonna x1 = Colonna.valueOf(mossa[1].charAt(0) + "");
					int y1 = Integer.parseInt(mossa[1].charAt(1) + "") - 1;
					Colonna x2 = Colonna.valueOf(mossa[1].charAt(2) + "");
					int y2 = Integer.parseInt(mossa[1].charAt(3) + "") - 1;
					PvE.mosse.add(new Mossa(PvE.caselle[y1][x1.ordinal()].getPezzo(), x1, y1, x2, y2));
					StockfishEngine.get().scriviMossa();
					
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
	
	public static void close(){
		try {
			StockfishEngine.get().eWriter.close();
			StockfishEngine.get().eReader.close();
			StockfishEngine.get().engine.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void setPath(String path){
		StockfishEngine.path = path;
	}
}

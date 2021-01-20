package sample.net;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import sample.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import sample.Mossa;
import sample.enums.Colonna;
import sample.enums.TipoPezzo;
import sample.scenes.BaseScene;
import sample.scenes.EntryPoint;
import sample.scenes.PvP;

public interface Net {
	String WEB_URL_OUT = "http://scacchipazziconmura.altervista.org/InserisciMossa.php";
	String WEB_URL_IN = "http://scacchipazziconmura.altervista.org/LeggiMossa.php";
	String WEB_URL_RESET = "http://scacchipazziconmura.altervista.org/Resetta.php";
	
	private static String leggiDaWeb(){
		HttpURLConnection con;
		try {
			URL url = new URL(Net.WEB_URL_IN);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setDoOutput(true);
			
			int status = con.getResponseCode();
			if(status == 200){
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String msg = in.readLine();
				in.close();
				con.disconnect();
				return(msg);
			}
			else{
				System.out.println("Response code in lettura: " + status);
			}

			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "[]";
	}
	static void scriviSuWeb(Colonna s1, int s2, Colonna d1, int d2, TipoPezzo pezzo){
		HttpURLConnection con;
		try {
			URL  url = new URL(Net.WEB_URL_OUT);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setDoOutput(true);
			
			Map<String, String> parameters = new HashMap<>(); // giocatore, xy_1, xy_2, pezzo
			parameters.put("giocatore", Main.giocatore.ordinal() + "");
			parameters.put("da", s1.toString() + s2);
			parameters.put("a", d1.toString() + d2);
			parameters.put("pezzo", pezzo.ordinal() + "");
			
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			out.writeBytes(getParamsString(parameters));
			out.flush();
			out.close();
			
			int status = con.getResponseCode();
			if(status != 200){
				System.out.println("Response code in scrittura: " + status);
			}
			
			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static void checkMossa(){
		riceviMossa(leggiDaWeb());
	}
	
	static void riceviMossa(String str){
		if(str == null || str.equals("") || str.equals("[null]"))
			return;
		try {
			JSONArray jsonArray =(JSONArray)( new JSONParser().parse(str));
			for (Object json:jsonArray) {
				JSONObject jo = (JSONObject) json;
				//String id = (String) jo.get("id");
				int giocatore = Integer.parseInt((String)jo.get("giocatore"));
				String dest = (String) jo.get("mossaA");
				String start = (String) jo.get("mossaDa");
				//TipoPezzo pezzo = TipoPezzo.values()[Integer.parseInt((String) jo.get("pezzo"))];
				
				int daX = Colonna.valueOf(start.charAt(0) + "").ordinal(), daY = Integer.parseInt(start.charAt(1) + "");
				int aX = Colonna.valueOf(dest.charAt(0) + "").ordinal(), aY = Integer.parseInt(dest.charAt(1) + "");
				
				if(giocatore != Main.giocatore.ordinal()){
					Mossa m = new Mossa(PvP.caselle[daY][daX].getPezzo(), Colonna.valueOf(start.charAt(0) + ""),
							daY, Colonna.valueOf(dest.charAt(0) + ""), aY);
					BaseScene.mosse.add(m);
					
					Runnable runnable = () -> {
						if(aY < 0){ // se è minore di 0 significa che è matto
							BaseScene.scaccoMatto = true;
							Text text = new Text("SCACCO MATTACCHIONE");
							text.setTextAlignment(TextAlignment.CENTER);
							text.setFont(new Font(40));
							text.setFill(Color.GREEN);
							EntryPoint.scene.getChildren().add(text);
						}
						else {
							if (BaseScene.caselle[m.getDestY()][m.getDestX().ordinal()].getPezzo() != null)
								BaseScene.caselle[m.getDestY()][m.getDestX().ordinal()].getPezzo().mangiato = true;
							
							// sposto i pezzi
							BaseScene.caselle[m.getDestY()][m.getDestX().ordinal()].setPezzo(PvP.caselle[m.getStartY()][m.getStartX().ordinal()].getPezzo());
							BaseScene.caselle[m.getStartY()][m.getStartX().ordinal()].setPezzo(null);
							EntryPoint.scene.close(); // smette di cercare nuove mosse sul database
							BaseScene.playerTurn = true;
							BaseScene.calcMinacce();
						}
					};
					
					Platform.runLater(runnable);
					return;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	static void resetta(){
		HttpURLConnection con;
		try {
			URL  url = new URL(WEB_URL_RESET);
			con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			con.setDoOutput(true);
			
			int status = con.getResponseCode();
			if(status != 200){
				System.out.println("Response code in reset: " + status);
			}

			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		
		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
			result.append("&");
		}
		
		String resultString = result.toString();
		return resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;
	}
	
}

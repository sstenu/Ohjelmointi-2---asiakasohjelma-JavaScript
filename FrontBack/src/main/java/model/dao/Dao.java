package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Asiakas;

public class Dao {
	private Connection con=null;
	private ResultSet rs = null;
	private PreparedStatement stmtPrep=null; 
	private String sql;
	private String db ="Myynti.sqlite";
	
	private Connection yhdista(){
    	Connection con = null;    	
    	String path = System.getProperty("catalina.base");    	
    	path = path.substring(0, path.indexOf(".metadata")).replace("\\", "/"); //Eclipsessa
    	//path += "/webapps/"; //Tuotannossa. Laita tietokanta webapps-kansioon
    	String url = "jdbc:sqlite:"+path+db;    	
    	try {	       
    		Class.forName("org.sqlite.JDBC");
	        con = DriverManager.getConnection(url);	
	        System.out.println("Yhteys avattu.");
	     }catch (Exception e){	
	    	 System.out.println("Yhteyden avaus epäonnistui.");
	        e.printStackTrace();	         
	     }
	     return con;
	}
	
	public ArrayList<Asiakas> listaaKaikki(){
		ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		sql = "SELECT * FROM asiakkaat";       
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);        		
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					//con.close();					
					while(rs.next()){
						Asiakas asiakas = new Asiakas();
						asiakas.setAsiakasID(rs.getInt(1));
						asiakas.setEtunimi(rs.getString(2));
						asiakas.setSukunimi(rs.getString(3));	
						asiakas.setPuhelin(rs.getString(4));	
						asiakas.setSposti(rs.getString(5));
						asiakkaat.add(asiakas);
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return asiakkaat;
	}
	
	public ArrayList<Asiakas> listaaKaikki(String hakusana){
		ArrayList<Asiakas> asiakkaat = new ArrayList<Asiakas>();
		sql = "SELECT * FROM asiakkaat WHERE etunimi LIKE ? or sukunimi LIKE ? or puhelin LIKE ? or sposti LIKE ?";      
		try {
			con=yhdista();
			if(con!=null){ //jos yhteys onnistui
				stmtPrep = con.prepareStatement(sql);
				stmtPrep.setString(1, "%" + hakusana + "%");
				stmtPrep.setString(2, "%" + hakusana + "%");
				stmtPrep.setString(3, "%" + hakusana + "%");   
				stmtPrep.setString(4, "%" + hakusana + "%"); 
        		rs = stmtPrep.executeQuery();   
				if(rs!=null){ //jos kysely onnistui
					//con.close();					
					while(rs.next()){
						Asiakas asiakas = new Asiakas();
						asiakas.setAsiakasID(rs.getInt(1));
						asiakas.setEtunimi(rs.getString(2));
						asiakas.setSukunimi(rs.getString(3));	
						asiakas.setPuhelin(rs.getString(4));	
						asiakas.setSposti(rs.getString(5));
						asiakkaat.add(asiakas);
					}					
				}				
			}	
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return asiakkaat; 
	}
	
	public boolean lisaaAsiakas(Asiakas asiakas) {
		boolean paluuArvo=true;
		sql="INSERT INTO asiakkaat VALUES(?,?,?,?,?)";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setInt(1, asiakas.getAsiakasID());
			stmtPrep.setString(2, asiakas.getEtunimi());
			stmtPrep.setString(3, asiakas.getSukunimi());
			stmtPrep.setString(4, asiakas.getPuhelin());
			stmtPrep.setString(5, asiakas.getSposti());
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public boolean poistaAsiakas(String poistettavaID) {
		boolean paluuArvo=true;
		int poistettava = Integer.parseInt(poistettavaID);
		sql="DELETE FROM asiakkaat WHERE asiakas_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setInt(1, poistettava);			
			stmtPrep.executeUpdate();
	        con.close();
	        System.out.println("Ypoistetu.");
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
	public Asiakas etsiAsiakas(String asiakasID) {
		Asiakas asiakas = null;
		int etsittava = Integer.parseInt(asiakasID);
		sql = "SELECT * FROM asiakkaat WHERE asiakas_id=?";       
		try {
			con=yhdista();
			if(con!=null){ 
				stmtPrep = con.prepareStatement(sql); 
				stmtPrep.setInt(1, etsittava);
        		rs = stmtPrep.executeQuery();  
        		if(rs.isBeforeFirst()){ //jos kysely tuotti dataa, eli rekNo on käytössä
        			rs.next();
        			asiakas = new Asiakas();        			
        			asiakas.setAsiakasID(rs.getInt(1));
        			asiakas.setEtunimi(rs.getString(2));
					asiakas.setSukunimi(rs.getString(3));
					asiakas.setPuhelin(rs.getString(4));	
					asiakas.setSposti(rs.getString(5));       			      			
				}        		
			}	
			con.close();  
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return asiakas;		
	}
	
	public boolean muutaAsiakas(Asiakas asiakas, Integer vanhaID){
		boolean paluuArvo=true;
		sql="UPDATE asiakkaat SET asiakas_id=?, etunimi=?, sukunimi=?, puhelin=?, sposti=? WHERE asiakas_id=?";						  
		try {
			con = yhdista();
			stmtPrep=con.prepareStatement(sql); 
			stmtPrep.setInt(1, asiakas.getAsiakasID());
			stmtPrep.setString(2, asiakas.getEtunimi());
			stmtPrep.setString(3, asiakas.getSukunimi());
			stmtPrep.setString(4, asiakas.getPuhelin());
			stmtPrep.setString(5, asiakas.getSposti());
			stmtPrep.setInt(6, vanhaID);
			stmtPrep.executeUpdate();
	        con.close();
		} catch (Exception e) {				
			e.printStackTrace();
			paluuArvo=false;
		}				
		return paluuArvo;
	}
	
}


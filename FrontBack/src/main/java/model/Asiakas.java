package model;

public class Asiakas {
	private String etunimi;
	private String sukunimi;
	private String puhelin;
	private String sposti;
	private int asiakasID;
	public Asiakas() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Asiakas(String etunimi, String sukunimi, String puhelin, String sposti, int asiakasID) {
		super();
		this.etunimi = etunimi;
		this.sukunimi = sukunimi;
		this.puhelin = puhelin;
		this.sposti = sposti;
		this.asiakasID = asiakasID;
	}

	public String getEtunimi() {
		return etunimi;
	}

	public void setEtunimi(String etunimi) {
		this.etunimi = etunimi;
	}

	public String getSukunimi() {
		return sukunimi;
	}

	public void setSukunimi(String sukunimi) {
		this.sukunimi = sukunimi;
	}

	public String getPuhelin() {
		return puhelin;
	}

	public void setPuhelin(String puhelin) {
		this.puhelin = puhelin;
	}

	public String getSposti() {
		return sposti;
	}

	public void setSposti(String sposti) {
		this.sposti = sposti;
	}

	public int getAsiakasID() {
		return asiakasID;
	}

	public void setAsiakasID(int asiakasID) {
		this.asiakasID = asiakasID;
	}

	@Override
	public String toString() {
		return "Asiakas (etunimi=" + etunimi + ", sukunimi=" + sukunimi + ", puhelin=" + puhelin + ", sposti=" + sposti
				+ ", asiakasID=" + asiakasID + ")";
	}
	

}

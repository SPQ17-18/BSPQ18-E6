package es.deusto.bspq18.e6.DeustoBox.Server.dto;

import java.io.Serializable;
import java.util.Date;

public class DConnectionDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int id;
	private String userEmail;
	private Date ConnectionDate;
	private String OSUsed;


	
	
	public DConnectionDTO( int id, String userEmail, Date connectionDate, String oSUsed) {
		this.id = id;
		this.userEmail = userEmail;
		ConnectionDate = connectionDate;
		OSUsed = oSUsed;
	}
	
	public DConnectionDTO(int id, String userEmail, String oSUsed) {
		
		this.id = id;
		this.userEmail = userEmail;
		this.ConnectionDate = new Date();
		this.OSUsed = oSUsed;
		
	}
	

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Date getConnectionDate() {
		return ConnectionDate;
	}

	public void setConnectionDate(Date connectionDate) {
		ConnectionDate = connectionDate;
	}

	public String getOSUsed() {
		return OSUsed;
	}

	public void setOSUsed(String oSUsed) {
		OSUsed = oSUsed;
	}
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return " ConnectionDate=" + ConnectionDate + ", OSUsed=" + OSUsed + "]";
	}


	
	
	

}

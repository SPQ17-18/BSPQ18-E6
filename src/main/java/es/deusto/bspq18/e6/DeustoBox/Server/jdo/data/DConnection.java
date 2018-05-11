package es.deusto.bspq18.e6.DeustoBox.Server.jdo.data;

import java.io.Serializable;
import java.util.Date;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
@PersistenceCapable(detachable="true")
public class DConnection implements Serializable{
	private static final long serialVersionUID = 1L;
	@PrimaryKey
	private int ID;
	private String userEmail;
	private Date ConnectionDate;
	private String OSUsed;
	
	public DConnection(int id, String userEmail, String oSUsed) {
	
		this.ID = id;
		this.userEmail = userEmail;
		ConnectionDate = new Date();
		OSUsed = oSUsed;
		
	}
	
	

	public DConnection(int iD, String userEmail, Date connectionDate, String oSUsed) {
		ID = iD;
		this.userEmail = userEmail;
		ConnectionDate = connectionDate;
		OSUsed = oSUsed;
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

	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String toString() {
		return "DConnection [userEmail=" + userEmail + ", ConnectionDate=" + ConnectionDate + ", OSUsed=" + OSUsed
				+ "]";
	}
	
	
	
	
	
	


	
	
}

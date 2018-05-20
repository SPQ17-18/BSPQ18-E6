package es.deusto.bspq18.e6.DeustoBox.Server.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class DMessageDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int messageId;
	private String emailfrom;
	private String emailTo;
	private Date date;
	private String Subject;
	private String text;
	

	Locale currentLocale = new Locale("en", "US");
	ResourceBundle resourcebundle = ResourceBundle.getBundle("lang/translations", currentLocale);
	
	
	public DMessageDTO(int messageId, String emailfrom, String emailTo, String subject, String text, Date date) {
		this.messageId = messageId;
		this.emailfrom = emailfrom;
		this.emailTo = emailTo;
		this.date = date;
		this.Subject = subject;
		this.text = text;
	}

	public DMessageDTO(int messageId, String emailfrom, String emailTo, String subject, String text) {
		this.messageId = messageId;
		this.emailfrom = emailfrom;
		this.emailTo = emailTo;
		this.date = new Date();
		this.Subject = subject;
		this.text = text;
	}



	public int getMessageId() {
		return messageId;
	}


	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}


	public String getEmailfrom() {
		return emailfrom;
	}


	public void setEmailfrom(String emailfrom) {
		this.emailfrom = emailfrom;
	}


	public String getEmailTo() {
		return emailTo;
	}


	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public String getSubject() {
		return Subject;
	}


	public void setSubject(String subject) {
		Subject = subject;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	@Override
	public String toString() {
		return resourcebundle.getString("msg_message_id") + messageId + resourcebundle.getString("msg_message_from") + emailfrom + resourcebundle.getString("msg_message_to") + emailTo + resourcebundle.getString("msg_message_date")
				+ date + resourcebundle.getString("msg_message_subject") + Subject + resourcebundle.getString("msg_message_txt") + text + "]";
	}
	
	
	
	
}

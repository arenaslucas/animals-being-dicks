package ar.com.ceritdumbre.android.app.animalsbeingdicks;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Comparable<Message> {
	static SimpleDateFormat FORMATTER = new SimpleDateFormat(
			"EEE, dd MMM yyyy HH:mm:ss Z");
	
	private String title;
	private URL link;
	private String description;
	private Date date;

	// getters and setters omitted for brevity
	public void setLink(String link) {
		try {
			this.link = new URL(link);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	

	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getDate() {
		return FORMATTER.format(this.date);
	}

	public void setDate(String date) {
		// pad the date if necessary
		/**while (!date.endsWith("00")) {
			date += "0";
		}
		try {
			this.date = FORMATTER.parse(date.trim());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}**/
		this.date = new Date();
	}

	@Override
	public String toString() {
		return "{title=" + title + ";link=" + link + "; description=" + description + ";date=" + date + "}";
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		return false;
	}

	// sort by date
	public int compareTo(Message another) {
		if (another == null)
			return 1;
		// sort descending, most recent first
		return another.date.compareTo(date);
	}
	
	public URL getLink() {
		return link;
	}


	public Message copy() {
		Message messageCopy = new Message();
		
		messageCopy.setDate(this.getDate());
		messageCopy.setDescription(this.getDescription());
		messageCopy.setLink(this.getLink().toString());
		messageCopy.setTitle(this.getTitle());

		return messageCopy;
	}
}

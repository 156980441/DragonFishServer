package com.liang.sys.bean;

import java.util.Date;

public class TournamentContent {  
    private static int idCounter = 0;  
    private String author;  
    private Date publicationDate;  
    private String name;  
    private String link;  
    private int id;  
  
    public static TournamentContent generateContent(String author, Date date, String name, String link) {  
        TournamentContent content = new TournamentContent();  
        content.author = author;  
        content.publicationDate = date;  
        content.name = name;  
        content.link = link;  
        content.id = idCounter++;  
  
        return content;  
    }

	public static int getIdCounter() {
		return idCounter;
	}

	public static void setIdCounter(int idCounter) {
		TournamentContent.idCounter = idCounter;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}  
      
}  
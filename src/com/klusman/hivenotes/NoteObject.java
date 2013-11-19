package com.klusman.hivenotes;

public class NoteObject {
	public long id;
	public String Title;
    public String Level;
    public String Note;
    
    
    public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
	
	public String getLevel() {
		return Level;
	}
	public void setLevel(String level) {
		Level = level;
	}
	
	
	public String getNote() {
		return Note;
	}
	public void setNote(String note) {
		Note = note;
	}
	
	
}

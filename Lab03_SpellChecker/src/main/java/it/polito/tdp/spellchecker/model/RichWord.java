package it.polito.tdp.spellchecker.model;

public class RichWord {
       
	String parola;
	boolean corretta;
	
	public RichWord(String parola, boolean corretta) {
		this.parola = parola;
		this.corretta = corretta;
	}

	@Override
	public String toString() {
		return "RichWord [parola=" + parola + ", corretta=" + corretta + "]";
	}

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}

	public boolean isCorretta() {
		return corretta;
	}

	public void setCorretta(boolean corretta) {
		this.corretta = corretta;
	}
	
	
	
}

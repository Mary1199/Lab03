package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dictionary {
	
	private List<String> dizionario;
	private String language;

	public Dictionary() {

	}

	public boolean loadDictionary(String language) {


		if (dizionario!=null && this.language.equals(language)){
			return true;
		}

		dizionario = new ArrayList<String>();
		this.language = language;

		try {

			FileReader fr = new FileReader("src/main/resources/" + language + ".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;

			while ((word = br.readLine()) != null) {
				dizionario.add(word.toLowerCase());
			}

			Collections.sort(dizionario);

			br.close();
			System.out.println("Dizionario " + language + " loaded. Found " + dizionario.size() + " words.");

			return true;

		} catch (IOException e) {
			System.err.println("Errore nella lettura del file");
			return false;
		}


	}

	public List<RichWord> spellCheckText(List<String> inputTextList){

		List<RichWord> parole= new ArrayList<RichWord>();
		//List<RichWord> parole= new LinkedList<RichWord>();

		for (String str : inputTextList) {

			RichWord richWord = new RichWord(str, true);
			if (dizionario.contains(str.toLowerCase())) {
				richWord.setCorretta(true);
			} else {
				richWord.setCorretta(false);
			}
			parole.add(richWord);
		}

		return parole;
	}
   
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {

		List<RichWord> parole = new ArrayList<RichWord>();
		//List<RichWord> parole= new LinkedList<RichWord>();

		for (String str : inputTextList) {

			RichWord richWord = new RichWord(str,true);

			boolean found = false;
			for (String word : dizionario) {
				if (word.equalsIgnoreCase(str)) {
					found = true;
					break;
				}
			}

			if (found) {
				richWord.setCorretta(true);	
			} else {
				richWord.setCorretta(false);
			}

			parole.add(richWord);
		}

		return parole;
	}

	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {

		List<RichWord> parole = new ArrayList<RichWord>();
		//List<RichWord> parole= new LinkedList<RichWord>();

		for (String str : inputTextList) {

			RichWord richWord = new RichWord(str,true);
			if (binarySearch(str.toLowerCase()))
				richWord.setCorretta(true);
			else
				richWord.setCorretta(false);
			parole.add(richWord);
		}

		return parole;
	}

	private boolean binarySearch(String stemp) {
		int inizio = 0;
		int fine = dizionario.size();

		while (inizio != fine) {
			int medio = inizio + (fine - inizio) / 2;
			if (stemp.compareToIgnoreCase(dizionario.get(medio)) == 0) {
				return true;
			} else if (stemp.compareToIgnoreCase(dizionario.get(medio)) > 0) {
				inizio = medio + 1;
			} else {
				fine = medio;
			}
		}

		return false;
	}


}

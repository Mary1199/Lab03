package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Dictionary dizionario;
	private List<String> inputTextList;
	
	private final static boolean dichotomicSearch = true;
	private final static boolean linearSearch = false;

	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxLingua;

    @FXML
    private TextArea txtDaCorreggere;

    @FXML
    private Button spellCheckButton;

    @FXML
    private TextArea txtCorretto;

    @FXML
    private Button clearTextButton;

    @FXML
    private Label lblErrori;

    @FXML
    private Label lblStato;

    @FXML
    void doActivation(ActionEvent event) {
    	if (boxLingua.getValue() !=null) {

    		txtDaCorreggere.setDisable(false);
			txtCorretto.setDisable(false);
			spellCheckButton.setDisable(false);
			clearTextButton.setDisable(false);
			txtDaCorreggere.clear();
			txtCorretto.clear();

    	}else {

    		txtDaCorreggere.setDisable(true);
    		txtCorretto.setDisable(true);
			spellCheckButton.setDisable(true);
			clearTextButton.setDisable(true);
			txtDaCorreggere.setText("Seleziona una lingua!");

    	}

    }

    @FXML
    void doClearText(ActionEvent event){

    	txtDaCorreggere.clear();
    	txtCorretto.clear();
    	lblErrori.setText("Number of Errors:");
		lblStato.setText("Spell Check Status:");

    }

    @FXML
    void doSpellCheck(ActionEvent event) {

    	txtCorretto.clear();
    	inputTextList = new LinkedList<String>();

    	if (boxLingua.getValue() == null) {
    		txtDaCorreggere.setText("Seleziona una lingua!");
			return;
		}

    	if (!dizionario.loadDictionary(boxLingua.getValue())) {
    		txtDaCorreggere.setText("Errore nel caricamento del dizionario!");
			return;
		}


    	String inputText = txtDaCorreggere.getText();

    	if (inputText.isEmpty()) {
    		txtDaCorreggere.setText("Inserire un testo da correggere!");
			return;
		}


    	inputText = inputText.replaceAll("\n", " ");
		inputText = inputText.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]]", "");
		StringTokenizer st = new StringTokenizer(inputText, " ");
		while (st.hasMoreTokens()) {
			inputTextList.add(st.nextToken());
		}


		long start = System.nanoTime();
		List<RichWord> outputTextList;

		if (dichotomicSearch) {
			outputTextList = dizionario.spellCheckTextDichotomic(inputTextList);
		} else if (linearSearch) {
			outputTextList = dizionario.spellCheckTextLinear(inputTextList);
		} else {
			outputTextList = dizionario.spellCheckText(inputTextList);
		}
		long end = System.nanoTime();

		int numErrori = 0;
		StringBuilder richText = new StringBuilder();

		for (RichWord r : outputTextList) {
			if (!r.isCorretta()) {
				numErrori++;
				richText.append(r.getParola() + "\n");
			}
		}

		txtCorretto.setText(richText.toString());
		lblStato.setText("Spell check completed in " + (end - start) / 1E9 + " seconds");
		lblErrori.setText("The text contains " + numErrori + " errors");


    }


    @FXML
    void initialize() {
    	assert boxLingua != null : "fx:id=\"boxLingua\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDaCorreggere != null : "fx:id=\"txtDaCorreggere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert spellCheckButton != null : "fx:id=\"spellCheckButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorretto != null : "fx:id=\"txtCorretto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrori != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert clearTextButton != null : "fx:id=\"clearTextButton\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblStato != null : "fx:id=\"lblStato\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Dictionary model) {

    	txtDaCorreggere.setDisable(true);
    	txtDaCorreggere.setText("Selezionare una lingua");

    	txtCorretto.setDisable(true);
    	boxLingua.getItems().addAll("English","Italian");

    	spellCheckButton.setDisable(true);
    	clearTextButton.setDisable(true);


    	this.dizionario = model;
    }
}
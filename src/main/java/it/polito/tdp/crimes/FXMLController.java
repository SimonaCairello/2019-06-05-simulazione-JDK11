/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.crimes;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.crimes.model.CoppiaDistretti;
import it.polito.tdp.crimes.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxMese"
    private ComboBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="boxGiorno"
    private ComboBox<Integer> boxGiorno; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaReteCittadina"
    private Button btnCreaReteCittadina; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaReteCittadina(ActionEvent event) {
    	txtResult.clear();
    	
    	Integer year = this.boxAnno.getValue();
    	if(year==null) {
    		txtResult.appendText("Scegliere un anno dalla tendina!\n");
    		return;
    	}
    	
    	this.model.generateGraph(year);
    	
    	txtResult.appendText("Elenco adiacenti per distanza:\n\n");
    	
    	List<Integer> districts = this.model.getDistricts();
    	for(Integer d : districts) {
    		txtResult.appendText("Adiacenti distretto "+d+":\n");
    		for(CoppiaDistretti dd : this.model.getAdiacenti(d)) {
    			txtResult.appendText(dd.toString()+"\n");
    		}
    		txtResult.appendText("\n");
    	}
    	
    	List<CoppiaDistretti> list = this.model.getDistrettiAdiacenti();
    	
    	for(CoppiaDistretti s : list) {
    		txtResult.appendText(s.toString());
    	}
    	
    	this.btnSimula.setDisable(false);
    	
    	this.boxMese.getItems().setAll(this.model.getMesi());
    	this.boxGiorno.getItems().setAll(this.model.getGiorni());    	
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	
    	Integer year = this.boxAnno.getValue();
    	Integer month = this.boxMese.getValue();
    	if(month==null) {
    		txtResult.appendText("Scegliere un mese!\n");
    		return;
    	}
    	
    	Integer day = this.boxGiorno.getValue();
    	if(day==null) {
    		txtResult.appendText("Scegliere un giorno!\n");
    		return;
    	}
    	
    	Integer numAgenti = 0;
    	
    	try {
    		numAgenti = Integer.parseInt(this.txtN.getText());
    	} catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un numero intero!\n");
    		return;
    	}
    	
    	if(numAgenti<=0) {
    		txtResult.appendText("Inserire un numero maggiore di zero!\n");
    		return;
    	}
    	
    	this.model.simula(year, month, day, numAgenti);
    	txtResult.appendText("Il numero di casi mal gestiti è: "+this.model.getNumMalGestiti()+"\n");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGiorno != null : "fx:id=\"boxGiorno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaReteCittadina != null : "fx:id=\"btnCreaReteCittadina\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.btnSimula.setDisable(true);
    	
    	this.boxAnno.getItems().setAll(this.model.listYears());
    }
}

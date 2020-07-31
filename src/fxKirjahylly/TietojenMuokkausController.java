package fxKirjahylly;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalController.ModalInitializeInterface;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import kirjahylly.Kirja;
import kirjahylly.Kirjailija;

/**
 * 
 * @author Ville Hytönen ville.j.hytonen@student.jyu.fi
 * @version 31.7.2020
 */
public class TietojenMuokkausController
        implements ModalControllerInterface<Kirja>, Initializable {

    
    @FXML private ComboBoxChooser<String> chooserKirjailijat;
    @FXML private GridPane gridKirja;
    @FXML private Label labelVirhe;
    @FXML private TextField editNimi;
    @FXML private TextField editGenre;
    @FXML private TextField editJulkaisuvuosi;
    @FXML private TextField editSivumaara;
    @FXML private ComboBoxChooser<String> editTila;
    @FXML private ComboBoxChooser<Integer> editArvio;
    @FXML private DatePicker editPvm;
    @FXML private TextArea editKommentit;


    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
    }
    
    
    @FXML
    void handleLisaaKirjailija() {
        lisaaKirjailija();
    }


    @FXML
    void handleTallenna() {
        tallenna();
    }


    @FXML
    private void handlePeruuta() {
        kirjaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

    // ==================================================================================
    // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    private static StringBuilder sbKirjailijat = new StringBuilder();
    private static Kirjailija apukirjailija;
    private Kirja kirjaKohdalla;
    
    
    /**
     * Tekee tarvittavat alustukset.
     */
    protected void alusta() {
           if (editNimi != null)
                editNimi.setOnKeyReleased(e -> kasitteleMuutosKirjaan((TextField)(e.getSource())));
           
           if (editGenre != null)
               editGenre.setOnKeyReleased(e -> kasitteleMuutosKirjaan((TextField)(e.getSource())));
           
           if (editJulkaisuvuosi != null)
               editJulkaisuvuosi.setOnKeyReleased(e -> kasitteleMuutosKirjaan((TextField)(e.getSource())));
           
           if (editSivumaara != null)
               editSivumaara.setOnKeyReleased(e -> kasitteleMuutosKirjaan((TextField)(e.getSource())));
           
        //panelKirja.setFitToHeight(true);
        
    }


    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }
    

    @Override
    public void setDefault(Kirja oletus) {
        kirjaKohdalla = oletus;
        naytaKirja(kirjaKohdalla);
    }


    @Override
    public Kirja getResult() {
        return kirjaKohdalla;
    }
    
    
    
    /**
     * @param edit muuttunut kenttä
     */
    protected void kasitteleMuutosKirjaan(TextField edit) {
        if (kirjaKohdalla == null) return;
        //int k = getFieldId(edit, apukirja.ekaKentta());
        int k = 1;
        if (edit == editGenre) k = 3; 
        if (edit == editJulkaisuvuosi) k = 4; 
        if (edit == editSivumaara) k = 5; 
        String s = edit.getText();
        String virhe = null;
        virhe = kirjaKohdalla.aseta(k,s);
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * palautetaan id:stä saatu luku
     * @param objekti tutkittava komponentti
     * @param oletus arvo jos id ei ole käyvä
     * @return komponentin id lukuna
     */
    public static int getFieldId(Object objekti, int oletus) {
        if ( !(objekti instanceof Node)) return oletus;
        Node node = (Node)objekti;
        return Mjonot.erotaInt(node.getId().substring(1), oletus);
    }
    
    
    /**
     * täytetään kirjan tiedot komponentteihin
     * @param kirja kirja
     */
    public void naytaKirja(Kirja kirja) {
        if (kirja == null) return; 
        editNimi.setText(kirja.getNimi());
        alustaComboBoxChooser();
        editGenre.setText(kirja.getGenre());
        editJulkaisuvuosi.setText("" + kirja.getVuosi());
        editSivumaara.setText("" + kirja.getSivut());
        editTila.setRivit(alustaTila());
        editPvm.setPromptText(kirja.getPvm());
        editArvio.setRivit(alustaArvio());
        editKommentit.setText(kirja.getKommentit());
    }


    


    private void tallenna() {
        if (kirjaKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        kirjaKohdalla.setNimi(editNimi.getText());
        if ( chooserKirjailijat.getSelectedIndex() != 0) kirjaKohdalla.setKirjailijaId(chooserKirjailijat.getSelectedIndex() -1);
        kirjaKohdalla.setGenre(editGenre.getText());
        kirjaKohdalla.setTila(editTila.getSelectedText());
        if ( editPvm.getValue() != null) kirjaKohdalla.setPvm(editPvm.getValue().toString());
        kirjaKohdalla.setKommentit(editKommentit.getText());
        
        StringBuilder sb = new StringBuilder(editJulkaisuvuosi.getText());
        kirjaKohdalla.setVuosi(Mjonot.erota(sb, 'a', 2000));
        sb = new StringBuilder(editSivumaara.getText());
        kirjaKohdalla.setSivut(Mjonot.erota(sb, 'a', 100));
        sb = new StringBuilder(editArvio.getValue().toString());
        kirjaKohdalla.setArvio(Mjonot.erota(sb, 'a', 5));
        
        ModalController.closeStage(chooserKirjailijat);
    }
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }


    /**
     * Avaa ikkunan
     * @param modalityStage mille ollaan modaalisia
     * @param oletus mitä nimeä käytetään oletuksena
     * @return null jos painetaan Peruuta, muuten kirjoitettu nimi
     */
    public static String annaKirjailija(Stage modalityStage, String oletus) {
        return ModalController.showModal(
                TietojenMuokkausController.class
                        .getResource("MuokkaaTietoja.fxml"),
                "Muokkaa Tietoja", modalityStage, oletus);
    }


    private void lisaaKirjailija() {
        String uusiKirjailija = LisaaKirjailijaController.lisaaNimi(null, "");
        if (uusiKirjailija == null)
            return;
        Kirjailija kirjailija = new Kirjailija(uusiKirjailija);
        kirjailija.rekisteroi();
        apukirjailija = kirjailija;
        asetaComboBoxChooser(annaKirjailijat(uusiKirjailija));
    }
    
    
    private String alustaTila() {
        StringBuilder sb = new StringBuilder(kirjaKohdalla.getTila());
        sb.append("\n");
        sb.append(editTila.getRivit());
        return sb.toString();
    }
    
    
    private String alustaArvio() {
        StringBuilder sb = new StringBuilder("" + kirjaKohdalla.getArvio());
        sb.append("\n");
        sb.append(editArvio.getRivit());
        return sb.toString();
    }

    
    private void alustaComboBoxChooser() {
        String s = sbKirjailijat.toString();
        chooserKirjailijat.setRivit(s);
    }
    

    private void asetaComboBoxChooser(String kirjailijat) {
        chooserKirjailijat.setRivit(kirjailijat);
    }


    /**
     * @param kirjailija lisättävä kirjailija
     * @return kaikki kirjailijat
     */
    public String annaKirjailijat(String kirjailija) {
        sbKirjailijat.append(kirjailija);
        sbKirjailijat.append("\n");
        return sbKirjailijat.toString();
    }


    /**
     * Luodaan kysymisdialogi ja palautetaan sama tietue joko muutettuna tai nullina
     * @param modalityStage mille ollaan modaalisisa
     * @param oletus mitä dataan näytetään oletuksena
     * @param sbKirjailijatGUI StringBuilder kirjailijoista
     * @return null jos painetaan Peruuta, muuten täytetty tietue
     */
    public static Kirja kysyKirja(Stage modalityStage, Kirja oletus, StringBuilder sbKirjailijatGUI) {
       return ModalController.showModal(TietojenMuokkausController.class
                        .getResource("MuokkaaTietoja.fxml"),
                "Muokkaa Tietoja", modalityStage, oletus, asetaKirjailijatSB(sbKirjailijatGUI));
        
    }


    private static ModalInitializeInterface<Kirja, ?> asetaKirjailijatSB(
            StringBuilder sbKirjailijatGUI) {
        sbKirjailijat = new StringBuilder();
        sbKirjailijat.append(sbKirjailijatGUI);
        return null;
    }


    /**
     * @return kirjailija, null jos ei ole tehty uutta kirjailija Tietojen Muokkaus -ikkunassa.
     */
    public static Kirjailija getApukirjailija() {
        return apukirjailija;
    }
  

}

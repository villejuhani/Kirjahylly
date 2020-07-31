package fxKirjahylly;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import kirjahylly.Kirja;
import kirjahylly.Kirjahylly;
import kirjahylly.Kirjailija;
import kirjahylly.SailoException;

/**
 * @author Ville Hytönen ville.j.hytonen@student.jyu.fi
 * @version 31.7.2020
 *
 * TODO: kirjailijan poistaminen. 
 */
public class KirjahyllyGUIController {
    
    @FXML private TextField haku;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelKirja;
    @FXML private GridPane gridKirja; 
    @FXML private ListChooser<Kirja> chooserKirjat;
    @FXML private ComboBoxChooser<String> chooserJarjesta;
    @FXML private TextField editNimi;
    @FXML private TextField editKirjailija;
    @FXML private TextField editGenre;
    @FXML private TextField editJulkaisuvuosi;
    @FXML private TextField editSivumaara;
    @FXML private ComboBoxChooser<String> editTila;
    @FXML private ComboBoxChooser<Integer> editArvio;
    @FXML private DatePicker editPvm;
    @FXML private TextArea editKommentit;
    

    /**
     * alustetaan pääikkuna
     */
    public void initialize() {
        alusta();      
    }
    
    @FXML private void handleHaku () {
        hae(0);
        //String ehto = haku.getText();
        //if (ehto.isEmpty()) naytaVirhe(null);
        //else naytaVirhe("Ei osata vielä hakea");
    }

    
    @FXML void handleTallenna() {
        tallenna();
    }
    
    
    @FXML void handleMuokkaaTietoja() {
        //palautaTiedot();
        muokkaaTietoja();
    }
    
    

    @FXML void handleTietoja() {
        naytaTilastot();
        
    }
    
    
    @FXML void handleLisaaKirjailija() {
        lisaaKirjailija();
    } 
    
    
    @FXML void handleLisaaKirja() {
        lisaaKirja();
    }

    
    @FXML void handlePoistaKirja() {
        poistaKirja();
    }
    
    
    @FXML void handleTilastot() {
        naytaTilastot();
    }


    
    @FXML void handlePoistaKirjailija() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa");
    }
   

    @FXML void handleJarjesta() {
        if (kirjaKohdalla == null)
            Dialogs.showMessageDialog("Vielä ei osata järjestää");
        else hae(kirjaKohdalla.getTunnusNro());
    }

    
    @FXML void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    //=============================================================
    // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    private Kirjahylly kirjahylly;
    private Kirja kirjaKohdalla;
    private String kirjahyllynnimi = "kirjalista";
    private StringBuilder sbKirjailijat = new StringBuilder();
    
    
    
    /**
     * Tekee tarvittavat muut alustukset
     */
    protected void alusta() {
        panelKirja.setFitToHeight(true);
        
        chooserKirjat.clear();
        chooserKirjat.addSelectionListener(e -> naytaKirja());
        
    }
    
    /**
     * avataan Tietojen Muokkaus -ikkuna, viedään kirjaKohdalla, tuodaan muutokset kirjaan
     */
    private void muokkaaTietoja() {
        if ( kirjaKohdalla == null) return;
        alustaKirjailijatSB();
       try {
            Kirja kirja = TietojenMuokkausController.kysyKirja(null, kirjaKohdalla.clone(), sbKirjailijat ); 
            if (kirja == null) return;
            kirjahylly.korvaaTaiLisaa(kirja);
            
            Kirjailija kirjailija = TietojenMuokkausController.getApukirjailija();
            if (kirjailija != null) kirjahylly.korvaaTaiLisaa(kirjailija);
            
            hae(kirja.getTunnusNro());
        } catch (CloneNotSupportedException e) {
            //
        } 
        
    }
    
    
    /**
     * Näyttää listalta valitun kirjan tiedot
     */
    protected void naytaKirja() {
        kirjaKohdalla = chooserKirjat.getSelectedObject();
        
        if (kirjaKohdalla == null) return;
        
        editNimi.setText(kirjaKohdalla.getNimi());
        Kirjailija kirjailija = kirjahylly.annaKirjailija(kirjaKohdalla.getKirjailijaId());
        editKirjailija.setText(kirjailija.getNimi());
        editGenre.setText(kirjaKohdalla.getGenre());
        editJulkaisuvuosi.setText("" + kirjaKohdalla.getVuosi());
        editSivumaara.setText("" + kirjaKohdalla.getSivut());
        editTila.setRivit(kirjaKohdalla.getTila());
        editPvm.setPromptText(kirjaKohdalla.getPvm());
        editArvio.setRivit("" + kirjaKohdalla.getArvio());
        editKommentit.setText(kirjaKohdalla.getKommentit());
    }
    
    
    /**
     * Alustaa kirjahyllyn lukemalla sen valitun nimisestä tiedostosta
     * @return null jos onnistuu, muuten virhe
     */
    protected String lueTiedosto() {
        
        try {
            kirjahylly.lueTiedostosta(kirjahyllynnimi);
            hae(0);
            return null;
        } catch (SailoException e) {
            hae(0);
            String virhe = e.getMessage();
            if ( virhe != null) Dialogs.showMessageDialog(virhe);
            return virhe;
        }
        
    }
    
    
    /**
     * Hakee kirjojen tiedot listaan
     * @param knr kirjan numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(final int knr) {
        int knro = knr;
        if ( knro <= 0) {
            Kirja kohdalla = kirjaKohdalla;
            if ( kohdalla != null ) knro = kohdalla.getTunnusNro();
        }
        
        int k = chooserJarjesta.getSelectionModel().getSelectedIndex();
        String ehto = haku.getText();
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
        
        chooserKirjat.clear();
  
        int index = 0;
            var kirjat = kirjahylly.etsi(ehto, k);
            int i = 0;
            for (Kirja kirja: kirjat) {
                if (kirja.getTunnusNro() == knro) index = i;
                chooserKirjat.add(kirja.getNimi(), kirja);
                i++;
            }
        chooserKirjat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää kirjan
    }
    
    
    /**
     * Luo uuden kirjan jota aletaan editoimaan
     */
    protected void lisaaKirja(){
        Kirja uusi = new Kirja();
        alustaKirjailijatSB();
        uusi = TietojenMuokkausController.kysyKirja(null, uusi, sbKirjailijat);
        if ( uusi == null ) return;
        uusi.rekisteroi();
        try {
            kirjahylly.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa" + e.getMessage());
            return;
        }
        Kirjailija kirjailija = TietojenMuokkausController.getApukirjailija();
        if (kirjailija != null) kirjahylly.korvaaTaiLisaa(kirjailija);
        hae(uusi.getTunnusNro());
    }
    
    
    /**
     * Tekee uuden tyhjän kirjailijan editointia varten
     */
    protected void lisaaKirjailija() {
        if (kirjaKohdalla == null) return;
        String uusiKirjailija = LisaaKirjailijaController.lisaaNimi(null, "");
        if (uusiKirjailija == null)
            return;
        Kirjailija kirjailija = new Kirjailija(uusiKirjailija);
        kirjailija.rekisteroi();
        kirjahylly.korvaaTaiLisaa(kirjailija);
        kirjailijatLisaaSB(kirjailija.getNimi());
        kirjaKohdalla.lisaaKirjailija(kirjailija.getTunnusNro());
        hae(kirjaKohdalla.getTunnusNro());
    }
    
    
    /**
     * Alustaa kirjailijat ComboBoxille StringBuilderilla
     */
    private void alustaKirjailijatSB() {
        if (kirjaKohdalla == null) return;
        sbKirjailijat = new StringBuilder();
        Kirjailija eka = kirjahylly.annaKirjailija(kirjaKohdalla.getKirjailijaId());
        StringBuilder sb = new StringBuilder(eka.getNimi());
        
        List<Kirjailija> kirjailijat = kirjahylly.annaKirjailijat();
        for (Kirjailija kirjailija : kirjailijat) {
            sbKirjailijat.append(kirjailija.getNimi());
            sbKirjailijat.append("\n");
        }
        sb.append("\n");
        sb.append(sbKirjailijat);
        sbKirjailijat = new StringBuilder(sb);
        
        
    }
    
    
    /**
     * @param kirjailija lisättävä kirjailija
     */
    public void kirjailijatLisaaSB(String kirjailija) {
        sbKirjailijat.append(kirjailija);
        sbKirjailijat.append("\n");
    }
    
    
    /**
     * @param kirjahylly Kirjahylly jota käytetään tässä käyttöliittymässä
     */
    public void setKirjahylly(Kirjahylly kirjahylly) {
        this.kirjahylly = kirjahylly;
        naytaKirja();
    }
    
    
    /**
     * Poistetaan listalta valittu kirja
     */
    private void poistaKirja() {
        Kirja kirja = kirjaKohdalla;
        if ( kirja == null ) return;
        if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko kirja: " + kirja.getNimi(), "Kyllä", "Ei") )
            return;
        kirjahylly.poista(kirja);
        int index = chooserKirjat.getSelectedIndex();
        hae(0);
        chooserKirjat.setSelectedIndex(index);
    }
    
    
    /**
     * Näytetään Tietoja -ikkuna tilastoineen
     */
    private void naytaTilastot() {
        int luetut = 0;
        int sivut = 0;
        for (int i = 0; i < kirjahylly.getKirjoja(); i++) {
            if ( kirjahylly.annaKirja(i).getTila().equals("Luettu") ) luetut++;
            sivut += kirjahylly.annaKirja(i).getSivut();
        }
        
        String[] til = new String[] { "" + kirjahylly.getKirjoja(), "" + luetut, "" + sivut };
        TietojaController.avaa(null, til);
    }
    
    
    /**
     * Tulostaa kirjan tiedot
     * @param os tietovirta johon tulostetaan
     * @param kirja tulostettava kirja
     */
    public void tulosta(PrintStream os, final Kirja kirja) {
        os.println("----------------------------------------------");
        kirja.tulosta(os);
        os.println("----------------------------------------------");
        Kirjailija kirjailija = kirjahylly.annaKirjailijat(kirja);  
        kirjailija.tulosta(os);  
    }
    
    
    /**
     * Tulostaa listassa olevat kirjat tekstialueeseen
     * @param text alue johon tulostetaan
     */
    public void tulostaValitut(TextArea text) {
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
         os.println("Tulostetaan kaikki kirjat");
         Collection<Kirja> kirjat = kirjahylly.etsi("", -1);
         for (Kirja kirja: kirjat) {
             tulosta(os, kirja);
             os.println("\n\n");
         }
        } 
    }


    /**
     * tarkastetaan onko muutokset tallennettu
     * @return true jos tallennettu, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }

    
    /**
     * Tallennetaan tiedot
     * @return null jos onnistuu, muuten virhe
     */
    private String tallenna(){
        try {
            kirjahylly.tallenna();
            return null;
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
            return ex.getMessage();
        }
    }

  
}

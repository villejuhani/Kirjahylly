package fxKirjahylly;

import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import kirjahylly.Kirja;
import kirjahylly.Kirjahylly;
import kirjahylly.Kirjailija;
import kirjahylly.SailoException;

/**
 * @author ville
 * @version 9.7.2020
 *
 */
public class KirjahyllyGUIController {
    
    @FXML private TextField haku;
    @FXML private Label labelVirhe;
    @FXML private ScrollPane panelKirja;
    @FXML private ListChooser<Kirja> chooserKirjat;
    @FXML private ComboBoxChooser<String> chooserJarjesta;
    
    

    /**
     * alustetaan pääikkuna
     */
    public void initialize() {
        alusta();      
    }
    
    @FXML private void handleHaku () {
        String ehto = haku.getText();
        if (ehto.isEmpty()) naytaVirhe(null);
        else naytaVirhe("Ei osata vielä hakea");
    }

    
    @FXML void handleTallenna() {
        tallenna();
    }
    
    
    @FXML void handleMuokkaaTietoja() {
        palautaTiedot();
    }
    
    
    @FXML void handleTietoja() {
        ModalController.showModal(KirjahyllyGUIController.class.getResource("Tietoja.fxml"),
                "Tietoja", null, "");
    }
    
    
    @FXML void handleLisaaKirjailija() {
        lisaaKirjailija();
    } 
    
    
    @FXML void handleLisaaKirja() {
        lisaaKirja();
    }

    
    @FXML void handlePoistaKirja() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kirjaa");
    }

    
    @FXML void handlePoistaKirjailija() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kirjailijaa");
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
    private TextArea areaKirja = new TextArea();
    private String kirjahyllynnimi = "kirjalista";
    
    
    /**
     * Tekee tarvittavat muut alustukset, vaihdetan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa jäsenten tiedot.
     * Alustetaan myös jasenlistan kuuntelija
     */
    protected void alusta() {
        panelKirja.setContent(areaKirja);
        areaKirja.setFont(new Font("Courier New", 12));
        panelKirja.setFitToHeight(true);
        
        chooserKirjat.clear();
        chooserKirjat.addSelectionListener(e -> naytaKirja());
    }
   
    
    /**
     * Näyttää virheen jos haku epäonnistuu
     */
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
     * Näyttää listalta valitun jäsenen tiedot
     */
    protected void naytaKirja() {
        kirjaKohdalla = chooserKirjat.getSelectedObject();
        
        if (kirjaKohdalla == null) return;
        
        areaKirja.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaKirja)){
            tulosta(os, kirjaKohdalla);
        }
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
     * @param knro kirjan numero, joka aktivoidaan haun jälkeen
     */
    protected void hae(int knro) {
        int k = chooserJarjesta.getSelectionModel().getSelectedIndex();
        String ehto = haku.getText();
        if (k > 0 || ehto.length() > 0)
            naytaVirhe(String.format("Ei osata hakea", k, ehto));
        else
            naytaVirhe(null);
        
        chooserKirjat.clear();
  
        int index = 0;
        Collection<Kirja> kirjat;
        try {
            kirjat = kirjahylly.etsi(ehto, k);
            int i = 0;
            for (Kirja kirja: kirjat) {
                if (kirja.getTunnusNro() == knro) index = i;
                chooserKirjat.add(kirja.getNimi(), kirja);
                i++;
            }
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Jäsenen hakemisessa ongelmia! " + ex.getMessage());
        }
        chooserKirjat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }
    
    
    /**
     * Luo uuden kirjan jota aletaan editoimaan
     */
    protected void lisaaKirja(){
        Kirja uusi = new Kirja();
        uusi.rekisteroi();
        uusi.taytaSapiensTiedoilla();
        try {
            kirjahylly.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa" + e.getMessage());
            return;
        }
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
        Kirjailija kirjailija = new Kirjailija(uusiKirjailija, kirjaKohdalla.getTunnusNro());
        kirjailija.rekisteroi();
        kirjahylly.lisaa(kirjailija);
        hae(kirjaKohdalla.getTunnusNro());
    }
    
    
    /**
     * Avaa Tietojen muokkaus -ikkunan ja ottaa uudesta kirjailijasta kopin
     */
    private void palautaTiedot() {
        if (kirjaKohdalla == null) return;
        String uusiKirjailija = TietojenMuokkausController.annaKirjailija(null, "");
        if (uusiKirjailija == null) return;
        Kirjailija kirjailija = new Kirjailija(uusiKirjailija, kirjaKohdalla.getTunnusNro());
        kirjailija.rekisteroi();
        kirjahylly.lisaa(kirjailija);
        hae(kirjaKohdalla.getTunnusNro());
    }
    
    
    /**
     * @param kirjahylly jota käytetään tässä käyttöliittymässä
     */
    public void setKirjahylly(Kirjahylly kirjahylly) {
        this.kirjahylly = kirjahylly;
        naytaKirja();
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
        List<Kirjailija> kirjailijat = kirjahylly.annaKirjailijat(kirja);   
        for (Kirjailija kirj:kirjailijat)
            kirj.tulosta(os);  
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
        } catch (SailoException ex) {
            Dialogs.showMessageDialog("Kirjan hakemisessa ongelmia " + ex.getMessage());
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

    /*
    public void avaa() {
        //String nimi = LisaaKirjailijaController.lisaaNimi(null, "");
        lueTiedosto(nimi);
    }*/
  
}

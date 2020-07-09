package fxKirjahylly;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.stage.Stage;

/**
 * 
 * @author ville
 * @version 9.7.2020
 */
public class TietojenMuokkausController
        implements ModalControllerInterface<String> {

    
    @FXML
    private ComboBoxChooser<String> chooserKirjailijat;


    @FXML
    void handleLisaaKirjailija() {
        lisaaKirjailija();
    }


    @FXML
    void handleTallenna() {
        tallenna();
        ModalController.closeStage(chooserKirjailijat);
    }


    @FXML
    private void handlePeruuta() {
        ModalController.closeStage(chooserKirjailijat);
    }

    // ==================================================================================
    // Tästä eteenpäin ei käyttöliittymään suoraan liittyvää koodia
    
    private StringBuilder sbKirjailijat = new StringBuilder();
    private String vastaus;

    @Override
    public String getResult() {
        return vastaus;
    }


    @Override
    public void setDefault(String oletus) {
        //
    }


    /**
     * Mitä tehdään kun dialogi on näytetty
     */
    @Override
    public void handleShown() {
        //
    }


    private void tallenna() {
        vastaus = chooserKirjailijat.getSelectedText();
        Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä.");
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
        // Kirjailija kirjailija = new Kirjailija(uusiKirjailija,
        // kirjaKohdalla.getTunnusNro());
        // kirjailija.rekisteroi();
        asetaComboBoxChooser(annaKirjailijat(uusiKirjailija));
        // kirjahylly.lisaa(kirjailija);
        // hae(kirjaKohdalla.getTunnusNro());
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

}

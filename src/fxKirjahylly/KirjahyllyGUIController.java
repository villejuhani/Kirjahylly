package fxKirjahylly;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;

/**
 * @author ville
 * @version 10.6.2020
 *
 */
public class KirjahyllyGUIController {

    @FXML void handleTallenna() {
        tallenna();
    }
    
    @FXML void handleMuokkaaTietoja() {
        TietojenMuokkausController.avaa();
    }
        
    @FXML void handleLisaaKirjailija() {
        LisaaKirjailijaController.avaa();
    } 
    
    @FXML void handleTietoja() {
        ModalController.showModal(KirjahyllyGUIController.class.getResource("Tietoja.fxml"),
                "Tietoja", null, "");
    }
    
    @FXML void handleLisaaKirja() {
        Dialogs.showMessageDialog("Vielä ei osata lisätä kirjaa");
    }

    @FXML void handlePoistaKirja() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kirjaa");
    }

    @FXML void handlePoistaKirjailija() {
        Dialogs.showMessageDialog("Vielä ei osata poistaa kirjailijaa");
    }
    
    @FXML void handleJarjesta() {
        Dialogs.showMessageDialog("Vielä ei osata järjestää");
    }

    @FXML void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    //===================================================
    
    
    private void tallenna(){
        Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä.");
    }
    
    

}

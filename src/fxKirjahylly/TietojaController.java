package fxKirjahylly;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author Ville Hytönen ville.j.hytonen@student.jyu.fi
 * @version 31.7.2020
 *
 */
public class TietojaController implements ModalControllerInterface<String[]>{
    
    @FXML private Label tilKirjat;
    @FXML private Label tilLuetut;
    @FXML private Label tilSivut;
    
    
    @FXML void handleOK() {
        ModalController.closeStage(tilKirjat);
    }
    
    
    @Override
    public String[] getResult() {
        return null;
    }

    
    @Override
    public void handleShown() {
        //
        
    }

    @Override
    public void setDefault(String[] oletus) {
        tilKirjat.setText("Kirjat: " + oletus[0]);
        tilLuetut.setText("Luettu: " + oletus[1]);
        tilSivut.setText("Sivut: " + oletus[2]);
        
    }   
    
    
    /**
     * @param modalityStage mille ollaan modaalisia
     * @param oletus taulukko tilastoista
     */
    public static void avaa(Stage modalityStage, String[] oletus) {
        ModalController.showModal(TietojaController.class.getResource("Tietoja.fxml"),
                "Tietoja", modalityStage, oletus);
        
    }
     

}

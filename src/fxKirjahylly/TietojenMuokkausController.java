package fxKirjahylly;
  
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
  
  /**
   * 
   * @author ville
   * @version 10.6.2020
   */
  public class TietojenMuokkausController implements ModalControllerInterface<String> {
      
      @FXML TextArea kirjoitusAlue;
      
      @FXML private void handleOK() {
          ModalController.closeStage(kirjoitusAlue);
      }
      
      @FXML void handleLisaaKirjailija() {
          LisaaKirjailijaController.avaa();
      } 
      
      @FXML void handleTallenna() {
          tallenna();
      }
      
      @FXML private void handlePeruuta() {
          Platform.exit();
      }
      
      @Override
      public String getResult() {
          return null;
      } 
  
      
      @Override
      public void setDefault(String oletus) {
          if ( oletus == null ) return;
          kirjoitusAlue.setText(oletus);
      }
  
      
      /**
       * Mitä tehdään kun dialogi on näytetty
       */
      @Override
      public void handleShown() {
          //
      }
      
      private void tallenna(){
          Dialogs.showMessageDialog("Tallennetaan! Mutta ei toimi vielä.");
      }
      
      /**
       * Avaa ikkunan
       */
      public static void avaa() {
          ModalController.showModeless(LisaaKirjailijaController.class.getResource("MuokkaaTietoja.fxml"),
                  "Muokkaa tietoja", null);
      }
  
  }
  



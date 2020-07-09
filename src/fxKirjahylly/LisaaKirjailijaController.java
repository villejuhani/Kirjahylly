package fxKirjahylly;
  
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
  
  /**
   * 
   * @author ville
   * @version 9.7.2020
   */
  public class LisaaKirjailijaController implements ModalControllerInterface<String> {
      
      @FXML private TextField textVastaus;
      private String vastaus = null;
      
      @FXML private void handleTallenna() {
          vastaus = textVastaus.getText();
          ModalController.closeStage(textVastaus);
      }
      
      
      @FXML private void handlePeruuta() {
          ModalController.closeStage(textVastaus);
      }
      
      @Override
      public String getResult() {
          return vastaus;
      } 
  
      
      @Override
      public void setDefault(String oletus) {
          textVastaus.setText(oletus);
      }
      
      
      
      /**
       * Mitä tehdään kun dialogi on näytetty
       */
      @Override
      public void handleShown() {
          textVastaus.requestFocus();
      }
      
      
      /**
       * Avaa ikkunan
       * @param modalityStage mille ollaan modaalisia
       * @param oletus mitä nimeä käytetään oletuksena
       * @return null jos painetaan Peruuta, muuten kirjoitettu nimi
       */
      public static String lisaaNimi(Stage modalityStage, String oletus) {
          return ModalController.showModal(LisaaKirjailijaController.class.getResource("LisaaKirjailija.fxml"),
                  "Lisää kirjailija", modalityStage, oletus);
      }
      
  
  }
  



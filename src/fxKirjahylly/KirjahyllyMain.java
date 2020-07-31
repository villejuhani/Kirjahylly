package fxKirjahylly;
	
import javafx.application.Application;
import javafx.stage.Stage;
import kirjahylly.Kirjahylly;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Ville Hytönen ville.j.hytonen@student.jyu.fi
 * @version 31.7.2020
 *
 * 
 */
public class KirjahyllyMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("KirjahyllyGUIView.fxml"));
			
		    final FXMLLoader ldr = new FXMLLoader(getClass().getResource("KirjahyllyGUIView.fxml"));
		    final Pane root = (Pane)ldr.load();
		    KirjahyllyGUIController kirjahyllyCtrl = (KirjahyllyGUIController)ldr.getController();
			
		    final Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("kirjahylly.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Kirjahylly");
			
			Kirjahylly kirjahylly = new Kirjahylly();
			kirjahyllyCtrl.setKirjahylly(kirjahylly);
			
			primaryStage.setOnCloseRequest((event) -> {
			    if (!kirjahyllyCtrl.voikoSulkea() ) event.consume();
			});
			
			primaryStage.show();
			
			kirjahyllyCtrl.lueTiedosto();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Käynnistetään käyttöliittymä
	 * @param args komentorivin parametrit
	 */
	public static void main(String[] args) {
		launch(args);
	}
}

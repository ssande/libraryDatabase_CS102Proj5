//Vs 12-13-18 catches args errors. uses showSaveDialog, etc
package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	static String[] arg;
	@Override
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = new BorderPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/main.fxml"));
			Parent root = loader.load();
			//Scene scene = new Scene(root,400,400);
			MainControl control = loader.getController();
			control.setParameters(arg);
			control.setInitialDatabase();
			Scene scene = new Scene(root);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		arg = args;
		launch(args);
	}
}

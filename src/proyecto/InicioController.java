package proyecto;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author oscar
 */
public class InicioController implements Initializable {

        @FXML
	Button b_play;
	@FXML
	Pane b_root;

	public Stage stage;
	
	
	public void nextScene() {
		stage = (Stage) b_play.getScene().getWindow();
                Proyecto.stageMain.setScene(Proyecto.sceneLog);
	}
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

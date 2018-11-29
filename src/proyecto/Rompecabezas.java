package proyecto;


import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;







public class Rompecabezas {
	private Timeline timeline;

	public  void  init(Stage primaryStage) {
		Group root= new Group();
		primaryStage.setScene(new Scene(root));
		//load image
			Image image= new Image(getClass().getResourceAsStream("Car.jpg"));

		int numOfColumns = (int)(image.getWidth()/Piece.SIZE);
		int numOfRows = (int)(image.getHeight()/Piece.SIZE);
		//create desk
		final Desk desk = new Desk(numOfColumns,numOfRows);
		//create puzzle pieces
		final List<Piece> pieces = new ArrayList<Piece>();
		for(int col= 0;col<numOfColumns;col ++){
			for(int row= 0;row<numOfRows;row++){
				int x=col*Piece.SIZE;
				int y=row*Piece.SIZE;
				 final Piece piece = new Piece(image, x, y, row>0, col>0,
	                        row<numOfRows -1, col < numOfColumns -1,
	                        desk.getWidth(), desk.getHeight());
	                pieces.add(piece);
			}
		}

		desk.getChildren().addAll(pieces);

		//create button box
		Button shuffleButton = new Button("Revolver");
		shuffleButton.setStyle("-fx-font-size: 2em;-fx-background-color: red");
		shuffleButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent actionEvent){
				if(timeline !=null) timeline.stop();
				timeline=new Timeline();
				for(final Piece piece:pieces){
					piece.setActive();
					double shuffleX=Math.random() *
							(desk.getWidth() - Piece.SIZE+ 48f)-
							24f - piece.getCorrectX();
					double shuffleY=Math.random() *
							(desk.getHeight() - Piece.SIZE+ 30f)-
							15f - piece.getCorrectY();
					timeline.getKeyFrames().add(
							new KeyFrame(Duration.seconds(1),
									new KeyValue(piece.translateXProperty(), shuffleX),
									new KeyValue(piece.translateYProperty(), shuffleY)));
				}
				timeline.playFromStart();
			}
		});
		Button solveButton = new Button("Resolver");
        solveButton.setStyle("-fx-font-size: 2em;-fx-background-color: blue");
        solveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                if (timeline != null) timeline.stop();
                timeline = new Timeline();
                for (final Piece piece : pieces) {
                    piece.setInactive();
                    timeline.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(1),
                            new KeyValue(piece.translateXProperty(), 0),
                            new KeyValue(piece.translateYProperty(), 0)));
                }
                timeline.playFromStart();
            }
        });

        HBox buttonBox=new HBox(8);
        buttonBox.getChildren().addAll(shuffleButton,solveButton);
        //create vbox for desk and buttons
        VBox vb= new VBox(10);


        HBox bb2=new HBox(10);
        //Crear Titulo
        HBox bb3=new HBox(30);
        Text tf=new Text("Rompecabezas");
        tf.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        tf.setFill(Color.YELLOW);
        bb3.getChildren().add(tf);



        //Agregar a vbox todo
        vb.getChildren().addAll(bb3,desk,buttonBox);
        Insets inset = new Insets(30);
		vb.setPadding(inset);
		vb.setBackground(new Background(new BackgroundFill(Color.rgb(168, 175, 161, 0.93), CornerRadii.EMPTY, Insets.EMPTY)));
        root.getChildren().addAll(vb);





	}


	

	

	





	
}

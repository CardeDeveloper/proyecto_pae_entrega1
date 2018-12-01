import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Puzzle  extends Application{
	public static Game game;
	private Timeline timeline;
	private final Integer startTime= 0;
	private Integer seconds=startTime;
	private Label labeltime;
	static String img;
	

	public Scene init(Stage primaryStage,Scene menu, String str) {
		Group root= new Group();
		Scene scene1=new Scene(root);
		//load image


		Image image= new Image(getClass().getResourceAsStream(str));

		//Cronometro
		labeltime = new Label();
		labeltime.setFont(Font.font(20));
		Timeline time= new Timeline();
		time.setCycleCount(Timeline.INDEFINITE);
		if(time!=null)
			time.stop();
		KeyFrame frame= new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				seconds++;

				labeltime.setText("Tiempo: "+seconds.toString()+" segundos");
			}
		});
		
		
		
		time.getKeyFrames().add(frame);
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
		shuffleButton.setStyle("-fx-background-color: #3c7fb1,linear-gradient(#fafdfe, #e8f5fc), linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);-fx-background-insets: 0,1,2;-fx-background-radius: 10,3,2;-fx-padding: 3 30 3 30;-fx-text-fill: black;-fx-font-size: 20px;");
		shuffleButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent actionEvent){
				if(timeline !=null) timeline.stop();
				//Cronometro iniciar
				seconds=0;
				time.playFromStart();

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
		solveButton.setStyle("-fx-background-color: #3c7fb1,linear-gradient(#fafdfe, #e8f5fc), linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);-fx-background-insets: 0,1,2;-fx-background-radius: 10,3,2;-fx-padding: 3 30 3 30;-fx-text-fill: black;-fx-font-size: 20px;");
		solveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent actionEvent) {
				if (timeline != null) timeline.stop();
				//Cronometro parar
				time.stop();

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

		//Boton para regresar a menu
		Button returnButton=new Button("Regresar");
		returnButton.setStyle("-fx-background-color: #3c7fb1,linear-gradient(#fafdfe, #e8f5fc), linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);-fx-background-insets: 0,1,2;-fx-background-radius: 10,3,2;-fx-padding: 3 30 3 30;-fx-text-fill: black;-fx-font-size: 20px;");
		//Accion de return
		returnButton.setOnAction(e->primaryStage.setScene(menu));

		//Hbox para meter botones principales
		HBox buttonBox=new HBox(8);
		buttonBox.getChildren().addAll(shuffleButton,solveButton,returnButton);
		//create vbox for desk and buttons
		VBox vb= new VBox(10);

		//Hbox para cronometro
		HBox bb2=new HBox(10);
		bb2.getChildren().addAll(labeltime);
		//Crear Titulo
		HBox bb3=new HBox(30);
		Text tf=new Text("Rompecabezas");
		tf.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		tf.setFill(Color.YELLOW);
		bb3.getChildren().add(tf);

		//Agregar a vbox todo principal
		vb.getChildren().addAll(bb3,bb2,desk,buttonBox);
		Insets inset = new Insets(30);
		vb.setPadding(inset);
		vb.setBackground(new Background(new BackgroundFill(Color.rgb(168, 175, 161, 0.93), CornerRadii.EMPTY, Insets.EMPTY)));
		root.getChildren().addAll(vb);


		return scene1;




	}
	@Override public void start(Stage primaryStage)throws Exception{
		Preferences userPref = Preferences.userRoot();

		game = new Game(Integer.parseInt(userPref.get("id", "")));
		
		//primer stage para elegir la imagen

		//titulo/botones/choice con sus estilos
		Text label=new Text("Elige rompecabezas");
		Button btn1=new Button("Elegir");
		VBox layout=new VBox(20);
		Scene menuElegir=new Scene(layout,500,300);
		Insets inset = new Insets(30);
		layout.setPadding(inset);
		layout.setBackground(new Background(new BackgroundFill(Color.rgb(168, 175, 161, 0.93), CornerRadii.EMPTY, Insets.EMPTY)));
		ChoiceBox<String> pickem=new ChoiceBox<String>((FXCollections.observableArrayList("FatTiger", "Minecraft","Car","Cupcake")));
		btn1.setStyle("-fx-background-color: #3c7fb1,linear-gradient(#fafdfe, #e8f5fc), linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);-fx-background-insets: 0,1,2;-fx-background-radius: 10,3,2;-fx-padding: 3 30 3 30;-fx-text-fill: black;-fx-font-size: 20px;");
		pickem.setStyle("-fx-background-color: #3c7fb1,linear-gradient(#fafdfe, #e8f5fc), linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);-fx-background-insets: 0,1,2;-fx-background-radius: 10,3,2;-fx-padding: 3 30 3 30;-fx-text-fill: black;-fx-font-size: 20px;");
		label.setFont(Font.font("Verdana", FontWeight.BOLD, 40));
		label.setFill(Color.YELLOW);


		//accion para elegir la imagen segun la choicebox
		btn1.setOnAction(e->{
			if(pickem.getValue()=="FatTiger"){
				img="Fatiger.jpg";
				primaryStage.setScene(init(primaryStage,menuElegir,img));
			}
			else if(pickem.getValue()=="Minecraft"){
				img="Minecraft.jpg";
				primaryStage.setScene(init(primaryStage,menuElegir,img));
			}
			else  if(pickem.getValue()=="Car"){
				img="Car.jpg";
				primaryStage.setScene(init(primaryStage,menuElegir,img));
			}
			else{
				img="cupcake.JPG";
				primaryStage.setScene(init(primaryStage,menuElegir,img));
			};});

		//agregar todo a la vbox del menu

		layout.getChildren().addAll(label,pickem,btn1);


		//enseñar el menu
		primaryStage.setScene(menuElegir);

		//titulo del stage
		primaryStage.setTitle("Rompecabezas");

		primaryStage.show();
	}





	public static void main(String[] args) {
		launch(args);
	}
}

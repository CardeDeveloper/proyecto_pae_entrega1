package proyecto;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author oscar
 */
public class Proyecto extends Application {
    public static Scene sceneInicio, sceneLog, sceneRegister, sceneProfile;
    public static Stage stageMain;
    public Timeline timeline;
    
    Pane root;
    Rectangle player, bot;
    Circle ball;
    Line line;
    AnimationTimer timer;
    
    private int speedx=3, speedy= 3 ,dv= speedx, dy= speedy;
    private final int width=1000 , height= 600;
    private final int rectangleHeight = 80, rectangleWidth = 10;
    
    private Parent createContent(){
        root= new Pane();
        root.setPrefSize(width, height);
        root.setStyle("-fx-background-color: black");
        
        line = new Line(width / 2 , 0 , width/2 , height);
        line.setStroke(Color.WHITE);
        
        bot= new Rectangle(rectangleWidth, rectangleHeight, Color.WHITE);
        bot.setLayoutX(0);
        bot.setLayoutY(height/2 -40);
        
        player= new Rectangle(rectangleWidth, rectangleHeight, Color.WHITE);
        player.setLayoutX(width -10);
        player.setLayoutY(height/2 -40);
        
        ball = new Circle(5);
        ball.setFill(Color.WHITE);
        ball.setLayoutX(width/2);
        ball.setLayoutY(height/2);
        
        
        root.getChildren().addAll(line, bot, player, ball);
        
        
        timer = new AnimationTimer(){
            @Override
            public void handle (long now){
                gameUpdate();
                
            }
        };
        timer.start();
        
        
        return root;
    }
    
    private void gameUpdate(){
        double x= ball.getLayoutX(), y = ball.getLayoutY();
        
        if(x<= 10 && y > bot.getLayoutY() && y<bot.getLayoutY() + 80) dv= speedx;
        if (x>= width-12.5 && y>player.getLayoutY() && y< player.getLayoutY() +80) {
            speedx++;
            dv =- speedx;     
        }
        //evento cuando alguien pierde
        if (ball.getLayoutX()> width) System.out.println("alguien perdio");
        if (y<= 0 ) dy = speedy ;
        if (y>= height -5) dy =- speedy;
        
        //System.out.println(ball.getLayoutX());
        ball.setLayoutX(ball.getLayoutX()+ dv);
        //System.out.println(ball.getLayoutX());
        //System.out.println(ball.getLayoutY());
        ball.setLayoutY(ball.getLayoutY()+ dy);
        //System.out.println(ball.getLayoutY());
        
        if(x<width/2 && bot.getLayoutY() >y) bot.setLayoutY(bot.getLayoutY()-5);
        if(x<width/2 && bot.getLayoutY() +80 <y) bot.setLayoutY(bot.getLayoutY()+5);
        
    }//end pong
    
  

	public  Parent  rompecabezas() {
		Group root= new Group();
		//primaryStage.setScene(new Scene(root));
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
        
        return root;





	}
    
    @Override
    public void start(Stage window) throws Exception {
        
        /////////////////////////// INICIO ///////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
		
	Parent inicio_root = FXMLLoader.load(getClass().getResource("Titulo.fxml"));
	sceneInicio = new Scene(inicio_root, 600, 400);
        
        
        /////////////////////////// Menu ///////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
        
        //layout
		VBox root=new VBox(15);
		Insets inset = new Insets(70);
		root.setPadding(inset);

		//Controls
		Text t=new Text("Menu");
        t.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        t.setFill(Color.YELLOW);

		Text t1=new Text("For Fun");
		t1.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

		Button b1=new Button("Pong");
		b1.setStyle("-fx-padding: 8 15 15 15;-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;-fx-background-radius: 8;-fx-background-color:  linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); -fx-font-weight: bold;-fx-font-size: 2em;-fx-text-fill: white");

		Button b2=new Button("Rompecabezas");
		b2.setStyle("-fx-padding: 8 15 15 15;-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;-fx-background-radius: 8;-fx-background-color:  linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); -fx-font-weight: bold;-fx-font-size: 2em;-fx-text-fill: white");

		Text t2=new Text("Usuario");
		t2.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

		Button b3=new Button("Perfil");
		b3.setStyle("-fx-padding: 8 15 15 15;-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;-fx-background-radius: 8;-fx-background-color:  linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); -fx-font-weight: bold;-fx-font-size: 2em;-fx-text-fill: white");
                
                //switching scene
                b1.setOnAction((e) -> {
                    Scene scenePong = new Scene(createContent());
                    window.setScene(scenePong);
                    window.getScene().setOnKeyPressed(event ->{
                        if (event.getCode() == KeyCode.UP) player.setLayoutY(player.getLayoutY() - 30);
                        if (event.getCode() == KeyCode.DOWN) player.setLayoutY(player.getLayoutY() + 30);
                    });
                        });
                b2.setOnAction((e) -> {
                    window.setScene(new Scene(rompecabezas()));
                   
                        });
                b3.setOnAction(e_-> window.setScene(sceneProfile));
                
		//Add to layout
		root.getChildren().addAll(t,t1,b1,b2,t2,b3);
		root.setAlignment(Pos.CENTER);
		root.setBackground(new Background(new BackgroundFill(Color.rgb(168, 175, 161, 0.93), CornerRadii.EMPTY, Insets.EMPTY)));

		//scene
		Scene menuScene=new Scene(root,600,400);
        
        
        ////////////////////// LOGIN /////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////
	//*****Root layout********
	FlowPane log_root = new FlowPane();
	log_root.setOrientation(Orientation.VERTICAL);
	log_root.setAlignment(Pos.TOP_CENTER);
	log_root.setHgap(100);
	String backgroundStyle = "-fx-background-color: rgba(168, 175, 161, 0.93);";
	log_root.setStyle(backgroundStyle);
        
        //<<<Children>>>>>>>>

	//Greeting label
	Label l_greet = new Label("¡Bienvenido!");
	l_greet.setStyle("-fx-font:50 Forte;");
	l_greet.setTextFill(Color.rgb(230, 204, 58, 0.91));
	l_greet.setAlignment(Pos.TOP_CENTER);
	l_greet.setPadding(new Insets(10));


	//**Fireworks icon layout**
	HBox imgLayout	= new HBox();
	imgLayout.setSpacing(210);
	//Icons making things pretty
	Class<?> c = this.getClass();
	InputStream input = c.getResourceAsStream("fireworks.png");
	Image image = new Image(input, 65, 65, false, false);
	ImageView img1 = new ImageView(image);
	img1.setRotate(img1.getRotate() -110);
	ImageView img2 = new ImageView(image);
	img2.setRotate(img2.getRotate() +110);
	imgLayout.getChildren().addAll(img1, img2);


	//***User-psw layout*****
	GridPane userLayout= new GridPane();

	//User-psw label
	Label l_user = new Label("Usuario");
	l_user.setFont(new Font("Gill Sans MT", 20));

	Label l_psw = new Label("Contraseña");
	l_psw.setFont(new Font("Gill Sans MT", 20));

	TextField tf_user = new TextField();
	PasswordField tf_psw = new PasswordField();

	userLayout.add(l_user, 0,0);
	userLayout.add(tf_user, 1,0);
	userLayout.add(l_psw, 0, 1);
	userLayout.add(tf_psw, 1, 1);

	userLayout.setPadding(new Insets(30));
	userLayout.setVgap(10.0);
	userLayout.setHgap(5.0);
		
		
	//****Entry layout****
	VBox entryLayout = new VBox();
	entryLayout.setAlignment(Pos.CENTER);
		
	//Enter button
	Button b_entrar = new Button("Entrar");
	String st_entrar = new String("-fx-background-color: \r\n" + 
				"        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\r\n" + 
				"        #9d4024,\r\n" + 
				"        #d86e3a,\r\n" + 
				"        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\r\n" + 
				"    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\r\n" + 
				"    -fx-font-weight: bold;\r\n" + 
				"    -fx-font-size: 1.5em;");
	b_entrar.setStyle(st_entrar);
	b_entrar.setAlignment(Pos.CENTER);
	
	//switching scene
	b_entrar.setOnAction(e_-> window.setScene(menuScene));
		
		
		
	//Not user label
	Label l_reg = new Label("¿No tienes usuario?");
	l_reg.setStyle("-fx-font:20 Consolas");
		
	//Regist button
	Button b_regist = new Button("Registrarme");
	String st_reg = new String(" -fx-background-color:\r\n" + 
				"        linear-gradient(#f0ff35, #a9ff00),\r\n" + 
				"        radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);\r\n" + 
				"    -fx-background-radius: 6, 5;\r\n" + 
				"    -fx-background-insets: 0, 1;\r\n" + 
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\r\n" + 
				"    -fx-text-fill: #395306;" + 
				"    -fx-font-size: 1.2em;");
	b_regist.setStyle(st_reg);
	entryLayout.setSpacing(10);
	entryLayout.getChildren().addAll(b_entrar, l_reg, b_regist);
		
	//switching scene
	b_regist.setOnAction(e -> window.setScene(sceneRegister));
		
	//adding all children
	log_root.getChildren().addAll(l_greet, imgLayout, userLayout, entryLayout);
	//asigning scene with rootLayout
	sceneLog = new Scene(log_root, 600 ,400);
		
	/////////////////////////// REGISTRO ///////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////
	FlowPane reg_root = new FlowPane();
	reg_root.setOrientation(Orientation.HORIZONTAL);
	reg_root.setHgap(30);
	String bksty = "-fx-background-color: rgba(168, 175, 161, 0.93);";
	reg_root.setStyle(bksty);
		
		
		
	//***PeopleIcon Layout*******
		
	FlowPane iconLayout = new FlowPane();
	iconLayout.setOrientation(Orientation.VERTICAL);
	iconLayout.setVgap(10);
		
	InputStream inp1 = c.getResourceAsStream("p1.png");
	Image p1 = new Image(inp1, 70, 70, false, false);
	ImageView viewP1 = new ImageView(p1); 
		
	InputStream inp3 = c.getResourceAsStream("p3.png");
	Image p3 = new Image(inp3,70, 70, false, false);
	ImageView viewP3 = new ImageView(p3); 
		
	InputStream inp4 = c.getResourceAsStream("p4.png");
	Image p4= new Image(inp4, 70, 70, false, false);
	ImageView viewP4 = new ImageView(p4); 
		
	InputStream inp5 = c.getResourceAsStream("p5.png");
	Image p5 = new Image(inp5, 70, 70, false, false);
	ImageView viewP5 = new ImageView(p5); 
		
	InputStream inp6 = c.getResourceAsStream("p6.png");
	Image p6 = new Image(inp6,70, 70, false, false);
	ImageView viewP6 = new ImageView(p6); 
		
	iconLayout.getChildren().addAll(viewP1, viewP3, viewP4, viewP5, viewP6);		
		
		
	//***Data-insertion Layout*****
	VBox dataLayout = new VBox();
	dataLayout.setSpacing(10);
		
	//<<Children>>
		
	//name
	Label l_name = new Label("Nombre");
	l_name.setFont(new Font("Gill Sans MT", 20));
		
	TextField tf_name = new TextField();
		
		
	//Birthdate
	Label l_birthDate = new Label("Fecha de nacimiento");
	l_birthDate.setFont(new Font("Gill Sans MT", 20));
		
	DatePicker dp_birthDate = new DatePicker();
		
		
	//Sex
	Label l_sex = new Label("Sexo");
	l_sex.setFont(new Font("Gill Sans MT", 20));
		
	ChoiceBox<String> cb_sex = new ChoiceBox<String>(FXCollections.observableArrayList("Hombre", "Mujer", "Otro"));
		
		
	//User
	Label l_usuario = new Label("Usuario");
	l_usuario.setFont(new Font("Gill Sans MT", 20));
		
	TextField tf_usuario = new TextField();
		
		
	//Password
	Label l_password = new Label("Contraseña");
	l_password.setFont(new Font("Gill Sans MT", 20));

	PasswordField tf_password = new PasswordField();

		
		
		
	//Registrar
	Button b_reg = new Button("Registrar");
	String reg_sty = new String("-fx-background-color: \r\n" + 
				"        #99ffbb,\r\n" + 
				"        linear-gradient(#d6d6d6 50%, white 100%),\r\n" + 
				"        radial-gradient(center 50% -40%, radius 200%, #e6e6e6 45%, rgba(230,230,230,0) 50%);\r\n" + 
				"    -fx-background-radius: 30;\r\n" + 
				"    -fx-background-insets: 0,1,1;\r\n" + 
				"    -fx-text-fill: black;\r\n" + 
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 3, 0.0 , 0 , 1 );"+ 
				"    -fx-font-size: 1.2em;");
	b_reg.setStyle(reg_sty);
		
	dataLayout.getChildren().addAll(l_name, tf_name, l_birthDate, dp_birthDate, l_sex, cb_sex, l_usuario, tf_usuario, l_password, tf_password, b_reg);
		
		
	reg_root.getChildren().addAll(iconLayout, dataLayout);
		
		
	sceneRegister = new Scene(reg_root, 600, 400);	
	
        
        //////////////////////////PERFIL//////////////////////////
		///////////////////////////////////////////////////////\
		//////////////////////////////////////////////////////\

		HBox root_menu = new HBox();
		String styMenu = new String("-fx-background-color: rgba(168, 175, 161, 0.93);");
		root_menu.setStyle(styMenu);

		//LeftSide == PERFIL DATA
		VBox profileLyout = new VBox();
		Label l_profTitle = new Label("Tu perfil");
		l_profTitle.setFont(new Font("Gill Sans MT", 50));
		l_profTitle.setTextFill(Color.rgb(230, 204, 58, 0.91));
		profileLyout.setPadding(new Insets(30));
		profileLyout.setSpacing(20);

		//name
		Label l_profName = new Label("Nombre");
		l_profName.setFont(new Font("Gill Sans MT", 20));

		Label l_profDataName = new Label("insert name of user from database");

		//edad
		Label l_profEdad= new Label("Edad");
		l_profEdad.setFont(new Font("Gill Sans MT", 20));

		Label l_profDataEdad = new Label("insert age of user from database");


		//Fecha nacim
		Label l_profNacimiento = new Label("Fecha Nacimiento");
		l_profNacimiento.setFont(new Font("Gill Sans MT", 20));

		Label l_profDataFecha = new Label("insert birthdate of user from database");

		//back arrow Icon
		InputStream intpArrow = c.getResourceAsStream("arrow.png");
		Image arrow = new Image(intpArrow, 50, 50, false, false);
		ImageView backArrow = new ImageView(arrow); 
		backArrow.setRotate(180.0);

		backArrow.setOnMouseClicked(e -> window.setScene(menuScene));



		profileLyout.getChildren().addAll(l_profTitle, l_profName, l_profDataName, l_profEdad, l_profDataEdad, l_profNacimiento, l_profDataFecha,backArrow);



		//RightSide == Statistics
		VBox statisticLayout = new VBox();
		Label l_statTitle = new Label("Estadísticas");
		l_statTitle.setFont(new Font("Gill Sans MT", 50));
		l_statTitle.setTextFill(Color.rgb(46, 117, 182));
		statisticLayout.setPadding(new Insets(30));
		statisticLayout.setSpacing(10);

		CategoryAxis xAxis = new CategoryAxis();
		xAxis.setLabel("Day");

		      NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Time");

		      BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);

		//data
		XYChart.Series<String, Number> percData = new XYChart.Series<String, Number>();

		percData.setName("Time");
		percData.getData().add(new XYChart.Data<String, Number>("Mon", 30));
		percData.getData().add(new XYChart.Data<String, Number>("Tue", 18));
		percData.getData().add(new XYChart.Data<String, Number>("Wed", 6));
		percData.getData().add(new XYChart.Data<String, Number>("Thu", 16));
		percData.getData().add(new XYChart.Data<String, Number>("Fry", 4));

		ObservableList<XYChart.Series<String,Number>> data = FXCollections.<XYChart.Series<String, Number>>observableArrayList();
		data.add(percData);

		chart.setData(data);
		chart.setMaxWidth(210.0);
		chart.setMaxHeight(140);
		chart.setTitle("Horas jugadas");




		      ObservableList<PieChart.Data> tech = FXCollections.observableArrayList(
				new PieChart.Data("Win", 40),
				new PieChart.Data("Tie", 15),
				new PieChart.Data("Lose", 35)
				);

		PieChart piechart = new PieChart(tech);
		piechart.setTitle("Desempeño");
		piechart.setClockwise(true);


		piechart.setMaxWidth(210.0);
		piechart.setMaxHeight(140);

		piechart.setLegendVisible(true);
		piechart.setAnimated(true);





		statisticLayout.getChildren().addAll(l_statTitle, chart, piechart);




		root_menu.getChildren().addAll(profileLyout, statisticLayout);
		sceneProfile = new Scene(root_menu, 600, 400);
        
		
        //linking scene with stage and showing
	window.setScene(sceneInicio);
	window.show();
        stageMain = window;
        
        
    }
    
    


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

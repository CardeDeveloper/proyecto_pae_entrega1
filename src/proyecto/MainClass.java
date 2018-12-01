import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;



public class MainClass extends Application{



	public static Scene sceneInicio, sceneLog, sceneRegister, sceneProfile, sceneMasterMind, sceneMenu;
	public static Stage stageMain;
	public Timeline timeline;

	Pane root;
	Rectangle player, bot;
	Circle ball;
	Line line;
	AnimationTimer timer;


	public static int score = 1000;
	public static Circulito source;
	public static Circulito[] secretCode;
	public static int roundCount;
	public static ArrayList<Circulito> encryptedCode;
	public static boolean win = false;


	////////////////////////////Pong//////////////////////////////////////
	////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////
	private int speedx=3, speedy= 3 ,dv= speedx, dy= speedy;
	private final int width=1000 , height= 600;
	private final int rectangleHeight = 80, rectangleWidth = 10;
	public static Preferences userPref = Preferences.userRoot();
	Game game;



	public ArrayList<Circulito> makeItSecret(ArrayList<Circulito> listCirc) {

		ArrayList<Integer> generados = new ArrayList<Integer>();

		int[] colors = new int[4];//turquesa, azul, rosa, tinto, naranja
		for(int i = 0; i < colors.length; i++) {
			int rand = (int) (Math.random()*4) ;
			boolean unique = false;

			while(!unique) {
				if(!generados.contains(rand)) {
					generados.add(rand);
					unique = true;
				}
				else
					rand = (int) (Math.random()*5) ;
			}

		}


		int n = 0;
		for(int g : generados) {
			colors[n] = g;
			n++;
		}
		System.out.println(Arrays.toString(colors));

		//clean var
		n=0;
		for(Circulito circ : listCirc) {

			if(colors[n] == 0) {
				circ.setFill(Color.DARKTURQUOISE);
				circ.setCode("turquesa");
			}

			else if(colors[n]== 1) {
				circ.setFill(Color.CORNFLOWERBLUE);
				circ.setCode("azul");


			}

			else if(colors[n] == 2) {
				circ.setFill(Color.DEEPPINK);
				circ.setCode("rosa");
			}

			else if(colors[n]== 3) {
				circ.setFill(Color.MAROON);
				circ.setCode("tinto");

			}

			else if(colors[n] == 4) {
				circ.setFill(Color.ORANGE);
				circ.setCode("naranja");
			}

			int j = 1;
			for(Circulito k : listCirc) {
				k.setPosition(j);
				j++;
			}

			n++;
			System.out.println("Circulito posicion" + circ.getPosition());
		}


		return listCirc;

	}


	private Parent createContent(){
		//session of specific user id
		game = new Game(Integer.parseInt(userPref.get("id", "")));

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
		if (x> width ){ 
			game.setScore(0.0);
			game.setEndTime();
			game.save();
			System.out.println("perdiste");
			timer.stop();

			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Perdiste", ButtonType.OK);
			alert.show();
//			w.setScene(sceneMenu);
			if (alert.getResult() == ButtonType.OK) {
				//do stuff
			}
		}

		if (x<0) {
			game.setScore(1.0);
			game.setEndTime();
			game.save();
			System.out.println("ganaste");
			timer.stop();

			Alert alert = new Alert(Alert.AlertType.INFORMATION, "Ganaste", ButtonType.OK);
			alert.show();

			if (alert.getResult() == ButtonType.OK) {
				//do stuff
			}
		}
		if (y<= 0 ) dy = speedy ;
		if (y>= height -5) dy =- speedy;

		//System.out.println(ball.getLayoutX());
		ball.setLayoutX(ball.getLayoutX()+ dv);
		//System.out.println(ball.getLayoutX());
		//System.out.println(ball.getLayoutY());
		ball.setLayoutY(ball.getLayoutY()+ dy);
		//System.out.println(ball.getLayoutY());
		int numero = (int) (Math.random() * 1);
		if(x<width/2 && bot.getLayoutY() >y) bot.setLayoutY(bot.getLayoutY()-(6 + numero));
		if(x<width/2 && bot.getLayoutY() +80 <y) bot.setLayoutY(bot.getLayoutY()+(6 + numero));

	}//end pong


	//////////////////////////////Rompecabezas/////////////////////
	///////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////
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


	////////////////////////////////////Mastermind/////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////






	@Override
	public void start(Stage window) throws Exception {

		/////////////////////////// INICIO ///////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////


		Pane inicio_root = new Pane();
		inicio_root.setStyle("-fx-background-color: rgba(168, 175, 161, 0.93);");


		Button b_play = new Button("¡JUGAR!");
		b_play.setStyle("-fx-background-color: rgb(226, 128, 0);");
		b_play.setFont(new Font("Arial Rounded MT Bold", 21));
		b_play.setLayoutX(233);
		b_play.setLayoutY(233);


		Label l_t1 = new Label("mini -");
		l_t1.setLayoutX(119);
		l_t1.setLayoutY(50);
		l_t1.setFont(new Font("Bookman Old Style", 46));

		Label l_t2 = new Label("¿Juegas?");
		l_t2.setLayoutX(193);
		l_t2.setLayoutY(78);
		l_t2.setFont(new Font("Britannic Bold", 86));		




		Class<?> clInicio = this.getClass();

		InputStream inpPacman = clInicio.getResourceAsStream("pacman.png");
		Image imgPac = new Image(inpPacman, 63, 56, false, false);
		ImageView imgViewPac = new ImageView(imgPac);
		imgViewPac.setLayoutX(265);
		imgViewPac.setLayoutY(318);
		imgViewPac.setPickOnBounds(true);



		InputStream inpCoin = clInicio.getResourceAsStream("coin.png");
		Image imgCoin = new Image(inpCoin, 27, 32, false, false);
		ImageView imgViewCoin1 = new ImageView(imgCoin);
		imgViewCoin1.setLayoutX(351);
		imgViewCoin1.setLayoutY(333);
		imgViewCoin1.setPickOnBounds(true);
		imgViewCoin1.setPreserveRatio(true);


		ImageView imgViewCoin2 = new ImageView(imgCoin);
		imgViewCoin2.setLayoutX(400);
		imgViewCoin2.setLayoutY(333);
		imgViewCoin2.setPickOnBounds(true);
		imgViewCoin2.setPreserveRatio(true);

		ImageView imgViewCoin3 = new ImageView(imgCoin);
		imgViewCoin3.setLayoutX(400);
		imgViewCoin3.setLayoutY(333);
		imgViewCoin3.setPickOnBounds(true);
		imgViewCoin3.setPreserveRatio(true);

		InputStream inpFirew = clInicio.getResourceAsStream("fireworks.png");
		Image imgFirew = new Image(inpFirew, 118, 118, false, false);
		ImageView imgViewFirew = new ImageView(imgFirew);
		imgViewFirew.setLayoutX(67);
		imgViewFirew.setLayoutY(94);
		imgViewFirew.setPickOnBounds(true);
		imgViewFirew.setPreserveRatio(true);
		imgViewFirew.setRotate(-109.4);


		InputStream inpPuz = clInicio.getResourceAsStream("puzzle.png");
		Image imgPuz = new Image(inpPuz, 110, 110, false, false);
		ImageView imgViewPuz = new ImageView(imgPuz);
		imgViewPuz.setLayoutX(64);
		imgViewPuz.setLayoutY(242);
		imgViewPuz.setPickOnBounds(true);
		imgViewPuz.setPreserveRatio(true);
		imgViewPuz.setRotate(-18.4);


		InputStream inpPing = clInicio.getResourceAsStream("ping-pong.png");
		Image imgPing = new Image(inpPing, 116, 109, false, false);
		ImageView imgViewPing= new ImageView(imgPing);
		imgViewPing.setLayoutX(439);
		imgViewPing.setLayoutY(179);
		imgViewPing.setPickOnBounds(true);
		imgViewPing.setPreserveRatio(true);



		inicio_root.getChildren().addAll(b_play, l_t1, l_t2, imgViewPac,imgViewCoin1, imgViewCoin2, imgViewCoin3, imgViewFirew,imgViewPuz, imgViewPing);

		b_play.setOnAction(e->{
			window.setScene(sceneLog);
		});



		sceneInicio = new Scene(inicio_root, 600, 400);


		/////////////////////////// MENU ///////////////////////////////////////
		/////////////////////////////////////////////////////////////////////////
		////////////////////////////////////////////////////////////////////////

		//layout
		Pane menu_root = new Pane();

		//Controls
		Text t=new Text("Menú");
		t.setFont(new Font("Century Gothic",  50));
		t.setFill(Color.YELLOW);
		t.setLayoutX(200);
		t.setLayoutY(50);


		Text t3=new Text("Usuario");
		t3.setFont(Font.font("Consolas", FontWeight.BOLD, 30));
		t3.setFill(Color.CADETBLUE);
		t3.setLayoutX(240);
		t3.setLayoutY(110);

		Button b3=new Button("Perfil");
		b3.setStyle("-fx-padding: 8 15 15 15;"
				+ "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"
				+ "-fx-background-radius: 8;"
				+ "-fx-background-color:  linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); "
				+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); "
				+ "-fx-font-weight: bold;"
				+ "-fx-font-size: 1.5em;"
				+ "-fx-text-fill: white");
		b3.setLayoutX(260);
		b3.setLayoutY(120);


		InputStream inpPipe = clInicio.getResourceAsStream("space-invaders.png");
		Image imgPipe = new Image(inpPipe, 110, 110, false, false);
		ImageView imgViewPipe = new ImageView(imgPipe);
		imgViewPipe.setLayoutX(60);
		imgViewPipe.setLayoutY(25);
		imgViewPipe.setPickOnBounds(true);
		imgViewPipe.setPreserveRatio(true);



		FlowPane rootLeft=new FlowPane();
		Insets inset = new Insets(10);
		rootLeft.setPadding(inset);
		rootLeft.setOrientation(Orientation.VERTICAL);
		rootLeft.setVgap(10);

		Text t1=new Text("For Fun");
		t1.setFont(Font.font("Comic Sans MS", 30));
		t1.setFill(Color.FUCHSIA);

		Button b1=new Button("Pong");
		b1.setStyle("-fx-padding: 8 15 15 15;-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"
				+ "-fx-background-radius: 8;-fx-background-color:  linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); "
				+ "-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );"
				+ " -fx-font-weight: bold; "
				+ "-fx-font-size: 1.5em;"
				+ "-fx-text-fill: white");

		Button b2=new Button("Rompecabezas");
		b2.setStyle("-fx-padding: 8 15 15 15;"
				+ "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"
				+ "-fx-background-radius: 8;"
				+ "-fx-background-color:  linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); "
				+ "-fx-font-weight: bold;"
				+ "-fx-font-size: 1.5em;"
				+ "-fx-text-fill: white");

		rootLeft.setLayoutX(50);
		rootLeft.setLayoutY(200);
		rootLeft.getChildren().addAll(t1, b1, b2);



		FlowPane rootRight = new FlowPane();
		rootRight.setOrientation(Orientation.VERTICAL);
		rootRight.setVgap(10);

		rootRight.setPadding(inset);
		Text t2=new Text("For the Brain");
		t2.setFont(Font.font("Bahnschrift", 30));
		t2.setFill(Color.LIGHTSKYBLUE);

		Button b4=new Button("Mastermind");
		b4.setStyle("-fx-padding: 8 15 15 15;"
				+ "-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;"
				+ "-fx-background-radius: 8;"
				+ "-fx-background-color:  linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024, #d86e3a,radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c); -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 ); "
				+ "-fx-font-weight: bold;"
				+ "-fx-font-size: 1.5em;"
				+ "-fx-text-fill: white");

		rootRight.getChildren().addAll(t2, b4);
		rootRight.setLayoutX(320);
		rootRight.setLayoutY(200);


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
			Puzzle puz = new Puzzle();
			try {
				puz.start(new Stage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}


		});
		b3.setOnAction(e_-> window.setScene(sceneProfile));

		b4.setOnAction(e-> {
			Mastermind mast = new Mastermind();
			try {
				mast.start(new Stage());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		//Add to layout
		//		rootLeft.getChildren().addAll(t,t1,b1,b2, b4,t2,b3, t3);
		menu_root.setBackground(new Background(new BackgroundFill(Color.rgb(168, 175, 161, 0.93), CornerRadii.EMPTY, Insets.EMPTY)));
		menu_root.getChildren().addAll(t, rootLeft, rootRight, t3, b3,imgViewPipe);

		//scene
		sceneMenu = new Scene(menu_root,600,400);


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
		b_entrar.setOnAction(e_->{
			//checar si usuario y contrasenia

			User userTest = new User(tf_user.getText(), tf_psw.getText() );
			System.out.println(userTest.getNickname() + " " + userTest.getPass());
			if(userTest.login()) {
				window.setScene(sceneMenu);

				Preferences userPreferences = Preferences.userRoot();
				userPreferences.put("id", Integer.toString((userTest.getId())));
			}

			else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Usuario o contraseña incorrecto(s)", ButtonType.OK);
				alert.show();

				if (alert.getResult() == ButtonType.OK) {
					//do stuff
				}
			}
		});


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
		b_regist.setOnAction(e ->{

			window.setScene(sceneRegister);


		});

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

		//Registro done
		Label l_regDone = new Label("");



		b_reg.setOnAction(e->{
			User u = new User(tf_name.getText(), tf_password.getText(), dp_birthDate.getAccessibleText(), tf_usuario.getText(), cb_sex.getValue().equals("Hombre")? Genre.MALE : Genre.FEMALE);
			if(u.register())
				l_regDone.setText( "Registrado. ¿Jugamos?");
			else {
				Alert alert = new Alert(Alert.AlertType.ERROR, "Usuario o contraseña incorrecto(s)", ButtonType.OK);
				alert.show();

				if (alert.getResult() == ButtonType.OK) {
					//do stuff
				}
			}




		});

		dataLayout.getChildren().addAll(l_name, tf_name, l_birthDate, dp_birthDate, l_sex, cb_sex,
				l_usuario, tf_usuario, l_password, tf_password, b_reg, l_regDone);


		AnchorPane buttLayout = new AnchorPane();	

		Button b_return = new Button("Inicio");
		String st_ret = new String("-fx-background-color: \r\n" + 
				"        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\r\n" + 
				"        #9d4024,\r\n" + 
				"        #d86e3a,\r\n" + 
				"        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\r\n" + 
				"    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\r\n" + 
				"    -fx-font-weight: bold;\r\n" + 
				"    -fx-font-size: 1.5em;");
		b_return.setStyle(st_ret);
		b_return.setOnAction(e-> {
			window.setScene(sceneLog);

			tf_usuario.setText("");
			tf_name.setText("");
			tf_password.setText("");
			cb_sex.setValue("");
			l_regDone.setVisible(false);
		});

		Label l_titRegistro = new Label("Regístrate");
		l_titRegistro.setFont(new Font("Forte", 50));
		l_titRegistro.setTextFill(Color.rgb(230, 204, 58, 0.91));

		Label l_gameOn = new Label("Y diviértete");
		l_gameOn.setFont(new Font("Berlin Sans FB", 25));


		AnchorPane.setTopAnchor(l_titRegistro, 80.0);
		AnchorPane.setLeftAnchor(l_titRegistro,20.0);

		AnchorPane.setTopAnchor(l_gameOn, 130.0);
		AnchorPane.setLeftAnchor(l_gameOn, 100.0);

		AnchorPane.setTopAnchor(b_return, 350.0);
		AnchorPane.setLeftAnchor(b_return, 200.0);

		buttLayout.getChildren().addAll(l_titRegistro,l_gameOn, b_return);

		reg_root.getChildren().addAll(iconLayout, dataLayout, buttLayout);




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

		Label l_profDataEdad = new Label();


		//Fecha nacim
		Label l_profNacimiento = new Label("Fecha Nacimiento");
		l_profNacimiento.setFont(new Font("Gill Sans MT", 20));

		Label l_profDataFecha = new Label("insert birthdate of user from database");

		//back arrow Icon
		InputStream intpArrow = c.getResourceAsStream("arrow.png");
		Image arrow = new Image(intpArrow, 50, 50, false, false);
		ImageView backArrow = new ImageView(arrow); 
		backArrow.setRotate(180.0);

		backArrow.setOnMouseClicked(e -> window.setScene(sceneMenu));



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

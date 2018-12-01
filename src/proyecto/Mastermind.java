




import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

import javafx.stage.Stage;

public class Mastermind extends Application {

	public static double score = 1000;
	public static Circulito source;
	public static Circulito[] secretCode;
	public static int roundCount;
	public static ArrayList<Circulito> encryptedCode;
	public static boolean win = false;
	public static Game game;

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




	public static void main(String[] args)  {
		launch(args);
	}

	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {
		Preferences userPref = Preferences.userRoot();
		//session of specific user id
		game = new Game(Integer.parseInt(userPref.get("id", "")));
		
		
		
		

		Text title = new Text(370, 80, "Mastermind");
		title.setScaleX(2.5);
		title.setScaleY(2.5);
		title.setFont(Font.font("Consolas", FontWeight.BOLD, 15));
		title.setFill(Color.STEELBLUE);
		title.setX(350);
		title.setY(40);


		Class<?> c = this.getClass();
		InputStream input = c.getResourceAsStream("brain.png");
		Image image = new Image(input, 80,80,true, false);
		ImageView brain = new ImageView(image);

		brain.setX(210);
		brain.setY(5);

		//0
		FlowPane fp0 = new FlowPane();
		fp0.setOrientation(Orientation.HORIZONTAL);
		fp0.setPadding(new Insets(2));
		fp0.setHgap(10);
		Circulito c01 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c02 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c03 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c04 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);

		ArrayList<Circulito> round0 = new ArrayList<Circulito>();

		round0.add(c01);
		round0.add(c02);
		round0.add(c03);
		round0.add(c04);


		//Clue to game
		GridPane clue0 = new GridPane();
		clue0.setVgap(2);
		clue0.setHgap(2);
		clue0.setAlignment(Pos.CENTER);

		Circle answ01 = new Circle(0,0,5,Color.WHITE);
		answ01.setVisible(false);

		Circle answ02 = new Circle(0,0,5,Color.WHITE);
		answ02.setVisible(false);

		Circle answ03 = new Circle(0,0,5,Color.WHITE);
		answ03.setVisible(false);

		Circle answ04 = new Circle(0,0,5,Color.WHITE);
		answ04.setVisible(false);

		ArrayList<Circle> answers0 = new ArrayList<Circle>();
		answers0.add(answ01);
		answers0.add(answ02);
		answers0.add(answ03);
		answers0.add(answ04);



		clue0.add(answ01, 0, 0);
		clue0.add(answ02, 0, 1);
		clue0.add(answ03, 1, 0);
		clue0.add(answ04, 1, 1);
		clue0.setVisible(true);


		Label l10 = new Label("1. ");
		l10.setStyle("-fx-font:20 Consolas");

		fp0.getChildren().addAll(l10, c01, c02, c03, c04, clue0);
		fp0.setAlignment(Pos.CENTER);





		//1
		FlowPane fp1 = new FlowPane();
		fp1.setOrientation(Orientation.HORIZONTAL);
		fp1.setPadding(new Insets(2));
		fp1.setHgap(10);

		Circulito c11 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c12 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c13 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c14 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);

		ArrayList<Circulito> round1 = new ArrayList<Circulito>();

		round1.add(c11);
		round1.add(c12);
		round1.add(c13);
		round1.add(c14);

		Label l9 = new Label("2. ");
		l9.setStyle("-fx-font:20 Consolas");


		//Clue to game
		GridPane clue1 = new GridPane();
		clue1.setVgap(2);
		clue1.setHgap(2);
		clue1.setAlignment(Pos.CENTER);

		Circle answ11 = new Circle(0,0,5,Color.WHITE);
		answ11.setVisible(false);

		Circle answ12 = new Circle(0,0,5,Color.WHITE);
		answ12.setVisible(false);

		Circle answ13 = new Circle(0,0,5,Color.WHITE);
		answ13.setVisible(false);

		Circle answ14 = new Circle(0,0,5,Color.WHITE);
		answ14.setVisible(false);

		clue1.add(answ11, 0, 0);
		clue1.add(answ12, 0, 1);
		clue1.add(answ13, 1, 0);
		clue1.add(answ14, 1, 1);
		clue1.setVisible(true);

		ArrayList<Circle> answer1 = new ArrayList<Circle>();
		answer1.add(answ11);
		answer1.add(answ12);
		answer1.add(answ13);
		answer1.add(answ14);


		fp1.getChildren().addAll(l9, c11, c12, c13, c14, clue1);
		fp1.setAlignment(Pos.CENTER);





		//2
		FlowPane fp2 = new FlowPane();
		fp2.setOrientation(Orientation.HORIZONTAL);
		fp2.setPadding(new Insets(2));
		fp2.setHgap(10);

		Circulito c21 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c22 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c23 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c24 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);

		ArrayList<Circulito> round2 = new ArrayList<Circulito>();

		round2.add(c21);
		round2.add(c22);
		round2.add(c23);
		round2.add(c24);


		Label l8 = new Label("3. ");
		l8.setStyle("-fx-font:20 Consolas");


		//Clue to game
		GridPane clue2 = new GridPane();
		clue2.setVgap(2);
		clue2.setHgap(2);
		clue2.setAlignment(Pos.CENTER);

		Circle answ21 = new Circle(0,0,5,Color.WHITE);	
		answ21.setVisible(false);

		Circle answ22 = new Circle(0,0,5,Color.WHITE);
		answ22.setVisible(false);

		Circle answ23 = new Circle(0,0,5,Color.WHITE);
		answ23.setVisible(false);

		Circle answ24 = new Circle(0,0,5,Color.WHITE);
		answ24.setVisible(false);

		ArrayList<Circle> answer2 = new ArrayList<Circle>();
		answer2.add(answ21);
		answer2.add(answ22);
		answer2.add(answ23);
		answer2.add(answ24);

		clue2.add(answ21, 0, 0);		
		clue2.add(answ22, 0, 1);
		clue2.add(answ23, 1, 0);
		clue2.add(answ24, 1, 1);
		clue2.setVisible(true);


		fp2.getChildren().addAll(l8, c21, c22, c23, c24, clue2);
		fp2.setAlignment(Pos.CENTER);



		//3
		FlowPane fp3 = new FlowPane();
		fp3.setOrientation(Orientation.HORIZONTAL);
		fp3.setPadding(new Insets(2));
		fp3.setHgap(10);

		Circulito c31 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c32 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c33 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c34 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);

		ArrayList<Circulito> round3 = new ArrayList<Circulito>();

		round3.add(c31);
		round3.add(c32);
		round3.add(c33);
		round3.add(c34);

		Label l7 = new Label("4. ");
		l7.setStyle("-fx-font:20 Consolas");

		//Clue to game
		GridPane clue3 = new GridPane();
		clue3.setVgap(2);
		clue3.setHgap(2);
		clue3.setAlignment(Pos.CENTER);

		Circle answ31 = new Circle(0,0,5,Color.WHITE);	
		answ31.setVisible(false);

		Circle answ32 = new Circle(0,0,5,Color.WHITE);
		answ32.setVisible(false);

		Circle answ33 = new Circle(0,0,5,Color.WHITE);
		answ33.setVisible(false);

		Circle answ34 = new Circle(0,0,5,Color.WHITE);
		answ34.setVisible(false);

		ArrayList<Circle> answer3 = new ArrayList<Circle>();
		answer3.add(answ31);
		answer3.add(answ32);
		answer3.add(answ33);
		answer3.add(answ34);

		clue3.add(answ31, 0, 0);
		clue3.add(answ32, 0, 1);
		clue3.add(answ33, 1, 0);
		clue3.add(answ34, 1, 1);
		clue3.setVisible(true);


		fp3.getChildren().addAll(l7, c31, c32, c33, c34, clue3);
		fp3.setAlignment(Pos.CENTER);



		//4
		FlowPane fp4 = new FlowPane();
		fp4.setOrientation(Orientation.HORIZONTAL);
		fp4.setPadding(new Insets(2));
		fp4.setHgap(10);

		Circulito c41 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c42 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c43 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c44 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);

		ArrayList<Circulito> round4 = new ArrayList<Circulito>();

		round4.add(c41);
		round4.add(c42);
		round4.add(c43);
		round4.add(c44);

		Label l6 = new Label("5. ");
		l6.setStyle("-fx-font:20 Consolas");

		//Clue to game
		GridPane clue4 = new GridPane();
		clue4.setVgap(2);
		clue4.setHgap(2);
		clue4.setAlignment(Pos.CENTER);


		Circle answ41 = new Circle(0,0,5,Color.WHITE);	
		answ41.setVisible(false);

		Circle answ42 = new Circle(0,0,5,Color.WHITE);
		answ42.setVisible(false);

		Circle answ43 = new Circle(0,0,5,Color.WHITE);
		answ43.setVisible(false);

		Circle answ44 = new Circle(0,0,5,Color.WHITE);
		answ44.setVisible(false);


		ArrayList<Circle> answer4 = new ArrayList<Circle>();
		answer4.add(answ41);
		answer4.add(answ42);
		answer4.add(answ43);
		answer4.add(answ44);

		clue4.add(answ41, 0, 0);
		clue4.add(answ42, 0, 1);
		clue4.add(answ43, 1, 0);
		clue4.add(answ44, 1, 1);
		clue4.setVisible(true);

		fp4.getChildren().addAll(l6, c41, c42, c43, c44, clue4);
		fp4.setAlignment(Pos.CENTER);



		//5
		FlowPane fp5 = new FlowPane();
		fp5.setOrientation(Orientation.HORIZONTAL);
		fp5.setPadding(new Insets(2));
		fp5.setHgap(10);

		Circulito c51 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c52 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c53 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c54 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);

		ArrayList<Circulito> round5 = new ArrayList<Circulito>();

		round5.add(c51);
		round5.add(c52);
		round5.add(c53);
		round5.add(c54);

		Label l5 = new Label("6. ");
		l5.setStyle("-fx-font:20 Consolas");

		//Clue to game
		GridPane clue5 = new GridPane();
		clue5.setVgap(2);
		clue5.setHgap(2);
		clue5.setAlignment(Pos.CENTER);
		clue5.setVisible(true);


		Circle answ51 = new Circle(0,0,5,Color.WHITE);		
		answ51.setVisible(false);

		Circle answ52 = new Circle(0,0,5,Color.WHITE);
		answ52.setVisible(false);

		Circle answ53 = new Circle(0,0,5,Color.WHITE);
		answ53.setVisible(false);

		Circle answ54 = new Circle(0,0,5,Color.WHITE);
		answ54.setVisible(false);

		ArrayList<Circle> answer5 = new ArrayList<Circle>();
		answer5.add(answ51);
		answer5.add(answ52);
		answer5.add(answ53);
		answer5.add(answ54);


		clue5.add(answ51, 0, 0);
		clue5.add(answ52, 0, 1);
		clue5.add(answ53, 1, 0);
		clue5.add(answ54, 1, 1);

		fp5.getChildren().addAll(l5, c51, c52, c53, c54, clue5);
		fp5.setAlignment(Pos.CENTER);



		//6
		FlowPane fp6 = new FlowPane();
		fp6.setOrientation(Orientation.HORIZONTAL);
		fp6.setPadding(new Insets(2));
		fp6.setHgap(10);

		Circulito c61 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c62 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c63 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c64 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);


		ArrayList<Circulito> round6 = new ArrayList<Circulito>();

		round6.add(c61);
		round6.add(c62);
		round6.add(c63);
		round6.add(c64);


		Label l4 = new Label("7. ");
		l4.setStyle("-fx-font:20 Consolas");

		//Clue to game
		GridPane clue6 = new GridPane();
		clue6.setVgap(2);
		clue6.setHgap(2);
		clue6.setAlignment(Pos.CENTER);


		Circle answ61 = new Circle(0,0,5,Color.WHITE);	
		answ61.setVisible(false);

		Circle answ62 = new Circle(0,0,5,Color.WHITE);
		answ62.setVisible(false);

		Circle answ63 = new Circle(0,0,5,Color.WHITE);
		answ63.setVisible(false);

		Circle answ64 = new Circle(0,0,5,Color.WHITE);
		answ64.setVisible(false);

		ArrayList<Circle> answer6 = new ArrayList<Circle>();
		answer6.add(answ61);
		answer6.add(answ62);
		answer6.add(answ63);
		answer6.add(answ64);


		clue6.add(answ61, 0, 0);
		clue6.add(answ62, 0, 1);
		clue6.add(answ63, 1, 0);
		clue6.add(answ64, 1, 1);
		clue6.setVisible(true);


		fp6.getChildren().addAll(l4, c61, c62, c63, c64, clue6);
		fp6.setAlignment(Pos.CENTER);



		//7
		FlowPane fp7 = new FlowPane();
		fp7.setOrientation(Orientation.HORIZONTAL);
		fp7.setPadding(new Insets(2));
		fp7.setHgap(10);

		Circulito c71 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c72 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c73 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c74 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);

		ArrayList<Circulito> round7 = new ArrayList<Circulito>();

		round7.add(c71);
		round7.add(c72);
		round7.add(c73);
		round7.add(c74);

		Label l3 = new Label("8. ");
		l3.setStyle("-fx-font:20 Consolas");


		//Clue to game
		GridPane clue7 = new GridPane();
		clue7.setVgap(2);
		clue7.setHgap(2);
		clue7.setAlignment(Pos.CENTER);

		Circle answ71 = new Circle(0,0,5,Color.WHITE);	
		answ71.setVisible(true);

		Circle answ72 = new Circle(0,0,5,Color.WHITE);
		answ72.setVisible(true);

		Circle answ73 = new Circle(0,0,5,Color.WHITE);
		answ73.setVisible(true);

		Circle answ74 = new Circle(0,0,5,Color.WHITE);
		answ74.setVisible(true);

		ArrayList<Circle> answer7 = new ArrayList<Circle>();
		answer7.add(answ71);
		answer7.add(answ72);
		answer7.add(answ73);
		answer7.add(answ74);


		clue7.add(answ71, 0, 0);
		clue7.add(answ72, 0, 1);
		clue7.add(answ73, 1, 0);
		clue7.add(answ74, 1, 1);
		clue7.setVisible(false);


		fp7.getChildren().addAll(l3, c71, c72, c73, c74, clue7);
		fp7.setAlignment(Pos.CENTER);



		//8
		FlowPane fp8 = new FlowPane();
		fp8.setOrientation(Orientation.HORIZONTAL);
		fp8.setPadding(new Insets(2));
		fp8.setHgap(10);

		Circulito c81 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c82 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c83 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c84 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);

		ArrayList<Circulito> round8 = new ArrayList<Circulito>();

		round8.add(c81);
		round8.add(c82);
		round8.add(c83);
		round8.add(c84);

		Label l2 = new Label("9. ");
		l2.setStyle("-fx-font:20 Consolas");

		//Clue to game
		GridPane clue8 = new GridPane();
		clue8.setVgap(2);
		clue8.setHgap(2);
		clue8.setAlignment(Pos.CENTER);

		Circle answ81 = new Circle(0,0,5,Color.WHITE);	
		answ81.setVisible(false);

		Circle answ82 = new Circle(0,0,5,Color.WHITE);
		answ82.setVisible(false);

		Circle answ83 = new Circle(0,0,5,Color.WHITE);
		answ83.setVisible(false);

		Circle answ84 = new Circle(0,0,5,Color.WHITE);
		answ84.setVisible(false);


		ArrayList<Circle> answer8 = new ArrayList<Circle>();
		answer8.add(answ81);
		answer8.add(answ82);
		answer8.add(answ83);
		answer8.add(answ84);


		clue8.add(answ81, 0, 0);
		clue8.add(answ82, 0, 1);
		clue8.add(answ83, 1, 0);
		clue8.add(answ84, 1, 1);
		clue8.setVisible(true);


		fp8.getChildren().addAll(l2, c81, c82, c83, c84 ,clue8);
		fp8.setAlignment(Pos.CENTER);



		//9
		FlowPane fp9 = new FlowPane();
		fp9.setOrientation(Orientation.HORIZONTAL);
		fp9.setPadding(new Insets(2));
		fp9.setHgap(10);

		Circulito c91 = new Circulito(0,0,22,Color.gray(.4), "gris", 1);
		Circulito c92 = new Circulito(0,0,22,Color.gray(.4), "gris", 2);
		Circulito c93 = new Circulito(0,0,22,Color.gray(.4), "gris", 3);
		Circulito c94 = new Circulito(0,0,22,Color.gray(.4), "gris", 4);

		ArrayList<Circulito> round9 = new ArrayList<Circulito>();

		round9.add(c91);
		round9.add(c92);
		round9.add(c93);
		round9.add(c94);

		Label l1 = new Label("10.");
		l1.setStyle("-fx-font:20 Consolas");

		//Clue to game
		GridPane clue9 = new GridPane();
		clue9.setVgap(2);
		clue9.setHgap(2);
		clue9.setAlignment(Pos.CENTER);

		Circle answ91 = new Circle(0,0,5,Color.WHITE);	
		answ91.setVisible(false);

		Circle answ92 = new Circle(0,0,5,Color.WHITE);
		answ92.setVisible(false);

		Circle answ93 = new Circle(0,0,5,Color.WHITE);
		answ93.setVisible(false);

		Circle answ94 = new Circle(0,0,5,Color.WHITE);
		answ94.setVisible(false);

		ArrayList<Circle> answer9 = new ArrayList<Circle>();
		answer9.add(answ91);
		answer9.add(answ92);
		answer9.add(answ93);
		answer9.add(answ94);


		clue9.add(answ91, 0, 0);
		clue9.add(answ92, 0, 1);
		clue9.add(answ93, 1, 0);
		clue9.add(answ94, 1, 1);
		clue9.setVisible(true);


		fp9.getChildren().addAll(l1, c91, c92, c93, c94, clue9); 
		fp9.setAlignment(Pos.CENTER);


		LinkedList<ArrayList<Circulito>> roundList = new LinkedList<ArrayList<Circulito>>();
		roundList.add(round0);
		roundList.add(round1);
		roundList.add(round2);
		roundList.add(round3);
		roundList.add(round4);
		roundList.add(round5);
		roundList.add(round6);
		roundList.add(round7);
		roundList.add(round8);
		roundList.add(round9);



		LinkedList<ArrayList<Circle>>answerSet = new LinkedList<>();
		answerSet.add(answers0);
		answerSet.add(answer1);
		answerSet.add(answer2);
		answerSet.add(answer3);
		answerSet.add(answer4);
		answerSet.add(answer5);
		answerSet.add(answer6);
		answerSet.add(answer7);
		answerSet.add(answer8);
		answerSet.add(answer9);






		//Gather horizontal panes on VBox
		VBox answers = new VBox();
		answers.getChildren().addAll(fp0, fp1, fp2, fp3, fp4, fp5, fp6, fp7, fp8, fp9);
		answers.setLayoutX(80);
		answers.setLayoutY(74);
		answers.setAlignment(Pos.CENTER);


		//Buttons

//		Button bAtras = new Button("Atrás");
//		String atrasStyle = new String("-fx-background-color: \r\n" + 
//				"        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\r\n" + 
//				"        #9d4024,\r\n" + 
//				"        #d86e3a,\r\n" + 
//				"        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\r\n" + 
//				"    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\r\n" + 
//				"    -fx-font-weight: bold;\r\n" + 
//				"    -fx-font-size: 1.7em;");
//		bAtras.setStyle(atrasStyle);
//		bAtras.setLayoutX(20);
//		bAtras.setLayoutY(80);



		Button bRewind = new Button("Nuevo");
		String rewindStyle = new String("-fx-background-color: \r\n" + 
				"        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\r\n" + 
				"        #9d4024,\r\n" + 
				"        #d86e3a,\r\n" + 
				"        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\r\n" + 
				"    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\r\n" + 
				"    -fx-font-weight: bold;\r\n" + 
				"    -fx-font-size: 1.7em;");
		bRewind.setStyle(rewindStyle);
		bRewind.setLayoutX(20);
		bRewind.setLayoutY(150);


		Button bSolve = new Button("Solución");
		String solveStyle = new String("-fx-background-color: \r\n" + 
				"        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\r\n" + 
				"        #9d4024,\r\n" + 
				"        #d86e3a,\r\n" + 
				"        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\r\n" + 
				"    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\r\n" + 
				"    -fx-font-weight: bold;\r\n" + 
				"    -fx-font-size: 1.7em;");
		bSolve.setStyle(solveStyle);
		bSolve.setLayoutX(20);
		bSolve.setLayoutY(220);

		Button test = new Button("test!");
		String testStyle  = new String(" -fx-background-color:\r\n" + 
				"        linear-gradient(#f0ff35, #a9ff00),\r\n" + 
				"        radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%);\r\n" + 
				"    -fx-background-radius: 6, 5;\r\n" + 
				"    -fx-background-insets: 0, 1;\r\n" + 
				"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.4) , 5, 0.0 , 0 , 1 );\r\n" + 
				"    -fx-text-fill: #395306;" + 
				"    -fx-font-size: 2em;");
		test.setStyle(testStyle);
		test.setFont(Font.font("Consolas", FontWeight.BOLD, 15));
		test.setLayoutX(570);
		test.setLayoutY(180);

		//Score
		Label l_txtScore = new Label("Score: " + Mastermind.score);
		l_txtScore.setStyle("-fx-font:30 Gadugi");
		l_txtScore.setTextFill(Color.YELLOW);
		l_txtScore.setLayoutX(630);
		l_txtScore.setLayoutY(50);



		//Balls
		FlowPane bolas = new FlowPane();
		bolas.setOrientation(Orientation.HORIZONTAL);
		Circulito color1 = new Circulito(0,0,22,Color.DARKTURQUOISE, "turquesa");
		Circulito color2 = new Circulito(0,0,22,Color.CORNFLOWERBLUE, "azul");
		Circulito color3 = new Circulito(0,0,22,Color.DEEPPINK, "rosa");
		Circulito color4 = new Circulito(0,0,22,Color.MAROON, "tinto");
		Circulito color5 = new Circulito(0,0,22,Color.ORANGE, "naranja");


		bolas.setHgap(5);
		bolas.setLayoutX(500);
		bolas.setLayoutY(122);
		bolas.setPadding(new Insets(5));
		bolas.getChildren().addAll(color1, color2, color3, color4, color5);


		//Answer
		Label answ = new Label("Secuencia correcta:");
		answ.setStyle("-fx-font:30 Gadugi");
		answ.setTextFill(Color.DIMGRAY);
		answ.setLayoutX(500);
		answ.setLayoutY(350);
		answ.setVisible(false);

		FlowPane encrypted = new FlowPane();
		encrypted.setOrientation(Orientation.HORIZONTAL);
		Circulito b1= new Circulito(0,0,22);
		Circulito b2= new Circulito(0,0,22);
		Circulito b3 = new Circulito(0,0,22);
		Circulito b4 = new Circulito(0,0,22);

		encryptedCode = new ArrayList<Circulito>();

		encryptedCode.add(b1);
		encryptedCode.add(b2);
		encryptedCode.add(b3);
		encryptedCode.add(b4);


		encryptedCode = makeItSecret(encryptedCode);


		encrypted.setHgap(5);
		encrypted.setLayoutX(500);
		encrypted.setLayoutY(400);
		encrypted.setPadding(new Insets(5));
		encrypted.getChildren().addAll(b1, b2, b3, b4);
		encrypted.setVisible(false);

		Label l_win = new Label("¡Ganaste, champ!");
		l_win.setStyle("-fx-font:30 Consolas");
		l_win.setTextFill(Color.DARKGOLDENROD);
		l_win.setLayoutX(500);
		l_win.setLayoutY(300);
		l_win.setVisible(false);
		


		Pane pane = new Pane();

		pane.getChildren().addAll(brain, title, answers, bRewind, bSolve,l_txtScore, bolas, test, answ, encrypted, l_win);
		pane.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
		Scene scene = new Scene(pane, 800, 600);
		stage.setTitle("Mastermind");
		stage.setScene(scene);
		stage.show();


		///////////////////////////////////////////////////////////
		// EVENTOS PARA DRAG & DROP////////////////////////////////
		/////////////////////////////////////////////////////////



		////Drag events for each color/////
		///////////////////////////////////

		// Detecta el drag en el EventTarget color1
		color1.setOnDragDetected(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				//actualizamos src
				source = new Circulito(color1.getCenterX(), color1.getCenterY(), color1.getRadius(), color1.getFill(), color1.getCode(), color1.getPosition());

				// Clipboard para DragEvent
				Dragboard db = color1.startDragAndDrop(TransferMode.COPY_OR_MOVE);
				// Contenido del clipboard. Es el contenido que se va copiar o mover de un source a un target
				ClipboardContent content = new ClipboardContent();
				//					content.putString(color1.getCode());
				content.putString(color1.getCode());
				// Agregamos el contenido al Dragboard
				db.setContent(content);
				// Consumimos el evento.
				event.consume();
			}
		});

		// Detecta el drag en el EventTarget color2
		color2.setOnDragDetected(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				//actualizamos src
				source = new Circulito(color2.getCenterX(), color2.getCenterY(), color2.getRadius(), color2.getFill(), color2.getCode(), color1.getPosition());

				// Clipboard para DragEvent
				Dragboard db = color2.startDragAndDrop(TransferMode.COPY_OR_MOVE);
				// Contenido del clipboard. Es el contenido que se va copiar o mover de un source a un target
				ClipboardContent content = new ClipboardContent();
				content.putString(color2.getCode());
				// Agregamos el contenido al Dragboard
				db.setContent(content);
				// Consumimos el evento.
				event.consume();
			}
		});

		// Detecta el drag en el EventTarget color3
		color3.setOnDragDetected(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				//actualizamos src
				source = new Circulito(color3.getCenterX(), color3.getCenterY(), color3.getRadius(), color3.getFill(), color3.getCode(), color3.getPosition());


				// Clipboard para DragEvent
				Dragboard db = color3.startDragAndDrop(TransferMode.COPY_OR_MOVE);
				// Contenido del clipboard. Es el contenido que se va copiar o mover de un source a un target
				ClipboardContent content = new ClipboardContent();
				content.putString(color3.getCode());
				// Agregamos el contenido al Dragboard
				db.setContent(content);
				// Consumimos el evento.
				event.consume();
			}
		});

		// Detecta el drag en el EventTarget color4
		color4.setOnDragDetected(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				//actualizamos src
				source = new Circulito(color4.getCenterX(), color4.getCenterY(), color4.getRadius(), color4.getFill(), color4.getCode(), color4.getPosition());


				// Clipboard para DragEvent
				Dragboard db = color4.startDragAndDrop(TransferMode.COPY_OR_MOVE);
				// Contenido del clipboard. Es el contenido que se va copiar o mover de un source a un target
				ClipboardContent content = new ClipboardContent();
				content.putString(color4.getCode());
				// Agregamos el contenido al Dragboard
				db.setContent(content);
				// Consumimos el evento.
				event.consume();
			}
		});


		// Detecta el drag en el EventTarget color5
		color5.setOnDragDetected(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				//actualizamos src
				source = new Circulito(color5.getCenterX(), color5.getCenterY(), color5.getRadius(), color5.getFill(), color5.getCode(), color5.getPosition());


				// Clipboard para DragEvent
				Dragboard db = color5.startDragAndDrop(TransferMode.COPY_OR_MOVE);
				// Contenido del clipboard. Es el contenido que se va copiar o mover de un source a un target
				ClipboardContent content = new ClipboardContent();
				content.putString(color5.getCode());

				// Agregamos el contenido al Dragboard
				db.setContent(content);
				// Consumimos el evento.
				event.consume();
			}
		});






		//DROP EVENTS ON TAGETS//
		//////////////////////////////////

		//		int i = 0;
		for(ArrayList<Circulito> listCircle : roundList) {

			for(Circulito testCircle : listCircle) {

				testCircle.setOnDragOver(event -> {

					if(event.getGestureSource() != testCircle &&
							event.getDragboard().hasString())
						event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
					event.consume();
				});

				testCircle.setOnDragDropped(event ->{

					Dragboard db = event.getDragboard();
					if(db.hasString()){
						testCircle.setCode(db.getString());
						event.setDropCompleted(true);
						testCircle.setFill(source.getFill());


					}
					event.consume();
				});


			}
		}





		////////////////////////////////////
		//Eventos Botones//
		///////////////////////////////////



//		bAtras.setOnAction(event->{
//			//load new pane
//			score +=100;
//			l_txtScore.setText("Score: " + Mastermind.score);
//		});
//




		bSolve.setOnAction(event->{
			score = 0;
			l_txtScore.setText("Score: " + Mastermind.score);

			answ.setVisible(true);
			encrypted.setVisible(true);
		});

		test.setOnAction(event->{

			boolean[] rightColors = new boolean[4];
			boolean[] rightCombo = new boolean[4];

			int k = 0;
			int countTrueColor = 0;
			//gets answer circle

			ArrayList<Circulito> colorTest = roundList.get(roundCount);

			for(Circulito testColorCirc : colorTest) { 
				for(int f = 0; f < encryptedCode.size(); f++) {
					if(testColorCirc.getFill() == encryptedCode.get(f).getFill()) {
						rightColors[k] = true;
						k++;
						countTrueColor++;
						testColorCirc.setRightColor(true);;
					}
				}

			}
			
			l_txtScore.setText("Score: " + Mastermind.score);

			int countTrueCombo = 0;
			k=0;//clean k counter


			ArrayList<Circulito> posTest = roundList.get(roundCount);

			for(Circulito testPosCirc : posTest) {
				for(int f = 0; f < encryptedCode.size(); f++) {

					if(testPosCirc.getFill() == encryptedCode.get(f).getFill() 
							&& testPosCirc.getPosition() == encryptedCode.get(f).getPosition()) {
						countTrueCombo++;
						countTrueColor--;
						rightCombo[k] = true;
					}
				}
				if(countTrueCombo == 4) {
					win = true;
					score+=100;
					break;
				}
				
			}
			score -=100;
			l_txtScore.setText("Score: " + Mastermind.score);



			ArrayList<Circle> setRespuesta = answerSet.get(roundCount);
			for(int s = 0; s < 4; s++) {
				if(countTrueColor > 0) {
					setRespuesta.get(s).setFill(Color.WHITE);
					setRespuesta.get(s).setVisible(true);
					countTrueColor--;
				}
				else if(countTrueCombo > 0) {
					setRespuesta.get(s).setFill(Color.BLACK);
					setRespuesta.get(s).setVisible(true);
					countTrueCombo --;

				}

			}

			roundCount++;
			
			
			if(roundCount == 10 && win == false) {
				l_win.setText("Desolé. Looser.");
				l_win.setVisible(true);
				game.setScore(score);
				game.save();
				System.out.println(game.getScore());
			}
			else if(win == true) {
				l_win.setText("¡Ganaste, champ!");
				l_win.setVisible(true);
				game.setScore(score);
				System.out.println(game.getScore());
				game.save();

			}


		});


		bRewind.setOnAction(event ->{
			encryptedCode = makeItSecret(encryptedCode);

			for(ArrayList<Circulito> answList : roundList){

				for(Circulito circ : answList) {
					circ.setCode ("gris");
					circ.setFill(Color.gray(.4));

				}
				
			}
			for(ArrayList<Circle> clueSet : answerSet) {
				for(Circle cClue : clueSet) {
					cClue.setFill(Color.WHITE);
					cClue.setVisible(false);
				}
					
			}
			l_win.setVisible(false);
			answ.setVisible(false);
			encrypted.setVisible(false);
			
			score = 1000;
			l_txtScore.setText("Score: " + Mastermind.score);
			roundCount = 0;
		});

	
	}

	public class Circulito extends Circle{

		private String code;
		private int position;
		private boolean rightColor;
		private boolean rightPos;

		public Circulito(double centerX, double centerY, double radius, Paint fill,  String code, int pos){
			super(centerX, centerY, radius, fill);
			this.code = code;
			this.position = pos;
			this.rightColor = false;
			this.rightPos = false;
		}

		//for answer balls
		public Circulito(double centerX, double centerY, double radius, Paint fill,  String code){
			super(centerX, centerY, radius, fill);
			this.code = code;
			this.rightColor = false;
			this.rightPos = false;

		}

		//for random selection of colors
		public Circulito(double centerX, double centerY, double radius ){
			super(centerX, centerY, radius);
			this.code= "";
			this.rightColor = false;
			this.rightPos = false;

		}


		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public int getPosition() {
			return this.position;
		}

		public void setPosition(int i) {
			this.position = i;
		}

		public boolean isRightColor() {
			return rightColor;
		}

		public void setRightColor(boolean rightColor) {
			this.rightColor = rightColor;
		}

		public boolean isRightPos() {
			return rightPos;
		}

		public void setRightPos(boolean rightPos) {
			this.rightPos = rightPos;
		}




	}



}






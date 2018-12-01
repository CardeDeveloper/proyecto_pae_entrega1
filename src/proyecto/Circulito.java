import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

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


import java.sql.*;

/**
 *
 * @author carde
 */
public class Jdbc_modelos_proyecto_pae {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
       //User usuario = new User("cardenas", "123456");
      /* User usuario = new User("oscar", "123456", "2010-09-08", "bruno", Genre.MALE);
        System.out.println(usuario.register());*/
      
      Game juego = new Game(1);
      juego.setEndTime();
      juego.setScore(1.0);
      System.out.println(juego.save());
       
       
    }
    
}

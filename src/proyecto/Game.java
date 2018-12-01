
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;


/**
 *
 * @author carde
 */
public class Game {
    long startTime;
    long endTime;
    long time;
    Double score;
    Date date;
    int user;
    private Connection connection ;

    public Game(int userId) {
        this.startTime = System.nanoTime();
        this.date = Date.valueOf(LocalDate.now());
        this.connection= ConnectionFactory.getConnection();
        this.user = userId;
    }
    
    public boolean save(){
        try {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO game VALUES (NULL, ?, ?, ?, ?,?,?)");
        
        ps.setString(1, Long.toString(this.startTime));
        ps.setString(2, Long.toString(this.endTime));
        ps.setString(3, Long.toString(this.time));
        ps.setDouble(4, this.score);
        ps.setDate(5, this.date);
        ps.setInt(6, this.user);
        int i = ps.executeUpdate();
      if(i == 1) {
        return true;
      }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return false;
    }
    public double getTimeInSeconds(){
        return this.time /1_000_000_000.0;
    }
    
    @Override
    public String toString(){
        return Long.toString(time);
    }
    
    public String calculateTime(){
        this.time = endTime - startTime;
        return Long.toString(time);
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }
    
    
    public void setStartTime() {
        this.startTime = System.nanoTime();
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
        if(this.startTime!=0)
            this.calculateTime();
    }
    public void setEndTime() {
        this.endTime = System.nanoTime();
        if(this.startTime!=0)
                this.calculateTime();
    }

    

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
    
    
}

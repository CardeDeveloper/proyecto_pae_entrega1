
import java.sql.*;

/**
 *
 * @author carde
 */
public class User {

    
    private int id;
    private String name;
    private String pass;
    private String birthday;
    private String nickname;
    private Genre genre;
    private boolean isAuthenticated;
    private Connection connection ;

    public User() {
        this.isAuthenticated=false;
        this.connection= ConnectionFactory.getConnection();
    }

    public User(String nickname, String pass ) {
        this.pass = pass;
        this.nickname = nickname;
        this.isAuthenticated = false;
        this.connection= ConnectionFactory.getConnection();
    }

    public User(String name, String pass, String birthday, String nickname, Genre genre) {
        this.name = name;
        this.pass = pass;
        this.birthday = birthday;
        this.nickname = nickname;
        this.genre = genre;
        this.isAuthenticated = false;
        this.connection= ConnectionFactory.getConnection();
    }
    
    public boolean login(){
        
        try {
            Statement stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE nickname = ? AND password= ?" );
            ps.setString(1, this.nickname);
            ps.setString(2, this.pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                this.isAuthenticated = true;
                this.id = rs.getInt("id");
                this.name = rs.getString("name");
                this.genre = rs.getString("genre").equals("MALE")? Genre.MALE : Genre.FEMALE;
                   
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return this.isAuthenticated;
    }
    
    public boolean register(){
        try {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO user VALUES (NULL, ?, ?, ?, ?,?)");
        
        ps.setString(1, this.name);
        ps.setString(2, this.pass);
        ps.setString(3, this.birthday);
        ps.setString(4, this.nickname);
        ps.setString(5, this.genre.toString());
        int i = ps.executeUpdate();
      if(i == 1) {
        return true;
      }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setIsAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
    
    
    
}

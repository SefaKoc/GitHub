import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlReader {
	
	private Connection c = null;
	public  Datenformat readDatabase(int tankenr) throws SQLException, 
	ClassNotFoundException{
				//SqlReader.connect();
				connect();
		 		Statement s = c.createStatement();
		 		ArrayList<Datenformat> data = new ArrayList<Datenformat>();
		 		int a=0;
		        ResultSet rs = s.executeQuery("select * from sprit_data WHERE tankenr=" + tankenr + ";");
		        while (rs.next()) {
		        	a++;
		        	Datenformat e = new Datenformat(rs.getInt("datum"),
		        	rs.getInt("tankenr")
		        	rs.getDouble("value"));
		            data.add(e);
		        }
		        rs.close();   
		        return data;
	}
	
	private void connect() throws SQLException, ClassNotFoundException{
		
		if(!c){
			Class.forName("org.sqlite.JDBC");
			 c = DriverManager.getConnection("jdbc:sqlite:sprit.db");
			 System.out.println("Connection successfully");
			 c = true;
		} else{
			System.out.println("Connection false");
		}
	       
	}
	public  ArrayList<Integer> holeTankstellen() throws SQLException, ClassNotFoundException{
		connect();
 		Statement stat = c.createStatement();
 		ArrayList<Integer> s = new ArrayList<Integer>();
        ResultSet rs = stat.executeQuery("select tankenr from sprit_data;");
        while (rs.next()) {	
        	if(!s.contains(rs.getInt("tankenr"))){
        	s.add(rs.getInt("tankenr"));
        	}
        }
        rs.close();
        System.out.println(s);      
        return s;
	}
}

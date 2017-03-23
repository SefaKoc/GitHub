import java.sql.*;
import java.util.ArrayList;

public class DBReader1
{
	public DBReader1() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<SpritData> getData(String tabelle )
	  {
	    Connection c = null;
	    Statement stmt = null;
	 
	    ArrayList<SpritData> data = new ArrayList<SpritData>();

	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:sprit.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM "+tabelle+";" );
	      SpritData sd = new SpritData();
	      while ( rs.next() ) {
	    	 sd.setDatum(rs.getInt("datum"));
	         sd.setTankennr(rs.getInt("tankennr"));
	         sd.setValue(rs.getDouble("value"));
	         System.out.println( "Datum = " + sd.getDatum() );
	         System.out.println( "Verbrauch = " + sd.getValue());
	         System.out.println( "Tankennr = " + sd.getTankennr());	   
	         System.out.println();
	      }
	      data.add(sd);
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	
	    return data;
	    
	  }

}
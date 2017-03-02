import java.sql.*;

public class DBReader
{
	public DBReader() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static  Datenformat getData(String tabelle )
	  {
	    Connection c = null;
	    Statement stmt = null;
	    Datenformat data;
	    double[] y;
	    double[] x ;
	    if(tabelle=="wasser_verbrauch"){
	    	  y = new double[47];
	 	      x = new double[47];
	    }else{
	    	  y = new double[60];
	 	      x = new double[60];
	    }
	    int counter =0;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:verbrauch.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
	      ResultSet rs = stmt.executeQuery( "SELECT * FROM "+tabelle+";" );
	      while ( rs.next() ) {
	         int datum = rs.getInt("datum");
	         double value= rs.getDouble("value");
	         x[counter]=datum;
	         y[counter]=value;
	         System.out.println( "Datum = " + datum );
	         System.out.println( "Verbrauch = " + value );
	         System.out.println();
	         counter++;
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	    data = new Datenformat(x,y);
	    return data;
	    
	  }

}
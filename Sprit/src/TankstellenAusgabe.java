import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class TankstellenAusgabe {
	static SqlReader dbr = new SqlReader();

	public static void main(String[] args) throws ClassNotFoundException,
	SQLException, IOException {
		dbr.getTankstellen();
		AbstractXYTest f = new AbstractXYTest();
		ArrayList<Datenformat> dtd = new ArrayList<Datenformat>();
		for(int z=1;z<dtd.size();z++){
			dtd.add(dbr.readDatabase(z));
		}
		f.showChart(dtd);
	
		dtd = new ArrayList<ArrayList<Datenformat>>();
		for(int z=1;z<dtd.size();z++){
			dtd.add(stdLosung(dbr.readDatabase(z),convertToDate(1485957044)));
		}
		f.showChartHour(dtd);
		System.out.println(convertToDate(1485957044));
		dayFormat(convertToDate(1485957044));
		System.out.println(zuTimestampKonvertieren(dayFormat(convertToDate(1485957044))));	
	}
	
	public static ArrayList<Datenformat> stdLosung(ArrayList<Datenformat> data, java.util.Date d){
		
		ArrayList<Datenformat> std = new ArrayList<Datenformat>(); 
		   double aktDatum = zuTimestampKonvertieren(dayFormat(d))-(365*86400);
		   for(int i=0; i<365;i++){  
			   std.add(new Datenformat(aktDatum,data.get(0).getTankenr(),aktDatum));
			   System.out.println(convertToDate((int)aktDatum)+"\n  "+data.get(0).getTankenr()+" | "+aktDatum);
			   aktDatum +=86400;
		   }
		   return std;
	}
	public static ArrayList<Datenformat> yearloesung(ArrayList<Datenformat> data, java.util.Date d){
		
		ArrayList<Datenformat> iData = new ArrayList<Datenformat>();
		
		   double aktDatum = zuTimestampKonvertieren(dayFormat(d))-86400/2-86400/24;
		   
		   for(int i=0; i<24;i++){
			   
			   iData.add(new Datenformat(aktDatum,data.get(0).getTankenr(),aktDatum));
			   System.out.println(convertToDate((int)aktDatum)+"\n  "+data.get(0).getTankenr()+" | "+aktDatum);
			   aktDatum +=86400/24;
		   }
		 //  double yi = psf.value(xi);
		   return iData;
	}
	
	public static double[] getDatum(ArrayList<Datenformat> e){
		double[] datum= new double[e.size()];
		for(int i =0; i<e.size();i++){
			datum[i]= e.get(i).getDatum();
		}
		return datum;
	}
	public static double[] getvalue(ArrayList<Datenformat> e){
		double[] value= new double[e.size()];
		for(int i =0; i<e.size();i++){
			value[i]= e.get(i).getValue();
		}
		return value;
	}
	public static java.util.Date convertToDate(int timeStamp){
		java.util.Date time=new java.util.Date(timeStamp*1000L);
		return time;
	}
	public static java.util.Date dayFormat(java.util.Date time){
		time.setHours(1);
		time.setMinutes(0);
		time.setSeconds(0);
		return time;
	}
	public static double zuTimestampKonvertieren(java.util.Date d){
		double unixtimestamp = (double) d.getTime()/1000;
		return unixtimestamp;
	}	
}

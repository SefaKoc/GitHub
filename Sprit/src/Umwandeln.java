import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

public class Umwandeln {
	
	ArrayList<SpritData> data = new ArrayList<SpritData>();
	ArrayList<Long> datum = new ArrayList<Long>();
	ArrayList<Integer> tankennr = new ArrayList<Integer>();
	ArrayList<Double> value = new ArrayList<Double>();
	
	public void getData(String tabelle){
		data = DBReader1.getData(tabelle);
		datum = data.get(0).getDatum();
		tankennr = data.get(0).getTankennr();
		value= data.get(0).getValue();
		
	}
	
	public void sortieren(){
		double puffer;
		boolean s=false;
		do{
			s=false;
		for(int i=0; i < datum.length-1;i++){
			if(datum[i]>datum[i+1]){
				puffer=datum[i];
				datum[i]=datum[i+1];
				datum[i+1]=puffer;
				
				puffer=value[i];
				value[i]=value[i+1];
				value[i+1]=puffer;
				s=true;
			}
		}
		}while(s);
	};	
	
	public  double[] absolutBerechnen(int jahr){
		Date d = new Date();
		double summe = 0;
		double[]datumJ = new double[366];
		double[]valueJ = new double[366];
		int pos=0;
		for(int i=0; i<idatum.length;i++){
			
			if(timeConverter((long)idatum[i]).getYear()==jahr){
				datumJ[pos]=idatum[i];
			    valueJ[pos]=ivalue[i]; 
			    pos++;
			}	
		}		
		double[] valueM=new double[31];
		int counter=0;
		double[] absolutWerte=new double[12];
		
		for(int i = 0; i<12; i++){
			for(int a= 0; a<datumJ.length;a++){
				if(timeConverter((long)datumJ[a]).getMonth()==i){
					valueM[counter]=valueJ[a];
					counter++;
				}
			}
			absolutWerte[i]= valueM[valueM.length-1]-valueM[0];
			//datumM= null;
			valueM=new double[31];
			counter=0;
		}
		return absolutWerte;
	}
	
	public  double[] avgBerechnen(int jahr){
		Date d = new Date();
		double summe = 0;
		double[]datumJ = new double[366];
		double[]valueJ = new double[366];
		int pos=0;
		for(int i=0; i<idatum.length;i++){
			if(timeConverter((long)idatum[i]).getYear()==jahr){
				datumJ[pos]=idatum[i];
			    valueJ[pos]=ivalue[i]; 
			    pos++;
			}	
		}		
		double[] valueM=new double[31];
		int counter=0;
		double sum=0;
		double[] avgWerte=new double[12];
		for(int i = 0; i<12; i++){
			for(int a= 0; a<datumJ.length;a++){
				if(timeConverter((long)datumJ[a]).getMonth()==i){
					valueM[counter]=valueJ[a];
					counter++;
				}
			}
			for(int c=0; i<valueM.length;c++){
				 sum = sum+valueM[c];
				 avgWerte[c]=sum/valueM.length;	
			}
			valueM=new double[31];
			counter=0;
		}
		return avgWerte;
	}
	
	public static double[] interpolate(double start, double end, int count) {
	    if (count < 2) {
	        throw new IllegalArgumentException("interpolate: illegal count!");
	    }
	    double[] array = new double[count + 1];
	    for (int i = 0; i <= count; ++ i) {
	        array[i] = start + i * (end - start) / count;
	    }
	    return array;
	}
	
	public void interpolieren(){
		
		 int laenge;
		 Date anfangDate=timeConverter((long) datum[0]);
		 Date endDate=timeConverter((long) datum[datum.length-1]);
		
	     Calendar cal1 = new GregorianCalendar();
	     Calendar cal2 = new GregorianCalendar();

	     SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

	     Date date = anfangDate;
	     cal1.setTime(date);
	     date = endDate;
	     cal2.setTime(date);

	     laenge = daysBetween(cal1.getTime(),cal2.getTime());

	     System.out.println("Days= "+daysBetween(cal1.getTime(),cal2.getTime()));
	
   		 idatum= new double[laenge];
		 ivalue= new double[laenge];
		 
		 LinearInterpolator lp = new LinearInterpolator();
		 PolynomialSplineFunction ps = lp.interpolate(datum, value);
		 System.out.println(laenge);

		 idatum[0]= DateConverter(timeConverter( (long) datum[0]));
			
		 for(int i =1;i < idatum.length;i++){			
			idatum[i]= idatum[0]+(i* 86400);
			ivalue[i]= ps.value(idatum[i]);	
			System.out.println(test((long) idatum[i])+" |||| "+ ivalue[i]);
		 }
	}
	
	public Date timeConverter(long UNIX_timestamp){
		java.util.Date a=new java.util.Date((long)UNIX_timestamp*1000);	  
			  return a;
	}
	
	public String test(long UNIX_timestamp){
		  Date a = new Date(UNIX_timestamp * 1000);
		  String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		  int year = a.getYear();
		  String month = months[a.getMonth()];
		  int date = a.getDate();
		  int hour = a.getHours();
		  int min = a.getMinutes();
		  int sec = a.getSeconds();
		  String time = date +"    " + month + "  " + year + "  "    + hour +":" + min + ":" + sec ;
		  return time;
    }
	
	public long TagFormat(int jahr,int monat, int tag){
		  Date a = new Date();
		  String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
		  Date d= new Date();
		  d.setYear(jahr);
		  d.setMonth(monat);
		  d.setDate(tag);
		  d.setHours(1);
		  d.setMinutes(0);
		  d.setSeconds(0);
		  return DateConverter(d);
    }
	
	public long DateConverter(Date time){
		long unixTime = time.getTime() / 1000L;
        return unixTime;
	}	
	
	public int daysBetween(Date d1, Date d2){
         return (int)((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
 
	public static void main(String[] args) {
			Umwandeln uw = new Umwandeln();
			CsvFileWriter writer= new CsvFileWriter();
			System.out.println("Stromverbrauch aus der DB:");
			System.out.println();
			uw.getData("strom_verbrauch");
			uw.sortieren();
			uw.interpolieren();
			LinearInterpolator lp = new LinearInterpolator();
			PolynomialSplineFunction ps = lp.interpolate(datum, value);
			writer.writeCsvFile("strom_verbrauch_abs1.csv", uw.absolutBerechnen(116));
//			writer.writeCsvFile("strom_verbrauch_avg1.csv", uw.avgBerechnen(116));
			System.out.println();
			System.out.println("Wasserverbrauch aus der DB:");
			System.out.println();
			Umwandeln uw2 = new Umwandeln();
			CsvFileWriter wr= new CsvFileWriter();
			uw2.getData("wasser_verbrauch");
			uw2.sortieren();
			uw2.interpolieren();
			LinearInterpolator l = new LinearInterpolator();
			PolynomialSplineFunction p = l.interpolate(datum, value);
			wr.writeCsvFile("wasser_abs.csv", uw2.absolutBerechnen(116));
//			wr.writeCsvFile("csv_wasser_avg.csv", uw2.avgBerechnen(116));
		}	
	
}

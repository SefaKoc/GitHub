
public class Datenformat {

	double[]datum;
	double[]value;
	public Datenformat() {
		super();
	}
	public Datenformat(double[] datum, double[] value) {
		super();
		this.datum = datum;
		this.value = value;
	}
	public double[] getDatum() {
		return datum;
	}
	public void setDatum(double[] datum) {
		this.datum = datum;
	}
	public double[] getValue() {
		return value;
	}
	public void setValue(double[] value) {
		this.value = value;
	}



}


public class SpritData {
	long datum;
	int tankennr;
	double value;
	
	public SpritData() {
	}
	public SpritData(long datum, int tankennr, double value) {
		this.datum = datum;
		this.tankennr = tankennr;
		this.value = value;
	}
	public long getDatum() {
		return datum;
	}
	public void setDatum(long datum) {
		this.datum = datum;
	}
	public int getTankennr() {
		return tankennr;
	}
	public void setTankennr(int tankennr) {
		this.tankennr = tankennr;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
	
}

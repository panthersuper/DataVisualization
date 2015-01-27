package Test;

public class TableWeather {
	double lng;//0
	double lat;//1
	double temp_C;//2
	double weather;//3
	double humidity;//4
	double winDeg;//5
	double winSp;//6
	double pressure;//7 air pressure
	double visD;//8 view distance

	public TableWeather(double lat, double lng, double temp_C, double weather, double humidity, double winDeg, double winSp, double pressure, double visD){
		this.lat = lat;
		this.lng = lng;
		this.temp_C = temp_C;
		this.weather = weather;
		this.humidity = humidity;
		this.winDeg = winDeg;
		this.winSp = winSp;
		this.pressure = pressure;
		this.visD = visD;
	}
	
	public float distSQ(int x, int y){
		float[] dd = CSVWeather.mmap(this);
		
		return (x-dd[0])*(x-dd[0])+(y-dd[1])*(y-dd[1]);
	}
	
	
	
	
	
}

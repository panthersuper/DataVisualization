package OSMData;

public class tag {
	private String k ;//key word
	private String v ;//value
	
	
	public tag(String k, String v){
		this.k = k;
		this.v = v;
	}
	
	public String getK(){
		return k;
	}
	
	public String getV(){
		return v;
	}
}

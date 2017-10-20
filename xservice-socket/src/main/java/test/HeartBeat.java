package test;

public class HeartBeat {

	private String lat;

	private String lon;

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	@Override
	public String toString() {
		return "HeartBeat [lat=" + lat + ", lon=" + lon + "]";
	}
}

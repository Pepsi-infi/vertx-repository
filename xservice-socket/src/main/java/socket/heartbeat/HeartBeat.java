package socket.heartbeat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeartBeat {

	private String accuracy;

	private String altitude;

	private String appType;

	private String bearing;

	private String date;

	private String dutyStatus;

	private String inCoordType;

	private String lat;

	private String lon;

	private String networkType;

	private String orderNo;

	private String passwordCode;

	private String positionTime;

	private String provider;

	private String serviceStatus;

	private String speed;

	private String token;

	private String version;

	private String zipcode;

	public String getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getBearing() {
		return bearing;
	}

	public void setBearing(String bearing) {
		this.bearing = bearing;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDutyStatus() {
		return dutyStatus;
	}

	public void setDutyStatus(String dutyStatus) {
		this.dutyStatus = dutyStatus;
	}

	public String getInCoordType() {
		return inCoordType;
	}

	public void setInCoordType(String inCoordType) {
		this.inCoordType = inCoordType;
	}

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

	public String getNetworkType() {
		return networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getPasswordCode() {
		return passwordCode;
	}

	public void setPasswordCode(String passwordCode) {
		this.passwordCode = passwordCode;
	}

	public String getPositionTime() {
		return positionTime;
	}

	public void setPositionTime(String positionTime) {
		this.positionTime = positionTime;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Override
	public String toString() {
		return "HeartBeat [accuracy=" + accuracy + ", altitude=" + altitude + ", appType=" + appType + ", bearing="
				+ bearing + ", date=" + date + ", dutyStatus=" + dutyStatus + ", inCoordType=" + inCoordType + ", lat="
				+ lat + ", lon=" + lon + ", networkType=" + networkType + ", orderNo=" + orderNo + ", passwordCode="
				+ passwordCode + ", positionTime=" + positionTime + ", provider=" + provider + ", serviceStatus="
				+ serviceStatus + ", speed=" + speed + ", token=" + token + ", version=" + version + ", zipcode="
				+ zipcode + "]";
	}

}

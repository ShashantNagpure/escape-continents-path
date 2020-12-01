
public class City {
	
	public String name;
	public String continent;
	public Double longitude;
	public Double latitude;
	public String cityCode;
	


	public City(String name, String continent, Double longitude, Double latitude, String cityCode) {
		super();
		this.name = name;
		this.continent = continent;
		this.longitude = longitude;
		this.latitude = latitude;
		this.cityCode = cityCode;
	}
	
	@Override
	public String toString() {
		return getCityCode()+ " ("+ getName()+", "+getContinent()+")";
	}
	
	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
}

import java.util.ArrayList;
import java.util.List;

public class Path {

	public List<City> Cities;
	public int distance;

	public List<City> getCity() {
		return Cities;
	}

	public void setCity(List<City> Cities) {
		this.Cities = Cities;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public Path(List<City> Cities, int distance) {

		this.Cities = Cities;
		this.distance = distance;
	}

	@Override
	public String toString() {
		String res = "";
		for (City c : Cities) {
			res += c.toString() + " -> ";
		}

		res += Cities.get(0).toString();
		res += "\n";
		res += "Distance travelled: " + getDistance() + "KMS\n";
		return res;

	}

}

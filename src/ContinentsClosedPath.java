import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ContinentsClosedPath {
	public static int nContinents = 6;
	public static String[] continents = { "asia", "europe", "north-america", "africa", "south-america", "oceania" };
	public static Map<String, Integer> continentsIndexMap = new HashMap<String, Integer>();
	public static ArrayList<City>[] graph = new ArrayList[nContinents];
	public static JSONObject cityMap;

	public static City getCityFromMap(String cityCode) {
		JSONObject cityObject = (JSONObject) cityMap.get(cityCode);
		String contId = (String) cityObject.get("contId");
		String name = (String) cityObject.get("name");
		JSONObject location = (JSONObject) cityObject.get("location");
		Double latitude = (Double) location.get("lat");
		Double longitude = (Double) location.get("lon");
		return new City(name, contId, longitude, latitude, cityCode);
	}

	public static Pair getClosestCity(int contIndex, City city) {
		int min = Integer.MAX_VALUE;
		City res = null;
		for (City c : graph[contIndex]) {
			int dist = getDistanceFromLatLonInKm(c.latitude, c.longitude, city.latitude, city.longitude);
			if (dist < min) {
				min = dist;
				res = c;
			}
		}
		return new Pair(res, min);

	}

	public static Pair getClosestContinentCity(Set<Integer> set, City city) {
		int min = Integer.MAX_VALUE;
		Pair res = null;
		for (Integer i : set) {
			Pair currCity = getClosestCity(i, city);
			if (currCity.getDistance() < min) {
				min = currCity.getDistance();
				res = currCity;
			}
		}
		return res;
	}

	public static Path generatePath(City source) {

		Integer allContIndices[] = { 0, 1, 2, 3, 4, 5 };
		Set<Integer> conts = new HashSet<>(Arrays.asList(allContIndices));

		int si = continentsIndexMap.get(source.getContinent());
		conts.remove(si);

		List<City> res = new ArrayList<>();
		res.add(source);

		int dist = 0;
		City currCity = source;

		while (!conts.isEmpty()) {
			Pair nextCity = getClosestContinentCity(conts, currCity);
			conts.remove(continentsIndexMap.get(nextCity.getCity().getContinent()));
			res.add(nextCity.getCity());
			dist += nextCity.getDistance();
			currCity = nextCity.getCity();
		}
		dist += getDistanceFromLatLonInKm(currCity.getLatitude(), currCity.getLongitude(), source.getLatitude(),
				source.getLongitude());
		return new Path(res, dist);
	}

	public static int getDistanceFromLatLonInKm(double lat1, double lon1, double lat2, double lon2) {
		int R = 6371; // Radius of the earth in km
		double dLat = deg2rad(lat2 - lat1); // deg2rad below
		double dLon = deg2rad(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		int d = (int) (R * c); // Distance in km
		return d;
	}

	public static double deg2rad(double deg) {
		return deg * (Math.PI / 180);
	}

	// Driver code
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		for (int i = 0; i < continents.length; i++) {
			continentsIndexMap.put(continents[i], i);
		}
		for (int i = 0; i < continents.length; i++) {
			graph[i] = new ArrayList<City>();
		}

		try (InputStream inputStream = ContinentsClosedPath.class.getResourceAsStream("cities.json");
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
			cityMap = (JSONObject) new JSONParser().parse(reader);
		}
		cityMap.keySet().forEach(keyStr -> {
			City city = getCityFromMap((String) keyStr);
			graph[continentsIndexMap.get(city.getContinent())].add(city);
		});

		InputStream stream = System.in;
		Scanner scanner = new Scanner(stream);
		System.out.println("Enter City Code: ");
		String input = scanner.next();
		if (cityMap.containsKey(input)) {
			System.out.println(generatePath(getCityFromMap(input)));
		} else {
			System.out.println("Wrong City Code");
		}
		scanner.close();

	}
}

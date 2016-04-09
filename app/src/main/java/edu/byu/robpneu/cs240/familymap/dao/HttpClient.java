package edu.byu.robpneu.cs240.familymap.dao;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import edu.byu.robpneu.cs240.familymap.model.Event;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;
import edu.byu.robpneu.cs240.familymap.model.Filter;
import edu.byu.robpneu.cs240.familymap.model.Person;

/**
 * @author Robert P Neu
 * @version 1.0 3/17/16
 */
public class HttpClient {
	private static HttpClient mHttpClient;
	private String urlString;

	private HttpClient(){
	}

	public static HttpClient getInstance(){
		if (mHttpClient == null){
			mHttpClient = new HttpClient();
		}
		return mHttpClient;
	}

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public boolean login(){
		JSONObject rootObj = httpPost("/user/login", null,
				"{ " +
						" username:\"" + Login.getInstance().getUserName() + "\"," +
						" password:\"" + Login.getInstance().getPassword() + "\"" +
						"}");

		try {
			if (rootObj.has("Authorization")) {
				Log.i("HTTPClient", "Successfully logged in!");
				Login.getInstance().setAuthToken(rootObj.getString("Authorization"));
				Login.getInstance().setPersonID(rootObj.getString("personId"));
				return true;
			} else {
				Log.i("HTTPClient", "Login Failed! " + rootObj.toString());
				return false;
			}
		}
		catch (Exception e){
			Log.e("httpLogin", "JSONObject had an issue", e);
		}
		return false;
	}

	public Map<String, Person> getAllPeople(){
		String urlApi = "/person/";
		String header = Login.getInstance().getAuthToken();

		JSONObject rootObj = httpGet(urlApi, header);
		Map<String, Person> people = new HashMap<>();
		JSONArray peopleArray = null;
		try {
			if (rootObj.has("data")) {
				peopleArray = rootObj.getJSONArray("data");
			}

			for (int i = 0; i < peopleArray.length(); i++){
				JSONObject elemObj = peopleArray.getJSONObject(i);
				String descendant = elemObj.getString("descendant");
				String personID = elemObj.getString("personID");
				String firstName = elemObj.getString("firstName");
				String lastName = elemObj.getString("lastName");
				String gender = elemObj.getString("gender");
				String fatherID = null;
				String motherID = null;
				String spouse = null;

				if (elemObj.has("spouse")){
					spouse = elemObj.getString("spouse");
				}

				if (elemObj.has("father")){
					fatherID = elemObj.getString("father");
				}
				if(elemObj.has("mother")) {
					motherID = elemObj.getString("mother");
				}

				Person p = new Person(descendant, personID, firstName, lastName, gender, fatherID, motherID, spouse);
				people.put(personID, p);
			}
		}
		catch (Exception e){
			Log.e("httpLogin", "JSONObject had an issue", e);
		}
		return people;
	}

	public Person getPerson(String personID){
		String urlApi = "/person/" + Login.getInstance().getPersonID();
		String header = Login.getInstance().getAuthToken();

		JSONObject rootObj = httpGet(urlApi, header);
		Person p = null;
		try {
			if (rootObj.has("personID")) {
				String descendant = rootObj.getString("descendant");
				String firstName = rootObj.getString("firstName");
				String lastName = rootObj.getString("lastName");
				String gender = rootObj.getString("gender");
				String fatherID = rootObj.getString("father");
				String motherID = rootObj.getString("mother");
				String spouse = null;
				if (rootObj.has("spouse")) {
					spouse = rootObj.getString("spouse");
				}
				p = new Person(descendant, personID, firstName, lastName, gender, fatherID, motherID, spouse);
			}
		}
		catch (Exception e){
			Log.e("httpLogin", "JSONObject had an issue", e);
		}
		return p;
	}

	public Map<String, Event> getAllEvents(){
		String urlApi = "/event/";
		String header = Login.getInstance().getAuthToken();

		JSONObject rootObj = httpGet(urlApi, header);
		Map<String, Event> events = new HashMap<>();
		JSONArray eventsArray = null;
		try {
			if (rootObj.has("data")) {
				eventsArray = rootObj.getJSONArray("data");
			}

			for (int i = 0; i < eventsArray.length(); i++){
				JSONObject elemObj = eventsArray.getJSONObject(i);
				String eventID = elemObj.getString("eventID");
				String personID = elemObj.getString("personID");
				Double latitude = elemObj.getDouble("latitude");
				Double longitude = elemObj.getDouble("longitude");
				String country = elemObj.getString("country");
				String city = elemObj.getString("city");
				String description = elemObj.getString("description");
				Double year = elemObj.getDouble("year");
				String descendant = elemObj.getString("descendant");

				Event e = new Event(eventID, personID, latitude, longitude, country, city, description, year, descendant);
				events.put(eventID, e);

				Filter filter = new Filter(description);
				FamilyMap.getInstance().addFilter(filter);
			}
		}
		catch (Exception e){
			Log.e("httpLogin", "JSONObject had an issue", e);
		}
		return events;
	}

	public JSONObject httpPost(String apiUrl, String headerData, String bodyData){
		try {
			URL url = new URL("http://" + urlString + apiUrl);

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();

			connection.setRequestMethod("POST");
			connection.setDoOutput(true);

			if (headerData != null){
				// Set HTTP request headers, if necessary
				connection.addRequestProperty("Authorization", headerData);

			}

			connection.connect();

			if (bodyData != null) {
				// Write post data to request body
				OutputStream requestBody = connection.getOutputStream();
				requestBody.write(bodyData.getBytes());
				requestBody.close();
			}

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// Get HTTP response headers, if necessary
				// Map<String, List<String>> headers = connection.getHeaderFields();

				// Get response body input stream
				InputStream responseBody = connection.getInputStream();

				// Read response body bytes
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024]; // TODO double check buffer size
				int length = 0;
				while ((length = responseBody.read(buffer)) != -1) {
					byteArrayOutputStream.write(buffer, 0, length);
				}

				// Convert response body bytes to a string
				String responseBodyData = byteArrayOutputStream.toString();

				JSONObject rootObj = new JSONObject(responseBodyData);
				return rootObj;
			}
//			else {
//				// SERVER RETURNED AN HTTP ERROR
//
//			}
		}
		catch (IOException e) {
			// IO ERROR
		}
		catch (JSONException e) {
			e.printStackTrace();
		}

		return null;
	}

	public JSONObject httpGet(String apiUrl, String headerData){
		try {
			URL url = new URL("http://" + urlString + apiUrl);

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();

			connection.setRequestMethod("GET");
			connection.setDoOutput(true);

			if (headerData != null) {
				// Set HTTP request headers, if necessary
				connection.addRequestProperty("Authorization", headerData);
			}

			connection.connect();

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// Get HTTP response headers, if necessary
				// Map<String, List<String>> headers = connection.getHeaderFields();

				// Get response body input stream
				InputStream responseBody = connection.getInputStream();

				// Read response body bytes
				ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int length = 0;
				while ((length = responseBody.read(buffer)) != -1) {
					byteArrayOutputStream.write(buffer, 0, length);
				}

				// Convert response body bytes to a string
				String responseBodyData = byteArrayOutputStream.toString();

				JSONObject rootObj = new JSONObject(responseBodyData);
				return rootObj;
			}
			else {
				// SERVER RETURNED AN HTTP ERROR
			}
		}
		catch (IOException e) {
			// IO ERROR
		}
		catch (JSONException e){
			e.printStackTrace();
		}

		return null;
	}
}

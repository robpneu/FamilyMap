package edu.byu.robpneu.cs240.familymap.dao;

/**
 * Object to keep track of all DAO login information. Essentially the object helper for the HttpClient object.
 *
 * @author Robert P Neu
 * @version 1.0 3/17/16
 */
public class Login {

	private static Login instance;
	private String mUserName;
	private String mPassword;
	private String mServerHost;
	private String mServerPort;
	private String mAuthToken;
	private String mPersonID;



	private Login(){
		// Default login info to speed up debugging/testing
//		mUserName = "r";
//		mPassword = "n";
//		mServerHost = "10.25.100.50";
////		mServerHost = "10.10.10.43";
//		mServerPort = "8081";

		// TA Credentials
//		mUserName = "sheila";
//		mPassword = "parker";
//		mServerHost = "192.168.250.17";
//		mServerPort = "8888";
	}

	/**
	 * Returns the singleton instance
	 *
	 * @return instance of the Login object.
	 */
	public static Login getInstance(){
		if (instance == null){
			instance = new Login();
		}
		return instance;
	}

	public String getUserName() {
		return mUserName;
	}

	public void setUserName(String userName) {
		mUserName = userName;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String password) {
		mPassword = password;
	}

	public void setServerHost(String serverHost) {
		mServerHost = serverHost;
	}

	public void setServerPort(String serverPort) {
		mServerPort = serverPort;
	}

	/**
	 * Gets a usable url based on the server host and server port variables
	 * @return the full URL
	 */
	public String getURL(){
		return mServerHost + ":" + mServerPort;
	}

	public String getAuthToken() {
		return mAuthToken;
	}

	public void setAuthToken(String authToken) {
		mAuthToken = authToken;
	}

	public String getPersonID() {
		return mPersonID;
	}

	public void setPersonID(String personID) {
		mPersonID = personID;
	}

	/**
	 * Resets all of the login information when it is called to ensure that information can't be used without re-authenticating
	 */
	public void logout(){
		mUserName = null;
		mPassword = null;
		mServerHost = null;
		mServerPort = null;
		mPersonID = null;
		mAuthToken = null;
	}
}

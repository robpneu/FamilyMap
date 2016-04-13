package edu.byu.robpneu.cs240.familymap.dao;

import android.test.AndroidTestCase;

import org.junit.Test;

/**
 * @author Robert P Neu
 * @version 1.0 4/12/16
 */
public class HttpClientTest extends AndroidTestCase {

	@Test
	public void testLogin() throws Exception {
		// Sets up the Login and HttpClient objects
		Login login = Login.getInstance();
		HttpClient httpClient = HttpClient.getInstance();

		//Should fail with no info input
		assertEquals(httpClient.login(), false);

		//set bad user info with good host info
		login.setUserName("a");
		login.setPassword("b");
		login.setServerHost("10.10.10.42");
		login.setServerPort("8081");

		// should fail with bad login info
		assertEquals(httpClient.login(), false);
		//the personID and AuthToken in the login object should be null
		assertEquals(login.getAuthToken() == null, true);
		assertEquals(login.getPersonID() == null, true);

		// set good username, keep good host info
		login.setUserName("r");
		login.setPassword("n");

		//should succeed with good login info
		assertEquals(httpClient.login(), true);
		//the personID and AuthToken in the login object should no longer be null
		assertEquals(login.getAuthToken() == null, false);
		assertEquals(login.getPersonID() == null, false);

		//set bad host info, keep good login info
		login.setServerHost("10.10.10.100");
		login.setServerPort("8988");

		assertEquals(httpClient.login(), false);
		//the personID and AuthToken in the login object should be null
		assertEquals(login.getAuthToken() == null, true);
		assertEquals(login.getPersonID() == null, true);
	}
}
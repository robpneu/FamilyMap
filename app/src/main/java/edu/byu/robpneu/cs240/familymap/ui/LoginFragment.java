package edu.byu.robpneu.cs240.familymap.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

import edu.byu.robpneu.cs240.familymap.R;
import edu.byu.robpneu.cs240.familymap.dao.HttpClient;
import edu.byu.robpneu.cs240.familymap.dao.Login;
import edu.byu.robpneu.cs240.familymap.model.FamilyMap;

/**
 * @author Robert P Neu
 * @version 1.0 3/17/16
 */
public class LoginFragment extends Fragment {
	private Login mLogin;
	private EditText mNameField;
	private EditText mPasswordField;
	private EditText mServerHostField;
	private EditText mServerPortField;
	private Button mLoginButton;

	/**
	 * Called to do initial creation of a fragment.  This is called after
	 * {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
	 * <p/>
	 * <p>Note that this can be called while the fragment's activity is
	 * still in the process of being created.  As such, you can not rely
	 * on things like the activity's content view hierarchy being initialized
	 * at this point.  If you want to do work once the activity itself is
	 * created, see {@link #onActivityCreated(Bundle)}.
	 *
	 * @param savedInstanceState If the fragment is being re-created from
	 *                           a previous saved state, this is the state.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLogin = Login.getInstance();
	}

	/**
	 * Called to have the fragment instantiate its user interface view.
	 * This is optional, and non-graphical fragments can return null (which
	 * is the default implementation).  This will be called between
	 * {@link #onCreate(Bundle)} and {@link #onActivityCreated(Bundle)}.
	 * <p/>
	 * <p>If you return a View from here, you will later be called in
	 * {@link #onDestroyView} when the view is being released.
	 *
	 * @param inflater           The LayoutInflater object that can be used to inflate
	 *                           any views in the fragment,
	 * @param container          If non-null, this is the parent view that the fragment's
	 *                           UI should be attached to.  The fragment should not add the view itself,
	 *                           but this can be used to generate the LayoutParams of the view.
	 * @param savedInstanceState If non-null, this fragment is being re-constructed
	 *                           from a previous saved state as given here.
	 * @return Return the View for the fragment's UI, or null.
	 */
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(false);
		View v = inflater.inflate(R.layout.fragment_login, container, false);
		mNameField = (EditText)v.findViewById(R.id.nameField);
		mNameField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// left blank....?
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.i("LoginFragment", "Name was set to " + s.toString());
				mLogin.setUserName(s.toString());
			}
		});


		mPasswordField = (EditText)v.findViewById(R.id.passwordField);
		mPasswordField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// left blank....?
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.i("LoginFragment", "Password was set to " + s.toString());
				mLogin.setPassword(s.toString());
			}
		});

		mServerHostField = (EditText)v.findViewById(R.id.serverHostField);
		mServerHostField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// left blank....?
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.i("LoginFragment", "Server Host was set to " + s.toString());
				mLogin.setServerHost(s.toString());
			}
		});

		mServerPortField = (EditText)v.findViewById(R.id.serverPortField);
		mServerPortField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// left blank....?
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.i("LoginFragment", "Server Port was set to " + s.toString());
				mLogin.setServerPort(s.toString());
			}
		});

		mLoginButton = (Button)v.findViewById(R.id.signInBtn);
		mLoginButton.setOnClickListener(new View.OnClickListener() {

			/**
			 * Called when a view has been clicked.
			 *
			 * @param v The view that was clicked.
			 */
			@Override
			public void onClick(View v) {
				Log.i("LoginFragment", "Login Button was clicked");
				HttpClient.getInstance().setUrlString(Login.getInstance().getURL());
				DownloadTask downloadTask = new DownloadTask();
				downloadTask.execute();
			}
		});

		return v;
	}

	/**
	 * Starts the login asynch task
	 * On successful post execute of the asynch task it will begin the onLogin method in the main activity
	 */
	public class DownloadTask extends AsyncTask<URL, Integer, String>{
		/**
		 * Override this method to perform a computation on a background thread. The
		 * specified parameters are the parameters passed to {@link #execute}
		 * by the caller of this task.
		 * <p/>
		 * This method can call {@link #publishProgress} to publish updates
		 * on the UI thread.
		 *
		 * @param params The parameters of the task.
		 * @return A result, defined by the subclass of this task.
		 * @see #onPreExecute()
		 * @see #onPostExecute
		 * @see #publishProgress
		 */
		@Override
		protected String doInBackground(URL... params) {
			Log.i("DownloadTask", "doInBackground begun");
			if (HttpClient.getInstance().login())
				return HttpClient.getInstance()
						.getPerson(Login.getInstance().getPersonID())
						.getFullName();
			else {
				return null;
			}
		}

		/**
		 * <p>Runs on the UI thread after {@link #doInBackground}. The
		 * specified result is the value returned by {@link #doInBackground}.</p>
		 * <p/>
		 * <p>This method won't be invoked if the task was cancelled.</p>
		 *
		 * @param s The result of the operation computed by {@link #doInBackground}.
		 * @see #onPreExecute
		 * @see #doInBackground
		 * @see #onCancelled(Object)
		 */
		@Override
		protected void onPostExecute(String s) {
			Log.i("DownloadTask", "onPostExecute");
			if (s == null){
				Log.i("loginToast", "Fail!");
				Toast.makeText(getActivity(), "Login Failed. Please try again.", Toast.LENGTH_LONG).show();
			}
			else {
				Log.i("loginToast", "Success! " + s);
				Toast.makeText(getActivity(), "Welcome " + s + "!", Toast.LENGTH_LONG).show();
				FamilyMap.getInstance().setLoggedIn(true);
				((MainActivity) getActivity()).onLogin();
			}
		}
	}

}

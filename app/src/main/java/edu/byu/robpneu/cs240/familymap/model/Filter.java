package edu.byu.robpneu.cs240.familymap.model;

import java.util.Locale;
import java.util.Random;

/**
 * @author Robert P Neu
 * @version 1.0 3/26/16
 */
public class Filter {
	private String mDescription;
	private String mFilterKey;
	private boolean mShown;
	private int mColor;

	public Filter(String filterKey){
		mDescription = filterKey.substring(0,1).toUpperCase(Locale.getDefault()) + filterKey.substring(1);
		mFilterKey = filterKey;
		Random random = new Random();
		mColor = (random.nextInt(360));
		mShown = true;
	}

	public Filter(String description, String filterKey) {
		this.mDescription = description;
		this.mFilterKey = filterKey;
		this.mShown = true;
	}

	public String getFilterKey() {
		return mFilterKey;
	}

	public String getDescription() {
		return mDescription;
	}

	public int getColor() {
		return mColor;
	}

	public boolean isShown() {
		return mShown;
	}

	public void setShown(boolean shown) {
		this.mShown = shown;
	}
}

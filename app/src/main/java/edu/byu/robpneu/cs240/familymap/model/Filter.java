package edu.byu.robpneu.cs240.familymap.model;

import android.graphics.Color;

import com.joanzapata.android.iconify.IconDrawable;

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
	private IconDrawable mIcon;
	private int mColor;

	/**
	 * Filter object constructor
	 *
	 * @param filterKey the key of the filter.
	 */
	public Filter(String filterKey){
		mDescription = filterKey.substring(0,1).toUpperCase(Locale.getDefault()) + filterKey.substring(1);
		mFilterKey = filterKey;
		Random random = new Random();
		mColor = (random.nextInt(360));
		mShown = true;
	}

	/**
	 * Filter object constructor
	 * @param description the filter description
	 * @param filterKey the key of the filter.
	 */
	public Filter(String description, String filterKey) {
		this.mDescription = description;
		this.mFilterKey = filterKey;
		Random random = new Random();
		mColor = (random.nextInt(360));
		this.mShown = true;
	}

	/**
	 * Gets the filter key
	 * @return the filter key string
	 */
	public String getFilterKey() {
		return mFilterKey;
	}

	/**
	 * Get the filter description
	 * @return string of the filter description
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * Get the randomly assigned HSV color
	 * @return integer of the HSV color
	 */
	public int getHSVColor() {
		return mColor;
	}

	/**
	 * return the status of the filter
	 * @return if the filter is on or off
	 */
	public boolean isShown() {
		return mShown;
	}

	/**
	 * Set the status of the filter
	 * @param shown if the filter is on or off
	 */
	public void setShown(boolean shown) {
		this.mShown = shown;
	}

	/**
	 * Gets the filter icon
	 * @return IconDrawable Filter Icon
	 */
	public IconDrawable getIcon() {
		return mIcon.color(getColor());
	}

	/**
	 * Gets the hue color
	 * @return the integer of the hue color
	 */
	public int getColor() {
		float[] color = {mColor, 1, 1};
		return Color.HSVToColor(color);
	}

	/**
	 * Set the filter icon
	 * @param icon IconDrawable  icon to be set
	 */
	public void setIcon(IconDrawable icon) {
		mIcon = icon;
	}
}

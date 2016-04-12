package edu.byu.robpneu.cs240.familymap.model;

import android.graphics.Color;

/**
 * @author Robert P Neu
 * @version 1.0 3/26/16
 */
public class Settings {
	private static final int orange = Color.rgb(255, 165, 0);
	private static final int purple = Color.rgb(128, 0, 128);
	private static final int brown = Color.rgb(165, 42, 42);
	private static Settings instance;
	private boolean mLifeStoryLines;
	private int mLifeStoryLinesColor;
	private boolean mFamilyStoryLines;
	private int mFamilyStoryLinesColor;
	private boolean mSpouseLines;
	private int mSpouseLinesColor;
	private int mMapType;

	/**
	 * Private constructor for a settings object
	 * Sets all of the default settings
	 */
	private Settings() {
		mLifeStoryLines = true;
		mLifeStoryLinesColor = Color.RED;
		mFamilyStoryLines = true;
		mFamilyStoryLinesColor = Color.BLUE;
		mSpouseLines = true;
		mSpouseLinesColor = Color.GREEN;
		mMapType = 1; // defaults to Map_Type_Noraml, which is int = 1;
	}

	/**
	 * Get the singleton instance
	 * @return the instance of the setting object
	 */
	public static Settings getInstance(){
		if (instance == null){
			instance = new Settings();
		}
		return instance;
	}

	/**
	 * If the life story lines should be shown
	 * @return boolean if the life story lines should be shown
	 */
	public boolean showLifeStoryLines() {
		return mLifeStoryLines;
	}

	/**
	 * Sets if the life story lines should be shown
	 * @param lifeStoryLines boolean if the life story lines should be shown
	 */
	public void setShowLifeStoryLines(boolean lifeStoryLines) {
		mLifeStoryLines = lifeStoryLines;
	}

	/**
	 * Get the life story lines color
	 * @return RGB in color
	 */
	public int getLifeStoryLinesColor() {
		return mLifeStoryLinesColor;
	}

	/**
	 * Get the location in the spinner of the life story lines color spinner
	 * @return location in the spinner
	 */
	public int getLifeStoryLinesSpinnerColor(){
		return getSpinnerColor(mLifeStoryLinesColor);
	}

	/**
	 * Get the color given by the give location on the spinner
	 * @param incomingColor the location the spinner
	 * @return the corresponding color int
	 */
	private int getSpinnerColor(int incomingColor){
		if (incomingColor == Color.RED)
			return 0;
		else if(incomingColor == orange)
			return 1;
		else if(incomingColor == Color.YELLOW)
			return 2;
		else if(incomingColor == Color.GREEN)
			return 3;
		else if(incomingColor == Color.BLUE)
			return 4;
		else if(incomingColor == purple)
			return 5;
		else if(incomingColor == brown)
			return 6;
		else if(incomingColor == Color.BLACK)
			return 7;
		else
			return 0;
	}

	/**
	 * Set the color of the life story line
	 * @param lifeStoryLinesColor
	 */
	public void setShowLifeStoryLinesColor(int lifeStoryLinesColor) {
		mLifeStoryLinesColor = lifeStoryLinesColor;
	}

	/**
	 * If the family story lines should be shown
	 * @return boolean if the life story lines should be shown
	 */
	public boolean showFamilyStoryLines() {
		return mFamilyStoryLines;
	}

	/**
	 * Sets if the family story lines should be shown
	 * @param familyStoryLines boolean if the family story lines should be shown
	 */
	public void setShowFamilyStoryLines(boolean familyStoryLines) {
		mFamilyStoryLines = familyStoryLines;
	}

	/**
	 * Get the family story lines color
	 * @return RGB in color
	 */
	public int getFamilyStoryLinesColor() {
		return mFamilyStoryLinesColor;
	}

	/**
	 * Get the location in the spinner of the family story lines color spinner
	 * @return location in the spinner
	 */
	public int getFamilyStoryLinesSpinnerColor(){
		return getSpinnerColor(mFamilyStoryLinesColor);
	}

	/**
	 * Set the color of the life story line
	 * @param familyStoryLinesColor RGB color
	 */
	public void setShowFamilyStoryLinesColor(int familyStoryLinesColor) {
		mFamilyStoryLinesColor = familyStoryLinesColor;
	}

	/**
	 * If the spouse lines should be shown
	 * @return boolean if the life story lines should be shown
	 */
	public boolean showSpouseLines() {
		return mSpouseLines;
	}

	/**
	 * Sets if the spouse lines should be shown
	 * @param spouseLines boolean if the life story lines should be shown
	 */
	public void setShowSpouseLines(boolean spouseLines) {
		mSpouseLines = spouseLines;
	}

	/**
	 * Get the spouse lines color
	 * @return RGB in color
	 */
	public int getSpouseLinesColor() {
		return mSpouseLinesColor;
	}

	/**
	 * Get the location in the spinner of the spouse lines color spinner
	 * @return location in the spinner
	 */
	public int getSpouseLinesSpinnerColor(){
		return getSpinnerColor(mSpouseLinesColor);
	}

	/**
	 * Set the color of the spouse lines
	 * @param spouseLinesColor RGB color
	 */
	public void setShowSpouseLinesColor(int spouseLinesColor) {
		mSpouseLinesColor = spouseLinesColor;
	}

	/**
	 * Get the map type
	 * @return the map type int
	 */
	public int getMapType() {
		return mMapType;
	}

	/**
	 * Sets the Map type
	 * According to Amazon API: https://developer.amazon.com/public/apis/experience/maps/javadocs-v2/maps-api-v2-reference
	 * 0 = None (no map tiles are loaded)
	 * 1 = Normal
	 * 2 = Satellite
	 * 3 = Terrain
	 * 4 = Hybrid
	 *
	 * @param mapType
	 */
	public void setShowMapType(int mapType) {
		/*
		According to Amazon API: https://developer.amazon.com/public/apis/experience/maps/javadocs-v2/maps-api-v2-reference
		0 = None (no map tiles are loaded)
		1 = Normal
		2 = Satellite
		3 = Terrain
		4 = Hybrid
		 */
		mMapType = mapType;
	}

	/**
	 * Returns the corresponding map spinner types
	 * location 0 is Normal(Map type 1)
	 * location 1 is Hybrid (map type 4)
	 * location 2 is Satellite(map type 2)
	 * Location 3 is Terrain(map type 3)
	 * @return the map type
	 */
	public int getMapSpinnerType(){
		switch (mMapType){
			case 1:
				return 0;
			case 2:
				return 2;
			case 3:
				return 3;
			case 4:
				return 1;
			default:
				return 0;
		}
	}
}

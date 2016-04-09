package edu.byu.robpneu.cs240.familymap.model;

import android.graphics.Color;

/**
 * @author Robert P Neu
 * @version 1.0 3/26/16
 */
public class Settings {
	private static Settings instance;
	private boolean mLifeStoryLines;
	private int mLifeStoryLinesColor;
	private boolean mFamilyStoryLines;
	private int mFamilyStoryLinesColor;
	private boolean mSpouseLines;
	private int mSpouseLinesColor;
	private int mMapType;

	private static final int orange = Color.rgb(255, 165, 0);
	private static final int purple = Color.rgb(128, 0, 128);
	private static final int brown = Color.rgb(165, 42, 42);

	private Settings() {
		// TODO Figure out default settings
		mLifeStoryLines = false;
		mLifeStoryLinesColor = Color.RED;
		mFamilyStoryLines = false;
		mFamilyStoryLinesColor = Color.BLUE;
		mSpouseLines = false;
		mSpouseLinesColor = Color.GREEN;
		mMapType = 1; // defaults to Map_Type_Noraml, which is int = 1;
	}

	public static Settings getInstance(){
		if (instance == null){
			instance = new Settings();
		}
		return instance;
	}

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

	public boolean showLifeStoryLines() {
		return mLifeStoryLines;
	}

	public void setShowLifeStoryLines(boolean lifeStoryLines) {
		mLifeStoryLines = lifeStoryLines;
	}

	public int getLifeStoryLinesColor() {
		return mLifeStoryLinesColor;
	}

	public int getLifeStoryLinesSpinnerColor(){
		return getSpinnerColor(mLifeStoryLinesColor);
	}

	public void setShowLifeStoryLinesColor(int lifeStoryLinesColor) {
		mLifeStoryLinesColor = lifeStoryLinesColor;
	}

	public boolean showFamilyStoryLines() {
		return mFamilyStoryLines;
	}

	public void setShowFamilyStoryLines(boolean familyStoryLines) {
		mFamilyStoryLines = familyStoryLines;
	}

	public int getFamilyStoryLinesColor() {
		return mFamilyStoryLinesColor;
	}

	public int getFamilyStoryLinesSpinnerColor(){
		return getSpinnerColor(mFamilyStoryLinesColor);
	}

	public void setShowFamilyStoryLinesColor(int familyStoryLinesColor) {
		mFamilyStoryLinesColor = familyStoryLinesColor;
	}

	public boolean showSpouseLines() {
		return mSpouseLines;
	}

	public void setShowSpouseLines(boolean spouseLines) {
		mSpouseLines = spouseLines;
	}

	public int getSpouseLinesColor() {
		return mSpouseLinesColor;
	}

	public int getSpouseLinesSpinnerColor(){
		return getSpinnerColor(mSpouseLinesColor);
	}

	public void setShowSpouseLinesColor(int spouseLinesColor) {
		mSpouseLinesColor = spouseLinesColor;
	}

	public int getMapType() {
		return mMapType;
	}

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

package com.sprogcoder.example.database;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;

import com.sprogcoder.grogdb.GrogTable;

public class TableCountry extends GrogTable
{

	private static final String TABLE_NAME = "Countries";
	private static Map<String, String> columns = new HashMap<String, String>();

	static
	{
		columns.put("COUNTRY_NAME", "TEXT");
		columns.put("COUNTRY_CAPITAL", "TEXT");
	}

	public TableCountry()
	{
		super(TABLE_NAME, columns);
	}

	public void addCountryCapital(String countryName, String countryCapital)
	{
		ContentValues cv = new ContentValues();
		cv.put("COUNTRY_NAME", countryName);
		cv.put("COUNTRY_CAPITAL", countryCapital);
		insertRow(TABLE_NAME, cv);
	}

	public String getCountryCapital(String countryName)
	{
		String args = "COUNTRY_NAME='" + countryName + "'";
		return getString(TABLE_NAME, args, "COUNTRY_CAPITAL");
	}
	
}
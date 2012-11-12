package com.sprogcoder.example.database;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;

import com.sprogcoder.grogdb.GrogTable;

public class TableOption extends GrogTable
{

	private static final String TABLE_NAME = "Options";
	private static Map<String, String> columns = new HashMap<String, String>();

	static
	{
		columns.put("OPTION_ID", "TEXT");
		columns.put("INTEGER_VALUE", "INTEGER");
	}

	public TableOption()
	{
		super(TABLE_NAME, columns);
	}

	public void setBooleanOption(String optionID, boolean onoffBoolean)
	{
		int onoff = (onoffBoolean) ? 1 : 0;
		ContentValues cv = new ContentValues();
		cv.put("OPTION_ID", optionID);
		cv.put("INTEGER_VALUE", onoff);
		updateOrInsertRow(TABLE_NAME, cv, "OPTION_ID", optionID);
	}

	public boolean getBooleanOption(String optionID)
	{
		String args = "OPTION_ID='" + optionID + "'";
		return getBoolean(TABLE_NAME, args, "INTEGER_VALUE");
	}
	
}
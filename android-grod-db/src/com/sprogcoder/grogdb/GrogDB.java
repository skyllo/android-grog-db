package com.sprogcoder.grogdb;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GrogDB
{

	private List<GrogTable> tables;
	private String databaseName;
	private String databaseTag;
	private int databaseVersion;
	private DatabaseHelper mDbHelper;
	private GrogDBListener listener;

	public GrogDB(String databaseName, String databaseTag, int databaseVersion, GrogDBListener listener)
	{
		this.listener = listener;
		this.tables = new ArrayList<GrogTable>();
		this.databaseName = databaseName;
		this.databaseTag = databaseTag;
		this.databaseVersion = databaseVersion;
	}

	public GrogDB addTable(GrogTable table)
	{
		tables.add(table);
		return this;
	}

	public GrogDB create(Context context)
	{
		if (mDbHelper == null)
		{
			synchronized (GrogDB.class)
			{
				if (mDbHelper == null)
				{
					mDbHelper = new DatabaseHelper(context);
					setTablesDatabaseHelper();
				}
			}
		}
		return this;
	}

	private void setTablesDatabaseHelper()
	{
		for (GrogTable table : tables)
		{
			table.setDatabaseHelper(mDbHelper);
		}
	}

	/**
	 * Internal Database
	 */
	private class DatabaseHelper extends SQLiteOpenHelper
	{

		DatabaseHelper(Context context)
		{
			super(context, databaseName, null, databaseVersion);
			Log.v(databaseTag, "DatabaseHelper() - Database " + databaseName + " Ready - Version: " + databaseVersion);
		}

		@Override
		public void onCreate(SQLiteDatabase db)
		{
			createTables(db);
			Log.v(databaseTag, "onCreate() - Created New Database - DatabaseName: " + databaseName);
		}

		private void createTables(SQLiteDatabase db)
		{
			for (GrogTable table : tables)
			{
				db.execSQL(table.getSqlCreate());
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			listener.upgradeDatabase(db, oldVersion, newVersion);
			Log.v(databaseTag, "onUpgrade() - Upgrading Database - DatabaseName: " + databaseName);
		}

	}

}

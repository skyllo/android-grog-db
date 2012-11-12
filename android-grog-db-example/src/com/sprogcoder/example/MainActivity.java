package com.sprogcoder.example;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.sprogcoder.example.database.TableCountry;
import com.sprogcoder.example.database.TableOption;
import com.sprogcoder.grogdb.GrogDB;
import com.sprogcoder.grogdb.GrogDBListener;

public class MainActivity extends Activity
{

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		testDatabase("DB1");
		testDatabase("DB2");
	}

	private void testDatabase(final String databaseName)
	{
		final TableOption tableOption = new TableOption();
		final TableCountry tableCountry = new TableCountry();
		
		GrogDB database = new GrogDB(databaseName, databaseName, 1, new GrogDBListener()
		{
			public void upgradeDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
			{
				db.execSQL(tableOption.getSqlDrop());
				db.execSQL(tableCountry.getSqlDrop());
				db.execSQL(tableOption.getSqlCreate());
				db.execSQL(tableCountry.getSqlCreate());
			}
		});
		database.addTable(tableOption);
		database.addTable(tableCountry);
		database.create(getBaseContext());

		tableOption.setBooleanOption("myoption", true);
		Log.v("MainActivity", Boolean.toString(tableOption.getBooleanOption("myoption")));
		tableOption.setBooleanOption("myoption", false);
		Log.v("MainActivity", Boolean.toString(tableOption.getBooleanOption("myoption")));

		Thread thread1 = new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					tableCountry.addCountryCapital("England", "London");
					tableCountry.addCountryCapital("France", "London");
					Log.v(databaseName, tableCountry.getCountryCapital("England"));
				}
			}
		});

		Thread thread2 = new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					tableCountry.addCountryCapital("France", "Paris");
					tableCountry.addCountryCapital("Spain", "Paris");
					Log.v(databaseName, tableCountry.getCountryCapital("France"));
				}
			}
		});

		Thread thread3 = new Thread(new Runnable()
		{
			public void run()
			{
				while (true)
				{
					tableCountry.addCountryCapital("Spain", "Madrid");
					tableCountry.addCountryCapital("England", "Madrid");
					Log.v(databaseName, tableCountry.getCountryCapital("Spain"));
				}
			}
		});

		thread1.start();
		thread2.start();
		thread3.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
}

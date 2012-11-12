GrogDB - Android Database Wrapper
===============
A Simple Wrapper around the Android SQLite Database API.

Features
-----
* Modular design with classes for each database table
* Easy interfaces to add tables, create and upgrade a database

Usage
-----
Creating a Table
```java
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
	
	/**
	* Table Getter/Setter Methods
	*/
	public void setBooleanOption(String optionID, boolean bool)
	{
		int onoff = (bool) ? 1 : 0;
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
```

Creating the Database
```java
final TableOption tableOption = new TableOption();
int databaseVersion = 1;

GrogDB database = new GrogDB("MyDatabase", "TAG", databaseVersion, new GrogDBListener()
{
	public void upgradeDatabase(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL(tableOption.getSqlDrop());
		db.execSQL(tableOption.getSqlCreate());
	}
});

database.addTable(tableOption);
database.create(getBaseContext());
```

Querying the Table
```java
tableOption.setBooleanOption("myoption", true);
Log.v("MainActivity", Boolean.toString(tableOption.getBooleanOption("myoption")));
tableOption.setBooleanOption("myoption", false);
Log.v("MainActivity", Boolean.toString(tableOption.getBooleanOption("myoption")));
```

License
-------
The code in this project is licensed under the Apache
Software License 2.0, per the terms of the included LICENSE
file.

References
------------
* Android Database JavaDoc - http://developer.android.com/reference/android/database/package-summary.html
* Android Storage Options - http://developer.android.com/guide/topics/data/data-storage.html#db
* SQLite Home Page - http://www.sqlite.org/
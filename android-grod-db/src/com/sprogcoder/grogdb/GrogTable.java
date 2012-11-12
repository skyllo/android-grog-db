/***
  Copyright (c) 2012 sprogcoder <sprogcoder@gmail.com>
  
  Licensed under the Apache License, Version 2.0 (the "License"); you may
  not use this file except in compliance with the License. You may obtain
  a copy of the License at
  
    http://www.apache.org/licenses/LICENSE-2.0
    
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package com.sprogcoder.grogdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class GrogTable
{

	protected SQLiteOpenHelper mDbHelper;
	protected Object lock = new Object();
	private String sqlCreate;
	private String sqlDrop;

	public GrogTable(String tableName, Map<String, String> columns)
	{
		this.sqlCreate = "CREATE TABLE " + tableName + " (" + GrogUtils.getColumnSql(columns) + ");";
		this.sqlDrop = "DROP TABLE IF EXISTS " + tableName + ";";
	}

	public String getSqlCreate()
	{
		return sqlCreate;
	}

	public String getSqlDrop()
	{
		return sqlDrop;
	}

	/**
	 * Private Methods
	 */
	void setDatabaseHelper(SQLiteOpenHelper mDbHelper)
	{
		this.mDbHelper = mDbHelper;
	}

	private Cursor getCursor(String tableName, String args)
	{
		String SQL_STATEMENT = "";
		if (args != null)
		{
			SQL_STATEMENT = "SELECT * FROM " + tableName + " WHERE " + args;
		}
		else
		{
			SQL_STATEMENT = "SELECT * FROM " + tableName;
		}
		Cursor c = mDbHelper.getReadableDatabase().rawQuery(SQL_STATEMENT, null);
		return c;
	}

	protected boolean getIdExists(String tableName, String columnName, String id)
	{
		synchronized (lock)
		{
			Cursor cursor = mDbHelper.getReadableDatabase().rawQuery("SELECT 1 FROM " + tableName + " WHERE " + columnName + "=?",
					new String[] { id });
			boolean exists = (cursor.getCount() > 0);
			cursor.close();
			mDbHelper.getReadableDatabase().close();
			return exists;
		}
	}

	protected boolean getIdExists(String tableName, String columnName, String id, String columnNameTwo, String idTwo)
	{
		synchronized (lock)
		{
			Cursor cursor = mDbHelper.getReadableDatabase().rawQuery(
					"SELECT 1 FROM " + tableName + " WHERE " + columnName + "='" + id + "' AND " + columnNameTwo + "='" + idTwo + "'", null);
			boolean exists = (cursor.getCount() > 0);
			cursor.close();
			mDbHelper.getReadableDatabase().close();
			return exists;
		}
	}

	protected boolean getBoolean(String tableName, String args, String columnValueIndex)
	{
		return getInt(tableName, args, columnValueIndex) > 0;
	}

	protected int getInt(String tableName, String args, String columnIndex)
	{
		synchronized (lock)
		{
			int i = -1;
			Cursor c = getCursor(tableName, args);
			if (c.moveToFirst())
			{
				i = c.getInt(c.getColumnIndex(columnIndex));
			}
			c.close();
			mDbHelper.getReadableDatabase().close();
			return i;
		}
	}

	protected String getString(String tableName, String args, String columnIndex)
	{
		synchronized (lock)
		{
			String text = "";
			Cursor c = getCursor(tableName, args);
			if (c.moveToFirst())
			{
				text = c.getString(c.getColumnIndex(columnIndex));
			}
			c.close();
			mDbHelper.getReadableDatabase().close();
			return text;
		}
	}

	protected List<String> getStrings(String tableName, String args, String columnIndex)
	{
		synchronized (lock)
		{
			List<String> stringList = new ArrayList<String>();
			Cursor c = getCursor(tableName, args);
			if (c.moveToFirst())
			{
				do
				{
					String channelValue = c.getString(c.getColumnIndex(columnIndex));
					stringList.add(channelValue);
				}
				while (c.moveToNext());
			}
			c.close();
			mDbHelper.getReadableDatabase().close();
			return stringList;
		}
	}

	protected String updateOrInsertRow(String tableName, ContentValues cv, String columnIndex, String columnValue)
	{
		synchronized (lock)
		{
			String result = "";
			int rowsUpdated = mDbHelper.getWritableDatabase().update(tableName, cv, columnIndex + "='" + columnValue + "'", null);
			if (rowsUpdated > 1)
			{
				throw new SQLException("Updated " + rowsUpdated + " rows but only meant to update 1");
			}
			if (rowsUpdated == 0)
			{
				result = Long.toString(mDbHelper.getWritableDatabase().insertOrThrow(tableName, null, cv));
			}
			mDbHelper.close();
			return result;
		}
	}

	protected String insertRow(String tableName, ContentValues cv)
	{
		synchronized (lock)
		{
			long result = mDbHelper.getWritableDatabase().insertOrThrow(tableName, null, cv);
			mDbHelper.getWritableDatabase().close();
			if (result == -1)
			{
				// String stringResult = (result == -1) ? "Row Already Exists" : "Row Added";
			}
			return Long.toString(result);
		}
	}

	protected void deleteRow(String tableName, String args)
	{
		synchronized (lock)
		{
			int deleteResult = mDbHelper.getWritableDatabase().delete(tableName, args, null);
			if (deleteResult == 0)
			{
			}
			else
			{
			}
			mDbHelper.getWritableDatabase().close();
		}
	}

	public boolean isDatabaseEmpty(String databaseName)
	{
		synchronized (lock)
		{
			try
			{
				Cursor cur = getCursor(databaseName, null);
				boolean empty = (cur.getCount() == 0);
				cur.close();
				return empty;
			}
			catch (Exception e)
			{
				// do nothing
			}
			return true;
		}
	}

}

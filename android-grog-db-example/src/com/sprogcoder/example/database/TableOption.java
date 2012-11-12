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
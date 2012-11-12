package com.sprogcoder.grogdb;

import android.database.sqlite.SQLiteDatabase;

public interface GrogDBListener
{
	
	void upgradeDatabase(SQLiteDatabase db, int oldVersion, int newVersion);

}

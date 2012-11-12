package com.sprogcoder.grogdb;

import java.util.Map;

class GrogUtils
{
	
	static String getColumnSql(Map<String, String> columns)
	{
		StringBuilder sb = new StringBuilder();
		int i = 0;

		for (Map.Entry<String, String> entry : columns.entrySet())
		{
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key + " " + value);
			i++;
			if (i != columns.size())
			{
				sb.append(", ");
			}
		}

		return sb.toString();
	}


}

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

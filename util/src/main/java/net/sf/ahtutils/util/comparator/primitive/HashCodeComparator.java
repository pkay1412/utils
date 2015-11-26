package net.sf.ahtutils.util.comparator.primitive;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by roblick on 24.11.2015.
 */
public class HashCodeComparator
{
	private Map<String,String> hash;
	private File startDir;

	public HashCodeComparator(File startDir)
	{
		this.startDir = startDir;
		this.hash = new HashMap<String, String>();
	}

	void searchForHashCodeBuilder(File start)
	{
		for(File f :start.listFiles())
		{
			if(f.isDirectory())
			{
				searchForHashCodeBuilder(f);
			}
			else
			{
				String hcb = "HashCodeBuilder";
				try
				{
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
					String input = br.readLine();
					while(input != null)
					{
						if(input.contains(hcb))
						{
							//do sth!!
						}
					}
				} catch(FileNotFoundException e)
				{
					e.printStackTrace();
				} catch(IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

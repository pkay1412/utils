package net.sf.ahtutils.db.sql;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.openfuxml.content.table.Table;
import org.openfuxml.factory.xml.table.OfxTableFactory;
import org.openfuxml.renderer.text.OfxTextRenderer;

public class SqlTableCounter
{
	private Map<String, String> dbmap1 = new LinkedHashMap<String, String>();
	private Map<String, String> dbmap2 = new LinkedHashMap<String, String>();
	
	private File f1;
	private File f2;

	public SqlTableCounter(String path, String filename1, String filename2)
	{
		f1 = new File(path, filename1);
		f2 = new File(path, filename2);
	}
	
	public void readData(File f, Map<String,String> map) throws IOException
	{
		LineIterator it = FileUtils.lineIterator(f, "UTF-8");
		try
		{
		   while (it.hasNext())
		   {
		     String line = it.nextLine();
		     String[] s = line.split("\\|");
             map.put(s[0], s[1]);
		   }
		}
		finally {it.close();}
	}

	public List<Object[]> data() throws IOException
	{
		readData(f1,dbmap1);
		readData(f2,dbmap2);
		
		List<Object[]> result = new ArrayList<Object[]>();
		Map<String, String> done = new HashMap<String, String>();
		
		for(Map.Entry<String, String> me : dbmap1.entrySet())
		{
			if(!dbmap2.containsKey(me.getKey()))
            {
                Object[] tmp = {me.getKey(), "X", "", me.getValue(), "", ""};
                result.add(tmp);
            }
			for (Map.Entry<String, String> me2 : dbmap2.entrySet())
			{
                    if(me.getKey().equals(me2.getKey())) {
                        if(me.getValue().equals(me2.getValue())) {
                            Object[] tmp = {me.getKey(), "X", "X", me.getValue(), me2.getValue(),"X"};
                            result.add(tmp);
                        }
                        else {
                            Object[] tmp = {me.getKey(), "X", "X", me.getValue(), me2.getValue(), ""};
                            result.add(tmp);
                        }
                    }
                    else if (!dbmap1.containsKey(me2.getKey()) && !done.containsKey(me2.getKey()) ) {
                        Object[] tmp = {me2.getKey(), "", "X", "", me2.getValue(), ""};
                        result.add(tmp);
                        done.put(me2.getKey(), me2.getValue()) ;
                    }
                }
            }
            return result;
        }

	private List<String> header()
	{
		List<String> header =  new ArrayList<String>();
		header.add("table");
		header.add("db1");
		header.add("db2");
		header.add("c1");
		header.add("c2");
		header.add("equal");
		return header;
	}

	public void output() throws Exception
	{
		Table t = OfxTableFactory.build(header(), data());
		OfxTextRenderer.table(t, System.out);
	}
}

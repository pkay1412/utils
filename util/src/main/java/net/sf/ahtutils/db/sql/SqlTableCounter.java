package net.sf.ahtutils.db.sql;

import org.openfuxml.content.table.Table;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.table.OfxTableFactory;
import org.openfuxml.renderer.text.OfxTextRenderer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class SqlTableCounter {

        private Map<String, String> dbmap1 = new LinkedHashMap<String, String>();
        private Map<String, String> dbmap2 = new LinkedHashMap<String, String>();
        private Path filepath1;
        private Path filepath2;

        public SqlTableCounter(String stringpath1, String filename1, String filename2) {
            filepath1 = FileSystems.getDefault().getPath(stringpath1, filename1);
            filepath2 = FileSystems.getDefault().getPath(stringpath1, filename2);
        }

        void readData(){
            try {
                BufferedReader br1 = Files.newBufferedReader(filepath1, Charset.forName("UTF-8"));
                BufferedReader br2 = Files.newBufferedReader(filepath2, Charset.forName("UTF-8"));
                String input1;
                String input2 = null;
                while((input1 = br1.readLine()) != null || (input2 = br2.readLine()) != null){
                    if(input1 != null) {
                        String[] line = input1.split("\\|");
                        dbmap1.put(line[0], line[1]);
                    }
                    if(input2 != null){
                        String[] line = input2.split("\\|");
                        dbmap2.put(line[0], line[1]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public List<Object[]> data() {
            readData();
            List<Object[]> result = new ArrayList<Object[]>();
            Map<String, String> done = new HashMap<String, String>();
            for(Map.Entry<String, String> me : dbmap1.entrySet()){
                if(!dbmap2.containsKey(me.getKey())) {
                    Object[] tmp = {me.getKey(), "X", "", me.getValue(), "", ""};
                    result.add(tmp);
                }
                for (Map.Entry<String, String> me2 : dbmap2.entrySet()) {
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

        List<String> header(){
            List<String> header =  new ArrayList<String>();
            header.add("table");
            header.add("db1");
            header.add("db2");
            header.add("c1");
            header.add("c2");
            header.add("equal");
            return header;
        }

        public void output(){
            Table t = OfxTableFactory.build(header(), data());
            try {
                OfxTextRenderer.table(t, System.out);
            } catch (OfxAuthoringException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}

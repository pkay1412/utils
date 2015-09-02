package net.sf.ahtutils.db;

import net.sf.ahtutils.db.sql.SqlTableCounter;


public class SqlTableCounterTest {
    public static void main(String[] args) {

        //relative Pfadangabe?
        SqlTableCounter sqltc = new SqlTableCounter("C:\\Users\\roblick\\WorkspaceIntelliJ\\utils\\util\\src\\test\\resources\\db","db1.txt", "db2.txt");
        sqltc.output();
    }
}

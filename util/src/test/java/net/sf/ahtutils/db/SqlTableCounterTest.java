package net.sf.ahtutils.db;

import net.sf.ahtutils.db.sql.SqlTableCounter;

public class SqlTableCounterTest
{
    public static void main(String[] args) throws Exception
    {

        SqlTableCounter sqltc = new SqlTableCounter("util/src/test/resources/db","db1.txt", "db2.txt");
        sqltc.output();
    }
}

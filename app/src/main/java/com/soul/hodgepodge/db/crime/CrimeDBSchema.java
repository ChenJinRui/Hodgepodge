package com.soul.hodgepodge.db.crime;

public
/**
 * Created by Chjr on 2020/8/26
 * 数据库表名 字段名
 */
class CrimeDBSchema {
    public static final class CrimeTable{
        public static final String NAME = "crimes";
        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }

}

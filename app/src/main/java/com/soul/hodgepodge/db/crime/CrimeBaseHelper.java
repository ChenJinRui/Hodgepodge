package com.soul.hodgepodge.db.crime;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.soul.hodgepodge.db.crime.CrimeDBSchema.CrimeTable;

import androidx.annotation.Nullable;

public
/**
 * Created by Chjr on 2020/8/26
 * 调用getWritableDatabase()方法CrimeBaseHelper做一下工作
 * ⒈打开data/data/package/databases/crimeBase.db（如果不存在就创建）
 * ⒉如果是首次创建，调用OnCreate方法，保存版本号
 * ⒊如果已有数据库，首先检查版本号，如果CrimeBaseHelper版本更高调用onUpgrade升级
 */
class CrimeBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public CrimeBaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, VERSION);
        Log.e("test" ,"CrimeBaseHelper");
    }

    /**
     * 数据的初始化工作
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("test" ,"onCreate");
        sqLiteDatabase.execSQL("create table " + CrimeTable.NAME + "(" +
                " _d integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + "," +
                CrimeTable.Cols.TITLE + "," +
                CrimeTable.Cols.DATE + "," +
                CrimeTable.Cols.SOLVED + ")"
        );
    }

    /**
     * 数据的升级
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

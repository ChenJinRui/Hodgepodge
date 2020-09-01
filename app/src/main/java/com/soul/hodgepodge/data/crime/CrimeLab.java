package com.soul.hodgepodge.data.crime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.soul.hodgepodge.bean.crime.CrimeBean;
import com.soul.hodgepodge.db.crime.CrimeBaseHelper;
import com.soul.hodgepodge.db.crime.CrimeCursorWrapper;
import com.soul.hodgepodge.db.crime.CrimeDBSchema;
import com.soul.hodgepodge.db.crime.CrimeDBSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public

/**
 * Created by Chjr on 2020/8/13
 *
 */
class CrimeLab {

    private static CrimeLab sCrimeLab;

//    private List<CrimeBean> mCrimeBeans;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab getInstance(Context context){
        if(null == sCrimeLab){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public void addCrime(CrimeBean c){
//        mCrimeBeans.add(c);
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME,null,values);

    }
    //

    /**
     * 该方法不会创建任何文件，只是指向，某个具体位置的File对象
     * @param crimeBean getPhotoFilename
     * @return File对象
     */
    public File getPhotoFile(CrimeBean crimeBean){
        File file = mContext.getFilesDir();
        return new File(file,crimeBean.getPhotoFilename());
    }
    public void updateCrime(CrimeBean c){
        String uuidString = c.getID().toString();
        ContentValues values = getContentValues(c);
        //不直接写Sql语句是应为String 可能是uuid 可能包含sql 语句 所以用= ？防止sql注入
        Log.e("test","updateCrime");
        mDatabase.update(CrimeTable.NAME,values,
                CrimeTable.Cols.UUID + " = ? ",
                new String[]{uuidString});
    }

//    public Cursor queryCrimes(String whereClause,String[] whereArgs){
    public CrimeCursorWrapper queryCrimes(String whereClause,String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,//数据库表明
                null,//要获取的字段 null 查询所有字段
                whereClause,//where 条件字段
                whereArgs,// 条件
                null,//groupBy
                null,//having
                null//orderBy
        );
        return new CrimeCursorWrapper(cursor);
    }

    private CrimeLab (Context context){
        mContext = context;
        /**
         * 调用getWritableDatabase()方法CrimeBaseHelper做一下工作
         * ⒈打开data/data/package/databases/crimeBase.db（如果不存在就创建）
         * ⒉如果是首次创建，调用OnCreate方法，保存版本号
         * ⒊如果已有数据库，首先检查版本号，如果CrimeBaseHelper版本更高调用onUpgrade升级
         */

        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimeBeans = new ArrayList<>();
    }

    private static ContentValues getContentValues(CrimeBean crimeBean){
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.TITLE,crimeBean.getTitle());
        values.put(CrimeTable.Cols.DATE,crimeBean.getDate().getTime());
        values.put(CrimeTable.Cols.UUID,crimeBean.getID().toString());
        values.put(CrimeTable.Cols.SOLVED,crimeBean.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT,crimeBean.getSuspect());

        return values;
    }

    public List<CrimeBean> getCrimeBeans(){
//        return mCrimeBeans;

        List<CrimeBean> crimeBeans = new ArrayList<>();
        CrimeCursorWrapper cursorWrapper = queryCrimes(null,null);
        try{
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                crimeBeans.add(cursorWrapper.getCrime());
                cursorWrapper.moveToNext();
            }
        }finally {
            cursorWrapper.close();
        }

        return crimeBeans;
    }

    public CrimeBean getCrimeBean(UUID uuid){
//        for (CrimeBean crimeBean : mCrimeBeans) {
//            if(uuid.equals(crimeBean.getID())){
//                return crimeBean;
//            }
//        }
        CrimeCursorWrapper cu = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuid.toString()}
        );
        try{
            if(cu.getCount() == 0){
                return null;
            }
            cu.moveToFirst();
            return cu.getCrime();
        }finally {
            cu.close();
        }
    }
    public void deleteCrimeBean(UUID uuid) {
//        for (int i = 0; i < mCrimeBeans.size(); i++) {
//            if(uuid.equals(mCrimeBeans.get(i).getID())){
//                mCrimeBeans.remove(i);
//            }
//        }

        String uuidString = uuid.toString();
        //不直接写Sql语句是应为String 可能是uuid 可能包含sql 语句 所以用= ？防止sql注入
        Log.e("test", "updateCrime");
        mDatabase.delete(CrimeTable.NAME,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

}

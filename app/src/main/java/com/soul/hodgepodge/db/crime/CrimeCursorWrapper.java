package com.soul.hodgepodge.db.crime;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.soul.hodgepodge.bean.crime.CrimeBean;
import com.soul.hodgepodge.db.crime.CrimeDBSchema.CrimeTable;

import java.util.Date;
import java.util.UUID;

public
/**
 * Created by Chjr on 2020/8/26
 *
 */
class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public CrimeBean getCrime(){
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.Cols.SUSPECT));

        CrimeBean crimeBean = new CrimeBean(UUID.fromString(uuidString));
        crimeBean.setTitle(title);
        crimeBean.setDate(new Date(date));
        crimeBean.setSolved(isSolved != 0);
        crimeBean.setSuspect(suspect);

        return crimeBean;
    }
}

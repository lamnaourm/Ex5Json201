package com.example.ex5json201;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBEtudiants extends SQLiteOpenHelper {

    public static String DBNAME ="etudiants.db";
    public static String TBNAME ="Etudiant";
    public static String COL1 ="ident";
    public static String COL2 ="nom";
    public static String COL3 ="ville";
    public static String COL4 ="naissance";
    public static String COL5 ="genre";
    public static String COL6 ="notefr";
    public static String COL7 ="notehist";
    public static String COL8 ="notemath";
    public static String COL9 ="notephys";

    public MyDBEtudiants(Context c){
        super(c,DBNAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = String.format("create table %s (%s text primary key, %s text, %s text, %s text,%s text, %s double, %s double,%s double,%s double )",TBNAME,COL1,COL2,COL3,COL4,COL5,COL6,COL7,COL8,COL9);
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "drop table " + TBNAME;
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public static long insert_etudiants(SQLiteDatabase sqLiteDatabase, Etudiant e){
        ContentValues ct = new ContentValues();
        ct.put(COL1,e.getIdent());
        ct.put(COL2,e.getNom());
        ct.put(COL3,e.getVille());
        ct.put(COL4,e.getNaissance());
        ct.put(COL5,e.getGenre());
        ct.put(COL6,e.getNoteFr());
        ct.put(COL7,e.getNoteHist());
        ct.put(COL8,e.getNoteMath());
        ct.put(COL9,e.getNotePhys());
        return  sqLiteDatabase.insert(TBNAME,null,ct);
    }
}

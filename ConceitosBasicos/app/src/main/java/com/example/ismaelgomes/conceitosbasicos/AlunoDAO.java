package com.example.ismaelgomes.conceitosbasicos;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlunoDAO extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "CB.db";
    private static final int DATABASE_VERSION = 1;
    private final String CREATE_TABLE = "CREATE TABLE Alunos (ID INTEGER PRIMARY KEY AUTOINCREMENT, Nome TEXT, Telefone TEXT, Endereco TEXT, Foto BLOB, Site TEXT, Nota REAL);";

    public AlunoDAO(Context context){
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}

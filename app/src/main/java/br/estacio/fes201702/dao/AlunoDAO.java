package br.estacio.fes201702.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.estacio.fes201702.domain.Aluno;

/**
 * Created by carlos on 12/09/17.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "aluno.db";
    private static final int VERSION = 1;
    private static final String TABLE = "aluno";

    public AlunoDAO(Context context) {
        super(context, DATABASE, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddlAluno = "create table aluno (" +
                "id integer primary key autoincrement, " +
                "nome text not null, " +
                "fone text not null, " +
                "email text not null)";
        db.execSQL(ddlAluno);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private ContentValues getContentValues(Aluno aluno) {
        ContentValues values = new ContentValues();
        values.put("id", aluno.getId());
        values.put("nome", aluno.getNome());
        values.put("fone", aluno.getFone());
        values.put("email",  aluno.getEmail());
        return values;
    }

    public void insert(Aluno aluno) {
        ContentValues values = getContentValues(aluno);

        getWritableDatabase().insert(TABLE, null, values);
    }

    public void update(Aluno aluno) {
        ContentValues values = getContentValues(aluno);

        String[] args = { String.valueOf(aluno.getId())};

        getWritableDatabase().update(TABLE, values, "id=?", args);
    }

    public void delete (Integer id) {
        String[] args = { id.toString() };
        getWritableDatabase().delete (TABLE, "id=?", args);
    }

    public Aluno findById(Integer id) {
        String[] args = { String.valueOf(id) };
        Cursor c = getReadableDatabase()
                .rawQuery("SELECT * FROM " + TABLE +  " where id = ?", args);
        Aluno aluno = null;
        if (c.moveToNext()) {
            aluno = obterDadosAluno(c);
        }
        c.close();
        return aluno;
    }

    private Aluno obterDadosAluno(Cursor c) {
        Aluno aluno;
        aluno = new Aluno();
        aluno.setId(c.getInt(c.getColumnIndex("id")));
        aluno.setNome(c.getString(c.getColumnIndex("nome")));
        aluno.setFone(c.getString(c.getColumnIndex("fone")));
        aluno.setEmail(c.getString(c.getColumnIndex("email")));
        return aluno;
    }

    public List<Aluno> list() {
        Cursor c = getReadableDatabase()
                .rawQuery("SELECT * FROM " + TABLE +  " order by nome", null);
        List<Aluno> list = new ArrayList<>();
        Aluno aluno = null;
        while (c.moveToNext()) {
            aluno = obterDadosAluno(c);
            list.add(aluno);
        }
        c.close();
        return list;
    }

}

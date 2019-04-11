package br.com.fernandohbrasil.voicetuner.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.fernandohbrasil.voicetuner.model.Pessoa;

public class PessoaDao {

    public static Pessoa getPessoa(int id) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();
            String selection = "id = ?";
            String[] selectionArgs = {String.valueOf(id)};
            Cursor cursor = db.query(MainDB.TABELA_PESSOA, null, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                String nome = cursor.getString(1);
                String email = cursor.getString(2);
                int senha = cursor.getInt(3);
                int papel = cursor.getInt(4);

                return new Pessoa(id, nome, email, senha, papel);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Pessoa fazLogin(String email, String senha) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();
            String selection = "email = ?  AND  senha  =  ?";
            String[] selectionArgs = {email, senha};

            Cursor cursor = db.query(MainDB.TABELA_PESSOA, null, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                int id = cursor.getInt(0);
                String nome = cursor.getString(1);
                int papel = cursor.getInt(4);

                return new Pessoa(id, nome, email, Integer.parseInt(senha), papel);
            } else {
                return null;
            }


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean emailExistente(String email) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();
            String selection = "email = ?";
            String[] selectionArgs = {email};

            Cursor cursor = db.query(MainDB.TABELA_PESSOA, null, selection, selectionArgs, null, null, null);

            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean autalizaPessoa(Pessoa oPessoa) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("nome", oPessoa.getNome());
            values.put("email", oPessoa.getEmail());
            values.put("senha", oPessoa.getSenha());
            values.put("papel", oPessoa.getPapel());

            String where = "id = '" + oPessoa.getId() + "'";

            return db.update(MainDB.TABELA_PESSOA, values, where, null) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean cadastraPessoa(Pessoa oPessoa) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("nome", oPessoa.getNome());
            values.put("email", oPessoa.getEmail());
            values.put("senha", oPessoa.getSenha());
            values.put("papel", oPessoa.getPapel());

            return db.insert(MainDB.TABELA_PESSOA, null, values) != -1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

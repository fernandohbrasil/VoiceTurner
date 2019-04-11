package br.com.fernandohbrasil.voicetuner.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.fernandohbrasil.voicetuner.model.Nota;

public class NotaDao {

    public static Nota getNota(int id) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();
            String selection = "id = ?";
            String[] selectionArgs = {String.valueOf(id)};
            Cursor cursor = db.query(MainDB.TABELA_NOTA, null, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {

                String descricao = cursor.getString(1);
                String sigla = cursor.getString(2);
                float frequencia = cursor.getFloat(3);
                int media = cursor.getInt(4);

                return new Nota(id, descricao, sigla, frequencia, media);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Nota> getNotas() {
        SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();

        String query = "SELECT * FROM " + MainDB.TABELA_NOTA + " where id <> 0";
        ArrayList<Nota> notas = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String descricao = cursor.getString(1);
                String sigla = cursor.getString(2);
                float frequencia = cursor.getFloat(3);
                int media = cursor.getInt(4);

                notas.add(new Nota(id, descricao, sigla, frequencia, media));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notas;
    }
}

package br.com.fernandohbrasil.voicetuner.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.fernandohbrasil.voicetuner.model.Exercicio;
import br.com.fernandohbrasil.voicetuner.model.Nota;
import br.com.fernandohbrasil.voicetuner.model.NotaExercicio;
import br.com.fernandohbrasil.voicetuner.model.Pessoa;

public class ExercicioDao {

    public boolean cadastraExercicio(Exercicio oExercicio, ArrayList<NotaExercicio> notasExercicio) {
        boolean sucesso = true;
        try {
            SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("id", oExercicio.getId());
            values.put("nivel", oExercicio.getNivel());
            values.put("professorId", oExercicio.getoProfessor().getId());
            values.put("tamanho", oExercicio.getTamanho());

            if (db.insert(MainDB.TABELA_EXERCICIO, null, values) != -1) {
                for (int i = 0; i < notasExercicio.size(); i++) {
                    if (!cadastraNotasExercicio(notasExercicio.get(i), oExercicio.getId())) {
                        deleteExercicio(oExercicio.getId());
                        i = notasExercicio.size() + 1;
                        sucesso = false;
                    }
                }
            }
            return sucesso;
        } catch (Exception e) {
            e.printStackTrace();
            return sucesso;
        }
    }

    private boolean cadastraNotasExercicio(NotaExercicio oNotasExercicio, int id) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("exercioId", id);
            values.put("sequencia", oNotasExercicio.getSequencia());
            values.put("notaId", oNotasExercicio.getaNota().getId());
            return db.insert(MainDB.TABELA_NOTA_EXERCICIO, null, values) != -1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteExercicio(int id) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();
            ContentValues values = new ContentValues();

            String selectionExercicio = "Id = ?";

            String selection = "exercioId = ?";
            String[] selectionArgs = {String.valueOf(id)};

            db.delete(MainDB.TABELA_NOTA_EXERCICIO, selection, selectionArgs);
            db.delete(MainDB.TABELA_EXERCICIO, selectionExercicio, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Exercicio> getExercicios() {
        SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();

        String query = "SELECT * FROM " + MainDB.TABELA_EXERCICIO;
        ArrayList<Exercicio> exercicios = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int nivel = cursor.getInt(1);
                Pessoa oProfessor = PessoaDao.getPessoa(cursor.getInt(2));
                int tamanho = cursor.getInt(3);

                exercicios.add(new Exercicio(id, nivel, oProfessor, tamanho, getNotasExercicio(id)));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return exercicios;
    }

    public static ArrayList<NotaExercicio> getNotasExercicio(int exercioId) {
        SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();
        ArrayList<NotaExercicio> notasexercicio = new ArrayList<>();

        String selection = "exercioId = ?";
        String[] selectionArgs = {String.valueOf(exercioId)};
        Cursor cursor = db.query(MainDB.TABELA_NOTA_EXERCICIO, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int sequencia = cursor.getInt(1);
                Nota aNota = NotaDao.getNota(cursor.getInt(2));

                notasexercicio.add(new NotaExercicio(sequencia, aNota));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return notasexercicio;
    }



    public int getExercicioId() {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();
            String query = "select max(Id) from exercicio";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            return cursor.getInt(0) + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



    public static Exercicio getExercicio(int id) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();
            String selection = "id = ?";
            String[] selectionArgs = {String.valueOf(id)};
            Cursor cursor = db.query(MainDB.TABELA_EXERCICIO, null, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                int nivel = cursor.getInt(1);
                Pessoa oProfessor = PessoaDao.getPessoa(cursor.getInt(2));
                int tamanho = cursor.getInt(3);
                ArrayList<NotaExercicio> notasexercicio = getNotasExercicio(id);

                return new Exercicio(id, nivel, oProfessor, tamanho, notasexercicio);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

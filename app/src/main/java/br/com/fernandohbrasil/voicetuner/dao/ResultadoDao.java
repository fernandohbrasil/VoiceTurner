package br.com.fernandohbrasil.voicetuner.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.fernandohbrasil.voicetuner.model.Exercicio;
import br.com.fernandohbrasil.voicetuner.model.ItemResultado;
import br.com.fernandohbrasil.voicetuner.model.Pessoa;
import br.com.fernandohbrasil.voicetuner.model.Resultado;

public class ResultadoDao {

    public int getResultadoId() {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();
            String query = "select max(Id) from resultado";
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            return cursor.getInt(0) + 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean cadastraResultado(Resultado oResultado) {
        boolean sucesso = true;
        try {
            SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("id", oResultado.getId());
            values.put("alunoId", oResultado.getoAluno().getId());
            values.put("exercioId", oResultado.getoExercicio().getId());

            if (db.insert(MainDB.TABELA_RESULTADO, null, values) != -1) {
                for (int i = 0; i < oResultado.getProgressao().size(); i++) {
                    if (!cadastraItensResultado(oResultado.getProgressao().get(i), oResultado.getId())) {
                        deleteResultado(oResultado.getId());
                        i = oResultado.getProgressao().size() + 1;
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

    private static boolean cadastraItensResultado(ItemResultado oItemResultado, int id) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("resultadoId", id);
            values.put("sequencia", oItemResultado.getSequencia());
            values.put("frequencia", oItemResultado.getFrequencia());
            values.put("notaId", oItemResultado.getoNota().getId());
            return db.insert(MainDB.TABELA_ITEM_RESULTADO, null, values) != -1;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void deleteResultado(int id) {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();

            String selectionResult = "Id = ?";

            String selection = "resultadoId = ?";
            String[] selectionArgs = {String.valueOf(id)};

            db.delete(MainDB.TABELA_ITEM_RESULTADO, selection, selectionArgs);
            db.delete(MainDB.TABELA_RESULTADO, selectionResult, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limparResultados() {
        try {
            SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();
            db.delete(MainDB.TABELA_ITEM_RESULTADO, null, null);
            db.delete(MainDB.TABELA_RESULTADO, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ItemResultado> getItemResultado(int resultadoId) {
        SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();
        ArrayList<ItemResultado> itensResultado = new ArrayList<>();

        String selection = "resultadoId = ?";
        String[] selectionArgs = {String.valueOf(resultadoId)};
        Cursor cursor = db.query(MainDB.TABELA_ITEM_RESULTADO, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int sequencia = cursor.getInt(1);
                float frequencia = cursor.getFloat(2);
                int notaId = cursor.getInt(3);

                itensResultado.add(new ItemResultado(sequencia, frequencia, NotaDao.getNota(notaId) ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return itensResultado;
    }

    public ArrayList<Resultado> getResultados() {
        SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();

        String query = "SELECT * FROM " + MainDB.TABELA_RESULTADO;
        ArrayList<Resultado> resultados = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                Pessoa oAluno = PessoaDao.getPessoa(cursor.getInt(1));
                Exercicio oExercicio = ExercicioDao.getExercicio(cursor.getInt(2));
                ArrayList<ItemResultado> progressao = getItemResultado(id);

                resultados.add(new Resultado(id, oAluno, oExercicio, progressao));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return resultados;
    }

    public ArrayList<Resultado> getResultados(int exercicioId) {
        SQLiteDatabase db = MainDB.getInstancia().getReadableDatabase();

        String selection = "exercioId = ?";
        String[] selectionArgs = {String.valueOf(exercicioId)};
        Cursor cursor = db.query(MainDB.TABELA_RESULTADO, null, selection, selectionArgs, null, null, null);

        ArrayList<Resultado> resultados = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                Pessoa oAluno = PessoaDao.getPessoa(cursor.getInt(1));
                Exercicio oExercicio = ExercicioDao.getExercicio(cursor.getInt(2));
                ArrayList<ItemResultado> progressao = getItemResultado(id);

                resultados.add(new Resultado(id, oAluno, oExercicio, progressao));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return resultados;
    }
}

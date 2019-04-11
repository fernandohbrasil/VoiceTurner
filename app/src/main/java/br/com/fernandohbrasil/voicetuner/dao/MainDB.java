package br.com.fernandohbrasil.voicetuner.dao;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.fernandohbrasil.voicetuner.config.MyApp;

public class MainDB extends SQLiteOpenHelper {

    private static String NOME_DB = "VoiceTurner.db";
    private static int VERSAO_DB = 1;
    public static String TABELA_PESSOA = "PESSOA";
    public static String TABELA_NOTA = "NOTA";
    public static String TABELA_EXERCICIO = "EXERCICIO";
    public static String TABELA_NOTA_EXERCICIO = "NOTA_EXERCICIO";
    public static String TABELA_RESULTADO = "RESULTADO";
    public static String TABELA_ITEM_RESULTADO = "ITEM_RESULTADO";

    private static MainDB instancia;

    public static MainDB getInstancia() {
        if (instancia == null) {
            instancia = new MainDB();
        }
        return instancia;
    }

    private MainDB() {
        super(MyApp.getContext(), MyApp.getDirFromSDCard() + "/databases/" + NOME_DB, null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        instancia = null;
        super.close();
    }


}

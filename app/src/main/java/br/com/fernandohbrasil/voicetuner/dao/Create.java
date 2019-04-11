package br.com.fernandohbrasil.voicetuner.dao;

import android.database.sqlite.SQLiteDatabase;

import br.com.fernandohbrasil.voicetuner.R;

public class Create {

    public void createTable() {

        SQLiteDatabase db = MainDB.getInstancia().getWritableDatabase();

        //db.execSQL("alter table ITEM_RESULTADO add notaId integer");

        //Pessoa
        String colunas = "(id integer primary key autoincrement, " +
                "nome varchar(50), " +
                "email varchar(30), " +
                "senha integer, " +
                "papel int(1))";

        String query = "CREATE TABLE IF NOT EXISTS " + MainDB.TABELA_PESSOA + colunas;
        db.execSQL(query);

        //Nota
        colunas = "(id integer primary key autoincrement, " +
                "descricao varchar(50), " +
                "sigla varchar(5), " +
                "frequencia numeric(6,2), " +
                "media integer)";

        query = "CREATE TABLE IF NOT EXISTS " + MainDB.TABELA_NOTA + colunas;
        db.execSQL(query);

        //exercicio
        colunas = "(id integer primary key," +
                "    nivel integer," +
                "    professorId integer," +
                "    tamanho integer)";
        query = "CREATE TABLE IF NOT EXISTS " + MainDB.TABELA_EXERCICIO + colunas;
        db.execSQL(query);

        //nota_exercicio
        colunas = "(exercioId integer," +
                "sequencia integer," +
                "notaId integer," +
                "primary key(exercioId, sequencia))";

        query = "CREATE TABLE IF NOT EXISTS " + MainDB.TABELA_NOTA_EXERCICIO + colunas;
        db.execSQL(query);

        //resultado
        colunas = "(id integer primary key," +
                "alunoId integer," +
                "exercioId integer)";

        query = "CREATE TABLE IF NOT EXISTS " + MainDB.TABELA_RESULTADO + colunas;
        db.execSQL(query);


        //item_resultado
        colunas = "(resultadoId integer," +
                "sequencia integer," +
                "frequencia numeric(6,2)," +
                "notaId integer," +
                "primary key (resultadoId, sequencia))";

        query = "CREATE TABLE IF NOT EXISTS " + MainDB.TABELA_ITEM_RESULTADO + colunas;
        db.execSQL(query);


        //db.execSQL("delete from " + MainDB.TABELA_ITEM_RESULTADO);
        //db.execSQL("Delete from " + MainDB.TABELA_RESULTADO);
        //db.execSQL("delete from  " + MainDB.TABELA_NOTA_EXERCICIO);
        //db.execSQL("delete from  " + MainDB.TABELA_EXERCICIO);
        //db.execSQL("delete from  " + MainDB.TABELA_NOTA);

        //insert notas


        /**
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (0, 'Not Available', 'NA', 0, 0)");


        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (24, 'Dó1', 'C1', 65.41," + R.raw.c1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (25, 'Dó#1', 'C#1', 69.30," + R.raw.cs1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (26, 'Ré1', 'D1', 73.42," + R.raw.d1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (27, 'Ré#1', 'D#1', 77.78," + R.raw.ds1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (28, 'Mi1', 'E1', 82.41," + R.raw.e1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (29, 'Fá1', 'F1', 87.31," + R.raw.f1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (30, 'Fá#1', 'F#1', 92.50," + R.raw.fs1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (31, 'Sol1', 'G1', 98.00," + R.raw.g1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (32, 'Sol#1', 'G#1', 103.83," + R.raw.gs1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (33, 'Lá1', 'A1', 110.00," + R.raw.a1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (34, 'Lá#1', 'A#1', 116.54," + R.raw.as1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (35, 'Si1', 'B1', 123.47," + R.raw.b1 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (36, 'Dó2', 'C2', 130.81," + R.raw.c2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (37, 'Dó#2', 'C#2', 138.59," + R.raw.cs2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (38, 'Ré2', 'D2', 146.83," + R.raw.d2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (39, 'Ré#2', 'D#2', 155.56," + R.raw.ds2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (40, 'Mi2', 'E2', 164.81," + R.raw.e2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (41, 'Fá2', 'F2', 174.61," + R.raw.f2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (42, 'Fá#2', 'F#2', 185.00," + R.raw.fs2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (43, 'Sol2', 'G2', 196.00," + R.raw.g2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (44, 'Sol#2', 'G#2', 207.65," + R.raw.gs2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (45, 'Lá2', 'A2', 220.00," + R.raw.a2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (46, 'Lá#2', 'A#2', 233.08," + R.raw.as2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (47, 'Si2', 'B2', 246.94," + R.raw.b2 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (48, 'Dó3', 'C3', 261.63," + R.raw.c3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (49, 'Dó#3', 'C#3', 277.18," + R.raw.cs3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (50, 'Ré3', 'D3', 293.66," + R.raw.d3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (51, 'Ré#3', 'D#3', 311.13," + R.raw.ds3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (52, 'Mi3', 'E3', 329.63," + R.raw.e3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (53, 'Fá3', 'F3', 349.23," + R.raw.f3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (54, 'Fá#3', 'F#3', 369.99," + R.raw.fs3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (55, 'Sol3', 'G3', 392.00," + R.raw.g3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (56, 'Sol#3', 'G#3', 415.30," + R.raw.gs3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (57, 'Lá3', 'A3', 440.00," + R.raw.a3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (58, 'Lá#3', 'A#3', 466.16," + R.raw.as3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (59, 'Si3', 'B3', 493.88," + R.raw.b3 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (60, 'Dó4', 'C4', 523.25," + R.raw.c4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (61, 'Dó#4', 'C#4', 554.37," + R.raw.cs4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (62, 'Ré4', 'D4', 587.33," + R.raw.d4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (63, 'Ré#4','D#4', 622.25," + R.raw.ds4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (64, 'Mi4', 'E4', 659.26," + R.raw.e4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (65, 'Fá4', 'F4', 698.46," + R.raw.f4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (66, 'Fá#4', 'F#4', 739.99," + R.raw.fs4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (67, 'Sol4', 'G4', 783.99," + R.raw.g4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (68, 'Sol#4', 'G#4', 830.61," + R.raw.gs4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (69, 'Lá4', 'A4', 880.00," + R.raw.a4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (70, 'Lá#4', 'A#4', 932.33," + R.raw.as4 + ")");
        db.execSQL("insert into nota (id, descricao, sigla, frequencia, media) values (71, 'Si4', 'B4', 987.77," + R.raw.b4 + ")");

**/
    }
}

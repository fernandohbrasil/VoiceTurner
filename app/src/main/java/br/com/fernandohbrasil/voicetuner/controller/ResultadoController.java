package br.com.fernandohbrasil.voicetuner.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.fernandohbrasil.voicetuner.R;
import br.com.fernandohbrasil.voicetuner.dao.ExercicioDao;
import br.com.fernandohbrasil.voicetuner.dao.NotaDao;
import br.com.fernandohbrasil.voicetuner.dao.ResultadoDao;
import br.com.fernandohbrasil.voicetuner.model.Afinacao;
import br.com.fernandohbrasil.voicetuner.model.ItemResultado;
import br.com.fernandohbrasil.voicetuner.model.Nota;
import br.com.fernandohbrasil.voicetuner.model.NotaExercicio;
import br.com.fernandohbrasil.voicetuner.model.Resultado;

public class ResultadoController extends Activity {


    private Resultado oResultadoSelecionado;
    private ListView listExercicioResultado, listItensResultado;

    private ArrayAdapter<Resultado> exercicioResultadoAdapter;
    private ArrayAdapter<String> itensResultadoAdapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultados);

        iniciaObjetos();
        iniciaEventos();
        carregaResultados();

    }

    private void iniciaObjetos() {
        listExercicioResultado = (ListView) findViewById(R.id.listExercicioResultado);
        listExercicioResultado.setLongClickable(true);

        listItensResultado = (ListView) findViewById(R.id.listItensResultado);
        listItensResultado.setLongClickable(true);
    }

    private void iniciaEventos() {
        listExercicioResultado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selecionaResultado((Resultado) listExercicioResultado.getItemAtPosition(i));
                return true;
            }
        });

        listItensResultado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return true;
            }
        });
    }

    private void carregaResultados() {
        ResultadoDao oResultadoDao = new ResultadoDao();

        ArrayList<Resultado> resultados = oResultadoDao.getResultados();

        if(resultados.isEmpty()){
            info();
        }

        exercicioResultadoAdapter = new ArrayAdapter<Resultado>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, resultados);
        listExercicioResultado.setAdapter(exercicioResultadoAdapter);
    }

    private void info(){
        ArrayList<String> resultados = new ArrayList<>();

        resultados.add("Nenhum resultado cadastrado !!!");

        itensResultadoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, resultados);
        listItensResultado.setAdapter(itensResultadoAdapter);
    }

    private void selecionaResultado(Resultado oResultado) {
        this.oResultadoSelecionado = oResultado;

        ArrayList<ItemResultado> itensResultado = new ResultadoDao().getItemResultado(oResultado.getId());
        ArrayList<NotaExercicio> notasExercicio = new ExercicioDao().getNotasExercicio(oResultado.getoExercicio().getId());

        ArrayList<String> resultados = new ArrayList<>();

        for (int i = 0; i < itensResultado.size(); i++) {
            Nota oNota = NotaDao.getNota(new Afinacao().getNotaId(itensResultado.get(i).getFrequencia()));

            resultados.add("Esperada:  " + notasExercicio.get(i).getaNota().toString() + "\n"
                    + "Frequência recebida:  " + itensResultado.get(i).getFrequencia() + "\n"
                    + oNota.toString());
        }

        itensResultadoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, resultados);
        listItensResultado.setAdapter(itensResultadoAdapter);
    }
}

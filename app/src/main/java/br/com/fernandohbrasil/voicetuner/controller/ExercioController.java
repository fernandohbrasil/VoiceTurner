package br.com.fernandohbrasil.voicetuner.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.fernandohbrasil.voicetuner.R;
import br.com.fernandohbrasil.voicetuner.config.MyApp;
import br.com.fernandohbrasil.voicetuner.dao.ExercicioDao;
import br.com.fernandohbrasil.voicetuner.dao.NotaDao;
import br.com.fernandohbrasil.voicetuner.model.Exercicio;
import br.com.fernandohbrasil.voicetuner.model.Nota;
import br.com.fernandohbrasil.voicetuner.model.NotaExercicio;

public class ExercioController extends Activity {

    private Button btnSalvar, btnSair;
    private RadioButton rbFacil, rbMedio;
    private ListView lvNotas, lvNotasExercicio;

    private ArrayAdapter<Nota> notasAdaptador;
    private ArrayAdapter<Nota> notasExercicioAdaptador;

    private ArrayList<Nota> notasSelecionadas;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercicio);

        iniciaObjetos();
        iniciaEventos();

    }

    private void iniciaEventos() {
        lvNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tocarNota((Nota) lvNotas.getItemAtPosition(i));
            }
        });

        lvNotas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                addNota((Nota) lvNotas.getItemAtPosition(i));
                return true;
            }
        });

        lvNotasExercicio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tocarNota((Nota) lvNotasExercicio.getItemAtPosition(i));
            }
        });

        lvNotasExercicio.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeNota(i);
                return true;
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }

    private void salvar() {
        int exercioId = new ExercicioDao().getExercicioId();
        Exercicio oExercicio = new Exercicio(exercioId, getNivel(), MyApp.getUsuarioLogado(), notasSelecionadas.size());

        ExercicioDao oExercicioDao = new ExercicioDao();
        if (oExercicioDao.cadastraExercicio(oExercicio, getNotasExercicio())) {
            iniciaTela();
            Toast.makeText(ExercioController.this, "Cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(ExercioController.this, "Erro ao cadastrar!", Toast.LENGTH_LONG).show();
        }
    }

    private void tocarNota(Nota oNota) {
        MyApp.tocarNota(oNota, ExercioController.this);

    }

    private ArrayList<NotaExercicio> getNotasExercicio() {
        ArrayList<NotaExercicio> notaExercicio = new ArrayList<>();
        for (int i = 0; i < notasSelecionadas.size(); i++) {
            notaExercicio.add(new NotaExercicio(i + 1, notasSelecionadas.get(i)));
        }
        return notaExercicio;
    }

    private void addNota(Nota aNota) {
        notasSelecionadas.add(aNota);
        carregaNotasExercicio();
    }

    private void removeNota(int i) {
        notasSelecionadas.remove(i);
        carregaNotasExercicio();
    }

    private void carregaNotasExercicio() {
        notasExercicioAdaptador = new ArrayAdapter<Nota>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, notasSelecionadas);
        lvNotasExercicio.setAdapter(notasExercicioAdaptador);
    }

    private void iniciaTela(){
        rbFacil.setChecked(true);
        notasSelecionadas.clear();
        carregaNotasExercicio();
    }

    private void iniciaObjetos() {
        lvNotas = (ListView) findViewById(R.id.listNotas);
        lvNotas.setLongClickable(true);
        lvNotasExercicio = (ListView) findViewById(R.id.listNotasExercicio);
        lvNotasExercicio.setLongClickable(true);

        rbFacil = (RadioButton) findViewById(R.id.rbFacil);
        rbMedio = (RadioButton) findViewById(R.id.rbMedio);

        btnSalvar = (Button) findViewById(R.id.btnGravarExercicio);
        btnSair = (Button) findViewById(R.id.btnSairCadExercicio);

        notasSelecionadas = new ArrayList<>();
        carregaNotas();
        iniciaTela();
    }

    private void carregaNotas() {
        NotaDao oNotaDao = new NotaDao();
        notasAdaptador = new ArrayAdapter<Nota>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, oNotaDao.getNotas());
        lvNotas.setAdapter(notasAdaptador);
    }

    private int getNivel() {
        if (rbFacil.isChecked()) {
            return 0;
        } else if (rbMedio.isChecked()) {
            return 1;
        } else {
            return 2;
        }
    }
}

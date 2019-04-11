package br.com.fernandohbrasil.voicetuner.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.fernandohbrasil.voicetuner.R;
import br.com.fernandohbrasil.voicetuner.dao.ExercicioDao;
import br.com.fernandohbrasil.voicetuner.dao.ResultadoDao;
import br.com.fernandohbrasil.voicetuner.model.Exercicio;
import br.com.fernandohbrasil.voicetuner.model.Resultado;

public class RemoverController extends Activity {

    private ListView listExercicioRemover, listResultadoRemover;
    private Button btnLimpaExercicio, btnLimpaResultado;

    private AlertDialog mensagemConfirmacao;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remover);

        iniciaObjetos();
        iniciaEventos();

        carregaExercicios();
        carregaResultados();
    }

    private void iniciaObjetos() {
        listExercicioRemover = (ListView) findViewById(R.id.listExercicioRemover);
        listExercicioRemover.setLongClickable(true);

        listResultadoRemover = (ListView) findViewById(R.id.listResultadoRemover);
        listResultadoRemover.setLongClickable(true);

        btnLimpaExercicio = (Button) findViewById(R.id.btnLimpaExercicio);
        btnLimpaResultado = (Button) findViewById(R.id.btnLimpaResultado);
    }

    private void iniciaEventos() {
        listExercicioRemover.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removerExercicio((Exercicio) listExercicioRemover.getItemAtPosition(i));
                return true;
            }
        });

        listResultadoRemover.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removerResultado((Resultado) listResultadoRemover.getItemAtPosition(i));
                return true;
            }
        });


        btnLimpaResultado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerTodosResultados();
            }
        });

        btnLimpaExercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removerTodosExercicios();
            }
        });
    }

    private void removerResultado(Resultado oResultado) {
        ResultadoDao oResultadoDao = new ResultadoDao();
        oResultadoDao.deleteResultado(oResultado.getId());

        carregaResultados();
    }

    private void removerExercicio(Exercicio oExercicio) {
        ExercicioDao oExercicioDao = new ExercicioDao();

        if (new ResultadoDao().getResultados(oExercicio.getId()).size() > 0) {
            Toast.makeText(RemoverController.this, "Para remover esse exercício, é necessário antes remover os resultados vinculados a ele ...", Toast.LENGTH_LONG).show();
        } else {
            oExercicioDao.deleteExercicio(oExercicio.getId());
            carregaExercicios();
        }
    }

    private void removerTodosExercicios() {
        ArrayList<Exercicio> exercicios = new ExercicioDao().getExercicios();
        boolean remeveutodos = true;

        for (int i = 0; i < exercicios.size(); i++) {
            if (new ResultadoDao().getResultados(exercicios.get(i).getId()).size() > 0) {
                remeveutodos = false;
            } else {
                new ExercicioDao().deleteExercicio(exercicios.get(i).getId());
            }
        }

        if (!remeveutodos){
            Toast.makeText(RemoverController.this, "Alguns exercícios não puderam ser removidos, possuem resultados vinculados...", Toast.LENGTH_LONG).show();
        }
        carregaExercicios();
    }


    private void removerTodosResultados() {
        new ResultadoDao().limparResultados();
        carregaResultados();
    }



    private void carregaResultados() {
        ResultadoDao oResultadoDao = new ResultadoDao();

        ArrayList<Resultado> resultados = oResultadoDao.getResultados();

        ArrayAdapter<Resultado> resultadoAdapter = new ArrayAdapter<Resultado>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, resultados);
        listResultadoRemover.setAdapter(resultadoAdapter);
    }

    private void carregaExercicios() {
        ExercicioDao oExercicioDao = new ExercicioDao();

        ArrayList<Exercicio> exercicios = oExercicioDao.getExercicios();

        ArrayAdapter<Exercicio> exercicioAdapter = new ArrayAdapter<Exercicio>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, exercicios);
        listExercicioRemover.setAdapter(exercicioAdapter);
    }


    /**
     private boolean confirmaExclusao() {
     boolean ff;

     AlertDialog.Builder builder = new AlertDialog.Builder(this);
     builder.setTitle("Excluir");
     builder.setMessage("Você realmente deseja excluir esse ítem?");


     builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface arg0, int arg1) {
     return true;
     }
     });

     builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
     public void onClick(DialogInterface arg0, int arg1) {

     }
     });

     //cria o AlertDialog
     mensagemConfirmacao = builder.create();
     //Exibe
     mensagemConfirmacao.show();

     return builder.
     }
     **/

}

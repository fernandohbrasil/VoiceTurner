package br.com.fernandohbrasil.voicetuner.controller;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.fernandohbrasil.voicetuner.R;
import br.com.fernandohbrasil.voicetuner.config.MyApp;
import br.com.fernandohbrasil.voicetuner.dao.ExercicioDao;
import br.com.fernandohbrasil.voicetuner.dao.NotaDao;
import br.com.fernandohbrasil.voicetuner.dao.ResultadoDao;
import br.com.fernandohbrasil.voicetuner.model.Afinacao;
import br.com.fernandohbrasil.voicetuner.model.Exercicio;
import br.com.fernandohbrasil.voicetuner.model.ItemResultado;
import br.com.fernandohbrasil.voicetuner.model.Nota;
import br.com.fernandohbrasil.voicetuner.model.NotaExercicio;
import br.com.fernandohbrasil.voicetuner.model.Resultado;

/**
 * Created by fernando on 25/02/18.
 */

public class TreinoController extends Activity {

    //Declaração dos objetos
    private static final int[] SAMPLE_RATES = {44100, 22050, 16000, 11025, 8000};

    private AudioRecord mAudioRecord;

    private Exercicio oExercicioSelecionado;
    private int seqNota;
    private int tentativa;
    private float frequencia;

    private Button btnCaptura, btnConfirma, btnDescarta, btnGrava;
    private TextView txtSequenciaNota, txtResultado, txtQuantidadeTentativas;

    private ListView lvExercicios, lvExercicioSelecionado, lvResultado;

    private ArrayList<String> resultados;
    private ArrayList<Float> listFrequencia;
    private ArrayList<Nota> listNota;

    private ArrayAdapter<Exercicio> exerciciosAdapter;
    private ArrayAdapter<Exercicio> exercicioSelecionadoAdapter;
    private ArrayAdapter<String> resultadoAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.treino);

        iniciaCaptura();
        getFreq();
        iniciaObjetos();
        iniciaEventos();
        iniciaVariaveisTreino();
        carregaExercicios();
        mostraInfo();
    }

    public void iniciaCaptura() {
        int bufSize = 16384;
        int avalaibleSampleRates = SAMPLE_RATES.length;
        int i = 0;
        do {
            int sampleRate = SAMPLE_RATES[i];
            int minBufSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            if (minBufSize != AudioRecord.ERROR_BAD_VALUE && minBufSize != AudioRecord.ERROR) {
                mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, Math.max(bufSize, minBufSize * 4));
            }
            i++;
        }
        while (i < avalaibleSampleRates && (mAudioRecord == null || mAudioRecord.getState() != AudioRecord.STATE_INITIALIZED));
    }

    private void iniciaObjetos() {
        lvExercicios = (ListView) findViewById(R.id.listExercicio);
        lvExercicios.setLongClickable(true);

        lvExercicioSelecionado = (ListView) findViewById(R.id.listExercicioSelecionado);
        lvExercicioSelecionado.setLongClickable(true);

        lvResultado = (ListView) findViewById(R.id.listResultado);

        btnConfirma = (Button) findViewById(R.id.btnConfirma);
        btnCaptura = (Button) findViewById(R.id.btnCaptura);
        btnDescarta = (Button) findViewById(R.id.btnDescartaTreino);
        btnGrava = (Button) findViewById(R.id.btnGravaTreino);

        txtSequenciaNota = (TextView) findViewById(R.id.txtSequenciaNota);
        txtResultado = (TextView) findViewById(R.id.txtResultado);
        txtQuantidadeTentativas = (TextView) findViewById(R.id.txtQuantidadeTentativas);
    }

    private void iniciaEventos() {
        lvExercicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tocarExercicio((Exercicio) lvExercicios.getItemAtPosition(i));
            }
        });

        lvExercicios.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                selecionaExercicio((Exercicio) lvExercicios.getItemAtPosition(i));
                return true;
            }
        });

        lvExercicioSelecionado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tocarExercicio((Exercicio) lvExercicioSelecionado.getItemAtPosition(i));
            }
        });

        lvExercicioSelecionado.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                removeExercicio();
                return true;
            }
        });

        btnCaptura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capturaNota();
            }
        });

        btnConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proximaNota();
            }
        });

        btnDescarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                descartaresultado();
            }
        });

        btnGrava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private void salvar() {
        if (listFrequencia.size() > 0) {
            int resultadoId = new ResultadoDao().getResultadoId();
            Resultado oResultado = new Resultado(resultadoId, MyApp.getUsuarioLogado(), oExercicioSelecionado, getItemResultado());

            ResultadoDao oResultadoDao = new ResultadoDao();
            if (ResultadoDao.cadastraResultado(oResultado)) {
                Toast.makeText(this, "Resultado gravado com Sucesso!", Toast.LENGTH_LONG).show();
                descartaresultado();
            } else {
                Toast.makeText(this, "Erro ao cadastrar resultado!", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Não há resultados para gravar", Toast.LENGTH_SHORT).show();
        }

    }

    private ArrayList<ItemResultado> getItemResultado() {
        ArrayList<ItemResultado> itensResultado = new ArrayList<>();

        for (int i = 0; i < listFrequencia.size(); i++) {
            itensResultado.add(new ItemResultado(i + 1, listFrequencia.get(i), listNota.get(i)));
        }
        return itensResultado;
    }

    private void capturaNota() {
        if (this.oExercicioSelecionado != null) {
            if (tentativa <= 3) {
                if (seqNota <= oExercicioSelecionado.getProgressao().size()) {
                    tentativa++;
                    toWait(1800);
                    frequencia = getFreq();
                    frequencia = getFreq();
                    atualizaTela(seqNota, frequencia, tentativa);
                } else {
                    Toast.makeText(this, "Exercício concluido!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Tentativas esgotadas para essa nota, necessário recomeçar exercício!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Selecione um exercício", Toast.LENGTH_SHORT).show();
        }
    }

    private void proximaNota() {
        if (this.oExercicioSelecionado != null) {

            if (seqNota <= oExercicioSelecionado.getProgressao().size()) {
                listFrequencia.add(frequencia);
                listNota.add(NotaDao.getNota(new Afinacao().getNotaId(frequencia)));

                seqNota++;
                tentativa = 1;
                frequencia = 0;
                atualizaTela(seqNota, frequencia, tentativa);
            } else {
                mostrarResultado();
                Toast.makeText(this, "Exercício concluido!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Selecione um exercício", Toast.LENGTH_SHORT).show();
        }
    }

    private void instrucoes() {
        resultados.add("Após o clique, inicie a reprodução da nota!");
        resultados.add("Não deixe o dispositivo, nem muito perto e nem muito longe, a distância do braço quase esticado é a ideal");
        resultados.add("Após-Se a frequência capturada for 0, recomenda-se observar a intensidade de sua voz, bem como a distância que está seu dispositivo.");
        resultados.add("Certifique-se de treinar em um local silencioso");
        resultados.add("Você possui 3 tentativas por nota. Ao tentar novamente, você está descartando seu resultado anteriror");
    }

    private void infoExercicio(Exercicio oExercicio) {
        resultados.clear();

        for (int i = 0; i < oExercicio.getProgressao().size(); i++) {
            resultados.add(oExercicio.getProgressao().get(i).getaNota().toString());
        }
        mostraInfo();
    }

    private void descartaresultado() {
        iniciaVariaveisTreino();
    }

    private void iniciaVariaveisTreino() {
        seqNota = 1;
        tentativa = 1;
        frequencia = 0;
        listFrequencia = new ArrayList<>();
        listNota = new ArrayList<>();
        resultados = new ArrayList<>();
        instrucoes();
        mostraInfo();

        removeExercicio();

        atualizaTela(seqNota, frequencia, tentativa);
    }

    private void mostraInfo() {
        resultadoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, resultados);
        lvResultado.setAdapter(resultadoAdapter);
    }

    private void mostrarResultado(){
        ArrayList<NotaExercicio> notasExercicio = oExercicioSelecionado.getProgressao();
        ArrayList<String> resultados = new ArrayList<>();

        for (int i = 0; i < notasExercicio.size(); i++) {
            if(listNota.get(i) == null){
                int aa = 3;
                int saa = 3;
            }


            resultados.add("Esperada:  " + notasExercicio.get(i).getaNota().toString() + "\n"
                    + "Frequência recebida:  " + listFrequencia.get(i) + "\n"
                    + listNota.get(i).toString());
        }
        resultadoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, resultados);
        lvResultado.setAdapter(resultadoAdapter);
    }

    private void atualizaTela(int sequencia, float frequencia, int tentativa) {
        if (tentativa > 3) {
            tentativa = 3;
        }

        txtSequenciaNota.setText("Nota nº: " + sequencia);
        txtResultado.setText("Frequência Capturada: " + frequencia);
        txtQuantidadeTentativas.setText("Tentativa " + tentativa + " de 3");
    }

    private void selecionaExercicio(Exercicio oExercicio) {
        this.oExercicioSelecionado = oExercicio;

        ArrayList<Exercicio> exercicioSelecionado = new ArrayList<>();
        exercicioSelecionado.add(oExercicio);

        exercicioSelecionadoAdapter = new ArrayAdapter<Exercicio>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, exercicioSelecionado);
        lvExercicioSelecionado.setAdapter(exercicioSelecionadoAdapter);

        infoExercicio(oExercicio);
    }

    private void removeExercicio() {
        this.oExercicioSelecionado = null;

        ArrayList<Exercicio> exercicioSelecionado = new ArrayList<>();
        exercicioSelecionado.clear();

        exercicioSelecionadoAdapter = new ArrayAdapter<Exercicio>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, exercicioSelecionado);
        lvExercicioSelecionado.setAdapter(exercicioSelecionadoAdapter);
    }

    private void tocarExercicio(Exercicio oExercicio) {
        Toast.makeText(TreinoController.this, "Reproduzindo Exercício", Toast.LENGTH_LONG).show();
        for (int i = 0; i < oExercicio.getProgressao().size(); i++) {
            MyApp.tocarNota(oExercicio.getProgressao().get(i).getaNota(), TreinoController.this);
            toWait(3000);
        }
    }

    private void carregaExercicios() {
        ArrayList<Exercicio> exercicios = new ExercicioDao().getExercicios();

        if (exercicios.isEmpty()) {
            resultados.clear();
            resultados.add("Nenhum exercício cadastrado!!!");
        }

        exerciciosAdapter = new ArrayAdapter<Exercicio>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, exercicios);
        lvExercicios.setAdapter(exerciciosAdapter);
    }

    private double averageIntensity(short[] data, int frames) {
        double sum = 0;
        for (int i = 0; i < frames; i++) {
            sum += Math.abs(data[i]);
        }
        return sum / frames;

    }

    private int zeroCrossingCount(short[] data) {
        int len = data.length;
        int count = 0;
        boolean prevValPositive = data[0] >= 0;
        for (int i = 1; i < len; i++) {
            boolean positive = data[i] >= 0;
            if (prevValPositive == !positive)
                count++;
            prevValPositive = positive;
        }
        return count;
    }

    private float getPitch(short[] data, int windowSize, int frames, float sampleRate, float minFreq, float maxFreq) {
        float maxOffset = sampleRate / minFreq;
        float minOffset = sampleRate / maxFreq;

        int minSum = Integer.MAX_VALUE;
        int minSumLag = 0;
        int[] sums = new int[Math.round(maxOffset) + 2];

        for (int lag = (int) minOffset; lag <= maxOffset; lag++) {
            int sum = 0;
            for (int i = 0; i < windowSize; i++) {
                int oldIndex = i - lag;
                int sample = ((oldIndex < 0) ? data[frames + oldIndex] : data[oldIndex]);
                sum += Math.abs(sample - data[i]);
            }
            sums[lag] = sum;

            if (sum < minSum) {
                minSum = sum;
                minSumLag = lag;
            }
        }
        // quadratic interpolation
        float delta = (float) (sums[minSumLag + 1] - sums[minSumLag - 1]) / ((float) (2 * (2 * sums[minSumLag] - sums[minSumLag + 1] - sums[minSumLag - 1])));
        return sampleRate / (minSumLag + delta);
    }

    private float getFreq() {
        mAudioRecord.startRecording();

        int bufSize = 8192;
        final int sampleRate = mAudioRecord.getSampleRate();
        final short[] buffer = new short[bufSize];
        float freq = 0;

        final int read = mAudioRecord.read(buffer, 0, bufSize);

        if (read > 0) {
            final double intensity = averageIntensity(buffer, read);
            int maxZeroCrossing = (int) (250 * (read / 8192) * (sampleRate / 44100.0));

            if (intensity >= 50 && zeroCrossingCount(buffer) <= maxZeroCrossing) {
                freq = getPitch(buffer, read / 4, read, sampleRate, 50, 500);
            }
        }
        return freq;
    }

    private void toWait(long tempo) {
        long t = System.currentTimeMillis();
        long end = t + tempo;

        while (System.currentTimeMillis() < end) {

        }
    }
}

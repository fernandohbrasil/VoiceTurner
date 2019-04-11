package br.com.fernandohbrasil.voicetuner.model;

public class NotaExercicio {

    private int sequencia;
    private Nota aNota;

    public NotaExercicio() {

    }

    public NotaExercicio(int sequencia, Nota aNota) {
        this.sequencia = sequencia;
        this.aNota = aNota;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public Nota getaNota() {
        return aNota;
    }

    public void setaNota(Nota aNota) {
        this.aNota = aNota;
    }
}

package br.com.fernandohbrasil.voicetuner.model;

public class ItemResultado {

    private int sequencia;
    private float frequencia;
    private Nota oNota;

    public ItemResultado() {
    }

    public ItemResultado(int sequencia, float frequencia, Nota oNota) {
        this.sequencia = sequencia;
        this.frequencia = frequencia;
        this.oNota = oNota;
    }

    public int getSequencia() {
        return sequencia;
    }

    public void setSequencia(int sequencia) {
        this.sequencia = sequencia;
    }

    public float getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(float frequencia) {
        this.frequencia = frequencia;
    }

    public Nota getoNota() {
        return oNota;
    }

    public void setoNota(Nota oNota) {
        this.oNota = oNota;
    }
}

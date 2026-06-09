public class Nota {

    private String numeDisciplina;
    private double nota;

    public Nota() {
        numeDisciplina = "-";
        nota= 0;
    }

    public Nota(String numeDisciplina, double nota) {
        this.numeDisciplina = numeDisciplina;
        this.nota = nota;
    }

    public String getNumeDisciplina() {
        return numeDisciplina;
    }

    public void setNumeDisciplina(String numeDisciplina) {
        this.numeDisciplina = numeDisciplina;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        if(nota >= 1 && nota <= 10)
            this.nota = nota;
        else
            throw  new IllegalArgumentException("Nota invalida");
    }

    @Override
    public String toString() {
        return
                "numeDisciplina='" + numeDisciplina + '\'' +
                ", nota=" + nota ;
    }
}

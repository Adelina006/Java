public class Consultatii {
    private String specialitate;
    private int codManevra;
    private int numar;

    public Consultatii(String specialitate, int codManevra, int numar) {
        this.specialitate = specialitate;
        this.codManevra = codManevra;
        this.numar = numar;
    }

    public String getSpecialitate() {
        return specialitate;
    }

    public void setSpecialitate(String specialitate) {
        this.specialitate = specialitate;
    }

    public int getCodManevra() {
        return codManevra;
    }

    public void setCodManevra(int codManevra) {
        this.codManevra = codManevra;
    }

    public int getNumar() {
        return numar;
    }

    public void setNumar(int numar) {
        this.numar = numar;
    }

    @Override
    public String toString() {
        return "Consultatii{" +
                "specialitate='" + specialitate + '\'' +
                ", codManevra=" + codManevra +
                ", numar=" + numar +
                '}';
    }
}

public class Autor {

    private int id;
    private String nume;
    private String prenume;
    private String tara;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Autor(int id, String nume, String prenume, String tara) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.tara = tara;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", tara='" + tara + '\'' +
                '}';
    }
}

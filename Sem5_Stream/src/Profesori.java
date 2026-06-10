public class Profesori {

    private int id;
    private String prenume;
    private String nume;
    private String Departament;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartament() {
        return Departament;
    }

    public void setDepartament(String departament) {
        Departament = departament;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Profesori(int id, String nume, String prenume, String departament) {
        Departament = departament;
        this.nume = nume;
        this.prenume = prenume;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Profesori{" +
                "id=" + id +
                ", prenume='" + prenume + '\'' +
                ", nume='" + nume + '\'' +
                ", Departament='" + Departament + '\'' +
                '}';
    }
}

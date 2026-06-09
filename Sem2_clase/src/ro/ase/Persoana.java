package ro.ase;

public class Persoana implements Comparable<Persoana>
{

    public static final int COD_NEINITIALIZAT = 0;
    public static final String NUME_NEINITIALIZAT="-";

    private int cod;
    private String nume;

    private static int ultimulCod = 0;

    public Persoana(String nume) {
        this.nume = nume;
        ultimulCod = ultimulCod + 1;
        this.cod = ultimulCod;
    }

    public Persoana() {
        this(NUME_NEINITIALIZAT);
    }

    public int getCod() {
        return cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Persoana{" +
                "cod=" + cod +
                ", nume='" + nume + '\'' +
                '}';
    }


    @Override
    public int compareTo(Persoana o) {
        return this.nume.compareTo(o.getNume());
    }
}

public class MateriiPrime {

    private int cod;
    private String nume;
    private double cantitate;
    private double Pret_unitar;
    private String Unitate_masura;

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret_unitar() {
        return Pret_unitar;
    }

    public void setPret_unitar(double pret_unitar) {
        Pret_unitar = pret_unitar;
    }

    public double getCantitate() {
        return cantitate;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    public String getUnitate_masura() {
        return Unitate_masura;
    }

    public void setUnitate_masura(String unitate_masura) {
        Unitate_masura = unitate_masura;
    }

    public MateriiPrime(int cod, String nume, double cantitate, double pret_unitar, String unitate_masura) {
        this.cod = cod;
        this.nume = nume;
        this.cantitate = cantitate;
        Pret_unitar = pret_unitar;
        Unitate_masura = unitate_masura;
    }

    @Override
    public String toString() {
        return "MateriiPrime{" +
                "cod=" + cod +
                ", nume='" + nume + '\'' +
                ", cantitate=" + cantitate +
                ", Pret_unitar=" + Pret_unitar +
                ", Unitate_masura='" + Unitate_masura + '\'' +
                '}';
    }
}

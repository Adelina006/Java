public class Consumuri {

    private int cod;
    private double cantitate;

    public Consumuri(int cod, double cantitate) {
        this.cod = cod;
        this.cantitate = cantitate;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public double getCantitate() {
        return cantitate;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "MaterieProdus{" +
                "cod=" + cod +
                ", cantitate=" + cantitate +
                '}';
    }
}

public class Preturi {

    private String Simbol;
    private double deschidere;
    private double min;
    private double max;
    private double inchidere;
    private long volum;

    public String getSimbol() {
        return Simbol;
    }

    public void setSimbol(String simbol) {
        Simbol = simbol;
    }

    public double getDeschidere() {
        return deschidere;
    }

    public void setDeschidere(double deschidere) {
        this.deschidere = deschidere;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getInchidere() {
        return inchidere;
    }

    public void setInchidere(double inchidere) {
        this.inchidere = inchidere;
    }

    public long getVolum() {
        return volum;
    }

    public void setVolum(long volum) {
        this.volum = volum;
    }

    public Preturi(String simbol, double deschidere, double min, double max, double inchidere, long volum) {
        Simbol = simbol;
        this.deschidere = deschidere;
        this.min = min;
        this.max = max;
        this.inchidere = inchidere;
        this.volum = volum;
    }

    @Override
    public String toString() {
        return "Preturi{" +
                "Simbol='" + Simbol + '\'' +
                ", deschidere=" + deschidere +
                ", min=" + min +
                ", max=" + max +
                ", inchidere=" + inchidere +
                ", volum=" + volum +
                '}';
    }
}

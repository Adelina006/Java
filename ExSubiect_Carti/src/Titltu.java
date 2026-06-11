public class Titltu {

    private String Simbol;
    private String deumire;

    public Titltu(String simbol, String deumire) {
        Simbol = simbol;
        this.deumire = deumire;
    }

    public String getSimbol() {
        return Simbol;
    }

    public void setSimbol(String simbol) {
        Simbol = simbol;
    }

    public String getDeumire() {
        return deumire;
    }

    public void setDeumire(String deumire) {
        this.deumire = deumire;
    }

    @Override
    public String toString() {
        return "Titltu{" +
                "Simbol='" + Simbol + '\'' +
                ", deumire='" + deumire + '\'' +
                '}';
    }
}

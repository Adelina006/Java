import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {

    private final String numeClient;
    private final LocalDate data;
    private final List<Linie> linii;

    public Factura(String numeClient, LocalDate data) {
        this.numeClient = numeClient;
        this.data = data;
        this.linii = new ArrayList<>();
    }

    public String getNumeClient() {
        return numeClient;
    }

    public List<Linie> getLinii() {
        return linii;
    }

    public LocalDate getData() {
        return data;
    }

    public void adaugareLinie( Linie l)
    {
        this.linii.add(l);
    }

    static final class Linie
    {
        private final String Produs;
        private final double pret;
        private final int cantitate;

        public Linie(String produs, double pret, int cantitate) {
            Produs = produs;
            this.pret = pret;
            this.cantitate = cantitate;
        }

        public String getLinie() {
            return Produs;
        }

        public int getCantitate() {
            return cantitate;
        }

        public double getPret() {
            return pret;
        }

        @Override
        public String toString() {
            return String.format("%s - %5.2f - %d ", Produs, pret, cantitate );
        }
    }

    @Override
    public String toString() {
        return "Factura{" +
                "numeClient='" + numeClient + '\'' +
                ", data=" + data +
                ", linii=" + linii +
                '}';
    }
}

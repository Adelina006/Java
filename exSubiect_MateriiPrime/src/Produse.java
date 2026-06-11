import java.util.ArrayList;
import java.util.List;

public class Produse {

    private int codProdus;
    private String denumireProdus;
    private List<Consumuri> consumuri;
    private int cantitateProdus;
    private String unitateProdus;

    public int getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(int codProdus) {
        this.codProdus = codProdus;
    }

    public String getDenumireProdus() {
        return denumireProdus;
    }

    public void setDenumireProdus(String denumireProdus) {
        this.denumireProdus = denumireProdus;
    }

    public List<Consumuri> getConsumuri() {
        return consumuri;
    }

    public void setConsumuri(List<Consumuri> consumuri) {
        this.consumuri = consumuri;
    }

    public int getCantitateProdus() {
        return cantitateProdus;
    }

    public void setCantitateProdus(int cantitateProdus) {
        this.cantitateProdus = cantitateProdus;
    }

    public String getUnitateProdus() {
        return unitateProdus;
    }

    public void setUnitateProdus(String unitateProdus) {
        this.unitateProdus = unitateProdus;
    }

    public Produse(int codProdus, String denumireProdus, int cantitateProdus, String unitateProdus) {
        this.codProdus = codProdus;
        this.denumireProdus = denumireProdus;
        this.cantitateProdus = cantitateProdus;
        this.unitateProdus = unitateProdus;
        this.consumuri= new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Produse{" +
                "codProdus=" + codProdus +
                ", denumireProdus='" + denumireProdus + '\'' +
                ", consumuri=" + consumuri +
                ", cantitateProdus=" + cantitateProdus +
                ", unitateProdus='" + unitateProdus + '\'' +
                '}';
    }
}

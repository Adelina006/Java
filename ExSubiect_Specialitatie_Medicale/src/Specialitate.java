import java.util.ArrayList;
import java.util.List;

public class Specialitate {
    private String denumire;
    private List<Manevra> manevre;

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public List<Manevra> getManevre() {
        return manevre;
    }

    public void setManevre(List<Manevra> manevre) {
        this.manevre = manevre;
    }

    public Specialitate(String denumire) {
        this.denumire = denumire;
        this.manevre = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Specialitate{" +
                "denumire='" + denumire + '\'' +
                ", manevre=" + manevre +
                '}';
    }
}

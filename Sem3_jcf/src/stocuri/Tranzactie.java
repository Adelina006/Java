package stocuri;

import java.time.LocalDate;

public class Tranzactie {

    private TipTranzactie tip;
    private LocalDate data;
    private int IdProdus;
    private int cantitate;

    public Tranzactie(TipTranzactie tip,LocalDate data, int idProdus,  int cantitate) {
        this.tip = tip;
        this.cantitate = cantitate;
        IdProdus = idProdus;
        this.data = data;
    }

    public TipTranzactie getTip() {
        return tip;
    }

    public void setTip(TipTranzactie tip) {
        this.tip = tip;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getIdProdus() {
        return IdProdus;
    }

    public void setIdProdus(int idProdus) {
        IdProdus = idProdus;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "cantitate=" + cantitate +
                ", data=" + data +
                ", tip=" + tip +
                '}';
    }
}

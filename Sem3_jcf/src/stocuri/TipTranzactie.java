package stocuri;

public enum TipTranzactie {

    INTRARE(1),
    IESIRE(-1);

    private int semn;

    TipTranzactie(int semn) {
        this.semn = semn;
    }

    public int getSemn() {
        return semn;
    }
}

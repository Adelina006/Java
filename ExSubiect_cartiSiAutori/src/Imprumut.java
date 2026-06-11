import java.time.LocalDate;

public class Imprumut {

    private String ISBN;
    private String titlu;
    private Autor autor;
    private LocalDate data;
    private int nrZile;
    private TipCarte tip;
    private String Categorie;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public int getNrZile() {
        return nrZile;
    }

    public void setNrZile(int nrZile) {
        this.nrZile = nrZile;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public TipCarte getTip() {
        return tip;
    }

    public void setTip(TipCarte tip) {
        this.tip = tip;
    }

    public String getCategorie() {
        return Categorie;
    }

    public void setCategorie(String categorie) {
        Categorie = categorie;
    }

    public Imprumut(String ISBN, String titlu, Autor autor, LocalDate data, int nrZile, TipCarte tip, String categorie) {
        this.ISBN = ISBN;
        this.titlu = titlu;
        this.autor = autor;
        this.data = data;
        this.nrZile = nrZile;
        this.tip = tip;
        Categorie = categorie;
    }

    @Override
    public String toString() {
        return "Imprumut{" +
                "ISBN='" + ISBN + '\'' +
                ", titlu='" + titlu + '\'' +
                ", autor=" + autor +
                ", data=" + data +
                ", nrZile=" + nrZile +
                ", tip=" + tip +
                ", Categorie='" + Categorie + '\'' +
                '}';
    }
}

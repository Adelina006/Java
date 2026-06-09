import java.util.Arrays;

public class Student {

    private int idstudent;
    private String nume;
    private int grupa;
    private int an;
    private Nota[] note;

    public Student() {
        this(0, "-",0,  0);
    }

    public Student(int idstudent, String nume, int grupa, int an) {
      setNume(nume);
      setIdstudent(idstudent);
      setAn(an);
      setNote(new Nota[0]);
      setGrupa(grupa);
    }

    public int getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(int idstudent) {
        this.idstudent = idstudent;
    }

    public Nota[] getNote() {
        return note;
    }

    public void setNote(Nota[] note) {
        this.note = note;
    }

    public int getAn() {
        return an;
    }

    public void setAn(int an) {
        if(an >= 1 && an<= 4)
         this.an = an;
        else
            throw new IllegalArgumentException("AN Invalid");
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void add( Nota nota)
    {
        for(Nota n : getNote())
        {
            if(n.getNumeDisciplina().equals(nota.getNumeDisciplina()))
            {
                n.setNota(nota.getNota());
                return;
            }
        }

        this.note = Arrays.copyOf(note, note.length + 1);
        note[note.length - 1] = nota;
    }

    @Override
    public String toString() {
        return
                "idstudent=" + idstudent +
                ", nume='" + nume + '\'' +
                ", grupa=" + grupa +
                ", an=" + an +
                ", note=" + Arrays.toString(note);
    }
}

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class Student implements Cloneable {

    private final String nume;
    private Map<String, Integer> note;

    public Student(String nume) {
        this.nume = nume;
        this.note = new HashMap<>();
    }

    public String getNume() {
        return nume;
    }

    public void addNota(String nume, int nota)
    {
        if( nota < 1 || nota > 10)
        {
            throw new IllegalArgumentException("Nota Invalida");

        }
        else
        {
            note.put(nume, nota);
        }
    }

    public void StergeNota(String nume)
    {
        note.remove(nume);
    }

    public Set<String> getDiscipline()
    {
        return note.keySet();
    }

    public int GetNota( String nume)
    {
        if(areNota(nume))
        {
            return note.get(nume);
        }
        else {
            throw new NoSuchElementException("Nu are nota la aceasta disciplina");
        }
    }

    public boolean areNota(String nume)
    {
        if( note.containsKey(nume))
        {
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public String toString() {
       var mesaj = new StringBuilder();
       mesaj.append(nume);
       mesaj.append(System.lineSeparator());
       for(Map.Entry<String, Integer> n : note.entrySet())
       {
           mesaj.append("  " + n.getKey() +" : " + n.getValue());
           mesaj.append(System.lineSeparator());
       }
       return mesaj.toString();
    }


    @Override
    public Student clone() {
        try {
            Student copie = (Student) super.clone();
            copie.note = new HashMap<>(this.note);
            return copie;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}


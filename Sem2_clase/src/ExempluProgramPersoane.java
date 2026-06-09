import ro.ase.Persoana;

import java.util.Arrays;
import java.util.Comparator;

class ComparatorCos implements Comparator
{

    @Override
    public int compare(Object o1, Object o2) {
        Persoana p1 = (Persoana) o1;
        Persoana p2 = (Persoana) o2;
        return Integer.compare(p1.getCod(), p2.getCod());
    }
}

public class ExempluProgramPersoane {

    static void afisare (String mesaj ,Persoana[] vector)
    {
        System.out.println(mesaj);
        for(Persoana p : vector)
        {
            System.out.println(p);
        }
    }

    public static void main (String[] args)
    {
        Persoana p = new Persoana();
        Persoana p2 = new Persoana("Ion");
        Persoana p3 = new Persoana("Maria");

       // System.out.println(p2);
       // System.out.println(p);
        p.setNume("George");

        var persoane = new Persoana[] {p2, p, p3};
        afisare("initial", persoane );

       //Arrays.sort(persoane, new ComparatorCos());
//        Arrays.sort(persoane, new Comparator<Persoana>() {
//            @Override
//            public int compare(Persoana o1, Persoana o2) {
//                return Integer.compare(o1.getCod(), o2.getCod());
//            }
//        });


       // afisare("Sortare dupa cod", persoane);

        Arrays.sort(persoane);
        afisare("Sortare dupa Nume", persoane);

    }
}



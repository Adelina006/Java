import stocuri.Produs;
import stocuri.TipTranzactie;
import stocuri.Tranzactie;

import java.time.LocalDate;
import java.util.*;

public class ProgramStocuri {

    private static Map<Produs, List<Tranzactie>> stocuri = new HashMap<>();

    public static void main(String[] args)
    {
        stocuri = new HashMap<>();
        AdaugaProdus(1, "A");
        AdaugaProdus(0, "C");
        AdaugaProdus(2, "B");

        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2020,1,3), 1, 10);
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2020,1,6), 2, 10);
        AdaugaTranzactie(TipTranzactie.IESIRE, LocalDate.of(2020,2,6), 1, 7);
        AdaugaTranzactie(TipTranzactie.INTRARE, LocalDate.of(2019,2,6), 1, 7);
        afisare();
    }

    public static void AdaugaProdus(int id, String denumire)
    {
        stocuri.put(new Produs(id, denumire), new ArrayList<>());
    }

    public static void AdaugaTranzactie(TipTranzactie tip, LocalDate data, int id, int cant)
    {
        var p = new Produs(id);
        if(stocuri.containsKey(p))
        {
            stocuri.get(p).add(new Tranzactie(tip, data, id, cant));
        }
        else
        {
            throw new NoSuchElementException("Nu exista Produsul");
        }
    }

    public static void afisare()
    {
        for(var p : stocuri.entrySet())
        {
            System.out.println(p.getKey());
            var tranz = p.getValue();
            for(var t : tranz)
            {
                System.out.println("  " +t);
            }

        }
    }
}

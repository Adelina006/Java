import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProgramAutori {
    private static Map<Integer, Autor> autori = new HashMap<>();
    private static List<Imprumut> imprumuturi = new ArrayList<>();


    public static void main(String[] args) throws FileNotFoundException {
        citireAutori();
        afisareAutori(autori);
        citireImprumuturi();
        afisareImprumuturi(imprumuturi);
        System.out.println("===================================================================================");
        afisareOrdonata();
        System.out.println("===================================================================================");
        afisareStatisticiAutor();
        System.out.println("===================================================================================");
        statisticiCategorie();

    }

    public static void citireAutori() throws FileNotFoundException {
        FileReader file = new FileReader("data\\autori.txt");
        try(var buffer = new BufferedReader(file))
        {
            autori = buffer.lines()
                    .map(linie ->
                            new Autor(
                                   Integer.parseInt( linie.split(",")[0].trim()),
                                    linie.split(",")[1].trim(),
                                    linie.split(",")[2].trim(),
                                    linie.split(",")[3].trim())
                            )
                    .collect(Collectors.toMap(autor -> autor.getId(), autor-> autor));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void afisareAutori(Map<Integer, Autor> autori)
    {
        for(var a : autori.entrySet())
        {
            System.out.println(a);
        }
    }

    public static void citireImprumuturi() throws FileNotFoundException {
        FileReader file = new FileReader("data\\imprumuturi.txt");
        try( var buffer = new BufferedReader(file))
        {
            imprumuturi = buffer.lines()
                    .map(linie ->new Imprumut(
                            linie.split(",")[0].trim(),
                                    linie.split(",")[1].trim(),
                            autori.get(Integer.parseInt(  linie.split(",")[2].trim())),
                                    LocalDate.parse(  linie.split(",")[3].trim()),
                                    Integer.parseInt(  linie.split(",")[4].trim()),
                                    TipCarte.valueOf(  linie.split(",")[5].trim()),
                                    linie.split(",")[6])
                            )
                    .collect(Collectors.toList());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void afisareImprumuturi(List<Imprumut> imprumuturi)
    {
        for(var i : imprumuturi)
        {
            System.out.println(i);
        }
    }

    public static void afisareOrdonata()
    {
        List<String> carti = imprumuturi.stream()
                .map(imprumut ->
                {
                    var carte = new StringBuilder();
                    carte.append(imprumut.getTitlu() + " - " + imprumut.getTip());
                    var rezultat = carte.toString();
                    return rezultat;
                })
                .distinct()
                .sorted((c1, c2) -> c1.compareTo(c2))
                .collect(Collectors.toList());
        carti.stream()

                .forEach(carte ->
                {
                    System.out.println(carte);
                });
    }

    public static void afisareStatisticiAutor()
    {

       imprumuturi.stream()
                .collect(Collectors.groupingBy(Imprumut::getAutor))
               .forEach((autor, carti) ->
               {
                   var nume = autor.getNume();
                   int fizic = Math.toIntExact(carti.stream()
                           .map(imprumut -> imprumut.getTip().equals("FIZIC"))
                           .count());
                   int ebook = Math.toIntExact(carti.stream()
                           .map(imprumut -> imprumut.getTip().equals("EBOOK"))
                           .count());
                   int audio = Math.toIntExact(carti.stream()
                           .map(imprumut -> imprumut.getTip().equals("AUDIOBOOK"))
                           .count());
                   System.out.printf("%-40s => %d fizice, %d Audio, %d Ebook \n", nume, fizic, audio, ebook);


               });

    }

    public static void statisticiCategorie()
    {
        record linie(String categorie, int nrZile){}

        List<linie> raport = imprumuturi.stream()
                .map(carte -> carte.getCategorie())
                .distinct()
                .map(categorie ->
                {
                    int zile = Math.toIntExact(imprumuturi.stream()
                            .filter(imprumut -> imprumut.getCategorie().equals(categorie))
                            .mapToInt(imprumut -> imprumut.getNrZile())
                            .sum()
                    );



                    return new linie(categorie, zile);
                })
                .collect(Collectors.toList());
        raport.stream()
                .sorted((linie1, linie2) -> Integer.compare(linie1.nrZile, linie2.nrZile))
                .forEach(linie ->
                        System.out.println(linie));



    }

}

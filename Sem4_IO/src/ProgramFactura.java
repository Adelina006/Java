import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class ProgramFactura {


    private static List<Factura> facturi = new ArrayList<>();
    private static List<Factura> facturiCitite = new ArrayList<>();
    public static void main (String[] args)
    {
        genereazaFacturi(LocalDate.of(2020, 01, 10), 10);
       //afisare(facturi);
       salvare("Test1.txt");
        citire("Test1.txt");
        afisare(facturiCitite);
        genereazaRaport(facturiCitite, "Raport1.txt");





    }


    public static void genereazaRaport(List<Factura> facturi, String numeFisier)
    {
        record LinieFactura(String numeClient, int nrFacturi, double total){}

        Map<String, List<Factura>> facturiSortate = facturi.stream()
                .collect(Collectors.groupingBy(Factura::getNumeClient));

        List<LinieFactura> raport = facturiSortate.entrySet().stream()
                .map( entry ->
                        {
                            var Nume = entry.getKey();
                            List<Factura> facturiSort = entry.getValue();

                            var nr = facturiSort.size();

                           List<Factura.Linie> linii = facturiSort.stream()
                                   .flatMap(f -> f.getLinii().stream())
                                   .collect(Collectors.toList());

                           double total = linii.stream()
                                   .mapToDouble(l -> l.getCantitate() * l.getPret())
                                   .sum();

                           return new LinieFactura(Nume, nr,total);
                        })
                .sorted((f1, f2) -> Double.compare(f1.total(), f2.total()))
                .collect(Collectors.toList());

        File file = new File(numeFisier);
        try(var writer = new PrintWriter(file))
        {
            for(var f : raport)
            {
                writer.printf("%-30s %d facturi, Total: %5.2f RON \n", f.numeClient, f.nrFacturi, f.total);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Nu se poate scrie in fisier");
        }


    }

    public static void citire(String nume)
    {
        File file = new File(nume);
        try( Scanner scanner = new Scanner(file))
        {
            while(scanner.hasNextLine()) {
                var numeCL = scanner.nextLine();
                var data = LocalDate.parse(scanner.nextLine());
                var f = new Factura(numeCL, data);
                var nr = Integer.parseInt(scanner.nextLine());
                for (int i = 0; i < nr; i++) {
                    var linie = scanner.nextLine().split("-");
                    var l = new Factura.Linie(linie[0], Double.parseDouble(linie[1].trim()), Integer.parseInt(linie[2].trim()));
                    f.adaugareLinie(l);
                }

                facturiCitite.add(f);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Nu exista fisierul");
        }
    }

    public static void genereazaFacturi(LocalDate data, int n)
    {
        String[] denumiriClienti = new String[]{
                "ALCOR CONSTRUCT SRL",
                "SC DOMINO COSTI SRL",
                "SC TRANSCRIPT SRL",
                "SIBLANY SRL",
                "INTERFLOOR SYSTEM SRL",
                "MERCURY  IMPEX  2000  SRL",
                "ALEXANDER SRL",
                "METAL INOX IMPORT EXPOSRT SRL",
                "EURIAL BROKER DE ASIGURARE SRL"
        };

        String[] denumiriProduse = new String[]{
                "Stafide 200g",
                "Seminte de pin 300g",
                "Bulion Topoloveana 190g",
                "Paine neagra Frontera",
                "Ceai verde Lipton"

        };

        double[] preturiProduse = new double[]{
                5.20,
                12.99,
                6.29,
                4.08,
                8.99
        };

        Random rand = new Random();
        var nrZile = (int) ChronoUnit.DAYS.between(data, LocalDate.now());
        for(int i = 0 ; i < n ; i++)
        {
            var nume = denumiriClienti[rand.nextInt(denumiriClienti.length - 1)];
            var dataa = data.plusDays(rand.nextInt(nrZile ));
            var f = new Factura(nume, dataa);
            for(int j = 0; j < rand.nextInt(10); j ++)
            {
                var denumire = denumiriProduse[rand.nextInt(denumiriProduse.length - 1)];
                var pret = preturiProduse[rand.nextInt(preturiProduse.length -1)];
                var cant = rand.nextInt(19);
                var l = new Factura.Linie(denumire, pret, cant);

                f.adaugareLinie(l);
            }

            facturi.add(f);
        }

    }

    public static void afisare(List<Factura> facturi)
    {
        for (var f : facturi)
        {
            System.out.println(f);
        }
    }

    public static void salvare(String nume)
    {
        File file = new File(nume);
        try ( var writer = new PrintWriter(file))
        {
            for(var f : facturi)
            {
                writer.println(f.getNumeClient());
                writer.println(f.getData());
                writer.println(f.getLinii().size());
                for(var l : f.getLinii())
                {
                    writer.println(l);
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Nu a functionat ");
        }
    }

}

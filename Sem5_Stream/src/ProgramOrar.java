import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProgramOrar {

    private static Map<Integer, Profesori> profesori = new HashMap<>();
    private static List<Programari> programari = new ArrayList<>();

    public static void citireProfesori() throws FileNotFoundException {
        FileReader file = new FileReader("data\\profesori.txt");
        try (var buffer = new BufferedReader(file)) {
            profesori = buffer.lines()
                    .map(linie -> new Profesori(
                            Integer.parseInt(linie.split("\t")[0]),
                            linie.split("\t")[1],
                            linie.split("\t")[2],
                            linie.split("\t")[3])
                    )
                    .collect(Collectors.toMap(prof -> prof.getId(), prof -> prof));
        } catch (IOException e) {
            System.err.println("Nu se gaseste fisierul");
        }
    }

    public static void citireProgramari() throws FileNotFoundException {
        FileReader file = new FileReader("data\\programari.txt");
        try (var buffer = new BufferedReader(file)) {
            programari = buffer.lines()
                    .map(linie -> new Programari(
                            linie.split("\t")[0],
                            linie.split("\t")[1],
                            profesori.get(Integer.parseInt(linie.split("\t")[2])),
                            linie.split("\t")[3],
                            linie.split("\t")[4],
                            Boolean.parseBoolean(linie.split("\t")[5]),
                            linie.split("\t")[6]
                    ))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Nu s a gasit fisierul");
        }
    }

    public static void afisareProfesori(Map<Integer, Profesori> profesori) {
        for (var p : profesori.entrySet()) {
            System.out.println(p);
        }
    }

    public static void afisareProgramari(List<Programari> prog) {
        for (var p : prog) {
            System.out.println(p);
        }
    }

    public static void GenerareCursuri() {
        record curs(String nume, String numeProfesor) {
        }
        ;
        List<curs> cursuri = programari.stream()
                .filter(Programari::isEsteCurs)
                .map(curs -> new curs(
                        curs.getDisciplina(),
                        curs.getProfesor(curs.getDisciplina())
                ))
                .sorted((c1, c2) -> c1.nume.compareTo(c2.nume))
                .collect(Collectors.toList());

        cursuri.stream()
                .forEach(c -> System.out.println(
                        c.nume + "-" + c.numeProfesor
                ));

    }


    public static void main(String[] args) throws FileNotFoundException {
        citireProfesori();
        //afisareProfesori(profesori);
        citireProgramari();
        // afisareProgramari(programari);
        //GenerareCursuri();

        programari.stream()
                .collect(Collectors.groupingBy(Programari::getProfesor))
                .forEach((prof, prog) ->
                        {
                        System.out.printf("%s - %d cursuri si %d seminarii \n",
                                prof.getNume(),
                                prog.stream()
                                        .filter(Programari::isEsteCurs).count(),
                                prog.stream()
                                        .filter(p -> p.isEsteCurs() == false).count());

                        });

       NrActiv();



    }
    public static void NrActiv()
    {
        record dep(String nume, long activitati){}

       List<dep> departamente =  programari.stream()
                .map(programare -> programare.getProfesor().getDepartament())
                .distinct()
                .map( denumire ->
                {
                    var nrActivitati = programari.stream()
                            .filter(programare -> programare.getProfesor().getDepartament().equals(denumire))
                            .count();

                    return new dep(denumire, nrActivitati);
                })
                .collect(Collectors.toList());

        departamente.stream()
                .sorted((dep1, dep2)-> - Long.compare(dep1.activitati(), dep2.activitati()))
                .forEach(dep ->
                {
                    System.out.printf("%s - %d activitati\n", dep.nume, dep.activitati);
                });
    }


}





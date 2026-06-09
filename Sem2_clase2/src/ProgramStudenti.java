import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class ProgramStudenti {

   private static Scanner scanner ;
   private static Student[] studenti = new Student[0];

    public static void main (String[] args)
    {
        File file = new File("data.txt");
        try {
         scanner = new Scanner(file);
            citireStudenti();
            afisareStudenti("Initial", studenti);
            citireCatalog();
            afisareStudenti("Dupa SD", studenti);
            citireCatalog();
            afisareStudenti("Dupa Java", studenti);
            afisareCatalog("Structuri de date");
            afisareCatalog("Programare Java");
        }
        catch (FileNotFoundException e)
        {
            System.err.println("Fisierul nu exista");
        }

    }

    public static void citireStudenti()
    {
        var nr = Integer.parseInt(scanner.nextLine().trim());
       studenti = new Student[nr];
       for(int i = 0; i < nr; i ++)
       {
           String linieStudent = scanner.nextLine();
           String linieNota = scanner.nextLine();

           var s = new Student(
                   Integer.parseInt(linieStudent.split(",")[0]),
                   linieStudent.split(",")[1],
                   Integer.parseInt(linieStudent.split(",")[2]),
                   Integer.parseInt(linieStudent.split(",")[3])

           );

           var elemNote = linieNota.split(",");
           for( int j = 0; j < elemNote.length - 1; j = j + 2)
           {
               var disciplina = elemNote[j];
               var not = Integer.parseInt(elemNote[j + 1]);
               Nota nota = new Nota(disciplina, not);

               s.add(nota);

           }

           studenti[i] = s;

       }
    }

    public static void afisareStudenti (String mesaj, Student[] s)
    {
        System.out.println(mesaj);
        for( var st : s)
        {
            System.out.println(st);
        }
        System.out.println("-------------------------------------------------------");
    }

    public static void citireCatalog()
    {
        var nume = scanner.nextLine().trim();
        var nrNote = Integer.parseInt( scanner.nextLine());

        buclaStudenti:
        for( int i = 0 ; i < nrNote; i++)
        {
            var linie = scanner.nextLine().split(",");
            var id = Integer.parseInt(linie[0]);
            var nota = Integer.parseInt(linie[1]);
            for( var s : studenti)
            {
                if( s.getIdstudent() == id) {
                    var n = new Nota(nume, nota);
                    s.add(n);
                    continue buclaStudenti;

                }
            }

            System.err.println("Cod Student Invalid #" + id);
        }
    }

    public static void afisareCatalog(String numeDisciplina)
    {
        class elemCatalog
        {
            public String nume;
            public double nota;

            public elemCatalog(String nume, double nota) {
                this.nume = nume;
                this.nota = nota;
            }

            public String getNume() {
                return nume;
            }

            public void setNume(String nume) {
                this.nume = nume;
            }

            public double getNota() {
                return nota;
            }

            public void setNota(double nota) {
                this.nota = nota;
            }

            @Override
            public String toString() {
                return
                        "nume='" + nume + '\'' +
                        ", nota=" + nota ;
            }
        }

        var nrElem = 0;
        for( var s : studenti)
        {
            for( var n : s.getNote())
            {
                if( n.getNumeDisciplina().equals(numeDisciplina))
                {
                    nrElem ++;
                }
            }
        }

        elemCatalog[] Catalog = new elemCatalog[nrElem];
        int i = 0;
        for( var s : studenti)
        {
            for(var n : s.getNote())
            {
                if(n.getNumeDisciplina().equals(numeDisciplina))
                {
                    var e =new  elemCatalog( s.getNume(), n.getNota()) ;
                    Catalog[i] = e;
                    i++;

                }
            }
        }

        Arrays.sort(Catalog, new Comparator<elemCatalog>() {
            @Override
            public int compare(elemCatalog o1, elemCatalog o2) {
                return Double.compare(o1.getNota(), o2.getNota());
            }
        });
        System.out.println("Catalog" + numeDisciplina);
        for( var e : Catalog)
        {
            System.out.println(e);
        }
    }



}

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ProgramStudent {

    public static void afisareCatalog(List<Student> studenti, String numeDisciplina)
    {
        System.out.println("Catalog" + numeDisciplina);
        for( Student s : studenti)
        {
            if( s.areNota(numeDisciplina)) {

                System.out.println( s.getNume() + " :" + s.GetNota(numeDisciplina));
            }
            else
            {
                System.out.println(s.getNume() + ": " +"ABS");
            }
        }
    }


    public static void main (String[] args)
    {
        Student s = new Student("Popescu Mirel");
        s.addNota("PAW", 7);
        s.addNota("Java", 10);
        s.addNota("Java", 9);

        Student s2 = new Student("Ion Ionel Ionut");
        s2.addNota("SDD", 7);
        s2.addNota("Java", 10);
//        System.out.println(s);
//        System.out.println(s2);

        s.StergeNota("PAW");
//        System.out.println(s);
//
        Student s3 = new Student("Ana");
        s3.addNota("SDD", 3);
        s3.addNota("Java", 10);

        List<Student> studenti = new ArrayList<>();
        studenti.add(s);
        Collections.addAll(studenti, s2, s3 );

//        for(String dis : s2.getDiscipline())
//        {
//            System.out.print(dis + " ->");
//            if(s2.areNota(dis))
//            {
//              System.out.println(s2.GetNota(dis));
//            }
//            else
//            {
//                System.out.println("ABS");
//            }
//        }

        afisareCatalog(studenti, "SDD");

       Student s4 =  s2.clone();
       s4.addNota("POO", 10);
       System.out.println(s2);
       System.out.println(s4);

    }

}

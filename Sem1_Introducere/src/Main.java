public class Main {

    public static void main ( String[] args)
    {
        Student s = new Student();
        System.out.println(s.getNota());
        s.setNota(6);
        System.out.println(s.getNota());

        Student s2 = new Student("Maria", 7);
        System.out.println(s2.getNume());
        Student s3 = s2;
        System.out.println(s3.getNume());
        s3.setNume("Ion");
        System.out.println(s2.getNume());
        System.out.println(s3.getNume());
        Student s4 = s2.copiaza();
        System.out.println(s4.getNume());
        s2.setNume("Ionel");
        System.out.println(s2.getNume());
        System.out.println(s4.getNume());
        s4.setNume("George");
        System.out.println(s2.getNume());
        System.out.println(s4.getNume());
    }
}

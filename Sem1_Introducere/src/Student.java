public class Student {

    private String nume;
    private double nota;

    public String getNume() {
        return nume;
    }

    public double getNota() {
        return nota;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public Student() {
        nume ="";
        nota = 0;
    }

    public Student(String nume, double nota) {
        this.nume = nume;
        this.nota = nota;
    }

    public Student copiaza()
    {
        Student s = new Student();
        s.nota = this.nota;
        s.nume = this.nume;
        return s;
    }


}

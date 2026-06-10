import java.util.Objects;

public class Programari {

    private String ziua;
    private String interval;
    private Profesori profesor;
    private String disciplina;
    private String sala;
    private boolean esteCurs;
    private String Formatie;

    public String getZiua() {
        return ziua;
    }

    public void setZiua(String ziua) {
        this.ziua = ziua;
    }

    public String getFormatie() {
        return Formatie;
    }

    public void setFormatie(String formatie) {
        Formatie = formatie;
    }

    public boolean isEsteCurs() {
        return esteCurs;
    }

    public void setEsteCurs(boolean esteCurs) {
        this.esteCurs = esteCurs;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Profesori getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesori profesor) {
        this.profesor = profesor;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Programari(String ziua,String interval,Profesori profesor, String disciplina, String sala, boolean esteCurs , String formatie) {
        this.ziua = ziua;
        Formatie = formatie;
        this.esteCurs = esteCurs;
        this.sala = sala;
        this.disciplina = disciplina;
        this.interval = interval;
        this.profesor = profesor;
    }

    @Override
    public String toString() {
        return "Programari{" +
                "ziua='" + ziua + '\'' +
                ", interval='" + interval + '\'' +
                ", profesor=" + profesor +
                ", disciplina='" + disciplina + '\'' +
                ", sala='" + sala + '\'' +
                ", esteCurs=" + esteCurs +
                ", Formatie='" + Formatie + '\'' +
                '}';
    }

    public String getProfesor(String nume)
    {
        if(disciplina.equals(nume))
        {
            return profesor.getNume();
        }
        else
        {
            return "-";
        }
    }


}

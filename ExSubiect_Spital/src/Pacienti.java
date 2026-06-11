public class Pacienti {

    private long CNP;
    private String NumePacient;
    private int varstaPacient;
    private int CodSectie;

    public long getCNP() {
        return CNP;
    }

    public void setCNP(long CNP) {
        this.CNP = CNP;
    }

    public String getNumePacient() {
        return NumePacient;
    }

    public void setNumePacient(String numePacient) {
        NumePacient = numePacient;
    }

    public int getVarstaPacient() {
        return varstaPacient;
    }

    public void setVarstaPacient(int varstaPacient) {
        this.varstaPacient = varstaPacient;
    }

    public int getCodSectie() {
        return CodSectie;
    }

    public void setCodSectie(int codSectie) {
        CodSectie = codSectie;
    }

    public Pacienti(long CNP, String numePacient, int varstaPacient, int codSectie) {
        this.CNP = CNP;
        NumePacient = numePacient;
        this.varstaPacient = varstaPacient;
        CodSectie = codSectie;
    }

    @Override
    public String toString() {
        return "Pacienti{" +
                "CNP=" + CNP +
                ", NumePacient='" + NumePacient + '\'' +
                ", varstaPacient=" + varstaPacient +
                ", CodSectie=" + CodSectie +
                '}';
    }
}

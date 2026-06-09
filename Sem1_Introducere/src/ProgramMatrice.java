import java.util.Scanner;

public class ProgramMatrice {

    static double[][] citire()
    {
        var scanner = new Scanner(System.in);
        var mat = new double[scanner.nextInt()][scanner.nextInt()];
        for(int linie = 0; linie < mat.length ; linie ++)
        {
            for (int coloana = 0; coloana < mat[linie].length ; coloana ++)
            {
                System.out.printf("mat[%d][%d] = ", linie, coloana);
                mat[linie][coloana] = scanner.nextDouble();
                System.out.println();
            }
        }
        System.out.println();
        return mat;

    }

    static void afisare(String mesaj, double[][] mat)
    {
        for(int linie = 0; linie < mat.length ; linie ++) {
            for (int coloana = 0; coloana < mat[linie].length; coloana++) {
                System.out.print(mat[linie][coloana] + " ");
            }
            System.out.println();
        }
    }

    static double[][] transpusa(double[][] mat)
    {
        var rezultat = new double[mat[0].length][mat.length];
        for(int i = 0 ; i < mat.length; i ++)
        {
            for ( int j = 0; j < mat[i].length; j++)
            {
                rezultat[j][i] = mat[i][j];
            }
        }
        return rezultat;
    }


    public static void main ( String[] args)
    {
        var mat = citire();
        afisare("Afisare initiala", mat);
        var mat2 = transpusa(mat);
        afisare("Transpusa", mat2);
    }
}

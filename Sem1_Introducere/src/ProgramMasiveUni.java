import java.util.Arrays;

public class ProgramMasiveUni {

    static void afisare ( String mesaj, int[] vector)
    {
        System.out.println(mesaj);
        for(int element : vector)
        {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    static void incrementare (int[] vector)
    {
        for(int i = 0 ; i < vector.length; i++)
        {
            vector[i] = vector[i] + 1;
        }
    }

     static int[] adaugareInceput(int[] vector, int valoare)
     {
         var rezultat = new int[vector.length + 1];
         rezultat[0] = valoare;
         System.arraycopy(vector, 0, rezultat, 1, vector.length);
         return rezultat;
     }

     static int[] eliminareImpare(int[] vector)
    {
        int k = 0;
        for(int element : vector)
        {
            if(element % 2 == 0)
            {
                k++;
            }
        }

        var rezultat = new int[k];
        int i = 0;
        for(int element : vector)
        {
            if(element % 2 == 0)
            {
                rezultat[i] = element;
                i++;
            }
        }

        return rezultat;

    }


    public static void main (String[] args)
    {
        String[] valori = args[0].split(",");
        int[] vector = new int[valori.length];
        for(int i = 0; i < valori.length ; i ++)
        {
            vector[i] = Integer.parseInt(valori[i].trim());
        }

        afisare("Afisare vector initial", vector);
        incrementare(vector);
        afisare("Afisare vector dupa incrementare", vector);
        var vector2 = adaugareInceput(vector, 10);
        afisare("Afisare dupa adaugare la inceput", vector2);
        var vectorPare = eliminareImpare(vector2);
        afisare("Vector elemente pare", vectorPare);
    }
}

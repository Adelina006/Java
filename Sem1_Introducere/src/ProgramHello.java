import java.util.Scanner;

public class ProgramHello {

    public static void main (String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nume :");
        String nume = scanner.nextLine();
        System.out.println("Hello " + nume + "!");

        //suma a 2 intregi
        System.out.print("a=");
        int a = scanner.nextInt();

        System.out.print("b=");
        int b = scanner.nextInt();

        System.out.printf( "%d + %d  = %d", a, b, a+b);
    }

}

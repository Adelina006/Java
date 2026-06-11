import java.sql.*;

public class ProgramTabela {

    public static void main(String[] args)
    {
        String url = new String("jdbc:sqlite:MateriiPrime.db");
        try {
            Connection con = DriverManager.getConnection(url);
            Statement stm = con.createStatement();
            String creare = "Create table MateriiPrime(Cod Integer Primary key," +
                    "Denumire Text Not Null," +
                    "Cantitate Real," +
                    "Pret_unitar Real," +
                    "Unitate_masura Text);";

            stm.execute(creare);

            String adaugare = "insert into MateriiPrime values (?, ?, ?,?,?)";

         try(   PreparedStatement pstmt = con.prepareStatement(adaugare))
         {
             pstmt.setInt(1, 1);                  // Primul '?' -> Cod
             pstmt.setString(2, "Faina 650");     // Al doilea '?' -> Denumire
             pstmt.setDouble(3, 10000.0);         // Al treilea '?' -> Cantitate
             pstmt.setDouble(4, 4.0);             // Al patrulea '?' -> Pret_unitar
             pstmt.setString(5, "kg");            // Al cincilea '?' -> Unitate_masura
             pstmt.executeUpdate();               // Tragem trăgaciul și salvăm rândul în fișier

             // Inserăm rândul 2 din exemplul subiectului (Zahăr)
             pstmt.setInt(1, 2);
             pstmt.setString(2, "Zahar");
             pstmt.setDouble(3, 100.0);
             pstmt.setDouble(4, 3.5);
             pstmt.setString(5, "kg");
             pstmt.executeUpdate();

             // Mai adăugăm 2 materii prime fictive (Sare și Drojdie) ca să avem date complete pentru rețete
             pstmt.setInt(1, 3);
             pstmt.setString(2, "Sare");
             pstmt.setDouble(3, 500.0);
             pstmt.setDouble(4, 1.5);
             pstmt.setString(5, "kg");
             pstmt.executeUpdate();

             pstmt.setInt(1, 4);
             pstmt.setString(2, "Drojdie");
             pstmt.setDouble(3, 200.0);
             pstmt.setDouble(4, 8.0);
             pstmt.setString(5, "kg");
             pstmt.executeUpdate();
         }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}

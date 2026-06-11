import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Program {

    private static List<Produse> produse = new ArrayList<>();
    private static List<MateriiPrime> materii = new ArrayList<>();

    public static void main(String[] args) throws IOException, SQLException, ParserConfigurationException, TransformerException {
        citireProduse();
        afisareProduse();
        citireMaterii();;
        afisareMaterii();
        valoareMaterii();
        sortareProdus();
        scriereFisier();
        scriereJson();

    }

    public static void citireProduse() throws IOException {
        String continut = new String(Files.readAllBytes(Paths.get("produse.json")));

        JSONArray materii = new JSONArray(continut);
        for(int i = 0 ; i < materii.length(); i++)
        {
            JSONObject obj = materii.getJSONObject(i);
            int cod = obj.getInt("Cod produs");
            String nume = obj.getString("Denumire produs");
            JSONArray consum = obj.getJSONArray("Consumuri");
            int cantitate = obj.getInt("Cantitate");
            String unitate = obj.getString("Unitate masura");
            Produse p = new Produse(cod, nume, cantitate, unitate);
            for(int j = 0; j < consum.length(); j ++)
            {
                JSONObject con = consum.getJSONObject(j);
                int codCons = con.getInt("Cod materie prima");
                int cant = con.getInt("Cantitate");
                Consumuri c = new Consumuri(codCons, cant);
                p.getConsumuri().add(c);

            }

            produse.add(p);

        }

    }

    public static void afisareProduse()
    {
        for(var p : produse)
        {
            System.out.println(p);
        }
    }

    public static void citireMaterii() throws SQLException {
        String url = "jdbc:sqlite:MateriiPrime.db";
        Connection con = DriverManager.getConnection(url);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from MateriiPrime");
        while(rs.next())
        {
            int cod = rs.getInt("Cod");
            String nume = rs.getString("Denumire");
            double cant = rs.getDouble("Cantitate");
            double pret = rs.getDouble("Pret_unitar");
            String unitate = rs.getString("Unitate_masura");

            MateriiPrime m = new MateriiPrime(cod, nume, cant, pret, unitate);
            materii.add(m);
        }
    }

    public static void afisareMaterii()
    {
        for( var m : materii)
        {
            System.out.println(m);
        }
    }

    public static void valoareMaterii()
    {
        double val = materii.stream()
                .mapToDouble(m -> m.getCantitate() * m.getPret_unitar())
                .sum();

        System.out.printf("VAloarea toata a materiilor este %.2f\n", val);
    }

    public static void sortareProdus()
    {
        record linie(String nume, int nr){}

        List<linie> raport = produse.stream()
                .map(p ->
                {
                    String nume = p.getDenumireProdus();
                    int nr = (int) p.getConsumuri().stream()
                            .count();
                    linie l = new linie(nume, nr);
                    return l;
                })
                .collect(Collectors.toList());

        raport.stream()
                .sorted((l1, l2) -> - Integer.compare(l1.nr, l2.nr))
                .forEach(l ->
                {
                    System.out.println(l.nume + "     "+ l.nr);
                });
    }

    public static void scriereFisier() throws ParserConfigurationException, TransformerException {
        record materie(int cod, String denumire, double valoare){}

        List<materie> materiiL = materii.stream()
                .map(m ->
                {
                    int cod = m.getCod();
                    String nume = m.getNume();
                    int catFolosit = produse.stream()
                            .mapToInt(p ->
                            {
                                int cantLoc = p.getConsumuri().stream()
                                        .filter(c -> c.getCod() == m.getCod())
                                        .mapToInt(c-> (int) c.getCantitate())
                                        .sum();
                                return cantLoc;

                            })
                            .sum();
                    int ramas = (int) (m.getCantitate() - catFolosit);
                    double rez =  ramas * m.getPret_unitar();

                    materie mat = new materie(cod, nume, rez);
                    return mat;
                })
                .collect(Collectors.toList());

        DocumentBuilderFactory fac = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = fac.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element radacina = doc.createElement("materii_prime");
        doc.appendChild(radacina);
        for(var l : materiiL)
        {
            Element m = doc.createElement("materie_prima");
            m.setAttribute("cod", String.valueOf(l.cod));
            m.setAttribute("denumire", l.denumire);
            m.setAttribute("valoare", String.valueOf(l.valoare));
            radacina.appendChild(m);
        }

        TransformerFactory f = TransformerFactory.newDefaultInstance();
        Transformer t = f.newTransformer();

        t.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource sursa = new DOMSource(doc);
        StreamResult s = new StreamResult("stoc.xml");

        t.transform(sursa, s);


    }

    public static void scriereJson()
    {
        JSONArray lista = new JSONArray();
         for( var p : produse)
         {
             JSONObject obj = new JSONObject();
             obj.put("Cod", p.getCodProdus());
             obj.put("Denumire", p.getDenumireProdus());
             JSONArray con = new JSONArray();
             for(var c : p.getConsumuri())
             {
                 JSONObject co = new JSONObject();
                 co.put("Cod Materie", c.getCod());
                 co.put("Cantitate", c.getCantitate());
                 con.put(co);
             }
             obj.put("Materii", con);
             obj.put("Cantitate", p.getCantitateProdus());
             obj.put("Unitate", p.getUnitateProdus());

             lista.put(obj);
         }

        try (FileWriter writer = new FileWriter("fisierJSON.json"))
        {
            writer.write(lista.toString(4));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




}

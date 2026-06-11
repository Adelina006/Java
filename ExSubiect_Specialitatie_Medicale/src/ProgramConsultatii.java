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
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramConsultatii {

    private static List<Specialitate> specialitatiList = new ArrayList<>();
    private static List<Consultatii> consultatii = new ArrayList<>();


    public static void main(String[] args) throws IOException, SQLException, ParserConfigurationException, TransformerException {
        CitireSpecializari();
        afisareSpecialitati();
        citireConsultatii();
        afisareConsultatii();
        afisareSpecialitateDupaDurata();
        afiareVenit();
        DocumentXML();
    }

    public static void CitireSpecializari() throws IOException {
        String continut = new String(Files.readAllBytes(Paths.get("medicale.json")));
        JSONArray specializari = new JSONArray(continut);
        for(int i=0; i < specializari.length(); i++)
        {
            JSONObject obj = specializari.getJSONObject(i);
            var nume = obj.getString("specialitate");
            Specialitate s= new Specialitate(nume);
            JSONArray manevre = new JSONArray(obj.getJSONArray("manevre"));
            for( int j = 0; j < manevre.length(); j ++)
            {
                JSONObject manevra = manevre.getJSONObject(j);
                int cod = manevra.getInt("cod");
                int durata = manevra.getInt("durata");
                double tarif = manevra.getDouble("tarif");
                Manevra m = new Manevra(cod, durata, tarif);
                s.getManevre().add(m);
            }

            specialitatiList.add(s);
        }

    }

    public static void afisareSpecialitati()
    {
        for(var s : specialitatiList)
        {
            System.out.println(s);
        }
    }

    public static void citireConsultatii() throws SQLException {
        String url = "jdbc:sqlite:consultatii.db";
        Connection conn = DriverManager.getConnection(url);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select * from Consultatii");
        while(rs.next())
        {
            var nume = rs.getString("Specialitate");
            int cod = rs.getInt("CodManevra");
            int nr = rs.getInt("Numar");

            Consultatii c = new Consultatii(nume, cod, nr);

            consultatii.add(c);
        }

    }

    public static void afisareConsultatii()
    {
        for(var c : consultatii)
        {
            System.out.println(c);
        }
    }

    //Să se afișeze la consolă, pentru fiecare specialitate medicala, manevrele disponibile in ordine descrescatoare
    //a duratei, în urmatorul format:

    public static void afisareSpecialitateDupaDurata()
    {
        System.out.printf("Specialitate     Cod           Durata\n");
        for( Specialitate s : specialitatiList)
        {
            s.getManevre().stream()
                    .sorted((s1, s2) -> Integer.compare(s1.getDurata(), s2.getDurata()))
                    .forEach(m ->
                    {
                        System.out.printf("%-15s  %d           %d\n", s.getDenumire(), m.getCod(), m.getDurata());
                    });
        }

    }

    //Să se afișeze la consolă specialitatile medicale sortate descrescator dupa veniturile generate de manevrele
    //efectuate pentru fiecare specialitate, in formatul:

    public static void afiareVenit()
    {
        System.out.println("Specialitate       Valoare Generata\n");
       record linie(String denumire, double valoare){}

        List<linie> raport = specialitatiList.stream()
                .map(s ->
                {
                   var val =  s.getManevre().stream()
                            .mapToDouble(m->
                            {
                                var tarif = m.getTarif();
                                var nr = consultatii.stream()
                                        .filter(c-> c.getCodManevra() == m.getCod())
                                        .mapToInt(c-> c.getNumar())
                                        .sum();
                                double valoare = tarif * nr;
                                return valoare;
                            })
                           .sum();
                   return new linie(s.getDenumire(), val);

                })
                .collect(Collectors.toList());

       raport.stream()
               .sorted((l1, l2) -> Double.compare(l1.valoare, l2.valoare))
               .forEach(l->
               {
                   System.out.printf("%-15s     %.2f\n", l.denumire, l.valoare);
               });


    }

    public static void DocumentXML() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory fac = DocumentBuilderFactory.newDefaultInstance();
        DocumentBuilder builder = fac.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element radacina = doc.createElement("medicale");
        doc.appendChild(radacina);

        List <Consultatii> cons;

        for(var s : specialitatiList)
        {
            cons = consultatii.stream()
                    .filter(c-> c.getSpecialitate().equals(s.getDenumire())  && c.getNumar() >= 20)
                    .collect(Collectors.toList());

            Element sp = doc.createElement("Specialitate");
            radacina.appendChild(sp);
            Element spe  = doc.createElement(s.getDenumire());
            sp.appendChild(spe);
            Element manevre = doc.createElement("manevre");
            sp.appendChild(manevre);
            for(var c : cons)
            {
                Element co = doc.createElement("manevra");
                co.setAttribute("cod", String.valueOf(c.getCodManevra()));
                co.setAttribute("numar", String.valueOf(c.getNumar()));
                manevre.appendChild(co);
            }

            TransformerFactory fact = TransformerFactory.newDefaultInstance();
            try {
                Transformer t = fact.newTransformer();
                t.setOutputProperty(OutputKeys.INDENT, "yes");
                t.setOutputProperty(OutputKeys.STANDALONE, "no");

                DOMSource sursa = new DOMSource(doc);
                StreamResult res = new StreamResult(new File("manevreMedicale.xml"));
                t.transform(sursa, res);


            } catch (TransformerConfigurationException e) {
                throw new RuntimeException(e);
            }


        }


    }


}

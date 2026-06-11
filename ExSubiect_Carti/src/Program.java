import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Program {
    private static List<Preturi> preturi = new ArrayList<>();
   private static  List<Titltu> titluri = new ArrayList<>();

    public Program() throws ParserConfigurationException, IOException, SAXException {
    }

    public static void main(String[] args) throws SQLException {
        citirePreturi();
        afisarePreturi();
        citireTitluri();
        afisareTitluri();
        TitluMin();
        afisaresortata();
        afisareDiferenta();
        cerinta4();

    }

    public static void citirePreturi()
    {
        try {
            BufferedReader buffer =  new BufferedReader(new FileReader("Pretvolum.txt"));
            buffer.lines()
                    .skip(1)
                    .forEach(l ->
                    {
                        String[] linie = l.split(",");

                        String simbol = linie[0];
                        double deschidere = Double.parseDouble(linie[1]);
                        double max = Double.parseDouble(linie[2]);
                        double min = Double.parseDouble(linie[3]);
                        double inchidere = Double.parseDouble(linie[4]);
                        long volum = Long.parseLong(linie[5]);

                        Preturi p = new Preturi(simbol, deschidere, min, max, inchidere, volum);
                        preturi.add(p);
                    });

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static void afisarePreturi()
    {
        for(var p : preturi)
        {
            System.out.println(p);
        }
    }

    public static void citireTitluri() throws SQLException {
        String url = "jdbc:sqlite:Titluri.db";
        Connection con = DriverManager.getConnection(url);
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from Titluri");
        while(rs.next())
        {
            String simbol = rs.getString("Simbol");
            String denumire = rs.getString("Denumire");

            Titltu t = new Titltu(simbol, denumire);
            titluri.add(t);
        }

    }

    public static void afisareTitluri()
    {
        for(var t : titluri)
        {
            System.out.println(t);
        }
    }

    public static void TitluMin()
    {
        record linie(String simbol, double val){}
       List<linie> lista =  preturi.stream()
               .map(p->
               {
                   String simbol = p.getSimbol();
                   double val = p.getVolum() * p.getInchidere();

                   linie l = new linie(simbol, val);
                   return l;
               })
               .collect(Collectors.toList());

     Optional<linie> l = lista.stream()
                .max(Comparator.comparing(linie::val));

     linie l2 = l.get();
     System.out.println(l2.simbol + "    " + l2.val);


    }

    public static void afisaresortata()
    {
        record linie(String simbol, String denumire, long Vol){}

        List<linie> raport = titluri.stream()
                .map(t ->
                {
                    String simbol = t.getSimbol();
                    String denumire = t.getDeumire();
                    long volum = preturi.stream()
                            .filter(p -> p.getSimbol().equals(simbol))
                            .mapToLong(p->  p.getVolum())
                            .sum();

                    linie l = new linie(simbol, denumire, volum);
                    return l;

                })
                .collect(Collectors.toList());

        raport.stream()
                .sorted((l1, l2) -> -Long.compare(l1.Vol, l2.Vol))
                .forEach(l->
                {
                    System.out.println(l.simbol + "     "+ l.denumire+ "     " + l.Vol );
                });

    }

    public static void afisareDiferenta()
    {
        record linie(String simbol, String denumire, double dif){}

        List<linie> raport = titluri.stream()
                .map(t->
                {
                    String simbol = t.getSimbol();
                    String denumire = t.getDeumire();
                    double dif = preturi.stream()
                            .filter(p-> p.getSimbol().equals(simbol))
                            .mapToDouble(p->
                            {
                                double d = p.getMax() - p.getMin();
                                return d;
                            })
                            .sum();
                    linie l = new linie(simbol, denumire, dif);
                    return l;

                })
                .collect(Collectors.toList());

        raport.stream()
                .filter(l-> l.dif > 1)
                .sorted((l1, l2) -> -Double.compare(l1.dif, l2.dif))
                .forEach(l->
                {
                    System.out.println(l.simbol + "     " + l.denumire + "     " + l.dif);
                });

    }

//client trimite simbol, server returneaza denumire companie, pret inchidere, volum

    public static void cerinta4()
    {
        record linie(String denumire, double inchidere, long vol){}
        Thread firServer = new Thread(()->
        {
            try {
                ServerSocket serverSocket = new ServerSocket(8887);

                while(true)
                {
                    Socket socket = serverSocket.accept();

                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream());

                    String simbol = in.readUTF();
                    System.out.println("[server] am primit simbolul " + simbol);


                   linie li = preturi.stream()
                            .filter(p -> p.getSimbol().equals(simbol))
                            .map(p ->
                            {
                                double inchidere = p.getInchidere();
                                long vol = p.getVolum();
                                String denumire = titluri.stream()
                                        .filter(t -> t.getSimbol().equals(simbol))
                                        .map(t -> t.getDeumire())
                                        .findFirst()
                                        .orElse("Necunoscut");

                                linie l = new linie(denumire, inchidere, vol);
                                return l;

                            })
                           .findFirst()
                           .orElse(null);

                   String rez = li.denumire+"   "+li.inchidere+"   "+li.vol;
                   out.writeUTF(rez);

                   socket.close();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Thread firClient = new Thread(() ->
        {
            try {
                Thread.sleep(1000);
                while(true) {

                    Socket socket = new Socket("localhost", 8887);
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("[client] Introduceti codul");
                    String cod = scanner.next();
                    out.writeUTF(cod);
                    out.flush();

                    String rez = in.readUTF();
                    System.out.println("[Client] Am primt   " + rez);

                    socket.close();

                }


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        firServer.start();
        firClient.start();
    }






















}

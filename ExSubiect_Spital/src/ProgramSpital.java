import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ProgramSpital {
private  static List<Pacienti> pacienti = new ArrayList<>();
private static List<Sectii> sectiiSpital = new ArrayList<>();

    public static void main (String[] args) throws IOException, SQLException {
        citireSectiiJSON();
        afisareSectii(sectiiSpital);
        citirePacientiBD();
        afisarePacienti(pacienti);
        System.out.println("===============Sectii cu mai mult de 5 locuri=======================");
        AfisareSectiiPeste5();
        System.out.println("===============Raport sectii=======================");
        System.out.printf("Cod         Sectie                NrPacienti\n");
        afisareRaport();
        scriereRaportFisierText();
        //cerinta5();
        cerinta6();

    }

    public static void citireSectiiJSON() throws IOException {
        String continut = new String(Files.readAllBytes(Paths.get("sectii.json")));

        JSONArray sectii = new JSONArray(continut);

        for(int i = 0; i < sectii.length(); i ++)
        {
            JSONObject obj = sectii.getJSONObject(i);

            int cod = obj.getInt("cod_sectie");
            String nume = obj.getString("denumire");
            int locuri = obj.getInt("numar_locuri");

            Sectii s = new Sectii(cod, nume, locuri);
            sectiiSpital.add(s);

        }

    }

    public static void citirePacientiBD() throws SQLException {
        String url = "jdbc:sqlite:spital.db";
        Connection conn = DriverManager.getConnection(url);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("Select * from Pacienti");
        while(rs.next())
        {
            long cnp = rs.getLong("CNP");
            String nume = rs.getString("NumePacient");
            int varsta = rs.getInt("VarstaPacient");
            int sectie = rs.getInt("CodSectie");

            Pacienti p = new Pacienti(cnp,nume, varsta, sectie);
            pacienti.add(p);

        }

    }

    public static void afisareSectii(List<Sectii> sectii)
    {
        for(var s : sectii)
        {
            System.out.println(s);
        }
    }

    public static void afisarePacienti(List<Pacienti> pacienti)
    {
        for( var p : pacienti)
        {
            System.out.println(p);
        }
    }

    //sectii cu numar de locuri mai mare de 5

    public static void AfisareSectiiPeste5()
    {
        sectiiSpital.stream()
                .filter(sectie -> sectie.getNumar_locuri() > 5)
                .forEach(sectii ->
                {
                    System.out.printf("Sectia % d - %s are %d locuri\n", sectii.getCod_sectie(), sectii.getDenumire(),sectii.getNumar_locuri());
                });
    }

    //raport => cod sectie, denumire sectie , numar pacienti

    public static void afisareRaport()
    {
        record linieRaport(int cod, String Denumire, int nrPacienti){}

        sectiiSpital.stream()
                .forEach(sectie ->
                {
                    int cod = sectie.getCod_sectie();
                    String denumire = sectie.getDenumire();
                    int nrPacienti = Math.toIntExact(pacienti.stream()
                            .filter(pacient -> Integer.compare(pacient.getCodSectie(), cod) == 0)
                            .count());

                    System.out.printf("%-5d  %-30s  are %d \n", cod, denumire, nrPacienti);

                });

    }

    //3) Să se scrie în fișierul text situatie.txt un raport cu secțiile spitalului sortate descrescător
    //după varsta medie a pacientilor internați pe secție. Pentru fiecare secție se va afișa codul, denumirea,
    //numărul de locuri și vârsta medie a pacienților, in formatul urmator:

    public static void scriereRaportFisierText()
    {
        record linie(int cod, String denumire, int locuri, Double medie){}
        List<linie> raport = sectiiSpital.stream()
                .map(sectie ->
                {
                    int cod = sectie.getCod_sectie();
                   String nume = sectie.getDenumire();
                   int locuri = sectie.getNumar_locuri();
                   Double medie = pacienti.stream()
                           .filter(pacient -> pacient.getCodSectie() == cod)
                           .mapToInt(pacient-> pacient.getVarstaPacient())
                           .average()
                           .orElse(0.0);


                   return new linie(cod, nume, locuri, medie);
                })
                .sorted((s1, s2) -> Double.compare(s1.medie, s2.medie))
                .collect(Collectors.toList());

        try (PrintWriter writer = new PrintWriter(new FileWriter("situatie.txt")))
        {
            writer.printf("Cod            Denumire  Nr Locuri  Varta Medie\n");
            for(var l : raport)
            {
                writer.printf("%d       %-20s      %3d     %5.2f\n", l.cod, l.denumire, l.locuri , l.medie);
            }

        } catch (IOException e) {
            System.err.printf("Eroare\n");
        }
    }

    public static void cerinta5()
    {
        Thread firServer = new Thread(() ->
        {
            try (ServerSocket serversocket = new ServerSocket(8888))
            {
                Socket socket = serversocket.accept();

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                int cod = in.readInt();
                System.out.println("[server] am primit codul" + cod);

                int numar = sectiiSpital.stream()
                        .filter(s -> s.getCod_sectie() == cod)
                        .mapToInt(s->
                        {
                            int locuri = s.getNumar_locuri();
                            int ocupate = Math.toIntExact(pacienti.stream()
                                    .filter(p -> p.getCodSectie() == cod)
                                    .count());
                            return locuri - ocupate;
                        }).sum();

                out.writeInt(numar);
                out.flush();

                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        Thread firClient = new Thread(() ->
        {
            try {
                Thread.sleep(1000);
                Socket socket = new Socket("localhost", 8888);

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                int cod = 1;
                out.writeInt(cod);
                out.flush();

                int numar = in.readInt();
                System.out.println("[Client] am primit de la server" + numar);


                socket.close();
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



    public static void cerinta6()
    {
        Thread firServer = new Thread(() ->
        {
            try (ServerSocket serversocket = new ServerSocket(8888))
            {
                while(true) {
                    Socket socket = serversocket.accept();

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    int cod = in.readInt();
                    System.out.println("[server] am primit codul" + cod);

                    int numar = sectiiSpital.stream()
                            .filter(s -> s.getCod_sectie() == cod)
                            .mapToInt(s ->
                            {
                                int locuri = s.getNumar_locuri();
                                int ocupate = Math.toIntExact(pacienti.stream()
                                        .filter(p -> p.getCodSectie() == cod)
                                        .count());
                                return locuri - ocupate;
                            }).sum();

                    out.writeInt(numar);
                    out.flush();

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
                    Socket socket = new Socket("localhost", 8888);

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    Scanner scanner = new Scanner(System.in);
                    System.out.println("[Client] introduceti codul");
                    int cod = scanner.nextInt();

                    out.writeInt(cod);
                    out.flush();

                    int numar = in.readInt();
                    System.out.println("[Client] am primit de la server" + numar);


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

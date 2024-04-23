package org.foi.uzdiz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.chainOfResponsibility.Upravitelj;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandeDV;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandeIP;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandePO;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandePP;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandePPV;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandePS;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandeQ;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandeSPV;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandeSV;
import org.foi.uzdiz.chainOfResponsibility.UpraviteljKomandeVR;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;
import org.foi.uzdiz.virtualnoVrijeme.VirtualnoVrijemeSingleton;

public class Main {

  static int brojSatiRada = 0;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    boolean izlazIzPrograma = false;

    if (args.length == 1) {
      if (provjeraDatotekeParametara(args[0])) {
        if (Files.exists(Paths.get(args[0]))) {
          Properties postavke = new Properties();
          Set<String> vidjeniKljucevi = new HashSet<>();
          try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String linija;

            while ((linija = reader.readLine()) != null) {
              String[] kljucVrijednost = linija.split("=", 2);
              if (kljucVrijednost.length == 2) {
                String kljuc = kljucVrijednost[0].trim();
                String vrijednost = kljucVrijednost[1].trim();

                if (vidjeniKljucevi.contains(kljuc)) {
                  UpraviteljGresakaSingleton.getInstance()
                      .greskaUlazneKomande("DATOTEKA PARAMETARA SADRŽI DUPLIKAT KLJUČA!");
                  return;
                }
                vidjeniKljucevi.add(kljuc);
                postavke.setProperty(kljuc, vrijednost);
              }
            }

            if (postavke.size() == 15) {
              ProvjeravateljArgumenta provjeravateljArgumenta = new ProvjeravateljArgumenta();
              ProvjeravateljParametara provjeravateljParametara =
                  new ProvjeravateljParametara(provjeravateljArgumenta);
              provjeravateljParametara.provjeriParametre(postavke);

              if (provjeravateljArgumenta.dohvatiBrojacGresaka() == 0) {
                TvrtkaSingleton.getInstance().ucitajDatoteke();
                VirtualnoVrijemeSingleton.getInstance()
                    .ucitajPocetnoVrijemeVs(TvrtkaSingleton.getInstance().dohvatiVrijednostVs());
              } else {
                scanner.close();
                return;
              }
            } else {
              UpraviteljGresakaSingleton.getInstance()
                  .greskaUlazneKomande("DATOTEKA PARAMETARA NE SADRŽI 15 PARAMETARA!");
              scanner.close();
              return;
            }
          } catch (IOException e) {
            UpraviteljGresakaSingleton.getInstance().sustavskaGreska(e);
          }

        } else {
          UpraviteljGresakaSingleton.getInstance()
              .greskaUlazneKomande("DATOTEKA PARAMETARA NE POSTOJI!");
          scanner.close();
          return;
        }
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaUlazneKomande("NAZIV DATOTEKE PARAMETARA JE U KRIVOM FORMATU!");
        scanner.close();
        return;
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande(
          "POTREBNO JE UNIJETI JEDAN ULAZNI ARGUMENT NAZIVA DATOTEKE PARAMETARA!");
      scanner.close();
      return;
    }

    unesiKomandu(izlazIzPrograma, scanner);
    scanner.close();
  }

  private static boolean provjeraDatotekeParametara(String datotekaParametara) {
    Pattern pattern = Pattern.compile("[A-Za-zčćšžđ0-9_-]+\\.txt");
    Matcher matcher = pattern.matcher(datotekaParametara);
    if (matcher.matches()) {
      return true;
    } else {
      return false;
    }
  }

  private static void unesiKomandu(Boolean izlazIzPrograma, Scanner scanner) {
    do {
      System.out.print("\nUnesite komandu tražene aktivnosti: \n");
      String spojenaKomanda = scanner.nextLine();

      Upravitelj upraviteljKomandeIP = new UpraviteljKomandeIP();
      Upravitelj upraviteljKomandeVR = new UpraviteljKomandeVR();
      Upravitelj upraviteljKomandeSV = new UpraviteljKomandeSV();
      Upravitelj upraviteljKomandePP = new UpraviteljKomandePP();
      Upravitelj upraviteljKomandePS = new UpraviteljKomandePS();
      Upravitelj upraviteljKomandePO = new UpraviteljKomandePO();
      Upravitelj upraviteljKomandeDV = new UpraviteljKomandeDV();
      Upravitelj upraviteljKomandeSPV = new UpraviteljKomandeSPV();
      Upravitelj upraviteljKomandePPV = new UpraviteljKomandePPV();
      Upravitelj upraviteljKomandeQ = new UpraviteljKomandeQ();

      upraviteljKomandeIP.postaviNasljednika(upraviteljKomandeVR);
      upraviteljKomandeVR.postaviNasljednika(upraviteljKomandeSV);
      upraviteljKomandeSV.postaviNasljednika(upraviteljKomandePP);
      upraviteljKomandePP.postaviNasljednika(upraviteljKomandePS);
      upraviteljKomandePS.postaviNasljednika(upraviteljKomandePO);
      upraviteljKomandePO.postaviNasljednika(upraviteljKomandeDV);
      upraviteljKomandeDV.postaviNasljednika(upraviteljKomandeSPV);
      upraviteljKomandeSPV.postaviNasljednika(upraviteljKomandePPV);
      upraviteljKomandePPV.postaviNasljednika(upraviteljKomandeQ);

      upraviteljKomandeIP.rukujZahtjevom(spojenaKomanda, spojenaKomanda.split(" ")[0], scanner);

    } while (!izlazIzPrograma);
  }

}

package org.foi.uzdiz.chainOfResponsibility;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.UpraviteljKomande;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class UpraviteljKomandeDV extends Upravitelj {

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("DV")) {
      provjeraKomandeDv(scanner);
    } else if (nasljednik != null) {
      nasljednik.rukujZahtjevom(komanda, prviDio, scanner);
    }
  }

  private static void provjeraKomandeDv(Scanner scanner) {
    System.out.println(
        "Unesite novo vozilo u sljedećem formatu: registracija;opis;kapacitetTezine;kapacitetProstora;redoslijed;prosjecnaBrzina;podrucjaPoRangu;status");
    String ulaz = scanner.nextLine();
    String regularniIzraz =
        "[A-Za-z0-9ČĆŠĐŽ]+;[A-Za-z0-9ČĆŠĐŽčćšđž\\-\\: ]+;\\d+(?:[.,]\\d+)?;\\d+(?:[.,]\\d+)?;[0-9]+;\\d+(?:[.,]\\d+)?;(\\d+(,\\d+)*);(A|NI|NA)";
    Pattern patternDv = Pattern.compile(regularniIzraz);
    Matcher matcherDv = patternDv.matcher(ulaz);

    if (matcherDv.matches()) {
      String[] komandaDv = ulaz.split(";");
      String registracija = komandaDv[0].trim();
      String opis = komandaDv[1].trim();
      String kapacitetTezine = komandaDv[2].trim();
      String kapacitetProstora = komandaDv[3].trim();
      String redoslijed = komandaDv[4].trim();
      String prosjecnaBrzina = komandaDv[5].trim();
      String podrucjaPoRangu = komandaDv[6].trim();
      String status = komandaDv[7].trim();
      UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
      upraviteljKomande.dodajVozilo(registracija, opis, kapacitetTezine, kapacitetProstora,
          redoslijed, prosjecnaBrzina, podrucjaPoRangu, status);
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA DV NIJE ISPRAVNA!");
    }
  }

}

package org.foi.uzdiz.chainOfResponsibility;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.UpraviteljKomande;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;
import org.foi.uzdiz.virtualnoVrijeme.VirtualnoVrijemeSingleton;

public class UpraviteljKomandePS extends Upravitelj {

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("PS")) {
      provjeraKomandePs(komanda);
    } else if (nasljednik != null) {
      nasljednik.rukujZahtjevom(komanda, prviDio, scanner);
    }
  }

  private void provjeraKomandePs(String komanda) {
    if (komanda.split(" ").length == 3) {
      Pattern patternPs = Pattern.compile(".*\\S$");
      Matcher matcherPs = patternPs.matcher(komanda);
      if (matcherPs.matches()) {
        LocalDateTime virtualnoVrijemePs =
            VirtualnoVrijemeSingleton.getInstance().trenutnoVirtualnoVrijeme;
        UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
        upraviteljKomande.izmjeniStatusVozila(komanda.split(" ")[1], komanda.split(" ")[2],
            virtualnoVrijemePs);
      } else {
        UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA PS NIJE ISPRAVNA!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance()
          .greskaUlazneKomande("KOMANDA PS MORA IMATI 3 PARAMETRA!");
    }
  }
}

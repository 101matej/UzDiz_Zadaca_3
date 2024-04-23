package org.foi.uzdiz.chainOfResponsibility;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.UpraviteljKomande;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class UpraviteljKomandePP extends Upravitelj {

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("PP")) {
      provjeraKomandePp(komanda);
    } else if (nasljednik != null) {
      nasljednik.rukujZahtjevom(komanda, prviDio, scanner);
    }
  }

  private void provjeraKomandePp(String komanda) {
    Pattern patternPp = Pattern.compile("PP");
    Matcher matcherPp = patternPp.matcher(komanda);
    if (matcherPp.matches()) {
      UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
      upraviteljKomande.ispisiPodrucjaMjestaUlice();
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA PP NIJE ISPRAVNA!");
    }
  }

}

package org.foi.uzdiz.chainOfResponsibility;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.UpraviteljKomande;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class UpraviteljKomandePO extends Upravitelj {

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("PO")) {
      provjeraKomandePo(komanda);
    } else if (nasljednik != null) {
      nasljednik.rukujZahtjevom(komanda, prviDio, scanner);
    }
  }


  private void provjeraKomandePo(String komanda) {
    String regularniIzraz = "PO '([^']+)' ([A-Za-z0-9ČĆŠĐŽčćšđž]+) ([ND])";
    Pattern patternPo = Pattern.compile(regularniIzraz);
    Matcher matcherPo = patternPo.matcher(komanda);

    if (matcherPo.matches()) {
      String osoba = matcherPo.group(1).trim();
      String oznakaPaketa = matcherPo.group(2);
      String status = matcherPo.group(3);
      UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
      upraviteljKomande.izmjeniStatusSlanjaObavijesti(osoba, oznakaPaketa, status);
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA PO NIJE ISPRAVNA!");
    }
  }
}

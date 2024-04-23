package org.foi.uzdiz.chainOfResponsibility;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.decorator.IspisComponent;
import org.foi.uzdiz.decorator.IspisOsnovneFunkcionalnosti;
import org.foi.uzdiz.decorator.IspisDodatneFunkcionalnosti;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;
import org.foi.uzdiz.virtualnoVrijeme.VirtualnoVrijemeSingleton;

public class UpraviteljKomandeIP extends Upravitelj {

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("IP")) {
      provjeraKomandeIp(komanda);
    } else if (nasljednik != null) {
      nasljednik.rukujZahtjevom(komanda, prviDio, scanner);
    }
  }

  private void provjeraKomandeIp(String komanda) {
    Pattern patternIp = Pattern.compile("IP");
    Matcher matcherIp = patternIp.matcher(komanda);
    if (matcherIp.matches()) {
      LocalDateTime virtualnoVrijeme =
          VirtualnoVrijemeSingleton.getInstance().trenutnoVirtualnoVrijeme;

      IspisComponent konkretniDecorator = new IspisDodatneFunkcionalnosti(new IspisOsnovneFunkcionalnosti());
      konkretniDecorator.ispisPrimljenihIDostavljenihPaketa(virtualnoVrijeme);

    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA IP NIJE ISPRAVNA!");
    }
  }

}

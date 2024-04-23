package org.foi.uzdiz.chainOfResponsibility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.memento.SpremnikStanjaCaretaker;
import org.foi.uzdiz.memento.SpremnikStanjaOriginator;
import org.foi.uzdiz.modeli.Vozilo;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;
import org.foi.uzdiz.virtualnoVrijeme.VirtualnoVrijemeSingleton;

public class UpraviteljKomandeSPV extends Upravitelj {

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("SPV")) {
      provjeraKomandeSpv(komanda);
    } else if (nasljednik != null) {
      nasljednik.rukujZahtjevom(komanda, prviDio, scanner);
    }
  }

  private void provjeraKomandeSpv(String komanda) {
    String regularniIzraz = "SPV '([^']+)'";
    Pattern patternSpv = Pattern.compile(regularniIzraz);
    Matcher matcherSpv = patternSpv.matcher(komanda);

    LocalDateTime virtualnoVrijeme =
        VirtualnoVrijemeSingleton.getInstance().trenutnoVirtualnoVrijeme;

    if (matcherSpv.matches()) {
      String naziv = matcherSpv.group(1).trim();
      SpremnikStanjaOriginator originator = TvrtkaSingleton.getInstance().dohvatiOriginator();
      SpremnikStanjaCaretaker caretaker = TvrtkaSingleton.getInstance().dohvatiCaretaker();
      Map<String, String> statusiVozila = new HashMap<>();

      for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
        statusiVozila.put(vozilo.dohvatiRegistraciju(), vozilo.dohvatiTrenutniStatus());
      }
      originator.postaviVirtualnoVrijeme(virtualnoVrijeme);
      originator.postaviStatusVozila(statusiVozila);

      caretaker.pohrani(naziv, originator.spremiStanjeUMemento());

      String virtualnoVrijemeString = virtualnoVrijemeUString(virtualnoVrijeme);

      System.out.println("TRENUTNO STANJE VIRTUALNOG VREMENA I STATUSA VOZILA JE SPREMLJENO!\n");
      System.out.println("TRENUTNO VIRTUALNO VRIJEME: " + virtualnoVrijemeString + "\n");
      System.out.println("TRENUTNA STANJA VOZILA:");

      for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
        System.out.println("Registracija: " + vozilo.dohvatiRegistraciju() + " Stanje: "
            + vozilo.dohvatiTrenutniStatus());
        System.out.println("=================================");
      }
      System.out.println();
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA SPV NIJE ISPRAVNA!");
    }
  }

  private String virtualnoVrijemeUString(LocalDateTime virtualnoVrijeme) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    String virtualnoVrijemeString = virtualnoVrijeme.format(formatter);
    return virtualnoVrijemeString;
  }

}

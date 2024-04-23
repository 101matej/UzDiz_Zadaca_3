package org.foi.uzdiz.chainOfResponsibility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.memento.SpremnikStanjaCaretaker;
import org.foi.uzdiz.memento.SpremnikStanjaMemento;
import org.foi.uzdiz.memento.SpremnikStanjaOriginator;
import org.foi.uzdiz.modeli.Vozilo;
import org.foi.uzdiz.state.KonkretnoStanjeAktivno;
import org.foi.uzdiz.state.KonkretnoStanjeNeaktivno;
import org.foi.uzdiz.state.KonkretnoStanjeNeispravno;
import org.foi.uzdiz.state.VoziloState;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;
import org.foi.uzdiz.virtualnoVrijeme.VirtualnoVrijemeSingleton;

public class UpraviteljKomandePPV extends Upravitelj {

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("PPV")) {
      provjeraKomandePpv(komanda);
    } else if (nasljednik != null) {
      nasljednik.rukujZahtjevom(komanda, prviDio, scanner);
    }
  }

  private void provjeraKomandePpv(String komanda) {
    String regularniIzraz = "PPV '([^']+)'";
    Pattern patternPpv = Pattern.compile(regularniIzraz);
    Matcher matcherPpv = patternPpv.matcher(komanda);

    if (matcherPpv.matches()) {
      String naziv = matcherPpv.group(1).trim();
      SpremnikStanjaOriginator originator = TvrtkaSingleton.getInstance().dohvatiOriginator();
      SpremnikStanjaCaretaker caretaker = TvrtkaSingleton.getInstance().dohvatiCaretaker();
      SpremnikStanjaMemento memento = caretaker.vrati(naziv);
      if (memento != null) {
        originator.dohvatiVirtualnoVrijemeIzMementa(memento);
        originator.dohvatiStanjaVozilaIzMementa(memento);

        VirtualnoVrijemeSingleton.getInstance()
            .postaviTrenutnoVirtualnoVrijeme(originator.dohvatiVirtualnoVrijeme());
        String virtualnoVrijemeString =
            virtualnoVrijemeUString(originator.dohvatiVirtualnoVrijeme());
        System.out.println("IZVRŠEN JE POVRATAK SPREMLJENOG STANJA!\n");
        System.out.println("TRENUTNO VIRTUALNO VRIJEME: " + virtualnoVrijemeString + "\n");
        System.out.println("TRENUTNA STANJA VOZILA:");
        ispisiRegistracijeIStatuseVozila(memento, originator);
        postaviStatusVozila(memento, originator);
        System.out.println();
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaURadu("PRETHODNO STANJE S NAVEDENIM NAZIVOM NE POSTOJI!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA PPV NIJE ISPRAVNA!");
    }
  }

  private String virtualnoVrijemeUString(LocalDateTime virtualnoVrijeme) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    String virtualnoVrijemeString = virtualnoVrijeme.format(formatter);
    return virtualnoVrijemeString;
  }

  public void ispisiRegistracijeIStatuseVozila(SpremnikStanjaMemento memento,
      SpremnikStanjaOriginator originator) {
    Map<String, String> statusiVozila = originator.dohvatiStanjaVozilaIzMementa(memento);

    for (Map.Entry<String, String> entry : statusiVozila.entrySet()) {
      String registracija = entry.getKey();
      String status = entry.getValue();

      System.out.println("Registracija: " + registracija + " Status: " + status);
      System.out.println("=================================");
    }
  }

  public void postaviStatusVozila(SpremnikStanjaMemento memento,
      SpremnikStanjaOriginator originator) {
    Map<String, String> statusiVozila = originator.dohvatiStanjaVozilaIzMementa(memento);
    for (Vozilo vozilo : TvrtkaSingleton.getInstance().listaVozila) {
      for (Map.Entry<String, String> entry : statusiVozila.entrySet()) {
        String registracija = entry.getKey();
        String status = entry.getValue();
        if (vozilo.dohvatiRegistraciju().equals(registracija)) {
          VoziloState noviStatus = odaberiStatus(status);
          vozilo.postaviStatus(noviStatus);
        }
      }
    }
  }

  private VoziloState odaberiStatus(String status) {
    switch (status) {
      case "A":
        return new KonkretnoStanjeAktivno();
      case "NI":
        return new KonkretnoStanjeNeispravno();
      case "NA":
        return new KonkretnoStanjeNeaktivno();
      default:
        throw new IllegalArgumentException("Nepodržan status: " + status);
    }
  }

}

package org.foi.uzdiz.decorator;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.foi.uzdiz.modeli.Paket;
import org.foi.uzdiz.uredi.UredZaDostavuSingleton;
import org.foi.uzdiz.uredi.UredZaPrijemSingleton;

public class IspisDodatneFunkcionalnosti extends IspisDecorator {

  private int brojUkupnihPaketa = 0;
  private int brojPrimljenihPaketa = 0;
  private int brojIsporucenihPaketa = 0;
  private int brojDostavljenihPaketa = 0;
  private Double postotakPrimljenihPaketa = 0.0;
  private Double postotakIsporucenihPaketa = 0.0;
  private Double postotakDostavljenihPaketa = 0.0;

  public IspisDodatneFunkcionalnosti(IspisComponent ispisComponent) {
    super(ispisComponent);
  }

  @Override
  public void ispisPrimljenihIDostavljenihPaketa(LocalDateTime virtualnoVrijeme) {
    super.ispisPrimljenihIDostavljenihPaketa(virtualnoVrijeme);

    System.out.println("\n\nPOSTOTAK PRIMLJENIH, ISPORUČENIH I DOSTAVLJENIH PAKETA:\n");

    postaviBrojevePaketa(virtualnoVrijeme);

    postotakPrimljenihPaketa =
        (brojUkupnihPaketa != 0) ? ((double) brojPrimljenihPaketa / brojUkupnihPaketa) * 100 : 0.0;
    postotakIsporucenihPaketa =
        (brojUkupnihPaketa != 0) ? ((double) brojIsporucenihPaketa / brojUkupnihPaketa) * 100 : 0.0;
    postotakDostavljenihPaketa =
        (brojUkupnihPaketa != 0) ? ((double) brojDostavljenihPaketa / brojUkupnihPaketa) * 100
            : 0.0;

    String postotakPrimljenihPaketaString = formatirajNaDvijeDecimale(postotakPrimljenihPaketa);
    String postotakIsporucenihPaketaString = formatirajNaDvijeDecimale(postotakIsporucenihPaketa);
    String postotakDostavljenihPaketaString = formatirajNaDvijeDecimale(postotakDostavljenihPaketa);

    nacrtajStupac("PRIMLJENI:   ", postotakPrimljenihPaketa, postotakPrimljenihPaketaString);
    nacrtajStupac("ISPORUČENI:  ", postotakIsporucenihPaketa, postotakIsporucenihPaketaString);
    nacrtajStupac("DOSTAVLJENI: ", postotakDostavljenihPaketa, postotakDostavljenihPaketaString);
  }

  private void postaviBrojevePaketa(LocalDateTime virtualnoVrijeme) {
    brojUkupnihPaketa = dohvatiBrojUkupnoPrimljenihPaketa();
    brojPrimljenihPaketa = dohvatiBrojPrimljenihPaketa(virtualnoVrijeme);
    brojIsporucenihPaketa = dohvatiBrojIsporucenihPaketa(virtualnoVrijeme);
    brojDostavljenihPaketa = dohvatiBrojDostavljenihPaketa(virtualnoVrijeme);
  }

  private void nacrtajStupac(String opis, Double postotak, String postotakString) {
    System.out.print(opis);

    int duzinaStupca = 20;
    int ispunaStupca = (int) ((postotak * duzinaStupca) / 100);

    for (int i = 0; i < duzinaStupca; i++) {
      if (i < ispunaStupca) {
        System.out.print("#");
      } else {
        System.out.print("-");
      }
    }

    System.out.println("  " + postotakString + "%");
  }

  private int dohvatiBrojUkupnoPrimljenihPaketa() {
    return UredZaPrijemSingleton.getInstance().listaPrimljenihPaketa.size();
  }

  private int dohvatiBrojPrimljenihPaketa(LocalDateTime virtualnoVrijeme) {
    for (Paket paket : UredZaPrijemSingleton.getInstance().listaPrimljenihPaketa) {
      String vrijemePreuzimanja = provjeraOznakeIVremenaPreuzimanja(paket);
      String statusIsporuke = "PRIMLJENA";
      if (!vrijemePreuzimanja.equals("")) {
        LocalDateTime vrijemePreuzimanjaLocalDateTime =
            vrijemePreuzimanjaULocalDateTime(vrijemePreuzimanja);
        statusIsporuke =
            definirajStatusIsporuke(vrijemePreuzimanjaLocalDateTime, virtualnoVrijeme, paket);
      }
      if (statusIsporuke.equals("PRIMLJENA")) {
        brojPrimljenihPaketa++;
      }
    }
    return brojPrimljenihPaketa;
  }

  private int dohvatiBrojIsporucenihPaketa(LocalDateTime virtualnoVrijeme) {
    for (Paket paket : UredZaPrijemSingleton.getInstance().listaPrimljenihPaketa) {
      String vrijemePreuzimanja = provjeraOznakeIVremenaPreuzimanja(paket);
      String statusIsporuke = "PRIMLJENA";
      if (!vrijemePreuzimanja.equals("")) {
        LocalDateTime vrijemePreuzimanjaLocalDateTime =
            vrijemePreuzimanjaULocalDateTime(vrijemePreuzimanja);
        statusIsporuke =
            definirajStatusIsporuke(vrijemePreuzimanjaLocalDateTime, virtualnoVrijeme, paket);
      }
      if (statusIsporuke.equals("ISPORUČENA")) {
        brojIsporucenihPaketa++;
      }
    }
    return brojIsporucenihPaketa;
  }

  private int dohvatiBrojDostavljenihPaketa(LocalDateTime virtualnoVrijeme) {
    for (Paket paket : UredZaPrijemSingleton.getInstance().listaPrimljenihPaketa) {
      String vrijemePreuzimanja = provjeraOznakeIVremenaPreuzimanja(paket);
      String statusIsporuke = "PRIMLJENA";
      if (!vrijemePreuzimanja.equals("")) {
        LocalDateTime vrijemePreuzimanjaLocalDateTime =
            vrijemePreuzimanjaULocalDateTime(vrijemePreuzimanja);
        statusIsporuke =
            definirajStatusIsporuke(vrijemePreuzimanjaLocalDateTime, virtualnoVrijeme, paket);
      }
      if (statusIsporuke.equals("DOSTAVLJENA")) {
        brojDostavljenihPaketa++;
      }
    }
    return brojDostavljenihPaketa;
  }

  private String provjeraOznakeIVremenaPreuzimanja(Paket paket) {
    List<String> lista = UredZaDostavuSingleton.getInstance().oznakeIVremenaPreuzimanja;
    String vrijemePreuzimanja = "";
    for (int i = 0; i < lista.size(); i++) {
      String oznaka = lista.get(i);

      if (oznaka.equals(paket.dohvatiOznaku())) {
        if (i < lista.size() - 1) {
          vrijemePreuzimanja = lista.get(i + 1);
        }
      }
    }
    return vrijemePreuzimanja;
  }

  private LocalDateTime vrijemePreuzimanjaULocalDateTime(String vrijemePreuzimanja) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm:ss");
    LocalDateTime vrijemePreuzimanjaLocalDateTime =
        LocalDateTime.parse(vrijemePreuzimanja, formatter);
    return vrijemePreuzimanjaLocalDateTime;
  }

  private String definirajStatusIsporuke(LocalDateTime vrijemePreuzimanjaLocalDateTime,
      LocalDateTime virtualnoVrijeme, Paket paket) {
    if (vrijemePreuzimanjaLocalDateTime.isBefore(virtualnoVrijeme)
        || vrijemePreuzimanjaLocalDateTime.isEqual(virtualnoVrijeme)) {
      if (paket.dostavljen) {
        return "DOSTAVLJENA";
      } else {
        return "ISPORUČENA";
      }
    } else {
      return "ISPORUČENA";
    }
  }

  private String formatirajNaDvijeDecimale(Double broj) {
    DecimalFormat df = new DecimalFormat("#.##");
    return df.format(broj);
  }
}

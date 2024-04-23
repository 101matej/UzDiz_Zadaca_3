package org.foi.uzdiz.chainOfResponsibility;

import java.time.LocalDateTime;
import java.util.Scanner;
import org.foi.uzdiz.UpraviteljKomande;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;
import org.foi.uzdiz.virtualnoVrijeme.VirtualnoVrijemeSingleton;

public class UpraviteljKomandeSV extends Upravitelj {

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("SV")) {
      provjeraKomandeSv(komanda);
    } else if (nasljednik != null) {
      nasljednik.rukujZahtjevom(komanda, prviDio, scanner);
    }
  }

  private void provjeraKomandeSv(String komanda) {
    if (komanda.equals("SV")) {
      LocalDateTime virtualnoVrijemeSv =
          VirtualnoVrijemeSingleton.getInstance().trenutnoVirtualnoVrijeme;
      UpraviteljKomande upraviteljKomande = new UpraviteljKomande();
      upraviteljKomande.pregledajPodatkeVozila(virtualnoVrijemeSv);
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("KOMANDA SV NIJE ISPRAVNA!");
    }
  }

}

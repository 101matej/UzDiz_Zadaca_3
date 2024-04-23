package org.foi.uzdiz.chainOfResponsibility;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.foi.uzdiz.tvrtka.TvrtkaSingleton;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;
import org.foi.uzdiz.virtualnoVrijeme.VirtualnoVrijemeSingleton;

public class UpraviteljKomandeVR extends Upravitelj {

  int brojSatiRada = 0;

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("VR")) {
      provjeraKomandeVr(komanda);
    } else if (nasljednik != null) {
      nasljednik.rukujZahtjevom(komanda, prviDio, scanner);
    }
  }

  private void provjeraKomandeVr(String komanda) {
    if (komanda.split(" ").length == 2) {
      Pattern patternVr = Pattern.compile("[0-9]|0[0-9]|1[0-9]|2[0-4]");
      Matcher matcherVr = patternVr.matcher(komanda.split(" ")[1]);
      if (matcherVr.matches()) {
        brojSatiRada = Integer.parseInt(komanda.split(" ")[1]);
        inicijalizacijaVirtualnogSata();
      } else {
        UpraviteljGresakaSingleton.getInstance()
            .greskaURadu("Neispravno definiran broj sati rada!");
      }
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaURadu("Neispravna komanda VR hh!");
    }
  }

  private void inicijalizacijaVirtualnogSata() {
    VirtualnoVrijemeSingleton.getInstance().ucitajBrojSatiRada(brojSatiRada);
    VirtualnoVrijemeSingleton.getInstance()
        .ucitajVrijednostMs(TvrtkaSingleton.getInstance().dohvatiVrijednostMs());
    VirtualnoVrijemeSingleton.getInstance()
        .ucitajPocetakRadaTvrtke(TvrtkaSingleton.getInstance().dohvatiVrijednostPr());
    VirtualnoVrijemeSingleton.getInstance()
        .ucitajKrajRadaTvrtke(TvrtkaSingleton.getInstance().dohvatiVrijednostKr());
    VirtualnoVrijemeSingleton.getInstance().upravljajVirtualnimVremenom(
        TvrtkaSingleton.getInstance().dohvatiVrijednostVs(),
        TvrtkaSingleton.getInstance().dohvatiVrijednostPr(),
        TvrtkaSingleton.getInstance().dohvatiVrijednostKr());
  }

}

package org.foi.uzdiz.chainOfResponsibility;

import java.util.Scanner;
import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class UpraviteljKomandeQ extends Upravitelj {

  @Override
  public void rukujZahtjevom(String komanda, String prviDio, Scanner scanner) {
    if (prviDio.equals("Q")) {
      System.out.println("IZLAZ IZ PROGRAMA!");
      System.exit(0);
    } else {
      UpraviteljGresakaSingleton.getInstance().greskaUlazneKomande("Ulazna komanda je neispravna!");
    }
  }

}

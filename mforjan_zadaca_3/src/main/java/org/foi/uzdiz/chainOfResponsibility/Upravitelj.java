package org.foi.uzdiz.chainOfResponsibility;

import java.util.Scanner;

public abstract class Upravitelj {

  protected Upravitelj nasljednik;

  public void postaviNasljednika(Upravitelj nasljednik) {
    this.nasljednik = nasljednik;
  }

  public abstract void rukujZahtjevom(String komanda, String prviDio, Scanner scanner);
}

package org.foi.uzdiz.state;

import org.foi.uzdiz.modeli.Paket;

public class KonkretnoStanjePosiljateljNe implements PosiljateljState {

  @Override
  public void promijeniStatus(Paket paket) {
    System.out.println("POŠILJATELJ " + paket.dohvatiPosiljatelja()
        + " NE ŽELI PRIMATI OBAVIJESTI ZA PAKET " + paket.dohvatiOznaku() + "!");
  }

  @Override
  public String dohvatiOznakuStatusa() {
    return "N";
  }

}

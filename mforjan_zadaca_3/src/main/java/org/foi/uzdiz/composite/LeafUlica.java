package org.foi.uzdiz.composite;

import org.foi.uzdiz.upraviteljGresaka.UpraviteljGresakaSingleton;

public class LeafUlica implements ComponentPodrucjaMjestaUlica {
  private String naziv;

  public LeafUlica(String naziv) {
    this.naziv = naziv;
  }

  @Override
  public void ispisiStrukturu() {
    System.out.println(naziv);
  }

  @Override
  public void dodajDio(ComponentPodrucjaMjestaUlica dio) {
    UpraviteljGresakaSingleton.getInstance().greskaURadu("List nema dijelove!");
  }

}

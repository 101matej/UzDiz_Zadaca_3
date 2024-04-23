package org.foi.uzdiz.composite;

import java.util.ArrayList;
import java.util.List;

public class CompositePodrucjaMjesta implements ComponentPodrucjaMjestaUlica {
  private String naziv;
  private List<ComponentPodrucjaMjestaUlica> dijelovi = new ArrayList<>();

  public CompositePodrucjaMjesta(String naziv) {
    this.naziv = naziv;
  }

  @Override
  public void dodajDio(ComponentPodrucjaMjestaUlica dio) {
    dijelovi.add(dio);
  }

  @Override
  public void ispisiStrukturu() {
    System.out.println(naziv);
    for (ComponentPodrucjaMjestaUlica dio : dijelovi) {
      dio.ispisiStrukturu();
    }
  }

}

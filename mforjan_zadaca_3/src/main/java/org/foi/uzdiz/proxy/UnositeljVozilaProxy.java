package org.foi.uzdiz.proxy;

public class UnositeljVozilaProxy implements UnositeljVozila {
  private UnositeljVozilaStvarniObjekt realSubject;

  public UnositeljVozilaProxy() {
    this.realSubject = new UnositeljVozilaStvarniObjekt();
  }

  @Override
  public void dodajVozilo(String registracija, String opis, String kapacitetTezine,
      String kapacitetProstora, String redoslijed, String prosjecnaBrzina, String podrucjaPoRangu,
      String status) {
    realSubject.dodajVozilo(registracija, opis, kapacitetTezine, kapacitetProstora, redoslijed,
        prosjecnaBrzina, podrucjaPoRangu, status);
  }
}

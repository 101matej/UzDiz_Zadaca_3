package org.foi.uzdiz.observer;

import java.util.ArrayList;
import java.util.List;

public class PromatraniPaketKonkretniSubject implements PromatracPaketaSubject {
  private List<ObserverPosiljateljPrimatelj> listaObservera = new ArrayList<>();

  @Override
  public void dodajObserver(ObserverPosiljateljPrimatelj observer) {
    listaObservera.add(observer);
  }

  @Override
  public void ukloniObserver(ObserverPosiljateljPrimatelj observer) {
    listaObservera.remove(observer);
  }

  @Override
  public void obavijestiObserverPreuzet() {
    for (ObserverPosiljateljPrimatelj observer : listaObservera) {
      observer.azurirajPreuzet();
    }
  }

  @Override
  public void obavijestiObserverZaprimljen() {
    for (ObserverPosiljateljPrimatelj observer : listaObservera) {
      observer.azurirajZaprimljen();
    }
  }
}

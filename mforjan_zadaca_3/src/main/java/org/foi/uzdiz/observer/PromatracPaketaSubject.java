package org.foi.uzdiz.observer;

public interface PromatracPaketaSubject {
  void dodajObserver(ObserverPosiljateljPrimatelj observer);

  void ukloniObserver(ObserverPosiljateljPrimatelj observer);

  void obavijestiObserverZaprimljen();

  void obavijestiObserverPreuzet();
}

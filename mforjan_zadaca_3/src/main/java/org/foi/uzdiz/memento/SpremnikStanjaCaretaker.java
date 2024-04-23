package org.foi.uzdiz.memento;

import java.util.HashMap;
import java.util.Map;

public class SpremnikStanjaCaretaker {

  private Map<String, SpremnikStanjaMemento> proslaStanja = new HashMap<>();

  public void pohrani(String naziv, SpremnikStanjaMemento memento) {
    proslaStanja.put(naziv, memento);
  }

  public SpremnikStanjaMemento vrati(String naziv) {
    return proslaStanja.get(naziv);
  }

}

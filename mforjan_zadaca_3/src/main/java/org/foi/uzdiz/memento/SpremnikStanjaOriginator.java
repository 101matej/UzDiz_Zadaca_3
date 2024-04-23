package org.foi.uzdiz.memento;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SpremnikStanjaOriginator {
  private LocalDateTime virtualnoVrijeme;
  private Map<String, String> statusiVozila;

  public void postaviVirtualnoVrijeme(LocalDateTime virtualnoVrijeme) {
    this.virtualnoVrijeme = virtualnoVrijeme;
  }

  public LocalDateTime dohvatiVirtualnoVrijeme() {
    return virtualnoVrijeme;
  }

  public void postaviStatusVozila(Map<String, String> statusiVozila) {
    this.statusiVozila = statusiVozila;
  }

  public Map<String, String> dohvatiStatusVozila() {
    return statusiVozila;
  }

  public SpremnikStanjaMemento spremiStanjeUMemento() {
    return new SpremnikStanjaMemento(virtualnoVrijeme, statusiVozila);
  }

  public LocalDateTime dohvatiVirtualnoVrijemeIzMementa(SpremnikStanjaMemento memento) {
    virtualnoVrijeme = memento.dohvatiVirtualnoVrijeme();
    return virtualnoVrijeme;
  }

  public Map<String, String> dohvatiStanjaVozilaIzMementa(SpremnikStanjaMemento memento) {
    statusiVozila = new HashMap<>(memento.dohvatiStatusVozila());
    return statusiVozila;
  }
}

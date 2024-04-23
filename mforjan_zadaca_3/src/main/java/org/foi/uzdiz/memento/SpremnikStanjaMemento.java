package org.foi.uzdiz.memento;

import java.time.LocalDateTime;
import java.util.Map;

public class SpremnikStanjaMemento {
  private LocalDateTime virtualnoVrijeme;
  private Map<String, String> statusiVozila;

  public SpremnikStanjaMemento(LocalDateTime virtualnoVrijeme, Map<String, String> statusiVozila) {
    this.virtualnoVrijeme = virtualnoVrijeme;
    this.statusiVozila = statusiVozila;
  }

  public LocalDateTime dohvatiVirtualnoVrijeme() {
    return this.virtualnoVrijeme;
  }

  public Map<String, String> dohvatiStatusVozila() {
    return statusiVozila;
  }
}

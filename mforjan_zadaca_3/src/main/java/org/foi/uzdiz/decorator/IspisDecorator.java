package org.foi.uzdiz.decorator;

import java.time.LocalDateTime;

public class IspisDecorator implements IspisComponent {

  private IspisComponent ispisComponent;

  public IspisDecorator(IspisComponent ispisComponent) {
    this.ispisComponent = ispisComponent;
  }

  @Override
  public void ispisPrimljenihIDostavljenihPaketa(LocalDateTime virtualnoVrijeme) {
    ispisComponent.ispisPrimljenihIDostavljenihPaketa(virtualnoVrijeme);
  }

}

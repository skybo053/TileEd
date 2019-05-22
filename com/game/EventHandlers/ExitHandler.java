package com.game.EventHandlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExitHandler implements EventHandler<ActionEvent>
{


  public void handle(ActionEvent pEvent) 
  {
    System.exit(0);
  }

}

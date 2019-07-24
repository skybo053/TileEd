package com.game.tileEditor;

import javafx.scene.layout.BorderPane;
import tileEvents.TileEvent;


public class EditableTileEventsMenu extends BorderPane
{
  private TileEvent oTileEvent = null;
  
  
  public EditableTileEventsMenu()
  {
    setPrefWidth(600.0);
    setPrefHeight(500.0);
  }
  
  
  public void setTileEvent(TileEvent pTileEvent)
  {
    oTileEvent = pTileEvent;
  }
  
}

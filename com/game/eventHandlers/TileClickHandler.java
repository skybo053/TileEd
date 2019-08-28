package com.game.eventHandlers;

import com.game.tileEditor.Tile;
import com.game.tileEditor.TileEditor;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;


public class TileClickHandler implements EventHandler<MouseEvent>
{
  private TileEditor oTileEditor = null;
  
  
  public TileClickHandler(TileEditor pTileEditor)
  {
    oTileEditor = pTileEditor;
  }
  
  
  public void handle(MouseEvent pMouseEvent)
  {
    Tile vCurrentTile  = null;
    Tile vPreviousTile = null;
    
    vCurrentTile = (Tile)pMouseEvent.getSource();
    
    oTileEditor.setCurrentTile(vCurrentTile);
    oTileEditor.clearTileMenuAttributeValues();
    oTileEditor.clearEditableTileMenuAttributeValues();
    
    vPreviousTile = oTileEditor.getPreviousTile();
    
    if(vPreviousTile != null)
    {
      oTileEditor.setTileStyle(vPreviousTile, TileEditor.GRID_CELL);
    }
    
    oTileEditor.setTileStyle(vCurrentTile, TileEditor.SELECTED_GRID_CELL);
    oTileEditor.displayCurrentTileConfig();
    oTileEditor.displayEditableTileConfig();
    
    oTileEditor.setPreviousTile(vCurrentTile);
    
    pMouseEvent.consume();
  }
}
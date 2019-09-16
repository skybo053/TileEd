package com.game.eventHandlers;

import java.io.File;

import com.game.tileEditor.TileEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;

public class SaveHandler implements EventHandler<ActionEvent>
{
  private FileChooser oFileChooser = null;
  private TileEditor  oTileEditor  = null;
  
  public SaveHandler(TileEditor pTileEditor)
  {
    oTileEditor  = pTileEditor;
    
    oFileChooser = new FileChooser();
    oFileChooser.setInitialFileName("*.json");
    oFileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("JSON", "*.json"));
  }
  
  
  public void handle(ActionEvent pActionEvent)
  {
    File vFile = null;
    
    vFile = oFileChooser.showSaveDialog(oTileEditor.getMainSceneWindow());
    
    if(vFile != null)
    {
      if(vFile.getName().endsWith(".json") == false)
      {
        vFile = new File(vFile.getName() + ".json");
      }
      
      oTileEditor.saveMap(vFile);
    }
  }

}

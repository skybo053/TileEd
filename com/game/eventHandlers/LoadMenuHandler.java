package com.game.eventHandlers;

import java.io.File;

import com.game.tileEditor.TileEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;


public class LoadMenuHandler implements EventHandler<ActionEvent>
{
  private TileEditor  oTileEditor = null;
  private FileChooser oFileChooser = null;
  
  
  public LoadMenuHandler(TileEditor pTileEditor)
  {
    oTileEditor  = pTileEditor;
    oFileChooser = new FileChooser();
  }
  
  
  public void handle(ActionEvent pActionEvent)
  {
    File   vChoosenFile = null;
    String vMapFilePath = null;
    
    vChoosenFile = oFileChooser.showOpenDialog(oTileEditor.getMainSceneWindow());
    
    if(vChoosenFile != null)
    {
      vMapFilePath = vChoosenFile.getAbsolutePath();
      
      System.out.println(vMapFilePath);
    }
  }

}

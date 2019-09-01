package com.game.eventHandlers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.game.tileEditor.Tile;
import com.game.tileEditor.TileEditor;
import com.game.tileEditor.tileEvents.TileEvent;
import com.game.utilities.SceneUtils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
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
    
    vChoosenFile = oFileChooser.showOpenDialog(oTileEditor.getMainSceneWindow());
    
    if(vChoosenFile != null)
    {
      loadMap(vChoosenFile);
    }
  }
  
  
  private void loadMap(File pMapFile)
  {
    TileClickHandler vTileClickHandler = null;
    JSONParser     vParser             = null; 
    JSONObject     vMapObject          = null;
    JSONObject     vDimensions         = null;
    JSONObject     vCurrTileObject     = null;
    JSONObject     vCurrTileData       = null;
    JSONArray      vTileDataArray      = null;
    
    
    int vMapRows         = 0;
    int vMapCols         = 0;
    int vCurrentRowIndex = 0;

    try
    {
      vParser           = new JSONParser();
      vMapObject        = (JSONObject)vParser.parse(new FileReader(pMapFile));
      vTileClickHandler = oTileEditor.getTileClickHandler();

      vDimensions    = (JSONObject)vMapObject.get("map");
      vMapRows       = Integer.parseInt(vDimensions.get("rows").toString());
      vMapCols       = Integer.parseInt(vDimensions.get("cols").toString());
      vTileDataArray = (JSONArray)vMapObject.get("tiles");
      
      oTileEditor.clearMapGrid();
      
      for(Iterator<JSONObject> vTileIterator = vTileDataArray.iterator(); vTileIterator.hasNext();)
      {
        ArrayList<Tile> vTileRow   = new ArrayList<Tile>();
        Integer         vRowIndex  = null;
       
         for(int vColumnCount = 1; vColumnCount <= vMapCols; ++vColumnCount)
         {
           Tile      vTile       = null;
           Boolean   vIsSolid    = null;
           String    vImageName  = null;
           Image     vImage      = null;
           JSONArray vTileEvents = null;
           
           
           JSONObject vTileObject = (JSONObject)vTileIterator.next();
           JSONObject vTileData   = (JSONObject)vTileObject.get("tile");
           vTile                  = new Tile();
           
           vIsSolid   = Boolean.valueOf(vTileData.get("solid").toString());
           vImageName = vTileData.get("image").toString();
           vImage     = oTileEditor.getLoadedImage(vImageName);
           vTileEvents = (JSONArray)vTileData.get("events");
           
           vTile.setTileImageName(vImageName);
           vTile.setIsSolid(vIsSolid);
           vTile.setTileImage(vImage);
           vTile.setOnMouseClicked(vTileClickHandler);
           
           if(vTileEvents.size() > 0)
           {
             vTile.setTileEvents(parseTileEvents(vTileEvents));
           }
           
           vTileRow.add(vTile);
           
           if(vTileIterator.hasNext() == false)
           {
             while(vColumnCount < vMapCols)
             {
               vTile = new Tile();
               vTile.setOnMouseClicked(vTileClickHandler);
               
               vTileRow.add(vTile);
               
               ++vColumnCount;
             }
           }
         }
         
         oTileEditor.addLoadedMapRow(vTileRow, vCurrentRowIndex);
         ++vCurrentRowIndex;
       }
     
     oTileEditor.enableAddRemoveTileButtons();
     oTileEditor.setMapGridRowCount(vCurrentRowIndex);
     oTileEditor.setMapGridColumnCount(vMapCols);
   }
    catch(NumberFormatException pNumberFormatException)
    {
    }
    catch(ParseException pParseException)
    {
      SceneUtils.displayAlert(
          Alert.AlertType.ERROR, 
          "ERROR",
          "Unable to load map file.  File must contain valid JSON");
    }
    catch(IOException pIOException)
    {
    }
    catch(ArrayIndexOutOfBoundsException pArrayIndexOutOfBoundsException)
    {
    }
    catch(NullPointerException pNullPointerException)
    {
      SceneUtils.displayAlert(
          Alert.AlertType.ERROR, 
          "ERROR",
          "Unable to load map file.  Map is missing required attributes");
    }
  }
  
  
  private ArrayList<TileEvent> parseTileEvents(JSONArray pEventsArray)
  {
    ArrayList<TileEvent> vTileEvents  = null;
    JSONObject           vEventObject = null;
    JSONObject           vEventData   = null;
    JSONArray            vEventArgs   = null;
    
    vTileEvents = new ArrayList<TileEvent>();
    
    for(Iterator<JSONObject> vEventsArrayObject = pEventsArray.iterator(); vEventsArrayObject.hasNext();)
    {
      TileEvent vTileEvent      = null;
      String    vEventClassName = null;
      
      vEventObject = (JSONObject)vEventsArrayObject.next();
      vEventData   = (JSONObject)vEventObject.get("event");
      
      vEventClassName = vEventData.get("name").toString();
      vEventArgs      = (JSONArray)vEventData.get("args");
      
      vTileEvent      = new TileEvent(vEventClassName);
      
      for(Iterator<String> vArgsArrayObject = vEventArgs.iterator(); vArgsArrayObject.hasNext();)
      {
        String   vArgumentString   = null;
        String[] vArgumentElements = null;
        String   vArgumentClass    = null;
        String   vArgumentValue    = null;
        
        vArgumentString   = vArgsArrayObject.next();
        vArgumentElements = vArgumentString.split("\\|");
        
        vArgumentClass = vArgumentElements[0];
        vArgumentValue = vArgumentElements[1];
        
        vTileEvent.addTileEventArg(vArgumentClass, vArgumentValue);
      }
      
      vTileEvents.add(vTileEvent);
    }
    
    return vTileEvents;
  }

}

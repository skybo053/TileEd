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
    JSONArray      vTileDataArray      = null;
    Object         vAttribValue        = null;
    
    int vMapCols         = 0;
    int vCurrentRowIndex = 0;

    try
    {
      vParser        = new JSONParser();
      vMapObject     = (JSONObject)vParser.parse(new FileReader(pMapFile));

      vDimensions    = (JSONObject)vMapObject.get("map");
      
      if(vDimensions == null)
      {
        throw new NullPointerException("Map file missing required \"map\" object");
      }
      
      vAttribValue = vDimensions.get("cols");
      
      if(vAttribValue == null)
      {
        throw new NullPointerException("Map object is missing required key/value \"cols\"");
      }
      
      vMapCols       = Integer.parseInt(vAttribValue.toString());
      vTileDataArray = (JSONArray)vMapObject.get("tiles");
      
      if(vTileDataArray == null || vTileDataArray.size() == 0)
      {
        throw new NullPointerException("Map file does not contain any \"tiles\"");
      }
      
      vTileClickHandler = oTileEditor.getTileClickHandler();
      
      oTileEditor.clearMapGrid();
     
      for(Iterator<JSONObject> vTileIterator = vTileDataArray.iterator(); vTileIterator.hasNext();)
      {
        ArrayList<Tile> vTileRow   = new ArrayList<Tile>();
        Integer         vRowIndex  = null;
       
         for(int vColumnCount = 1; vColumnCount <= vMapCols; ++vColumnCount)
         {
           Tile      vTile        = null;
           Boolean   vIsSolid     = null;
           String    vImageName   = null;
           Image     vImage       = null;
           JSONArray vTileEvents  = null;
           
           
           JSONObject vTileObject = (JSONObject)vTileIterator.next();
           JSONObject vTileData   = (JSONObject)vTileObject.get("tile");
           
           if(vTileData == null)
           {
             continue;
           }
           
           vTile  = new Tile();
           
           vAttribValue = vTileData.get("solid");
           
           if(vAttribValue == null)
           {
             vIsSolid = false;
           }
           else
           {
             vIsSolid = Boolean.valueOf(vAttribValue.toString());
           }
           
           vAttribValue = vTileData.get("image");
           
           if(vAttribValue == null)
           {
             vImageName = "NO_IMAGE_ATTRIBUTE";
           }
           else
           {
             vImageName = vAttribValue.toString();
           }
           
           vImage     = oTileEditor.getLoadedImage(vImageName);
           
           vTileEvents = (JSONArray)vTileData.get("events");
           
           vTile.setTileImageName(vImageName);
           vTile.setIsSolid(Boolean.valueOf(vIsSolid));
           vTile.setTileImage(vImage);
           vTile.setOnMouseClicked(vTileClickHandler);
           
           if(vTileEvents != null && vTileEvents.size() > 0)
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
      SceneUtils.displayAlert(
          Alert.AlertType.ERROR, 
          "ERROR",
          "Unable to parse value given for \"cols\" attribute into a valid number");
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
          "Unable to load map file.  Map is missing required attributes: " +
           pNullPointerException.getMessage());
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
      Object    vEventAttrib    = null;
      
      vEventObject = (JSONObject)vEventsArrayObject.next();
      vEventData   = (JSONObject)vEventObject.get("event");
      
      if(vEventData == null)
      {
        continue;
      }
      
      vEventAttrib = vEventData.get("name");
      
      if(vEventAttrib == null)
      {
        vEventClassName = "NO_NAME_GIVEN";
      }
      else
      {
        vEventClassName = vEventAttrib.toString();
      }
      
      vEventArgs  = (JSONArray)vEventData.get("args");
      vTileEvent  = new TileEvent(vEventClassName);
      
      if(vEventArgs != null && vEventArgs.size() > 0)
      {
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
      }
      
      vTileEvents.add(vTileEvent);
    }
    
    return vTileEvents;
  }

}

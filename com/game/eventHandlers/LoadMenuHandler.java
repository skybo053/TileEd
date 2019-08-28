package com.game.eventHandlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    JSONArray      vTileEventsArray    = null;
    
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
      oTileEditor.setMapGridRowCount(vMapRows);
      oTileEditor.setMapGridColumnCount(vMapCols);
      
      for(Iterator<JSONObject> vTileIterator = vTileDataArray.iterator(); vTileIterator.hasNext();)
      {
        ArrayList<Tile> vTileRow   = new ArrayList<Tile>();
        Integer         vRowIndex  = null;
       
         for(int vColumnCount = 1; vColumnCount <= vMapCols; ++vColumnCount)
         {
           Tile    vTile      = null;
           Boolean vIsSolid   = null;
           String  vImageName = null;
           Image   vImage     = null;
           
           
           JSONObject vTileObject = (JSONObject)vTileIterator.next();
           JSONObject vTileData   = (JSONObject)vTileObject.get("tile");
           vTile                  = new Tile();
           
           vIsSolid   = Boolean.valueOf(vTileData.get("solid").toString());
           vImageName = vTileData.get("image").toString();
           vImage     = oTileEditor.getLoadedImage(vImageName);
           
           vTile.setIsSolid(vIsSolid);
           vTile.setTileImage(vImage);
           vTile.setOnMouseClicked(vTileClickHandler);
           
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

    if(vTileEventsArray.size() > 0)
    {
      //vTileEvents = parseEventsArray(vTileEventsArray);
    }
    
        
      
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
  
  
  private static ArrayList<TileEvent> parseEventsArray(
      JSONArray pEventsArray)
  {
    ArrayList<TileEvent> vTileEvents = null;
    
    Class<?>[]  vArgsClass      = null;
    Object[]    vArgsObject     = null;
    
    JSONObject  vCurrEventObject  = null;
    JSONObject  vCurrEventData    = null;
    JSONArray   vEventArgs        = null;
    
    String      vEventClassName   = null;
    
    try
    {
      vTileEvents = new ArrayList<TileEvent>();
      
      for(int vIndex = 0; vIndex < pEventsArray.size(); ++vIndex)
      {
        vCurrEventObject = (JSONObject)pEventsArray.get(vIndex);
        vCurrEventData   = (JSONObject)vCurrEventObject.get("event");
        vEventClassName  = vCurrEventData.get("name").toString();
        vEventArgs       = (JSONArray)vCurrEventData.get("args");
        
        vArgsClass  = new Class<?>[vEventArgs.size()];
        vArgsObject = new Object[vEventArgs.size()];
        
        for(int vArgsIndex = 0; vArgsIndex < vEventArgs.size(); ++vArgsIndex)
        {
          String[] vArgTokens = vEventArgs.get(vArgsIndex).toString().split("\\|");
          
          vArgsClass[vArgsIndex]  = Class.forName(vArgTokens[0]);
          vArgsObject[vArgsIndex] = vArgsClass[vArgsIndex]
                                    .getConstructor(new Class<?>[]{vArgTokens[1].getClass()})
                                    .newInstance(vArgTokens[1]);
        }
        
        Class<?>       vEventClassObject = Class.forName(vEventClassName);
        Constructor<?> vEventConstructor = vEventClassObject.getConstructor(vArgsClass);
        
        vTileEvents.add( (TileEvent)vEventConstructor.newInstance(vArgsObject) );
      }
      return vTileEvents;
    }
    catch(IllegalAccessException pIllegalAccessException)
    {
      
    }
    catch(NoSuchMethodException pNoSuchMethodException)
    {
    }
    catch(ClassNotFoundException pClassNotFoundException)
    {
    }
    catch(InvocationTargetException pInvocationTargetException)
    {
    }
    catch(InstantiationException pInstantiationException)
    {
    }
    return vEventArgs;
  }

}

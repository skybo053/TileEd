package com.game.tileEditor;

import java.util.ArrayList;
import java.util.Iterator;

import com.game.tileEditor.tileEvents.TileEvent;
import com.game.utilities.SceneUtils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane
{
  private static final String STYLE_CLASS     = "grid-cell";
  
  private String               oTileImageName = "No Image";
  private ImageView            oImageView     = null;
  private Boolean              oIsSolid       = false;
  private ArrayList<TileEvent> oTileEvents    = null;
  private Integer              oRowIndex      = null;
  private Integer              oColumnIndex   = null;
 
  
  public Tile()
  {
    oImageView     = new ImageView();
    oTileEvents    = new ArrayList<TileEvent>();
    oRowIndex      = 0;
    oColumnIndex   = 0;
    
    setPrefWidth(TileEditor.TILE_PANE_LENGTH);
    setPrefHeight(TileEditor.TILE_PANE_LENGTH);
    setTileStyle(STYLE_CLASS);
  }
  
  
  public Tile( 
      Boolean   pIsSolid, 
      String    pImagePath,
      String    pTileImageName)
  {
    this();
    
    oIsSolid       = pIsSolid;
    oTileImageName = pTileImageName;
    
    setTileImage(pImagePath);
  }

  
  public void setTileImage(String pImagePath)
  {
    Image vImage = null;
    
    vImage = new Image(
        pImagePath,
        TileEditor.TILE_IMAGE_LENGTH,
        TileEditor.TILE_IMAGE_LENGTH,
        false,
        true);
    
    oImageView.setImage(vImage);
    
    SceneUtils.clearPane(this);
    SceneUtils.addToPane(this, oImageView);
  }
  
  
  public void setTileImage(Image pImage)
  {
    oImageView.setImage(pImage);
    
    SceneUtils.clearPane(this);
    SceneUtils.addToPane(this, oImageView);
  }
  
  
  public ImageView getTileImageView()
  {
    return oImageView;
  }
  
  
  public Image getTileImage()
  {
    if(oImageView == null)
    {
      return null;
    }
    else
    {
      return oImageView.getImage();
    }
  }
  
  
  public void setIsSolid(Boolean pIsSolid)
  {
    oIsSolid = pIsSolid;
  }
  
  
  public Boolean isSolid()
  {
    return oIsSolid;
  }
  
  
  public double getTileWidth()
  {
    Image vImage = null;
    
    vImage = oImageView.getImage();
    
    if(vImage == null)
    {
      return getWidth();
    }
    else
    {
      return vImage.getWidth();
    }
  }
  
  
  public double getTileHeight()
  {
    Image vImage = null;
    
    vImage = oImageView.getImage();
    
    if(vImage == null)
    {
      return getHeight();
    }
    else
    {
      return vImage.getHeight();
    }
  }

  
  public void setTileImageName(String pTileImageName)
  {
    oTileImageName = pTileImageName;
  }
  
  
  public String getTileImageName()
  {
    return oTileImageName;
  }
  
  
  public void setTileEvents(ArrayList<TileEvent> pTileEvents)
  {
    oTileEvents.addAll(pTileEvents);
  }
  
  
  public void addTileEvent(TileEvent pTileEvent)
  {
    oTileEvents.add(pTileEvent);
  }
  
  
  public void deleteTileEvent(TileEvent pTileEvent)
  {
    oTileEvents.removeIf(pExistingTileEvent ->
    {
      return pExistingTileEvent == pTileEvent;
    });
  }
  
  
  public ArrayList<TileEvent> getTileEvents()
  {
    return oTileEvents;
  }
  
  
  public void setTileStyle(String pStyle)
  {
    getStyleClass().clear();
    getStyleClass().add(pStyle);
  }
  
  
  public Integer getRowIndex()
  {
    return oRowIndex;
  }
  
  
  public void setRowIndex(Integer pRowIndex)
  {
    oRowIndex = pRowIndex;
  }
  
  
  public Integer getColumnIndex()
  {
    return oColumnIndex;
  }
  
  
  public void setColumnIndex(Integer pColumnIndex)
  {
    oColumnIndex = pColumnIndex;
  }
  
  
  public String toJson()
  {
    StringBuilder vStringBuilder = null;
    String        vIndent        = null;
    String        vNewLine       = null;
    
    vStringBuilder = new StringBuilder(1024);
    vIndent        = "  ";
    vNewLine       = System.lineSeparator();
    
    vStringBuilder.append(vIndent + vIndent + "{" + vNewLine);
    vStringBuilder.append(vIndent + vIndent + vIndent + "\"tile\":" + vNewLine);
    vStringBuilder.append(vIndent + vIndent + vIndent + "{" + vNewLine);
    vStringBuilder.append(vIndent + vIndent + vIndent + vIndent + "\"row\":"          + oRowIndex      + ","   + vNewLine);
    vStringBuilder.append(vIndent + vIndent + vIndent + vIndent + "\"col\":"          + oColumnIndex   + ","   + vNewLine);
    vStringBuilder.append(vIndent + vIndent + vIndent + vIndent + "\"image-name\":\"" + oTileImageName + "\"," + vNewLine);
    vStringBuilder.append(vIndent + vIndent + vIndent + vIndent + "\"solid\":"        + oIsSolid       + ","   + vNewLine);
    vStringBuilder.append(vIndent + vIndent + vIndent + vIndent + "\"events\":" + vNewLine);
    vStringBuilder.append(vIndent + vIndent + vIndent + vIndent + "[");
    vStringBuilder.append(oTileEvents.size() > 0 ? vNewLine : "");
    
    for(Iterator<TileEvent> vEventIterator = oTileEvents.iterator(); vEventIterator.hasNext();)
    {
      TileEvent vTileEvent = null;
      
      vTileEvent = vEventIterator.next();
      
      vStringBuilder.append(vTileEvent.toJson());
      
      if(vEventIterator.hasNext())
      {
        vStringBuilder.append("," + vNewLine);
      }
    }
    
    vStringBuilder.append(vNewLine + vIndent + vIndent + vIndent + vIndent + "]" + vNewLine);
    vStringBuilder.append(vIndent + vIndent + vIndent + "}" + vNewLine);
    vStringBuilder.append(vIndent + vIndent + "}");
    
    return vStringBuilder.toString();
  }
  
}
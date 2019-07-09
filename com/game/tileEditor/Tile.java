package com.game.tileEditor;

import java.util.ArrayList;

import com.game.utilities.SceneUtils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import tileEvents.TileEvent;

public class Tile extends StackPane
{
  private static final String STYLE_CLASS     = "grid-cell";
  
  private String               oTileImageName = "No Image";
  private ImageView            oImageView     = null;
  private Boolean              oIsSolid       = false;
  private ArrayList<TileEvent> oTileEvents    = null;
 
  
  public Tile()
  {
    oImageView     = new ImageView();
    oTileEvents    = new ArrayList<TileEvent>();
    
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
    oTileEvents = pTileEvents;
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
  
}
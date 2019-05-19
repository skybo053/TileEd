package com.game.TileEditor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane
{
  private static final String STYLE_CLASS = "grid-cell";
  
  private ImageView oImageView = null;
  private Boolean   oIsSolid   = null;
  private Integer   oWidth     = null;
  private Integer   oHeight    = null;
  private TileImage oTileImage = null;
  
  
  public Tile(
      int       pWidth, 
      int       pHeight, 
      Boolean   pIsSolid, 
      String    pImagePath,
      TileImage pTileImage)
  {
    oWidth     = pWidth;
    oHeight    = pHeight;
    oIsSolid   = pIsSolid;
    oTileImage = pTileImage;
    
    oImageView = new ImageView();
    
    setTileImage(pImagePath);
    setTileStyle(STYLE_CLASS);
  }

  
  public void setTileImage(String pImagePath)
  {
    Image vImage = null;
    
    vImage = new Image(
        pImagePath,
        oWidth,
        oHeight,
        true,
        true);
    
    oImageView.setImage(vImage);
    
    getChildren().add(oImageView);
  }
  
  
  public ImageView getTileImage()
  {
    return oImageView;
  }
  
  
  public void setIsSolid(Boolean pIsSolid)
  {
    oIsSolid = pIsSolid;
  }
  
  
  public Boolean isSolid()
  {
    return oIsSolid;
  }
  
  
  public void setTileWidth(Integer pWidth)
  {
    oWidth = pWidth;
  }
  
  
  public Integer getTileWidth()
  {
    return oWidth;
  }
  
  
  public void setTileHeight(Integer pHeight)
  {
    oHeight = pHeight;
  }
  
  
  public Integer getTileHeight()
  {
    return oHeight;
  }
  
  
  public String getDescription()
  {
    return oTileImage.getDescription();
  }
  
  
  public void setTileStyle(String pStyle)
  {
    getStyleClass().clear();
    getStyleClass().add(pStyle);
  }
  
}
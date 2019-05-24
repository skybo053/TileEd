package com.game.TileEditor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane
{
  private static final String STYLE_CLASS = "grid-cell";
  
  private ImageView     oImageView     = null;
  private Boolean       oIsSolid       = null;
  private Integer       oWidth         = null;
  private Integer       oHeight        = null;
  private TileImageType oTileImageType = null;
  
  
  public Tile(int pWidth, int pHeight)
  {
    oWidth         = pWidth;
    oHeight        = pHeight;
    oTileImageType = TileImageType.NOT_SET;
    oIsSolid       = false;
    
    setPrefWidth(oWidth);
    setPrefHeight(oHeight);
    
    setTileStyle(STYLE_CLASS);
  }
  
  
  public Tile(
      int           pWidth, 
      int           pHeight, 
      Boolean       pIsSolid, 
      String        pImagePath,
      TileImageType pTileImageType)
  {
    this(pWidth, pHeight);
    
    oImageView     = new ImageView();
    oIsSolid       = pIsSolid;
    oTileImageType = pTileImageType;
    
    setTileImage(pImagePath);
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
  
  
  public String getTileImageName()
  {
    return oTileImageType.getDescription();
  }
  
  
  public String getTileEvents()
  {
    return "None";
  }
  
  
  public void setTileStyle(String pStyle)
  {
    getStyleClass().clear();
    getStyleClass().add(pStyle);
  }
  
}
package com.game.TileEditor;

import com.game.Utilities.SceneUtils;

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
  private String        oTileImageName = null;
  
  public Tile(int pWidth, int pHeight)
  {
    oWidth         = pWidth;
    oHeight        = pHeight;
    oIsSolid       = false;
    oTileImageName = "No Image";
    oImageView     = new ImageView();
    
    setPrefWidth(oWidth);
    setPrefHeight(oHeight);
    
    setTileStyle(STYLE_CLASS);
  }
  
  
  public Tile(
      int         pWidth, 
      int         pHeight, 
      Boolean     pIsSolid, 
      String      pImagePath,
      String      pTileImageName)
  {
    this(pWidth, pHeight);
    
    oImageView     = new ImageView();
    oIsSolid       = pIsSolid;
    oTileImageName = pTileImageName;
    
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
  
  
  public void setTileImageName(String pTileImageName)
  {
    oTileImageName = pTileImageName;
  }
  
  
  public String getTileImageName()
  {
    return oTileImageName;
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
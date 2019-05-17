package com.game.TileEditor;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile
{
  private Image     oImage     = null;
  private ImageView oImageView = null;
  
  private Boolean   oIsSolid   = null;
  
  private Integer oWidth  = null;
  private Integer oHeight = null;
  
  
  public Tile(int pWidth, int pHeight)
  {
    oWidth  = pWidth;
    oHeight = pHeight;
  }
  
  
  public void setImageView(String pPath)
  {
    Image vImage = null;
    
    vImage = new Image(
        pPath,
        oWidth,
        oHeight,
        true,
        true);
    
    if(oImageView == null)
    {
      oImageView = new ImageView(vImage);
    }
    else
    {
      oImageView.setImage(vImage);
    }
  }
  
  
  public ImageView getImageView()
  {
    return oImageView;
  }
}
package com.game.TileEditor;

public enum TileImageType 
{
  GRASS("Grass tile"),
  DIRT("Dirt tile"),
  WATER("Water tile"),
  NOT_SET("No image");
  
  private String oDescription = null;
  
  
  private TileImageType(String pDescription)
  {
    oDescription = pDescription;
  }
  
  
  public String getDescription()
  {
    return oDescription;
  }

}

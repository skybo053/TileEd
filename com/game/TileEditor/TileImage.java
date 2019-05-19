package com.game.TileEditor;

public enum TileImage 
{
  GRASS("Grass tile"),
  DIRT("Dirt tile"),
  WATER("Water tile");
  
  private String oDescription = null;
  
  
  private TileImage(String pDescription)
  {
    oDescription = pDescription;
  }
  
  
  public String getDescription()
  {
    return oDescription;
  }

}

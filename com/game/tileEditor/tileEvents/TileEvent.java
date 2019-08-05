package com.game.tileEditor.tileEvents;

import java.util.ArrayList;

public interface TileEvent 
{
  public String toString();
  public String toJSON();
  public String toDisplayString();
  
  public String getEventName();
  public String getEventClassName();
  
  public void addTileEventArg(String pClassType, String pValue);
  public void setTileEventArgs(ArrayList<TileEventArg> pTileEventArg);
  public ArrayList<TileEventArg> getTileEventArgs();
}

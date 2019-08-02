package tileEvents;

import java.util.ArrayList;

public interface TileEvent 
{
  public String toString();
  public String toJSON();
  public String toDisplayString();
  
  public String getEventName();
  
  public TileEventArg createTileEventArg(String pClassType, String pValue);
  public ArrayList<TileEventArg> getTileEventArgs();
}

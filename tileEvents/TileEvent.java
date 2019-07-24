package tileEvents;

public interface TileEvent 
{
  public String toString();
  public String toJSON();
  public String toDisplayString();
  
  public TileEventArg createTileEventArg(String pClassType, String pValue);
}

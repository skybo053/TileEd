package tileEvents;

import java.util.ArrayList;
import java.util.Iterator;

public class MoveEvent implements TileEvent
{
  private static final String EVENT_NAME = "MoveEvent";
  
  private String                  oEventClassName = null;
  private ArrayList<TileEventArg> oTileEventArgs  = null;
  
  
  public MoveEvent(String pEventClassName)
  {
    oEventClassName = pEventClassName;
    oTileEventArgs  = new ArrayList<TileEventArg>();
  }
  
  
  public String getEventName()
  {
    return EVENT_NAME;
  }
  
  
  public TileEventArg createTileEventArg(String pClassType, String pValue)
  {
    TileEventArg vTileEventArg = null;
    
    vTileEventArg = new TileEventArg(pClassType, pValue);
    
    oTileEventArgs.add(vTileEventArg);
    
    return vTileEventArg;
  }
  
  
  public String toJSON()
  {
    StringBuilder          vEventJSON    = null;
    TileEventArg           vTileEventArg = null;
    Iterator<TileEventArg> vIterator     = null;
    
    vEventJSON = new StringBuilder(255);
    vIterator  = oTileEventArgs.iterator();
    
    vEventJSON.append("{ \n");
    vEventJSON.append("  \"event\" : \n");
    vEventJSON.append("  { \n");
    vEventJSON.append("    \"name\" : ");
    vEventJSON.append("\"" + oEventClassName + "\" \n");
    vEventJSON.append("    \"args\" : [");
    
    while(vIterator.hasNext())
    {
      vTileEventArg = vIterator.next();
      
      vEventJSON.append(vTileEventArg.toJSON());
      
      if(vIterator.hasNext())
      {
        vEventJSON.append(", ");
      }
    }
    
    vEventJSON.append("] \n");
    vEventJSON.append("  } \n");
    vEventJSON.append("}");
    
    return vEventJSON.toString();
  }
  
  
  public String toString()
  {
    return EVENT_NAME;
  }
}

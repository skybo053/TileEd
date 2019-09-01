package com.game.tileEditor.tileEvents;

import java.util.ArrayList;
import java.util.Iterator;

public class TileEvent
{
  private String                  oEventClassName = null;
  private String                  oEventName      = null;
  private ArrayList<TileEventArg> oTileEventArgs  = null;
  
  
  public TileEvent(String pEventClassName)
  {
    oEventClassName = pEventClassName;
    oEventName      = parseEventName(pEventClassName);
    oTileEventArgs  = new ArrayList<TileEventArg>();
  }
  
  
  public void setEventClassName(String pEventClassName)
  {
    oEventClassName = pEventClassName;
    
    setEventName(parseEventName(pEventClassName));
  }
  
  
  public String getEventClassName()
  {
    return oEventClassName;
  }
  
  
  public void setEventName(String pEventName)
  {
    oEventName = pEventName;
  }
  
  
  public String getEventName()
  {
    return oEventName;
  }
  
  
  public ArrayList<TileEventArg> getTileEventArgs()
  {
    return oTileEventArgs;
  }
  
  
  public void setTileEventArgs(ArrayList<TileEventArg> pTileEventArgs)
  {
    oTileEventArgs = pTileEventArgs;
  }
  
  
  public void addTileEventArg(String pClassType, String pValue)
  {
    TileEventArg vTileEventArg = null;
    
    vTileEventArg = new TileEventArg(pClassType, pValue);
    
    oTileEventArgs.add(vTileEventArg);
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
    return oEventName;
  }
  
  
  public String toDisplayString()
  {
    StringBuilder vDisplayString = null;
    TileEventArg  vTileEventArg  = null;
    
    vDisplayString = new StringBuilder(255);
    
    vDisplayString.append("Event Class Name:  ");
    vDisplayString.append(oEventClassName);
    
    for(Iterator<TileEventArg> vIterator = oTileEventArgs.iterator(); vIterator.hasNext();)
    {
      vTileEventArg = vIterator.next();
      
      vDisplayString.append("\n");
      vDisplayString.append("Argument Type:  " + vTileEventArg.getArgClassType() + "  ");
      vDisplayString.append("Argument Value:  " + vTileEventArg.getArgValue());    
    }
    
    return vDisplayString.toString();
  }
  
  
  private String parseEventName(String pEventClassName)
  {
    int vIndex = -1;
    
    try
    {
      vIndex = pEventClassName.lastIndexOf(".");
      
      if(vIndex == -1)
      {
        return pEventClassName;
      }
      else
      {
        return pEventClassName.substring(vIndex + 1);
      }
    }
    catch(StringIndexOutOfBoundsException pStringIndexOutOfBoundsException)
    {
      return pEventClassName;
    }
  }
}

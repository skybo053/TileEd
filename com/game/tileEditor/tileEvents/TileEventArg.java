package com.game.tileEditor.tileEvents;

public class TileEventArg 
{
  private String oClassType  = null;
  private String oValue      = null;
  
  
  public TileEventArg(String pClassType, String pValue)
  {
    oClassType  = pClassType;
    oValue      = pValue;
  }
  
  
  public String getArgClassType()
  {
    return oClassType;
  }
  
  
  public String getArgValue()
  {
    return oValue;
  }
  
  
  public String toJSON()
  {
    StringBuilder vJSONString = null;
    
    vJSONString = new StringBuilder(255);
    
    vJSONString.append("\"" + oClassType);
    vJSONString.append("|" + oValue + "\"");
    
    return vJSONString.toString();
  }

}

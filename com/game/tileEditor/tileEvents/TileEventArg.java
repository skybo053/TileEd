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
  
  
  public String toJson()
  {
    StringBuilder vStringBuilder = null;
    
    vStringBuilder = new StringBuilder(255);
    
    vStringBuilder.append("\"" + oClassType);
    vStringBuilder.append("|" + oValue + "\"");
    
    return vStringBuilder.toString();
  }

}

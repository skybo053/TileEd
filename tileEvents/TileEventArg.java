package tileEvents;

public class TileEventArg 
{
  private String oClassType  = null;
  private String oValue      = null;
  
  
  public TileEventArg(String pClassType, String pValue)
  {
    oClassType  = pClassType;
    oValue      = pValue;
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

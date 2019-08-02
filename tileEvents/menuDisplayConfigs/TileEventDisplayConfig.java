package tileEvents.menuDisplayConfigs;

import java.util.ArrayList;

import javafx.scene.layout.HBox;
import tileEvents.TileEventArg;

public interface TileEventDisplayConfig 
{
  public HBox getRowNode(int pRowIndex);
  
  public String getTileEventClassName();
  
  public int getRowCount();
  
  public void setTextFieldArgValues(ArrayList<TileEventArg> pTileEventArgs);

}

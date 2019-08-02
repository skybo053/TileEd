package tileEvents.menuDisplayConfigs;

import java.util.ArrayList;
import java.util.TreeMap;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import tileEvents.TileEventArg;

public class MoveEventDisplayConfig implements TileEventDisplayConfig
{
  private static final String CLASS_NAME    = "com.game.TileEvents.events.MoveEvent";
  private static final String ARG_TYPE_NAME = "java.lang.Integer";
  
  private static final double TEXT_FIELD_WIDTH = 50.0;
  
  private Label     oArgTypeOneLabel      = null;
  private Label     oArgTypeTwoLabel      = null;
  
  private TextField oArgValueOneTextField = null;
  private TextField oArgValueTwoTextField = null;
  
  private TreeMap<Integer, HBox> oRowNodes = null;
  private HBox                   oHBoxRow1 = null;
  private HBox                   oHBoxRow2 = null;
  
  ArrayList<TextField> oTextFields = null;
  
  
  public MoveEventDisplayConfig()
  {
    oArgTypeOneLabel      = new Label(ARG_TYPE_NAME);
    oArgTypeTwoLabel      = new Label(ARG_TYPE_NAME);
    oArgValueOneTextField = new TextField();
    oArgValueTwoTextField = new TextField();
    oTextFields           = new ArrayList<TextField>();
    oRowNodes             = new TreeMap<Integer, HBox>();
    oHBoxRow1             = new HBox();
    oHBoxRow2             = new HBox();
    
    oTextFields.add(oArgValueOneTextField);
    oTextFields.add(oArgValueTwoTextField);
    
    oArgValueOneTextField.setPrefWidth(TEXT_FIELD_WIDTH);
    oArgValueTwoTextField.setPrefWidth(TEXT_FIELD_WIDTH);
    
    oHBoxRow1.setAlignment(Pos.CENTER);
    oHBoxRow2.setAlignment(Pos.CENTER);
    
    HBox.setMargin(oArgValueOneTextField, new Insets(3,0,3,20));
    HBox.setMargin(oArgValueTwoTextField, new Insets(3,0,3,20));
    
    oHBoxRow1.getChildren().addAll(oArgTypeOneLabel, oArgValueOneTextField);
    oHBoxRow2.getChildren().addAll(oArgTypeTwoLabel, oArgValueTwoTextField);
    
    oRowNodes.put(0, oHBoxRow1);
    oRowNodes.put(1, oHBoxRow2);
  }
  
  
  public HBox getRowNode(int pRowIndex)
  {
    return oRowNodes.get(pRowIndex);
  }
  
  
  public int getRowCount()
  {
    return oRowNodes.size();
  }
  
  
  public void setTextFieldArgValues(ArrayList<TileEventArg> pTileEventArgs)
  {
    TileEventArg vTileEventArg = null;
    TextField    vTextField   = null;
    
    for(int vIndex = 0; vIndex < oTextFields.size(); ++vIndex)
    {
      vTileEventArg = pTileEventArgs.get(vIndex);
      vTextField    = oTextFields.get(vIndex);
      
      vTextField.setText(vTileEventArg.getArgValue());
    }
  }
  
  
  public String getTileEventClassName()
  {
    return CLASS_NAME;
  }
}

package com.game.tileEditor;

import com.game.utilities.SceneUtils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;


public class AddRemoveTilesMenu extends GridPane 
{
  private static final double BUTTON_WIDTH = 95.0;
  
  private TileEditor oTileEditor         = null;
  
  private Button     oAddRowButton       = null;
  private Button     oDeleteRowButton    = null;
  private Button     oAddColumnButton    = null;
  private Button     oDeleteColumnButton = null;
  
  private HBox oRowHBox    = null;
  private HBox oColumnHBox = null;
  
  
  public AddRemoveTilesMenu(TileEditor pTileEditor)
  {
    oTileEditor         = pTileEditor;
        
    oAddRowButton       = new Button("Add Row");
    oDeleteRowButton    = new Button("Delete Row");
    oAddColumnButton    = new Button("Add Column");
    oDeleteColumnButton = new Button("Delete Column");
    
    oRowHBox    = new HBox();
    oColumnHBox = new HBox();
    
    SceneUtils.setColumnConstraints(this, 1);
    SceneUtils.setRowConstraints(this, 2);
    
    configureHBoxes();
    configureButtons();
    addButtons();
    addHBoxes();
  }
  
  
  private void configureHBoxes()
  {
    oRowHBox.setAlignment(Pos.CENTER);
    oColumnHBox.setAlignment(Pos.CENTER);
  }
  
  
  private void configureButtons()
  {
    oAddRowButton.setPrefWidth(BUTTON_WIDTH);
    oDeleteRowButton.setPrefWidth(BUTTON_WIDTH);
    oAddColumnButton.setPrefWidth(BUTTON_WIDTH);
    oDeleteColumnButton.setPrefWidth(BUTTON_WIDTH);
    
    HBox.setMargin(oAddRowButton,       new Insets(3,1,2,0));
    HBox.setMargin(oDeleteRowButton,    new Insets(3,0,2,1));
    HBox.setMargin(oAddColumnButton,    new Insets(1,1,1,0));
    HBox.setMargin(oDeleteColumnButton, new Insets(1,0,1,0));
    
    oAddRowButton.setOnMouseClicked(pMouseEvent->
    {
      oTileEditor.addTileRow();
    });
    
    oAddColumnButton.setOnMouseClicked(pMouseEvent->
    {
      oTileEditor.addTileColumn();
    });
    
    oDeleteRowButton.setOnMouseClicked(pMouseEvent->
    {
      oTileEditor.deleteTileRow();
    });
    
    oDeleteColumnButton.setOnMouseClicked(pMouseEvent->
    {
      oTileEditor.deleteTileColumn();
    });
    
    disableButtons();
  }
  
  
  public void disableButtons()
  {
    oAddRowButton.setDisable(true);
    oDeleteRowButton.setDisable(true);
    oAddColumnButton.setDisable(true);
    oDeleteColumnButton.setDisable(true);
  }
  
  
  public void enableButtons()
  {
    oAddRowButton.setDisable(false);
    oDeleteRowButton.setDisable(false);
    oAddColumnButton.setDisable(false);
    oDeleteColumnButton.setDisable(false);
  }
  
  
  private void addButtons()
  {
    oRowHBox.getChildren().addAll(oAddRowButton, oDeleteRowButton);
    oColumnHBox.getChildren().addAll(oAddColumnButton, oDeleteColumnButton);
  }
  
  
  private void addHBoxes()
  {
    add(oRowHBox, 0, 0);
    add(oColumnHBox, 0, 1);
  }

}

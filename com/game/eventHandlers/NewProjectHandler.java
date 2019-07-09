package com.game.eventHandlers;

import com.game.tileEditor.TileEditor;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class NewProjectHandler implements EventHandler<ActionEvent>
{
  private static final double WIDTH   = 220.0;
  private static final double HEIGHT  = 150.0;
  
  private TextField  oRowTextField    = null;
  private TextField  oColumnTextField = null;
  
  private Label      oRowLabel        = null;
  private Label      oColumnLabel     = null;
  
  private TileEditor oTileEditor      = null;
  
  private Stage      oStage           = null;
  
  
  public NewProjectHandler(TileEditor pTileEditor)
  {
    oTileEditor      = pTileEditor;
    
    oRowTextField    = new TextField();
    oColumnTextField = new TextField();
    oRowLabel        = new Label("Rows ");
    oColumnLabel     = new Label("Columns ");
  }
  
  
  public void handle(ActionEvent pActionEvent) 
  {
    if(oStage == null)
    {
      oStage  = new Stage();
      
      oStage.setTitle("Map Grid");
      oStage.setWidth(WIDTH);
      oStage.setHeight(HEIGHT);
      oStage.setScene(createSelectMapDimensionScene());
      oStage.setResizable(false);
      oStage.initModality(Modality.APPLICATION_MODAL);
      oStage.setOnCloseRequest(new EnterMapDimensionClose());
    }
    
    oStage.show();
  }
  
  
  private Scene createSelectMapDimensionScene()
  {
    GridPane   vGridPane   = null;
    Button     vButton     = null;
    
    vButton   = new Button("Enter");
    vGridPane = new GridPane();
    
    vGridPane.setVgap(5.0);
    
    oRowTextField.setPrefWidth(100.0);
    oColumnTextField.setPrefWidth(100.0);
    
    vButton.setPrefWidth(80.0);
    vButton.setPrefHeight(30.0);
    vButton.setOnAction(new EnterMapDimensionsButton());
    GridPane.setHalignment(vButton, HPos.CENTER);
    
    vGridPane.add(oRowLabel, 0, 0);
    vGridPane.add(oRowTextField, 1, 0);
    
    vGridPane.add(oColumnLabel, 0, 1);
    vGridPane.add(oColumnTextField, 1, 1);
    
    vGridPane.add(vButton, 1, 5);
    
    return new Scene(vGridPane);
  }
  
  
  private void clearTextFields()
  {
    oRowTextField.clear();
    oColumnTextField.clear();
  }
  
  
  private class EnterMapDimensionsButton implements EventHandler<ActionEvent>
  {
    public void handle(ActionEvent pActionEvent)
    {
      Integer vRows    = null;
      Integer vColumns = null;
      
      try
      {
        vRows    = Integer.parseInt(oRowTextField.getText().trim());
        vColumns = Integer.parseInt(oColumnTextField.getText().trim());
        
        oTileEditor.clearMapGrid();
        oTileEditor.createMapGrid(vRows, vColumns);
        
        clearTextFields();
        oStage.close();
      }
      catch(NumberFormatException pNumberFormatException)
      {
        
      }
    }
  }
  
  
  private class EnterMapDimensionClose implements EventHandler<WindowEvent>
  {
    public void handle(WindowEvent pWindowEvent)
    {
      clearTextFields();
    }
  }
  
}

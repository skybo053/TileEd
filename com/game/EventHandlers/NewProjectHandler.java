package com.game.EventHandlers;

import com.game.TileEditor.TileEditor;

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


public class NewProjectHandler implements EventHandler<ActionEvent>
{
  private TextField  oRowTextField    = null;
  private TextField  oColumnTextField = null;
  
  private Label      oRowLabel        = null;
  private Label      oColumnLabel     = null;
  
  private TileEditor oTileEditor      = null;
  
  private Stage      oStage           = null;
  
  
  public NewProjectHandler(TileEditor pTileEditor)
  {
    oTileEditor      = pTileEditor;
    
    oStage           = new Stage();
    
    oRowTextField    = new TextField();
    oColumnTextField = new TextField();
    oRowLabel        = new Label("Rows ");
    oColumnLabel     = new Label("Columns ");
  }
  
  
  public void handle(ActionEvent pActionEvent) 
  {
    oStage.setTitle("Enter Starting Grid Size");
    oStage.setWidth(220.0);
    oStage.setHeight(150.0);
    oStage.setScene(createSelectMapDimensionScene());
    oStage.setResizable(false);
    
    if(oStage.getModality() == Modality.NONE)
    {
      oStage.initModality(Modality.APPLICATION_MODAL);
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
      }
      catch(NumberFormatException pNumberFormatException)
      {
        vRows    = 0;
        vColumns = 0;
      }
      finally
      {
        oTileEditor.clearMapGrid();
        oTileEditor.createMapGrid(vRows, vColumns);
        
        oRowTextField.clear();
        oColumnTextField.clear();
        
        oStage.close();
      }
    }
  }
  
}

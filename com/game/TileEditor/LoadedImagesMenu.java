package com.game.TileEditor;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class LoadedImagesMenu extends BorderPane
{
  private static final double WIDTH  = 400.0;
  private static final double HEIGHT = 300.0;
  
  private static final double BUTTON_GRIDPANE_HEIGHT = 50.0;
  private static final double BUTTON_HEIGHT          = 20.0;
  private static final double BUTTON_WIDTH           = 80.0;
  
  
  public LoadedImagesMenu()
  {
    GridPane   vNameImageGridPane = null;
    GridPane   vButtonGridPane    = null;
    
    setPrefHeight(HEIGHT);
    setPrefWidth(WIDTH);
    
    vNameImageGridPane = getNameImageGridPane();
    vButtonGridPane    = getButtonGridPane();
    
    setCenter(vNameImageGridPane);
    setBottom(vButtonGridPane);
  }
  
  
  private GridPane getNameImageGridPane()
  {
    GridPane vGridPane = null;
    
    vGridPane = new GridPane();
    
    setColumnConstraints(vGridPane);
    
    vGridPane.add(new Label("Image Name"), 0, 0);
    vGridPane.add(new Label("Image"), 1, 0);
    
    vGridPane.setStyle("-fx-background-color: cyan;");
 
    
    return vGridPane;
  }
  
  
  private GridPane getButtonGridPane()
  {
    GridPane vGridPane     = null;
    Button   vAddButton    = null;
    Button   vDeleteButton = null;
    
    vGridPane     = new GridPane();
    vAddButton    = new Button("Add");
    vDeleteButton = new Button("Delete");
    
    vGridPane.setPrefHeight(BUTTON_GRIDPANE_HEIGHT);
    
    GridPane.setHalignment(vAddButton, HPos.CENTER);
    GridPane.setHalignment(vDeleteButton, HPos.CENTER);
    
    GridPane.setValignment(vAddButton, VPos.CENTER);
    GridPane.setValignment(vDeleteButton, VPos.CENTER);
    
    setColumnConstraints(vGridPane);
    setRowConstraints(vGridPane);
    
    vAddButton.setPrefHeight(BUTTON_HEIGHT);
    vAddButton.setPrefWidth(BUTTON_WIDTH);
    
    vDeleteButton.setPrefHeight(BUTTON_HEIGHT);
    vDeleteButton.setPrefWidth(BUTTON_WIDTH);
    
    vGridPane.add(vAddButton, 0, 0);
    vGridPane.add(vDeleteButton, 1, 0);
    
    return vGridPane;
  }
  
  
  private void setColumnConstraints(GridPane pGridPane)
  {
    ColumnConstraints vColumn1 = null;
    ColumnConstraints vColumn2 = null;
    
    vColumn1 = new ColumnConstraints();
    vColumn2 = new ColumnConstraints();
    
    vColumn1.setPercentWidth(50);
    vColumn2.setPercentWidth(50);
    pGridPane.getColumnConstraints().addAll(vColumn1, vColumn2);
  }
  
  
  private void setRowConstraints(GridPane pGridPane)
  {
    RowConstraints vRow1 = null;
    
    vRow1 = new RowConstraints();
    
    vRow1.setPercentHeight(100);
    
    pGridPane.getRowConstraints().addAll(vRow1);
  }  
  
}

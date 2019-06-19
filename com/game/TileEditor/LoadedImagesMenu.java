package com.game.TileEditor;

import com.game.EventHandlers.LoadImagesHandler;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;


public class LoadedImagesMenu extends BorderPane
{
  private static final double WIDTH  = 400.0;
  private static final double HEIGHT = 300.0;
  
  private static final double BUTTON_GRIDPANE_HEIGHT = 50.0;
  private static final double BUTTON_HEIGHT          = 20.0;
  private static final double BUTTON_WIDTH           = 80.0;
  
  private int oRowCount = 0;
  
  
  public LoadedImagesMenu()
  {
    GridPane   vNameImageGridPane   = null;
    GridPane   vButtonGridPane      = null;
    ScrollPane vNameImageScrollPane = null;
    
    vNameImageScrollPane = new ScrollPane();
    
    setPrefHeight(HEIGHT);
    setPrefWidth(WIDTH);
    
    vNameImageGridPane   = getNameImageGridPane();
    vButtonGridPane      = getButtonGridPane();
    vNameImageScrollPane = getNameImageScrollPane();
    
    vNameImageScrollPane.setContent(vNameImageGridPane);
    setCenter(vNameImageScrollPane);
    setBottom(vButtonGridPane);
  }
  
  
  private ScrollPane getNameImageScrollPane()
  {
    ScrollPane vScrollPane = null;
    
    vScrollPane = new ScrollPane();
    
    vScrollPane.setFitToHeight(true);
    vScrollPane.setFitToWidth(true);
    vScrollPane.getStyleClass().clear();
    
    return vScrollPane;
  }
  
  
  private GridPane getNameImageGridPane()
  {
    GridPane vGridPane = null;
    
    vGridPane = new GridPane();
    
    setColumnConstraints(vGridPane);
    
    for(int i = 0; i < 4; ++i)
    {
      StackPane vStackPaneName  = null;
      StackPane vStackPaneImage = null;
      
      vStackPaneName  = new StackPane();
      vStackPaneImage = new StackPane();
      
      if(oRowCount % 2 == 0)
      {
        vStackPaneName.setStyle("-fx-background-color: #f4f5f7;");
        vStackPaneImage.setStyle("-fx-background-color: #f4f5f7;");
      }
      else
      {
        vStackPaneName.setStyle("-fx-background-color: white;");
        vStackPaneImage.setStyle("-fx-background-color: white;");
      }
      
      vStackPaneName.getChildren().add(new Label("Image Name"));
      vStackPaneImage.getChildren().add(new Label("Image"));
      
      vGridPane.add(vStackPaneName, 0, i);
      vGridPane.add(vStackPaneImage, 1, i);
      
      ++oRowCount;
    }
 
    
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
    vAddButton.setOnMouseClicked(new LoadImagesHandler(this));
    
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

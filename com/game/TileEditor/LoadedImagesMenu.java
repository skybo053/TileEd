package com.game.TileEditor;

import java.util.HashMap;
import java.util.TreeMap;

import com.game.EventHandlers.LoadImagesHandler;
import com.game.Utilities.SceneUtils;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class LoadedImagesMenu extends BorderPane
{
  private static final double WIDTH  = 400.0;
  private static final double HEIGHT = 300.0;
  
  private static final double BUTTON_GRIDPANE_HEIGHT = 50.0;
  private static final double BUTTON_HEIGHT          = 20.0;
  private static final double BUTTON_WIDTH           = 80.0;
  
  private GridPane               oNameImageGridPane = null;
  private TreeMap<String, Image> oImageMap          = null;
  
  private int oRowCount = 0;
  
  
  public LoadedImagesMenu()
  {
    GridPane   vButtonGridPane      = null;
    ScrollPane vNameImageScrollPane = null;
    
    vNameImageScrollPane = new ScrollPane();
    oImageMap            = new TreeMap<String, Image>();
    
    setPrefHeight(HEIGHT);
    setPrefWidth(WIDTH);
    
    oNameImageGridPane   = getNameImageGridPane();
    vButtonGridPane      = getButtonGridPane();
    vNameImageScrollPane = getNameImageScrollPane();
    
    vNameImageScrollPane.setContent(oNameImageGridPane);
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
    
    SceneUtils.setColumnConstraints(vGridPane, 2);
    
    return vGridPane;
  }
  
  
  public void addRow(String pImageName, Image pImage)
  {
    StackPane vStackPaneName  = null;
    StackPane vStackPaneImage = null;
    
    try
    {
      vStackPaneName  = new StackPane();
      vStackPaneImage = new StackPane();
      
      vStackPaneName.setPadding(new Insets(1,0,1,0));
      vStackPaneImage.setPadding(new Insets(1,0,1,0));
      
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
      
      SceneUtils.addToPane(vStackPaneName, new Label(pImageName));
      
      if(pImage != null)
      {
        SceneUtils.addToPane(vStackPaneImage, new ImageView(pImage));
      }
      
      oNameImageGridPane.add(vStackPaneName, 0, oRowCount);
      oNameImageGridPane.add(vStackPaneImage, 1, oRowCount);
      
      oImageMap.put(pImageName, pImage);
      
      ++oRowCount;
    }
    catch(NullPointerException e)
    {
      e.printStackTrace();
    }
  }
  
  
  public Image getImage(String pImageName)
  {
    return oImageMap.get(pImageName);
  }
  
  
  public boolean containsKey(String pImageName)
  {
    return oImageMap.containsKey(pImageName);
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
    
    SceneUtils.setColumnConstraints(vGridPane, 2);
    SceneUtils.setRowConstraints(vGridPane, 1);
    
    vAddButton.setPrefHeight(BUTTON_HEIGHT);
    vAddButton.setPrefWidth(BUTTON_WIDTH);
    vAddButton.setOnMouseClicked(new LoadImagesHandler(this));
    
    vDeleteButton.setPrefHeight(BUTTON_HEIGHT);
    vDeleteButton.setPrefWidth(BUTTON_WIDTH);
    
    vGridPane.add(vAddButton, 0, 0);
    vGridPane.add(vDeleteButton, 1, 0);
    
    return vGridPane;
  }
  
}

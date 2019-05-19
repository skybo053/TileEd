package com.game.TileEditor;

import java.util.ArrayList;

import com.game.EventHandlers.Quit_Handler;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class TileEditor extends Application
{
  private static final String TITLE              = "TEd 1.0.0";
  private static final String SELECTED_GRID_CELL = "selected-grid-cell";
  private static final String GRID_CELL          = "grid-cell";
  private static final double SCENE_WIDTH        = 800.0;
  private static final double SCENE_HEIGHT       = 600.0;
  
  private Scene      oScene          = null;
  private MenuBar    oMenuBar        = null;
  private Menu       oFileMenu       = null;
  private BorderPane oBorderPane     = null;
  private GridPane   oGridPane       = null;
  private GridPane   oSideGridPane   = null;
  
  private Tile oPreviousSelectedTile = null;
  
  
  public static void main(String[] pArgs) 
  {
    launch(pArgs);
  }
  
  
  public void start(Stage pStage) throws Exception 
  {
    initialize();
    
    pStage.setTitle(TITLE);
    pStage.setWidth(SCENE_WIDTH);
    pStage.setHeight(SCENE_HEIGHT);
    pStage.setScene(oScene);
    pStage.show();
  }
  

  private void initialize()
  {
    oBorderPane   = new BorderPane();
    oMenuBar      = new MenuBar();
    oFileMenu     = new Menu("File");
    oGridPane     = new GridPane();
    oSideGridPane = new GridPane();
   
    
    createMenuItems();
    createGridPane();
    createSideGridPane();
    
    oBorderPane.setTop(oMenuBar);
    oBorderPane.setCenter(oGridPane);
    oBorderPane.setRight(oSideGridPane);
    
    oScene = new Scene(oBorderPane);
    oScene.getStylesheets().add("file:Resources/CSS/stylesheet.css");
  }


  private void createMenuItems()
  {
    MenuItem vMenuItem = null;
    
    vMenuItem = new MenuItem("Save");
    oFileMenu.getItems().add(vMenuItem);
    
    vMenuItem = new MenuItem("Load");
    oFileMenu.getItems().add(vMenuItem);
    
    vMenuItem = new MenuItem("Quit");
    vMenuItem.setOnAction(new Quit_Handler());
    oFileMenu.getItems().add(vMenuItem);
    
    
    oMenuBar.getMenus().add(oFileMenu);
  }
  
  
  private void createGridPane()
  {
    Tile            vTile   = null;
    ArrayList<Node> vImages = null;
    
    try
    {
      vImages = new ArrayList<Node>();
      
      vTile = new Tile(
          35,
          35,
          false,
          "file:Resources/Images/grass.png",
          TileImage.GRASS);
      
      setTileOnMouseClick(vTile);
      
      vImages.add(vTile);
       
      vTile = new Tile(
          35,
          35,
          true,
          "file:Resources/Images/water.png",
          TileImage.WATER);
      
      setTileOnMouseClick(vTile);
      
      vImages.add(vTile);
       
      vTile = new Tile(
          35,
          35,
          false,
          "file:Resources/Images/dirt.png",
          TileImage.DIRT);
      
      setTileOnMouseClick(vTile);
      
      vImages.add(vTile);
       
      oGridPane.addRow(0, vImages.toArray(new Node[vImages.size()]));
    }
    catch(Exception e)
    {
     System.out.println(e.getMessage());
    }
  }
  
  
  private void createSideGridPane()
  {
    Label vLabel = null;
    
    oSideGridPane.setPrefWidth(200);
    oSideGridPane.setStyle("-fx-background-color: pink;");

    vLabel = new Label("Description");
    oSideGridPane.add(vLabel, 0, 0);
  }
  
  
  private void setTileOnMouseClick(Tile pTile)
  {
    pTile.setOnMouseClicked(pEvent -> {
      
      Tile  vTile  = null;
      Label vLabel = null;
      
      vTile = (Tile)pEvent.getSource();
      
      if(oPreviousSelectedTile != null)
      {
        oPreviousSelectedTile.setTileStyle(GRID_CELL);
      }
      
      oPreviousSelectedTile = vTile;
      
      vTile.setTileStyle(SELECTED_GRID_CELL);
      
      vLabel = (Label) oSideGridPane.getChildren().get(0);
      vLabel.setText(vTile.getDescription());
      
      
    });
  }
}
  
  



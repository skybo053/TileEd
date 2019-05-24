package com.game.TileEditor;

import java.util.ArrayList;

import com.game.EventHandlers.ExitHandler;
import com.game.EventHandlers.NewProjectHandler;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class TileEditor extends Application
{
  private static final String TITLE              = "TEd 1.0.0";
  private static final String STYLE_SHEET        = "file:Resources/CSS/stylesheet.css";
  private static final String SELECTED_GRID_CELL = "selected-grid-cell";
  private static final String GRID_CELL          = "grid-cell";
  private static final String SIDE_PANE          = "side-border-pane";
  private static final double SCENE_WIDTH        = 1000.0;
  private static final double SCENE_HEIGHT       = 800.0;
  private static final int    TILE_LENGTH        = 45;
  
  private Scene      oScene          = null;
  private MenuBar    oMenuBar        = null;
  private Menu       oFileMenu       = null;
  private BorderPane oMainBorderPane = null;
  private BorderPane oSideBorderPane = null;
  private ScrollPane oMainScrollPane = null;
  private GridPane   oMainGridPane   = null;
  private TileMenu   oSideTileMenu   = null;
 
  
  private Tile oCurrentTile  = null;
  private Tile oPreviousTile = null;
  
  
  public static void main(String[] pArgs) 
  {
    launch(pArgs);
  }
  
  
  public void start(Stage pStage) throws Exception 
  {
    initialize();
    buildScene();
    applyStyles();
    
    pStage.setTitle(TITLE);
    pStage.setWidth(SCENE_WIDTH);
    pStage.setHeight(SCENE_HEIGHT);
    pStage.setScene(oScene);
    pStage.show();
  }
  

  private void initialize()
  {
    oMainBorderPane = new BorderPane();
    oSideBorderPane = new BorderPane();
    oMenuBar        = new MenuBar();
    oFileMenu       = new Menu("File");
    oMainGridPane   = new GridPane();
    oMainScrollPane = new ScrollPane();
    oSideTileMenu   = new TileMenu();
    
    buildMenu();
    configureMainGridPane();
    configureSideBorderPane();
  }
  
  
  private void buildScene()
  {
    oScene = new Scene(oMainBorderPane);

    oSideBorderPane.setCenter(oSideTileMenu);
    
    oMainScrollPane.setContent(oMainGridPane);
    
    oMainBorderPane.setTop(oMenuBar);
    oMainBorderPane.setCenter(oMainScrollPane);
    oMainBorderPane.setRight(oSideBorderPane);
  }
  
  
  private void applyStyles()
  {
    oMainScrollPane.getStyleClass().clear();
    
    oSideBorderPane.getStyleClass().add(SIDE_PANE);
    
    oScene.getStylesheets().add(STYLE_SHEET);
  }


  private void buildMenu()
  {
    MenuItem                 vMenuItem  = null;
    ObservableList<MenuItem> vMenuItems = null;
    
    vMenuItems = oFileMenu.getItems();
    
    vMenuItem = new MenuItem("New");
    vMenuItem.setOnAction(new NewProjectHandler(this));
    vMenuItems.add(vMenuItem);
    
    vMenuItem = new MenuItem("Save");
    vMenuItems.add(vMenuItem);
    
    vMenuItem = new MenuItem("Load");
    vMenuItems.add(vMenuItem);
    
    vMenuItem = new MenuItem("Quit");
    vMenuItem.setOnAction(new ExitHandler());
    vMenuItems.add(vMenuItem);
    
    oMenuBar.getMenus().add(oFileMenu);
  }
  
  
  public void clearMapGrid()
  {
    oMainGridPane.getChildren().clear();
  }
  
  
  public void createMapGrid(int pTotalRows, int pTotalColumns)
  {
    ArrayList<Node> vTiles      = null;
    Tile            vTile       = null;
    
    for(int vCurrentRow = 0; vCurrentRow < pTotalRows; ++vCurrentRow)
    {
      vTiles = new ArrayList<Node>();
      
      for(int vCurrentColumn = 0; vCurrentColumn < pTotalColumns; ++vCurrentColumn)
      {
        vTile  = new Tile(TILE_LENGTH, TILE_LENGTH);
        
        setTileOnMouseClick(vTile);
        
        vTiles.add(vTile);
      }
      
      oMainGridPane.addRow(vCurrentRow, vTiles.toArray(new Node[vTiles.size()]));
    }
  }
  
  
  public void showTileMenu()
  {
    oSideTileMenu.setVisible(true);
  }
  
  
  public void hideTileMenu()
  {
    oSideTileMenu.setVisible(false);
  }
  
  
  private void configureMainGridPane()
  {
    Tile            vTile   = null;
    ArrayList<Node> vImages = null;
    
    try
    {
    
      
      vImages = new ArrayList<Node>();
      
      /*vTile = new Tile(
          35,
          35,
          false,
          "file:Resources/Images/grass.png",
          TileImage.GRASS);*/
      
      vTile = new Tile(35, 35);
      
      setTileOnMouseClick(vTile);
      
      vImages.add(vTile);
       
      vTile = new Tile(
          35,
          35,
          true,
          "file:Resources/Images/water.png",
          TileImageType.WATER);
      
      setTileOnMouseClick(vTile);
      
      vImages.add(vTile);
       
      vTile = new Tile(
          35,
          35,
          false,
          "file:Resources/Images/dirt.png",
          TileImageType.DIRT);
      
      setTileOnMouseClick(vTile);
      
      vImages.add(vTile);
       
      oMainGridPane.addRow(0, vImages.toArray(new Node[vImages.size()]));
    }
    catch(Exception e)
    {
     System.out.println(e.getMessage());
    }
  }
  
  
  private void configureSideBorderPane()
  {
    oSideBorderPane.setPrefWidth(200);
    
  }
  
  
  private void setTileOnMouseClick(Tile pTile)
  {
    pTile.setOnMouseClicked(pEvent -> {
      
      oCurrentTile = (Tile)pEvent.getSource();
      
      if(oPreviousTile != null)
      {
        oPreviousTile.setTileStyle(GRID_CELL);
      }
      
      oPreviousTile = oCurrentTile;
      
      oCurrentTile.setTileStyle(SELECTED_GRID_CELL);
      
      displayCurrentTileConfig();
    });
  }
  
  
  public void displayCurrentTileConfig()
  {
    Integer vRowIndex = null;
    Integer vColIndex = null;
    
    vRowIndex = GridPane.getRowIndex(oCurrentTile)    + 1;
    vColIndex = GridPane.getColumnIndex(oCurrentTile) + 1;
    
    oSideTileMenu.setRow(vRowIndex.toString());
    oSideTileMenu.setColumn(vColIndex.toString());
    oSideTileMenu.setImage(oCurrentTile.getTileImageName());
    oSideTileMenu.setIsSolid(oCurrentTile.isSolid().toString());
    oSideTileMenu.setEvents(oCurrentTile.getTileEvents());
  }
  
}
  
  



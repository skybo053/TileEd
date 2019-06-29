package com.game.TileEditor;

import java.util.ArrayList;

import com.game.EventHandlers.ExitHandler;
import com.game.EventHandlers.NewProjectHandler;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
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
  private static final double SIDE_PANE_WIDTH    = 200.0;
  public  static final int    TILE_PANE_LENGTH   = 37;
  public  static final int    TILE_IMAGE_LENGTH  = 35;
  
  private Scene            oScene                     = null;
  private MenuBar          oMenuBar                   = null;
  private Menu             oFileMenu                  = null;
  private BorderPane       oMainBorderPane            = null;
  private ScrollPane       oMainScrollPane            = null;
  private GridPane         oMainGridPane              = null;
  private TileMenu         oSideTileMenu              = null;
  private TileEditableMenu oSideEditableTileMenu      = null;
  private FlowPane         oSideFlowPane              = null;
  private TitledPane       oTileAttribsTitledPane     = null;
  private TitledPane       oEditTileAttribsTitledPane = null;
  private Scene            oLoadedImagesMenuScene     = null;
  private LoadedImagesMenu oLoadedImagesMenu          = null;
  private Stage            oLoadedImagesMenuStage     = null;
  
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
    oLoadedImagesMenuStage     = new Stage();
    oLoadedImagesMenu          = new LoadedImagesMenu(this);
    oLoadedImagesMenuScene     = new Scene(oLoadedImagesMenu);
    oMainBorderPane            = new BorderPane();
    oMenuBar                   = new MenuBar();
    oFileMenu                  = new Menu("File");
    oMainGridPane              = new GridPane();
    oMainScrollPane            = new ScrollPane();
    oSideTileMenu              = new TileMenu();
    oSideEditableTileMenu      = new TileEditableMenu(this);
    oSideFlowPane              = new FlowPane();
    oTileAttribsTitledPane     = new TitledPane();
    oEditTileAttribsTitledPane = new TitledPane();
    
    buildMenu();
    configureLoadedImagesMenuStage();
    configureMainBorderPane();
    configureMainGridPane();
    configureSideFlowPane();
    configureTileAttribTitlePane();
    configureEditTileAttribsTitlePane();
  }
  
  
  private void configureLoadedImagesMenuStage()
  {
    oLoadedImagesMenuStage.setTitle("Loaded Images");
    oLoadedImagesMenuStage.setScene(oLoadedImagesMenuScene);
    oLoadedImagesMenuStage.setResizable(false);
    oLoadedImagesMenuStage.initModality(Modality.APPLICATION_MODAL);
    
    oLoadedImagesMenuStage.setOnCloseRequest(pWindowEvent->
    {
      clearLoadedImagesMenuSelectedRow();
    });
  }
  
  
  public void closeLoadedImagesMenu()
  {
    oLoadedImagesMenuStage.close();
  }
  
  
  public void showLoadedImagesMenu()
  {
    oLoadedImagesMenuStage.show();
  }
  
  
  private void buildScene()
  {
    oScene = new Scene(oMainBorderPane);
    
    oSideFlowPane.getChildren().add(oTileAttribsTitledPane);
    oSideFlowPane.getChildren().add(oEditTileAttribsTitledPane);
    
    oMainScrollPane.setContent(oMainGridPane);
    
    oMainBorderPane.setTop(oMenuBar);
    oMainBorderPane.setCenter(oMainScrollPane);
    oMainBorderPane.setRight(oSideFlowPane);
  }
  
  
  private void applyStyles()
  {
    oMainScrollPane.getStyleClass().clear();
    
    oSideFlowPane.getStyleClass().add(SIDE_PANE);
    
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
        vTile  = new Tile();
        
        vTile.setOnMouseClicked(new TileClickHandler());
        
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
  
  
  public void clearLoadedImagesMenuSelectedRow()
  {
    oLoadedImagesMenu.clearSelectedRow();
  }
  
  
  private void configureMainBorderPane()
  {
    oMainBorderPane.setOnMouseClicked(pMouseEvent ->
    {
      if(oCurrentTile != null)
      {
        oCurrentTile.setTileStyle(GRID_CELL);
        
        oSideTileMenu.clearAttributeValues();
        oSideEditableTileMenu.clearAttributeValues();
        
        oCurrentTile = null;
      }
    });
  }
  
  
  private void configureMainGridPane()
  {
    Tile            vTile   = null;
    ArrayList<Node> vImages = null;
    
    try
    {
      vImages = new ArrayList<Node>();
      
      vTile   = new Tile();
      
      vTile.setOnMouseClicked(new TileClickHandler());
      
      vImages.add(vTile);
       
      vTile = new Tile(
          true,
          "file:Resources/Images/water.png",
          "water");
      
      vTile.setOnMouseClicked(new TileClickHandler());
      
      vImages.add(vTile);
       
      vTile = new Tile(
          false,
          "file:Resources/Images/dirt.png",
          "dirt");
      
      vTile.setOnMouseClicked(new TileClickHandler());
      
      vImages.add(vTile);
       
      oMainGridPane.addRow(0, vImages.toArray(new Node[vImages.size()]));
    }
    catch(Exception e)
    {
     System.out.println(e.getMessage());
    }
  }
  
  
  private void configureSideFlowPane()
  {
    oSideFlowPane.setPrefWidth(SIDE_PANE_WIDTH);
  }
  
  
  private void configureTileAttribTitlePane()
  {
    oTileAttribsTitledPane.setContent(oSideTileMenu);
    oTileAttribsTitledPane.setText("Tile Attributes");
    oTileAttribsTitledPane.setPrefWidth(SIDE_PANE_WIDTH);
  }
  
  
  private void configureEditTileAttribsTitlePane()
  {
    oEditTileAttribsTitledPane.setContent(oSideEditableTileMenu);
    oEditTileAttribsTitledPane.setText("Edit Tile");
    oEditTileAttribsTitledPane.setPrefWidth(SIDE_PANE_WIDTH);
  }
  
  
  private void displayCurrentTileConfig()
  {
    Integer vRowIndex   = null;
    Integer vColIndex   = null;
    Double vTileWidth   = null;
    Double vTileHeight  = null;
    
    vRowIndex   = GridPane.getRowIndex(oCurrentTile);
    vColIndex   = GridPane.getColumnIndex(oCurrentTile);
    
    vTileWidth  = oCurrentTile.getTileWidth();
    vTileHeight = oCurrentTile.getTileHeight();
    
    oSideTileMenu.setRow(vRowIndex.toString());
    oSideTileMenu.setColumn(vColIndex.toString());
    oSideTileMenu.setWidth(vTileWidth.toString());
    oSideTileMenu.setHeight(vTileHeight.toString());
    oSideTileMenu.setImage(oCurrentTile.getTileImageName());
    oSideTileMenu.setIsSolid(oCurrentTile.isSolid().toString());
    oSideTileMenu.setEvents(oCurrentTile.getTileEvents());
  }

  
  private void displayEditableTileConfig()
  {
    oSideEditableTileMenu.setIsSolid(oCurrentTile.isSolid().toString());
    oSideEditableTileMenu.setImageView(oCurrentTile.getTileImage());
  }
  
  
  public Tile getCurrentTile()
  {
    return oCurrentTile;
  }
  
  
  public void setIsSolidReferences(Boolean pIsSolid)
  {
    oCurrentTile.setIsSolid(pIsSolid);

    oSideTileMenu.setIsSolid(pIsSolid.toString());
  }
 
  
  public void setImageViewReferences(String pTileImageName, Image pImage)
  {
    oCurrentTile.setTileImageName(pTileImageName);
    oCurrentTile.setTileImage(pImage);

    oSideTileMenu.setImage(pTileImageName);
    oSideEditableTileMenu.setImageView(pImage);
  }
  
  //Tile Handler class
  private class TileClickHandler implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      oCurrentTile = (Tile)pMouseEvent.getSource();
      
      oSideEditableTileMenu.clearAttributeValues();
      
      if(oPreviousTile != null)
      {
        oPreviousTile.setTileStyle(GRID_CELL);
      }
      
      oCurrentTile.setTileStyle(SELECTED_GRID_CELL);
      
      displayCurrentTileConfig();
      displayEditableTileConfig();
      
      oPreviousTile = oCurrentTile;
      
      pMouseEvent.consume();
    }
  }
  
  
}
  
  



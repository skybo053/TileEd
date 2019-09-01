package com.game.tileEditor;

import java.util.ArrayList;
import java.util.Iterator;

import com.game.eventHandlers.ExitHandler;
import com.game.eventHandlers.LoadMenuHandler;
import com.game.eventHandlers.NewProjectHandler;
import com.game.eventHandlers.TileClickHandler;
import com.game.tileEditor.tileEvents.TileEvent;
import com.game.utilities.SceneUtils;

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
import javafx.stage.Window;


public class TileEditor extends Application
{
  private static final String TITLE              = "TEd 1.0.0";
  private static final String STYLE_SHEET        = "file:Resources/CSS/stylesheet.css";
  public  static final String SELECTED_GRID_CELL = "selected-grid-cell";
  public  static final String GRID_CELL          = "grid-cell";
  private static final String SIDE_PANE          = "side-border-pane";
  private static final double SCENE_WIDTH        = 1200.0;
  private static final double SCENE_HEIGHT       = 900.0;
  private static final double SIDE_PANE_WIDTH    = 200.0;
  public  static final int    TILE_PANE_LENGTH   = 37;
  public  static final int    TILE_IMAGE_LENGTH  = 35;
  
  private Scene              oScene                     = null;
  private MenuBar            oMenuBar                   = null;
  private Menu               oFileMenu                  = null;
  private BorderPane         oMainBorderPane            = null;
  private ScrollPane         oMainScrollPane            = null;
  private GridPane           oMainGridPane              = null;
  private TileMenu           oTileMenu                  = null;
  private EditableTileMenu   oEditableTileMenu          = null;
  private AddRemoveTilesMenu oAddRemoveTilesMenu        = null;
  private FlowPane           oSideFlowPane              = null;
  private TitledPane         oTileAttribsTitledPane     = null;
  private TitledPane         oEditTileAttribsTitledPane = null;
  private TitledPane         oAddRemoveTilesTitledPane  = null;
  private Scene              oLoadedImagesMenuScene     = null;
  private LoadedImagesMenu   oLoadedImagesMenu          = null;
  private Stage              oLoadedImagesMenuStage     = null;
  private TileClickHandler   oTileClickHandler          = null;
  
  private Tile oCurrentTile  = null;
  private Tile oPreviousTile = null;

  private int oMapGridRows    = 0;
  private int oMapGridColumns = 0;
  
  
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
    oTileMenu                  = new TileMenu(this);
    oEditableTileMenu          = new EditableTileMenu(this);
    oAddRemoveTilesMenu        = new AddRemoveTilesMenu(this);
    oSideFlowPane              = new FlowPane();
    oTileAttribsTitledPane     = new TitledPane();
    oEditTileAttribsTitledPane = new TitledPane();
    oAddRemoveTilesTitledPane  = new TitledPane();
    oTileClickHandler          = new TileClickHandler(this);
    
    buildMenu();
    configureLoadedImagesMenuStage();
    configureMainBorderPane();
    configureMainGridPane();
    configureSideFlowPane();
    configureTileAttribTitlePane();
    configureEditTileAttribsTitlePane();
    configureAddRemoveTilesTitlePane();
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
    
    oSideFlowPane.getChildren().addAll(
        oTileAttribsTitledPane, 
        oEditTileAttribsTitledPane,
        oAddRemoveTilesTitledPane);
    
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
  
  
  public Window getMainSceneWindow()
  {
    if(oScene == null)
    {
      return null;
    }
    else
    {
      return oScene.getWindow();
    }
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
    
    vMenuItem = new MenuItem("Load Map");
    vMenuItem.setOnAction(new LoadMenuHandler(this));
    vMenuItems.add(vMenuItem);
    
    vMenuItem = new MenuItem("Load Images");
    vMenuItem.setOnAction(pEvent->
    {
      showLoadedImagesMenu();
    });
    vMenuItems.add(vMenuItem);
    
    vMenuItem = new MenuItem("Quit");
    vMenuItem.setOnAction(new ExitHandler());
    vMenuItems.add(vMenuItem);
    
    oMenuBar.getMenus().add(oFileMenu);
  }
  
  
  public void clearMapGrid()
  {
    SceneUtils.clearPane(oMainGridPane);
    
    oMapGridRows    = 0;
    oMapGridColumns = 0;
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
        
        vTile.setOnMouseClicked(oTileClickHandler);
        
        vTiles.add(vTile);
      }
      
      oMainGridPane.addRow(vCurrentRow, vTiles.toArray(new Node[vTiles.size()]));
    }
    
    enableAddRemoveTileButtons();
    setMapGridRowCount(pTotalRows);
    setMapGridColumnCount(pTotalColumns);
  }
  
  
  public void enableAddRemoveTileButtons()
  {
    oAddRemoveTilesMenu.enableButtons();
  }
  
  
  public void setMapGridRowCount(int pMapGridRows)
  {
    oMapGridRows = pMapGridRows;
  }
  
  
  public void setMapGridColumnCount(int pMapGridColumns)
  {
    oMapGridColumns = pMapGridColumns;
  }
  
  
  public void addLoadedMapRow(ArrayList<Tile> pTileRow, int pRowIndex)
  {
    oMainGridPane.addRow(pRowIndex, pTileRow.toArray(new Node[pTileRow.size()]));
  }
  
  
  public void addTileRow()
  {
    ArrayList<Tile> vTilesToAdd = null;
    
    vTilesToAdd = new ArrayList<Tile>();
    
    for(int vCount = 0; vCount < oMapGridColumns; ++vCount)
    {
      vTilesToAdd.add(new Tile());
    }
    
    oMainGridPane.addRow(oMapGridRows, vTilesToAdd.toArray(new Node[vTilesToAdd.size()]));
    
    incrementMapGridRowCount();
  }
  
  
  public void addTileColumn()
  {
    ArrayList<Tile> vTilesToAdd = null;
    
    vTilesToAdd = new ArrayList<Tile>();
    
    for(int vCount = 0; vCount < oMapGridRows; ++vCount)
    {
      vTilesToAdd.add(new Tile());
    }
    
    oMainGridPane.addColumn(oMapGridColumns, vTilesToAdd.toArray(new Node[vTilesToAdd.size()]));
    
    incrementMapGridColumnCount();
  }
  
  
  public void deleteTileRow()
  {
    Node vNode = null;
    
    for(Iterator<Node> vIterator = oMainGridPane.getChildren().iterator(); vIterator.hasNext();)
    {
      vNode = vIterator.next();
      
      if(GridPane.getRowIndex(vNode) == (oMapGridRows - 1))
      {
        vIterator.remove();
      }
    }
    
    decrementMapGridRowCount();
  }
  
  
  public void deleteTileColumn()
  {
    Node vNode = null;
    
    for(Iterator<Node> vIterator = oMainGridPane.getChildren().iterator(); vIterator.hasNext();)
    {
      vNode = vIterator.next();
      
      if(GridPane.getColumnIndex(vNode) == (oMapGridColumns - 1))
      {
        vIterator.remove();
      }
    }
    
    decrementMapGridColumnCount();
  }
  
  
  public void incrementMapGridRowCount()
  {
    ++oMapGridRows;
  }
  
  
  public void incrementMapGridColumnCount()
  {
    ++oMapGridColumns;
  }
  
  
  public void decrementMapGridRowCount()
  {
    if(--oMapGridRows == 0)
    {
      resetColumnAndRowCount();
      
      oAddRemoveTilesMenu.disableButtons();
      clearTileMenuAttributeValues();
      clearEditableTileMenuAttributeValues();
    }
  }
  
  
  public void decrementMapGridColumnCount()
  {
    if(--oMapGridColumns == 0)
    {
      resetColumnAndRowCount();
      
      oAddRemoveTilesMenu.disableButtons();
      clearTileMenuAttributeValues();
      clearEditableTileMenuAttributeValues();
    }
  }
  
  
  private void resetColumnAndRowCount()
  {
    oMapGridRows    = 0;
    oMapGridColumns = 0;
  }
  
  
  public void showTileMenu()
  {
    oTileMenu.setVisible(true);
  }
  
  
  public void hideTileMenu()
  {
    oTileMenu.setVisible(false);
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
        
        oTileMenu.clearAttributeValues();
        oEditableTileMenu.clearAttributeValues();
        
        oCurrentTile = null;
      }
    });
  }
  
  
  private void configureMainGridPane()
  {
    Tile                 vTile       = null;
    ArrayList<Node>      vImages     = null;
    TileEvent            vMoveEvent  = null;
    
    try
    {
      vImages = new ArrayList<Node>();
      
      vTile   = new Tile();
      
      vTile.setOnMouseClicked(oTileClickHandler);
      
      vImages.add(vTile);
       
      vTile = new Tile(
          true,
          "file:Resources/Images/water.png",
          "water");
      
      vTile.setOnMouseClicked(oTileClickHandler);
      
      vImages.add(vTile);
       
      vTile = new Tile(
          false,
          "file:Resources/Images/dirt.png",
          "dirt");
      
      
      vMoveEvent  = new TileEvent("com.game.TileEvents.events.MoveEvent");
      vMoveEvent.addTileEventArg("java.lang.Integer", "5");
      vMoveEvent.addTileEventArg("java.lang.Integer", "11");
 
      vTile.addTileEvent(vMoveEvent);
      
      /*vMoveEvent  = new TileEvent("com.game.TileEvents.events.OTHEREvent", "OtherEvent");
      vMoveEvent.addTileEventArg("java.lang.Integer", "100");
      vMoveEvent.addTileEventArg("java.lang.Integer", "999");
      
      vTile.addTileEvent(vMoveEvent);*/
      
      vTile.setOnMouseClicked(oTileClickHandler);
      
      vImages.add(vTile);
       
      oMainGridPane.addRow(0, vImages.toArray(new Node[vImages.size()]));
    }
    catch(Exception e)
    {
     System.out.println(e.getMessage());
    }
  }
  
  
  public void removeTileEvent(TileEvent pTileEvent)
  {
    oCurrentTile.deleteTileEvent(pTileEvent);
  }
  
  
  public void addTileEvent(TileEvent pTileEvent)
  {
    oCurrentTile.addTileEvent(pTileEvent);
  }
  
  
  private void configureSideFlowPane()
  {
    oSideFlowPane.setPrefWidth(SIDE_PANE_WIDTH);
  }
  
  
  private void configureTileAttribTitlePane()
  {
    oTileAttribsTitledPane.setContent(oTileMenu);
    oTileAttribsTitledPane.setText("Tile Attributes");
    oTileAttribsTitledPane.setPrefWidth(SIDE_PANE_WIDTH);
  }
  
  
  private void configureEditTileAttribsTitlePane()
  {
    oEditTileAttribsTitledPane.setContent(oEditableTileMenu);
    oEditTileAttribsTitledPane.setText("Edit Tile");
    oEditTileAttribsTitledPane.setPrefWidth(SIDE_PANE_WIDTH);
  }
  
  
  private void configureAddRemoveTilesTitlePane()
  {
    oAddRemoveTilesTitledPane.setContent(oAddRemoveTilesMenu);
    oAddRemoveTilesTitledPane.setText("Add/Remove Tiles");
    oAddRemoveTilesTitledPane.setPrefWidth(SIDE_PANE_WIDTH);
  }
  
  
  public void displayCurrentTileConfig()
  {
    Integer vRowIndex   = null;
    Integer vColIndex   = null;
    Double vTileWidth   = null;
    Double vTileHeight  = null;
    
    vRowIndex   = GridPane.getRowIndex(oCurrentTile);
    vColIndex   = GridPane.getColumnIndex(oCurrentTile);
    
    vTileWidth  = oCurrentTile.getTileWidth();
    vTileHeight = oCurrentTile.getTileHeight();
    
    oTileMenu.setRow(vRowIndex.toString());
    oTileMenu.setColumn(vColIndex.toString());
    oTileMenu.setWidth(vTileWidth.toString());
    oTileMenu.setHeight(vTileHeight.toString());
    oTileMenu.setImage(oCurrentTile.getTileImageName());
    oTileMenu.setIsSolid(oCurrentTile.isSolid().toString());
    oTileMenu.setTileEvents(oCurrentTile.getTileEvents());
  }

  
  public void displayEditableTileConfig()
  {
    oEditableTileMenu.setIsSolid(oCurrentTile.isSolid().toString());
    oEditableTileMenu.setImageView(oCurrentTile.getTileImage());
    oEditableTileMenu.setTileEvents(oCurrentTile.getTileEvents());
  }
  
  
  public void setCurrentTileTileEvents(ArrayList<TileEvent> pTileEvents)
  {
    oCurrentTile.setTileEvents(pTileEvents);
  }
  
  
  public void updateTileEventListViews()
  {
    ArrayList<TileEvent> vTileEvents = null;
    
    vTileEvents = oCurrentTile.getTileEvents();
    
    oTileMenu.setTileEvents(vTileEvents);
    oEditableTileMenu.setTileEvents(vTileEvents);
  }
  
  
  public void unhighlightEditableTileMenu()
  {
    if(oEditableTileMenu.isHighlighted())
    {
      oEditableTileMenu.unhighlightMenu();
    }
  }
  
  
  public Tile getCurrentTile()
  {
    return oCurrentTile;
  }
  
  
  public void setCurrentTile(Tile pCurrentTile)
  {
    oCurrentTile = pCurrentTile;
  }
  
  
  public Tile getPreviousTile()
  {
    return oPreviousTile;
  }
  
  
  public void setPreviousTile(Tile pPreviousTile)
  {
    oPreviousTile = pPreviousTile;
  }
  
  
  public void clearTileMenuAttributeValues()
  {
    oTileMenu.clearAttributeValues();
  }
  
  
  public void clearEditableTileMenuAttributeValues()
  {
    oEditableTileMenu.clearAttributeValues();
  }
  
  
  public TileClickHandler getTileClickHandler()
  {
    return oTileClickHandler;
  }
  
  
  public void setIsSolidReferences(Boolean pIsSolid)
  {
    oCurrentTile.setIsSolid(pIsSolid);

    oTileMenu.setIsSolid(pIsSolid.toString());
  }
 
  
  public void setTileStyle(Tile pTile, String pStyle)
  {
    pTile.setTileStyle(pStyle);
  }
  
  
  public Image getLoadedImage(String pImageName)
  {
    return oLoadedImagesMenu.getImage(pImageName);
  }
  
  
  public void setImageReferences(String pTileImageName, Image pImage)
  {
    oCurrentTile.setTileImageName(pTileImageName);
    oCurrentTile.setTileImage(pImage);

    oTileMenu.setImage(pTileImageName);
    oEditableTileMenu.setImageView(pImage);
  }
  
}
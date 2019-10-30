package com.game.tileEditor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.game.eventHandlers.ExitHandler;
import com.game.eventHandlers.LoadMenuHandler;
import com.game.eventHandlers.NewProjectHandler;
import com.game.eventHandlers.SaveHandler;
import com.game.eventHandlers.TileClickHandler;
import com.game.tileEditor.tileEvents.TileEvent;
import com.game.utilities.SceneUtils;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
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
  
  private LinkedList<Tile> oTileLinkedList = null;
  
  
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
    oTileLinkedList            = new LinkedList<Tile>();
    
    buildMenu();
    configureLoadedImagesMenuStage();
    configureMainBorderPane();
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
    vMenuItem.setOnAction(new SaveHandler(this));
    vMenuItems.add(vMenuItem);
    
    vMenuItem = new MenuItem("Load");
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
  
  
  public void addMapRow(int pRowIndex, ArrayList<Tile> pTiles)
  {
    oMainGridPane.addRow(pRowIndex, pTiles.toArray(new Node[0]));
    
    oTileLinkedList.addAll(pTiles);
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
  
  
  public void addTileRow()
  {
    ArrayList<Tile> vRowToAdd = null;
    
    vRowToAdd = new ArrayList<Tile>();
    
    for(int vColumnIndex = 0; vColumnIndex < oMapGridColumns; ++vColumnIndex)
    {
      Tile vTile = new Tile();
      
      vTile.setRowIndex(oMapGridRows);
      vTile.setColumnIndex(vColumnIndex);
      vTile.setOnMouseClicked(oTileClickHandler);
      
      vRowToAdd.add(vTile);
    }
    
    addMapRow(oMapGridRows, vRowToAdd);
    
    incrementMapGridRowCount();
  }
  
  
  public void addTileColumn()
  {
    ArrayList<Tile> vColumnToAdd            = null;
    int             vTileLinkedListAddIndex = 0;
    
    vColumnToAdd            = new ArrayList<Tile>();
    vTileLinkedListAddIndex = oMapGridColumns;
    
    for(int vRowIndex = 0; vRowIndex < oMapGridRows; ++vRowIndex)
    {
      Tile vTile = new Tile();
      
      vTile.setRowIndex(vRowIndex);
      vTile.setColumnIndex(oMapGridColumns);
      vTile.setOnMouseClicked(oTileClickHandler);
      
      vColumnToAdd.add(vTile);
      
      oTileLinkedList.add(vTileLinkedListAddIndex, vTile);
      
      vTileLinkedListAddIndex += (oMapGridColumns + 1);
    }
    
    oMainGridPane.addColumn(oMapGridColumns, vColumnToAdd.toArray(new Node[0]));
    
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
    Double  vTileWidth  = null;
    Double  vTileHeight = null;
    
    vTileWidth  = oCurrentTile.getTileWidth();
    vTileHeight = oCurrentTile.getTileHeight();
    
    oTileMenu.setRow(oCurrentTile.getRowIndex().toString());
    oTileMenu.setColumn(oCurrentTile.getColumnIndex().toString());
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
  
  
  public void saveMap(File pFile)
  {
    Iterator<Tile> vIterator   = null;    
    PrintWriter    vFileWriter = null;
    String         vIndent     = null;
    
    try
    {
      vFileWriter = new PrintWriter(new BufferedWriter(new FileWriter(pFile)), true);
      vIterator   = oTileLinkedList.iterator();
      vIndent     = "  ";
      
      vFileWriter.println("{");
      vFileWriter.println(vIndent + "\"map\":");
      vFileWriter.println(vIndent + "{");
      vFileWriter.println(vIndent + vIndent + "\"rows\":" + oMapGridRows + ",");
      vFileWriter.println(vIndent + vIndent + "\"cols\":"  + oMapGridColumns);
      vFileWriter.println(vIndent + "},");
      vFileWriter.println(vIndent + "\"tiles\":");
      vFileWriter.println(vIndent + "[");
      
      while(vIterator.hasNext())
      {
        Tile vTile = null;
        
        vTile = (Tile)vIterator.next();
        
        vFileWriter.print(vTile.toJson());
        
        if(vIterator.hasNext())
        {
          vFileWriter.println(",");
        }
      }
      
      vFileWriter.println("\n" + vIndent + "]");
      vFileWriter.print("}");
    }
    catch(IOException pIOException)
    {
      System.out.println("TileEditor.saveMap - " + pIOException.getMessage());
    }
    finally
    {
      if(vFileWriter != null)
      {
        vFileWriter.close();
      }
    }
  }
  
}
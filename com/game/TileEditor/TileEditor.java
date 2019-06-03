package com.game.TileEditor;

import java.util.ArrayList;

import com.game.EventHandlers.ExitHandler;
import com.game.EventHandlers.NewProjectHandler;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;


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
  private static final int    TILE_LENGTH        = 45;
  
  
  private Scene      oScene                     = null;
  private MenuBar    oMenuBar                   = null;
  private Menu       oFileMenu                  = null;
  private BorderPane oMainBorderPane            = null;
  private ScrollPane oMainScrollPane            = null;
  private GridPane   oMainGridPane              = null;
  private TileMenu   oSideTileMenu              = null;
  private FlowPane   oSideFlowPane              = null;
  private TitledPane oTileAttribsTitledPane     = null;
  private TitledPane oEditTileAttribsTitledPane = null;
  
  private TableView<Pair<String, Object>>  oEditTileAttribsTableView  = null;
  
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
    oMainBorderPane            = new BorderPane();
    oMenuBar                   = new MenuBar();
    oFileMenu                  = new Menu("File");
    oMainGridPane              = new GridPane();
    oMainScrollPane            = new ScrollPane();
    oSideTileMenu              = new TileMenu();
    oSideFlowPane              = new FlowPane();
    oTileAttribsTitledPane     = new TitledPane();
    oEditTileAttribsTitledPane = new TitledPane();
    oEditTileAttribsTableView  = new TableView<>();
    
    buildMenu();
    configureMainBorderPane();
    configureMainGridPane();
    configureSideFlowPane();
    configureTileAttribTitlePane();
    configureEditTileAttribsTitlePane();
    configureEditTileAttribsTableView();
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
        vTile  = new Tile(TILE_LENGTH, TILE_LENGTH);
        
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
  
  
  private void configureMainBorderPane()
  {
    oMainBorderPane.setOnMouseClicked(pMouseEvent ->
    {
      if(oCurrentTile != null)
      {
        oCurrentTile.setTileStyle(GRID_CELL);
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
      
      vTile   = new Tile(35, 35);
      
      vTile.setOnMouseClicked(new TileClickHandler());
      
      vImages.add(vTile);
       
      vTile = new Tile(
          35,
          35,
          true,
          "file:Resources/Images/water.png",
          TileImageType.WATER);
      
      vTile.setOnMouseClicked(new TileClickHandler());
      
      vImages.add(vTile);
       
      vTile = new Tile(
          35,
          35,
          false,
          "file:Resources/Images/dirt.png",
          TileImageType.DIRT);
      
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
    oEditTileAttribsTitledPane.setContent(oEditTileAttribsTableView);
    oEditTileAttribsTitledPane.setText("Edit Tile");
    oEditTileAttribsTitledPane.setPrefWidth(SIDE_PANE_WIDTH);
  }
  
  
  private void configureEditTileAttribsTableView()
  {
    TableColumn<Pair<String, Object>, String> vTileAttribColumn = null;
    TableColumn<Pair<String, Object>, Object> vTileValueColumn  = null;
      
    vTileAttribColumn = new TableColumn<Pair<String, Object>, String>("Tile Attribute");
    vTileValueColumn  = new TableColumn<Pair<String, Object>, Object>("Tile Value");
  
    vTileAttribColumn.setSortable(false);
    vTileValueColumn.setSortable(false);
    
    
    oEditTileAttribsTableView.getSelectionModel().setCellSelectionEnabled(true);
    oEditTileAttribsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    

    vTileAttribColumn.setCellValueFactory(pArg->{
      return new ReadOnlyObjectWrapper<String>(pArg.getValue().getKey());
    });
    
    vTileValueColumn.setCellValueFactory(pArg->{
      
      Object vObjectValue = null;
      
      vObjectValue = pArg.getValue().getValue();
      
      return new ReadOnlyObjectWrapper<>(((vObjectValue == null) ? "Not Set" : vObjectValue)); 
    });
    
    oEditTileAttribsTableView.setRowFactory(pTableView ->
    {
      TableRow<Pair<String, Object>> vTableRow = new TableRow<>();
      
      vTableRow.setOnMouseClicked(pEvent->
      {
        Pair<String, Object> vRowData    = null;
        int                  vRowIndex   = -1;
        int                  vClickCount = -1;
        
        vRowIndex   = vTableRow.getIndex();
        vClickCount = pEvent.getClickCount();
        
        if(vClickCount == 2)
        {
          if(vRowIndex == 0)
          {
            System.out.println("Double clicked row index 0");
          }
        }
      });
      
      return vTableRow;
    });
    
    oEditTileAttribsTableView.getColumns().addAll(vTileAttribColumn, vTileValueColumn);
    
    oEditTileAttribsTableView.setPrefHeight(200.0);
    
    //set editing config
    
    oEditTileAttribsTableView.setEditable(true);
    
  }
  
  
  private void displayCurrentTileConfig()
  {
    Integer vRowIndex   = null;
    Integer vColIndex   = null;
    Integer vTileWidth  = null;
    Integer vTileHeight = null;
    
    vRowIndex   = GridPane.getRowIndex(oCurrentTile)    + 1;
    vColIndex   = GridPane.getColumnIndex(oCurrentTile) + 1;
    
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
  
  
  private void displayCurrentTileEditableConfig()
  {
    ObservableList<Pair<String, Object>> vTableViewData = null;
    
    vTableViewData = FXCollections.observableArrayList(
        new Pair<String, Object>(
            "IsSolid", 
            oCurrentTile.isSolid()),
        new Pair<String, Object>(
            "Image",
            oCurrentTile.getTileImage() == null 
                ? null 
                : new ImageView(oCurrentTile.getTileImage().getImage())),
        new Pair<String, Object>("TileEvents", new String[]{"TileEvent1", "TileEvent2"})
        );
    
    oEditTileAttribsTableView.getItems().setAll(vTableViewData);
  }
  
  
  //Tile Handler class
  private class TileClickHandler implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      oCurrentTile = (Tile)pMouseEvent.getSource();
      
      if(oPreviousTile != null)
      {
        oPreviousTile.setTileStyle(GRID_CELL);
      }
      
      oCurrentTile.setTileStyle(SELECTED_GRID_CELL);
      
      displayCurrentTileConfig();
      displayCurrentTileEditableConfig();
      
      oPreviousTile = oCurrentTile;
      
      pMouseEvent.consume();
    }
  }
  
  
  private class ValueCell extends TableCell<Pair<String, Object>, Object>
  {
    public void updateItem(Object pItem, boolean pEmpty)
    {
      super.updateItem(pItem, pEmpty);
      
      if(pItem == null)
      {
        setText("Not Set");
      }
      else if(pItem instanceof String || pItem instanceof Boolean)
      {
        setText(pItem.toString());
      }
      else if(pItem instanceof ImageView)
      {
        ImageView vImageView = (ImageView)pItem;
        vImageView.setPreserveRatio(true);
        vImageView.setSmooth(true);
        setGraphic(vImageView);
      }
    }
  }
  
}
  
  



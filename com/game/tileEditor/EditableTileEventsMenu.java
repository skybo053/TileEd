package com.game.tileEditor;

import java.util.ArrayList;
import java.util.TreeMap;

import com.game.utilities.SceneUtils;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import tileEvents.TileEvent;
import tileEvents.menuDisplayConfigs.MoveEventDisplayConfig;
import tileEvents.menuDisplayConfigs.TileEventDisplayConfig;


public class EditableTileEventsMenu extends BorderPane
{
  private static final double PANE_WIDTH  = 600.0;
  private static final double PANE_HEIGHT = 500.0;
  
  private static final int    NUM_COLUMNS = 2;
  
  private TileEvent oTileEvent = null;
  
  private ListView<String>                        oTileEventsList = null;
  private TreeMap<String, TileEventDisplayConfig> oDisplayConfigs = null;
  
  private TileEventDisplayConfig oCurrentDisplayConfig = null;
  
  private BorderPane oTopPane                 = null;
  private FlowPane   oTileEventsListPane      = null;
  private FlowPane   oTileEventClassPane      = null;
  private GridPane   oTileEventArgsPane       = null;
  private GridPane   oButtonsPane             = null;
  private Label      oEventClassLabel         = null;
  private TextField  oEventClassTextField     = null;
  private Insets     oTextFieldMargin         = null;
  
  
  public EditableTileEventsMenu()
  {
    setPrefWidth(PANE_WIDTH);
    setPrefHeight(PANE_HEIGHT);
    
    oTileEventsList          = new ListView<String>();
    oDisplayConfigs          = new TreeMap<String, TileEventDisplayConfig>();
    
    oTopPane                 = new BorderPane();
    oTileEventsListPane      = new FlowPane();
    oTileEventClassPane      = new FlowPane();
    oTileEventArgsPane       = new GridPane();
    oButtonsPane             = new GridPane();
    
    oEventClassLabel         = new Label("Event Class Name: ");
    
    oEventClassTextField     = new TextField();
    oEventClassTextField.setPrefWidth(235.0);
    
    configureTopPane();
    configureTileEventArgsPane();
    populateCollections();
    addPanesToBorderPane();
  }
  
  
  private void configureTopPane()
  {
    configureTileEventsList();
    configureTileEventsListPane();
    configureTileEventClassPane();
    
    oTopPane.setTop(oTileEventsListPane);
    oTopPane.setBottom(oTileEventClassPane);
  }
  
  
  private void configureTileEventArgsPane()
  {
    SceneUtils.setColumnConstraints(oTileEventArgsPane, 1);
    
    oTileEventArgsPane.setStyle("-fx-background-color: cyan;");
  }
  
  
  private void configureTileEventsList()
  {
    oTileEventsList.setPrefHeight(75.0);
    oTileEventsList.setPrefWidth(110.0);
    oTileEventsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    oTileEventsList.setOnMouseClicked(new EditableTileEventsMenuListViewClickHandler());
  }
  
  
  private void populateCollections()
  {
    oTileEventsList.getItems().add("MoveEvent");
    oDisplayConfigs.put("MoveEvent", new MoveEventDisplayConfig());
  }
  
  
  private void configureTileEventsListPane()
  {
    oTileEventsListPane.setStyle("-fx-background-color: pink;");
    oTileEventsListPane.setPrefHeight(90.0);
    oTileEventsListPane.setAlignment(Pos.CENTER);
    
    SceneUtils.addToPane(oTileEventsListPane, oTileEventsList);
  }
  
  
  private void configureTileEventClassPane()
  {
    oTileEventClassPane.setStyle("-fx-background-color: yellow;");
    oTileEventClassPane.setPrefHeight(50.0);
    oTileEventClassPane.setAlignment(Pos.CENTER);
    
    SceneUtils.addToPane(oTileEventClassPane, oEventClassLabel);
    SceneUtils.addToPane(oTileEventClassPane, oEventClassTextField);
  }
  
  
  private void addPanesToBorderPane()
  {
    setTop(oTopPane);
    setCenter(oTileEventArgsPane);
  }
  
  
  private void displayMenu()
  {
    oEventClassTextField.setText(oCurrentDisplayConfig.getTileEventClassName());
    
    SceneUtils.clearPane(oTileEventArgsPane);
    
    for(int vRowCount = 0; vRowCount < oCurrentDisplayConfig.getRowCount(); ++vRowCount)
    {
      oTileEventArgsPane.add(oCurrentDisplayConfig.getRowNode(vRowCount), 0, vRowCount);
    }
    
    if(oTileEvent != null)
    {
      oCurrentDisplayConfig.setTextFieldArgValues(oTileEvent.getTileEventArgs());
    }
  }
  
  
  public void setTileEvent(TileEvent pTileEvent)
  {
    String vTileEventName = null;
    
    oTileEvent     = pTileEvent;
    vTileEventName = pTileEvent.getEventName();
    
    oTileEventsList.getSelectionModel().select(vTileEventName);
    
    setCurrentDisplayConfig(vTileEventName);
    
    displayMenu();
  }
  
  
  private void setCurrentDisplayConfig(String pEventName)
  {
    oCurrentDisplayConfig = oDisplayConfigs.get(pEventName);
  }
  
  
  public void clearMenu()
  {
    oTileEventsList.getSelectionModel().clearSelection();
    
    oEventClassTextField.clear();
    oCurrentDisplayConfig.clearTextFieldArgValues();
    SceneUtils.clearPane(oTileEventArgsPane);
    
    oTileEvent = null;
  }
  
  
  private class EditableTileEventsMenuListViewClickHandler implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      ListView<String> vEventListView = null;
      String           vEventName     = null;
      
      vEventListView = (ListView<String>)pMouseEvent.getSource();
      vEventName     = vEventListView.getSelectionModel().getSelectedItem();
      
      if(vEventName != null)
      { 
        
        setCurrentDisplayConfig(vEventName);
        
        if(oTileEvent != null)
        {
          oCurrentDisplayConfig.clearTextFieldArgValues();
          oTileEvent = null;
        }
        
        displayMenu();
      }
    }
  }
  
}

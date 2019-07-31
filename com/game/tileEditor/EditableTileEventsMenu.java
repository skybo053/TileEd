package com.game.tileEditor;

import java.util.TreeMap;

import com.game.utilities.SceneUtils;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import tileEvents.MoveEventDisplayConfig;
import tileEvents.TileEvent;
import tileEvents.TileEventMenuConfig;


public class EditableTileEventsMenu extends BorderPane
{
  private static final double PANE_WIDTH  = 600.0;
  private static final double PANE_HEIGHT = 500.0;
  
  private TileEvent oTileEvent = null;
  
  private ListView<String>                     oTileEventsList = null;
  private TreeMap<String, TileEventMenuConfig> oDisplayConfigs = null;
  
  private BorderPane oTopPane                 = null;
  private FlowPane   oTileEventsListPane      = null;
  private FlowPane   oTileEventClassPane      = null;
  private GridPane   oTileEventArgsPane       = null;
  private ScrollPane oTileEventArgsScrollPane = null;
  private GridPane   oButtonsPane             = null;
  private Label      oEventClassLabel         = null;
  private TextField  oEventClassTextField     = null;
  private Insets     oTextFieldMargin         = null;
  
  
  public EditableTileEventsMenu()
  {
    setPrefWidth(PANE_WIDTH);
    setPrefHeight(PANE_HEIGHT);
    
    oTileEventsList          = new ListView<String>();
    oDisplayConfigs          = new TreeMap<String, TileEventMenuConfig>();
    
    oTopPane                 = new BorderPane();
    oTileEventsListPane      = new FlowPane();
    oTileEventClassPane      = new FlowPane();
    oTileEventArgsPane       = new GridPane();
    oTileEventArgsScrollPane = new ScrollPane();
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
    configureEventClassTextField();
    configureTileEventsList();
    configureTileEventsListPane();
    configureTileEventClassPane();
    
    oTopPane.setTop(oTileEventsListPane);
    oTopPane.setBottom(oTileEventClassPane);
  }
  
  
  private void configureTileEventArgsPane()
  {
    oTileEventArgsScrollPane.setContent(oTileEventArgsPane);
  }
  
  
  private void configureEventClassTextField()
  {
    
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
    setCenter(oTileEventArgsScrollPane);
  }
  
  
  private void displayMenu(String pTileEventName)
  {
    TileEventMenuConfig vMenuConfig = null;
    
    vMenuConfig = oDisplayConfigs.get(pTileEventName);
    
    oEventClassTextField.setText(vMenuConfig.getTileEventClassName());
  }
  
  
  public void setTileEvent(TileEvent pTileEvent)
  {
    oTileEvent = pTileEvent;
  }
  
  
  public void clearMenu()
  {
    oTileEventsList.getSelectionModel().clearSelection();
    
    oEventClassTextField.clear();
    
    SceneUtils.clearPane(oTileEventArgsPane);
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
        displayMenu(vEventName);
      }
    }
  }
  
}

package com.game.tileEditor;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import tileEvents.TileEvent;


public class TileMenu extends GridPane
{
  private static final double TILE_EVENTS_PANE_WIDTH  = 250.0;
  private static final double TILE_EVENTS_PANE_HEIGHT = 85.0;
  private static final double LIST_VIEW_X_POS         = 882.0;
  
  private Label oRowPosLabel    = null;
  private Label oColumnPosLabel = null;
  private Label oRow            = null;
  private Label oColumn         = null;
  private Label oImageLabel     = null;
  private Label oImage          = null;
  private Label oIsSolidLabel   = null;
  private Label oIsSolid        = null;
  private Label oEventsLabel    = null;
  private Label oWidthLabel     = null;
  private Label oWidth          = null;
  private Label oHeight         = null;
  private Label oHeightLabel    = null;
  
  private Pane  oTileEventsPane  = null;
  private Label oTileEventsLabel = null;
  private Popup oTileEventsPopup = null;
  
  private Double oLabelWidth  = null;
  private Double oLabelHeight = null;
  
  private ListView<TileEvent> oTileEvents = null;

  
  public TileMenu()
  {
    
    oRowPosLabel    = new Label("Row Index: ");
    oColumnPosLabel = new Label("Column Index: ");
    oImageLabel     = new Label("Image: ");
    oIsSolidLabel   = new Label("IsSolid: ");
    oEventsLabel    = new Label("Tile Events: ");
    oWidthLabel     = new Label("Width: ");
    oHeightLabel    = new Label("Height: ");
    
    oRow        = new Label();
    oColumn     = new Label();
    oImage      = new Label();
    oIsSolid    = new Label();
    oWidth      = new Label();
    oHeight     = new Label();
    
    oTileEventsLabel = new Label();
    oTileEventsPopup = new Popup();
    oTileEventsPane  = new Pane();
    oTileEvents      = new ListView<TileEvent>();
    
    oLabelWidth  = 295.0;
    oLabelHeight = 119.0;
    
    GridPane.setMargin(oRowPosLabel,    new Insets(0,12,0,5));
    GridPane.setMargin(oColumnPosLabel, new Insets(0,12,0,5));
    GridPane.setMargin(oImageLabel,     new Insets(0,12,0,5));
    GridPane.setMargin(oIsSolidLabel,   new Insets(0,12,0,5));
    GridPane.setMargin(oEventsLabel,    new Insets(0,12,0,5));
    GridPane.setMargin(oWidthLabel,     new Insets(0,12,0,5));
    GridPane.setMargin(oHeightLabel,    new Insets(0,12,0,5));
    
    GridPane.setValignment(oEventsLabel, VPos.TOP);
    
    configureTileEventsLabel();
    configureTileEventsPopup();
    configureListView();
    placeComponents();
  }
  
  
  public void clearAttributeValues()
  {
    oRow.setText(null);
    oColumn.setText(null);
    oImage.setText(null);
    oIsSolid.setText(null);
    oWidth.setText(null);
    oHeight.setText(null);
    
    oTileEventsPopup.hide();
    oTileEventsLabel.setText(null);
    oTileEvents.getItems().clear();
  }
  
  
  private void configureTileEventsLabel()
  {
    oTileEventsLabel.setStyle("-fx-background-color: #fffbd7;");
    
    oTileEventsLabel.widthProperty().addListener((pObservable, pOldVal, pNewVal) ->
    {
      oLabelWidth = pNewVal.doubleValue();
    });
    
    oTileEventsLabel.heightProperty().addListener((pObservable, pOldVal, pNewVal) ->
    {
      oLabelHeight = pNewVal.doubleValue();
    });
  }
  
  
  private void configureTileEventsPopup()
  {
    oTileEventsPopup.addEventHandler(MouseEvent.MOUSE_CLICKED, pEvent->
    {
      oTileEventsPopup.hide();
    });
    
    oTileEventsPopup.getContent().add(oTileEventsLabel);
  }
  
  
  private void configureListView()
  {
    oTileEvents.setPrefHeight(50.0);
    oTileEvents.setPrefWidth(95.0);
    oTileEvents.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    
    oTileEvents.setOnMouseClicked(pEvent->
    {
      ListView<TileEvent>  vTileEventsListView   = null;
      TileEvent            vTileEvent            = null;
      Double               vSceneX               = null;
      Double               vSceneY               = null;
      
      Bounds vBounds       = null;
      Double vListViewXPos = null;
      Double vListViewYPos = null;
      Double vLabelXOffset = null;
      Double vLabelYOffset = null;
      
      vTileEventsListView = (ListView<TileEvent>)pEvent.getSource();
      vTileEvent          = vTileEventsListView.getSelectionModel().getSelectedItem();
      
      if(vTileEvent != null)
      {
        vSceneX       = pEvent.getSceneX();
        vSceneY       = pEvent.getSceneY();
        vBounds       = vTileEventsListView.localToScene(vTileEventsListView.getBoundsInLocal());
        
        oTileEventsLabel.setText(vTileEvent.toJSON());
        
        vListViewXPos = vBounds.getMinX();
        vListViewYPos = vBounds.getMinY();
        
        System.out.println("LabelWidth: " + oLabelWidth + " LabelHeight: " + oLabelHeight);
        System.out.println("ListView X = " + vListViewXPos + " ListView Y = " + vListViewYPos);
        System.out.println("Clicked X = " + vSceneX + " Clicked Y = " + vSceneY);
        
        oTileEventsPopup.show(
            vTileEventsListView, 
            vListViewXPos - (oLabelWidth / 2), 
            vSceneY + (oLabelHeight / 2));
      }
    });
  }
  
  
  private void placeComponents()
  {
    add(oRowPosLabel, 0, 0);
    add(oRow, 1, 0);
    
    add(oColumnPosLabel, 0, 1);
    add(oColumn, 1, 1);
    
    add(oWidthLabel, 0, 2);
    add(oWidth, 1, 2);
    
    add(oHeightLabel, 0, 3);
    add(oHeight, 1, 3);
    
    add(oImageLabel, 0, 4);
    add(oImage, 1, 4);
    
    add(oIsSolidLabel, 0, 5);
    add(oIsSolid, 1, 5);
    
    add(oEventsLabel, 0, 6);
    add(oTileEvents, 1, 6);
  }
  
  
  public void setRow(String pRow)
  {
    oRow.setText(pRow);
  }
  
  
  public void setColumn(String pColumn)
  {
    oColumn.setText(pColumn);
  }
  
  
  public void setWidth(String pWidth)
  {
    oWidth.setText(pWidth);
  }
  
  
  public void setHeight(String pHeight)
  {
    oHeight.setText(pHeight);
  }
  
  
  public void setImage(String pImage)
  {
    oImage.setText(pImage);
  }
  
  
  public void setIsSolid(String pIsSolid)
  {
    oIsSolid.setText(pIsSolid);
  }
  
  
  public void setTileEvents(ArrayList<TileEvent> pTileEvents)
  {
    ObservableList<TileEvent> pTileEventList = null;
    
    pTileEventList = oTileEvents.getItems();
    
    for(TileEvent pTileEvent : pTileEvents)
    {
      pTileEventList.add(pTileEvent);
    }
  }
  
}

package com.game.tileEditor;

import java.util.ArrayList;

import com.game.tileEditor.tileEvents.TileEvent;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;


public class TileMenu extends GridPane
{
  private static final double LABEL_OFFSET = 310.0;
  
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
  
  private Label oTileEventsLabel = null;
  private Popup oTileEventsPopup = null;
  
  private ListView<TileEvent> oTileEventsList = null;

  private TileEditor oTileEditor = null;
  
  
  public TileMenu(TileEditor pTileEditor)
  {
    oTileEditor     = pTileEditor;
    
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
    oTileEventsList  = new ListView<TileEvent>();
    
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
    configureTileEventsListView();
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
    
    closeTileEventsPopup();
    
    oTileEventsLabel.setText(null);
    oTileEventsList.getItems().clear();
  }
  
  
  private void configureTileEventsLabel()
  {
    oTileEventsLabel.setStyle("-fx-background-color: #fffbd7;");
    oTileEventsLabel.setPadding(new Insets(5,5,5,5));
  }
  
  
  private void configureTileEventsPopup()
  {
    oTileEventsPopup.addEventHandler(MouseEvent.MOUSE_CLICKED, pEvent->
    {
      closeTileEventsPopup();
    });
    
    oTileEventsPopup.getContent().add(oTileEventsLabel);
  }
  
  
  private void configureTileEventsListView()
  {
    oTileEventsList.setPrefHeight(75.0);
    oTileEventsList.setPrefWidth(95.0);
    oTileEventsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    oTileEventsList.setOnMouseClicked(new TileEventListViewHandler());
    
    oTileEventsList.focusedProperty().addListener((pObservable, pOldValue, pNewValue)->
    {
      if(pNewValue == false)
      {
        closeTileEventsPopup();
        oTileEventsList.getSelectionModel().clearSelection();
      }
      else
      {
        oTileEditor.unhighlightEditableTileMenu();
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
    add(oTileEventsList, 1, 6);
  }
  
  
  public void closeTileEventsPopup()
  {
    if(oTileEventsPopup.isShowing())
    {
      oTileEventsPopup.hide();
    }
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
    
    pTileEventList = oTileEventsList.getItems();
    
    pTileEventList.clear();
    pTileEventList.addAll(pTileEvents);
  }
  
  
  private class TileEventListViewHandler implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      ListView<TileEvent>  vTileEventsListView   = null;
      TileEvent            vTileEvent            = null;
      
      vTileEvent  = oTileEventsList.getSelectionModel().getSelectedItem();
      
      closeTileEventsPopup();
      
      if(pMouseEvent.getClickCount() == 2)
      {
        if(vTileEvent != null)
        {
          oTileEventsLabel.setText(vTileEvent.toDisplayString());
          
          oTileEventsPopup.show(
              oTileEventsList, 
              pMouseEvent.getScreenX() - LABEL_OFFSET, 
              pMouseEvent.getScreenY());
        }
      }
    }
  }
  
}

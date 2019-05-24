package com.game.TileEditor;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;


public class TileMenu extends GridPane
{
  private Label oTileMenuLabel  = null;
  private Label oRowPosLabel    = null;
  private Label oColumnPosLabel = null;
  private Label oRow            = null;
  private Label oColumn         = null;
  private Label oImageLabel     = null;
  private Label oImage          = null;
  private Label oIsSolidLabel   = null;
  private Label oIsSolid        = null;
  private Label oEventsLabel    = null;
  private Label oEvents         = null;
  
  
  public TileMenu()
  {
    oTileMenuLabel  = new Label("Tile Attributes");
    oRowPosLabel    = new Label("Row: ");
    oColumnPosLabel = new Label("Column: ");
    oImageLabel     = new Label("Image: ");
    oIsSolidLabel   = new Label("Is Solid: ");
    oEventsLabel    = new Label("Tile Events: ");
    
    oRow     = new Label();
    oColumn  = new Label();
    oImage   = new Label();
    oIsSolid = new Label();
    oEvents  = new Label();
    
    GridPane.setMargin(oTileMenuLabel,  new Insets(0,0,10,5));
    GridPane.setMargin(oRowPosLabel,    new Insets(0,0,0,5));
    GridPane.setMargin(oColumnPosLabel, new Insets(0,0,0,5));
    GridPane.setMargin(oImageLabel,     new Insets(0,0,0,5));
    GridPane.setMargin(oIsSolidLabel,   new Insets(0,0,0,5));
    GridPane.setMargin(oEventsLabel,    new Insets(0,0,0,5));
    
    configureStyles();
    placeLabels();
  }
  
  
  private void configureStyles()
  {
    oTileMenuLabel.setStyle("-fx-font-size: 10pt;");
  }
  
  
  private void placeLabels()
  {
    add(oTileMenuLabel, 0 , 0);
    
    add(oRowPosLabel, 0, 2);
    add(oRow, 1, 2);
    
    add(oColumnPosLabel, 0, 3);
    add(oColumn, 1, 3);
    
    add(oImageLabel, 0, 4);
    add(oImage, 1, 4);
    
    add(oIsSolidLabel, 0, 5);
    add(oIsSolid, 1, 5);
    
    add(oEventsLabel, 0, 6);
    add(oEvents, 1, 6);
  }
  
  
  public void setRow(String pRow)
  {
    oRow.setText(pRow);
  }
  
  
  public void setColumn(String pColumn)
  {
    oColumn.setText(pColumn);
  }
  
  
  public void setImage(String pImage)
  {
    oImage.setText(pImage);
  }
  
  
  public void setIsSolid(String pIsSolid)
  {
    oIsSolid.setText(pIsSolid);
  }
  
  
  public void setEvents(String pEvents)
  {
    oEvents.setText(pEvents);
  }
  
}

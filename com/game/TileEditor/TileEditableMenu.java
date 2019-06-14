package com.game.TileEditor;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class TileEditableMenu extends GridPane
{
  private Label     oIsSolidLabel    = null;
  private Label     oImageViewLabel  = null;
  private Label     oTileEventsLabel = null;
  
  private Label     oIsSolid         = null;
  private Label     oTileEvents      = null;
  
  private StackPane oIsSolidLabelStackPane    = null;
  private StackPane oImageViewLabelStackPane  = null;
  private StackPane oTileEventsLabelStackPane = null;
  
  private StackPane oIsSolidStackPane    = null;
  private StackPane oImageViewStackPane  = null;
  private StackPane oTileEventsStackPane = null;
  
  private TextField oIsSolidTextField = null;
  
  private ImageView oEditableImage = null;
  
  private Label oNoImageLabel = null;
  
  private Tile oCurrentTile = null;
  
  private TileMenu oSideTileMenu = null;
  
  
  public TileEditableMenu(TileMenu pSideTileMenu)
  {
    oSideTileMenu    = pSideTileMenu;

    oIsSolidLabel    = new Label("IsSolid: ");
    oImageViewLabel  = new Label("Image: ");
    oTileEventsLabel = new Label("TileEvents:");
    
    oIsSolid    = new Label();
    oTileEvents = new Label();
    
    oIsSolidLabel.setPadding(   new Insets(0,15,0,5));
    oImageViewLabel.setPadding( new Insets(0,15,0,5));
    oTileEventsLabel.setPadding(new Insets(0,15,0,5));
    
    oIsSolidLabelStackPane    = new StackPane();
    oImageViewLabelStackPane  = new StackPane();
    oTileEventsLabelStackPane = new StackPane();
    oIsSolidStackPane         = new StackPane();
    oImageViewStackPane       = new StackPane();
    oTileEventsStackPane      = new StackPane();
    
    oIsSolidTextField = new TextField();
    oIsSolidTextField.setOnKeyPressed(new IsSolidTextFieldHandler());
    
    oIsSolidLabelStackPane.setAlignment(Pos.CENTER_LEFT);
    oImageViewLabelStackPane.setAlignment(Pos.CENTER_LEFT);
    oTileEventsLabelStackPane.setAlignment(Pos.CENTER_LEFT);
    oIsSolidStackPane.setAlignment(Pos.CENTER_LEFT);
    oImageViewStackPane.setAlignment(Pos.CENTER_LEFT);
    oTileEventsStackPane.setAlignment(Pos.CENTER_LEFT);
    
    oEditableImage = new ImageView();
    oNoImageLabel  = new Label("No Image");
    
    setColumnConstraints();
    setBaseRowStyles();
    setClickHandlers();
    placeLabelsInPanes();
    placePanesInGridPane();
  }
  
  
  public void clearAttributeValues()
  {
    oIsSolid.setText(null);
    clearImageStackPane();
    oTileEvents.setText(null);
  }
  
  
  private void setColumnConstraints()
  {
    ColumnConstraints vColumn1 = null;
    ColumnConstraints vColumn2 = null;
    
    vColumn1 = new ColumnConstraints();
    vColumn2 = new ColumnConstraints();
    
    vColumn1.setPercentWidth(50);
    vColumn2.setPercentWidth(50);
    getColumnConstraints().addAll(vColumn1, vColumn2);
  }
  
  
  private void setBaseRowStyles()
  {
    oIsSolidLabelStackPane.setStyle("-fx-background-color: #f4f5f7;");
    oIsSolidStackPane.setStyle("-fx-background-color: #f4f5f7;");
    oImageViewLabelStackPane.setStyle("-fx-background-color: white;");
    oImageViewStackPane.setStyle("-fx-background-color: white;");
    oTileEventsLabelStackPane.setStyle("-fx-background-color: #f4f5f7;");
    oTileEventsStackPane.setStyle("-fx-background-color: #f4f5f7;");
  }
  
  
  private void setClickHandlers()
  {
    oIsSolidLabelStackPane.setOnMouseClicked(new EditableMenuClickHandler());
    oIsSolidStackPane.setOnMouseClicked(new EditableMenuClickHandler());
  }
  
  
  private void placeLabelsInPanes()
  {
    oIsSolidLabelStackPane.getChildren().add(oIsSolidLabel);
    oImageViewLabelStackPane.getChildren().add(oImageViewLabel);
    oTileEventsLabelStackPane.getChildren().add(oTileEventsLabel);
    oIsSolidStackPane.getChildren().add(oIsSolid);
    oTileEventsStackPane.getChildren().add(oTileEvents);
  }
  
  
  private void placePanesInGridPane()
  {
    add(oIsSolidLabelStackPane, 0, 0);
    add(oIsSolidStackPane, 1, 0);
    
    add(oImageViewLabelStackPane, 0, 1);
    add(oImageViewStackPane, 1, 1);
    
    add(oTileEventsLabelStackPane, 0, 2);
    add(oTileEventsStackPane, 1, 2);
  }
  
  public void setIsSolid(String pIsSolid)
  {
    oIsSolid.setText(pIsSolid);
  }
  
  
  public void setImageView(Image pImage)
  {
    clearImageStackPane();
 
    if(pImage == null)
    {
      oImageViewStackPane.getChildren().add(oNoImageLabel);
    }
    else
    {
      oEditableImage.setImage(pImage);
      
      oImageViewStackPane.getChildren().add(oEditableImage);
    }
  }
  

  public void setTileEvents(String pTileEvents)
  {
    oTileEvents.setText(pTileEvents);
  }
  
  
  public void clearImageStackPane()
  {
    oImageViewStackPane.getChildren().clear();
  }
  
  
  public void setCurrentTile(Tile pTile)
  {
    oCurrentTile = pTile;
  }
  
  
  private class EditableMenuClickHandler implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      StackPane vClickedPane = null;
      
      if(pMouseEvent.getClickCount() == 2 && oCurrentTile != null)
      {
        vClickedPane = (StackPane)pMouseEvent.getSource();
        
        if(GridPane.getRowIndex(vClickedPane) == 0)
        {
          System.out.println("Clicked row 1!!");
          editIsSolidRow();
        }
      }
    }
    
    
    private void editIsSolidRow()
    {
      oIsSolidStackPane.getChildren().clear();
      
      oIsSolidTextField.setText(oIsSolid.getText());
      oIsSolidStackPane.getChildren().add(oIsSolidTextField);
    }
  } //End of EditableMenuClickHandler class
  
  
  
  private class IsSolidTextFieldHandler implements EventHandler<KeyEvent>
  {
    public void handle(KeyEvent pKeyEvent) 
    {
      String  vIsSolidEntry          = null;
      
      if(pKeyEvent.getCode() == KeyCode.ENTER) 
      {
        vIsSolidEntry = oIsSolidTextField.getText().toLowerCase();
        
        if((vIsSolidEntry.equals("false")  == true  ||
            vIsSolidEntry.equals("true")   == true) &&
            Boolean.valueOf(vIsSolidEntry) != oCurrentTile.isSolid())
        {
          oCurrentTile.setIsSolid(Boolean.valueOf(vIsSolidEntry));
          oIsSolid.setText(oCurrentTile.isSolid().toString());
          oSideTileMenu.setIsSolid(oCurrentTile.isSolid().toString());
        }
        
        oIsSolidStackPane.getChildren().clear();
        oIsSolidStackPane.getChildren().add(oIsSolid);
      }
    }
  } //End of IsSolidTextFieldHandler class
  
  
  
  
}//End of TileEditableMenu class

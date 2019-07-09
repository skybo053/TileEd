package com.game.tileEditor;


import com.game.utilities.SceneUtils;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import javafx.stage.Modality;
import javafx.stage.Stage;


public class EditableTileMenu extends GridPane
{
  private Label     oIsSolidLabel    = null;
  private Label     oImageViewLabel  = null;
  private Label     oTileEventsLabel = null;
  
  private Label     oIsSolid         = null;
  private Label     oImageView       = null;
  private Label     oTileEvents      = null;
  
  private StackPane oIsSolidLabelStackPane    = null;
  private StackPane oImageViewLabelStackPane  = null;
  private StackPane oTileEventsLabelStackPane = null;
  
  private StackPane oIsSolidStackPane    = null;
  private StackPane oImageViewStackPane  = null;
  private StackPane oTileEventsStackPane = null;
  
  private TextField oIsSolidTextField    = null;
  private ImageView oEditableImage       = null;
  
  private TileEditor oTileEditor               = null;
  
  private Integer    oSelectedEditableRowIndex = null;
  
  
  public EditableTileMenu(TileEditor pTileEditor)
  {
    oTileEditor            = pTileEditor;

    oIsSolidLabel    = new Label("IsSolid: ");
    oImageViewLabel  = new Label("Image: ");
    oTileEventsLabel = new Label("TileEvents:");
    
    oIsSolid    = new Label();
    oImageView  = new Label();
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
    
    oIsSolidLabelStackPane.setAlignment(   Pos.CENTER_LEFT);
    oImageViewLabelStackPane.setAlignment( Pos.CENTER_LEFT);
    oTileEventsLabelStackPane.setAlignment(Pos.CENTER_LEFT);
    oIsSolidStackPane.setAlignment(        Pos.CENTER_LEFT);
    oImageViewStackPane.setAlignment(      Pos.CENTER_LEFT);
    oTileEventsStackPane.setAlignment(     Pos.CENTER_LEFT);
    
    oEditableImage = new ImageView();
    
    SceneUtils.setColumnConstraints(this, 2);
    
    setBaseRowStyles();
    placeLabelsInPanes();
    placePanesInGridPane();
    setHandlers();
  }
  
  
  public void clearAttributeValues()
  {
    oIsSolid.setText(null);
    oTileEvents.setText(null);
    
    SceneUtils.clearPane(oIsSolidStackPane);
    SceneUtils.clearPane(oImageViewStackPane);
    SceneUtils.clearPane(oTileEventsStackPane);
    
    SceneUtils.addToPane(oIsSolidStackPane,    oIsSolid);
    SceneUtils.addToPane(oTileEventsStackPane, oTileEvents);
    
    setBaseRowStyles();
    
    oSelectedEditableRowIndex = null;
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
  
  
  private void setRowHighlighted(int pRowIndex)
  {
    switch(pRowIndex)
    {
      case 0:
        oIsSolidLabelStackPane.setStyle("-fx-background-color: #87ceeb;");
        oIsSolidStackPane.setStyle("-fx-background-color: #87ceeb;");
        break;
        
      case 1:
        oImageViewLabelStackPane.setStyle("-fx-background-color: #87ceeb;");
        oImageViewStackPane.setStyle("-fx-background-color: #87ceeb;");
        
        if(SceneUtils.paneContainsNode(oIsSolidStackPane, oIsSolidTextField))
        {
          SceneUtils.clearPane(oIsSolidStackPane);
          SceneUtils.addToPane(oIsSolidStackPane, oIsSolid);
        }
        
        break;
        
      case 2:
        oTileEventsLabelStackPane.setStyle("-fx-background-color: #87ceeb;");
        oTileEventsStackPane.setStyle("-fx-background-color: #87ceeb;");
        
        if(SceneUtils.paneContainsNode(oIsSolidStackPane, oIsSolidTextField))
        {
          SceneUtils.clearPane(oIsSolidStackPane);
          SceneUtils.addToPane(oIsSolidStackPane, oIsSolid);
        }
        
        break;
    }
  }
  
  
  private void setHandlers()
  {
    oIsSolidTextField.setOnKeyPressed(new IsSolidTextFieldHandler());
    
    for(Node vNode : getChildren())
    {
      vNode.setOnMouseClicked(new EditableMenuClickHandler());
    }
  }
  
  
  private void placeLabelsInPanes()
  {
    SceneUtils.addToPane(oIsSolidLabelStackPane,    oIsSolidLabel);
    SceneUtils.addToPane(oImageViewLabelStackPane,  oImageViewLabel);
    SceneUtils.addToPane(oTileEventsLabelStackPane, oTileEventsLabel);
    SceneUtils.addToPane(oIsSolidStackPane,         oIsSolid);
    SceneUtils.addToPane(oImageViewStackPane,       oImageView);
    SceneUtils.addToPane(oTileEventsStackPane,      oTileEvents);
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
    SceneUtils.clearPane(oImageViewStackPane);
 
    if(pImage == null)
    {
      oImageView.setText("No Image");
      SceneUtils.addToPane(oImageViewStackPane, oImageView);
    }
    else
    {
      oEditableImage.setImage(pImage);
      SceneUtils.addToPane(oImageViewStackPane, oEditableImage);
    }
  }
  

  public void setTileEvents(String pTileEvents)
  {
    oTileEvents.setText(pTileEvents);
  }
  
  /////// Inner class to handle edit menu clicks ////////
  private class EditableMenuClickHandler implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      StackPane vClickedPane     = null;
      int       vClickedRowIndex = -1;
      
      vClickedPane     = (StackPane)pMouseEvent.getSource();
      vClickedRowIndex = GridPane.getRowIndex(vClickedPane);
      
      if(oTileEditor.getCurrentTile() != null)
      {
        if(oSelectedEditableRowIndex == null ||
           vClickedRowIndex          != oSelectedEditableRowIndex)
        {
          setBaseRowStyles();
          setRowHighlighted(vClickedRowIndex);
          
          oSelectedEditableRowIndex = vClickedRowIndex;
        }
        
        if(pMouseEvent.getClickCount() == 2)
        {
          if(GridPane.getRowIndex(vClickedPane) == 0)
          {
            editIsSolidRow();
          }
          else if(GridPane.getRowIndex(vClickedPane) == 1)
          {
            oTileEditor.showLoadedImagesMenu();
          }
        }
      }
    }
    
    
    private void editIsSolidRow()
    {
      oIsSolidTextField.setText(oIsSolid.getText());
      
      SceneUtils.clearPane(oIsSolidStackPane);
      SceneUtils.addToPane(oIsSolidStackPane, oIsSolidTextField);
    }
  } //End of EditableMenuClickHandler class
  
  
  
  ///////// Inner class to handle Is solid text field entries ///////////
  private class IsSolidTextFieldHandler implements EventHandler<KeyEvent>
  {
    public void handle(KeyEvent pKeyEvent) 
    {
      String  vIsSolidEntry = null;
      Boolean vIsSolid      = null;
      
      if(pKeyEvent.getCode() == KeyCode.ENTER) 
      {
        vIsSolidEntry = oIsSolidTextField.getText().toLowerCase();
        
        if(vIsSolidEntry.equals("false")  == true ||
           vIsSolidEntry.equals("true")   == true)
        {
          vIsSolid = Boolean.valueOf(vIsSolidEntry);
          
          if(vIsSolid != oTileEditor.getCurrentTile().isSolid())
          {
            oIsSolid.setText(vIsSolid.toString());
            oTileEditor.setIsSolidReferences(vIsSolid);
          }
        }
        
        SceneUtils.clearPane(oIsSolidStackPane);
        SceneUtils.addToPane(oIsSolidStackPane, oIsSolid);
      }
    }
  } //End of IsSolidTextFieldHandler class
  
  
  
  
}//End of TileEditableMenu class

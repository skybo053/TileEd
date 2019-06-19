package com.game.TileEditor;


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


public class TileEditableMenu extends GridPane
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
  
  private TextField oIsSolidTextField = null;
  
  private ImageView oEditableImage = null;
  
  private Tile     oCurrentTile           = null;
  private TileMenu oSideTileMenu          = null;
  
  private Scene    oLoadedImagesMenuScene = null;
  private Stage    oLoadedImagesMenuStage = null;
  
  
  public TileEditableMenu(TileMenu pSideTileMenu, Scene pLoadedImagesMenuScene)
  {
    oSideTileMenu          = pSideTileMenu;
    oLoadedImagesMenuScene = pLoadedImagesMenuScene;

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
    
    initializeLoadedImagesMenuStage();
    setColumnConstraints();
    setBaseRowStyles();
    placeLabelsInPanes();
    placePanesInGridPane();
    setHandlers();
  }
  
  
  private void initializeLoadedImagesMenuStage()
  {
    oLoadedImagesMenuStage = new Stage();
    
    oLoadedImagesMenuStage.setTitle("Loaded Images");
    oLoadedImagesMenuStage.setScene(oLoadedImagesMenuScene);
    oLoadedImagesMenuStage.setResizable(false);
    oLoadedImagesMenuStage.initModality(Modality.APPLICATION_MODAL);
  }
  
  
  public void clearAttributeValues()
  {
    oIsSolid.setText(null);
    oTileEvents.setText(null);
    
    clearPane(oIsSolidStackPane);
    clearPane(oImageViewStackPane);
    clearPane(oTileEventsStackPane);
    
    addToPane(oIsSolidStackPane, oIsSolid);
    addToPane(oTileEventsStackPane, oTileEvents);
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
    addToPane(oIsSolidLabelStackPane,    oIsSolidLabel);
    addToPane(oImageViewLabelStackPane,  oImageViewLabel);
    addToPane(oTileEventsLabelStackPane, oTileEventsLabel);
    addToPane(oIsSolidStackPane,         oIsSolid);
    addToPane(oImageViewStackPane,       oImageView);
    addToPane(oTileEventsStackPane,      oTileEvents);
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
    clearPane(oImageViewStackPane);
 
    if(pImage == null)
    {
      oImageView.setText("No Image");
      addToPane(oImageViewStackPane, oImageView);
    }
    else
    {
      oEditableImage.setImage(pImage);
      addToPane(oImageViewStackPane, oEditableImage);
    }
  }
  

  public void setTileEvents(String pTileEvents)
  {
    oTileEvents.setText(pTileEvents);
  }
  
  
  public void setCurrentTile(Tile pTile)
  {
    oCurrentTile = pTile;
  }
  
  
  public void clearPane(StackPane pStackPane)
  {
    pStackPane.getChildren().clear();
  }
  
  
  public void addToPane(StackPane pStackPane, Node pNode)
  {
    pStackPane.getChildren().add(pNode);
  }
  
  
  /////// Inner class to handle edit menu clicks ////////
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
          editIsSolidRow();
        }
        else if(GridPane.getRowIndex(vClickedPane) == 1)
        {
          oLoadedImagesMenuStage.show();
        }
      }
    }
    
    
    private void editIsSolidRow()
    {
      oIsSolidTextField.setText(oIsSolid.getText());
      
      clearPane(oIsSolidStackPane);
      addToPane(oIsSolidStackPane, oIsSolidTextField);
    }
  } //End of EditableMenuClickHandler class
  
  
  
  ///////// Inner class to handle Is solid text field entries ///////////
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
        
        clearPane(oIsSolidStackPane);
        addToPane(oIsSolidStackPane, oIsSolid);
      }
    }
  } //End of IsSolidTextFieldHandler class
  
  
  
  
}//End of TileEditableMenu class

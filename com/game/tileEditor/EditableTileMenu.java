package com.game.tileEditor;


import java.util.ArrayList;

import com.game.tileEditor.tileEvents.TileEvent;
import com.game.utilities.SceneUtils;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
  
  private TextField oIsSolidTextField    = null;
  private ImageView oEditableImage       = null;
  
  private TileEditor oTileEditor               = null;
  
  private Integer    oSelectedEditableRowIndex = null;
  
  private ListView<TileEvent> oTileEventsList = null;
  
  private EditableTileEventsMenu oEditableTileEventsMenu      = null;
  private Stage                  oEditableTileEventsMenuStage = null;
  
  
  public EditableTileMenu(TileEditor pTileEditor)
  {
    oTileEditor       = pTileEditor;
    
    oIsSolidLabel     = new Label("IsSolid: ");
    oImageViewLabel   = new Label("Image: ");
    oTileEventsLabel  = new Label("Tile Events:");
    oIsSolid          = new Label();
    oImageView        = new Label();
    oTileEvents       = new Label();
    oEditableImage    = new ImageView();
    oIsSolidTextField = new TextField();
    
    oIsSolidLabelStackPane    = new StackPane();
    oImageViewLabelStackPane  = new StackPane();
    oTileEventsLabelStackPane = new StackPane();
    oIsSolidStackPane         = new StackPane();
    oImageViewStackPane       = new StackPane();
    
    oTileEventsList = new ListView<TileEvent>();
    
    oEditableTileEventsMenuStage = new Stage();
    oEditableTileEventsMenu      = new EditableTileEventsMenu(oTileEditor, this);
    
    oIsSolidLabel.setPadding(   new Insets(0,15,0,5));
    oImageViewLabel.setPadding( new Insets(0,15,0,5));
    oTileEventsLabel.setPadding(new Insets(0,15,0,5));
    
    oIsSolidLabelStackPane.setAlignment(   Pos.CENTER_LEFT);
    oImageViewLabelStackPane.setAlignment( Pos.CENTER_LEFT);
    oTileEventsLabelStackPane.setAlignment(Pos.CENTER_LEFT);
    oIsSolidStackPane.setAlignment(        Pos.CENTER_LEFT);
    oImageViewStackPane.setAlignment(      Pos.CENTER_LEFT);
    
    SceneUtils.setColumnConstraints(this, 2);
    
    configureTileEventsListView();
    configureEditableTileEventsMenuStage();
    setBaseRowStyles();
    placeLabelsInPanes();
    placePanesInGridPane();
    setHandlers();
  }
  
  
  private void configureTileEventsListView()
  {
    oTileEventsList.setPrefHeight(75.0);
    oTileEventsList.setPrefWidth(95.0);
    oTileEventsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    oTileEventsList.setOnMouseClicked(new TileEventListViewHandler());
    
    GridPane.setMargin(oTileEventsList, new Insets(2,5,0,0));
  }
  
  
  private void configureEditableTileEventsMenuStage()
  {
    Scene vScene = new Scene(oEditableTileEventsMenu);
    
    oEditableTileEventsMenuStage.setTitle("Edit TileEvent");
    oEditableTileEventsMenuStage.setScene(vScene);
    oEditableTileEventsMenuStage.setResizable(false);
    oEditableTileEventsMenuStage.initModality(Modality.APPLICATION_MODAL);
    
    oEditableTileEventsMenuStage.setOnCloseRequest(pWindowEvent->
    {
      oEditableTileEventsMenu.clearMenu();
    });
  }
  
  
  public void clearAttributeValues()
  {
    oIsSolid.setText(null);
    oTileEvents.setText(null);
    oTileEventsList.getItems().clear();
    
    SceneUtils.clearPane(oIsSolidStackPane);
    SceneUtils.clearPane(oImageViewStackPane);
    
    SceneUtils.addToPane(oIsSolidStackPane, oIsSolid);
    
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
  }
  
  
  public void unhighlightMenu()
  {
    setBaseRowStyles();
    
    if(SceneUtils.paneContainsNode(oIsSolidStackPane, oIsSolidTextField))
    {
      oIsSolidTextField.setText(null);
      
      SceneUtils.clearPane(oIsSolidStackPane);
      SceneUtils.addToPane(oIsSolidStackPane, oIsSolid);
    }
    
    oTileEventsList.getSelectionModel().clearSelection();
    oSelectedEditableRowIndex = null;
  }
  
  
  private void setRowHighlighted(int pRowIndex)
  {
    switch(pRowIndex)
    {
      case 0:
        oIsSolidLabelStackPane.setStyle("-fx-background-color: #87ceeb;");
        oIsSolidStackPane.setStyle("-fx-background-color: #87ceeb;");
        
        oTileEventsList.getSelectionModel().clearSelection();
        
        break;
        
      case 1:
        oImageViewLabelStackPane.setStyle("-fx-background-color: #87ceeb;");
        oImageViewStackPane.setStyle("-fx-background-color: #87ceeb;");
        
        if(SceneUtils.paneContainsNode(oIsSolidStackPane, oIsSolidTextField))
        {
          SceneUtils.clearPane(oIsSolidStackPane);
          SceneUtils.addToPane(oIsSolidStackPane, oIsSolid);
        }
        
        oTileEventsList.getSelectionModel().clearSelection();
        
        break;
        
      case 2:
        oTileEventsLabelStackPane.setStyle("-fx-background-color: #87ceeb;");
        
        if(SceneUtils.paneContainsNode(oIsSolidStackPane, oIsSolidTextField))
        {
          SceneUtils.clearPane(oIsSolidStackPane);
          SceneUtils.addToPane(oIsSolidStackPane, oIsSolid);
        }
        
        if(oTileEventsList.getItems().isEmpty() == false)
        {
          oTileEventsList.getSelectionModel().select(0);
        }
        
        break;
    }
  }
  
  
  private void setHandlers()
  {
    oIsSolidTextField.setOnKeyPressed(          new IsSolidTextFieldHandler());
    oIsSolidLabelStackPane.setOnMouseClicked(   new EditableMenuClickHandler());   
    oImageViewLabelStackPane.setOnMouseClicked( new EditableMenuClickHandler()); 
    oTileEventsLabelStackPane.setOnMouseClicked(new EditableMenuClickHandler()); 
    oIsSolidStackPane.setOnMouseClicked(        new EditableMenuClickHandler());   
    oImageViewStackPane.setOnMouseClicked(      new EditableMenuClickHandler()); 
  }
  
  
  private void placeLabelsInPanes()
  {
    SceneUtils.addToPane(oIsSolidLabelStackPane,    oIsSolidLabel);
    SceneUtils.addToPane(oImageViewLabelStackPane,  oImageViewLabel);
    SceneUtils.addToPane(oTileEventsLabelStackPane, oTileEventsLabel);
    SceneUtils.addToPane(oIsSolidStackPane,         oIsSolid);
    SceneUtils.addToPane(oImageViewStackPane,       oImageView);
  }
  
  
  private void placePanesInGridPane()
  {
    add(oIsSolidLabelStackPane, 0, 0);
    add(oIsSolidStackPane, 1, 0);
    
    add(oImageViewLabelStackPane, 0, 1);
    add(oImageViewStackPane, 1, 1);
    
    add(oTileEventsLabelStackPane, 0, 2);
    add(oTileEventsList, 1, 2);
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
  

  public void setTileEvents(ArrayList<TileEvent> pTileEvents)
  {
    ObservableList<TileEvent> pTileEventList = null;
    
    pTileEventList = oTileEventsList.getItems();
    
    pTileEventList.clear();
    pTileEventList.addAll(pTileEvents);
  }
  
  
  public Boolean isHighlighted()
  {
    if(oSelectedEditableRowIndex == null)
    {
      return false;
    }
    else
    {
      return true;
    }
  }
  
  
  public void closeEditableTileEventsMenu()
  {
    oEditableTileEventsMenuStage.close();
  }
  
  
  /////// Inner class to handle edit menu clicks ////////
  private class EditableMenuClickHandler implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      StackPane vClickedPane     = null;
      int       vClickedRowIndex = -1;
      
      if(oTileEditor.getCurrentTile() != null)
      {
        vClickedPane     = (StackPane)pMouseEvent.getSource();
        vClickedRowIndex = GridPane.getRowIndex(vClickedPane);
        
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
  
  
  private class TileEventListViewHandler implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      ListView<TileEvent>  vTileEventsListView   = null;
      TileEvent            vTileEvent            = null;
      
      if(pMouseEvent.getClickCount() == 2)
      {
        vTileEventsListView = (ListView<TileEvent>)pMouseEvent.getSource();
        vTileEvent          = vTileEventsListView.getSelectionModel().getSelectedItem();
        
        if(vTileEvent != null)
        {
          oEditableTileEventsMenu.setTileEvent(vTileEvent);
        }
        
        oEditableTileEventsMenuStage.show();
      }
    }
  }
  
  
}//End of TileEditableMenu class

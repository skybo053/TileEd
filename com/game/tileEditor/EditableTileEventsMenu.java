package com.game.tileEditor;

import java.util.ArrayList;

import com.game.tileEditor.tileEvents.TileEvent;
import com.game.tileEditor.tileEvents.TileEventArg;
import com.game.utilities.SceneUtils;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;


public class EditableTileEventsMenu extends BorderPane
{
  private static final double PANE_WIDTH  = 600.0;
  private static final double PANE_HEIGHT = 400.0;
  
  private static final String ADD_TILEEVENT_BUTTON_TEXT    = "Add Event";
  private static final String UPDATE_TILEEVENT_BUTTON_TEXT = "Update Event";
  
  private TileEvent        oTileEvent        = null;
  private TileEditor       oTileEditor       = null;
  
  private FlowPane   oTileEventClassPane      = null;
  private FlowPane   oTileEventArgsPane       = null;
  private ScrollPane oTileEventArgsScrollPane = null;
  private FlowPane   oButtonsPane             = null;
  private Label      oEventClassLabel         = null;
  private TextField  oEventClassTextField     = null;
  private Button     oAddUpdateButton         = null;
  private Button     oDeleteButton            = null;
  private Button     oAddArgButton            = null;
  private Button     oNewEventButton          = null;
  
  
  public EditableTileEventsMenu(TileEditor pTileEditor)
  {
    setPrefWidth(PANE_WIDTH);
    setPrefHeight(PANE_HEIGHT);
    
    oTileEditor       = pTileEditor;
    
    oTileEventClassPane      = new FlowPane();
    oTileEventArgsPane       = new FlowPane();
    oTileEventArgsScrollPane = new ScrollPane();
    oButtonsPane             = new FlowPane();
    oAddUpdateButton         = new Button(ADD_TILEEVENT_BUTTON_TEXT);
    oDeleteButton            = new Button("Delete TileEvent");
    oAddArgButton            = new Button("Add Argument");
    oNewEventButton          = new Button("Add New Event");
    oEventClassLabel         = new Label("Event Class Name: ");
    
    oEventClassTextField     = new TextField();
    oEventClassTextField.setPrefWidth(235.0);
    oEventClassTextField.setFocusTraversable(false);
    
    configureButtons();
    configureButtonsPane();
    configureTileEventClassPane();
    configureTileEventArgsPane();
    addPanesToBorderPane();
  }
  
  
  private void configureButtons()
  {
    disableButton(oDeleteButton, true);
    disableButton(oNewEventButton, true);
    
    oDeleteButton.setOnMouseClicked(pMouseEvent ->
    {
      oTileEditor.removeTileEvent(oTileEvent);
      oTileEditor.updateTileEventListViews();
      
      clearMenu();
    });
    
    oAddArgButton.setOnMouseClicked(pMouseEvent -> 
    {
      createArgumentPane();
    });
    
    oNewEventButton.setOnMouseClicked(pEvent -> 
    {
      clearMenu();
    });
    
    oAddUpdateButton.setOnMouseClicked(new AddUpdateButtonClickHandler());
  }
  
  
  private void configureButtonsPane()
  {
    oButtonsPane.setPrefHeight(60.0);
    oButtonsPane.setAlignment(Pos.CENTER);
    
    FlowPane.setMargin(oAddUpdateButton, new Insets(0,5,0,0));
    FlowPane.setMargin(oDeleteButton, new Insets(0,5,0,5));
    FlowPane.setMargin(oAddArgButton, new Insets(0,5,0,5));
    FlowPane.setMargin(oNewEventButton, new Insets(0,0,0,5));
    
    
    SceneUtils.addToPane(oButtonsPane, oAddUpdateButton);
    SceneUtils.addToPane(oButtonsPane, oDeleteButton);
    SceneUtils.addToPane(oButtonsPane, oAddArgButton);
    SceneUtils.addToPane(oButtonsPane, oNewEventButton);
  }
  
  
  private void configureTileEventArgsPane()
  {
    oTileEventArgsPane.setAlignment(Pos.CENTER);
    
    oTileEventArgsScrollPane.setContent(oTileEventArgsPane);
  }
  
  
  private void configureTileEventClassPane()
  {
    oTileEventClassPane.setPrefHeight(50.0);
    oTileEventClassPane.setAlignment(Pos.CENTER);
    
    SceneUtils.addToPane(oTileEventClassPane, oEventClassLabel);
    SceneUtils.addToPane(oTileEventClassPane, oEventClassTextField);
  }
  
  
  private void addPanesToBorderPane()
  {
    setTop(oTileEventClassPane);
    setCenter(oTileEventArgsScrollPane);
    setBottom(oButtonsPane);
  }
  
  
  private ArgumentHBox createArgumentPane()
  {
    ArgumentHBox vArgumentHBox = null;
    
    vArgumentHBox = new ArgumentHBox();
    
    SceneUtils.addToPane(oTileEventArgsPane, vArgumentHBox);
    
    return vArgumentHBox;
  }
  
  
  public void setTileEvent(TileEvent pTileEvent)
  {
    oTileEvent = pTileEvent;
    
    oEventClassTextField.setText(oTileEvent.getEventClassName());
    setButtonText(oAddUpdateButton, UPDATE_TILEEVENT_BUTTON_TEXT);
    disableButton(oDeleteButton, false);
    disableButton(oNewEventButton, false);
    
    for(TileEventArg vTileEventArg : oTileEvent.getTileEventArgs())
    {
      ArgumentHBox vArgumentHBox = createArgumentPane();
      
      vArgumentHBox.setArgumentClassTextField(vTileEventArg.getArgClassType());
      vArgumentHBox.setArgumentValueTextField(vTileEventArg.getArgValue());
    }
  }
  
  
  public void clearMenu()
  {
    oEventClassTextField.clear();
    
    SceneUtils.clearPane(oTileEventArgsPane);
    
    setButtonText(oAddUpdateButton, ADD_TILEEVENT_BUTTON_TEXT);
    disableButton(oDeleteButton, true);
    disableButton(oNewEventButton, true);
    
    oTileEvent = null;
  }
  
  
  private void disableButton(Button pButton, boolean pDisable)
  {
    pButton.setDisable(pDisable);
  }
  
  
  private void setButtonText(Button pButton, String pText)
  {
    pButton.setText(pText);
  }
  
  
  private class AddUpdateButtonClickHandler implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      ArgumentHBox            vArgumentHBox  = null;
      ArrayList<TileEventArg> vTileEventArgs = null;
      ArrayList<TileEvent>    vTileEvents    = null;
      TileEvent               vTileEvent     = null;
      String                  vEventClass    = null;
      
      if(oTileEventArgsPane.getChildren().size() == 0)
      {
        return;
      }
    
      vTileEventArgs = new ArrayList<TileEventArg>();
      vEventClass    = oEventClassTextField.getText();
      
      for(Node vNode : oTileEventArgsPane.getChildren())
      {
        String vArgClassType = null;
        String vArgValue     = null;
        
        vArgumentHBox = (ArgumentHBox)vNode;
        
        vArgClassType = vArgumentHBox.getArgumentClassText();
        vArgValue     = vArgumentHBox.getArgumentValueText();
        
        vTileEventArgs.add(new TileEventArg(vArgClassType, vArgValue));
      }
      
      if(oTileEvent != null)
      {
        oTileEvent.setTileEventArgs(vTileEventArgs);
        oTileEvent.setEventClassName(vEventClass);
      }
      else
      {
        vTileEvent = new TileEvent(vEventClass);
        vTileEvent.setTileEventArgs(vTileEventArgs);
        oTileEditor.addTileEvent(vTileEvent);
      }
      
      clearMenu();
      oTileEditor.updateTileEventListViews();
    }
  }
  
  
  private class ArgumentHBox extends HBox
  {
    private Label     oArgClassLabel     = null;
    private Label     oArgValueLabel     = null;
    private TextField oArgClassTextField = null;
    private TextField oArgValueTextField = null;
    
    
    public ArgumentHBox()
    {
      oArgClassLabel     = new Label("Argument Class ");
      oArgValueLabel     = new Label("Argument Value ");
      oArgClassTextField = new TextField();
      oArgValueTextField = new TextField();
      
      setAlignment(Pos.CENTER);
      
      HBox.setMargin(oArgClassTextField, new Insets(2,5,2,2));
      HBox.setMargin(oArgValueTextField, new Insets(2,0,2,2));
      
      getChildren().addAll(
          oArgClassLabel, 
          oArgClassTextField, 
          oArgValueLabel, 
          oArgValueTextField);
    }
    
    
    private void setArgumentClassTextField(String pArgumentClass)
    {
      oArgClassTextField.setText(pArgumentClass);
    }
    
    
    private String getArgumentClassText()
    {
      return oArgClassTextField.getText();
    }
    
    
    private void setArgumentValueTextField(String pArgumentValue)
    {
      oArgValueTextField.setText(pArgumentValue);
    }
    
    
    private String getArgumentValueText()
    {
      return oArgValueTextField.getText();
    }
  }
  
}

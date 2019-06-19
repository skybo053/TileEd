package com.game.EventHandlers;

import com.game.TileEditor.LoadedImagesMenu;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoadImagesHandler implements EventHandler<MouseEvent>
{
  private static final double WIDTH  = 340.0;
  private static final double HEIGHT = 160.0;
  
  private Stage            oStage                 = null;
  private Label            oEnteredImageNameLabel = null;
  private ImageView        oEnteredImageView      = null;
  private TextField        oImageNameTextField    = null;
  private LoadedImagesMenu oLoadedImagesMenu      = null;
  
  
  public LoadImagesHandler(LoadedImagesMenu pLoadedImagesMenu)
  {
    oLoadedImagesMenu      = pLoadedImagesMenu;
    
    oEnteredImageNameLabel = new Label();
    oEnteredImageView      = new ImageView();
    oImageNameTextField    = new TextField();
  }
  
  
  public void handle(MouseEvent pMouseEvent)
  {
    if(oStage == null)
    {
      oStage  = new Stage();
      
      oStage.setTitle("Load Image");
      oStage.setWidth(WIDTH);
      oStage.setHeight(HEIGHT);
      oStage.setScene(createLoadImageMenuScene());
      oStage.setResizable(false);
      oStage.initModality(Modality.APPLICATION_MODAL);
      oStage.setOnCloseRequest(new LoadMenuImageClose());
    }
    
    oStage.show();
  }
  
  
  private Scene createLoadImageMenuScene()
  {
    BorderPane vBorderPane         = null;
    GridPane   vChoicesGridPane    = null;
    GridPane   vLoadButtonGridPane = null;
    Label      vEnterNameLabel     = null;
    Label      vChooseImageLabel   = null;
    Button     vBrowseImagesButton = null;
    Button     vLoadButton         = null;
    
    vBorderPane         = new BorderPane();
    vChoicesGridPane    = new GridPane();
    vLoadButtonGridPane = new GridPane();
    vEnterNameLabel     = new Label("Enter Image Name: ");
    vChooseImageLabel   = new Label("Choose Image: ");
    vBrowseImagesButton = new Button("Browse Images");
    vLoadButton         = new Button("Load");
    
    vBrowseImagesButton.setOnMouseClicked(new BrowseImageButtonClick());
    vLoadButton.setOnMouseClicked(new LoadButtonClick());
    vLoadButton.setPrefWidth(100.0);
    
    oImageNameTextField.setPrefWidth(20.0);
    oImageNameTextField.setOnKeyPressed(new ImageNameTextFieldEnter());
    
    setColumnConstraints(vChoicesGridPane, 3);
    
    GridPane.setHalignment(vEnterNameLabel,        HPos.CENTER);
    GridPane.setHalignment(oImageNameTextField,    HPos.CENTER);
    GridPane.setHalignment(oEnteredImageNameLabel, HPos.CENTER);
    GridPane.setHalignment(vChooseImageLabel,      HPos.CENTER);
    GridPane.setHalignment(vBrowseImagesButton,    HPos.CENTER);
    GridPane.setHalignment(oEnteredImageView,      HPos.CENTER);
    
    vChoicesGridPane.add(vEnterNameLabel, 0, 0);
    vChoicesGridPane.add(oImageNameTextField, 1, 0);
    vChoicesGridPane.add(oEnteredImageNameLabel, 2, 0);
    vChoicesGridPane.add(vChooseImageLabel, 0, 1);
    vChoicesGridPane.add(vBrowseImagesButton, 1, 1);
    vChoicesGridPane.add(oEnteredImageView, 2, 1);
    vChoicesGridPane.setVgap(20.0);
    
    GridPane.setHalignment(vLoadButton, HPos.CENTER);
    GridPane.setValignment(vLoadButton, VPos.CENTER);

    setColumnConstraints(vLoadButtonGridPane, 1);
    setRowConstraint(vLoadButtonGridPane);
    
    vLoadButtonGridPane.setPrefHeight(50.0);
    vLoadButtonGridPane.add(vLoadButton, 0, 0);
    
    vBorderPane.setCenter(vChoicesGridPane);
    vBorderPane.setBottom(vLoadButtonGridPane);
    
    return new Scene(vBorderPane);
  }
  
  
  private void setColumnConstraints(GridPane pGridPane, int pNumColumns)
  {
    ColumnConstraints vColumnConstraints  = null;
    double            vPercentColumnWidth = 0.0;
    
    vPercentColumnWidth = 100.0 / pNumColumns;
    
    for(int i = 0; i < pNumColumns; ++i)
    {
      vColumnConstraints = new ColumnConstraints();
      vColumnConstraints.setPercentWidth(vPercentColumnWidth);
      
      pGridPane.getColumnConstraints().add(vColumnConstraints);
    }
  }
  
  
  private void setRowConstraint(GridPane pGridPane)
  {
    RowConstraints vRow1 = null;
      
    vRow1 = new RowConstraints();
    
    vRow1.setPercentHeight(100);
    
    pGridPane.getRowConstraints().addAll(vRow1);
  }
  
  
  private void clearValues()
  {
    oEnteredImageView.setImage(null);
    oEnteredImageNameLabel.setText("");
    oImageNameTextField.setText("");
  }
  
  
  private class LoadMenuImageClose implements EventHandler<WindowEvent>
  {
    public void handle(WindowEvent pWindowEvent)
    {
      oEnteredImageNameLabel.setText("");
    }
  } //End LoadMenuImageClose class
  
  
  private class ImageNameTextFieldEnter implements EventHandler<KeyEvent>
  {
    public void handle(KeyEvent pKeyEvent)
    {
      if(pKeyEvent.getCode() == KeyCode.ENTER)
      {
        String vEnteredText = null;
        
        vEnteredText = oImageNameTextField.getText().trim();
        
        if(vEnteredText.length() > 0)
        {
          oEnteredImageNameLabel.setText(vEnteredText);
          oImageNameTextField.setText("");
        }
      }
    }
  }// End ImageNameTextfieldEnter class
  
  
  private class BrowseImageButtonClick implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      FileChooser vFileChooser = null;
      Node        vNode        = null;
      
      vNode        = (Node) pMouseEvent.getSource();
      vFileChooser = new FileChooser();
      
      vFileChooser.showOpenDialog(vNode.getScene().getWindow());
    }
  }//End BrowseImageButtonClick class
  
  
  private class LoadButtonClick implements EventHandler<MouseEvent>
  {
    String vImageNameLabelText     = null;
    String vImageNameTextFieldText = null;
    
    public void handle(MouseEvent pMouseEvent)
    {
      vImageNameLabelText     = oEnteredImageNameLabel.getText().trim();
      vImageNameTextFieldText = oImageNameTextField.getText().trim();
      
      if(vImageNameLabelText != null && vImageNameLabelText.length() > 0)
      {
        oLoadedImagesMenu.addRow(vImageNameLabelText, oEnteredImageView);
        clearValues();
        oStage.close();
      }
      else if(vImageNameTextFieldText != null && vImageNameTextFieldText.length() > 0)
      {
        oLoadedImagesMenu.addRow(vImageNameTextFieldText, oEnteredImageView);
        clearValues();
        oStage.close();
      }
    }
  }//End LoadButtonClick class
  
  
}//End LoadImagesHandler class

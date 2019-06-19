package com.game.EventHandlers;

import com.game.TileEditor.LoadedImagesMenu;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoadImagesHandler implements EventHandler<MouseEvent>
{
  private static final double WIDTH  = 300.0;
  private static final double HEIGHT = 150.0;
  
  private Stage oStage = null;
  
  private Label     oEnteredImageNameLabel = null;
  private ImageView oEnteredImageView      = null;
  
  private TextField oImageNameTextField    = null;
  
  private LoadedImagesMenu oLoadedImagesMenu = null;
  
  
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
    GridPane  vGridPane           = null;
    Button    vBrowseImagesButton = null;
    
    vGridPane           = new GridPane();
    vBrowseImagesButton = new Button("Browse Images");
    
    vBrowseImagesButton.setOnMouseClicked(new BrowseImageButtonClick());
    
    oImageNameTextField.setPrefWidth(20.0);
    oImageNameTextField.setOnKeyPressed(new ImageNameTextFieldEnter());
    
    setColumnConstraints(vGridPane);
    
    GridPane.setHalignment(oImageNameTextField,    HPos.CENTER);
    GridPane.setHalignment(oEnteredImageNameLabel, HPos.CENTER);
    
    vGridPane.add(oImageNameTextField, 0, 0);
    vGridPane.add(oEnteredImageNameLabel, 1, 0);
    
    vGridPane.add(vBrowseImagesButton, 0, 1);
    vGridPane.add(oEnteredImageView, 1, 1);
    
    vGridPane.setVgap(20.0);
    
    oImageNameTextField.setPromptText("Image Name...");
    oImageNameTextField.getParent().requestFocus();
    
    return new Scene(vGridPane);
  }
  
  
  private void setColumnConstraints(GridPane pGridPane)
  {
    ColumnConstraints vColumn1 = null;
    ColumnConstraints vColumn2 = null;
    
    vColumn1 = new ColumnConstraints();
    vColumn2 = new ColumnConstraints();
    
    vColumn1.setPercentWidth(50.0);
    vColumn2.setPercentWidth(50.0);
    
    pGridPane.getColumnConstraints().addAll(vColumn1, vColumn2);
  }
  
  
  private class LoadMenuImageClose implements EventHandler<WindowEvent>
  {
    public void handle(WindowEvent pWindowEvent)
    {
      oEnteredImageNameLabel.setText(null);
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
          oImageNameTextField.setText(null);
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
  }
  
  
}//End LoadImagesHandler class

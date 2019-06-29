package com.game.EventHandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.game.TileEditor.LoadedImagesMenu;
import com.game.TileEditor.TileEditor;
import com.game.Utilities.SceneUtils;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
  private ImageView        oImageView             = null;
  private Image            oImage                 = null;
  private TextField        oImageNameTextField    = null;
  private LoadedImagesMenu oLoadedImagesMenu      = null;
  private FileChooser      oFileChooser           = null;
  
  
  public LoadImagesHandler(LoadedImagesMenu pLoadedImagesMenu)
  {
    oLoadedImagesMenu      = pLoadedImagesMenu;
    
    oEnteredImageNameLabel = new Label();
    oImageView             = new ImageView();
    oImageNameTextField    = new TextField();
    oFileChooser           = new FileChooser();
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
    
    SceneUtils.setColumnConstraints(vChoicesGridPane, 3);
    
    GridPane.setHalignment(vEnterNameLabel,        HPos.CENTER);
    GridPane.setHalignment(oImageNameTextField,    HPos.CENTER);
    GridPane.setHalignment(oEnteredImageNameLabel, HPos.CENTER);
    GridPane.setHalignment(vChooseImageLabel,      HPos.CENTER);
    GridPane.setHalignment(vBrowseImagesButton,    HPos.CENTER);
    GridPane.setHalignment(oImageView,             HPos.CENTER);
    
    vChoicesGridPane.add(vEnterNameLabel, 0, 0);
    vChoicesGridPane.add(oImageNameTextField, 1, 0);
    vChoicesGridPane.add(oEnteredImageNameLabel, 2, 0);
    vChoicesGridPane.add(vChooseImageLabel, 0, 1);
    vChoicesGridPane.add(vBrowseImagesButton, 1, 1);
    vChoicesGridPane.add(oImageView, 2, 1);
    vChoicesGridPane.setVgap(20.0);
    
    GridPane.setHalignment(vLoadButton, HPos.CENTER);
    GridPane.setValignment(vLoadButton, VPos.CENTER);

    SceneUtils.setColumnConstraints(vLoadButtonGridPane, 1);
    SceneUtils.setRowConstraints(vLoadButtonGridPane, 1);
    
    vLoadButtonGridPane.setPrefHeight(50.0);
    vLoadButtonGridPane.add(vLoadButton, 0, 0);
    
    vBorderPane.setCenter(vChoicesGridPane);
    vBorderPane.setBottom(vLoadButtonGridPane);
    
    return new Scene(vBorderPane);
  }
  
  
  private void clearValues()
  {
    oImage = null;
    oImageView.setImage(null);
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
    String vEnteredText = null;
    
    public void handle(KeyEvent pKeyEvent)
    {
      if(pKeyEvent.getCode() == KeyCode.ENTER)
      {
        vEnteredText = oImageNameTextField.getText().trim();
        
        if(vEnteredText.length() > 0)
        {
          if(oLoadedImagesMenu.containsKey(vEnteredText) == false)
          {
            oImageNameTextField.setText("");
            oEnteredImageNameLabel.setText(vEnteredText);
          }
          else
          {
            SceneUtils.displayAlert(
                Alert.AlertType.ERROR, 
                "ERROR", 
                "There is already a loaded image with name \"" + 
                vEnteredText + "\"");
          }
        }
      }
    }
  }// End ImageNameTextfieldEnter class
  
  
  private class BrowseImageButtonClick implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      File   vChoosenFile     = null;
      String vChoosenFilePath = null;
      String vDirectoryName   = null;
      Node   vNode            = null;
      int    vIndex           = -1;
      
      try
      {
        vNode        = (Node) pMouseEvent.getSource();
        vChoosenFile = oFileChooser.showOpenDialog(vNode.getScene().getWindow());
        
        if(vChoosenFile != null)
        {
          vChoosenFilePath = vChoosenFile.getAbsolutePath();
          
          oImage = new Image(
              new FileInputStream(vChoosenFilePath),
              TileEditor.TILE_IMAGE_LENGTH,
              TileEditor.TILE_IMAGE_LENGTH,
              false,
              true);
          
          if(oImage.isError())
          {
            oImage = null;
          }
          else
          {
            oImageView.setImage(oImage);
          }
          
          vIndex         = vChoosenFilePath.lastIndexOf("\\");
          vDirectoryName = vChoosenFilePath.substring(0, vIndex);
          
          oFileChooser.setInitialDirectory(new File(vDirectoryName));
        }
      }
      catch(FileNotFoundException pFileNotFoundException)
      {
        System.out.println("LoadImagesHandler.BrowseImageButtonClick - " + 
                            pFileNotFoundException.getMessage());
      }
      catch(IllegalArgumentException pIllegalArgumentException)
      {
        System.out.println("LoadImagesHandler.BrowseImageButtonClick - " + 
                            pIllegalArgumentException.getMessage());
      }
      catch(NullPointerException pNullPointerException)
      {
        System.out.println("LoadImagesHandler.BrowseImageButtonClick - " + 
                            pNullPointerException.getMessage());
      }
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
        oLoadedImagesMenu.addRow(vImageNameLabelText, oImage);
        clearValues();
        oStage.close();
      }
      else if(vImageNameTextFieldText != null && vImageNameTextFieldText.length() > 0)
      {
        if(oLoadedImagesMenu.containsKey(vImageNameTextFieldText) == false)
        {
          oLoadedImagesMenu.addRow(vImageNameTextFieldText, oImage);
          clearValues();
          oStage.close();
        }
        else
        {
          SceneUtils.displayAlert(
              Alert.AlertType.ERROR, 
              "ERROR", 
              "There is already a loaded image with name \"" + 
               vImageNameTextFieldText + "\"");
        }
      }
      else
      {
        SceneUtils.displayAlert(
            Alert.AlertType.ERROR, 
            "ERROR", 
            "You must enter a name for your image");
      }
    }
  }//End LoadButtonClick class
  
  
  
}//End LoadImagesHandler class

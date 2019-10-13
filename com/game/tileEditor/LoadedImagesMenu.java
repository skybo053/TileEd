package com.game.tileEditor;

import java.util.HashMap;
import java.util.Iterator;

import com.game.eventHandlers.LoadImagesHandler;
import com.game.utilities.SceneUtils;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class LoadedImagesMenu extends BorderPane
{
  private static final double WIDTH  = 400.0;
  private static final double HEIGHT = 300.0;
  
  private static final double BUTTON_GRIDPANE_HEIGHT = 50.0;
  private static final double BUTTON_HEIGHT          = 20.0;
  private static final double BUTTON_WIDTH           = 80.0;
  
  private GridPane                                    oNameImageGridPane = null;
  private HashMap<String, Image>                      oNameImageMap      = null;
  private HashMap<Integer, LoadedImagesMenu.RowPanes> oRowPanesMap       = null;
  
  private int      oRowCount                = 0;
  private RowPanes oPreviousSelectedRow     = null;
  private RowPanes oCurrentSelectedRow      = null;
  private Integer  oCurrentSelectedRowIndex = null;
  
  private TileEditor oTileEditor = null;
  
  
  public LoadedImagesMenu(TileEditor pTileEditor)
  {
    GridPane   vButtonGridPane      = null;
    ScrollPane vNameImageScrollPane = null;
    
    oTileEditor          = pTileEditor;
    
    vNameImageScrollPane = new ScrollPane();
    oNameImageMap        = new HashMap<String, Image>();
    oRowPanesMap         = new HashMap<Integer, LoadedImagesMenu.RowPanes>();
    
    setPrefHeight(HEIGHT);
    setPrefWidth(WIDTH);
    
    oNameImageGridPane   = getNameImageGridPane();
    vButtonGridPane      = getButtonGridPane();
    vNameImageScrollPane = getNameImageScrollPane();
    
    vNameImageScrollPane.setContent(oNameImageGridPane);
    setCenter(vNameImageScrollPane);
    setBottom(vButtonGridPane);
  }
  
  
  private ScrollPane getNameImageScrollPane()
  {
    ScrollPane vScrollPane = null;
    
    vScrollPane = new ScrollPane();
    
    vScrollPane.setFitToHeight(true);
    vScrollPane.setFitToWidth(true);
    vScrollPane.getStyleClass().clear();
    
    return vScrollPane;
  }
  
  
  private GridPane getNameImageGridPane()
  {
    GridPane vGridPane = null;
    
    vGridPane = new GridPane();
    
    SceneUtils.setColumnConstraints(vGridPane, 2);
    
    return vGridPane;
  }
  
  
  public void addRow(String pImageName, Image pImage)
  {
    StackPane                 vStackPaneName  = null;
    StackPane                 vStackPaneImage = null;
    LoadedImagesMenu.RowPanes vRowPanes       = null;
    
    try
    {
      vStackPaneName  = new StackPane();
      vStackPaneImage = new StackPane();
      vRowPanes       = new LoadedImagesMenu.RowPanes();
      
      vStackPaneName.setPadding(new Insets(1,0,1,0));
      vStackPaneImage.setPadding(new Insets(1,0,1,0));
      
      vStackPaneName.setOnMouseClicked(new LoadedImagesMenuRowSelect());
      vStackPaneImage.setOnMouseClicked(new LoadedImagesMenuRowSelect());
      
      if(oRowCount % 2 == 0)
      {
        vStackPaneName.setStyle("-fx-background-color: #f4f5f7;");
        vStackPaneImage.setStyle("-fx-background-color: #f4f5f7;");
      }
      else
      {
        vStackPaneName.setStyle("-fx-background-color: white;");
        vStackPaneImage.setStyle("-fx-background-color: white;");
      }
      
      vRowPanes.setNamePane(vStackPaneName);
      vRowPanes.setImagePane(vStackPaneImage);
      vRowPanes.setRowBaseStyle(vStackPaneName.getStyle());
      
      SceneUtils.addToPane(vStackPaneName, new Label(pImageName));
      
      if(pImage != null)
      {
        SceneUtils.addToPane(vStackPaneImage, new ImageView(pImage));
      }
      
      oNameImageGridPane.add(vStackPaneName, 0, oRowCount);
      oNameImageGridPane.add(vStackPaneImage, 1, oRowCount);
      
      oNameImageMap.put(pImageName, pImage);
      oRowPanesMap.put(oRowCount, vRowPanes);
      
      ++oRowCount;
    }
    catch(NullPointerException e)
    {
      e.printStackTrace();
    }
  }
  
  
  public Image getImage(String pImageName)
  {
    return oNameImageMap.get(pImageName);
  }
  
  
  public boolean containsKey(String pImageName)
  {
    return oNameImageMap.containsKey(pImageName);
  }
  
 
  public void clearSelectedRow()
  {
    if(oCurrentSelectedRow != null)
    {
      oCurrentSelectedRow.unselectRow();
    }
    
    oCurrentSelectedRow  = null;
    oPreviousSelectedRow = null;
  }
  
  
  private GridPane getButtonGridPane()
  {
    GridPane vGridPane     = null;
    Button   vAddButton    = null;
    Button   vDeleteButton = null;
    
    vGridPane     = new GridPane();
    vAddButton    = new Button("Add Image");
    vDeleteButton = new Button("Delete");
    
    vGridPane.setPrefHeight(BUTTON_GRIDPANE_HEIGHT);
    
    GridPane.setHalignment(vAddButton, HPos.CENTER);
    GridPane.setHalignment(vDeleteButton, HPos.CENTER);
    
    GridPane.setValignment(vAddButton, VPos.CENTER);
    GridPane.setValignment(vDeleteButton, VPos.CENTER);
    
    SceneUtils.setColumnConstraints(vGridPane, 2);
    SceneUtils.setRowConstraints(vGridPane, 1);
    
    vAddButton.setPrefHeight(BUTTON_HEIGHT);
    vAddButton.setPrefWidth(BUTTON_WIDTH);
    vAddButton.setOnMouseClicked(new LoadImagesHandler(this));
    
    vDeleteButton.setPrefHeight(BUTTON_HEIGHT);
    vDeleteButton.setPrefWidth(BUTTON_WIDTH);
    vDeleteButton.setOnAction(new DeleteImagesHandler());
    
    vGridPane.add(vAddButton, 0, 0);
    vGridPane.add(vDeleteButton, 1, 0);

    return vGridPane;
  }
  
  
  private class LoadedImagesMenuRowSelect implements EventHandler<MouseEvent>
  {
    public void handle(MouseEvent pMouseEvent)
    {
      StackPane vClickedStackPane = null;
      
      vClickedStackPane        = (StackPane)pMouseEvent.getSource();
      oCurrentSelectedRowIndex = GridPane.getRowIndex(vClickedStackPane);
      
      oPreviousSelectedRow = oCurrentSelectedRow;
      oCurrentSelectedRow  = oRowPanesMap.get(oCurrentSelectedRowIndex);
      
      if(oPreviousSelectedRow != null)
      {
        oPreviousSelectedRow.unselectRow();
      }
      
      oCurrentSelectedRow.selectRow("-fx-background-color: #87ceeb");
      
      if(pMouseEvent.getClickCount() == 2 && oTileEditor.getCurrentTile() != null)
      {
        oTileEditor.setImageReferences(
            oCurrentSelectedRow.getImageName(), 
            oCurrentSelectedRow.getImage());
        
        oTileEditor.closeLoadedImagesMenu();
      }
    }
  }
  
  
  private class DeleteImagesHandler implements EventHandler<ActionEvent>
  {
    public void handle(ActionEvent pActionEvent)
    {
      if(oCurrentSelectedRow != null)
      {
        LoadedImagesMenu.RowPanes vRowPanes         = null;
        StackPane                 vNamePane         = null;
        StackPane                 vImagePane        = null;
        ObservableList<Node>      vNameImageList    = null;
        
        vRowPanes         = oRowPanesMap.get(oCurrentSelectedRowIndex);
        vNamePane         = vRowPanes.getNamePane();
        vImagePane        = vRowPanes.getImagePane();
        
        vNameImageList    = oNameImageGridPane.getChildren();
        
        oRowPanesMap.remove(oCurrentSelectedRowIndex);
        oNameImageMap.remove(vRowPanes.getImageName());
        
        for(Iterator<Node> vIterator = vNameImageList.iterator(); vIterator.hasNext();)
        {
          Node vCurrentNode = vIterator.next();
          
          if(vCurrentNode == vNamePane || vCurrentNode == vImagePane)
          {
            vIterator.remove();
          }
        }
      }
    }
  }
  
  
  private class RowPanes
  {
    StackPane oNamePane     = null;
    StackPane oImagePane    = null;
    String    oBaseRowStyle = null;
    
    private RowPanes()
    {
      
    }
    
    
    private void setNamePane(StackPane pNamePane)
    {
      oNamePane = pNamePane;
    }
    
    
    private StackPane getNamePane()
    {
      return oNamePane;
    }
    
    
    private void setImagePane(StackPane pImagePane)
    {
      oImagePane = pImagePane;
    }
    
    
    private StackPane getImagePane()
    {
      return oImagePane;
    }
    
    
    private void setRowBaseStyle(String pBaseRowStyle)
    {
      oBaseRowStyle = pBaseRowStyle;
    }
    
    
    private String getRowBaseStyle()
    {
      return oBaseRowStyle;
    }
    
    
    private void unselectRow()
    {
      oNamePane.setStyle(oBaseRowStyle);
      oImagePane.setStyle(oBaseRowStyle);
    }
    
    
    private void selectRow(String pSelectedRowStyle)
    {
      oNamePane.setStyle(pSelectedRowStyle);
      oImagePane.setStyle(pSelectedRowStyle);
    }
    
    
    private Image getImage()
    {
      ImageView vImageView = null;
      
      vImageView = (ImageView)oImagePane.getChildren().get(0);
      
      return vImageView.getImage();
    }
    
    
    private String getImageName()
    {
      Label vNameLabel = null;
      
      vNameLabel = (Label)oNamePane.getChildren().get(0);
      
      return vNameLabel.getText();
    }
  }
  
}

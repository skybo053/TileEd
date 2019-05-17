package com.game.TileEditor;



import java.util.ArrayList;

import com.game.EventHandlers.Quit_Handler;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;


public class TileEditor extends Application
{
  Scene      oScene          = null;
  MenuBar    oMenuBar        = null;
  Menu       oFileMenu       = null;
  BorderPane oBorderPane     = null;
  TilePane   oTilePane       = null;
  GridPane   oGridPane       = null;
  GridPane   oSideGridPane   = null;
  
  private static final String Title = "TEd 1.0.0";
  
  
  public static void main(String[] pArgs) 
  {
    launch(pArgs);
  }
  
  
  public void start(Stage pStage) throws Exception 
  {
    initialize();
    
    pStage.setTitle(Title);
    pStage.setWidth(600.0);
    pStage.setHeight(500.0);
    pStage.setScene(oScene);
    pStage.show();
  }
  

  private void initialize()
  {
    oBorderPane   = new BorderPane();
    oMenuBar      = new MenuBar();
    oFileMenu     = new Menu("File");
    oTilePane     = new TilePane();
    oGridPane     = new GridPane();
    oSideGridPane = new GridPane();
   
    
    createMenuItems();
    createGridPane();
    
    oBorderPane.setTop(oMenuBar);
    oBorderPane.setCenter(oGridPane);
    
    oSideGridPane.setPrefWidth(100);
    oSideGridPane.setStyle("-fx-background-color: pink;");
    
    oBorderPane.setRight(oSideGridPane);
    
    oScene = new Scene(oBorderPane);
    oScene.getStylesheets().add("file:Resources/CSS/stylesheet.css");
  }


  private void createMenuItems()
  {
    MenuItem vMenuItem = null;
    
    vMenuItem = new MenuItem("Save");
    oFileMenu.getItems().add(vMenuItem);
    
    vMenuItem = new MenuItem("Load");
    oFileMenu.getItems().add(vMenuItem);
    
    vMenuItem = new MenuItem("Quit");
    vMenuItem.setOnAction(new Quit_Handler());
    oFileMenu.getItems().add(vMenuItem);
    
    
    oMenuBar.getMenus().add(oFileMenu);
  }
  
  
  private void createGridPane()
  {
    Tile      vTile      = null;
    StackPane vStackPane = null;
    
    try
    {
      ArrayList<Node> vImages = new ArrayList<Node>();
    
      vTile      = new Tile(35, 35);
      vStackPane = new StackPane();
      
      vTile.setImageView("file:Resources/Images/grass.png");
      
      vStackPane.getStyleClass().add("grid-cell");
      vStackPane.getChildren().add(vTile.getImageView());
      
      vImages.add(vStackPane);
      
      vTile      = new Tile(35, 35);
      vStackPane = new StackPane();
      
      vTile.setImageView("file:Resources/Images/water.png");
      vStackPane.getStyleClass().add("grid-cell");
      vStackPane.getChildren().add(vTile.getImageView());
      
      vImages.add(vStackPane);
      
      
      
      vTile      = new Tile(35, 35);
      vStackPane = new StackPane();
      
      vTile.setImageView("file:Resources/Images/dirt.png");
      vStackPane.getStyleClass().add("grid-cell");
      vStackPane.getChildren().add(vTile.getImageView());
      
      vImages.add(vStackPane);
    
    
    Node[] vNodeArray  = vImages.toArray(new Node[vImages.size()]);
    
    oGridPane.addRow(0, vNodeArray);
    
    System.out.println("dirt tile at position: " + GridPane.getRowIndex(vStackPane)
    + ", " + GridPane.getColumnIndex(vStackPane));
    }
    catch(Exception e)
    {
     System.out.println(e.getMessage());
    }
  }
}
  
  



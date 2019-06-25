package com.game.Utilities;

import javafx.scene.control.Alert;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SceneUtils 
{
  public static void setColumnConstraints(GridPane pGridPane, int pNumColumns)
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
  
  
  public static void setRowConstraints(GridPane pGridPane, int pNumRows)
  {
    RowConstraints vRowConstraints  = null;
    double         vPercentRowHeight = 0.0;
    
    vPercentRowHeight = 100.0 / pNumRows;
    
    for(int i = 0; i < pNumRows; ++i)
    {
      vRowConstraints = new RowConstraints();
      vRowConstraints.setPercentHeight(vPercentRowHeight);
      
      pGridPane.getRowConstraints().add(vRowConstraints);
    }
  }
  
  
  public static void displayAlert(
      Alert.AlertType pAlertType, 
      String          pAlertTitle, 
      String          pAlertText)
  {
    Alert vAlert = null;
    
    vAlert = new Alert(pAlertType);
    
    vAlert.setTitle(pAlertTitle);
    vAlert.setContentText(pAlertText);
    vAlert.showAndWait();
  }

}

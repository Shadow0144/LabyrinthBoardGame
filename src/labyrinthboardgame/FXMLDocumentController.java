/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package labyrinthboardgame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Corbi
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private GridPane gameGridPane;
    
    private String tLocation = getClass().getResource("assets/T.png").toString();
    private BackgroundImage myBI = new BackgroundImage(new Image(tLocation,90,90,false,true),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, 
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    
    @FXML
    private void updateHighlight(MouseEvent event) 
    {
        //System.out.println("You clicked me!");
        //((Pane)event.getSource()).setBackground(new Background(myBI));
    }
    
    @FXML
    private void addTile(MouseEvent event) 
    {
        System.out.println("You clicked me!");
        ((Pane)event.getSource()).setBackground(new Background(myBI));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

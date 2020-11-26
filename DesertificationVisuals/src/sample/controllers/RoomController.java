package sample.controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.WorldOfZuul.*;

import java.io.IOException;


public class RoomController {


    @FXML
    public ImageView background;
    @FXML
    public Button East;
    @FXML
    public Button North;
    @FXML
    public Button West;
    @FXML
    public Button South;
    @FXML
    public TextArea roomInfo;
    public Text Trash;
    public AnchorPane anchorPane;

    private static int trashCount = 0;
    public static int coins = 0;
    public static int saplings = 0;
    public static int trash = 0;

    public static Inventory inventory = new Inventory();
    public Text inventoryTrash;
    public Text Coins;
    public Text Saplings;
    public Button shop;


    Game game = new Game();

    @FXML
    public void setRoomInfo() {
        roomInfo.setText(game.getRoomInfo());
    }

    public void roomDirection(MouseEvent event) {
        if (event.getSource() == East) {
            Command command = new Command(CommandWord.GO, "east");
            game.goRoom(command);
            setRoomInfo();
            setBackground();
        }
        if (event.getSource() == South) {
            Command command = new Command(CommandWord.GO, "south");
            game.goRoom(command);
            setRoomInfo();
            setBackground();
        }
        if (event.getSource() == North) {
            Command command = new Command(CommandWord.GO, "north");
            game.goRoom(command);
            setRoomInfo();
            setBackground();
        }
        if (event.getSource() == West) {
            Command command = new Command(CommandWord.GO, "west");
            game.goRoom(command);
            setRoomInfo();
            setBackground();
        }

    }


    public void setBackground() {
        if (game.getCurrentRoom().getType() == 2) {
            background.setImage(new Image("Resources/TutorialRoom.png"));
        }else if(game.getCurrentRoom().getType() == 3){
            background.setImage(new Image("Resources/CurrencyRoom.png"));
            removeTrashFromRoom();
        }else if(game.getCurrentRoom().getType() == 4){
            background.setImage(new Image("Resources/CurrencyObtainLeft.png"));
            addTrashToRoom(50, 50, "t1");
            addTrashToRoom(100, 100, "t2");
            addTrashToRoom(300, 600, "t3");
            System.out.println(trashCount);
        }else if(game.getCurrentRoom().getType() == 5){
            background.setImage(new Image("Resources/DesertBaseRoom.png"));
        }else if(game.getCurrentRoom().getType() == 6){
            background.setImage(new Image("Resources/DesertLeft.png"));
        }else if(game.getCurrentRoom().getType() == 8){
            background.setImage(new Image("Resources/DesertRight.png"));
        }else if(game.getCurrentRoom().getType() == 9){
            background.setImage(new Image("Resources/DesertTop.png"));
        }else if(game.getCurrentRoom().getType() == 1){
            background.setImage(new Image("Resources/EntryRoom.png"));
        }else if(game.getCurrentRoom().getType() == 11){
            background.setImage(new Image("Resources/CurrencyObtainRight.png"));
        }


    }

    public ImageView addTrash(){
        ImageView trash = new ImageView(new Image("Resources/trash.png"));
        trash.setFitHeight(50);
        trash.setFitWidth(50);
        trashCount++;
        trash.setId("t"+trashCount);


        trash.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                anchorPane.getChildren().remove(trash);
                inventory.addTrash();
                Trash.setText(""+inventory.countTrash());
            }
        });


        return trash;
    }

    public Node getTrashId(String id){
        for (Node node :
                anchorPane.getChildren()) {
            if(node.getId() != null && node.getId().equals(id)){
                return node;
            }
        }
        return null;
    }


    // xlimit = 350, ylimit = 550
    public void addTrashToRoom(double x, double y, String id){
        if(x>350) x = 350;
        if(y>550) y = 550;
        anchorPane.getChildren().add(addTrash());
        AnchorPane.setTopAnchor(getTrashId(id), x);
        AnchorPane.setLeftAnchor(getTrashId(id), y);
    }

    public void openShop(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource()==shop){
            if(game.getCurrentRoom().getType() == 3){

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../fxml/shop.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 400, 300);
                Stage stage = new Stage();

                stage.setTitle("Shop");
                stage.setScene(scene);
                stage.show();

            }
        }
    }

    public void removeTrashFromRoom(){
        for (int i = 1; i <= trashCount; i++) {
            for (Node node :
                anchorPane.getChildren()) {
                if(node.getId() != null && node.getId().equals("t"+i)){
                    anchorPane.getChildren().remove(node);
                    break;
                }
            }
        }
        trashCount=0;
    }


}

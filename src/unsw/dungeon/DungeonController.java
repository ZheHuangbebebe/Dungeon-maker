package unsw.dungeon;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 * A JavaFX controller for the dungeon.
 * @author Robert Clifton-Everest
 *
 */
public class DungeonController {

    @FXML
    private GridPane squares;

    private List<ImageView> initialEntities;


    private Dungeon dungeon;

    public DungeonController(Dungeon dungeon, List<ImageView> initialEntities) {
        this.dungeon = dungeon;
        this.initialEntities = new ArrayList<>(initialEntities);
    }

    @FXML
    public void initialize() {
        Image ground = new Image("/dirt_0_new.png");

        // Add the ground first so it is below all other entities
        for (int x = 0; x < dungeon.getWidth(); x++) {
            for (int y = 0; y < dungeon.getHeight(); y++) {
                squares.add(new ImageView(ground), x, y);
            }
        }

        for (ImageView entity : initialEntities)
            squares.getChildren().add(entity);
        
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        //System.out.println(dungeon.getPlayer().getX() + " and " + dungeon.getPlayer().getY());
        switch (event.getCode()) {
        case UP:
        	dungeon.move(Direction.UP);
            break;
        case DOWN:
        	dungeon.move(Direction.DOWN);
            break;
        case LEFT:
        	dungeon.move(Direction.LEFT);
            break;
        case RIGHT:
        	dungeon.move(Direction.RIGHT);
            break;
        case W:
        	dungeon.useSword(Direction.UP);
        	break;
        case A:
        	dungeon.useSword(Direction.LEFT);
        	break;
        case S:
        	dungeon.useSword(Direction.DOWN);
        	break;
        case D:
        	dungeon.useSword(Direction.RIGHT);
        	break;
        case B:
        	//but bomb_lit_1.png does't print, maybe covered by player image view
        	addLitBomb(dungeon.useBomb(),new ImageView(new Image("/bomb_lit_1.png")));
        	break;
        default:
            break;
        }
        //System.out.println(dungeon.getEntityByLocation(dungeon.getPlayer().getX(),dungeon.getPlayer().getY()));
        changeLitBombImage();
        changOpenDoorImage();
        printMsgBox();
    }
    
    private void changeLitBombImage() {
    	 for(Entity e : dungeon.getEntities()) {
         	if(e == null) continue;
         	if(e.getClass().equals(LitBomb.class))
         		e.switchBool();
         }
    }
    
    private void changOpenDoorImage() {
    	for(Entity e : dungeon.getEntityByLocation(dungeon.getPlayer().getX(), dungeon.getPlayer().getY())) {
    		if(e.getClass().equals(Door.class) && ((Door) e).getState()) {
    			squares.add(new ImageView(new Image("/open_door.png")), e.getX(), e.getY());
    		}
    	}
    }
    
    private void printMsgBox() {
    	if(dungeon.gameCompleted()) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Game Completed!");
	    	alert.setHeaderText("Congratulations!");
	    	alert.setContentText("You Win!");
	    	alert.showAndWait();
    	}
    	if(dungeon.getPlayer().getState().getClass().equals(Dead.class)) {
    		Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Game Over!");
	    	alert.setHeaderText("Unfortunately!");
	    	alert.setContentText("You Dead!");
	    	alert.showAndWait();
    	}
    }

	private void addLitBomb(LitBomb e, ImageView n) {
		squares.add(n, e.getX(), e.getY());
		e.b().addListener(new ChangeListener<>() {
        	@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        		n.setVisible(false);
        		ImageView newN = null;
        		switch(e.getTimer()) {
        		case 2:
        			newN = new ImageView(new Image("/bomb_lit_2.png"));
					break;
        		case 1:
        			newN = new ImageView(new Image("/bomb_lit_3.png"));
					break;
        		case 0:
        			newN = new ImageView(new Image("/bomb_lit_4.png"));
					break;
        		}
        		if(newN != null)
        			addLitBomb(e, newN);
			}
        });
	}

}


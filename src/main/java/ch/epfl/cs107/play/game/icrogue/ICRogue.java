package ch.epfl.cs107.play.game.icrogue;


import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level;
import ch.epfl.cs107.play.game.icrogue.area.level0.Level0;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.Level0Room;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;


public class ICRogue extends AreaGame {

    public final static float CAMERA_SCALE_FACTOR = 11.f;

    private Level0Room currentRoom = new Level0Room(new DiscreteCoordinates(0,0));
    private Level0 level0;

    public final static float STEP = 0.05f;
    private ICRoguePlayer player;

    @Override
    public String getTitle() {
        return "ICRogue";
    }

    protected void initLevel() {

        level0 = new Level0(new DiscreteCoordinates(0,0),4,2);
        //),4,2);
        ICRogueRoom startArea = level0.getStartArea();

        level0.addRoomToArea(this);
        setCurrentArea(startArea.getTitle(),true);
        System.out.println(startArea.getTitle());

        player = new ICRoguePlayer(startArea, Orientation.UP, new DiscreteCoordinates(2,2));
        startArea.registerActor(player);
        startArea.setVisited(true);
        //player.enterArea(startArea, new DiscreteCoordinates(2,2));
    }

    public void update(float deltaTime) {

        super.update(deltaTime);

        Keyboard keyboard = getWindow().getKeyboard();
        //Keyboard keyboard = level0.getStartArea().getKeyboard();

        if (keyboard.get(Keyboard.R).isPressed())
             initLevel();

        if(player.isPassingConnector()) {
            switchRoom();
        }
        if(player.getIsDead()) {
            System.out.println("GAME OVER");
            System.exit(0);
        }
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {

        if (super.begin(window, fileSystem)) {
            initLevel();
            return true;
        }
        return false;
    }
    protected void switchRoom() {

        Connector connector = player.getPassedConnector();
        player.leaveArea();
        ICRogueRoom currentRoom = (ICRogueRoom) setCurrentArea(connector.getDestinationAreaName(),false);
        player.enterArea(currentRoom, connector.getDestinationCoordinates());
        player.setPassingConnector(false);
        currentRoom.setVisited(true);

    }
}

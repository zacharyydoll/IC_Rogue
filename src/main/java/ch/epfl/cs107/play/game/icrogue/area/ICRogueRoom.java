package ch.epfl.cs107.play.game.icrogue.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.Connector;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Enemy;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;

public abstract class ICRogueRoom extends Area implements Logic {
    private DiscreteCoordinates roomCoordinates;
    protected String behaviorName;
    private final static float CAMERA_SCALE_FACTOR = 11.f;
    private ICRogueBehavior behavior;
    private List<DiscreteCoordinates> connectorCoordinates;
    private List<Orientation> orientations;
    private List<Connector> connectors;
    private List<Enemy> enemies;
    private boolean signalIsOn;
    protected boolean isVisited;

    public ICRogueRoom(List<DiscreteCoordinates> connectorCoordinates,
                       List<Orientation> orientations,
                       String BehaviorName,
                       DiscreteCoordinates roomCoordinates) {

        this.connectorCoordinates = connectorCoordinates;
        this.orientations = orientations;
        this.roomCoordinates = roomCoordinates;
        this.behaviorName = BehaviorName;
        isVisited = false;
        signalIsOn = true;
        connectors = new ArrayList<>();

        for (int i = 0; i < connectorCoordinates.size(); ++i) {
            connectors.add(new Connector(this, orientations.get(i).opposite(), connectorCoordinates.get(i)));
        }
    }

    @Override
    public final float getCameraScaleFactor() {
        return ICRogueRoom.CAMERA_SCALE_FACTOR;
    }

    protected void createArea() {
        for(Connector connector : connectors) {
            registerActor(connector);
        }
    }
    public DiscreteCoordinates getRoomCoordinates(){
        return roomCoordinates;
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new ICRogueBehavior(window,behaviorName);
            setBehavior(behavior);
            registerActor(new Background(this, behaviorName));
            createArea();
            return true;
        }
        return false;
    }

    public void changeRoomCoordinates(ConnectorInRoom connector, String destination){
        connectors.get(connector.getIndex()).setDestinationAreaName(destination,connector.getDestination());
    }

    public void changeRoomConnector(ConnectorInRoom connector, String destination){
        changeRoomCoordinates(connector,destination);
        connectors.get(connector.getIndex()).setState(Connector.ConnectorState.CLOSED);
    }

    public void changeLockConnector(ConnectorInRoom connector){
        connectors.get(connector.getIndex()).setState(Connector.ConnectorState.LOCKED);
    }
    public void setVisited(boolean b) {
        this.isVisited = b;
    }

    public void update(float deltaTime) {
        super.update(deltaTime);

        Keyboard keyboard = getKeyboard();
        if(isOn()) {
            for (Connector connector : connectors) {
                if(connector.getState() == Connector.ConnectorState.CLOSED)
                    connector.setState(Connector.ConnectorState.OPEN);
            }
        }

        if (keyboard.get(Keyboard.O).isPressed()) {
            for (Connector connector : connectors) {
                if(connector.getState() == Connector.ConnectorState.CLOSED ||
                        connector.getState() == Connector.ConnectorState.LOCKED)
                    connector.setState(Connector.ConnectorState.OPEN);
            }
        }
    }
    public void setSignalIsOnIsOn(boolean b) {
        signalIsOn = b;
    }
    @Override
    public boolean isOn(){
        return isVisited && signalIsOn;
    }
}

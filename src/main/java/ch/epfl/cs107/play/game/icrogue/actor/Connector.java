package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Connector extends AreaEntity implements Interactable {
    private final int NO_KEY_ID = 0;
    private ConnectorState state;
    private String destinationAreaName;
    private DiscreteCoordinates destinationCoordiantes;
    private int keyIdentifier;
    private Sprite sprite_locked;
    private Sprite sprite_closed;
    private Sprite sprite_invisible;
    private ConnectorInteractionHandler connectorHandler;

    public Connector(Area currentArea, Orientation orientation, DiscreteCoordinates position){

        super(currentArea,orientation,position);
        this.state = ConnectorState.INVISIBLE;
        this.keyIdentifier = NO_KEY_ID;

        sprite_invisible = new Sprite("icrogue/invisibleDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1,
                orientation.ordinal()%2+1,
                this);
        sprite_closed = new Sprite("icrogue/door_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1,
                orientation.ordinal()%2+1,
                this);
        sprite_locked = new Sprite("icrogue/lockedDoor_"+orientation.ordinal(),
                (orientation.ordinal()+1)%2+1,
                orientation.ordinal()%2+1,
                this);
    }

    public Connector(Area currentArea, Orientation orientation, DiscreteCoordinates position, int keyIdentifier) {
        this(currentArea, orientation, position);
        this.keyIdentifier = keyIdentifier;
    }

    public void setState(ConnectorState state) {
        this.state = state;
    }

    public int getKeyIdentifier() {
        return keyIdentifier;
    }

    public String getDestinationAreaName(){
        System.out.println(destinationAreaName);
        return destinationAreaName;
    }

    public void setDestinationAreaName(String destinationAreaName, DiscreteCoordinates destitationAreaCoordinates) {
        this.destinationAreaName = destinationAreaName;
        this.destinationCoordiantes = destitationAreaCoordinates;
    }

    public DiscreteCoordinates getDestinationCoordinates() {
        System.out.println(destinationCoordiantes);
        return destinationCoordiantes;
    }

    public ConnectorState getState(){
        return state;
    }

    @Override
    public void draw(Canvas canvas) {
        switch (this.state) {
            case INVISIBLE -> sprite_invisible.draw(canvas);
            case CLOSED -> sprite_closed.draw(canvas);
            case LOCKED -> sprite_locked.draw(canvas);
        }
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        DiscreteCoordinates coord = getCurrentMainCellCoordinates();
        return List.of(coord,coord.jump(new Vector((getOrientation().ordinal()+1)%2, getOrientation().ordinal()%2)));
    }

    @Override
    public boolean takeCellSpace() {
        return (getState() != Connector.ConnectorState.OPEN);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(connectorHandler, isCellInteraction);
    }

    public enum ConnectorState{
        OPEN,
        CLOSED,
        LOCKED,
        INVISIBLE;
    }
    public void changeState(){
        if(this.getState() == Connector.ConnectorState.OPEN) {
            this.setState(Connector.ConnectorState.CLOSED);
        } else if(this.getState() == Connector.ConnectorState.CLOSED){
            this.setState(Connector.ConnectorState.OPEN);
        }
    }

    private class ConnectorInteractionHandler implements ICRogueInteractionHandler {}
}

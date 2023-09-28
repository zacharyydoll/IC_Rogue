package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Level0Room extends ICRogueRoom {

    public Level0Room(DiscreteCoordinates roomCoordinates) {
        super(Level0Connectors.getAllConnectorsPosition(), Level0Connectors.getAllConnectorsOrientation(), "icrogue/Level0Room", roomCoordinates);
    }


    public enum Level0Connectors implements ConnectorInRoom {
        W(new DiscreteCoordinates(0,4), new DiscreteCoordinates(8,5), Orientation.LEFT),
        S(new DiscreteCoordinates(4,0), new DiscreteCoordinates(5,8), Orientation.DOWN),
        E(new DiscreteCoordinates(9,4), new DiscreteCoordinates(1,5), Orientation.RIGHT),
        N(new DiscreteCoordinates(4,9), new DiscreteCoordinates(5,1 ), Orientation.UP);

        private final DiscreteCoordinates position;
        private final DiscreteCoordinates destination;
        private final Orientation orientation;

        private Level0Connectors(DiscreteCoordinates position, DiscreteCoordinates destination, Orientation orientation){
            this.position = position;
            this.destination = destination;
            this.orientation = orientation;
        }

        @Override
        public int getIndex() {
            return this.ordinal();
        }

        @Override
        public DiscreteCoordinates getDestination() {
            return this.destination;
        }

        public static List<Orientation> getAllConnectorsOrientation(){
            List<Orientation> list_orientation = new ArrayList<Orientation>();
            list_orientation.add(W.orientation);
            list_orientation.add(S.orientation);
            list_orientation.add(E.orientation);
            list_orientation.add(N.orientation);

            return list_orientation;
        }

        public static List<DiscreteCoordinates> getAllConnectorsPosition(){
            List<DiscreteCoordinates> list_position = new ArrayList<DiscreteCoordinates>();
            list_position.add(W.position);
            list_position.add(S.position);
            list_position.add(E.position);
            list_position.add(N.position);

            return list_position;
        }
    }

    /*@Override
    protected void createArea() {
        super.createArea();
        registerActor(new Background(this, behaviorName));
        //registerActor(new Cherry(this, Orientation.UP, new DiscreteCoordinates(6,3)));
        //registerActor(new Staff(this, Orientation.UP, new DiscreteCoordinates(4, 3)));
        registerActor(new Background(this, behaviorName));

    }*/
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    public String getTitle() {
        return "icrogue/level0"+getRoomCoordinates().x + getRoomCoordinates().y;
    }
}

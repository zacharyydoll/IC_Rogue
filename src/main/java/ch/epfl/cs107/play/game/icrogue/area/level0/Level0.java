package ch.epfl.cs107.play.game.icrogue.area.level0;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.area.level0.rooms.*;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0 extends Level {

    private final static DiscreteCoordinates startRoomCoordinates = new DiscreteCoordinates(1,1);
    private final static int KEY = 0;
    private final static int BOSS_KEY = 1;

    /*public LevelO(){
        carte = new int[4][2];
    }*/

    public Level0(DiscreteCoordinates coordinates, int width, int height) {
        super(coordinates, width, height);
        setStartRoom(startRoomCoordinates);

    }
    @Override
    public void generateFixedMap(){
        //generateMap1();
        generateMap2();
    }
    private void generateMap1() {
        final int KEY_2;

        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0KeyRoom(room00, KEY));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);
        lockRoomConnector(room00, Level0Room.Level0Connectors.E, KEY);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1, 0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level000", Level0Room.Level0Connectors.W);
    }

    private void generateMap2() {


        DiscreteCoordinates room00 = new DiscreteCoordinates(0, 0);
        setRoom(room00, new Level0TurretRoom(room00));
        setRoomConnector(room00, "icrogue/level010", Level0Room.Level0Connectors.E);
        setRoomConnector(room00, "icrogue/level001", Level0Room.Level0Connectors.S);

        lockRoomConnector(room00, Level0Room.Level0Connectors.S, BOSS_KEY);
        setRoomConnectorDestination(room00, "icrogue/level001", Level0Room.Level0Connectors.S);

        DiscreteCoordinates room10 = new DiscreteCoordinates(1,0);
        setRoom(room10, new Level0Room(room10));
        setRoomConnector(room10, "icrogue/level011", Level0Room.Level0Connectors.S);
        setRoomConnector(room10, "icrogue/level020", Level0Room.Level0Connectors.E);

        lockRoomConnector(room10, Level0Room.Level0Connectors.W, BOSS_KEY);
        setRoomConnectorDestination(room10, "icrogue/level000", Level0Room.Level0Connectors.W);

        DiscreteCoordinates room20 = new DiscreteCoordinates(2,0);
        setRoom(room20,  new Level0StaffRoom(room20));
        setRoomConnector(room20, "icrogue/level010", Level0Room.Level0Connectors.W);
        setRoomConnector(room20, "icrogue/level030", Level0Room.Level0Connectors.E);

        DiscreteCoordinates room30 = new DiscreteCoordinates(3,0);
        setRoom(room30, new Level0KeyRoom(room30, BOSS_KEY));
        setRoomConnector(room30, "icrogue/level020", Level0Room.Level0Connectors.W);
        setRoomConnector(room30, "icrogue/level031", Level0Room.Level0Connectors.S);

        DiscreteCoordinates room11 = new DiscreteCoordinates(1, 1);
        setRoom (room11, new Level0Room(room11));
        setRoomConnector(room11, "icrogue/level010", Level0Room.Level0Connectors.N);

        DiscreteCoordinates room31 = new DiscreteCoordinates(3, 1);
        setRoom (room31, new Level0BowRoom(room31));
        setRoomConnector(room31, "icrogue/level030", Level0Room.Level0Connectors.N);

        DiscreteCoordinates room01 = new DiscreteCoordinates(0, 1);
        setRoom (room01, new Level0FriendRoom(room01));
        setRoomConnector(room01, "icrogue/level000", Level0Room.Level0Connectors.N);
    }

    public void addRoomToArea(AreaGame room){
        for(int i = 0 ; i < this.carte.length; ++i){
            for (int j = 0; j < this.carte[0].length ; ++j){
                if(carte[i][j] != null)
                    room.addArea(this.carte[i][j]);
            }
        }
    }

    public ICRogueRoom getStartArea() {
        return super.carte[startRoomCoordinates.x][startRoomCoordinates.y];
    }
}

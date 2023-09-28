package ch.epfl.cs107.play.game.icrogue.area.level0;

import ch.epfl.cs107.play.game.areagame.AreaGame;
import ch.epfl.cs107.play.game.icrogue.area.ConnectorInRoom;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public abstract class Level implements Logic {
    protected ICRogueRoom[][] carte;
    private DiscreteCoordinates arrivalCoordinates;
    private DiscreteCoordinates positionBoss;
    private String destination;
    private boolean isSolved;
    public Level(DiscreteCoordinates arrivalCoordinates, int width, int height){
        carte = new ICRogueRoom[width][height];
        this.arrivalCoordinates = arrivalCoordinates;
        this.positionBoss = new DiscreteCoordinates(0,0);
        generateFixedMap();
    }

    protected void setRoom(DiscreteCoordinates coords,ICRogueRoom room){
        this.carte[coords.x][coords.y] = room;
    }

    protected void setRoomConnectorDestination(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){
        carte[coords.x][coords.y].changeRoomCoordinates(connector,destination);

    }

    protected void setRoomConnector(DiscreteCoordinates coords, String destination, ConnectorInRoom connector){
        carte[coords.x][coords.y].changeRoomConnector(connector,destination);
    }


    protected void lockRoomConnector(DiscreteCoordinates coords, ConnectorInRoom connector, int keyId){
        carte[coords.x][coords.y].changeLockConnector(connector);
    }

    protected void setStartRoom(DiscreteCoordinates coords) {
        destination = carte[coords.x][coords.y].getTitle();
    }

    public abstract void generateFixedMap();

    public abstract void addRoomToArea(AreaGame room);

    protected boolean isSolved(){
        boolean isSolved = true;
        for(int i = 0 ; i < carte.length ;++i){
            for(int j = 0; j < carte[0].length ; ++j) {
                if (carte[i][j] != null) {
                    isSolved = isSolved && carte[i][j].isOn();
                }
            }
        }
        return isSolved;
    }

    @Override
    public boolean isOn(){
        return isSolved();
    }
}

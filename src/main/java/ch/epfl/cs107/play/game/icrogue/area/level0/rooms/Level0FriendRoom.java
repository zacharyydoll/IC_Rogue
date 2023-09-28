package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.Ally.Ally;
import ch.epfl.cs107.play.game.icrogue.actor.Ally.King;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

public class Level0FriendRoom extends Level0AllyRoom{
    public Level0FriendRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        ArrayList<Ally> allies =  new ArrayList<>();
        King king1 = new King(this, Orientation.UP,new DiscreteCoordinates(5,5));
        allies.add(king1);
        setAllies(allies);
    }
    @Override
    public void createArea() {
        super.createArea();

    }
}

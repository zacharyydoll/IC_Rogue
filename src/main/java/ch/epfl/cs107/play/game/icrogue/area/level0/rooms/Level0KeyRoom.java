package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Boss;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0KeyRoom extends Level0ItemRoom {
    private final int KEY_ID;

    public Level0KeyRoom(DiscreteCoordinates coordinates, int KEY_ID) {
        super(coordinates);
        this.KEY_ID = KEY_ID;
    }
    @Override
    public void createArea() {
        super.createArea();
        Key key = new Key(this, Orientation.UP, new DiscreteCoordinates(5,5),KEY_ID);
        registerActor(key);
        super.addItem(key);
        Boss boss = new Boss(this, Orientation.UP, new DiscreteCoordinates(5,5));
        this.registerActor(boss);
    }
}

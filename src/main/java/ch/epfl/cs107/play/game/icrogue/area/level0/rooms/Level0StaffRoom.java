package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.ICRogue;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.game.icrogue.actor.items.Key;
import ch.epfl.cs107.play.game.icrogue.actor.items.Staff;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Level0StaffRoom extends Level0ItemRoom {

    private List<Item> items;
    private boolean signalIsOn;
    public Level0StaffRoom(DiscreteCoordinates coordinates) {
        super(coordinates);
        addItem(new Staff(this, Orientation.UP, new DiscreteCoordinates(5,5)));
        items = new ArrayList<>();
    }

    @Override
    public void createArea() {
        super.createArea();
        Staff staff = new Staff(this, Orientation.UP, new DiscreteCoordinates(5, 5));
        registerActor(staff);
        items.add(staff);
        super.addItem(staff);

    }
    public boolean itemsAreCollected() {
        if(items != null) {
            for (Item item : items) {
                if (item.isCollected())
                    return true;
            }
        }
        return false;
    }
    @Override
    public void update(float deltaTime) {
        if(isOn()) {
            super.setSignalIsOnIsOn(true);
        }
        super.setSignalIsOnIsOn(false);
        super.update(deltaTime);
    }
}

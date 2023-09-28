package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.CollectableAreaEntity;
import ch.epfl.cs107.play.game.icrogue.actor.items.Item;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public abstract class Level0ItemRoom extends Level0Room {

    private List<Item> itemList = new ArrayList<>();
    public Level0ItemRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    @Override
    public void createArea() {
        super.createArea();
    }

    protected void addItem(Item item){
        if(item != null) {
            itemList.add(item);
        }
    }

    private boolean isSolved() {
        for (Item item : itemList) {
            if (!item.isCollected())
                return false;
        }
        return true;
    }

    @Override
    public boolean isOn() {
        return (isSolved() && isVisited);
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}
   

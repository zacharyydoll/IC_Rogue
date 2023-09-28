package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;
/*
 * Author:       Karen Staner
 * Date:         21.12.22
 */

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Boss;
import ch.epfl.cs107.play.game.icrogue.actor.items.Bow;
import ch.epfl.cs107.play.game.icrogue.actor.items.Cherry;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level0BowRoom extends Level0ItemRoom {
    private boolean hasBeenPickedUp;

    public Level0BowRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        hasBeenPickedUp = false;
        addItem(new Bow(this, Orientation.UP, new DiscreteCoordinates(5, 5)));
    }

    public void setHasBeenPickedUp(boolean hasBeenPickedUp) {

        this.hasBeenPickedUp = hasBeenPickedUp;
    }

    @Override
    public void createArea() {
        super.createArea();
        if (!hasBeenPickedUp) {
            Bow bow = new Bow(this, Orientation.UP, new DiscreteCoordinates(5, 5));
            registerActor(bow);
            super.addItem(bow);
            Cherry cherry = new Cherry(this, Orientation.UP, new DiscreteCoordinates(1,1));
            this.registerActor(cherry);
        }

    }
}


package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.icrogue.actor.Ally.Ally;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;


public class Level0AllyRoom extends Level0Room {

    private List<Ally> allies;

    public Level0AllyRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
        allies = new ArrayList<>();
    }

    protected void setAllies(List<Ally> allies){
        this.allies = new ArrayList<>(allies);// deep copy
    }

    @Override
    protected void createArea() {
        for(Ally ally : allies) {
            registerActor( ally);
        }
        super.createArea();
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);
    }
}




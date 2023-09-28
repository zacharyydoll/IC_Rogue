package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Boss;
import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Enemy;
import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Turret;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;

public class Level0TurretRoom  extends Level0EnemyRoom{

    public Level0TurretRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);

        ArrayList<Orientation> orientation1 = new ArrayList<>();
        orientation1.add(Orientation.DOWN);
        orientation1.add(Orientation.RIGHT);
        Turret turret1 = new Turret(this, Orientation.UP,new DiscreteCoordinates(3,5),orientation1);

        ArrayList<Orientation> orientation2 = new ArrayList<>();
        orientation2.add(Orientation.UP);
        orientation2.add(Orientation.LEFT);
        Turret turret2 = new Turret(this, Orientation.UP,new DiscreteCoordinates(2,1),orientation2);


        ArrayList<Enemy> ennemies = new ArrayList<>();
        ennemies.add(turret1);
        ennemies.add(turret2);
        //ennemies.add(new Boss(this, Orientation.UP, new DiscreteCoordinates(5,5)));
        setEnnemies(ennemies);
    }
}

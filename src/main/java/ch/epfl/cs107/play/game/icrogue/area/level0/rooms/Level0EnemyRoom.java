package ch.epfl.cs107.play.game.icrogue.area.level0.rooms;

import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Enemy;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.ArrayList;
import java.util.List;

public class Level0EnemyRoom extends Level0Room{
    private List<Enemy> ennemies;

    public Level0EnemyRoom(DiscreteCoordinates roomCoordinates) {
        super(roomCoordinates);
    }

    protected void setEnnemies(List<Enemy> ennemies){
        this.ennemies = new ArrayList<>(ennemies); // deep copy
    }

    @Override
    protected void createArea() {
        for(Enemy enemy : ennemies) {
            registerActor(enemy);
        }
        super.createArea();
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);
        for(Enemy enemy : ennemies) {
            if (enemy.isDead()) {
                unregisterActor(enemy);
            }
        }
    }
}


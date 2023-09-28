package ch.epfl.cs107.play.game.icrogue.actor.ennemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

abstract public class Enemy extends ICRogueActor {
    private boolean isDead;
    private Sprite sprite;

    private int currentHP;
    private int fullHP;

    public Enemy(Area area, Orientation orientation, DiscreteCoordinates position, int fullHP) {
        super(area, orientation, position);
        isDead = false;
        this.fullHP = fullHP;
    }
    public Enemy(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        isDead = false;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public boolean takeCellSpace() {
        return !isDead();
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    public boolean isDead() {
        return isDead;
    }
    public void setDead() {
        isDead = true;
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}

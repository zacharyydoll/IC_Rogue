package ch.epfl.cs107.play.game.icrogue.actor.Ally;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

abstract public class Ally extends ICRogueActor {

    private Sprite sprite;

    private boolean isDead;

    private int currentHP;

    private int fullHP;

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public Ally(Area area, Orientation orientation, DiscreteCoordinates position, int fullHP) {
        super(area, orientation, position);
        isDead = false;
        this.fullHP = fullHP;
    }

    public Ally(Area area, Orientation orientation, DiscreteCoordinates position) {
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

    public boolean isDead() {
        return isDead;
    }

    public void setDead() {
        isDead = true;
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }
}


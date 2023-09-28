package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRogueActor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

import java.util.Collections;
import java.util.List;

public abstract class Projectile extends ICRogueActor implements Consumable, Interactor {

    private Sprite sprite;
    private boolean isConsumed;
    private int dmg;
    private int frames;
    private final int DEFAULT_DMG = 1;
    private final int DEFAULT_MOVE_DURATION = 10;
    protected int moveDuration;


    public Projectile(Area area, Orientation orientation, DiscreteCoordinates startCoordinates, int dmg, int frames) {
        super(area, orientation, startCoordinates);

        this.dmg = dmg;
        this.frames = frames;
        isConsumed = false;
    }

    public Projectile(Area area, Orientation orientation, DiscreteCoordinates startCoordinates) {
        super(area, orientation, startCoordinates);
        int dmg = DEFAULT_DMG;
        int move_duration = DEFAULT_MOVE_DURATION;
    }

    public void update(float deltaTime) {
        move(frames);
        super.update(deltaTime);
    }
    public void consume() {
        getOwnerArea().unregisterActor(this);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList
                (getCurrentMainCellCoordinates()) ;
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList
                (getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }
    @Override
    public boolean wantsViewInteraction() {
        return true;
    }


    @Override
    public boolean wantsCellInteraction() {
        return !isConsumed;
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    public boolean isConsumed() {
        return isConsumed;
    }

    protected void setConsumed(boolean isConsumed) {
        this.isConsumed = isConsumed;
    }


    protected class ProjectileHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            if (cell.getICRogueCellType() == ICRogueBehavior.ICRogueCellType.WALL || cell.getICRogueCellType() == ICRogueBehavior.ICRogueCellType.HOLE )
                consume();
        }
    }
}



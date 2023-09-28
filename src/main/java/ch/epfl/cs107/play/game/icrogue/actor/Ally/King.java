package ch.epfl.cs107.play.game.icrogue.actor.Ally;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class King extends Ally implements Interactor {
    private static int energy = 100;
    private FriendInteractionHandler handler;

    private boolean isDead;

    private Sprite sprite_bottom = new Sprite("zelda/king", .75f, 1.5f, this,
            new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));
    private Sprite sprite_right = new Sprite("zelda/king", .75f, 1.5f, this,
            new RegionOfInterest(0, 32, 16, 32), new Vector(.15f, -.15f));
    private Sprite sprite_top = new Sprite("zelda/king", .75f, 1.5f, this,
            new RegionOfInterest(0, 64, 16, 32), new Vector(.15f, -.15f));
    private Sprite sprite_left = new Sprite("zelda/king", .75f, 1.5f, this,
            new RegionOfInterest(0, 96, 16, 32), new Vector(.15f, -.15f));

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     * @param fullHP
     */
    public King(Area area, Orientation orientation, DiscreteCoordinates position, int fullHP) {
        super(area, orientation, position, fullHP);
        handler = new FriendInteractionHandler();
        isDead = false;
    }

    public King(Area area, Orientation orientation, DiscreteCoordinates position) {
        this(area, orientation, position, energy);
    }

    @Override
    public void draw(Canvas canvas) {
        switch (getOrientation()) {
            case UP -> sprite_top.draw(canvas);
            case DOWN -> sprite_bottom.draw(canvas);
            case LEFT -> sprite_left.draw(canvas);
            case RIGHT -> sprite_right.draw(canvas);
        }
    }

    /*public void takeDamage(int dmg) {
        //this method would be used in superieur levels,
        // if the player's friend was also in a room with monsters
        energy -= dmg;
        if (energy <= 0) {
            isDead = true;
        }
    } */

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (isDead)
            this.getOwnerArea().unregisterActor(this);
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return false;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    private class FriendInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {
        }

    }
}

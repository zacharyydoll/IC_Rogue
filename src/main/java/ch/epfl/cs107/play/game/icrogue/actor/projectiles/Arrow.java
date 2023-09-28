package ch.epfl.cs107.play.game.icrogue.actor.projectiles;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.ICRogueBehavior;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Boss;
import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Turret;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.List;

public class Arrow extends Projectile {
    private ArrowInteractionHandler handler;
    public final static int ARROW_DMG = 20;
    private boolean firedByPlayer;

    public Arrow(Area area, Orientation orientation, DiscreteCoordinates startCoordinates) {
        super(area, orientation, startCoordinates, ARROW_DMG, 5);

        setSprite(new Sprite("zelda/arrow", 1f, 1f, this, new RegionOfInterest(32*orientation.ordinal(), 0, 32, 32), new Vector(0, 0)));

        firedByPlayer = true;
        handler = new ArrowInteractionHandler();

    }

    public void setFiredByPlayer(boolean b) {
        firedByPlayer = b;
    }

    public boolean getFiredByPlayer() {
        return this.firedByPlayer;
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        if (!isConsumed())
            other.acceptInteraction(handler, isCellInteraction);
    }

    @Override
    public void draw(Canvas canvas) {
        getSprite().draw(canvas);
    }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void consume() {
        super.consume();
        resetMotion();
    }

    private class ArrowInteractionHandler implements ICRogueInteractionHandler {

        @Override
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {
            if(!firedByPlayer) {
                player.takeDamage(ARROW_DMG);
                consume();
            }
        }

        @Override
        public void interactWith(Boss boss, boolean isCellInteraction) {
            if (firedByPlayer) {
                boss.takeDamage(ARROW_DMG);
                consume();
            }
        }

        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            if (cell.getICRogueCellType() == ICRogueBehavior.ICRogueCellType.WALL || cell.getICRogueCellType() == ICRogueBehavior.ICRogueCellType.HOLE) {
                consume();
            }
        }
    }
}




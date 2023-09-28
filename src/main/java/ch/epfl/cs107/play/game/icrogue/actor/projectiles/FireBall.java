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

public class FireBall extends Projectile {
    public final static int FIREBALL_DMG = 10;
    private FireballInteractionHandler handler;

    private boolean firedByPlayer;

    public FireBall(Area area, Orientation orientation, DiscreteCoordinates startCoordinates) {
        super(area, orientation, startCoordinates, FIREBALL_DMG, 5);

        setSprite(new Sprite("zelda/fire", 1f, 1f, this, new RegionOfInterest(0, 0, 16, 16), new Vector(0, 0)));
        this.firedByPlayer = true;
        handler = new FireballInteractionHandler();
    }
    public void setFiredByPlayer(boolean b){
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

    private class FireballInteractionHandler implements ICRogueInteractionHandler {

        @Override
        public void interactWith(ICRogueBehavior.ICRogueCell cell, boolean isCellInteraction) {
            if (cell.getICRogueCellType() == ICRogueBehavior.ICRogueCellType.WALL || cell.getICRogueCellType() == ICRogueBehavior.ICRogueCellType.HOLE) {
                consume();
            }
        }

        @Override
        public void interactWith(ICRoguePlayer player, boolean isCellInteraction) {
            if (!firedByPlayer) {
                player.takeDamage(FIREBALL_DMG);
                consume();
            }
        }

        @Override
        public void interactWith(Boss boss, boolean isCellInteraction) {
            if (firedByPlayer) {
                boss.takeDamage(FIREBALL_DMG);
                consume();
            }
        }

        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {
            if (firedByPlayer) {
                turret.setDead();
                consume();
            }
        }
    }
}

package ch.epfl.cs107.play.game.icrogue.actor.ennemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ICRoguePlayer;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Turret extends Enemy {

    private final static float COOLDOWN = 10.f;
    private final static float dt = .5f;
    private final List<Orientation> orientationArrows;
    private float timeCounter;
    private TurretInteractionHandler handler;

    private boolean isDead;

    public Turret(Area area, Orientation orientation, DiscreteCoordinates position, List<Orientation> orientationArrows) {
        super(area, orientation, position);
        setSprite(new Sprite("icrogue/static_npc", 1.5f, 1.5f, this, null, new Vector(-0.25f, 0)));
        this.orientationArrows = new ArrayList<>(orientationArrows); // deep copy of the list to avoid encapsulation errors
        this.timeCounter = 0;
        isDead = false;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        timeCounter += dt;

        if (timeCounter == COOLDOWN) {
            attack();
            timeCounter = 0;
        }
        if(super.isDead()) {
            getOwnerArea().unregisterActor(this);
        }
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        getSprite().draw(canvas);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    private void attack() {
        if (!this.isDead()) {
            for (Orientation orientationArrow : orientationArrows) {
                getOwnerArea().registerActor(new Arrow(getOwnerArea(), orientationArrow, getCurrentMainCellCoordinates()));
            }
        }
    }

    private class TurretInteractionHandler implements ICRogueInteractionHandler {
        public void interactWith(Arrow arrow, boolean isCellInteraction) {
        }
    }
}


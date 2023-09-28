package ch.epfl.cs107.play.game.icrogue.actor.ennemies;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.FireBall;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Boss extends Enemy implements Interactor {

    private static int energy = 200;
    private BossInteractionHandler handler;
    private final static float ATTACK_PROB = 0.5f;
    private final static int FRAMES_MOVE = 5;
    public final static float COOLDOWN = 10.f;
    private static final float dt = .5f;
    private State state;
    private float timerCounter;
    private boolean isDead;

    private Sprite sprite_bottom = new Sprite("zelda/darkLord", 2, 1.3F, this, new RegionOfInterest(0, 64, 32, 32), new Vector(.15f, -.15f));
    private Sprite sprite_top = new Sprite("zelda/darkLord", 2, 1.3F, this, new RegionOfInterest(0, 0, 32, 32), new Vector(.15f, -.15f));
    private Sprite sprite_left = new Sprite("zelda/darkLord", 2, 1.3F, this, new RegionOfInterest(0, 32, 32, 32), new Vector(.15f, -.15f));
    private Sprite sprite_right = new Sprite("zelda/darkLord", 2, 1.3F, this, new RegionOfInterest(0, 96, 32, 32), new Vector(.15f, -.15f));

    private Boss(Area area, Orientation orientation, DiscreteCoordinates position, int fullHP) {
        super(area, orientation, position, fullHP);
        state = State.NORMAL;
        handler = new BossInteractionHandler();
        isDead = false;
    }

    public Boss(Area area, Orientation orientation, DiscreteCoordinates position) {
        this(area, orientation, position, energy);
    }

    private boolean strategicOrienting() {

        int randomInt;

        List<DiscreteCoordinates> fireBallPos;
        List<Orientation> randomOrientations = new ArrayList<Orientation>();
        Orientation orientation;
        FireBall fireBall;


        for (Orientation o : Orientation.values()) {
            randomOrientations.add(o);
        }

        do {
            randomInt = RandomGenerator.getInstance().nextInt(randomOrientations.size());
            orientation = Orientation.values()[randomInt];

            randomOrientations.remove(randomInt);

            fireBallPos = Collections.singletonList(getCurrentMainCellCoordinates().jump(orientation.toVector()));

            fireBall = new FireBall(getOwnerArea(), orientation, fireBallPos.get(0));

        } while (!randomOrientations.isEmpty() && !getOwnerArea().canEnterAreaCells(fireBall, fireBallPos));

        if (getOwnerArea().canEnterAreaCells(fireBall, fireBallPos)) {
            orientate(orientation);
            return true;
        }

        return false;
    }

    @Override
    public boolean takeCellSpace() {
        return !isDead();
    }

    @Override
    public boolean isCellInteractable() {
        return !isDead();
    }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return true;
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    private void randomState() {
        if (RandomGenerator.getInstance().nextFloat() <= ATTACK_PROB) {
            state = State.ATTACK;
        }
    }

    protected void movement() {
        if (!isDead() && !isDisplacementOccurs()) {
            orientate(Orientation.values()[RandomGenerator.getInstance().nextInt(4)]);
            move(FRAMES_MOVE);
        }
    }

    private void move() {
        switch (state) {

            case ATTACK:
                if (strategicOrienting()) {
                    FireBall fireball = new FireBall(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
                    getOwnerArea().registerActor(fireball);
                    fireball.setFiredByPlayer(false);
                    state = State.NORMAL;
                }
                break;
            case NORMAL:
                movement();
        }
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        timerCounter += dt;
        randomState();
        if (timerCounter == COOLDOWN) {
            move();
            movement();
            timerCounter = 0;
        }
        if(isDead)
            this.getOwnerArea().unregisterActor(this);
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

    public void takeDamage(int dmg) {
         energy -= dmg;
        if (energy <= 0) {
            isDead = true;
        }
    }
    private enum State {
        ATTACK,
        NORMAL;

    }

    private class BossInteractionHandler implements ICRogueInteractionHandler {


        @Override
        public void interactWith(FireBall fireBall, boolean isCellInteraction) {
            if (fireBall.getFiredByPlayer()) {
                if (fireBall.isCellInteractable()) {
                    takeDamage(FireBall.FIREBALL_DMG);
                    getOwnerArea().unregisterActor(fireBall);

                }
            }
        }


        @Override
        public void interactWith(Arrow arrow, boolean isCellInteraction) {
            if(arrow.getFiredByPlayer()) {
                if (arrow.isCellInteractable()) {
                    takeDamage(Arrow.ARROW_DMG);
                    getOwnerArea().unregisterActor(arrow);

                }
            }
        }
    }
}

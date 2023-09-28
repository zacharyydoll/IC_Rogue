package ch.epfl.cs107.play.game.icrogue.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Interactor;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.ennemies.Turret;
import ch.epfl.cs107.play.game.icrogue.actor.items.*;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.Arrow;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.FireBall;
import ch.epfl.cs107.play.game.icrogue.area.ICRogueRoom;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Button;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ICRoguePlayer extends ICRogueActor implements Interactor {

    private final static int MOVE_DURATION = 4;
    private final static int MAX_ENERGY = 100;
    private final static float SPEED_FIRE_COOLDOWN = 3.5f;
    private float SPEED_FIRE_DURATION = 2f;

    private final static float COOLDOWN = 7f;
    private final static float dt = .5f;
    private boolean hasSpeedFire;
    private float timeCounter;
    private boolean wantsInteraction = false;
    private boolean isPassingConnector;
    private int energylevel;
    private int nbOfArrows;
    private boolean hasStaff = false;
    private boolean hasKey = false;
    private boolean hasBow = false;
    private Connector passedConnector;
    private Area oldArea;

    private ArrayList<Integer> keyIdentifiers;
    private ICRoguePlayerInteractionHandler handler;
    private final Keyboard keyboard;
    private boolean isDead;
    private List<Item> weapons;
    private int weaponCount = 0;

    private boolean savedFriend;

    //Player without the staff and bow
    Sprite sprite_bottom = new Sprite("zelda/player", .75f, 1.5f, this,
            new RegionOfInterest(0, 0, 16, 32), new Vector(.15f, -.15f));
    Sprite sprite_right = new Sprite("zelda/player", .75f, 1.5f, this,
            new RegionOfInterest(0, 32, 16, 32), new Vector(.15f, -.15f));
    Sprite sprite_top = new Sprite("zelda/player", .75f, 1.5f, this,
            new RegionOfInterest(0, 64, 16, 32), new Vector(.15f, -.15f));
    Sprite sprite_left = new Sprite("zelda/player", .75f, 1.5f, this,
            new RegionOfInterest(0, 96, 16, 32), new Vector(.15f, -.15f));

    //Player with the staff
    Sprite spriteWater_bottom = new Sprite("zelda/player.staff_water", 1.5f, 1.5f, this,
            new RegionOfInterest(0, 0, 32, 32), new Vector(.15f, -.15f));
    Sprite spriteWater_right = new Sprite("zelda/player.staff_water", 1.5f, 1.5f, this,
            new RegionOfInterest(0, 32, 32, 32), new Vector(.15f, -.15f));
    Sprite spriteWater_top = new Sprite("zelda/player.staff_water", 1.5f, 1.5f, this,
            new RegionOfInterest(0, 64, 32, 32), new Vector(.15f, -.15f));
    Sprite spriteWater_left = new Sprite("zelda/player.staff_water", 1.5f, 1.5f, this,
            new RegionOfInterest(0, 96, 32, 32), new Vector(.15f, -.15f));

    //PLayer with the bow
    Sprite spriteBow_bottom = new Sprite("zelda/player.bow", 1.5f, 1.5f, this,
            new RegionOfInterest(0, 0, 32, 32), new Vector(.15f, -.15f));
    Sprite spriteBow_right = new Sprite("zelda/player.bow", 1.5f, 1.5f, this,
            new RegionOfInterest(0, 32, 32, 32), new Vector(.15f, -.15f));
    Sprite spriteBow_top = new Sprite("zelda/player.bow", 1.5f, 1.5f, this,
            new RegionOfInterest(0, 64, 32, 32), new Vector(.15f, -.15f));
    Sprite spriteBow_left = new Sprite("zelda/player.bow", 1.5f, 1.5f, this,
            new RegionOfInterest(0, 96, 32, 32), new Vector(.15f, -.15f));


    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */

    public ICRoguePlayer(Area area, Orientation orientation, DiscreteCoordinates position) {
        this(area, orientation, position, 100);
    }

    public ICRoguePlayer(Area area, Orientation orientation, DiscreteCoordinates position, int energylevel) {
        super(area, orientation, position);
        keyboard = getOwnerArea().getKeyboard();
        handler = new ICRoguePlayerInteractionHandler();
        this.energylevel = energylevel;
        isDead = false;
        passedConnector = null;
        isPassingConnector = false;
        weapons = new ArrayList<Item>();
        hasSpeedFire = false;
        nbOfArrows = 20;
        savedFriend = false;
    }
    public boolean getIsDead() {
        return isDead;
    }

    public int getEnergylevel() {
        return energylevel;
    }

    @Override
    public void update(float deltaTime) {

        super.update(deltaTime);

        timeCounter += dt;

        moveIfPressed(Orientation.LEFT, keyboard.get(Keyboard.LEFT));
        moveIfPressed(Orientation.UP, keyboard.get(Keyboard.UP));
        moveIfPressed(Orientation.RIGHT, keyboard.get(Keyboard.RIGHT));
        moveIfPressed(Orientation.DOWN, keyboard.get(Keyboard.DOWN));

        if (keyboard.get(Keyboard.X).isPressed() && (hasStaff || hasBow)) {

            if (weaponCount % 2 == 0 && timeCounter >= COOLDOWN) {
                FireBall fireball = new FireBall(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates());
                getOwnerArea().registerActor(fireball);
                fireball.setFiredByPlayer(true);
                timeCounter = 0;

            } else {

                if(nbOfArrows > 0 && hasSpeedFire && timeCounter >= SPEED_FIRE_COOLDOWN) {
                    Arrow arrow = new Arrow(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates());
                    getOwnerArea().registerActor(arrow);
                    arrow.setFiredByPlayer(true);
                    --nbOfArrows;
                    timeCounter = 0;
                }

                else if (nbOfArrows > 0 && timeCounter >= COOLDOWN) {
                    Arrow arrow = new Arrow(getOwnerArea(), getOrientation(), getCurrentMainCellCoordinates());
                    getOwnerArea().registerActor(arrow);
                    arrow.setFiredByPlayer(true);
                    timeCounter = 0;
                    --nbOfArrows;

                } else {
                    System.out.println("OUT OF ARROWS");
                }
            }
        }
        if (!keyboard.get(Keyboard.W).isDown()) {
            wantsInteraction = false;
        }
        if (keyboard.get(Keyboard.W).isDown()) {
            wantsInteraction = true;
        }
        if (isDead) {
            this.getOwnerArea().unregisterActor(this);
        }
        if (keyboard.get(Keyboard.S).isPressed() && hasStaff && hasBow) {
            weaponCount++;
        }
    }
    public void leaveArea() {
        oldArea = getOwnerArea();
        getOwnerArea().unregisterActor(this);
    }
    public boolean isPassingConnector() {
        return isPassingConnector;
    }

    public void setPassingConnector(boolean bol) {
        this.isPassingConnector = bol;
    }

    public Connector getPassedConnector() {
        return passedConnector;
    }

    private void moveIfPressed(Orientation orientation, Button b) {
        if (b.isDown()) {
            if (!isDisplacementOccurs()) {
                orientate(orientation);
                move(MOVE_DURATION);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (!hasStaff && !hasBow) {
            switch (getOrientation()) {
                case UP -> sprite_top.draw(canvas);
                case DOWN -> sprite_bottom.draw(canvas);
                case RIGHT -> sprite_right.draw(canvas);
                case LEFT -> sprite_left.draw(canvas);
            }
        }if (hasStaff && (weaponCount % weapons.size() == 0)) {
            switch (getOrientation()) {
                case UP -> spriteWater_right.draw(canvas);
                case DOWN -> spriteWater_bottom.draw(canvas);
                case RIGHT -> spriteWater_top.draw(canvas);
                case LEFT -> spriteWater_left.draw(canvas);
            }
        }  if (hasBow && (weaponCount % weapons.size() == 1)) {
            switch (getOrientation()) {
                case UP -> spriteBow_right.draw(canvas);
                case DOWN -> spriteBow_bottom.draw(canvas);
                case RIGHT -> spriteBow_top.draw(canvas);
                case LEFT -> spriteBow_left.draw(canvas);
            }
        }
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
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }

    //Implements Interactor
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates().jump(getOrientation().toVector()));
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return wantsInteraction;
    }
    public void takeDamage(int dmg) {
        energylevel -= dmg;
        if (energylevel <= 0) {
            isDead = true;
        }
    }

    public void addEnergy(int energy) {
        if (energylevel + energy > MAX_ENERGY) {
            energylevel = MAX_ENERGY;
            System.out.println("player HP: " + getEnergylevel());
        } else {
            energylevel += energy;
            System.out.println("player HP: " + getEnergylevel());
        }
    }

    private class ICRoguePlayerInteractionHandler implements ICRogueInteractionHandler {
        @Override
        public void interactWith(Cherry cherry, boolean isCellInteraction) {
            if (cherry.isCellInteractable()) {
                getOwnerArea().unregisterActor(cherry);
                addEnergy(cherry.INCREASE_ENERGY);
                System.out.println("Speed fire unlocked for Bow");
                hasSpeedFire = true;
            }
        }

        @Override
        public void interactWith(Staff staff, boolean isCellInteraction) {
            if (staff.isViewInteractable()) {
                getOwnerArea().unregisterActor(staff);
                hasStaff = true;
                weapons.add(staff);
            }
        }

        @Override
        public void interactWith(Bow bow, boolean isCellInteraction) {
            if (bow.isViewInteractable()) {
                getOwnerArea().unregisterActor(bow);
                hasBow = true;
                weapons.add(bow);
                nbOfArrows = 50;
            }
        }

        @Override
        public void interactWith(Key key, boolean isCellInteraction) {
            if (key.isCellInteractable()) {
                getOwnerArea().unregisterActor(key);
                hasKey = true;
                //keyIdentifiers.add(key.getIdentifier());
            }
        }

        public void interactWith(Connector connector, boolean isCellInteraction) {
            if (!(connector.getState().equals(Connector.ConnectorState.OPEN)) && hasKey) {
                connector.setState(Connector.ConnectorState.OPEN);

            } else if (connector.getState().equals(Connector.ConnectorState.OPEN) && !isDisplacementOccurs() && wantsCellInteraction()) {
                passedConnector = connector;
                isPassingConnector = true;
                connector.setState(Connector.ConnectorState.CLOSED);

            }
            if (connector.getState().equals(Connector.ConnectorState.CLOSED) && wantsInteraction) {
                connector.setState(Connector.ConnectorState.OPEN);
            }
        }

        @Override
        public void interactWith(Arrow arrow, boolean isCellInteraction) {
            if (arrow.isCellInteractable() && !arrow.getFiredByPlayer()) {
                takeDamage(Arrow.ARROW_DMG);
                getOwnerArea().unregisterActor(arrow);

            }
        }

        @Override
        public void interactWith(FireBall fireBall, boolean isCellInteraction) {
            if (fireBall.isCellInteractable() && !fireBall.getFiredByPlayer()) {
                takeDamage(FireBall.FIREBALL_DMG);
                getOwnerArea().unregisterActor(fireBall);

            }
        }
        @Override
        public void interactWith(Turret turret, boolean isCellInteraction) {
            if (turret.isCellInteractable()) {
                turret.setDead();
            }
        }
    }
}

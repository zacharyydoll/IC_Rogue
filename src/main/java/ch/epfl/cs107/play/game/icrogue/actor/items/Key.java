package ch.epfl.cs107.play.game.icrogue.actor.items;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Key extends Item {
    private int identifier;
    private KeyInteractionHandler handler;

    public Key(Area area, Orientation orientation, DiscreteCoordinates position, int identifier) {
        super(area, orientation, position);

        setSprite(new Sprite("icrogue/key", 0.8f, 0.8f, this));
        this.identifier = identifier;
    }
    public int getIdentifier(){
        return identifier;
    }

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
    public void acceptInteraction(AreaInteractionVisitor v, boolean isCellInteraction) {
        ((ICRogueInteractionHandler) v).interactWith(this, isCellInteraction);
    }

    @Override
    public void interactWith(Interactable other, boolean isCellInteraction) {
        other.acceptInteraction(handler, isCellInteraction);
    }
    //destination room key
    //key arrival coordinates

    private class KeyInteractionHandler extends ItemHandler {}
}
package ch.epfl.cs107.play.game.icrogue;

import ch.epfl.cs107.play.game.actor.Entity;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.icrogue.actor.projectiles.FireBall;
import ch.epfl.cs107.play.game.icrogue.handler.ICRogueInteractionHandler;
import ch.epfl.cs107.play.game.tutosSolution.Tuto2Behavior;
import ch.epfl.cs107.play.window.Window;

public class ICRogueBehavior extends AreaBehavior {
    /**
     * Default AreaBehavior Constructor
     *
     * @param window (Window): graphic context, not null
     * @param name   (String): name of the behavior image, not null
     */
    public ICRogueBehavior(Window window, String name) {
        super(window, name);

        int height = getHeight();
        int width = getWidth();

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width ; x++) {
                ICRogueCellType color = ICRogueCellType.toType(getRGB(height-1-y, x));
                setCell(x,y, new ICRogueCell(x,y,color));
            }
        }
    }

    public enum ICRogueCellType {

        NONE(0, false),
        GROUND(-16777216, true),
        WALL(-14112955, false),
        HOLE(-65536, true);
        final int type;
        final boolean isWalkable;

        ICRogueCellType(int type, boolean isWalkable) {
            this.type = type;
            this.isWalkable = isWalkable;
        }

        public static ICRogueCellType toType(int type){
            /*for(ICRogueCellType ict : ICRogueCellType.values()){
                if(ict.type == type)
                    return ict;
            }
            // When you add a new color, you can print the int value here before assign it to a type
            System.out.println(type);
            return NONE;*/
            return switch (type) {
                case -16777216 -> GROUND;
                case -14112955 -> WALL;
                case -65536 -> HOLE;
                default -> NONE;
            };
        }
    }
    public class ICRogueCell extends Cell { //extends Cell

        private ICRogueCellType type;

        public ICRogueCell(int x, int y, ICRogueCellType ttype) {
            super(x,y);
            this.type = ttype;

        }

        @Override
        public boolean takeCellSpace() {
            return false;
        }


        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }
        @Override
        protected boolean canEnter(Interactable entity) {
            if(!type.isWalkable)
                return false;

            if(!entity.takeCellSpace()) {
                return true;
            }

            for (Interactable element : entities) {
                if (element.takeCellSpace()) {
                    return false;
                }
            }
            return true;
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
        public ICRogueCellType getICRogueCellType(){
            return type;
        }
    }
}




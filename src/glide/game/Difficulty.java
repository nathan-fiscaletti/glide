package glide.game;

import java.awt.Color;

public enum Difficulty {
    Easy, Normal, Hard, Expert;

    public static Color getDifficultyColor(Difficulty difficulty){
        switch(difficulty){
            case Easy:
                return Color.GREEN;
            case Expert:
                return Color.RED;
            case Hard:
                return Color.ORANGE;
            case Normal:
                return Color.BLUE;
            default:
                return Color.WHITE;
        }
    }
}

package net.bote.gamecore.api.game;

public @interface GameInfo {

    String name();

    String description() default "";

    String version() default "";

    String[] authors() default "";

}

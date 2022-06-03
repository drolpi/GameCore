package net.bote.gamecore.api.condition;

public @interface VictoryConditionInfo {

    String name();

    String description() default "";

    String version() default "";

    String[] authors() default "";

}

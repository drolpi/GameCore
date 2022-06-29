package net.bote.gamecore.api.phase;

public @interface PhaseInfo {

    String name();

    String description() default "";

    String version() default "";

    String[] authors() default "";

}

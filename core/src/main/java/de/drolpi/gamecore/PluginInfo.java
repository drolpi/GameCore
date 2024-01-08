package de.drolpi.gamecore;

public @interface PluginInfo {

    String name();

    String description() default "";

    String version() default "";

    String[] authors() default "";

}
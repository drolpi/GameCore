package net.bote.gamecore.api;

public @interface PluginInfo {

    String name();

    String description() default "";

    String version() default "";

    String[] authors() default "";

}

package net.bote.gamecore.api.feature;

public @interface FeatureInfo {

    String name();

    String description() default "";

    String version() default "";

    String[] authors() default "";

}

package net.bote.gamecore.api.game;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface GameInfo {

    String name();

    String description() default "";

    String version() default "";

    String[] authors() default "";

}

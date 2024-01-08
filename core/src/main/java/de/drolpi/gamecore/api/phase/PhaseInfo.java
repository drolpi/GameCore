package de.drolpi.gamecore.api.phase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PhaseInfo {

    String name();

    String key();

    String description() default "";

    String version() default "";

    String[] authors() default "";

}

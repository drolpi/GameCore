package net.bote.gamecore.api;

import com.google.gson.annotations.Expose;

public abstract class AbstractIdentifiable {

    @Expose
    private final String type = this.getClass().getName();

}

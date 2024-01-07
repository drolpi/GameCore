package de.drolpi.gamecore.internal.config;

import com.google.gson.annotations.Expose;

public class GlobalConfig {

    @Expose
    private String defaultGame = "none";

    public GlobalConfig() {

    }

    public String defaultGame() {
        return this.defaultGame;
    }
}

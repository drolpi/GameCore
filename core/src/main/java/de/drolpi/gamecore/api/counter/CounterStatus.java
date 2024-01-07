package de.drolpi.gamecore.api.counter;

/**
 * Represents the different statuses for a {@link Counter}.
 */
public enum CounterStatus {

    /**
     * The singleton instance for the status while the counter is not running.
     */
    IDLING,
    /**
     * The singleton instance for the status while the counter is running and performing actions.
     */
    RUNNING,
    /**
     * The singleton instance for the status while the counter is paused and not performing any actions.
     */
    PAUSED
}

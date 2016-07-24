package com.capitriumgames.controllers.input;

import net.java.games.input.Component;

/**
 * Used as an input event template for comparisons against real input events when configuring
 * a controller device at runtime.
 * @author Capitrium
 */
public class InputEventConfiguration {
    /**
     * The {@link Component.Identifier} to bind an action to in the current {@link InputContext}.
     */
    public Class<? extends Component.Identifier> inputComponentIdentifierType;

    /**
     * The user-defined name used to identify input events that match this configuration.
     */
    public String inputEventName;

    /**
     * The absolute value to compare against the value of the input event.
     *
     * For digital input devices, this will be either 0 or 1 and is used to determine
     * the target state of matching input events during controller configuration.
     *
     * For analog input devices, the value will be within the range [0, 1] and is used
     * as a dead-zone value to prevent false positives when configuring analog controller inputs.
     */
    public float inputEventAbsValue;

    /**
     * Constructs a new InputEventConfiguration that compares the given parameters against their respective
     * counterparts in {@link net.java.games.input.Event} and uses the given name to store matching events
     * in an {@link InputContext} during controller configuration.
     * @param inputComponentIdentifierType The class of the target {@link Component.Identifier}
     * @param inputEventName The name to store matching events under in an {@link InputContext}
     * @param inputEventAbsValue The target state of matching digital events, or a dead-zone on matching analog events
     */
    public InputEventConfiguration(Class<? extends Component.Identifier> inputComponentIdentifierType,
                                   String inputEventName,
                                   float inputEventAbsValue) {
        this.inputComponentIdentifierType = inputComponentIdentifierType;
        this.inputEventName = inputEventName;
        this.inputEventAbsValue = inputEventAbsValue;
    }
}

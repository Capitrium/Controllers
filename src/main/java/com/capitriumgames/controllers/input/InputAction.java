package com.capitriumgames.controllers.input;

import net.java.games.input.Event;

/**
 * @author Capitrium
 */
public interface InputAction<T> {
    void execute(T source, Event inputEvent);
}

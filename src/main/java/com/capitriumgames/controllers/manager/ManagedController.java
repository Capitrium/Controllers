package com.capitriumgames.controllers.manager;

import com.badlogic.gdx.utils.Array;
import com.capitriumgames.controllers.input.InputContext;
import com.capitriumgames.controllers.input.InputEventConfiguration;
import com.capitriumgames.controllers.input.InputHandler;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 * @author Capitrium
 */
public class ManagedController {

    private int id;
    private boolean controllerConnected = true;

    private InputHandler<?> inputHandler;

    private Controller controller;
    private final Event queuedEvent = new Event();

    public ManagedController(int id, Controller controller) {
        this.id = id;
        this.controller = controller;
    }

    public int getControllerId() {
        return id;
    }

    public InputHandler<?> getInputHandler() {
        return inputHandler;
    }

    public <T> void setInputHandler(InputHandler<T> handler) {
        this.inputHandler = handler;
    }

    public void bindInputs(InputContext context, Array<InputEventConfiguration> inputEventListeners) {
        controllerConnected = controller.poll();
        if (!controllerConnected) {
            return;
        }

        while(controller.getEventQueue().getNextEvent(queuedEvent)) {
            if (inputEventListeners.size <= 0) break;
            for (int i = 0; i < inputEventListeners.size; i++) {
                InputEventConfiguration eventListener = inputEventListeners.get(i);
                if (eventListener.inputComponentIdentifierType.equals(Component.Identifier.Axis.class)
                        && queuedEvent.getComponent().getIdentifier() instanceof Component.Identifier.Axis) {
                    // eventListener.inputEventDeadZone used as a dead-zone check against the analog event value
                    if (Math.abs(queuedEvent.getValue()) > eventListener.inputEventAbsValue) {
                        context.addInputBinding(
                                eventListener.inputEventName, queuedEvent.getComponent().getIdentifier().getName()
                        );
                        inputEventListeners.removeIndex(i);
                    }
                } else if (eventListener.inputComponentIdentifierType.equals(Component.Identifier.Button.class)) {
                    // eventListener.inputEventDeadZone used as the digital event value
                    if (queuedEvent.getValue() == eventListener.inputEventAbsValue) {
                        context.addInputBinding(
                                eventListener.inputEventName, queuedEvent.getComponent().getIdentifier().getName()
                        );
                        inputEventListeners.removeIndex(i);
                    }
                }
            }
        }
    }

    public boolean poll() {
        controllerConnected = controller.poll();
        if (!controllerConnected) {
            return false;
        }
        try {
            handleEventQueue(controller.getEventQueue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return controllerConnected;
    }

    private void handleEventQueue(EventQueue eventQueue) {
        while(eventQueue.getNextEvent(queuedEvent)) {
            if (inputHandler != null) {
                inputHandler.handleInputEvent(queuedEvent);
            }
        }
    }

    protected Controller getController() {
        return this.controller;
    }

}

package com.capitriumgames.controllers.input;

import com.badlogic.gdx.utils.TimeUtils;
import com.capitriumgames.controllers.manager.ControllerManager;
import com.capitriumgames.controllers.manager.ManagedController;

import net.java.games.input.Event;

/**
 * @author Capitrium
 */
public class InputHandler<T> {

    private InputContext<T> activeContext;
    private InputAction<T> handledAction;
    private String handledInputBinding;

    private final Event manualEvent = new Event();

    public InputHandler() {

    }

    public InputHandler(InputContext<T> context) {
        this.activeContext = context;
    }

    /**
     * Sets the active {@link InputContext} for this InputHandler.
     * @param context the InputContext to set.
     */
    public void changeInputContext(InputContext<T> context) {
        activeContext = context;
    }

    /**
     * Manually executes the {@link InputAction} bound to the given inputBinding, passing in an optional
     * {@link Event} value to {@link InputAction#execute}.
     * @param inputBinding the binding for the desired {@link InputAction}
     * @param inputValue the value of the input event used when executing the action.
     * @return true if the action was executed, otherwise false.
     */
    public boolean executeInputAction(String inputBinding, float inputValue) {
        if (activeContext != null) {
            handledAction = activeContext.getInputAction(inputBinding);
            if (handledAction != null) {
                manualEvent.set(null, inputValue, TimeUtils.nanoTime());
                handledAction.execute(activeContext.inputTarget, manualEvent);
                return true;
            }
        }
        return false;
    }

    /**
     * Manually executes the {@link InputAction} bound to the given boundInput, passing in an optional
     * {@link Event} value to {@link InputAction#execute}.
     * @param boundInputComponentName the name of the component identifier bound to the desired {@link InputAction}.
     * @param inputValue the value of the input event used when executing the action.
     * @return true if the action was executed, otherwise false.
     */
    public boolean handleInputAction(String boundInputComponentName, float inputValue) {
        if (activeContext != null) {
            handledInputBinding = activeContext.getInputBinding(boundInputComponentName);
            if (handledInputBinding != null) {
                handledAction = activeContext.getInputAction(activeContext.getInputBinding(boundInputComponentName));
                if (handledAction != null) {
                    manualEvent.set(null, inputValue, TimeUtils.nanoTime());
                    handledAction.execute(activeContext.inputTarget, manualEvent);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Called by {@link ManagedController} instances when they are polled by the {@link ControllerManager}.
     * @param inputEvent an event received from a managed controller during {@link ControllerManager#poll}.
     * @return true if an action associated with the inputEvent was executed, otherwise false.
     */
    public boolean handleInputEvent(Event inputEvent) {
        if (activeContext != null) {
            handledInputBinding = activeContext.getInputBinding(inputEvent.getComponent().getIdentifier().getName());
            if (handledInputBinding != null) {
                handledAction = activeContext.getInputAction(handledInputBinding);
                if (handledAction != null) {
                    handledAction.execute(activeContext.inputTarget, inputEvent);
                    return true;
                }
            }
        }
        return false;
    }

}

package com.capitriumgames.input;

import com.badlogic.gdx.utils.ObjectMap;

import net.java.games.input.Component;

/**
 * @author Capitrium
 */
public class InputContext<T> {

    /**
     * The game object to be manipulated by the bound input actions in this context.
     */
    protected T inputTarget;

    /**
     * A map of {@link Component.Identifier} instances to user-defined action constants.
     */
    protected ObjectMap<Component.Identifier, String> inputMap =
            new ObjectMap<Component.Identifier, String>();

    /**
     * A map of {@link Component.Identifier} instances to user-defined {@link InputAction} objects.
     */
    protected ObjectMap<Component.Identifier, InputAction<T>> actionMap =
            new ObjectMap<Component.Identifier, InputAction<T>>();

    /**
     * Sets the object to be manipulated by the bound input actions in this context.
     * @param target the object to be manipulated.
     */
    public void setInputTarget(T target) {
        inputTarget = target;
    }

    /**
     * Binds the given {@link Component.Identifier} to a user-defined integer constant.
     * @param inputComponent The component identifier to bind.
     * @param inputBinding The constant to which the component identifier will be bound.
     */
    public void addInputBinding(Component.Identifier inputComponent, String inputBinding) {
        inputMap.put(inputComponent, inputBinding);
    }

    /**
     * Returns the user-defined input constant associated with the given {@link Component.Identifier}.
     * @param inputComponent The {@link Component.Identifier} to be translated to a user-defined constant.
     * @return The user-defined constant for the given {@link Component.Identifier}.
     */
    public String getInputBinding(Component.Identifier inputComponent) {
        return inputMap.get(inputComponent, null);
    }

    /**
     * Attaches the given {@link InputAction} to the {@link Component.Identifier} currently bound to the
     * given inputBinding.
     * @param inputBinding The constant for the desired {@link Component.Identifier}.
     * @param inputAction The {@link InputAction} be bound to the {@link Component.Identifier}.
     */
    public void bindInputAction(String inputBinding, InputAction<T> inputAction) {
        if (inputMap.findKey(inputBinding, false) != null) {
            actionMap.put(inputMap.findKey(inputBinding, false), inputAction);
        }
    }

    /**
     * Creates a new input binding for the given {@link Component.Identifier} and attaches the given {@link InputAction}
     * to the new input binding.
     * @param inputComponent The {@link Component.Identifier} to bind the input to.
     * @param inputBinding The name for the input binding.
     * @param inputAction The {@link InputAction} to be bound to the {@link Component.Identifier}.
     */
    public void bindInputAction(Component.Identifier inputComponent, String inputBinding, InputAction<T> inputAction) {
        inputMap.put(inputComponent, inputBinding);
        actionMap.put(inputComponent, inputAction);
    }

    /**
     * Returns the {@link InputAction} associated with the given inputBinding value.
     * @param inputBinding A user-defined constant that represents a distinct input event.
     * @return The {@link InputAction} associated with the inputBinding value.
     */
    public InputAction<T> getInputAction(String inputBinding) {
        if (inputMap.containsValue(inputBinding, false)) {
            return actionMap.get(inputMap.findKey(inputBinding, false));
        }
        return null;
    }
}

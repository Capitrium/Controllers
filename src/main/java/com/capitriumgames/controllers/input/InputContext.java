package com.capitriumgames.controllers.input;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;

/**
 * @author Capitrium
 */
public class InputContext<T> implements Serializable {

    /**
     * The game object to be manipulated by the bound input actions in this context.
     */
    protected T inputTarget;

    /**
     * A map of user-defined action constants to {@link Component.Identifier} instances.
     */
    protected ObjectMap<String, Component.Identifier> inputMap =
            new ObjectMap<String, Component.Identifier>();

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
     * @param inputBinding The constant to which the component identifier will be bound.
     * @param inputComponent The component identifier to bind.
     */
    public void addInputBinding(String inputBinding, Identifier inputComponent) {
        inputMap.put(inputBinding, inputComponent);
    }

    /**
     * Returns the user-defined input constant associated with the given {@link Component.Identifier}.
     * @param inputComponent The {@link Component.Identifier} to be translated to a user-defined constant.
     * @return The user-defined constant for the given {@link Component.Identifier}.
     */
    public String getInputBinding(Component.Identifier inputComponent) {
        return inputMap.findKey(inputComponent, false);
    }

    /**
     * Attaches the given {@link InputAction} to the {@link Component.Identifier} currently bound to the
     * given inputBinding.
     * @param inputBinding The constant for the desired {@link Component.Identifier}.
     * @param inputAction The {@link InputAction} be bound to the {@link Component.Identifier}.
     */
    public void bindInputAction(String inputBinding, InputAction<T> inputAction) {
        if (inputMap.findKey(inputBinding, false) != null) {
            actionMap.put(inputMap.get(inputBinding), inputAction);
        }
    }

    /**
     * Creates a new input binding for the given {@link Component.Identifier} and attaches the given {@link InputAction}
     * to the new input binding.
     * @param inputBinding The name for the input binding.
     * @param inputComponent The {@link Identifier} to bind the input to.
     * @param inputAction The {@link InputAction} to be bound to the {@link Identifier}.
     */
    public void bindInputAction(String inputBinding, Identifier inputComponent, InputAction<T> inputAction) {
        inputMap.put(inputBinding, inputComponent);
        actionMap.put(inputComponent, inputAction);
    }

    /**
     * Returns the {@link InputAction} associated with the given inputBinding value.
     * @param inputBinding A user-defined constant that represents a distinct input event.
     * @return The {@link InputAction} associated with the inputBinding value.
     */
    public InputAction<T> getInputAction(String inputBinding) {
        return actionMap.get(inputMap.get(inputBinding), null);
    }

    @Override
    public void write(Json json) {
        json.writeValue("inputMap", inputMap);
        json.writeValue("actionMap", actionMap);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void read(Json json, JsonValue jsonData) {
        inputMap = json.readValue(ObjectMap.class, Component.Identifier.class, jsonData.get("inputMap"));
        actionMap = json.readValue(ObjectMap.class, InputAction.class, jsonData.get("actionMap"));
    }
}

package com.capitriumgames.controllers.input;

import com.badlogic.gdx.Input;
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
     * A map of user-defined action constants to input component identifiers. An input component can be identified
     * by interfaces that provide a name for each identifier in the interface, such as {@link Identifier#getName()}
     * or {@link Input.Keys#toString(int)}.
     */
    protected ObjectMap<String, String> inputMap =
            new ObjectMap<String, String>();

    /**
     * A map of input component identifier names to user-defined {@link InputAction} objects.
     */
    protected ObjectMap<String, InputAction<T>> actionMap =
            new ObjectMap<String, InputAction<T>>();

    /**
     * Sets the object to be manipulated by the bound input actions in this context.
     * @param target the object to be manipulated.
     */
    public void setInputTarget(T target) {
        inputTarget = target;
    }

    /**
     * Binds the given named input component to a user-defined integer constant.
     * If an {@link InputAction} was bound to the old component, it will be re-bound to the new component.
     * @param inputBinding The constant to which the component identifier will be bound.
     * @param inputComponentName The name of the component identifier to bind.
     */
    public void addInputBinding(String inputBinding, String inputComponentName) {
        String oldComponentName = inputMap.put(inputBinding, inputComponentName);
        if (oldComponentName != null) {
            actionMap.put(inputComponentName, actionMap.get(oldComponentName));
            actionMap.remove(oldComponentName);
        }
    }

    /**
     * Returns the user-defined input constant associated with the given inputComponentName.
     * @param inputComponentName The name of the input component identifier to be translated to a user-defined constant.
     * @return The user-defined constant for the given inputComponentName.
     */
    public String getInputBinding(String inputComponentName) {
        return inputMap.findKey(inputComponentName, false);
    }

    /**
     * Attaches the given {@link InputAction} to the {@link Component.Identifier#name} currently bound to the
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
     * Creates a new input binding for the given inputComponentName
     * and attaches the given {@link InputAction} to the new input binding.
     * @param inputBinding The name for the input binding.
     * @param inputComponentName The name of the input component identifier to bind the input to.
     * @param inputAction The {@link InputAction} to be bound to the inputComponentName.
     */
    public void bindInputAction(String inputBinding, String inputComponentName, InputAction<T> inputAction) {
        inputMap.put(inputBinding, inputComponentName);
        actionMap.put(inputComponentName, inputAction);
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
        inputMap = json.readValue(ObjectMap.class, String.class, jsonData.get("inputMap"));
        actionMap = json.readValue(ObjectMap.class, InputAction.class, jsonData.get("actionMap"));
    }
}

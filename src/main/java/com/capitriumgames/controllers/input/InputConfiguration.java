package com.capitriumgames.controllers.input;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

/**
 * @author Capitrium
 */
@SuppressWarnings("unchecked")
public class InputConfiguration implements Serializable {

    public ObjectMap<String, InputContext> configurationContexts;

    public InputConfiguration() {

    }

    @Override
    public void write(Json json) {
        for (Entry<String, InputContext> entry : configurationContexts) {
            json.writeValue(entry.key, entry.value);
        }
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        configurationContexts = json.readValue(ObjectMap.class, InputContext.class, jsonData);
    }
}

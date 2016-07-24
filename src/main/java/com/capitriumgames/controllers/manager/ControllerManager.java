package com.capitriumgames.controllers.manager;

import com.badlogic.gdx.jnigen.SharedLibraryFinder;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.capitriumgames.controllers.input.InputHandler;
import com.capitriumgames.controllers.utils.JniWrapSharedLibraryLoader;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.util.zip.ZipFile;

/**
 * @author Capitrium
 */
public class ControllerManager {

    private boolean loadedNatives = false;
    private boolean refreshRequired = false;

    private static ControllerManager instance;

    private ControllerEnvironment controllerEnvironment;

    private final Array<ManagedController> managedControllers = new Array<ManagedController>();

    protected ControllerManager() {
        loadLibraries();
        if (!loadedNatives) {
            throw new GdxRuntimeException("Failed to load jinput native libraries!");
        }
        loadControllers();
    }

    public static ControllerManager getInstance() {
        if (instance == null) {
            instance = new ControllerManager();
        }
        return instance;
    }

    public ManagedController getController(int controllerIndex) {
        return managedControllers.get(controllerIndex);
    }

    public int getNumControllers() { return managedControllers.size; }

    public boolean poll() {
        for (ManagedController m : managedControllers) {
            if (!m.poll()) {
                refreshRequired = true;
            }
        }
        if (refreshRequired) {
            reloadControllers();
            refreshRequired = false;
            return false;
        }
        return true;
    }

    public void reloadControllers() {
        // Assumes reconnected controllers are at the end of the newControllers array and attempts to
        // assign them to the proper ManagedController instance
        controllerEnvironment = ControllerEnvironment.refreshDefaultEnvironment();
        Controller[] newControllers = controllerEnvironment.getControllers();
        Array<Controller> tmpControllers = new Array<Controller>(true, newControllers.length, Controller.class);
        for (Controller c : newControllers) {
            if (c.getType() != Controller.Type.KEYBOARD && c.getType() != Controller.Type.MOUSE) {
                tmpControllers.add(c);
            }
        }
        newControllers = tmpControllers.toArray();

        // return if any controllers are still disconnected
        for (int i = 0; i < newControllers.length; i++) {
            if (!newControllers[i].poll()) return;
        }

        IntArray keepIndices = new IntArray();
        IntArray replaceIndices = new IntArray();

        for (int i = 0; i < managedControllers.size; i++) {
            if (managedControllers.get(i).getController().poll()) {
                keepIndices.add(i);
            } else {
                replaceIndices.add(i);
            }
        }

        IntArray mergedIndices = new IntArray();
        mergedIndices.addAll(keepIndices);
        mergedIndices.addAll(replaceIndices);

        for (int i = 0; i < newControllers.length; i++) {
            if (i < managedControllers.size) {
                InputHandler<?> controllerInputHandler = managedControllers.get(i).getInputHandler();
                managedControllers.set(i, new ManagedController(i, newControllers[mergedIndices.get(i)]));
                managedControllers.get(i).setInputHandler(controllerInputHandler);
            } else {
                managedControllers.add(new ManagedController(i, newControllers[i]));
            }
        }
    }

    private void loadControllers() {
        controllerEnvironment = ControllerEnvironment.refreshDefaultEnvironment();
        Controller[] newControllers = controllerEnvironment.getControllers();

        int index = 0;
        for (int i = 0; i < newControllers.length; i++) {
            if (newControllers[i].getType() != Controller.Type.KEYBOARD && newControllers[i].getType() != Controller.Type.MOUSE) {
                managedControllers.add(new ManagedController(index, newControllers[i]));
                index++;
            }
        }
    }

    private void loadLibraries() {
        if (loadedNatives) {
            return;
        }

        JniWrapSharedLibraryLoader sharedLibraryLoader = new JniWrapSharedLibraryLoader();
        sharedLibraryLoader.setSharedLibraryFinder(new SharedLibraryFinder() {
            @Override
            public String getSharedLibraryNameWindows(String sharedLibName, boolean is64Bit, ZipFile nativesJar) {
                if (is64Bit) {
                    return sharedLibName + "_64.dll";
                } else {
                    return sharedLibName + ".dll";
                }
            }

            @Override
            public String getSharedLibraryNameLinux(String sharedLibName, boolean is64Bit, boolean isArm,
                                                    ZipFile nativesJar) {
                return "lib" + sharedLibName + (is64Bit ? "64" : "") + ".so";
            }

            @Override
            public String getSharedLibraryNameMac(String sharedLibName, boolean is64Bit, ZipFile nativesJar) {
                return "lib" + sharedLibName + ".jnilib";
            }

            @Override
            public String getSharedLibraryNameAndroid(String sharedLibName, ZipFile nativesJar) {
                return "";
            }
        });
        sharedLibraryLoader.load("jinput-dx8");
        sharedLibraryLoader.load("jinput-raw");
        sharedLibraryLoader.load("jinput-linux");
        sharedLibraryLoader.load("jinput-osx");
        System.setProperty("net.java.games.input.librarypath", sharedLibraryLoader.getLoadedLibraryPaths());
        loadedNatives = true;
    }

}

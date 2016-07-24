import com.badlogic.gdx.utils.Array;
import com.capitriumgames.controllers.manager.ControllerManager;
import com.capitriumgames.controllers.input.InputAction;
import com.capitriumgames.controllers.input.InputContext;
import com.capitriumgames.controllers.input.InputEventConfiguration;
import com.capitriumgames.controllers.input.InputHandler;

import net.java.games.input.Component;
import net.java.games.input.Event;

/**
 * @author Capitrium
 */
public class ConfigurableControllerTest {

    private ControllerManager controllerManager;
    private InputHandler<Object> testInputHandler;
    private ConfigurableInputContext testInputContext;

    public ConfigurableControllerTest() {
        controllerManager = ControllerManager.getInstance();
        testInputContext = new ConfigurableInputContext();
        testInputHandler = new InputHandler<Object>(testInputContext);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean controllerConfigured = false;
                String lastMsg = "";
                // Ensure a controller is connected before trying to configure it
                while (!controllerManager.poll()) {
                    if (controllerManager.getNumControllers() > 0) {
                        testInputContext.setInputTarget(null);
                        controllerManager.getController(0).setInputHandler(testInputHandler);
                    }
                }
                try {
                    while (true) {
                        if (controllerConfigured) {
                            controllerManager.poll();
                            Thread.sleep(17);
                        } else {
                            if (testInputContext.getInputAction(ConfigurableInputContext.BUTTON_A_PRESSED) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.BUTTON_A_RELEASED) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.BUTTON_B_PRESSED) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.BUTTON_B_RELEASED) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.BUTTON_X_PRESSED) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.BUTTON_X_RELEASED) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.BUTTON_Y_PRESSED) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.BUTTON_Y_RELEASED) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.LEFT_AXIS_X_NEGATIVE) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.LEFT_AXIS_X_POSITIVE) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.LEFT_AXIS_Y_NEGATIVE) != null &&
                                    testInputContext.getInputAction(ConfigurableInputContext.LEFT_AXIS_Y_POSITIVE) != null) {
                                lastMsg = "Controller configured!";
                                System.out.println(lastMsg);
                                controllerConfigured = true;
                            } else {
                                if (testInputContext.getInputAction(ConfigurableInputContext.BUTTON_A_PRESSED) == null
                                        || testInputContext.getInputAction(ConfigurableInputContext.BUTTON_A_RELEASED) == null) {
                                    if (!lastMsg.equals("Press the A Button")) {
                                        lastMsg = "Press the A Button";
                                        System.out.println("Press the A Button");
                                    }
                                    Array<InputEventConfiguration> eventsToConfigure = new Array<InputEventConfiguration>();
                                    eventsToConfigure.add(new InputEventConfiguration(Component.Identifier.Button.class, ConfigurableInputContext.BUTTON_A_PRESSED, 1));
                                    eventsToConfigure.add(new InputEventConfiguration(Component.Identifier.Button.class, ConfigurableInputContext.BUTTON_A_RELEASED, 0));
                                    controllerManager.getController(0).bindInputs(testInputContext, eventsToConfigure);
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.BUTTON_A_PRESSED)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.BUTTON_A_PRESSED, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                System.out.println("Button A pressed");
                                            }
                                        });
                                    }
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.BUTTON_A_RELEASED)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.BUTTON_A_RELEASED, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                System.out.println("Button A released");
                                            }
                                        });
                                    }
                                } else if (testInputContext.getInputAction(ConfigurableInputContext.BUTTON_B_PRESSED) == null
                                        || testInputContext.getInputAction(ConfigurableInputContext.BUTTON_B_RELEASED) == null) {
                                    if (!lastMsg.equals("Press the B Button")) {
                                        lastMsg = "Press the B Button";
                                        System.out.println(lastMsg);
                                    }
                                    Array<InputEventConfiguration> eventsToConfigure = new Array<InputEventConfiguration>();
                                    eventsToConfigure.add(new InputEventConfiguration(Component.Identifier.Button.class, ConfigurableInputContext.BUTTON_B_PRESSED, 1));
                                    eventsToConfigure.add(new InputEventConfiguration(Component.Identifier.Button.class, ConfigurableInputContext.BUTTON_B_RELEASED, 0));
                                    controllerManager.getController(0).bindInputs(testInputContext, eventsToConfigure);
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.BUTTON_B_PRESSED)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.BUTTON_B_PRESSED, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                System.out.println("Button B pressed");
                                            }
                                        });
                                    }
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.BUTTON_B_RELEASED)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.BUTTON_B_RELEASED, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                System.out.println("Button B released");
                                            }
                                        });
                                    }
                                } else if (testInputContext.getInputAction(ConfigurableInputContext.BUTTON_X_PRESSED) == null
                                        || testInputContext.getInputAction(ConfigurableInputContext.BUTTON_X_RELEASED) == null) {
                                    if (!lastMsg.equals("Press the X Button")) {
                                        lastMsg = "Press the X Button";
                                        System.out.println(lastMsg);
                                    }
                                    Array<InputEventConfiguration> eventsToConfigure = new Array<InputEventConfiguration>();
                                    eventsToConfigure.add(new InputEventConfiguration(Component.Identifier.Button.class, ConfigurableInputContext.BUTTON_X_PRESSED, 1));
                                    eventsToConfigure.add(new InputEventConfiguration(Component.Identifier.Button.class, ConfigurableInputContext.BUTTON_X_RELEASED, 0));
                                    controllerManager.getController(0).bindInputs(testInputContext, eventsToConfigure);
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.BUTTON_X_PRESSED)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.BUTTON_X_PRESSED, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                System.out.println("Button X pressed");
                                            }
                                        });
                                    }
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.BUTTON_X_RELEASED)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.BUTTON_X_RELEASED, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                System.out.println("Button X released");
                                            }
                                        });
                                    }
                                } else if (testInputContext.getInputAction(ConfigurableInputContext.BUTTON_Y_PRESSED) == null
                                        || testInputContext.getInputAction(ConfigurableInputContext.BUTTON_Y_RELEASED) == null) {
                                    if (!lastMsg.equals("Press the Y Button")) {
                                        lastMsg = "Press the Y Button";
                                        System.out.println(lastMsg);
                                    }
                                    Array<InputEventConfiguration> eventsToConfigure = new Array<InputEventConfiguration>();
                                    eventsToConfigure.add(new InputEventConfiguration(Component.Identifier.Button.class, ConfigurableInputContext.BUTTON_Y_PRESSED, 1));
                                    eventsToConfigure.add(new InputEventConfiguration(Component.Identifier.Button.class, ConfigurableInputContext.BUTTON_Y_RELEASED, 0));
                                    controllerManager.getController(0).bindInputs(testInputContext, eventsToConfigure);
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.BUTTON_Y_PRESSED)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.BUTTON_Y_PRESSED, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                System.out.println("Button Y pressed");
                                            }
                                        });
                                    }
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.BUTTON_Y_RELEASED)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.BUTTON_Y_RELEASED, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                System.out.println("Button Y released");
                                            }
                                        });
                                    }
                                } else if (!testInputContext.containsBoundInput(ConfigurableInputContext.LEFT_AXIS_X_NEGATIVE)) {
                                    if (!lastMsg.equals("Press the Left Control Stick to the left")) {
                                        lastMsg = "Press the Left Control Stick to the left";
                                        System.out.println(lastMsg);
                                    }
                                    Array<InputEventConfiguration> eventsToConfigure = new Array<InputEventConfiguration>();
                                    eventsToConfigure.add(new InputEventConfiguration(
                                            Component.Identifier.Axis.class,
                                            ConfigurableInputContext.LEFT_AXIS_X_NEGATIVE,
                                            0.6f
                                    ));
                                    Thread.sleep(500);
                                    controllerManager.getController(0).bindInputs(testInputContext, eventsToConfigure);
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.LEFT_AXIS_X_NEGATIVE)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.LEFT_AXIS_X_NEGATIVE, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                if (Math.abs(inputEvent.getValue()) > 0.35f) {
                                                    System.out.println("Control Stick pressed left (" + inputEvent.getValue() + ")");
                                                }
                                            }
                                        });
                                        System.out.println("Release the control stick");
                                        Thread.sleep(1500);
                                        controllerManager.getController(0).poll();
                                    } else {
                                        Thread.sleep(500);
                                    }
                                } else if (!testInputContext.containsBoundInput(ConfigurableInputContext.LEFT_AXIS_X_POSITIVE)) {
                                    if (!lastMsg.equals("Press the Left Control Stick to the right")) {
                                        lastMsg = "Press the Left Control Stick to the right";
                                        System.out.println(lastMsg);
                                    }
                                    Array<InputEventConfiguration> eventsToConfigure = new Array<InputEventConfiguration>();
                                    eventsToConfigure.add(new InputEventConfiguration(
                                            Component.Identifier.Axis.class,
                                            ConfigurableInputContext.LEFT_AXIS_X_POSITIVE,
                                            0.6f
                                    ));
                                    Thread.sleep(500);
                                    controllerManager.getController(0).bindInputs(testInputContext, eventsToConfigure);
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.LEFT_AXIS_X_POSITIVE)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.LEFT_AXIS_X_POSITIVE, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                if (Math.abs(inputEvent.getValue()) > 0.35f) {
                                                    System.out.println("Control Stick pressed right (" + inputEvent.getValue() + ")");
                                                }
                                            }
                                        });
                                        System.out.println("Release the control stick");
                                        Thread.sleep(1500);
                                        controllerManager.getController(0).poll();
                                    } else {
                                        Thread.sleep(500);
                                    }
                                } else if (!testInputContext.containsBoundInput(ConfigurableInputContext.LEFT_AXIS_Y_NEGATIVE)) {
                                    if (!lastMsg.equals("Press the Left Control Stick down")) {
                                        lastMsg = "Press the Left Control Stick down";
                                        System.out.println(lastMsg);
                                    }
                                    Array<InputEventConfiguration> eventsToConfigure = new Array<InputEventConfiguration>();
                                    eventsToConfigure.add(new InputEventConfiguration(
                                            Component.Identifier.Axis.class,
                                            ConfigurableInputContext.LEFT_AXIS_Y_NEGATIVE,
                                            0.6f
                                    ));
                                    Thread.sleep(500);
                                    controllerManager.getController(0).bindInputs(testInputContext, eventsToConfigure);
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.LEFT_AXIS_Y_NEGATIVE)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.LEFT_AXIS_Y_NEGATIVE, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                if (Math.abs(inputEvent.getValue()) > 0.35f) {
                                                    System.out.println("Control Stick pressed down (" + inputEvent.getValue() + ")");
                                                }
                                            }
                                        });
                                        System.out.println("Release the control stick");
                                        Thread.sleep(1500);
                                        controllerManager.getController(0).poll();
                                    } else {
                                        Thread.sleep(500);
                                    }
                                } else if (!testInputContext.containsBoundInput(ConfigurableInputContext.LEFT_AXIS_Y_POSITIVE)) {
                                    if (!lastMsg.equals("Press the Left Control Stick up")) {
                                        lastMsg = "Press the Left Control Stick up";
                                        System.out.println(lastMsg);
                                    }
                                    Array<InputEventConfiguration> eventsToConfigure = new Array<InputEventConfiguration>();
                                    eventsToConfigure.add(new InputEventConfiguration(
                                            Component.Identifier.Axis.class,
                                            ConfigurableInputContext.LEFT_AXIS_Y_POSITIVE,
                                            0.6f
                                    ));
                                    Thread.sleep(500);
                                    controllerManager.getController(0).bindInputs(testInputContext, eventsToConfigure);
                                    if (testInputContext.containsBoundInput(ConfigurableInputContext.LEFT_AXIS_Y_POSITIVE)) {
                                        testInputContext.bindInputAction(ConfigurableInputContext.LEFT_AXIS_Y_POSITIVE, new InputAction<Object>() {
                                            @Override
                                            public void execute(Object source, Event inputEvent) {
                                                if (Math.abs(inputEvent.getValue()) > 0.35f) {
                                                    System.out.println("Control Stick pressed up (" + inputEvent.getValue() + ")");
                                                }
                                            }
                                        });
                                        System.out.println("Release the control stick");
                                        Thread.sleep(1500);
                                        controllerManager.getController(0).poll();
                                    } else {
                                        Thread.sleep(500);
                                    }
                                }
                                Thread.sleep(17);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ConfigurableControllerTest();
    }

    private class ConfigurableInputContext extends InputContext<Object> {

        public static final String BUTTON_A_PRESSED = "button-a-pressed";
        public static final String BUTTON_A_RELEASED = "button-a-released";
        public static final String BUTTON_B_PRESSED = "button-b-pressed";
        public static final String BUTTON_B_RELEASED = "button-b-released";
        public static final String BUTTON_X_PRESSED = "button-x-pressed";
        public static final String BUTTON_X_RELEASED = "button-x-released";
        public static final String BUTTON_Y_PRESSED = "button-y-pressed";
        public static final String BUTTON_Y_RELEASED = "button-y-released";

        public static final String LEFT_AXIS_X_NEGATIVE = "left-axis-x-";
        public static final String LEFT_AXIS_X_POSITIVE = "left-axis-x+";
        public static final String LEFT_AXIS_Y_NEGATIVE = "left-axis-y-";
        public static final String LEFT_AXIS_Y_POSITIVE = "left-axis-y+";

        public boolean containsBoundInput(String binding) {
            return inputMap.containsValue(binding, false);
        }

    }

}

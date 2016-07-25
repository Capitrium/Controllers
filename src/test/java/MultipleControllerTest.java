import com.capitriumgames.controllers.manager.ControllerManager;
import com.capitriumgames.controllers.manager.ManagedController;
import com.capitriumgames.controllers.input.InputAction;
import com.capitriumgames.controllers.input.InputContext;
import com.capitriumgames.controllers.input.InputHandler;

import net.java.games.input.Component;
import net.java.games.input.Event;

/**
 * @author Capitrium
 */
public class MultipleControllerTest {
    private ControllerManager controllerManager;
    private InputHandler<ManagedController> controllerOneInputHandler;
    private InputHandler<ManagedController> controllerTwoInputHandler;
    private TestInputContext controllerOneInputContext;
    private TestInputContext controllerTwoInputContext;

    public MultipleControllerTest() {
        controllerManager = ControllerManager.getInstance();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (!controllerManager.poll()) {
                            if (controllerManager.getNumControllers() > 0) {
                                controllerOneInputContext = new TestInputContext(
                                        ControllerManager.getInstance().getController(0));
                                controllerOneInputHandler = new InputHandler<ManagedController>(controllerOneInputContext);
                                controllerManager.getController(0).setInputHandler(controllerOneInputHandler);
                            }
                            if (controllerManager.getNumControllers() > 1) {
                                controllerTwoInputContext = new TestInputContext(
                                        ControllerManager.getInstance().getController(1));
                                controllerTwoInputHandler = new InputHandler<ManagedController>(controllerTwoInputContext);
                                controllerManager.getController(1).setInputHandler(controllerTwoInputHandler);
                            }
                        }
                        Thread.sleep(17);
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
        new MultipleControllerTest();
    }

    private class TestInputContext extends InputContext<ManagedController> {

        public static final String TEST_BUTTON_PRESSED = "test-button-pressed";

        public TestInputContext(ManagedController inputTarget) {
            setInputTarget(inputTarget);
            addInputBinding(TEST_BUTTON_PRESSED, Component.Identifier.Button._0);
            bindInputAction(TEST_BUTTON_PRESSED, new InputAction<ManagedController>() {
                @Override
                public void execute(ManagedController source, Event inputEvent) {
                    if (inputEvent.getValue() == 1) {
                        System.out.println("Detected TEST_BUTTON_PRESSED on controller " + source.getControllerId());
                    } else {
                        System.out.println("Detected TEST_BUTTON_RELEASED on controller " + source.getControllerId());
                    }
                }
            });
        }
    }
}


import com.capitriumgames.controllers.manager.ControllerManager;
import com.capitriumgames.controllers.input.InputAction;
import com.capitriumgames.controllers.input.InputContext;
import com.capitriumgames.controllers.input.InputHandler;

import net.java.games.input.Component;
import net.java.games.input.Event;

/**
 * @author Capitrium
 */
public class SimpleControllerTest {

    private ControllerManager controllerManager;
    private InputHandler<Object> testInputHandler;
    private TestInputContext testInputContext;

    public SimpleControllerTest() {
        controllerManager = ControllerManager.getInstance();
        testInputContext = new TestInputContext();
        testInputHandler = new InputHandler<Object>(testInputContext);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (!controllerManager.poll()) {
                            if (controllerManager.getNumControllers() > 0) {
                                if (controllerManager.getController(0).getInputHandler() == null) {
                                    controllerManager.getController(0).setInputHandler(testInputHandler);
                                }
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
        new SimpleControllerTest();
    }

    private class TestInputContext extends InputContext<Object> {

        public static final String TEST_BUTTON_PRESSED = "test-button-pressed";

        public TestInputContext() {
            addInputBinding(TEST_BUTTON_PRESSED, Component.Identifier.Button._0.getName());
            bindInputAction(TEST_BUTTON_PRESSED, new InputAction<Object>() {
                @Override
                public void execute(Object source, Event inputEvent) {
                    if (inputEvent.getValue() == 1) {
                        System.out.println("Detected TEST_BUTTON_PRESSED");
                    } else {
                        System.out.println("Detected TEST_BUTTON_RELEASED");
                    }
                }
            });
        }
    }

}

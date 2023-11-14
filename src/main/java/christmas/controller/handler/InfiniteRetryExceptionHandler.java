package christmas.controller.handler;

import christmas.controller.ExceptionHandler;
import christmas.controller.InputView;
import christmas.controller.OutputView;
import java.util.function.Function;

public class InfiniteRetryExceptionHandler implements ExceptionHandler {

    @Override
    public Object handle(InputView inputView, OutputView outputView, Function<InputView, Object> function) {
        while (true) {
            try {
                return function.apply(inputView);
            } catch (IllegalArgumentException e) {
                outputView.printError(e);
            }
        }
    }
}

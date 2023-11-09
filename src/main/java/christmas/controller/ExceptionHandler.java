package christmas.controller;

import java.util.function.Function;

public interface ExceptionHandler {

    Object handle(InputView inputView, OutputView outputView, Function<InputView, Object> function);
}

package io.github.heezer.alephalpha.client;

import java.util.function.Consumer;

public interface AsyncResponseHandling {
  ErrorHandling onError(Consumer<Throwable> errorHandler);

  ErrorHandling ignoreErrors();
}

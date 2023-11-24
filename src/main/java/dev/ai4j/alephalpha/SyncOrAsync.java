package dev.ai4j.alephalpha;

import java.util.function.Consumer;

public interface SyncOrAsync<ResponseContent> {

    ResponseContent execute();

    AsyncResponseHandling onResponse(Consumer<ResponseContent> responseHandler);
}
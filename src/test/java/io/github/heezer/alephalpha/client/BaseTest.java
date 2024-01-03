package io.github.heezer.alephalpha.client;

public abstract class BaseTest {

  protected static final String DEFAULT_PROMPT = "An apple a day keeps the doctor away.";
  protected final Client client = Client
    .builder()
    .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
    .logRequests()
    .logResponses()
    .build();
}

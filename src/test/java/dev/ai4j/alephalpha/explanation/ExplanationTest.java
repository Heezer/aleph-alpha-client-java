package dev.ai4j.alephalpha.explanation;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.Client;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
class ExplanationTest {

  private final Client client = Client
    .builder()
    .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
    .logRequests()
    .logResponses()
    .build();

  @Test
  void simpleExplanationWorks() {
    val response = client.explain(
      ExplanationRequest.builder().prompt("An apple a day").target("keeps the doctor away").build()
    );

    assertThat(response).isNotNull().extracting(ExplanationResponse::getExplanations).isNotNull();
  }
}

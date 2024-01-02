package dev.ai4j.alephalpha.tokenize;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.Client;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
class TokenizationTest {

  private final Client client = Client
    .builder()
    .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
    .logRequests()
    .logResponses()
    .build();

  @Test
  void simpleTokenizationWorks() {
    val response = client.tokenize(
      TokenizationRequest.builder().prompt("An apple a day keeps the doctor away").tokens(true).tokenIds(false).build()
    );

    assertThat(response)
      .isNotNull()
      .extracting(TokenizationResponse::getTokens)
      .isNotNull()
      .asList()
      .hasSize(8)
      .contains("Ġapple");
  }
}

package dev.ai4j.alephalpha.tokens;

import dev.ai4j.alephalpha.Client;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
@Slf4j
class TokensTest {

  private final Client client = Client
    .builder()
    .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
    .logRequests()
    .logResponses()
    .build();

  @Test
  void receivingIssuedApiTokensWorks() {
    val response = client.apiTokens();

    log.info("Your issued Aleph Alpha API tokens: {}", response);
    assertThat(response).isNotNull().isNotEmpty();
  }

  @Test
  void creatingAndDeletingApiTokenWorks() {
    val response = client.createApiToken(NewApiTokenRequest.builder().description("tokenFromTest").build());

    log.info("Your issued Aleph Alpha API tokens: {}", response);
    assertThat(response)
      .isNotNull()
      .extracting(NewApiTokenResponse::getMetadata)
      .extracting(ApiToken::getDescription)
      .isEqualTo("tokenFromTest");

    client.deleteApiToken(response.getMetadata().getTokenId());

    assertThat(client.apiTokens()).noneSatisfy(token -> assertThat(token.getDescription()).isEqualTo("tokenFromTest"));
  }
}

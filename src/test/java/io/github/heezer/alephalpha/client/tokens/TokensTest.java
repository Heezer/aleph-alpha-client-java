package io.github.heezer.alephalpha.client.tokens;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.heezer.alephalpha.BaseTest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

@Slf4j
class TokensTest extends BaseTest {

  @Test
  void receivingIssuedApiTokensWorks() {
    val response = client.apiTokens();

    log.info("Your issued API tokens: {}", response);
    assertThat(response).isNotNull().isNotEmpty();
  }

  @Test
  void creatingAndDeletingApiTokenWorks() {
    val response = client.createApiToken(NewApiTokenRequest.builder().description("tokenFromTest").build());

    log.info("Your issued API tokens: {}", response);
    assertThat(response)
      .isNotNull()
      .extracting(NewApiTokenResponse::getMetadata)
      .extracting(ApiToken::getDescription)
      .isEqualTo("tokenFromTest");

    client.deleteApiToken(response.getMetadata().getTokenId());

    assertThat(client.apiTokens()).noneSatisfy(token -> assertThat(token.getDescription()).isEqualTo("tokenFromTest"));
  }
}

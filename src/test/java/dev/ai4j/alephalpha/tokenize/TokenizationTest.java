package dev.ai4j.alephalpha.tokenize;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.BaseTest;
import lombok.val;
import org.junit.jupiter.api.Test;

class TokenizationTest extends BaseTest {

  @Test
  void simpleTokenizationWorks() {
    val response = client.tokenize(
      TokenizationRequest.builder().prompt(DEFAULT_PROMPT).tokens(true).tokenIds(false).build()
    );

    assertThat(response)
      .isNotNull()
      .extracting(TokenizationResponse::getTokens)
      .isNotNull()
      .asList()
      .hasSize(9)
      .contains("Ġapple");
    assertThat(response).extracting(TokenizationResponse::getTokenIds).isNull();
  }

  @Test
  void simpleDetokenizationWorks() {
    val tokens = client
      .tokenize(TokenizationRequest.builder().prompt(DEFAULT_PROMPT).tokens(false).tokenIds(true).build())
      .getTokenIds();

    val response = client.detokenize(DetokenizationRequest.builder().tokenIds(tokens).build());

    assertThat(response).isNotNull().extracting(DetokenizationResponse::getResult).asString().endsWith(DEFAULT_PROMPT);
  }
}

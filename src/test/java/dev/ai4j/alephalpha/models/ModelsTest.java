package dev.ai4j.alephalpha.models;

import dev.ai4j.alephalpha.Client;
import static dev.ai4j.alephalpha.Models.BASE_MODEL;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
@Slf4j
public class ModelsTest {

  private final Client client = Client
    .builder()
    .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
    .logRequests()
    .logResponses()
    .build();

  @Test
  void receivingAvailableModelsWorks() {
    val response = client.models();

    log.info("Available Aleph Alpha models: {}", response);
    assertThat(response).isNotNull().hasSizeGreaterThan(5);
  }

  @Test
  void receivingTokenizerThatWasUsedToTrainModelWorks() {
    val response = client.tokenizerForModel(BASE_MODEL);

    log.info("Tokenizer that was used to train models {}: {}", BASE_MODEL, response);
    assertThat(response).isNotNull();
  }
}

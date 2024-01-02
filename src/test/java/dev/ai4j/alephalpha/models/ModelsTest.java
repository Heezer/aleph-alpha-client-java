package dev.ai4j.alephalpha.models;

import static dev.ai4j.alephalpha.Models.BASE_MODEL;
import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.BaseTest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled("Cannot be executed automatically because of the API key")
@Slf4j
public class ModelsTest extends BaseTest {

  @Test
  void receivingAvailableModelsWorks() {
    val response = client.models();

    log.info("Available models: {}", response);
    assertThat(response).isNotNull().hasSizeGreaterThan(5);
  }

  @Test
  void receivingTokenizerThatWasUsedToTrainModelWorks() {
    val response = client.tokenizerForModel(BASE_MODEL);

    log.info("Tokenizer that was used to train models {}: {}", BASE_MODEL, response);
    assertThat(response).isNotNull();
  }
}

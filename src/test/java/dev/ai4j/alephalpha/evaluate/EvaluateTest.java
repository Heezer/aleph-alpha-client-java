package dev.ai4j.alephalpha.evaluate;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.BaseTest;
import lombok.val;
import org.junit.jupiter.api.Test;

class EvaluateTest extends BaseTest {

  @Test
  void simpleEvaluateWorks() {
    val response = client.evaluate(
      EvaluateRequest
        .builder()
        .prompt("An apple a day")
        .completionExpected("keeps the doctor away, but what about a banana a day?")
        .build()
    );

    assertThat(response).isNotNull();

    val result = response.getResult();
    assertThat(result.getLogProbability()).isLessThan(-23);
    assertThat(result.getLogPerplexity()).isGreaterThan(23);
    assertThat(result.getCharacterCount()).isEqualTo(53);
  }
}

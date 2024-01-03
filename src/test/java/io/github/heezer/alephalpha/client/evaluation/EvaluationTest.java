package io.github.heezer.alephalpha.client.evaluation;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.heezer.alephalpha.BaseTest;
import lombok.val;
import org.junit.jupiter.api.Test;

class EvaluationTest extends BaseTest {

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

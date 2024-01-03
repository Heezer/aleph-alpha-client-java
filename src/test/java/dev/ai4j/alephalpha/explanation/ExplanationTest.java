package dev.ai4j.alephalpha.explanation;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.BaseTest;
import lombok.val;
import org.junit.jupiter.api.Test;

class ExplanationTest extends BaseTest {

  @Test
  void simpleExplanationWorks() {
    val response = client.explain(
      ExplanationRequest.builder().prompt("An apple a day").target("keeps the doctor away").build()
    );

    assertThat(response).isNotNull().extracting(ExplanationResponse::getExplanations).isNotNull();
  }
}

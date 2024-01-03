package dev.ai4j.alephalpha.specification;

import static org.assertj.core.api.Assertions.assertThat;

import dev.ai4j.alephalpha.BaseTest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

@Slf4j
class SpecificationTest extends BaseTest {

  @Test
  void receivingSpecificatonWorks() {
    val response = client.specification();

    log.info("API specification: {}", response);
    assertThat(response).isNotNull().hasSizeGreaterThan(1000);
  }
}

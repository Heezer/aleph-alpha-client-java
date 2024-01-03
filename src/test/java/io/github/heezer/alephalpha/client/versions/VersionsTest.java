package io.github.heezer.alephalpha.client.versions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.heezer.alephalpha.client.BaseTest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;

@Slf4j
class VersionsTest extends BaseTest {

  @Test
  void receivingVersionWorks() {
    val response = client.version();

    log.info("API version: {}", response);
    assertThat(response).isNotNull().hasSizeGreaterThan(5);
  }

  @Test
  void receivingOpenApiVersionsWorks() {
    val response = client.openApiVersions();

    log.info("Available versions of OpenAPI description: {}", response);
    assertThat(response).isNotNull().hasSizeGreaterThan(1);
  }

  @Test
  void receivingOpenApiDescriptionWorks() {
    val lastOpenApiVersion = client.openApiVersions().stream().reduce((first, second) -> second).orElse(null);

    val response = client.openApiDescription(lastOpenApiVersion);

    log.info("OpenAPI description for API version {}: {}", lastOpenApiVersion, response);
    assertThat(response).isNotNull().hasSizeGreaterThan(1000);
  }
}

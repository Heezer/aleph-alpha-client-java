package io.github.heezer.alephalpha.client.tokens;

import lombok.Data;

@Data
public class NewApiTokenResponse {

  ApiToken metadata;
  String token;
}

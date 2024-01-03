package io.github.heezer.alephalpha.tokens;

import lombok.Data;

@Data
public class NewApiTokenResponse {

  ApiToken metadata;
  String token;
}

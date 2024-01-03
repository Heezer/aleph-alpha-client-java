package io.github.heezer.alephalpha.client.tokens;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewApiTokenRequest {

  String description;
}

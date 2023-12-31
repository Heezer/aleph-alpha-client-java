package dev.ai4j.alephalpha.tokens;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewApiTokenRequest {

  String description;
}

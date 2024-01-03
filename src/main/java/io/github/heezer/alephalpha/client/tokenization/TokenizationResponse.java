package io.github.heezer.alephalpha.client.tokenization;

import java.util.List;
import lombok.Data;

@Data
public class TokenizationResponse {

  private List<String> tokens;
  private List<Integer> tokenIds;
}

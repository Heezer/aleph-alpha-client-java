package io.github.heezer.alephalpha.client.tokenization;

import static io.github.heezer.alephalpha.client.Models.BASE_MODEL;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetokenizationRequest {

  @Builder.Default
  private String model = BASE_MODEL;

  private List<Integer> tokenIds;
}

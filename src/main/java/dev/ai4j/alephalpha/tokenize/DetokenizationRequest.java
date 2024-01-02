package dev.ai4j.alephalpha.tokenize;

import static dev.ai4j.alephalpha.Models.BASE_MODEL;

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

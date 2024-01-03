package io.github.heezer.alephalpha.explanation;

import java.util.List;
import lombok.Data;

@Data
public class ExplanationResponse {

  private String modelVersion;
  private List<Explanation> explanations;

  @Data
  public static class Explanation {

    private String target;
    private List<Object> items;
  }
}

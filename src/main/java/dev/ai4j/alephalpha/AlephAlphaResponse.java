package dev.ai4j.alephalpha;

import lombok.Data;

import java.util.List;

@Data
public class AlephAlphaResponse {
    private List<Completion> completions;
    private String modelVersion;
    private String optimizedPrompt;

    @Data
    public static class Completion {
        private String completion;
        private String finishReason;
    }
}

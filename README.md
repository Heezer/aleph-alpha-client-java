# Java client library for [Aleph Alpha](https://aleph-alpha.com/)

This is an unofficial Java client library that helps to connect your Java applications with [Aleph Alpha API](https://docs.aleph-alpha.com/api/).

## Current capabilities

- [Completion](https://docs.aleph-alpha.com/api/complete)
- [Embeddings](https://docs.aleph-alpha.com/api/embed)
- [Semantic Embeddings](https://docs.aleph-alpha.com/api/semantic-embed)

## Coming soon

- Detailed javadocs
- The rest of API endpoints
- [Tell us what you need](https://github.com/Heezer/aleph-alpha-client-java/issues/new)

# Code examples

## Create an Aleph Alpha Client

Simple way:

```
Client client = Client.builder()
  .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
  .build();
```

Customizable way:

```
Client client = Client.builder()
  .apiKey(System.getenv("ALEPH_ALPHA_API_KEY"))
  .callTimeout(ofSeconds(60))
  .connectTimeout(ofSeconds(60))
  .readTimeout(ofSeconds(60))
  .writeTimeout(ofSeconds(60))
  .proxy(HTTP, "XXX.XXX.XXX.XXX", 8080)
  .logRequests(true)
  .logResponses(true)
  .build();
```

## Completions

Simple way:

```
CompletionResponse response = client.complete(CompletionRequest.builder().prompt("An apple a day").build());
```

Customizable way:

```
CompletionResponse response = client.complete(
  CompletionRequest
    .builder()
    .model("luminous-supreme-control")
    .prompt(
      "### Instruction:\n" +
      "Identify the topic of the text.\n" +
      "Reply like this: \"The topic is: <topic>\"\n" +
      "\n" +
      "### Input:\n" +
      "Flowering plants grow fruits to spread their seeds. Edible fruits function symbiotically. " +
      "Animals obtain nourishment from consuming the fruit and the animal's movement helps spread the seed.\n" +
      "\n" +
      "### Response:"
    )
    .maximumTokens(16)
    .rawCompletion(true)
    .build()
);
```

## Embeddings

```
EmbeddingsResponse response = client.embed(
  EmbeddingsRequest
    .builder()
    .prompt("An apple a day keeps the doctor away.")
    .layers(Collections.singletonList(INPUT_LAYER_MINUS_ONE))
    .pooling(Arrays.asList(POOLING_MAX, POOLING_WEIGHTED_MEAN))
    .build()
);
```

## Semantic Embeddings

```
SemanticEmbeddingsResponse response = client.semanticEmbed(
  SemanticEmbeddingsRequest.builder().prompt("An apple a day keeps the doctor away.").compressToSize(128).build()
);
```


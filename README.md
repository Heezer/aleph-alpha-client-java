# Java client library for [Aleph Alpha](https://aleph-alpha.com/)

<img src="aaclient.jpg" width="600" alt="Aleph Alpha Java Client Image"/>

This is an unofficial Java client library that helps to connect your Java applications to [Aleph Alpha API](https://docs.aleph-alpha.com/api/).

## Current capabilities

We support the [API version 1.16.0](https://docs.aleph-alpha.com/api/v1.16.0/) completely for Java 8+.

Tell us about any issue you face or new feature [here](https://github.com/Heezer/aleph-alpha-client-java/issues/new).

### What's coming

* Asynchronous communication

# Start using

Maven:

```
<dependency>
    <groupId>io.github.heezer</groupId>
    <artifactId>aleph-alpha-client-java</artifactId>
    <version>0.1.2</version>
</dependency>
```

Gradle:

```
implementation 'io.github.heezer:aleph-alpha-client-java:0.1.2'
```


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

## Image Completions

```
String base64Image = <your image as Base64>;

CompletionResponse response = client.complete(
  CompletionRequest
    .builder()
    .prompt(Collections.singletonList(MultimodalImage.builder().data(base64Image).build()))
    .build()
);

response.getCompletions().get(0).getCompletion(); // image completion/description is here
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

## Tokenization & Detokenization

```
List<Integer> tokens = client
  .tokenize(
    TokenizationRequest
      .builder()
      .prompt("An apple a day keeps the doctor away.")
      .tokens(false)
      .tokenIds(true)
      .build()
  )
  .getTokenIds();

DetokenizationResponse response = client.detokenize(DetokenizationRequest.builder().tokenIds(tokens).build());

response.getResult(); // An apple a day keeps the doctor away.
```

## Retrieving Available Models

```
List<Model> response = client.models();
```


# Useful Links
* [Aleph Alpha Latest HTTP API](https://docs.aleph-alpha.com/api/)
* [Aleph Alpha: Die deutsche Antwort auf ChatGPT (in German)](https://www.youtube.com/watch?v=ATrWzENRAu8)

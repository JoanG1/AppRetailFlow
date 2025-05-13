package com.tuempresa.retailflow.testRail;

import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TestRailClient {
    private final String baseUrl;
    private final String authHeader;

    public TestRailClient(String baseUrl, String username, String apiKey) {
        this.baseUrl = baseUrl;
        String auth = username + ":" + apiKey;
        this.authHeader = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
    }

    private HttpRequest.Builder createRequest(String path, String jsonBody) {
        return HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody));
    }

    public String createTestRun(int projectId, int suiteId, int caseId, String name) throws Exception {
        String path = "/index.php?/api/v2/add_run/" + projectId;

        String body = String.format("""
    {
        "suite_id": %d,
        "name": "%s",
        "include_all": true,
        "case_ids": [%d]
    }
    """, suiteId, name, caseId);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                createRequest(path, body).build(),
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to create test run: " + response.body());
        }

        return response.body();
    }


    public void addResult(int testId, int statusId, String comment) throws Exception {
        String path = "/index.php?/api/v2/add_result/" + testId;
        String body = String.format("{\"status_id\": %d, \"comment\": \"%s\"}", statusId, comment);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                createRequest(path, body).build(),
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to add result: " + response.body());
        }
    }

    public String getTestsForRun(int runId) throws Exception {
        String path = "/index.php?/api/v2/get_tests/" + runId;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .header("Content-Type", "application/json")
                .header("Authorization", authHeader)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to get tests: " + response.body());
        }

        return response.body();
    }

    public void addResultForCase(int runId, int caseId, int statusId, String comment) throws Exception {
        String path = "/index.php?/api/v2/add_result_for_case/" + runId + "/" + caseId;
        String body = String.format("{\"status_id\": %d, \"comment\": \"%s\"}", statusId, comment);

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(
                createRequest(path, body).build(),
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException("❌ Error al subir resultado a TestRail: " + response.body());
        }
    }

}



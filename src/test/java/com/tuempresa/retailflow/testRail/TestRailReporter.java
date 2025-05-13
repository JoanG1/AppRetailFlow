package com.tuempresa.retailflow.testRail;

import org.json.JSONObject;

public class TestRailReporter {
    private final TestRailClient client;
    private final int projectId;
    private final int suiteId;
    private final int caseId;

    public TestRailReporter(TestRailClient client, int projectId, int suiteId, int caseId) {
        this.client = client;
        this.projectId = projectId;
        this.suiteId = suiteId;
        this.caseId = caseId;
    }

    public void reportResultPerTest(String runName, boolean passed, String comment) throws Exception {
        // Crear nuevo run SOLO para este test
        String runJson = client.createTestRun(projectId, suiteId, caseId, runName);
        JSONObject runObj = new JSONObject(runJson);
        System.out.println("🧪 Creando test run con:\nSuite ID: " + suiteId + "\nNombre: " + runName + "\nCase ID: " + caseId);
        System.out.println("🧾 JSON enviado:\n" + runObj.toString());

        int runId = runObj.getInt("id");

        int statusId = passed ? 1 : 5; // 1 = Passed, 5 = Failed
        client.addResultForCase(runId, caseId, statusId, comment);
    }
}


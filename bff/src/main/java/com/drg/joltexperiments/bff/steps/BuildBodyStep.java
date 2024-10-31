package com.drg.joltexperiments.bff.steps;

import com.drg.joltexperiments.bff.Step;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpHeaders;

import java.util.Map;

public class BuildBodyStep implements StepInteface {

    @Override
    public String execute(HttpHeaders headers, String body, Step step, Map<String, Object> stepResults) {
        Map<String, String> renameMappings = step.getRenameMappings();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode bodyNode = mapper.createObjectNode();

        if (renameMappings != null) {
            renameMappings.forEach((sourcePath, targetKey) -> {
                extractJsonPathValue(sourcePath, stepResults, mapper)
                        .ifPresent(value -> bodyNode.putPOJO(targetKey, value));
            });
        }
        stepResults.put(step.getName(), bodyNode.toString());
        return "";
    }
}
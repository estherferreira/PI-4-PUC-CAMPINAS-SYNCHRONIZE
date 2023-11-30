package com.synback.services;

import com.synback.models.UserProfile;
import com.synback.models.UserDiagnosis;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Calendar;
import com.synback.utils.Data;
import java.util.List;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DiagnosticsService {

    @Value("${openai.token}")
    private String openaiApiKey;

    public List<UserDiagnosis.ReportItem> parseOpenAiResponse(String response) {
        List<UserDiagnosis.ReportItem> reportItems = new ArrayList<>();
        Pattern pattern = Pattern.compile(
                "Problema: (.*?)\\nChance de ocorrer: (.*?)\\nDescrição: (.*?)(?=\\n\\nProblema:|\\Z)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(response);

        while (matcher.find()) {
            String problem = matcher.group(1).trim();
            String chanceOfOccurrence = matcher.group(2).trim();
            String description = matcher.group(3).trim();
            reportItems.add(new UserDiagnosis.ReportItem(problem, chanceOfOccurrence, description));
        }

        System.out.println(reportItems);
        return reportItems;
    }

    private int calculateAge(Data birthData) {
        Calendar birthDate = Calendar.getInstance();
        birthDate.set(Calendar.YEAR, birthData.getAno());
        birthDate.set(Calendar.MONTH, birthData.getMes() - 1); // O mês no Calendar começa do 0
        birthDate.set(Calendar.DAY_OF_MONTH, birthData.getDia());

        Calendar currentDate = Calendar.getInstance();

        int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        if (birthDate.get(Calendar.DAY_OF_YEAR) > currentDate.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        return age;
    }

    public String buildPrompt(UserProfile user, String symptoms) {
        int age = calculateAge(user.getDateOfBirth());

        return "Por favor, analise as seguintes informações e forneça uma lista de três possíveis problemas de saúde. Para cada problema, indique o nome do problema, a chance de ocorrer (baixa, moderada ou alta) e uma breve descrição. Formate sua resposta como: 'Problema: [nome do problema], Chance de ocorrer: [baixa/moderada/alta], Descrição: [descrição]'.\n\n"
                +
                "Informações do usuário:\n" +
                "- Idade: " + age + "\n" +
                "- Gênero: " + user.getGender() + "\n" +
                "- Peso: " + user.getWeight() + "\n" +
                "- Altura: " + user.getHeight() + "\n" +
                "- Doenças na família: " + user.getDiseaseHistory() + "\n" +
                "- Tempo médio de exercícios por dia: " + user.getExerciseTime() + "\n" +
                "- Sintoma: " + symptoms + "\n";
    }

    // Método para extrair o conteúdo relevante da resposta da OpenAI
    private String extractContentFromResponse(String response) {
        String startToken = "\"content\": \"";
        int startIndex = response.indexOf(startToken);

        if (startIndex == -1) {
            return ""; // Retorna string vazia se não encontrar o token
        }

        // Ajustar startIndex para começar após o token
        startIndex += startToken.length();

        // Encontrar o índice de fim (pode ser o final da string ou um token específico)
        int endIndex = response.indexOf("\n\n", startIndex);
        endIndex = (endIndex == -1) ? response.length() : endIndex;

        // Extrair e retornar o conteúdo relevante
        return response.substring(startIndex, endIndex).replace("\\n", "\n").trim();
    }

    // Enviar para ChatGPT
    public String callOpenAiService(String prompt) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + openaiApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(createRequestBody(prompt)))
                .build();

        // try {
        // HttpResponse<String> response = client.send(request,
        // HttpResponse.BodyHandlers.ofString());
        // return parseResponse(response.body());
        // } catch (IOException | InterruptedException e) {
        // e.printStackTrace();
        // return null;
        // }
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String content = extractContentFromResponse(response.body());
            return content;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String createRequestBody(String prompt) {
        JsonObject messageUser = new JsonObject();
        messageUser.addProperty("role", "user");
        messageUser.addProperty("content", prompt);

        JsonObject messageSystem = new JsonObject();
        messageSystem.addProperty("role", "system");
        messageSystem.addProperty("content", "Seu prompt aqui");

        JsonArray messages = new JsonArray();
        messages.add(messageSystem);
        messages.add(messageUser);

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-3.5-turbo");
        requestBody.add("messages", messages);

        return requestBody.toString();
    }

    private String parseResponse(String responseBody) {
        return responseBody;
    }
}

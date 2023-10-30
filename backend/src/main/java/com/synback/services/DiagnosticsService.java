package com.synback.services;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import com.synback.models.User;
import java.util.Optional;

public class DiagnosticsService {

    private static final String OPENAI_API_KEY = "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";

    private final UserService userService;

    public DiagnosticsService(UserService userService) {
        this.userService = userService;
    }

    public String diagnose(String userId, String symptoms) {
        try {
            Optional<User> userOptional = userService.getUserById(userId);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                String prompt = "Idade: " + user.getDateOfBirth() + "\n" +
                        "Gênero: " + user.getGender() + "\n" +
                        "Peso: " + user.getWeight() + "\n" +
                        "Altura: " + user.getHeight() + "\n" +
                        "Doenças na família: " + user.getDiseasesInTheFamily() + "\n" +
                        "Tempo médio de exercícios por dia: " + user.getDailyExerciseTime() + "\n" +
                        "Sintoma: " + symptoms + "\n" +
                        "Quais são as três doenças ou problemas de saúde mais prováveis que eu deveria me atentar baseado nos dados informados, quais são suas respectivas porcentagens em números de ocorrerem e o que devo fazer para melhorar esses sintomas ou acabar com eles?";

                // Enviar para ChatGPT
                OpenAiService service = new OpenAiService(OPENAI_API_KEY);
                CompletionRequest completionRequest = CompletionRequest.builder()
                        .prompt(prompt)
                        .model("gpt-3.5-turbo")
                        .echo(true)
                        .build();
                String response = service.createCompletion(completionRequest).getChoices().get(0).getText();

                return response;
            } else {
                return "Usuário não encontrado";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Ocorreu um erro ao diagnosticar";
        }
    }
}

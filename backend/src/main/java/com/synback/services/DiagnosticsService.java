// package com.synback.services;

// import com.synback.models.UserProfile;
// import com.synback.models.Diagnosis;
// import com.synback.repositories.DiagnosisRepository;
// import com.synback.repositories.UserProfileRepository;
// import com.theokanning.openai.completion.CompletionRequest;
// import com.theokanning.openai.service.OpenAiService;
// import java.util.ArrayList;
// import java.util.Calendar;
// import com.synback.utils.Data;
// import java.util.List;
// import org.springframework.stereotype.Service;

// @Service
// public class DiagnosticsService {

//     private final UserProfileRepository userRepository;
//     private final DiagnosisRepository diagnosisRepository;
//     private static final String OPENAI_API_KEY = System.getenv("PUBLIC_API_KEY");

//     public DiagnosticsService(UserProfileRepository userRepository, DiagnosisRepository diagnosisRepository) {
//         this.userRepository = userRepository;
//         this.diagnosisRepository = diagnosisRepository;
//     }

//     public Diagnosis diagnosis(String userId, String symptoms) {
//         UserProfile user = userRepository.findById(userId)
//                 .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

//         String prompt = buildPrompt(user, symptoms);
//         String openAiResponse = callOpenAiService(prompt);

//         Diagnosis diagnosis = new Diagnosis(userId, parseOpenAiResponse(openAiResponse), symptoms, user.getName());
//         return diagnosisRepository.save(diagnosis);
//     }

//     private List<Diagnosis.ReportItem> parseOpenAiResponse(String response) {
//         List<Diagnosis.ReportItem> reportItems = new ArrayList<>();
//         String[] lines = response.split("\n");

//         for (String line : lines) {
//             String[] parts = line.split(",");
//             if (parts.length >= 3) { // Garantir que há pelo menos 3 partes
//                 String problem = parts[0].replace("Problema: ", "").trim();
//                 String percentage = parts[1].replace("Chance de ocorrer: ", "").trim();
//                 String description = parts[2].replace("Descrição: ", "").trim();

//                 // Agora adicionamos ReportItems à lista
//                 reportItems.add(new Diagnosis.ReportItem(problem, percentage, description));
//             }
//         }

//         return reportItems;
//     }

//     private int calculateAge(Data birthData) {
//         Calendar birthDate = Calendar.getInstance();
//         birthDate.set(Calendar.YEAR, birthData.getAno());
//         birthDate.set(Calendar.MONTH, birthData.getMes() - 1); // O mês no Calendar começa do 0
//         birthDate.set(Calendar.DAY_OF_MONTH, birthData.getDia());

//         Calendar currentDate = Calendar.getInstance();

//         int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
//         if (birthDate.get(Calendar.DAY_OF_YEAR) > currentDate.get(Calendar.DAY_OF_YEAR)) {
//             age--;
//         }

//         return age;
//     }

//     private String buildPrompt(UserProfile user, String symptoms) {
//         int age = calculateAge(user.getDateOfBirth());

//         return "Idade: " + age + "\n" +
//                 "Gênero: " + user.getGender() + "\n" +
//                 "Peso: " + user.getWeight() + "\n" +
//                 "Altura: " + user.getHeight() + "\n" +
//                 "Doenças na família: " + user.getDiseaseHistory() + "\n" +
//                 "Tempo médio de exercícios por dia: " + user.getExerciseTime() + "\n" +
//                 "Sintoma: " + symptoms + "\n" +
//                 "Quais são as três doenças ou problemas de saúde mais prováveis que eu deveria me atentar baseado nos dados informados, quais são suas respectivas porcentagens em números de ocorrerem e o que devo fazer para melhorar esses sintomas ou acabar com eles?";
//     }

//     // Enviar para ChatGPT
//     private String callOpenAiService(String prompt) {
//         OpenAiService service = new OpenAiService(OPENAI_API_KEY);
//         CompletionRequest completionRequest = CompletionRequest.builder()
//                 .prompt(prompt)
//                 .model("gpt-3.5-turbo")
//                 .maxTokens(4000)
//                 .echo(true)
//                 .build();
//         String response = service.createCompletion(completionRequest).getChoices().get(0).getText();
//         System.out.println("Response" + response);
//         return response;
//     }
// }

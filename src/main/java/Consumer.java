import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();                                          // Объект для запроса к удаленным сервисам (RestAPI)

        String url = "https://reqres.in/api/users/2";                                            // Запрос к тестовому сервису https://reqres.in для получения JSON, GET-запрос
        String response = restTemplate.getForObject(url, String.class);                          // JSON получим в виде объекта класса String

        System.out.println(response);

// POST-запрос
        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", "Test name");
        jsonToSend.put("job", "Test job");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonToSend);                   // Для отправки Map нужно упаковывать ее в http-запрос. Map преобразуется в JSON.

        String urlPost = "https://reqres.in/api/users";
        String responsePost = restTemplate.postForObject(url, request, String.class);

        System.out.println(responsePost);

// Парсим JSON c помощью Jackson
        ObjectMapper mapper = new ObjectMapper();                                                 // С помощью ObjectMapper можно распарсить любую строку JSONa
        JsonNode obj = mapper.readTree(response);
        System.out.println(obj.get("data").get("email"));

        JsonNode objPost = mapper.readTree(responsePost);
        System.out.println(objPost.get("job"));                                                     // Если массив в JSONе, то obj.get(номер/имя эл.).get("нужное поле")

// Парсить массив можно и через класс с сущностями
        String urlArray = "https://reqres.in/api/users?page=2";
        String responseTest = restTemplate.getForObject(urlArray, String.class);
        JsonNode objTest = mapper.readTree(responseTest);

        String responseTestString = restTemplate.getForObject(urlArray, String.class);
        System.out.println(objTest.get("data").get(0).get("email"));
        //System.out.println(responseTest.getFieldList().get().getEmail());
    }
}

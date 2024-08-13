import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class ViaCep {
    public static void main(String[] args) {
        String cep = "15043020";
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            JSONObject response = new JSONObject(content.toString());
            String logradouro = response.getString("logradouro");
            String bairro = response.getString("bairro");
            String localidade = response.getString("localidade");
            String uf = response.getString("uf");

            System.out.println("Seu endereço de entrega \nLogradouro: " + logradouro + "\nBairro: " + bairro);
            System.out.println("Localidade: " + localidade + " - " + uf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Conversor {
    private static final String API_KEY = "1bea75fa83c9e945fa285ecf";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD";

    private JsonObject rates;

    public Conversor() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonParser parser = new JsonParser();
            JsonObject jsonResponse = parser.parse(response.body()).getAsJsonObject();

            if (jsonResponse.get("result").getAsString().equals("success")) {
                this.rates = jsonResponse.getAsJsonObject("conversion_rates");
            } else {
                System.out.println("Erro ao obter taxas de câmbio. Usando taxas padrão.");
                carregarTaxasPadrao();
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao conectar com a API. Usando taxas padrão.");
            carregarTaxasPadrao();
        }
    }

    private void carregarTaxasPadrao() {
        this.rates = new JsonObject();
        rates.addProperty("USD", 1.0);
        rates.addProperty("ARS", 350.0);  // Peso Argentino
        rates.addProperty("BRL", 4.8665); // Real Brasileiro
        rates.addProperty("COP", 3900.0); // Peso Colombiano
    }

    public double converter(double valor, String moedaOrigem, String moedaDestino) {
        try {
            double taxaOrigem = rates.get(moedaOrigem).getAsDouble();
            double taxaDestino = rates.get(moedaDestino).getAsDouble();
            return valor * (taxaDestino / taxaOrigem);
        } catch (Exception e) {
            System.out.println("Erro na conversão: Moeda não suportada.");
            return 0.0;
        }
    }

    public void exibirMenu() {
        System.out.println("""
                *************************************************************************
                Seja bem-vindo/a ao Conversor de Moeda =]
                
                1) Dólar  =>> Peso argentino
                2) Peso argentino ==> Dólar
                3) Dólar =>> Real brasileiro
                4) Real brasileiro =>> Dólar
                5) Dólar =>> Peso colombiano
                6) Peso colombiano =>> Dólar
                7) Sair
                Escolha uma opção válida:
                ****************************************************************************""");
    }

    public void executarOpcao(int opcao, double valor) {
        switch (opcao) {
            case 1:
                System.out.printf("Valor %.1f [USD] corresponde ao valor final de =>>> %.2f [ARS]%n",
                        valor, converter(valor, "USD", "ARS"));
                break;
            case 2:
                System.out.printf("Valor %.1f [ARS] corresponde ao valor final de =>>> %.2f [USD]%n",
                        valor, converter(valor, "ARS", "USD"));
                break;
            case 3:
                System.out.printf("Valor %.1f [USD] corresponde ao valor final de =>>> %.2f [BRL]%n",
                        valor, converter(valor, "USD", "BRL"));
                break;
            case 4:
                System.out.printf("Valor %.1f [BRL] corresponde ao valor final de =>>> %.2f [USD]%n",
                        valor, converter(valor, "BRL", "USD"));
                break;
            case 5:
                System.out.printf("Valor %.1f [USD] corresponde ao valor final de =>>> %.2f [COP]%n",
                        valor, converter(valor, "USD", "COP"));
                break;
            case 6:
                System.out.printf("Valor %.1f [COP] corresponde ao valor final de =>>> %.2f [USD]%n",
                        valor, converter(valor, "COP", "USD"));
                break;
            case 7:
                System.out.println("Saindo do conversor...");
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }
}


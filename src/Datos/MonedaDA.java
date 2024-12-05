package Datos;

import Datos.Interfaces.IGestionarMonedaDA;
import Dominio.Moneda;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static Util.ExchangeRateAPIServicio.API_URL;

public class MonedaDA implements IGestionarMonedaDA {

    private URI direccionUrl;
    private HttpClient clienteHttp;

    public MonedaDA() {
        this.direccionUrl = URI.create(API_URL);
        this.clienteHttp = HttpClient.newHttpClient();
    }

    @Override
    public Moneda[] obtenerCambiosSegunMoneda(String nombreMoneda) {

        this.direccionUrl = URI.create(API_URL+nombreMoneda);
        HttpRequest solicitud = HttpRequest.newBuilder().uri(this.direccionUrl).build();

        try {
            HttpResponse<String> respuesta = clienteHttp.send(solicitud, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();

            //Convierte la respuesta del API en un objeto JSON
            JsonObject jsonObject = gson.fromJson(respuesta.body(), JsonObject.class);

            // Acceder a la propiedad de conversion_rates
            JsonObject conversionRatesJson = jsonObject.getAsJsonObject("conversion_rates");

            // Mapea el campo conversion_rates a un array de Moneda
            Moneda[] resultadoMonedas = conversionRatesJson.entrySet().stream()
                    .map(moneda -> new Moneda(moneda.getKey(), moneda.getValue().getAsDouble()
                    ))
                    .toArray(Moneda[]::new);

            return resultadoMonedas;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener las monedas.", e);
        }
    }



}

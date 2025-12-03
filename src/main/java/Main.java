package com.example;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa la moneda (ej: USD): ");
        String base = scanner.nextLine().toUpperCase();

        System.out.print("A que moneda deseas convertirla (ej: EUR): ");
        String destino = scanner.nextLine().toUpperCase();

        System.out.print("Cuantas Monedas deseas convertir: ");
        double cantidad = scanner.nextDouble();

        String apiKey = "d4b5c877fce443c8c1d9e385";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + base;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        if (!json.get("result").getAsString().equals("success")) {
            System.out.println("Error al obtener los datos de la API.");
            return;
        }

        JsonObject rates = json.getAsJsonObject("conversion_rates");
        double tasa = rates.get(destino).getAsDouble();
        double resultado = cantidad * tasa;

        System.out.println("====================================");
        System.out.println(" Tasa de cambio: 1 " + base + " = " + tasa + " " + destino);
        System.out.println(" Resultado: " + resultado + " " + destino);
        System.out.println("====================================");
    }
}

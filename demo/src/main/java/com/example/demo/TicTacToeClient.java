//package com.example.demo;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//public class TicTacToeClient {
//    private static final String BASE_URL = "http://localhost:8080/api/game";
//    private final HttpClient client = HttpClient.newHttpClient();
//    private String gameId;
//
//    // Neues Spiel starten mit Spielernamen
//    public String startGame(String player1Name) throws Exception {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/start?player1Name=" + player1Name))
//                .POST(HttpRequest.BodyPublishers.noBody())
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        this.gameId = extractGameId(response.body());
//        return response.body();
//    }
//
//    // Spielzug ausführen
//    public String makeMove(byte row, byte col, String playerName) throws Exception {
//        if (gameId == null) {
//            throw new IllegalStateException("Spiel wurde noch nicht gestartet.");
//        }
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/move?gameId=" + gameId + "&row=" + row + "&col=" + col + "&playerName=" + playerName))
//                .POST(HttpRequest.BodyPublishers.noBody())
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
//
//    // Spielstatus abrufen
//    public String getGameState() throws Exception {
//        if (gameId == null) {
//            throw new IllegalStateException("Spiel wurde noch nicht gestartet.");
//        }
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/state?gameId=" + gameId))
//                .GET()
//                .build();
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        return response.body();
//    }
//
//    // Hilfsmethode zur Spiel-ID Extraktion
//    private String extractGameId(String responseBody) {
//        if (responseBody.contains("Spiel erstellt mit ID: ")) {
//            return responseBody.split(": ")[1];
//        }
//        return null;
//    }
//
//    public static void main(String[] args) {
//        try {
//            TicTacToeClient client = new TicTacToeClient();
//            String startResponse = client.startGame("Mahan");
//            System.out.println(startResponse);
//
//            String moveResponse = client.makeMove((byte) 0, (byte) 0, "Mahan");
//            System.out.println(moveResponse);
//
//            String stateResponse = client.getGameState();
//            System.out.println(stateResponse);
//
//        } catch (Exception e) {
//            System.err.println("Fehler: " + e.getMessage());
//        }
//    }
//}

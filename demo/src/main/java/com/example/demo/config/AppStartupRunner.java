package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppStartupRunner {

    private static final Logger logger = LoggerFactory.getLogger(AppStartupRunner.class);

    @Bean
    public CommandLineRunner run() {
        return args -> {
            logger.info("==================================");
            logger.info("     T I C  T A C  T O E");
            logger.info("==================================");
            logger.info("Tic Tac Toe - Anwendung erfolgreich gestartet!");
            initializeGame();
        };
    }

    // Initialisierungsmethode für Spielstart
    private void initializeGame() {
        logger.info("Spielbrett wird geladen...");

        // Simulierte Initialisierung (hier könnte z.B. ein Spielfeld gesetzt werden)
        try {
            // Spiellogik oder vorbereitende Maßnahmen
            logger.info("Spiel wird vorbereitet...");

            // Beispiel für zusätzliche Initialisierungen (z.B. Laden von Spielständen)
            loadDefaultGame();

            logger.info("Spiel erfolgreich initialisiert und bereit für Spieler!");
        } catch (Exception e) {
            logger.error("Fehler beim Initialisieren des Spiels: {}", e.getMessage());
        }
    }

    // Methode zur Simulation eines Standardspiels oder Voreinstellungen
    private void loadDefaultGame() {
        logger.info("Standard-Spielbrett wird geladen...");

        // Hier könnte das Standardspiel geladen oder ein Testspiel initialisiert werden
        // Beispiel:
        // TicTacToeGame game = new TicTacToeGame();
        // gameManager.createGame("Spieler 1");

        logger.info("Standard-Spiel ist geladen und bereit.");
    }
}

package SPA.dev.Stock.config;

import java.security.SecureRandom;

public class RandomStringGenerator {
    // Ensemble de caractères possibles (chiffres et lettres)
    private static final String CHARACTERS = "0123456789";

    // Générateur aléatoire sécurisé
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}

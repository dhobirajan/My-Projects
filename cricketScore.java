import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class CricketScoreTracker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> scoreMap = new HashMap<>();

        // Load existing scores from a file
        loadScores(scoreMap);

        while (true) {
            System.out.println("\nCricket Score Tracker Menu:");
            System.out.println("1. Add a player's score");
            System.out.println("2. View player's score");
            System.out.println("3. Save and Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter player's name: ");
                    String playerName = scanner.nextLine();
                    System.out.print("Enter the score: ");
                    int playerScore = scanner.nextInt();

                    // Update the player's score
                    if (scoreMap.containsKey(playerName)) {
                        int currentScore = scoreMap.get(playerName);
                        scoreMap.put(playerName, currentScore + playerScore);
                    } else {
                        scoreMap.put(playerName, playerScore);
                    }
                    break;
                case 2:
                    System.out.print("Enter player's name to view score: ");
                    String playerNameToView = scanner.nextLine();
                    if (scoreMap.containsKey(playerNameToView)) {
                        int playerScoreToView = scoreMap.get(playerNameToView);
                        System.out.println(playerNameToView + "'s score: " + playerScoreToView);
                    } else {
                        System.out.println("Player not found in the records.");
                    }
                    break;
                case 3:
                    // Save scores to a file and exit
                    saveScores(scoreMap);
                    System.out.println("Scores saved. Exiting the program.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Load scores from a file into the HashMap
    private static void loadScores(Map<String, Integer> scoreMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader("scores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String playerName = parts[0];
                    int playerScore = Integer.parseInt(parts[1]);
                    scoreMap.put(playerName, playerScore);
                }
            }
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }

    // Save scores to a file
    private static void saveScores(Map<String, Integer> scoreMap) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt"))) {
            for (Map.Entry<String, Integer> entry : scoreMap.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            // Handle the exception
            e.printStackTrace();
        }
    }
}

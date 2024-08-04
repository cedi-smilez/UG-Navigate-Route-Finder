import javax.swing.*;
import java.awt.*;
import java.util.List;
/*
    |--------------------------------------------------------------------------
    | ResultWindow Class
    |--------------------------------------------------------------------------
    |
  | The ResultWindow class is a GUI component that displays routing results
    | in a separate window. It shows the optimal route between two locations,
    | including landmarks, the algorithm used, and its time complexity.
    |
    | Constructor:
    | - ResultWindow(String start, String end, String landmark, List<Location> nearbyLandmarks,
    |                String algorithmUsed, String timeComplexity, List<Location> route):
    |   Initializes the ResultWindow with the following parameters:
    |   - `start`: The name of the starting location.
    |   - `end`: The name of the destination location.
    |   - `landmark`: The name of a landmark that the route passes through.
    |   - `nearbyLandmarks`: A list of landmarks that are within close proximity
    |     to the starting location.
    |   - `algorithmUsed`: The name of the algorithm used to calculate the route.
    |   - `timeComplexity`: The time complexity of the algorithm used.
    |   - `route`: A list of locations that make up the optimal route from the start
    |     to the end location.
    |
    | Fields:
    | - `backgroundLabel`: A JLabel component that displays a background image for
    |   the window.
    | - `resultArea`: A JTextArea component that displays the textual results, including
    |   the route information and nearby landmarks.
    |
    | Methods:
    | - `buildResultText(String start, String end, String landmark, List<Location> landmarks,
    |                    String algorithmUsed, String timeComplexity, List<Location> route)`:
    |   Generates a formatted text string that includes:
    |   - The optimal route from the start location to the end location.
    |   - The algorithm used for route calculation.
    |   - The time complexity of the algorithm.
    |   - The route details, including any landmarks passed through.
    |   - A list of nearby landmarks if any are found.
    |
    | Purpose:
    | The ResultWindow class provides a visual representation of the route calculation results
    | for users. It helps users understand the route details, including the specific locations,
    | landmarks, and the efficiency of the route calculation algorithm. The window features
    | a background image and allows users to view detailed results in a scrollable text area.
    |
    */

public class ResultWindow extends JFrame {
    public ResultWindow(String start, String end, String landmark, List<Location> nearbyLandmarks, String algorithmUsed, String timeComplexity, List<Location> route) {
        setTitle("Route Results");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the background image
        ImageIcon backgroundIcon = new ImageIcon("resources/images/ug.jpg");
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setOpaque(false); // Make the panel transparent
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextArea resultArea = new JTextArea(20, 60);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setText(buildResultText(start, end, landmark, nearbyLandmarks, algorithmUsed, timeComplexity, route));

        gbc.gridx = 0;
//        gbc.gridy = 0;
//        panel.add(new JLabel(new ImageIcon("resources/images/finish-flag.png")), gbc); // Placeholder image
        gbc.gridy = 1;
        panel.add(new JScrollPane(resultArea), gbc);

        backgroundLabel.add(panel);

        add(backgroundLabel);
    }

    private String buildResultText(String start, String end, String landmark, List<Location> landmarks, String algorithmUsed, String timeComplexity, List<Location> route) {
        StringBuilder result = new StringBuilder();
        result.append("Optimal routes from ").append(start).append(" to ").append(end).append(":\n");
        result.append("Algorithm used: ").append(algorithmUsed).append("\n");
        result.append("Time Complexity: ").append(timeComplexity).append("\n");

        if (route.isEmpty()) {
            result.append("No routes found.\n");
        } else {
            result.append("Routes:\n");
            for (int i = 0; i < route.size(); i++) {
                result.append((i + 1)).append(". Route ").append((char) ('A' + i)).append(" (Passes through: ").append(landmark).append(")\n");
            }
        }

        if (!landmarks.isEmpty()) {
            result.append("\nNearby landmarks:\n");
            for (Location loc : landmarks) {
                result.append("- ").append(loc.getName()).append("\n");
            }
        } else {
            result.append("\nNo nearby landmarks found.\n");
        }

        return result.toString();
    }
}

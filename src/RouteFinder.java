import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.SwingWorker; // Correct import for SwingWorker

/*
    |--------------------------------------------------------------------------
    | RouteFinder Class
    |--------------------------------------------------------------------------
    |
    | The RouteFinder class provides a graphical user interface (GUI) for users
    | to find optimal routes between locations at the University of Ghana. It
    | includes dropdown menus for selecting start and end locations, and an
    | optional landmark for route consideration. The results are displayed in
    | a separate window with details of the route, nearby landmarks, and algorithm
    | information.
    |
    | Constructor:
    | - RouteFinder():
    |   Sets up the main window with dropdowns for locations and landmarks, a
    |   button to find routes, and a menu bar with help options.
    |
    | Components:
    | - `startComboBox`: Dropdown for selecting the start location.
    | - `endComboBox`: Dropdown for selecting the end location.
    | - `landmarkComboBox`: Dropdown for selecting an optional landmark.
    | - `findRouteButton`: Button to initiate route calculation.
    | - `backgroundLabel`: Displays a background image for visual appeal.
    |
    | Methods:
    | - `showDevTeamsDialog()`:
    |   Shows a dialog with developer credits.
    |
    | - `buildResultText(String start, String end, String landmark, List<Location> landmarks,
    |                    String algorithmUsed, String timeComplexity, List<Location> route)`:
    |   Constructs a text summary of the route, landmarks, and algorithm details.
    |
    | - `main(String[] args)`:
    |   Launches the RouteFinder application.
    |
    | Features:
    | - Displays routing results in a new window after computation.
    | - Shows nearby landmarks within a 1 km radius.
    | - Provides a menu bar with options for developer credits, usage manual,
    |   and copyright information.
    |
    */

public class RouteFinder extends JFrame {
    private JComboBox<String> startComboBox;
    private JComboBox<String> endComboBox;
    private JComboBox<String> landmarkComboBox;
    private JButton findRouteButton;
    private JLabel backgroundLabel;

    public RouteFinder() {
        setTitle("UG Navigate - Route Finder");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the background image
        ImageIcon backgroundIcon = new ImageIcon("resources/images/ug.jpg");
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(600, 400, Image.SCALE_SMOOTH);
        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setOpaque(false); // Make the panel transparent
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Locations and landmarks
        String[] locationNames = {
                "Main Entrance Gate",
                "Legon Campus Library",
                "University of Ghana Medical School",
                "Centre for Digital Innovation and Entrepreneurship",
                "University Hostel"
        };
        String[] landmarkNames = {
                "Great Hall",
                "Sports Complex",
                "International House"
        };

        startComboBox = new JComboBox<>(locationNames);
        endComboBox = new JComboBox<>(locationNames);
        landmarkComboBox = new JComboBox<>(landmarkNames);
        findRouteButton = new JButton("Find Route");

        // Customizing components
        startComboBox.setToolTipText("Select the start location");
        endComboBox.setToolTipText("Select the end location");
        landmarkComboBox.setToolTipText("Select landmark for route selection");
        findRouteButton.setBackground(new Color(0x2196F3));
        findRouteButton.setForeground(Color.WHITE);
        findRouteButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Start Location:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(startComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("End Location:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(endComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Landmark:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(landmarkComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(findRouteButton, gbc);

        backgroundLabel.add(panel);

        add(backgroundLabel);

        findRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start = (String) startComboBox.getSelectedItem();
                String end = (String) endComboBox.getSelectedItem();
                String landmark = (String) landmarkComboBox.getSelectedItem();

                Location startLocation = MapUtils.getLocationByName(start);
                Location endLocation = MapUtils.getLocationByName(end);
                Location landmarkLocation = MapUtils.getLandmarkByName(landmark);

                SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() throws Exception {
                        ProgressMonitor progressMonitor = new ProgressMonitor(RouteFinder.this, "Calculating Route...", "", 0, 100);
                        progressMonitor.setProgress(0);

                        List<Location> nearbyLandmarks = MapUtils.getNearbyLandmarks(startLocation);
                        String algorithmUsed = "Dijkstra's Algorithm";
                        String timeComplexity = "O((V + E) log V)";

                        // Simulate computation time
                        for (int i = 0; i <= 100; i += 10) {
                            Thread.sleep(200);
                            progressMonitor.setProgress(i);
                        }

                        List<Location> route = MapUtils.calculateRoute(startLocation, endLocation, landmarkLocation);
                        return buildResultText(start, end, landmark, nearbyLandmarks, algorithmUsed, timeComplexity, route);
                    }

                    @Override
                    protected void done() {
                        try {
                            String result = get();
                            new ResultWindow(start, end, landmark, MapUtils.getNearbyLandmarks(MapUtils.getLocationByName(start)), "Dijkstra's Algorithm", "O((V + E) log V)", MapUtils.calculateRoute(MapUtils.getLocationByName(start), MapUtils.getLocationByName(end), MapUtils.getLandmarkByName(landmark))).setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                worker.execute();
            }
        });

        // Add menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        JMenuItem devCreditsMenuItem = new JMenuItem("Developer Credits");
        JMenuItem usageManualMenuItem = new JMenuItem("Usage Manual");
        JMenuItem copyrightMenuItem = new JMenuItem("Copyright");

        devCreditsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDevTeamsDialog();
            }
        });

        usageManualMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(RouteFinder.this,
                        "Usage Manual:\n" +
                                "1. Select the start location from the dropdown menu.\n" +
                                "2. Select the end location from the dropdown menu.\n" +
                                "3. Optionally, select a landmark if you wish to include it in the route.\n" +
                                "4. Click 'Find Route' to calculate and display the optimal route.",
                        "Usage Manual", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        copyrightMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(RouteFinder.this,
                        "© 2024 UG Navigate. All rights reserved.",
                        "Copyright", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        helpMenu.add(devCreditsMenuItem);
        helpMenu.add(usageManualMenuItem);
        helpMenu.add(copyrightMenuItem);

        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void showDevTeamsDialog() {
        JOptionPane.showMessageDialog(this,
                "11126125 - Issaka Issifu\n" +
                        "11143832 - Bejir.T. Fobil\n" +
                        "11297426 - Aliya Kamal\n" +
                        "11129744 - Ebenezer Baafi\n" +
                        "11014006 - Korletey Christian Kwadjo\n" +
                        "11140508 - Sulemana Abdul-Haafiz Bundana\n" +
                        "11081933 - Agalisi Desmond",
                "Developer Credits", JOptionPane.INFORMATION_MESSAGE);
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

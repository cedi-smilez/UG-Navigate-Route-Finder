/*
    |--------------------------------------------------------------------------
    | Main Class
    |--------------------------------------------------------------------------
    |
    | The Main class is the entry point of the application. It initializes
    | the user interface by creating and displaying the login window.
    | The SwingUtilities.invokeLater method is used to ensure that the
    | UI updates are done on the Event Dispatch Thread, which is the
    | standard practice for Swing applications to ensure thread safety.
    |
    | This class does not contain any business logic but is essential for
    | starting the application and making the login window visible to
    | the user.
    |
    */
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Create and show the login window
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}

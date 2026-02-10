import java.sql.*;
import java.util.Scanner;

public class New {
    private static final String URL = "jdbc:mysql://localhost:3306/LENDEN";
    private static final String USER = "root";
    private static final String PASSWORD = "15062005Ay@";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver not found: " + e.getMessage());
            return;
        }

        try (
                Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
                Scanner scanner = new Scanner(System.in)
        ) {
            // Disable auto-commit for manual transaction handling
            connection.setAutoCommit(false);

            // Queries
            String debitQuery = "UPDATE ACCOUNTS SET BALANCE = BALANCE - ? WHERE ACC = ?";
            String creditQuery = "UPDATE ACCOUNTS SET BALANCE = BALANCE + ? WHERE ACC = ?";

            // Prepared statements
            PreparedStatement debitPreparedStatement = connection.prepareStatement(debitQuery);
            PreparedStatement creditPreparedStatement = connection.prepareStatement(creditQuery);

            System.out.print("Enter amount to transfer: ");
            double amount = scanner.nextDouble();

            int fromAcc = 101;
            int toAcc = 102;

            // Check balance before transaction
            if (isSufficient(connection, fromAcc, amount)) {
                // Set parameters for debit
                debitPreparedStatement.setDouble(1, amount);
                debitPreparedStatement.setInt(2, fromAcc);

                // Set parameters for credit
                creditPreparedStatement.setDouble(1, amount);
                creditPreparedStatement.setInt(2, toAcc);

                // Execute both queries
                int affectedRows1 = debitPreparedStatement.executeUpdate();
                int affectedRows2 = creditPreparedStatement.executeUpdate();

                // Commit only if both are successful
                if (affectedRows1 > 0 && affectedRows2 > 0) {
                    connection.commit();
                    System.out.println("✅ Transaction Successful!");
                } else {
                    connection.rollback();
                    System.out.println("❌ Transaction Failed, rolled back!");
                }

            } else {
                System.out.println("⚠️ Insufficient Balance!");
            }

        } catch (SQLException e) {
            System.out.println("💥 SQL Error: " + e.getMessage());
        }
    }

    // ----------------- BALANCE CHECK METHOD -----------------
    static boolean isSufficient(Connection connection, int ACC, double amount) {
        try {
            String query = "SELECT BALANCE FROM ACCOUNTS WHERE ACC = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, ACC);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double balance = resultSet.getDouble("BALANCE");
                return balance >= amount;
            } else {
                System.out.println("❌ Account not found!");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("💥 SQL Error (Balance Check): " + e.getMessage());
            return false;
        }
    }
}

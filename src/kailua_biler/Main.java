package kailua_biler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Connection connection;

    public static void main(String[] args) throws SQLException {
        MySqlConnection mySqlConnection = new MySqlConnection();
        connection = mySqlConnection.createConnection();

        try {
            displayMenu();
        } finally {
            mySqlConnection.closeConnection();
        }
    }

    private static void displayMenu() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("==== Car Rental Application ====");
            System.out.println("1. Opret bil");
            System.out.println("2. Opret kunde");
            System.out.println("3. Opret Lejekontrakt");
            System.out.println("4. Vis alle biler");
            System.out.println("5. Vis alle kunder");
            System.out.println("6. Vis alle lejekontrakter");
            System.out.println("7. Update bil");
            System.out.println("8. Update kunde");
            System.out.println("9. Update lejekontrakt");
            System.out.println("10. Slet bil");
            System.out.println("11. Slet kunde");
            System.out.println("12. Slet lejekontrakt");
            System.out.println("13. Aflsut");
            System.out.print("skriv dit valg her: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    insertBil();
                    break;
                case 2:
                    insertKunde();
                    break;
                case 3:
                    insertLejekontrakt();
                    break;
                case 4:
                    visAlleBiler();
                    break;
                case 5:
                    visAlleKunder();
                    break;
                case 6:
                    lejekontrakter();
                    break;
                case 7:
                    updateBil();
                    break;
                case 8:
                    updateKunde();
                    break;
                case 9:
                    updateLejekontrakt();
                    break;
                case 10:
                    deleteBil();
                    break;
                case 11:
                    deleteKunde();
                    break;
                case 12:
                    deleteLejekontrakt();
                    break;
                case 13:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 13);

        scanner.close();
    }
    private static void insertBil() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-Opret bil-");

        System.out.print("bil mærke: ");
        String mærke = scanner.nextLine();

        System.out.print("brændstof type: ");
        String brændStofType = scanner.nextLine();

        System.out.print("registration nummer: ");
        String registrationNr = scanner.nextLine();

        System.out.print("registrations dato: ");
        String registrationDato = scanner.nextLine();

        System.out.print("kilometer tal: ");
        int kilometerTal = scanner.nextInt();

        try {
            Bil car = new Bil(mærke, brændStofType, registrationNr, registrationDato, kilometerTal);
            car.saveToDatabase(connection);
            System.out.println("bilen blev tilføjet!");
        } catch (SQLException e) {
            System.out.println("bilen kunne ikke tilføjes: " + e.getMessage());
        }
    }
    private static void deleteBil() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("indtast bil id: ");
        int bilID = scanner.nextInt();

        String query = "DELETE FROM Biler WHERE bil_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, bilID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bil er blivet slettet");
            } else {
                System.out.println("Bil kunne ikke blive fundet");
            }
        }
    }

    private static void updateBil() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Indtast bil id: ");
        int bil_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter updated car details:");
        System.out.print("Mærke: ");
        String mærke = scanner.nextLine();
        System.out.print("brændstof type: ");
        String brændstof_type = scanner.nextLine();
        System.out.print("Registration nr: ");
        String registration_nr = scanner.nextLine();
        System.out.print("Registration Dato: ");
        String første_registration_dato = scanner.nextLine();
        System.out.print("kilometer tal: ");
        int kilometer_tal = scanner.nextInt();
        System.out.println("bil type(luxury,family,sport)");
        String bil_type =scanner.nextLine();

        String query = "UPDATE Biler SET Mærke = ?, brændstof_type = ?, registration_nr = ?, første_registration_dato" +
                " = ?, kilometer_tal = ? , bil_type=?, WHERE bil_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mærke);
            preparedStatement.setString(2, brændstof_type);
            preparedStatement.setString(3, registration_nr);
            preparedStatement.setString(4, første_registration_dato);
            preparedStatement.setInt(5, kilometer_tal);
            preparedStatement.setInt(6, bil_id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bilen blev opdateret.");
            } else {
                System.out.println("bilen blev ikke fundet.");
            }
        }
    }

    private static void insertKunde() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("opret kunde");

        System.out.print("Navn: ");
        String navn = scanner.nextLine();

        System.out.print("adress: ");
        String address = scanner.nextLine();

        System.out.print("postnummer: ");
        String postnummer = scanner.nextLine();

        System.out.print("Enter stad navn: ");
        String stad = scanner.nextLine();

        System.out.print("Enter tlf: ");
        String tlf = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter driver's license number: ");
        String kørekort_nr = scanner.nextLine();

        System.out.print("Enter license issuance date: ");
        String kørekort_udstedelsedato = scanner.nextLine();

        try {
            Kunder customer = new Kunder(navn, address, Integer.parseInt(postnummer), stad, tlf, email, kørekort_nr, kørekort_udstedelsedato);
            customer.saveToDatabase(connection);
            System.out.println("ny kunde oprettet");
        } catch (SQLException e) {
            System.out.println("kunne ikke indtaste kunde: " + e.getMessage());
        }
    }
    private static void updateKunde() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer ID to update: ");
        int kunde_id = scanner.nextInt();
        scanner.nextLine();

        System.out.println("indtast opdateret information:");
        System.out.print("navn: ");
        String name = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("postnummer: ");
        int postnummer = scanner.nextInt();
        scanner.nextLine();
        System.out.print("by: ");
        String stad = scanner.nextLine();
        System.out.print("Telefon nummer: ");
        String tlf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("kørekort nummer: ");
        String kørekort_nr = scanner.nextLine();
        System.out.print("License Issuance Date: ");
        String kørekort_udstedelses_dato = scanner.nextLine();

        String query = "UPDATE Lejere SET navn = ?, adress = ?, postnummer = ?, ByNavn = ?, tlf" +
                " = ?, Email = ?,kørekort_nr = ?, kørekort_udstedelses_dato = ? " +
                "WHERE kunde_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, address);
            preparedStatement.setInt(3, postnummer);
            preparedStatement.setString(4, stad);
            preparedStatement.setString(5, tlf);
            preparedStatement.setString(6, email);
            preparedStatement.setString(7, kørekort_nr);
            preparedStatement.setString(8, kørekort_udstedelses_dato);
            preparedStatement.setInt(9, kunde_id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Kunde er blevet updateret.");
            } else {
                System.out.println("kunde kunne ikke blive fundet.");
            }
        }
    }

    private static void deleteKunde() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("indtast kunde id: ");
        int kunde_id = scanner.nextInt();

        String query = "DELETE FROM Kunder WHERE kunde_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, kunde_id);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Customer deleted successfully.");
            } else {
                System.out.println("Customer not found or deletion failed.");
            }
        }
    }


    private static void insertLejekontrakt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert lejekontrakt");

        System.out.print("indtast kunde id: ");
        int kundeID = scanner.nextInt();

        System.out.print("indtast bil id: ");
        int bilID = scanner.nextInt();

        scanner.nextLine();

        System.out.print("indtast start dato: ");
        String startDato = scanner.nextLine();

        System.out.print("indtast slut dato: ");
        String slutDato = scanner.nextLine();

        System.out.print("indtast max kilometer tal: ");
        int maxKM = scanner.nextInt();

        System.out.print("indtast nuværende kilometertal: ");
        int startKm = scanner.nextInt();

        try {
            Lejekontrakt rental = new Lejekontrakt(kundeID, bilID, startDato, slutDato, maxKM, startKm);
            rental.saveToDatabase(connection);
            System.out.println("lejekontrakt tilføjet!");
        } catch (SQLException e) {
            System.out.println("fejl kunne ikke tilføke lejekontrakt: " + e.getMessage());
        }
    }
    private static void updateLejekontrakt() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("indtast lejekontrakt id: ");
        int lejekontraktID = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter updated rental details:");
        System.out.print("Customer ID: ");
        int kundeID = scanner.nextInt();
        System.out.print("Car ID: ");
        int bilID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Start Date and Time: ");
        String startDato = scanner.nextLine();
        System.out.print("End Date and Time: ");
        String slutDato = scanner.nextLine();
        System.out.print("Max Kilometers: ");
        int maxKm = scanner.nextInt();
        System.out.print("Starting Kilometers: ");
        int startKm = scanner.nextInt();

        String query = "UPDATE Lejekontrakter SET LejerID = ?, BilID = ?, FraDatoTid = ?, TilDatoTid = ?, " +
                "MaxKilometer = ?, StartKilometer = ? WHERE KontraktID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, kundeID);
            preparedStatement.setInt(2, bilID);
            preparedStatement.setString(3, startDato);
            preparedStatement.setString(4, slutDato);
            preparedStatement.setInt(5, maxKm);
            preparedStatement.setInt(6, startKm);
            preparedStatement.setInt(7, lejekontraktID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("lejekontrakt updated.");
            } else {
                System.out.println("kunne ikke finde lejekontrakt.");
            }
        }
    }

    private static void deleteLejekontrakt() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("indtast lejekontrakt id: ");
        int lejekontraktID = scanner.nextInt();

        String query = "DELETE FROM Lejekontrakter WHERE KontraktID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, lejekontraktID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("lejekontrakten blev slettet.");
            } else {
                System.out.println("kunne ikke finde lejekontrakt.");
            }
        }
    }


    private static void visAlleBiler() {
        try {
            List<Bil> biler = Bil.getAllBiler(connection);
            System.out.println("==== All Cars ====");
            for (Bil bil : biler) {
                System.out.println(bil);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving cars: " + e.getMessage());
        }
    }

    private static void visAlleKunder() {
        try {
            List<Kunder> kunder = Kunder.getAllkunder(connection);
            System.out.println("All kunder");
            for (Kunder kunder1 : kunder) {
                System.out.println(kunder1);
            }
        } catch (Exception e) {
            System.out.println("Fejl ved at finde kunder ");
        }
    }

    private static void lejekontrakter() {
        try {
            List<Lejekontrakt> lejekontrakter = Lejekontrakt.getAllLejekontrakter(connection);
            System.out.println("All lejekontrakter");
            for (Lejekontrakt lejekontrakt : lejekontrakter) {
                System.out.println(lejekontrakt);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rentals: " + e.getMessage());
        }
    }

}
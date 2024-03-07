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
                    insertCar();
                    break;
                case 2:
                    insertCustomer();
                    break;
                case 3:
                    insertRental();
                    break;
                case 4:
                    displayAllCars();
                    break;
                case 5:
                    displayAllCustomers();
                    break;
                case 6:
                    displayAllRentals();
                    break;
                case 7:
                    updateCar();
                    break;
                case 8:
                    updaterKunder();
                    break;
                case 9:
                    updateRental();
                    break;
                case 10:
                    deleteCar();
                    break;
                case 11:
                    deleteCustomer();
                    break;
                case 12:
                    deleteRental();
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
    private static void insertCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("==== Insert Car ====");

        System.out.print("Enter car brand and model: ");
        String brandModel = scanner.nextLine();

        System.out.print("Enter fuel type: ");
        String fuelType = scanner.nextLine();

        System.out.print("Enter registration number: ");
        String regNumber = scanner.nextLine();

        System.out.print("Enter registration date: ");
        String regDate = scanner.nextLine();

        System.out.print("Enter vehicle kilometers: ");
        int kilometers = scanner.nextInt();

        try {
            Bil car = new Bil(brandModel, fuelType, regNumber, regDate, kilometers);
            car.saveToDatabase(connection);
            System.out.println("Car successfully inserted!");
        } catch (SQLException e) {
            System.out.println("Error inserting car: " + e.getMessage());
        }
    }
    private static void deleteCar() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Car ID to delete: ");
        int carID = scanner.nextInt();

        String query = "DELETE FROM Biler WHERE bil_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, carID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Bil er blivet slettet");
            } else {
                System.out.println("Bil kunne ikke blive fundet");
            }
        }
    }

    private static void updateCar() throws SQLException {
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

    private static void insertCustomer() {
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
    private static void updaterKunder() throws SQLException {
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
        scanner.nextLine();  // Consume the newline character
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

    private static void deleteCustomer() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Customer ID to delete: ");
        int kunde_id = scanner.nextInt();

        String query = "DELETE FROM Lejere WHERE kunde_id = ?";

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


    private static void insertRental() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert Rental");

        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();

        System.out.print("Enter car ID: ");
        int carId = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter rental start date and time: ");
        String startDate = scanner.nextLine();

        System.out.print("Enter rental end date and time: ");
        String endDate = scanner.nextLine();

        System.out.print("Enter max kilometers allowed: ");
        int maxKilometers = scanner.nextInt();

        System.out.print("Enter starting kilometers: ");
        int startKilometers = scanner.nextInt();

        try {
            Lejekontrakt rental = new Lejekontrakt(customerId, carId, startDate, endDate, maxKilometers, startKilometers);
            rental.saveToDatabase(connection);
            System.out.println("Rental successfully inserted!");
        } catch (SQLException e) {
            System.out.println("Error inserting rental: " + e.getMessage());
        }
    }
    private static void updateRental() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Rental ID to update: ");
        int rentalID = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        System.out.println("Enter updated rental details:");
        System.out.print("Customer ID: ");
        int customerID = scanner.nextInt();
        System.out.print("Car ID: ");
        int carID = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Start Date and Time: ");
        String startDate = scanner.nextLine();
        System.out.print("End Date and Time: ");
        String endDate = scanner.nextLine();
        System.out.print("Max Kilometers: ");
        int maxKilometers = scanner.nextInt();
        System.out.print("Starting Kilometers: ");
        int startKilometers = scanner.nextInt();

        String query = "UPDATE Lejekontrakter SET LejerID = ?, BilID = ?, FraDatoTid = ?, TilDatoTid = ?, " +
                "MaxKilometer = ?, StartKilometer = ? WHERE KontraktID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            preparedStatement.setInt(2, carID);
            preparedStatement.setString(3, startDate);
            preparedStatement.setString(4, endDate);
            preparedStatement.setInt(5, maxKilometers);
            preparedStatement.setInt(6, startKilometers);
            preparedStatement.setInt(7, rentalID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Rental updated successfully.");
            } else {
                System.out.println("Rental not found or update failed.");
            }
        }
    }

    private static void deleteRental() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Rental ID to delete: ");
        int rentalID = scanner.nextInt();

        String query = "DELETE FROM Lejekontrakter WHERE KontraktID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, rentalID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Rental deleted successfully.");
            } else {
                System.out.println("Rental not found or deletion failed.");
            }
        }
    }


    private static void displayAllCars() {
        try {
            List<Bil> cars = Bil.getAllBiler(connection);
            System.out.println("==== All Cars ====");
            for (Bil car : cars) {
                System.out.println(car);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving cars: " + e.getMessage());
        }
    }

    private void displayAllCustomers() {
        try {
            List<Kunder> kunder = Kunder.getAllkunder(connection);
            System.out.println("==== All Customers ====");
            for (Kunder customer : kunder) {
                System.out.println(customer);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }

    private static void displayAllRentals() {
        try {
            List<Lejekontrakt> rentals = Lejekontrakt.getAllLejekontrakter(connection);
            System.out.println("==== All Rentals ====");
            for (Lejekontrakt rental : rentals) {
                System.out.println(rental);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rentals: " + e.getMessage());
        }
    }

}
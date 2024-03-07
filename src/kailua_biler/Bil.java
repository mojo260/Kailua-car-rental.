package kailua_biler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Bil {
    private int bil_id;
    private String mærke;
    private String brændstof_type;
    private String registration_nr;
    private String første_registration_dato;
    private int kilometer_tal;

    public Bil() {
    }

    public Bil(String mærke, String brændstof_type, String registration_nr, String første_registration_dato, int kilometer_tal) {
        this.mærke = mærke;
        this.brændstof_type = brændstof_type;
        this.registration_nr = registration_nr;
        this.første_registration_dato = første_registration_dato;
        this.kilometer_tal = kilometer_tal;
    }

    public int getBil_id() {
        return bil_id;
    }

    public void setBil_id(int bil_id) {
        this.bil_id = bil_id;
    }

    public String getMærke() {
        return mærke;
    }

    public void setMærke(String mærke) {
        this.mærke = mærke;
    }

    public String getBrændstof_type() {
        return brændstof_type;
    }

    public void setBrændstof_type(String brændstof_type) {
        this.brændstof_type = brændstof_type;
    }

    public String getRegistration_nr() {
        return registration_nr;
    }

    public void setRegistration_nr(String registration_nr) {
        this.registration_nr = registration_nr;
    }

    public String getFørste_registration_dato() {
        return første_registration_dato;
    }

    public void setFørste_registration_dato(String første_registration_dato) {
        this.første_registration_dato = første_registration_dato;
    }

    public int getKilometer_tal() {
        return kilometer_tal;
    }

    public void setKilometer_tal(int kilometer_tal) {
        this.kilometer_tal = kilometer_tal;
    }

    // Andre metoder

    public void saveToDatabase(Connection connection) throws SQLException {
        String query = "INSERT INTO Biler (MaerkeModel, BraendstofType, Registreringsnummer, RegistreringDATO, KoeretoejKilometer) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, mærke);
            preparedStatement.setString(2, brændstof_type);
            preparedStatement.setString(3, registration_nr);
            preparedStatement.setString(4, første_registration_dato);
            preparedStatement.setInt(5, kilometer_tal);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bil_id = generatedKeys.getInt(1);
            }
        }
    }

    public static List<Bil> getAllBiler(Connection connection) throws SQLException {
        List<Bil> biler = new ArrayList<>();
        String query = "SELECT * FROM Biler";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Bil bil = new Bil();
                bil.setBil_id(resultSet.getInt("BilID"));
                bil.setMærke(resultSet.getString("MaerkeModel"));
                bil.setBrændstof_type(resultSet.getString("BraendstofType"));
                bil.setRegistration_nr(resultSet.getString("Registreringsnummer"));
                bil.setFørste_registration_dato(resultSet.getString("RegistreringDATO"));
                bil.setKilometer_tal(resultSet.getInt("KoeretoejKilometer"));
                biler.add(bil);
            }
        }
        return biler;
    }

    @Override
    public String toString() {
        return "Bil{" +
                "bilID=" + bil_id +
                ", maerkeModel='" + mærke + '\'' +
                ", braendstofType='" + brændstof_type + '\'' +
                ", registreringsnummer='" + registration_nr + '\'' +
                ", registreringDATO='" + første_registration_dato + '\'' +
                ", koeretoejKilometer=" + kilometer_tal +
                '}';
    }
}

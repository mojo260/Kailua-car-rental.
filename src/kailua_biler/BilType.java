package kailua_biler;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BilType {
    private int gruppeID;
    private String bil_type;

    public BilType() {
    }

    public BilType(String bil_type) {
        this.bil_type = bil_type;
    }

    public int getGruppeID() {
        return gruppeID;
    }

    public void setGruppeID(int gruppeID) {
        this.gruppeID = gruppeID;
    }

    public String getBil_type() {
        return bil_type;
    }

    public void setBil_type(String bil_type) {
        this.bil_type = bil_type;
    }

    public void saveToDatabase(Connection connection) throws SQLException {
        String query = "INSERT INTO BilGrupper (GruppeNavn) VALUES (?) ON DUPLICATE KEY UPDATE GruppeNavn=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, bil_type);
            preparedStatement.setString(2, bil_type); // Tilføjet parameter til UPDATE-sætningen
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                gruppeID = generatedKeys.getInt(1);
            }
        }
    }

    public static List<BilType> getAllBilGrupper(Connection connection) throws SQLException {
        List<BilType> bilGrupper = new ArrayList<>();
        String query = "SELECT * FROM BilGrupper";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BilType bilType = new BilType();
                bilType.setGruppeID(resultSet.getInt("GruppeID"));
                bilType.setBil_type(resultSet.getString("GruppeNavn"));
                bilGrupper.add(bilType);
            }
        }
        return bilGrupper;
    }

    @Override
    public String toString() {
        return "BilGruppe{" +
                "gruppeID=" + gruppeID +
                ", gruppeNavn='" + bil_type + '\'' +
                '}';
    }
}

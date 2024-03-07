package kailua_biler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PostNummer {
    private int postnummerID;
    private String postnummer;

    public PostNummer() {
    }

    public PostNummer(String postnummer) {
        this.postnummer = postnummer;
    }

    public int getPostnummerID() {
        return postnummerID;
    }

    public void setPostnummerID(int postnummerID) {
        this.postnummerID = postnummerID;
    }

    public String getPostnummer() {
        return postnummer;
    }

    public void setPostnummer(String postnummer) {
        this.postnummer = postnummer;
    }

    public void saveToDatabase(Connection connection) throws SQLException {
        String query = "INSERT INTO Postnumre (Postnummer) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, postnummer);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                postnummerID = generatedKeys.getInt(1);
            }
        }
    }

    public static List<PostNummer> getAllPostnumre(Connection connection) throws SQLException {
        List<PostNummer> postnumre = new ArrayList<>();
        String query = "SELECT * FROM Postnumre";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PostNummer postnummer = new PostNummer();
                postnummer.setPostnummerID(resultSet.getInt("PostnummerID"));
                postnummer.setPostnummer(resultSet.getString("Postnummer"));
                postnumre.add(postnummer);
            }
        }
        return postnumre;
    }

    @Override
    public String toString() {
        return "Postnummer{" +
                "postnummerID=" + postnummerID +
                ", postnummer='" + postnummer + '\'' +
                '}';
    }
}
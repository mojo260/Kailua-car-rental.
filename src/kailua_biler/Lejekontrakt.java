package kailua_biler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class Lejekontrakt {
    private int leje_kontrakt_id;
    private int kunde_id;
    private int bil_id;
    private String fra_dato_tid;
    private String til_dato_tid;
    private int max_km;
    private int km_tæller;

    public Lejekontrakt() {
    }

    public Lejekontrakt(int kunde_id, int bil_id, String fra_dato_tid, String til_dato_tid, int max_km, int km_tæller) {
        this.kunde_id = kunde_id;
        this.bil_id = bil_id;
        this.fra_dato_tid = fra_dato_tid;
        this.til_dato_tid = til_dato_tid;
        this.max_km = max_km;
        this.km_tæller = km_tæller;
    }

    public int getLeje_kontrakt_id() {
        return leje_kontrakt_id;
    }

    public void setLeje_kontrakt_id(int leje_kontrakt_id) {
        this.leje_kontrakt_id = leje_kontrakt_id;
    }

    public int getKunde_id() {
        return kunde_id;
    }

    public void setKunde_id(int kunde_id) {
        this.kunde_id = kunde_id;
    }

    public int getBil_id() {
        return bil_id;
    }

    public void setBil_id(int bil_id) {
        this.bil_id = bil_id;
    }

    public String getFra_dato_tid() {
        return fra_dato_tid;
    }

    public void setFra_dato_tid(String fra_dato_tid) {
        this.fra_dato_tid = fra_dato_tid;
    }

    public String getTil_dato_tid() {
        return til_dato_tid;
    }

    public void setTil_dato_tid(String til_dato_tid) {
        this.til_dato_tid = til_dato_tid;
    }

    public int getMax_km() {
        return max_km;
    }

    public void setMax_km(int max_km) {
        this.max_km = max_km;
    }

    public int getKm_tæller() {
        return km_tæller;
    }

    public void setKm_tæller(int km_tæller) {
        this.km_tæller = km_tæller;
    }

    public void saveToDatabase(Connection connection) throws SQLException {
        String query = "INSERT INTO Lejekontrakter (LejerID, BilID, FraDatoTid, TilDatoTid, MaxKilometer, StartKilometer) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, kunde_id);
            preparedStatement.setInt(2, bil_id);
            preparedStatement.setString(3, fra_dato_tid);
            preparedStatement.setString(4, til_dato_tid);
            preparedStatement.setInt(5, max_km);
            preparedStatement.setInt(6, km_tæller);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                leje_kontrakt_id = generatedKeys.getInt(1);
            }
        }
    }

    public static List<Lejekontrakt> getAllLejekontrakter(Connection connection) throws SQLException {
        List<Lejekontrakt> lejekontrakter = new ArrayList<>();
        String query = "SELECT * FROM Lejekontrakter";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Lejekontrakt lejekontrakt = new Lejekontrakt();
                lejekontrakt.setLeje_kontrakt_id(resultSet.getInt("KontraktID"));
                lejekontrakt.setKunde_id(resultSet.getInt("LejerID"));
                lejekontrakt.setBil_id(resultSet.getInt("BilID"));
                lejekontrakt.setFra_dato_tid(resultSet.getString("FraDatoTid"));
                lejekontrakt.setTil_dato_tid(resultSet.getString("TilDatoTid"));
                lejekontrakt.setMax_km(resultSet.getInt("MaxKilometer"));
                lejekontrakt.setKm_tæller(resultSet.getInt("StartKilometer"));
                lejekontrakter.add(lejekontrakt);
            }
        }
        return lejekontrakter;
    }

    @Override
    public String toString() {
        return "Lejekontrakt{" +
                "kontraktID=" + leje_kontrakt_id +
                ", lejerID=" + kunde_id +
                ", bilID=" + bil_id +
                ", fraDatoTid='" + fra_dato_tid + '\'' +
                ", tilDatoTid='" + til_dato_tid + '\'' +
                ", maxKilometer=" + max_km +
                ", startKilometer=" + km_tæller +
                '}';
    }
}
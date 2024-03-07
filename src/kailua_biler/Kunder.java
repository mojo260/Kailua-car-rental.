package kailua_biler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Kunder {
    private int lejer_kontrakt_id;
    private static String navn;
    private static String address;
    private int postnummer;
    private String stad;
    private String mobiltelefon;
    private String tlf;
    private String email;
    private String kørekort_nr;
    private String kørekort_udstedelsesdato;

    public Kunder(int lejer_kontrakt_id,String navn, String address, int i, String stad, String tlf, String email, String kørekort_nr, String kørekort_udstedelsedato) {
        this.lejer_kontrakt_id = lejer_kontrakt_id;
        this.navn = navn;
        this.address = address;
        this.postnummer = postnummer;
        this.stad = stad;
        this.tlf = tlf;
        this.email = email;
        this.kørekort_nr = kørekort_nr;
        this.kørekort_udstedelsesdato = kørekort_udstedelsesdato;
    }

    public Kunder(String navn, String address, int postnummer, String stad, String tlf, String email, String kørekort_nr, String kørekort_udstedelsesdato) {
        this.navn = navn;
        this.address = address;
        this.postnummer = postnummer;
        this.stad = stad;
        this.tlf = tlf;
        this.email = email;
        this.kørekort_nr = kørekort_nr;
        this.kørekort_udstedelsesdato = kørekort_udstedelsesdato;
    }

    public int getleje_kontrakt_id() {
        return lejer_kontrakt_id;
    }

    public void setLejer_kontrakt_id(int lejer_kontrakt_id) {
        this.lejer_kontrakt_id = lejer_kontrakt_id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostnummer() {
        return postnummer;
    }

    public void setPostnummer(int postnummer) {
        this.postnummer = postnummer;
    }

    public String getStad() {
        return stad;
    }

    public void setStad(String stad) {
        this.stad = stad;
    }

    public String getMobiltelefon() {
        return mobiltelefon;
    }

    public void setMobiltelefon(String mobiltelefon) {
        this.mobiltelefon = mobiltelefon;
    }

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKørekort_nr() {
        return kørekort_nr;
    }

    public void setKørekort_nr(String kørekort_nr) {
        this.kørekort_nr = kørekort_nr;
    }

    public String getKørekort_udstedelsesdato() {
        return kørekort_udstedelsesdato;
    }

    public void setKørekort_udstedelsesdato(String kørekort_udstedelsesdato) {
        this.kørekort_udstedelsesdato = kørekort_udstedelsesdato;
    }

    public void saveToDatabase(Connection connection) throws SQLException {
        String query = "INSERT INTO Lejere (Navn, Adresse, PostnummerID, ByNavn, Mobiltelefon, Telefon, Email, KoerekortNummer, KoerekortUdstedelsesdato) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, navn);
            preparedStatement.setString(2, address);
            preparedStatement.setInt(3, postnummer);
            preparedStatement.setString(4, stad);
            preparedStatement.setString(5, mobiltelefon);
            preparedStatement.setString(6, tlf);
            preparedStatement.setString(7, email);
            preparedStatement.setString(8, kørekort_nr);
            preparedStatement.setString(9, kørekort_udstedelsesdato);
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                lejer_kontrakt_id = generatedKeys.getInt(1);
            }
        }
    }

    public List<Kunder> getAllLejere(Connection connection) throws SQLException {
        List<Kunder> lejere = new ArrayList<>();
        String query = "SELECT * FROM Lejere";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Kunder kunder = new Kunder(navn, address, Integer.parseInt(String.valueOf(postnummer)), stad, tlf, email, kørekort_nr, kørekort_udstedelsesdato);
                kunder.setLejer_kontrakt_id(resultSet.getInt("LejerID"));
                kunder.setNavn(resultSet.getString("Navn"));
                kunder.setAddress(resultSet.getString("Adresse"));
                kunder.setPostnummer(resultSet.getInt("PostnummerID"));
                kunder.setStad(resultSet.getString("ByNavn"));
                kunder.setMobiltelefon(resultSet.getString("Mobiltelefon"));
                kunder.setTlf(resultSet.getString("Telefon"));
                kunder.setEmail(resultSet.getString("Email"));
                kunder.setKørekort_nr(resultSet.getString("KoerekortNummer"));
                kunder.setKørekort_udstedelsesdato(resultSet.getString("KoerekortUdstedelsesdato"));
                lejere.add(kunder);
            }
        }
        return lejere;
    }

    @Override
    public String toString() {
        return "Lejer{" +
                "lejerID=" + lejer_kontrakt_id +
                ", navn='" + navn + '\'' +
                ", adresse='" + address + '\'' +
                ", postnummerID=" + postnummer +
                ", byNavn='" + stad + '\'' +
                ", mobiltelefon='" + mobiltelefon + '\'' +
                ", telefon='" + tlf + '\'' +
                ", email='" + email + '\'' +
                ", koerekortNummer='" + kørekort_nr + '\'' +
                ", koerekortUdstedelsesdato='" + kørekort_udstedelsesdato + '\'' +
                '}';
    }
}
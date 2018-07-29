package ProductMgmtGUI.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

//clasa de tip singleton - se poate creea o singura instanta
public class DBconnector {

    private static DBconnector instance = null;

    private static final String URL = "jdbc:mysql://localhost:3306/productie?allowPublicKeyRetrieval=true&useSSL=false&useJDBCCompliantTimezoneShift=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "RootAcces" ;

    public static final String TABLE_PRODUS = "produs";
    public static final String COLUMN_PRODUS_ID = "idProdus";
    public static final String COLUMN_NUME_PRODUS = "Nume";

    public static final String TABLE_SERII = "serii";
    public static final String COLUMN_SERII_ID = "idserii";
    public static final String COLUMN_NUMAR_SERIE = "NumarSerie";
    public static final String COLUMN_ID_PRODUS = "idProdus";

    public static final String TABLE_TIMPI = "timpi";
    public static final String COLUMN_ID_TIMPI = "idtimpi";
    public static final String COLUMN_DIVIZARE_INCEPUT = "Divizare_inceput";
    public static final String COLUMN_DIVIZARE_FINAL = "Divizare_final";
    public static final String COLUMN_PREPARARE_INCEPUT = "Preparare_inceput";
    public static final String COLUMN_PREPARARE_SFARSIT = "Preparare_sfarsit";
    public static final String COLUMN_FILTRARE_INCEPUT = "Filtrare_inceput";
    public static final String COLUMN_FILTRARE_SFARSIT = "Filtrare_sfarsit";
    public static final String COLUMN_UMPLERE_INCEPUT = "Umplere_inceput";
    public static final String COLUMN_UMPLERE_SFARSIT = "Umplere_final";
    public static final String COLUMN_STERILIZAT = "Sterilizat";
    public static final String COLUMN_STERILIZARE_INCEPUT = "Sterilizare_inceput";
    public static final String COLUMN_STERILIZARE_FINAL = "Sterilizare_final";
    public static final String COLUMN_CONTROL_MACROSCOPIC_INCEPUT = "Control_macroscopic_inceput";
    public static final String COLUMN_CONTROL_MACROSCOPIC_FINAL = "Control_macroscopic_final";
    public static final String COLUMN_AMBALARE_INCEPUT = "Ambalare_inceput";
    public static final String COLUMN_AMBALARE_FINAL = "Ambalare_final";
    public static final String COLUMN_ID_SERIE = "idSerie";

    public static final String INSERT_PRODUS = "INSERT INTO " + TABLE_PRODUS + '(' + COLUMN_NUME_PRODUS + ") VALUE( ? )";
    public static final String INSERT_SERIE = "INSERT INTO " + TABLE_SERII + '(' + COLUMN_NUMAR_SERIE + ',' + COLUMN_ID_PRODUS + ") VALUES (?,?)";
    public static final String MODIF_PRODUS = "UPDATE " + TABLE_PRODUS + " SET " + COLUMN_NUME_PRODUS + '=' + "? WHERE " + COLUMN_PRODUS_ID + '=' + '?';
    public static final String DELETE_PRODUS = "DELETE FROM " + TABLE_PRODUS + " WHERE " + COLUMN_PRODUS_ID + " = ?";
    public static final String DELETE_SERIE = "DELETE FROM " + TABLE_SERII + " WHERE " + COLUMN_SERII_ID + " = ?";
    public static final String NEW_TIMPI = "INSERT INTO " + TABLE_TIMPI + '(' + COLUMN_ID_SERIE + ") VALUE (?)";

    private PreparedStatement insertIntoProdus;
    private PreparedStatement insertIntoSerii;
    private PreparedStatement modifProdus;
    private PreparedStatement deleteProdus;
    private PreparedStatement deleteSerie;
    private PreparedStatement newTimp;
    private Connection myCon;

    //metoda de instantiere
    public static DBconnector getInstance() {

        if(instance == null ){
            instance = new DBconnector();
        }
        return instance;
    }

    //constructor private, inaccesibil altor clase;
    private DBconnector() {};


    public boolean open () {

        try {
            myCon = DriverManager.getConnection(URL, USER, PASSWORD);
            insertIntoProdus =  myCon.prepareStatement(INSERT_PRODUS, Statement.RETURN_GENERATED_KEYS);
            insertIntoSerii = myCon.prepareStatement(INSERT_SERIE, Statement.RETURN_GENERATED_KEYS);
            modifProdus = myCon.prepareStatement(MODIF_PRODUS);
            deleteProdus = myCon.prepareStatement(DELETE_PRODUS);
            deleteSerie = myCon.prepareStatement(DELETE_SERIE);
            newTimp = myCon.prepareStatement(NEW_TIMPI);
            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database" + e.getMessage());
            return false;
        }
    }

    public void close () {
        try {

            if (myCon != null) {
                myCon.close();
            }

            if (insertIntoProdus != null) {
                insertIntoProdus.close();
            }

            if (modifProdus != null) {
                modifProdus.close();
            }

            if (deleteProdus != null) {
                deleteProdus.close();
            }

            if (insertIntoSerii != null) {
                insertIntoSerii.close();
            }

            if (deleteSerie != null) {
                deleteSerie.close();
            }

            if (newTimp != null) {
                newTimp.close();
            }

        }catch (SQLException e) {
            System.out.println("Couldn't close connection " + e.getMessage());
        }
    }

    public ObservableList<Produs> queryProduseObservList() {

        try (Statement mySmt = myCon.createStatement();
             ResultSet myRs = mySmt.executeQuery("SELECT * FROM " + TABLE_PRODUS)){

            ObservableList<Produs> produse = FXCollections.observableArrayList();

            while(myRs.next()) {
                Produs produs = new Produs();
                produs.setId(myRs.getInt(COLUMN_PRODUS_ID));
                produs.setName(myRs.getString(COLUMN_NUME_PRODUS));
                produse.add(produs);
            }
            return produse;
        }
        catch (SQLException e) {
            System.out.println("Failed to query produse");
            e.printStackTrace();
            close();
            return null;
        }
    }

    public ArrayList<Produs> queryProduse() {

        try(Statement myStmt = myCon.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM " + TABLE_PRODUS)) {

            ArrayList<Produs> produse = new ArrayList<>();
            while (myRs.next()){
                Produs produs = new Produs();
                produs.setId(myRs.getInt(COLUMN_PRODUS_ID));
                produs.setName(myRs.getString(COLUMN_NUME_PRODUS));
                produse.add(produs);
            }
            return produse;
        }catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public ObservableList<Serii> querrySerii() {

        try(Statement myStmt = myCon.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM " + TABLE_SERII)) {

            ObservableList<Serii> serii = FXCollections.observableArrayList();
            while (myRs.next()){
                Serii serie = new Serii();
                serie.setIdSerii(myRs.getInt(COLUMN_SERII_ID));
                serie.setIdProdus(myRs.getInt(COLUMN_PRODUS_ID));
                serie.setNumarSerie(myRs.getString(COLUMN_NUMAR_SERIE));
                serii.add(serie);
            }
            return serii;
        }catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }

    public ArrayList<Timpi> querryTimpi() {

        try(Statement myStmt = myCon.createStatement();
            ResultSet myRs = myStmt.executeQuery("SELECT * FROM " + TABLE_TIMPI)) {

            ArrayList<Timpi> timpi = new ArrayList<>();
            while (myRs.next()){
                Timpi timp = new Timpi();
                timp.setIdtimpi(myRs.getInt(COLUMN_ID_TIMPI));
                timp.setDivizareInceput(myRs.getTimestamp(COLUMN_DIVIZARE_INCEPUT));
                timp.setDivizareFinal(myRs.getTimestamp(COLUMN_DIVIZARE_FINAL));
                timp.setPreparareInceput(myRs.getTimestamp(COLUMN_PREPARARE_INCEPUT));
                timp.setPreparareSfarsit(myRs.getTimestamp(COLUMN_PREPARARE_SFARSIT));
                timp.setFiltrareInceput(myRs.getTimestamp(COLUMN_FILTRARE_INCEPUT));
                timp.setFiltrareSfarsit(myRs.getTimestamp(COLUMN_FILTRARE_SFARSIT));
                timp.setUmplereInceput(myRs.getTimestamp(COLUMN_UMPLERE_INCEPUT));
                timp.setUmplereFinal(myRs.getTimestamp(COLUMN_UMPLERE_SFARSIT));
                timp.setSterilizareInceput(myRs.getTimestamp(COLUMN_STERILIZARE_INCEPUT));
                timp.setSterilizareFinal(myRs.getTimestamp(COLUMN_STERILIZARE_FINAL));
                timp.setControlMacroscopicInceput(myRs.getTimestamp(COLUMN_CONTROL_MACROSCOPIC_INCEPUT));
                timp.setControlMacroscopicFinal(myRs.getTimestamp(COLUMN_CONTROL_MACROSCOPIC_FINAL));
                timp.setAmbalareInceput(myRs.getTimestamp(COLUMN_AMBALARE_INCEPUT));
                timp.setAmbalareFinal(myRs.getTimestamp(COLUMN_AMBALARE_FINAL));
                timp.setIdSerie(myRs.getInt(COLUMN_ID_SERIE));
                timpi.add(timp);
            }
            return timpi;
        }catch (SQLException e){
            System.out.println("Query failed " + e.getMessage());
            return null;
        }
    }


    public int insertProdus (String name) throws SQLException {

        int idProdus = 0;
        ArrayList<Produs> produse = queryProduse();
        for(Produs produs:produse) {
            if (name.equals(produs.getName())) {
                System.out.println("Produsul este deja inregistrat in baza de date");
                return 0;
            }
        }

        insertIntoProdus.setString(1, name);
        int affectedRows = insertIntoProdus.executeUpdate(); // metoda care returneaza 1 daca a fost adaugat un rand si 0 daca nu, executa
        // prepared statement-ul

        if(affectedRows != 1) {                                 // verifica daca a avut loc modificarea
            throw new SQLException("Nu s-a putut introduce produsul");
        }

        ResultSet generatedKeys = insertIntoProdus.getGeneratedKeys();
        if(generatedKeys.next()) {
            idProdus = generatedKeys.getInt(1);
        }
        else {
            throw new SQLException("Nu s-a putut obtine ID-ul produsului");
        }

        return idProdus;
    }

    public void insertSerie (String numarSerie, int idProdus) throws SQLException {
        int idSerie = 0;

        ObservableList<Serii> serii = querrySerii();
        for(Serii serie:serii){
            if(numarSerie.equals(serie.getNumarSerie())){
                System.out.println("Seria este deja inregistrata in baza de date");
                return;
            }
        }

        insertIntoSerii.setString(1, numarSerie);
        insertIntoSerii.setInt(2, idProdus);

        int affectedRows = insertIntoSerii.executeUpdate();

        ResultSet generatedKeys = insertIntoSerii.getGeneratedKeys();
        if(generatedKeys.next()) {
            idSerie = generatedKeys.getInt(1);
        }
        System.out.println(idSerie);
        newTimp.setInt(1, idSerie);
        int affectedRowsTimpi = newTimp.executeUpdate();

        if(affectedRows != 1 ){
            throw new SQLException("Nu s-a putut inregistra seria");
        }

        if(affectedRowsTimpi != 1) {
            throw  new SQLException("Nu s-a creeat timp nou");
        }
    }

    public void modificaProdus(String nume, int idProdus) throws SQLException {

        ObservableList<Produs> produse = queryProduseObservList();
        for(Produs produs:produse){
            if(nume.equals(produs.getName())){
                System.out.println("Produsul exista deja in baza de date");
                return;
            }
        }

        modifProdus.setString(1, nume);
        modifProdus.setInt(2, idProdus);

        int affectedRows = modifProdus.executeUpdate();

        if(affectedRows != 1) {
            throw new SQLException("Nu s-a putut inregistra modificarea");
        }
    }

    public void deleteProdus (int idProdus) throws SQLException {


        deleteProdus.setInt(1, idProdus);

        int affectedRows = deleteProdus.executeUpdate();

        if(affectedRows != 1) {
            throw new SQLException("Nu s-a putut sterge produsul");
        }

    }

    public void deleteSerie (int idSerie) throws SQLException {

        deleteSerie.setInt(1, idSerie);

        int affectedRows = deleteSerie.executeUpdate();

        if(affectedRows != 1) {
            throw new SQLException("Nu s-a putut sterge seria");
        }
    }

    //metoda care primeste string-ul de la strinStergeTimp sau stringInsertTimp si il executa in SQL
    public void executeQuerryTimpi(String query) throws SQLException {

        Statement myStmt = myCon.createStatement();
        int affectedRows = myStmt.executeUpdate(query);

        if(affectedRows != 1) {
         throw new SQLException("Nu s-a putut executa querry-ul");
        }
    }


//     metoda creeza un SQL statement sub forma de string pentru a fi executat de catre metoda de executie,
//    a fost folosit un string in loc de preparedStatement pentru a putea pasa nume de tabel intr-in mod dinamic
    public String stringStergeTimp(int etapa, int idSerie) throws SQLException  {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE " + TABLE_TIMPI + " SET ");

        sb.append(obtineColoanaTimp(etapa));

        sb.append(" = NULL WHERE " + COLUMN_ID_SERIE + " = ");

        sb.append(idSerie);

        System.out.println(sb.toString());
        return sb.toString();
    }

    //metoda care preia datele din InsertTimesController si creeaza un query care e pasata la executeTimpQuerry
//    string in loc de preparedStatement pentru a selecta dincamic numele coloanei din tabel
    public String stringInsertTimp(int etapa, int idSerie,String data, String ora, String minut, String secunda) {
        StringBuilder sb = new StringBuilder();
        sb.append(" UPDATE " + TABLE_TIMPI + " SET ");

        sb.append(obtineColoanaTimp(etapa));

        sb.append(" = '" + data + ' ' + ora + ':' + minut + ':' + secunda + "'");
        sb.append(" WHERE idSerie = ");
        sb.append(idSerie);
        String test = sb.toString();
        System.out.println(test);
        return sb.toString();
    }

//    metoda alege in functie de valoarea etapei primite de la timpiController numele coloanei;
    public String obtineColoanaTimp(int etapa) {
        String etapaStr = null;
        switch (etapa) {
            case 11:
                etapaStr = COLUMN_DIVIZARE_INCEPUT;
                break;
            case 12:
                etapaStr = COLUMN_DIVIZARE_FINAL;
                break;
            case 21:
                etapaStr = COLUMN_PREPARARE_INCEPUT;
                break;
            case 22:
                etapaStr = COLUMN_PREPARARE_SFARSIT;
                break;
            case 31:
                etapaStr = COLUMN_FILTRARE_INCEPUT;
                break;
            case 32:
                etapaStr = COLUMN_FILTRARE_SFARSIT;
                break;
            case 41:
                etapaStr = COLUMN_UMPLERE_INCEPUT;
                break;
            case 42:
                etapaStr = COLUMN_UMPLERE_SFARSIT;
                break;
            case 51:
                etapaStr = COLUMN_STERILIZARE_INCEPUT;
                break;
            case 52:
                etapaStr = COLUMN_STERILIZARE_FINAL;
                break;
            case 61:
                etapaStr = COLUMN_CONTROL_MACROSCOPIC_INCEPUT;
                break;
            case 62:
                etapaStr = COLUMN_CONTROL_MACROSCOPIC_FINAL;
                break;
            case 71:
                etapaStr = COLUMN_AMBALARE_INCEPUT;
                break;
            case 72:
                etapaStr = COLUMN_AMBALARE_FINAL;
                break;
        }
        return etapaStr;
    }

}

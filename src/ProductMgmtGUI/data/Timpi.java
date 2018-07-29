package ProductMgmtGUI.data;

import java.sql.Timestamp;

public class Timpi {


    private int idtimpi;
    private Timestamp divizare_inceput;
    private Timestamp divizare_final;
    private Timestamp Preparare_inceput;
    private Timestamp Preparare_sfarsit;
    private Timestamp Filtrare_inceput;
    private Timestamp Filtrare_sfarsit;
    private Timestamp Umplere_inceput;
    private Timestamp Umplere_final;
    private boolean Sterilizat;

    private Timestamp Sterilizare_inceput;
    private Timestamp Sterilizare_final;
    private Timestamp Control_macroscopic_inceput;
    private Timestamp Control_macroscopic_final;
    private Timestamp Ambalare_inceput;
    private Timestamp Ambalare_final;
    private int idSerie;

    public Timestamp getUmplere_inceput() {
        return Umplere_inceput;
    }

    public void setUmplere_inceput(Timestamp umplere_inceput) {
        Umplere_inceput = umplere_inceput;
    }

    public Timestamp getUmplere_final() {
        return Umplere_final;
    }

    public void setUmplere_final(Timestamp umplere_final) {
        Umplere_final = umplere_final;
    }

    public int getIdtimpi() {
        return idtimpi;
    }

    public void setIdtimpi(int idtimpi) {
        this.idtimpi = idtimpi;
    }


    public Timestamp getDivizare_inceput() {
        return divizare_inceput;
    }

    public void setDivizare_inceput(Timestamp divizare_inceput) {
        this.divizare_inceput = divizare_inceput;
    }

    public Timestamp getDivizare_final() {
        return divizare_final;
    }

    public void setDivizare_final(Timestamp divizare_final) {
        this.divizare_final = divizare_final;
    }

    public Timestamp getPreparare_inceput() {
        return Preparare_inceput;
    }

    public void setPreparare_inceput(Timestamp preparare_inceput) {
        Preparare_inceput = preparare_inceput;
    }

    public Timestamp getPreparare_sfarsit() {
        return Preparare_sfarsit;
    }

    public void setPreparare_sfarsit(Timestamp preparare_sfarsit) {
        Preparare_sfarsit = preparare_sfarsit;
    }

    public Timestamp getFiltrare_inceput() {
        return Filtrare_inceput;
    }

    public void setFiltrare_inceput(Timestamp filtrare_inceput) {
        Filtrare_inceput = filtrare_inceput;
    }

    public Timestamp getFiltrare_sfarsit() {
        return Filtrare_sfarsit;
    }

    public void setFiltrare_sfarsit(Timestamp filtrare_sfarsit) {
        Filtrare_sfarsit = filtrare_sfarsit;
    }

    public boolean isSterilizat() {
        return Sterilizat;
    }

    public void setSterilizat(boolean sterilizat) {
        Sterilizat = sterilizat;
    }

    public Timestamp getSterilizare_inceput() {
        return Sterilizare_inceput;
    }

    public void setSterilizare_inceput(Timestamp sterilizare_inceput) {
        Sterilizare_inceput = sterilizare_inceput;
    }

    public Timestamp getSterilizare_final() {
        return Sterilizare_final;
    }

    public void setSterilizare_final(Timestamp sterilizare_final) {
        Sterilizare_final = sterilizare_final;
    }

    public Timestamp getControl_macroscopic_inceput() {
        return Control_macroscopic_inceput;
    }

    public void setControl_macroscopic_inceput(Timestamp control_macroscopic_inceput) {
        Control_macroscopic_inceput = control_macroscopic_inceput;
    }

    public Timestamp getControl_macroscopic_final() {
        return Control_macroscopic_final;
    }

    public void setControl_macroscopic_final(Timestamp control_macroscopic_final) {
        Control_macroscopic_final = control_macroscopic_final;
    }

    public Timestamp getAmbalare_inceput() {
        return Ambalare_inceput;
    }

    public void setAmbalare_inceput(Timestamp ambalare_inceput) {
        Ambalare_inceput = ambalare_inceput;
    }

    public Timestamp getAmbalare_final() {
        return Ambalare_final;
    }

    public void setAmbalare_final(Timestamp ambalare_final) {
        Ambalare_final = ambalare_final;
    }

    public int getIdSerie() {
        return idSerie;
    }

    public void setIdSerie(int idSerie) {
        this.idSerie = idSerie;
    }
}

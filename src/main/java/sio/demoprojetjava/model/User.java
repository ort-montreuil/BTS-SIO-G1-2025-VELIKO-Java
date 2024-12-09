package sio.demoprojetjava.model;

public class User {

    private int idUser;
    private String NomUser;
    private String PrenomUser;
    private String emailUser;

    private Integer VerifUser;
    private Boolean BloquerUser;
    private Boolean ChangerMdpUser;

    public User(int idUser, String nomUser, String prenomUser, String emailUser, Integer verifUser, Boolean bloquerUser, Boolean changerMdpUser) {
        this.idUser = idUser;
        NomUser = nomUser;
        PrenomUser = prenomUser;
        this.emailUser = emailUser;

        VerifUser = verifUser;
        BloquerUser = bloquerUser;
        ChangerMdpUser = changerMdpUser;
    }

    public Boolean getBloquerUser() {
        return BloquerUser;
    }

    public void setBloquerUser(Boolean bloquerUser) {
        BloquerUser = bloquerUser;
    }

    public Boolean getChangerMdpUser() {
        return ChangerMdpUser;
    }

    public void setChangerMdpUser(Boolean changerMdpUser) {
        ChangerMdpUser = changerMdpUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNomUser() {
        return NomUser;
    }

    public void setNomUser(String nomUser) {
        NomUser = nomUser;
    }

    public String getPrenomUser() {
        return PrenomUser;
    }

    public void setPrenomUser(String prenomUser) {
        PrenomUser = prenomUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }


    public Integer getVerifUser() {
        return VerifUser;
    }

    public void setVerifUser(Integer verifUser) {
        VerifUser = verifUser;
    }
}



package sio.demoprojetjava.model;

public class Station {
    private double latitude;
    private double longitude;
    private String name;
    private int popularite;

    // Constructeur principal
    public Station(double latitude, double longitude, String name, int popularite) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.popularite = popularite;
    }

    // Constructeur pour les stations favorites (pas besoin des coordonnées ici)
    public Station(String name, int popularite) {
        this.name = name;
        this.popularite = popularite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularite() {
        return popularite;
    }

    public void setPopularite(int popularite) {
        this.popularite = popularite;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

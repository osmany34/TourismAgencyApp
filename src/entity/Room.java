package entity;

import business.HotelManager;
import business.PensionManager;

public class Room {
    private int id;
    private int hotel_id;
    private int pension_id;
    private int season_id;
    private String type;
    private int stock;
    private double adult_price;
    private double child_price;
    private int bed_capacity;
    private int square_meter;
    private boolean television;
    private boolean minibar;
    private boolean iron;
    private boolean air;
    private boolean cafe;


    // Boş kurucu metot
    public Room() {
    }


    // Parametreli kurucu metot
    public Room(int id, int hotel_id, int pension_id, int season_id, String type, int stock, double adult_price, double child_price, int bed_capacity, int square_meter, boolean television, boolean minibar, boolean iron, boolean air,boolean cafe) {
        this.id = id;
        this.hotel_id = hotel_id;
        this.pension_id = pension_id;
        this.season_id = season_id;
        this.type = type;
        this.stock = stock;
        this.adult_price = adult_price;
        this.child_price = child_price;
        this.bed_capacity = bed_capacity;
        this.square_meter = square_meter;
        this.television = television;
        this.minibar = minibar;
        this.iron = iron;
        this.air = air;
        this.cafe = cafe;
    }


    // Getter ve Setter metotları
    public boolean isCafe() {
        return cafe;
    }

    public void setCafe(boolean cafe) {
        this.cafe = cafe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getPension_id() {
        return pension_id;
    }

    public void setPension_id(int pension_id) {
        this.pension_id = pension_id;
    }

    public int getSeason_id() {
        return season_id;
    }

    public void setSeason_id(int season_id) {
        this.season_id = season_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getStock() {
        return stock;
    }

    public int setStock(int stock) {
        this.stock = stock;
        return stock;
    }

    public double getAdult_price() {
        return adult_price;
    }

    public void setAdult_price(double adult_price) {
        this.adult_price = adult_price;
    }

    public double getChild_price() {
        return child_price;
    }

    public void setChild_price(double child_price) {
        this.child_price = child_price;
    }

    public int getBed_capacity() {
        return bed_capacity;
    }

    public void setBed_capacity(int bed_capacity) {
        this.bed_capacity = bed_capacity;
    }

    public int getSquare_meter() {
        return square_meter;
    }

    public void setSquare_meter(int square_meter) {
        this.square_meter = square_meter;
    }

    public boolean isTelevision() {
        return television;
    }

    public void setTelevision(boolean television) {
        this.television = television;
    }

    public boolean isMinibar() {
        return minibar;
    }

    public void setMinibar(boolean minibar) {
        this.minibar = minibar;
    }

    public boolean isIron() {
        return iron;
    }

    public void setIron(boolean iron) {
        this.iron = iron;
    }

    public boolean isAir() {
        return air;
    }

    public void setAir(boolean air) {
        this.air = air;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hotel_id=" + hotel_id +
                ", pension_id=" + pension_id +
                ", season_id=" + season_id +
                ", type='" + type + '\'' +
                ", stock=" + stock +
                ", adult_price=" + adult_price +
                ", child_price=" + child_price +
                ", bed_capacity=" + bed_capacity +
                ", square_meter=" + square_meter +
                ", television=" + television +
                ", minibar=" + minibar +
                ", iron=" + iron +
                ", air=" + air +
                ", cafe=" + cafe +
                '}';
    }
    public Hotel getHotel() {
        HotelManager hotelManager = new HotelManager();
        return hotelManager.getById(hotel_id);
    }
    public Pension getPension() {
        PensionManager pensionManager=new PensionManager();
        return pensionManager.getById(this.pension_id);
    }
}

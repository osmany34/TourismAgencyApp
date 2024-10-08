package business;

import core.Helper;
import dao.RoomDao;
import entity.Hotel;
import entity.Room;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RoomManager {
    RoomDao roomDao = new RoomDao(); // Oda veritabanı erişim nesnesi

    // Belirli bir ID'ye sahip odayı getiren metot
    public Room getById(int id){return this.roomDao.getByID(id);}

    // Tüm odaları getiren metot
    public ArrayList<Room> findAll(){return this.roomDao.findAll();}

    public ArrayList<Room> getRoomByOtelId(int id){return this.roomDao.getRoomByOtelId(id);}

    // Tablo için gerekli bilgileri sağlayan metot
    public ArrayList<Object[]> getForTable(int size, ArrayList<Room> rooms) {
        ArrayList<Object[]> roomList = new ArrayList<>();
        for (Room obj : rooms) {
            int i = 0;
            Object[] rowObject = new Object[size];
            rowObject[i++] = obj.getId();
            rowObject[i++] = obj.getHotel_id();
            rowObject[i++] = obj.getPension_id();
            rowObject[i++] = obj.getSeason_id();
            rowObject[i++] = obj.getType();
            rowObject[i++] = obj.getStock();
            rowObject[i++] = obj.getAdult_price();
            rowObject[i++] = obj.getChild_price();
            rowObject[i++] = obj.getBed_capacity();
            rowObject[i++] = obj.getSquare_meter();
            rowObject[i++] = obj.isTelevision();
            rowObject[i++] = obj.isMinibar();
            rowObject[i++] = obj.isGame_console();
            rowObject[i++] = obj.isCash_box();
            rowObject[i++] = obj.isProjection();
            roomList.add(rowObject);
        }
        return roomList;
    }

    // Belirtilen kriterlere göre odaları arar
    public ArrayList<Room> searchRooms(String startDate, String endDate, String city, String hotelName) {
        return roomDao.searchRooms(startDate, endDate, city, hotelName);
    }
    // Otelin bulunduğu şehirleri döndürür
    public ArrayList<String> getHotelCities(int hotelId) {
        return roomDao.getHotelCities(hotelId);
    }
    // Tüm otelleri döndürür
    public ArrayList<Hotel> getAllHotels() {
        return roomDao.getAllHotels();
    }

    // Oda kaydını veritabanına ekleyen metot
    public boolean save(Room room){
        if(room.getId()!=0){
            Helper.showMsg("error");
            return false;
        }
        return this.roomDao.save(room);
    }

    // Oda stokunu güncelleyen metot
    public boolean updateStock(Room room){
        if(this.getById(room.getId())== null){
            return false;
        }
        return this.roomDao.updateStock(room);
    }

    // Belirli bir ID'ye sahip odayı silen metot
    public boolean delete(int id){
        if(this.getById(id) == null){
            Helper.showMsg(id + " ID kayıtlı  bulunamadı");
            return false;
        }
        return this.roomDao.delete(id);
    }

    // Oda bilgilerini güncelleyen metot
    public boolean update(Room room) {
        if (this.getById(room.getId()) == null) {
            Helper.showMsg(room.getId()+ " ID kayıtlı  bulunamadı");
            return false;
        }
        return this.roomDao.update(room);
    }

    public ArrayList<Room> searchForTable(String hotelName, String cityName, String checkinDate, String checkoutDate, String adultNum, String childNum) {
        String query = "SELECT * FROM public.room r " +
                "LEFT JOIN public.hotel h ON r.hotel_id = h.id " +
                "LEFT JOIN public.season s ON r.season_id = s.id WHERE r.stock > 0";

        LocalDate checkin = LocalDate.parse(checkinDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate checkout = LocalDate.parse(checkoutDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        query += " AND s.start_date <= '" + checkin + "' AND s.finish_date >= '" + checkout + "'";

        if (hotelName != null && !hotelName.isEmpty()) {
            query += " AND h.name ILIKE '%" + hotelName + "%'";
        }
        if (cityName != null && !cityName.isEmpty()) {
            query += " AND h.city ILIKE '%" + cityName + "%'";
        }
        if (adultNum != null && !adultNum.isEmpty() && childNum != null && !childNum.isEmpty()) {
            try {
                int adultNumber = Integer.parseInt(adultNum);
                int childNumber = Integer.parseInt(childNum);
                int totalNumber = adultNumber + childNumber;
                query += " AND r.bed_capacity >= " + totalNumber;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        System.out.println(query);

        return this.roomDao.selectByQuery(query);
    }
}

package dao;

import core.Db;
import entity.Hotel;
import entity.Pension;
import entity.Room;
import entity.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Oda veritabanı işlemlerini yapan sınıf
public class RoomDao {
    private final Connection connection;

    // Yapılandırıcı metot
    public RoomDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Room> getRoomByOtelId(int id) {
        ArrayList<Room> rooms = new ArrayList<>();
        String query = "SELECT * FROM public.room WHERE hotel_id = ?";

        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();

            while (rs.next()) {
                Room room = match(rs);
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    // Belirli bir ID'ye sahip odanın bilgilerini getiren metot
    public Room getByID(int id) {
        Room obj = null;
        String query = "SELECT * FROM public.room WHERE id = ? ";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, id);
            ResultSet rs = pr.executeQuery();
            if (rs.next()) {
                obj = this.match(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return obj;
    }

    // ResultSet'ten Room nesnesine eşleme yapan yardımcı metot
    public Room match(ResultSet rs) throws SQLException {
        Room obj = new Room();
        obj.setId(rs.getInt("id"));
        obj.setHotel_id(rs.getInt("hotel_id"));
        obj.setPension_id(rs.getInt("pension_id"));
        obj.setSeason_id(rs.getInt("season_id"));
        obj.setType(rs.getString("type"));
        obj.setStock(rs.getInt("stock"));
        obj.setAdult_price(rs.getDouble("adult_price"));
        obj.setChild_price(rs.getDouble("child_price"));
        obj.setBed_capacity(rs.getInt("bed_capacity"));
        obj.setSquare_meter(rs.getInt("square_meter"));
        obj.setTelevision(rs.getBoolean("television"));
        obj.setMinibar(rs.getBoolean("minibar"));
        obj.setGame_console(rs.getBoolean("game_console"));
        obj.setProjection(rs.getBoolean("projection"));
        obj.setCash_box(rs.getBoolean("cash_box"));
        return obj;
    }
    public ArrayList<Room> searchRooms(String startDate, String endDate, String city, String hotelName) {
        ArrayList<Room> rooms = new ArrayList<>();

        String query = "WITH AvailableRoomCounts AS ( " +
                "    SELECT r.id, r.hotel_id, r.pension_id, r.season_id, r.type, r.stock, r.adult_price, r.child_price, r.bed_capacity, r.square_meter, r.television, r.minibar, r.game_console, r.projection, r.cash_box, " +
                "           r.stock + COUNT(res.id) AS available_stock " +
                "    FROM public.room r " +
                "    LEFT JOIN public.reservation res " +
                "    ON r.id = res.room_id " +
                "    AND (res.check_out_date < ?::DATE OR res.check_in_date > ?::DATE) " +
                "    GROUP BY r.id, r.hotel_id, r.pension_id, r.season_id, r.type, r.stock, r.adult_price, r.child_price, r.bed_capacity, r.square_meter, r.television, r.minibar, r.game_console, r.projection, r.cash_box " +
                ") " +
                "SELECT r.*, ar.available_stock " +
                "FROM public.room r " +
                "JOIN public.hotel h ON r.hotel_id = h.id " +
                "JOIN AvailableRoomCounts ar ON r.id = ar.id " +
                "WHERE ar.available_stock > 0 ";

        if (city != null && !city.isEmpty()) {
            query += "AND h.city = ? ";
        }

        if (hotelName != null && !hotelName.isEmpty()) {
            query += "AND h.name = ? ";
        }

        query += "ORDER BY r.id ASC";

        try (PreparedStatement pr = connection.prepareStatement(query)) {
            int paramIndex = 1;

            pr.setString(paramIndex++, startDate);
            pr.setString(paramIndex++, endDate);

            if (city != null && !city.isEmpty()) {
                pr.setString(paramIndex++, city);
            }

            if (hotelName != null && !hotelName.isEmpty()) {
                pr.setString(paramIndex++, hotelName);
            }

            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                Room room = this.match(rs);
                room.setStock(rs.getInt("available_stock"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    // Tüm otelleri döndürür
    public ArrayList<Hotel> getAllHotels() {
        ArrayList<Hotel> hotels = new ArrayList<>();
        String query = "SELECT id, name FROM public.hotel";
        try (PreparedStatement pr = connection.prepareStatement(query)) {
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                int hotel_id = rs.getInt("id");
                String hotel_name = rs.getString("name");
                hotels.add(new Hotel(hotel_id, hotel_name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hotels;
    }
    // Otelin bulunduğu şehirleri döndürür
    public ArrayList<String> getHotelCities(int hotelId) {
        ArrayList<String> cities = new ArrayList<>();
        String query = "SELECT DISTINCT city FROM public.hotel WHERE id = ?";
        try (PreparedStatement pr = connection.prepareStatement(query)) {
            pr.setInt(1, hotelId);
            ResultSet rs = pr.executeQuery();
            while (rs.next()) {
                cities.add(rs.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }
    // Tüm odaları getiren metot
    public ArrayList<Room> findAll() {
        ArrayList<Room> roomList = new ArrayList<>();
        String sql = "SELECT * FROM public.room";
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(sql);
            while (rs.next()) {

                roomList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    // Oda ekleyen metot
    public boolean save(Room room) {
        String query = "INSERT INTO public.room" +
                "(" +
                "hotel_id," +
                "pension_id," +
                "season_id," +
                "type," +
                "stock," +
                "adult_price," +
                "child_price," +
                "bed_capacity," +
                "square_meter," +
                "television," +
                "minibar," +
                "game_console," +
                "cash_box," +
                "projection" +
                ")" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, room.getHotel_id());
            pr.setInt(2, room.getPension_id());
            pr.setInt(3, room.getSeason_id());
            pr.setString(4, room.getType());
            pr.setInt(5, room.getStock());
            pr.setDouble(6, room.getAdult_price());
            pr.setDouble(7, room.getChild_price());
            pr.setInt(8, room.getBed_capacity());
            pr.setInt(9, room.getSquare_meter());
            pr.setBoolean(10, room.isTelevision());
            pr.setBoolean(11, room.isMinibar());
            pr.setBoolean(12, room.isGame_console());
            pr.setBoolean(13, room.isCash_box());
            pr.setBoolean(14, room.isProjection());
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    // Belirli bir sorgu ile odaları getiren metot
    public ArrayList<Room> selectByQuery(String query){
        ArrayList<Room> roomList = new ArrayList<>();
        try {
            ResultSet rs = this.connection.createStatement().executeQuery(query);
            while (rs.next()){
                roomList.add(this.match(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roomList;
    }

    // Stok bilgisini güncelleyen metot
    public boolean updateStock(Room room){
        String query = "UPDATE public.room SET stock = ? WHERE id = ? ";
        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, room.getStock());
            pr.setInt(2,room.getId());

            pr.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    // Oda silen metot
    public boolean delete(int hotel_id) {
        try {
            String query = "DELETE FROM public.room WHERE id = ?";
            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, hotel_id);
            return pr.executeUpdate() != -1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }


    // Oda bilgilerini güncelleyen metot
    public boolean update(Room room) {
        try {
            String query = "UPDATE public.room SET " +
                    "hotel_id = ?," +
                    "pension_id = ?," +
                    "season_id= ?," +
                    "type= ?," +
                    "stock= ?," +
                    "adult_price = ?," +
                    "child_price = ?," +
                    "bed_capacity = ?," +
                    "square_meter = ?," +
                    "television = ?," +
                    "minibar = ?," +
                    "game_console = ?," +
                    "cash_box = ?," +
                    "projection = ?" +
                    " WHERE id = ?";

            PreparedStatement pr = connection.prepareStatement(query);
            pr.setInt(1, room.getHotel_id());
            pr.setInt(2, room.getPension_id());
            pr.setInt(3, room.getSeason_id());
            pr.setString(4, room.getType());
            pr.setInt(5, room.getStock());
            pr.setDouble(6, room.getAdult_price());
            pr.setDouble(7, room.getChild_price());
            pr.setInt(8, room.getBed_capacity());
            pr.setInt(9, room.getSquare_meter());
            pr.setBoolean(10, room.isTelevision());
            pr.setBoolean(11, room.isMinibar());
            pr.setBoolean(12, room.isGame_console());
            pr.setBoolean(13, room.isCash_box());
            pr.setBoolean(14, room.isProjection());
            pr.setInt(15, room.getId());
            return pr.executeUpdate() != -1;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }



}


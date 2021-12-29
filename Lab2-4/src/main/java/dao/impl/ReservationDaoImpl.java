package dao.impl;

import builder.Builder;
import dao.api.ReservationDao;
import entity.User;
import entity.reservation.Reservation;
import entity.reservation.ReservationStatus;
import entity.room.Room;
import entity.room.RoomClass;
import exception.DaoException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.text.DateFormat;

public class ReservationDaoImpl extends AbstractDao<Reservation> implements ReservationDao {

    private static final String TABLE_NAME = "reservation";
    private static final String SELECT_QUERY = "SELECT res.*, " +
            "   u.email, u.is_admin, u.user_password, u.first_name, u.second_name, " +
            "   c.class_name, c.basic_rate, c.rate_per_person, " +
            "   r.is_active, r.beds_amount " +
            "FROM reservation AS res " +
            "JOIN booking_user AS u ON res.user_id=u.id " +
            "JOIN room_class AS c ON res.room_class_id=c.id " +
            "LEFT JOIN room AS r ON res.room_id=r.id";
    private static final String FIND_BY_ID_QUERY = SELECT_QUERY + ' ' + "WHERE res.id = ?;";
    private static final String FIND_BY_USER_ID_QUERY = SELECT_QUERY + ' ' + "WHERE u.id = ?;";
    private static final String UPDATE_QUERY = "UPDATE reservation SET user_id = ?, room_class_id = ?, room_id = ?, "+
    	"reservation_status = ?, arrival_date = ?, departure_date = ?, persons_amount = ?, total_price = ? WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO reservation (user_id, room_class_id, room_id, " +
    	"reservation_status, arrival_date, departure_date, persons_amount, total_price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    public ReservationDaoImpl(Builder<Reservation> builder, Connection connection) {
        super(builder, connection);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Optional<Reservation> getById(int id) throws DaoException {
        return executeForSingleResult(FIND_BY_ID_QUERY, id);
    }

    @Override
    public List<Reservation> getByUserId(int id) throws DaoException {
        return executeQuery(FIND_BY_USER_ID_QUERY, id);
    }

    @Override
    public List<Reservation> getAll() throws DaoException {
        return executeQuery(SELECT_QUERY);
    }

    @Override
    public void save(Reservation entity) throws DaoException {
        User user = entity.getUser();
        RoomClass roomClass = entity.getRoomClass();
        Room room = entity.getRoom();
        ReservationStatus reservationStatus = entity.getReservationStatus();
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        
        if (entity.getId() == null) {
	        Object[] parameters = {
	                user.getId(),
	                roomClass.getId(),
	                (room != null) ? room.getId() : null,
	                reservationStatus.toString(),
	                df.format(entity.getArrivalDate()),
	                df.format(entity.getDepartureDate()),
	                entity.getPersonsAmount(),
	                entity.getTotalPrice()
	        };
	        executeUpdate(INSERT_QUERY, parameters);
        }
        else {
        	Object[] parameters = {
	                user.getId(),
	                roomClass.getId(),
	                (room != null) ? room.getId() : null,
	                reservationStatus.toString(),
	                df.format(entity.getArrivalDate()),
	                df.format(entity.getDepartureDate()),
	                entity.getPersonsAmount(),
	                entity.getTotalPrice(),
	                entity.getId()
	        };
        	executeUpdate(UPDATE_QUERY, parameters);
        }
    }

    @Override
    public void deleteById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

}

package builder.impl;

import builder.Builder;
import entity.room.Room;
import entity.room.RoomClass;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RoomBuilder implements Builder<Room> {

    private static final String DEFAULT_ID_COLUMN = "id";
    private static final String IS_ACTIVE_COLUMN = "is_active";
    private static final String ROOM_CLASS_ID_ALIAS = "room_class_id";
    private static final String BEDS_AMOUNT_COLUMN = "beds_amount";

    private Builder<RoomClass> roomClassBuilder;
    private String idAlias;

    public RoomBuilder() {
        roomClassBuilder = new RoomClassBuilder(ROOM_CLASS_ID_ALIAS);
        idAlias = DEFAULT_ID_COLUMN;
    }


    public RoomBuilder(String idAlias) {
        this.idAlias = idAlias;
        roomClassBuilder = new RoomClassBuilder(ROOM_CLASS_ID_ALIAS);
    }

    @Override
    public Room build(ResultSet resultSet) throws SQLException {
        Integer id = (Integer) resultSet.getObject(idAlias);
        if (resultSet.wasNull()) {
            return null;
        }
        boolean isActive = resultSet.getBoolean(IS_ACTIVE_COLUMN);
        RoomClass roomClass = roomClassBuilder.build(resultSet);
        int bedsAmount = resultSet.getInt(BEDS_AMOUNT_COLUMN);
        return new Room(id, isActive, roomClass, bedsAmount);
    }

}

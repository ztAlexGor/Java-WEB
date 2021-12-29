package builder.impl;

import builder.Builder;
import entity.room.RoomClass;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomClassBuilder implements Builder<RoomClass> {

    private static final String DEFAULT_ID_COLUMN = "id";
    private static final String NAME_COLUMN = "class_name";
    private static final String BASIC_RATE_COLUMN = "basic_rate";
    private static final String RATE_PER_PERSON_COLUMN = "rate_per_person";

    private String idAlias;

    public RoomClassBuilder() {
        idAlias = DEFAULT_ID_COLUMN;
    }


    public RoomClassBuilder(String idAlias) {
        this.idAlias = idAlias;
    }

    @Override
    public RoomClass build(ResultSet resultSet) throws SQLException {
        Integer id = (Integer) resultSet.getObject(idAlias);
        if (resultSet.wasNull()) {
            return null;
        }
        String name = resultSet.getString(NAME_COLUMN);
        BigDecimal basicRate = resultSet.getBigDecimal(BASIC_RATE_COLUMN);
        BigDecimal ratePerPerson = resultSet.getBigDecimal(RATE_PER_PERSON_COLUMN);
        return new RoomClass(id, name, basicRate, ratePerPerson);
    }

}

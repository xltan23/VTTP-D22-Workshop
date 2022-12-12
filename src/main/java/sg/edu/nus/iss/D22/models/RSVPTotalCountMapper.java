package sg.edu.nus.iss.D22.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RSVPTotalCountMapper implements RowMapper<RSVP> {

    @Override
    public RSVP mapRow(ResultSet rs, int rowNum) throws SQLException {
        RSVP rsvp = new RSVP();
        rsvp.setTotalCount(rs.getInt("total"));
        return rsvp;
    }
    
}

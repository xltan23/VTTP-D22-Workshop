package sg.edu.nus.iss.D22.repositories;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.D22.models.RSVP;
import sg.edu.nus.iss.D22.models.RSVPTotalCountMapper;

import static sg.edu.nus.iss.D22.repositories.Queries.*;

@Repository
public class RSVPRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Retrieving all RSVP or specific RSVP (By name)
    public List<RSVP> getAllRSVP(String name) {
        final List<RSVP> rsvpList = new LinkedList<>();
        SqlRowSet srs = null;
        if (name == null) {
            srs = jdbcTemplate.queryForRowSet(SQL_SELECT_ALL_RSVP);
        }
        if (name != null) {
            srs = jdbcTemplate.queryForRowSet(SQL_SELECT_RSVP_BY_NAME, name);
        }
        while(srs.next()) {
            rsvpList.add(RSVP.create(srs));
        }
        return rsvpList;
    }

    // Search existing RSVP by email
    public RSVP searchRSVPByEmail(String email) {
        final List<RSVP> rsvpList = new LinkedList<>();
        final SqlRowSet srs = jdbcTemplate.queryForRowSet(SQL_SELECT_RSVP_BY_EMAIL, email);
        while(srs.next()) {
            rsvpList.add(RSVP.create(srs));
        }
        // Return first RSVP for email input
        return rsvpList.get(0);
    }

    // Insert RSVP into database
    public RSVP insertRSVP(final RSVP rsvp) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(conn -> { 
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RSVP, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, rsvp.getName());
                ps.setString(2, rsvp.getEmail());
                ps.setString(3, rsvp.getPhone());
                ps.setTimestamp(4, new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()));
                ps.setString(5, rsvp.getComments());
                return ps;
            }, keyHolder);
            BigInteger primaryKeyVal = (BigInteger) keyHolder.getKey();
            rsvp.setId(primaryKeyVal.intValue());
        } catch (DataIntegrityViolationException e) {
            // RSVP Exists, retrieve existing RSVP 
            RSVP existingRSVP = searchRSVPByEmail(rsvp.getEmail());
            // Change to new values
            existingRSVP.setName(rsvp.getName());
            existingRSVP.setPhone(rsvp.getPhone());
            existingRSVP.setConfirmationDate(rsvp.getConfirmationDate());
            existingRSVP.setComments(rsvp.getComments());
            if (updateRSVP(existingRSVP)) {
                rsvp.setId(existingRSVP.getId());
            }
        }
        return rsvp;
    }

    // Method to update RSVP and concurrently check if change has occured
    public boolean updateRSVP(RSVP rsvp) {
        return jdbcTemplate.update(SQL_UPDATE_RSVP_BY_EMAIL, 
        rsvp.getName(),
        rsvp.getEmail(),
        rsvp.getPhone(),
        // rsvp DateTime is converted to SQL DateTime style 
        new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()),
        rsvp.getComments(),
        // Return number of rows affected. If > 0 means a change has occured
        rsvp.getEmail()) > 0;           
    }

    // Method to count total RSVP
    public Integer getTotalRSVP() {
        List<RSVP> rsvpList = jdbcTemplate.query(SQL_SELECT_TOTAL_COUNT, new RSVPTotalCountMapper(), new Object[] {});
        return rsvpList.get(0).getTotalCount();
    }
}

package sg.edu.nus.iss.D22.repositories;

public class Queries {
    // SQL Query for selecting all RSVP
    public static final String SQL_SELECT_ALL_RSVP = "SELECT id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%y\") as confirmation_date, comments from rsvp";
    // SQL Query for selecting RSVP by name
    public static final String SQL_SELECT_RSVP_BY_NAME = "SELECT id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%y\") as confirmation_date, comments from rsvp where name = ?";
    // SQL Query for selecting RSVP by email
    public static final String SQL_SELECT_RSVP_BY_EMAIL = "SELECT id, name, email, phone, DATE_FORMAT(confirmation_date, \"%d/%m/%y\") as confirmation_date, comments from rsvp where email = ?";
    // SQL Query for inserting RSVP
    public static final String SQL_INSERT_RSVP = "INSERT into rsvp(name, email, phone, confirmation_date, comments) VALUES (?,?,?,?,?)";
    // SQL Query for updating RSVP by email
    public static final String SQL_UPDATE_RSVP_BY_EMAIL = "UPDATE rsvp SET name = ?, email = ?, phone = ?, confirmation_date = ?, comments = ? where email = ?";
    // SQL Query for calculating total count of RSVP (Name total count field as total for value-retrieving)
    public static final String SQL_SELECT_TOTAL_COUNT = "SELECT count(*) as total from rsvp";
}

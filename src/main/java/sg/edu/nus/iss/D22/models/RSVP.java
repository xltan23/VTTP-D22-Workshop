package sg.edu.nus.iss.D22.models;

import java.io.StringReader;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class RSVP {
    
    // Defining members of RSVP
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private DateTime confirmationDate;
    private String comments;
    private Integer totalCount;

    // Generate getter and setter
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public DateTime getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(DateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public Integer getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
    
    // Creating RSVP object from SQL row results
    public static RSVP create(SqlRowSet srs) {
        final RSVP rsvp = new RSVP();
        rsvp.setId(srs.getInt("id"));
        rsvp.setName(srs.getString("name"));
        rsvp.setEmail(srs.getString("email"));
        rsvp.setPhone(srs.getString("phone"));
        rsvp.setConfirmationDate(new DateTime(DateTimeFormat.forPattern("dd/MM/yyyy")
                                                    .parseDateTime(srs.getString("confirmation_date"))));
        rsvp.setComments(srs.getString("comments"));
        return rsvp;
    }

    // Creating RSVP object from Json Object
    public static RSVP create(JsonObject jo) {
        final RSVP rsvp = new RSVP();
        rsvp.setName(jo.getString("name"));
        rsvp.setEmail(jo.getString("email"));
        rsvp.setPhone(jo.getString("phone"));
        // Converting string to DateTime
        rsvp.setConfirmationDate(new DateTime(Instant.parse(jo.getString("confirmation_date"))));
        rsvp.setComments(jo.getString("comments"));
        return rsvp;
    }
    
    // Creating RSVP object from Json String
    public static RSVP create(String jsonString) {
        StringReader sr = new StringReader(jsonString);
        JsonReader jr = Json.createReader(sr);
        return create(jr.readObject());
    }

    // Converting RSVP object to Json
    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                    .add("id", id)
                    .add("name", name)
                    .add("email", email)
                    .add("phone", phone)
                    .add("confirmation_date", confirmationDate != null ? confirmationDate.toString() : "")
                    .add("comments", comments)
                    .build();
    }
}

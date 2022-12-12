package sg.edu.nus.iss.D22.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.D22.models.RSVP;
import sg.edu.nus.iss.D22.services.RSVPService;

@RestController
@RequestMapping(path = "/api/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
public class RSVPRestController {
    
    @Autowired
    private RSVPService rsvpSvc;

    // GET: localhost:8080/api/rsvp
    @GetMapping
    public ResponseEntity<String> getRSVP(@RequestParam(required = false) String name) {
        List<RSVP> rsvpList = rsvpSvc.getAllRSVP(name);
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (RSVP rsvp : rsvpList) {
            jab.add(rsvp.toJSON());
        }
        JsonArray ja = jab.build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ja.toString());
    }

    // POST: localhost:8080/api/rsvp
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postRSVP(@RequestBody String jsonString) {
        RSVP rsvp = null;
        try {
            rsvp = RSVP.create(jsonString);
        } catch (Exception e) {
            // If JsonString does not match specification of RSVP Json Object => print error message
            e.printStackTrace();
            JsonObject errorMsg = Json.createObjectBuilder()
                                        .add("Error", e.getMessage())
                                        .build();
            return ResponseEntity.badRequest().body(errorMsg.toString());
        }
        // Insert RSVP (Including giving RSVP an ID)
        RSVP rsvpInsert = rsvpSvc.insertRSVP(rsvp);
        JsonObject jo = Json.createObjectBuilder()
                                .add("RSVP_ID", rsvpInsert.getId())
                                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jo.toString());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putRSVP(@RequestBody String jsonString) {
        RSVP rsvp = null;
        try {
            rsvp = RSVP.create(jsonString);
        } catch (Exception e) {
            // If JsonString does not match specification of RSVP Json Object => print error message
            e.printStackTrace();
            JsonObject errorMsg = Json.createObjectBuilder()
                                        .add("Error", e.getMessage())
                                        .build();
            return ResponseEntity.badRequest().body(errorMsg.toString());
        }
        // Update RSVP
        Boolean rsvpUpdate = rsvpSvc.updateRSVP(rsvp);
        JsonObject jo = Json.createObjectBuilder()
                                .add("Updated", rsvpUpdate)
                                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jo.toString());
    }

    // localhost:8080/api/rsvp/count
    @GetMapping(path = "/count")
    public ResponseEntity<String> countRSVP() {
        Integer rsvpCount = rsvpSvc.getTotalRSVP();
        JsonObject jo = Json.createObjectBuilder()
                                .add("total_count", rsvpCount)
                                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(jo.toString());
    }
}

package sg.edu.nus.iss.D22.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.D22.models.RSVP;
import sg.edu.nus.iss.D22.repositories.RSVPRepository;

@Service
public class RSVPService {
    
    @Autowired
    private RSVPRepository rsvpRepo;

    public List<RSVP> getAllRSVP(String name) {
        return rsvpRepo.getAllRSVP(name);
    }

    public RSVP insertRSVP(RSVP rsvp) {
        return rsvpRepo.insertRSVP(rsvp);
    }

    public boolean updateRSVP(RSVP rsvp) {
        return rsvpRepo.updateRSVP(rsvp);
    }

    public Integer getTotalRSVP() {
        return rsvpRepo.getTotalRSVP();
    }
}

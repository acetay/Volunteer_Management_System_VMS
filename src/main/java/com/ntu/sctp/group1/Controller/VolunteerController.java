package com.ntu.sctp.group1.Controller;

import com.ntu.sctp.group1.Exceptions.NoProfileFoundExceptions;
import com.ntu.sctp.group1.Exceptions.NoVolunteerFoundExceptions;
import com.ntu.sctp.group1.Service.ProfileService;
import com.ntu.sctp.group1.Service.VolunteerService;
import com.ntu.sctp.group1.entity.Profile;
import com.ntu.sctp.group1.entity.Volunteer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

    @CrossOrigin(origins= {"*"}, maxAge = 86400, allowCredentials = "false" )
    @RestController
    public class VolunteerController {

    @Autowired
    VolunteerService volunteerService;

    @Autowired
    ProfileService profileService;

    record Message(String message, boolean success){}

        @GetMapping("/admin/volunteers")
        public ResponseEntity<?> getAllVolunteers() {
            try {
                return ResponseEntity.ok().body(volunteerService.getAllVolunteers());
            } catch (NoVolunteerFoundExceptions ex) {
                ex.printStackTrace();
                return ResponseEntity.notFound().build();
            } catch (Exception ex) {
                ex.printStackTrace();
                return ResponseEntity.badRequest().body(new Message(ex.getMessage(),false));
            } 
        }

        @GetMapping("/admin/volunteers/{id}")
        public ResponseEntity<?> getVolunteerById(@PathVariable int id) throws NoVolunteerFoundExceptions {
            try {
                Volunteer volunteer = volunteerService.getVolunteerById(id);
                return ResponseEntity.ok().body(volunteer);
            } catch (NoVolunteerFoundExceptions ex) {
                ex.printStackTrace();
                return ResponseEntity.notFound().build();
            } catch (Exception ex) {
                ex.printStackTrace();
                return ResponseEntity.badRequest().body(new Message(ex.getMessage(),false));
            }
        }

        @GetMapping("/admin/volunteers/search")
        public ResponseEntity<List<Volunteer>> searchByParams (@RequestParam Map<String, String> params) {
            try {
                return ResponseEntity.ok().body(volunteerService.searchByParams(params));
            } catch (Exception ex) {
                ex.printStackTrace();
                return ResponseEntity.badRequest().body(null);
            }
        }




        @PutMapping("/volunteers/{id}")
        public ResponseEntity<?> updateVolunteer(@PathVariable int id, @RequestBody Volunteer updatedVolunteer) {
            try {
                Volunteer volunteer = volunteerService.updateVolunteer(id, updatedVolunteer);
                return ResponseEntity.ok().body(volunteer);
            } catch (NoVolunteerFoundExceptions ex) {
                ex.printStackTrace();
                return ResponseEntity.notFound().build();
            } catch (Exception ex) {
                ex.printStackTrace();
                return ResponseEntity.badRequest().body(new Message(ex.getMessage(),false));
            }
        }

        @DeleteMapping("/admin/volunteers/{id}")
        public ResponseEntity<?> deleteVolunteer(@PathVariable int id) {

          try {
              volunteerService.deleteVolunteer(id);
              return ResponseEntity.ok().body(new Message("Volunteer with ID " + id + " deleted" , true));
          } catch(NoVolunteerFoundExceptions ex) {
              ex.printStackTrace();
              return ResponseEntity.notFound().build();
          }catch (Exception ex) {
              ex.printStackTrace();
              return ResponseEntity.badRequest().body(new Message(ex.getMessage(),false));
          }

        }


        @GetMapping("/admin/volunteers/profiles")
        public ResponseEntity<?> getAllProfiles() {
            try {
                List<Profile> profiles = profileService.findAll();
                return ResponseEntity.ok(profiles);
            } catch (NoProfileFoundExceptions ex ) {
                ex.printStackTrace();
                return ResponseEntity.notFound().build();

            } catch (Exception ex) {
                ex.printStackTrace();
                return ResponseEntity.badRequest().body(new Message(ex.getMessage(), false));
            }
        }

        // ADDED ON 29 MAR
        @GetMapping("/volunteers/{id}/enrolments")
        public ResponseEntity<?> findEnrolmentsOfVolunteer(@PathVariable int id) {
            try {
                return ResponseEntity.ok().body(volunteerService.getEnrolmentsOfVolunteer(id));
            } catch(NoVolunteerFoundExceptions ex) {
                return ResponseEntity.notFound().build();
            } catch (Exception ex) {
                return ResponseEntity.internalServerError().body(new Message(ex.getMessage(), false));
            }
        }
    }
package com.ntu.sctp.group1.Controller

import com.ntu.sctp.group1.Exceptions.NoAvailabilityFoundExceptions
import com.ntu.sctp.group1.Exceptions.NoVolunteerFoundExceptions
import com.ntu.sctp.group1.Service.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@CrossOrigin(origins = ["*"], maxAge = 86400, allowCredentials = "false")
@RestController
class TestController {

    @Autowired
    lateinit var TestService: TestService

    data class Status(val msg: String, val success: Boolean)

    @PostMapping("/test/volunteers/availability/{volunteerId}")
    fun setAvailability(
        @PathVariable volunteerId: Int,
        @RequestParam date: String,
        @RequestParam timeslot: String
    ): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(TestService.setAvailability(volunteerId, date, timeslot))
        } catch (ex: NoVolunteerFoundExceptions) {
            ex.printStackTrace()
            ResponseEntity.notFound().build<Any>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseEntity.badRequest().body(Status(ex.message ?: "Error", false))
        }
    }

    @GetMapping("/test/volunteers/availability/date/{date}")
    fun searchByDate(@PathVariable date: String): ResponseEntity<*> {
        return try {
            val parsedDate = LocalDate.parse(date)
            val volunteers = TestService.searchByDate(parsedDate)
            ResponseEntity.ok().body(volunteers)
        } catch (ex: NoAvailabilityFoundExceptions) {
            ex.printStackTrace()
            ResponseEntity.notFound().build<Any>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseEntity.badRequest().body(Status(ex.message ?: "Error", false))
        }
    }

    @GetMapping("/test/volunteers/availabilities/{volunteerId}")
    fun getAvailabilitiesOfAVolunteer(@PathVariable volunteerId: Int): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(TestService.getAvailabilitiesOfAVolunteer(volunteerId))
        } catch (ex: NoVolunteerFoundExceptions) {
            ex.printStackTrace()
            ResponseEntity.notFound().build<Any>()
        } catch (ex: NoAvailabilityFoundExceptions) {
            ex.printStackTrace()
            ResponseEntity.notFound().build<Any>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseEntity.internalServerError().body(Status(ex.message ?: "Error", false))
        }
    }

    @PutMapping("/test/volunteers/availability/{volunteerId}")
    fun updateAvailability(
        @PathVariable volunteerId: Int,
        @RequestParam date: String,
        @RequestParam(required = true) isAvail: String
    ): ResponseEntity<*> {
        return try {
            val parsedDate = LocalDate.parse(date)
            ResponseEntity.ok().body(TestService.updateAvailability(volunteerId, parsedDate, isAvail))
        } catch (ex: NoVolunteerFoundExceptions) {
            ex.printStackTrace()
            ResponseEntity.notFound().build<Any>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseEntity.badRequest().body(Status(ex.message ?: "Error", false))
        }
    }

    @DeleteMapping("/test/volunteers/availability/{volunteerId}")
    fun deleteAvailability(
        @PathVariable volunteerId: Int,
        @RequestParam date: String
    ): ResponseEntity<*> {
        return try {
            val parsedDate = LocalDate.parse(date)
            TestService.deleteAvail(volunteerId, parsedDate)
            ResponseEntity.ok().body(Status("Availability deleted successfully", true))
        } catch (ex: NoVolunteerFoundExceptions) {
            ex.printStackTrace()
            ResponseEntity.notFound().build<Any>()
        } catch (ex: Exception) {
            ex.printStackTrace()
            ResponseEntity.badRequest().body(Status(ex.message ?: "Error", false))
        }
    }

    @GetMapping("/test/volunteers/availability/all")
    fun getAllAvailabilities(): ResponseEntity<*> {
        return try {
            ResponseEntity.ok().body(TestService.getAllAvailabilities())
        } catch (ex: NoAvailabilityFoundExceptions) {
            ResponseEntity.notFound().build<Any>()
        } catch (ex: Exception) {
            ResponseEntity.internalServerError().body(Status(ex.message ?: "Error", false))
        }
    }
}
package com.ntu.sctp.group1.Service

import com.ntu.sctp.group1.Exceptions.AvailDateFoundException
import com.ntu.sctp.group1.Exceptions.NoAvailabilityFoundExceptions
import com.ntu.sctp.group1.Exceptions.NoVolunteerFoundExceptions
import com.ntu.sctp.group1.entity.Availability
import com.ntu.sctp.group1.entity.Volunteer
import com.ntu.sctp.group1.repository.AvailabilityRepository
import com.ntu.sctp.group1.repository.VolunteerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.stream.Collectors

@Service
class TestService @Autowired constructor(
    private val availabilityRepo: AvailabilityRepository,
    private val volunteerRepository: VolunteerRepository
) {

    @Throws(NoVolunteerFoundExceptions::class, NoAvailabilityFoundExceptions::class)
    fun getAvailabilitiesOfAVolunteer(volunteerId: Int): List<Availability> {
        val findVolunteer = volunteerRepository.findById(volunteerId)
        if (findVolunteer.isEmpty) {
            throw NoVolunteerFoundExceptions("No volunteer found under id $volunteerId")
        }
        if (findVolunteer.get().availabilities.isEmpty()) {
            throw NoAvailabilityFoundExceptions("Volunteer had not set any availabilities as yet!")
        }

        val avails = findVolunteer.get().availabilities
        avails.sortBy { it.date }
        return avails
    }

    @Throws(NoVolunteerFoundExceptions::class, AvailDateFoundException::class)
    fun setAvailability(volunteerId: Int, date: String, timeslot: String): Availability {
        val findVolunteer = volunteerRepository.findById(volunteerId)
        if (findVolunteer.isEmpty) {
            throw NoVolunteerFoundExceptions("No volunteer id found")
        }
        val searchExistingAvail = availabilityRepo.findByVolunteerId(volunteerId)
        val found = searchExistingAvail.isPresent
        if (found) {
            val checkForSameDate = searchExistingAvail.get().stream()
                .filter { avail -> avail.date == LocalDate.parse(date) }
                .toList()
            if (checkForSameDate.isNotEmpty()) {
                throw AvailDateFoundException("You have already set an availability on this date!")
            }
        }
        val availDate = Availability()
        availDate.avail = true
        availDate.timeslot = timeslot
        availDate.volunteer = findVolunteer.get()
        availDate.date = LocalDate.parse(date)

        return availabilityRepo.save(availDate)
    }

    @Throws(NoAvailabilityFoundExceptions::class)
    fun searchByDate(date: LocalDate): List<Volunteer> {
        val availabilities = availabilityRepo.findByDate(date)

        if (availabilities.isEmpty()) {
            throw NoAvailabilityFoundExceptions("No volunteers available on the given date")
        }

        val volunteers = availabilities.stream()
            .filter { it.avail }
            .map { it.volunteer }
            .collect(Collectors.toList())

        if (volunteers.isEmpty()) {
            throw NoAvailabilityFoundExceptions("No volunteers available on the given date")
        }

        return volunteers
    }

    @Throws(NoVolunteerFoundExceptions::class, NoAvailabilityFoundExceptions::class)
    fun updateAvailability(volunteerId: Int, date: LocalDate, isAvail: String): Availability {
        val volunteer = volunteerRepository.findById(volunteerId)
            .orElseThrow { NoVolunteerFoundExceptions("No volunteer found with the given ID: $volunteerId") }

        val availability = availabilityRepo.findByVolunteerAndDate(volunteer, date)
            .orElseThrow { NoAvailabilityFoundExceptions("No availability record found for the given volunteer and date") }
        val avail = isAvail.equals("true", ignoreCase = true)
        availability.avail = avail
        return availabilityRepo.save(availability)
    }

    @Throws(NoVolunteerFoundExceptions::class, NoAvailabilityFoundExceptions::class)
    fun deleteAvail(volunteerId: Int, date: LocalDate) {
        val volunteer = volunteerRepository.findById(volunteerId)
            .orElseThrow { NoVolunteerFoundExceptions("No volunteer found with the given ID: $volunteerId") }
        val availability = availabilityRepo.findByVolunteerAndDate(volunteer, date)
            .orElseThrow { NoAvailabilityFoundExceptions("No availability record found for the given volunteer and date") }
        availabilityRepo.delete(availability)
    }

    @Throws(NoAvailabilityFoundExceptions::class)
    fun getAllAvailabilities(): List<Availability> {
        val allAvails = availabilityRepo.findAll()
        if (allAvails.isEmpty()) {
            throw NoAvailabilityFoundExceptions("There is no availability found!")
        }
        return allAvails
    }
}

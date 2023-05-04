package com.ntu.sctp.group1;

import com.ntu.sctp.group1.Exceptions.NoVolunteerFoundExceptions;
import com.ntu.sctp.group1.Service.VolunteerService;
import com.ntu.sctp.group1.entity.Volunteer;
import com.ntu.sctp.group1.repository.VolunteerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VolunteerServiceTest {

    @Mock
    VolunteerRepository volunteerRepository;

    @InjectMocks
    VolunteerService volunteerService;

    @Test
    public void testGetVolunteerById() throws NoVolunteerFoundExceptions {
        // create test data
        Volunteer volunteer = new Volunteer();
        volunteer.setName("John Doe");
        volunteer.setEmail("johndoe@example.com");
        volunteer.setContact("12345678");

        when(volunteerRepository.findById(1)).thenReturn(Optional.of(volunteer)); //change to 2 to fail the test

        // getVolunteerById
        Volunteer result = volunteerService.getVolunteerById(1);

        // check result
        assertNotNull(result);
        assertEquals(volunteer.getName(), result.getName());
        assertEquals(volunteer.getEmail(), result.getEmail());
        assertEquals(volunteer.getContact(), result.getContact());
    }
}
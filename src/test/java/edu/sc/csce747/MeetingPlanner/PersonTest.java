package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class PersonTest {
	private Person person;
	private Meeting testMeeting;
	private ArrayList<Person> attendees;
	private Room testRoom;
	
	@Before
	public void setUp() {
		person = new Person("John Doe");
		attendees = new ArrayList<Person>();
		attendees.add(person);
		testRoom = new Room("CONF01");
		testMeeting = new Meeting(5, 15, 9, 10, attendees, testRoom, "Test Meeting");
	}
	
	// ========== CONSTRUCTOR TESTS ==========
	
	@Test
	public void testDefaultConstructor() {
		Person defaultPerson = new Person();
		assertNotNull("Default constructor should create person", defaultPerson);
		assertEquals("Default name should be empty string", "", defaultPerson.getName());
	}
	
	@Test
	public void testConstructor_withName() {
		Person namedPerson = new Person("Jane Smith");
		assertEquals("Name should be set correctly", "Jane Smith", namedPerson.getName());
	}
	
	// ========== MEETING MANAGEMENT TESTS ==========
	
	@Test
	public void testAddMeeting_valid() {
		try {
			person.addMeeting(testMeeting);
			assertTrue("Person should be busy during meeting time", 
				person.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for valid meeting: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_conflict() {
		try {
			person.addMeeting(testMeeting);
			
			// Try to add conflicting meeting
			Meeting conflictMeeting = new Meeting(5, 15, 9, 11, attendees, testRoom, "Conflict Meeting");
			person.addMeeting(conflictMeeting);
			fail("Should throw TimeConflictException for conflicting meetings");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention person name", 
				e.getMessage().contains("John Doe"));
			assertTrue("Exception should mention conflict", 
				e.getMessage().contains("Conflict"));
		}
	}
	
	@Test
	public void testRemoveMeeting() {
		try {
			person.addMeeting(testMeeting);
			assertTrue("Person should be busy before removal", 
				person.isBusy(5, 15, 9, 10));
			
			person.removeMeeting(5, 15, 0);
			assertFalse("Person should not be busy after removal", 
				person.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	// ========== AVAILABILITY TESTS ==========
	
	@Test
	public void testIsBusy_notBusy() {
		try {
			assertFalse("Person should not be busy when no meetings scheduled", 
				person.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for valid time check: " + e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_busy() {
		try {
			person.addMeeting(testMeeting);
			assertTrue("Person should be busy during meeting time", 
				person.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_invalidDate() {
		try {
			person.isBusy(13, 15, 9, 10);
			fail("Should throw TimeConflictException for invalid month");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention invalid month", 
				e.getMessage().contains("Month does not exist"));
		}
	}
	
	// ========== AGENDA PRINTING TESTS ==========
	
	@Test
	public void testPrintAgenda_month() {
		try {
			person.addMeeting(testMeeting);
			String agenda = person.printAgenda(5);
			assertTrue("Monthly agenda should contain meeting", 
				agenda.contains("Test Meeting"));
			assertTrue("Monthly agenda should contain month", 
				agenda.contains("Agenda for 5"));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_day() {
		try {
			person.addMeeting(testMeeting);
			String agenda = person.printAgenda(5, 15);
			assertTrue("Daily agenda should contain meeting", 
				agenda.contains("Test Meeting"));
			assertTrue("Daily agenda should contain date", 
				agenda.contains("5/15"));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_emptyMonth() {
		String agenda = person.printAgenda(5);
		assertTrue("Empty monthly agenda should contain month header", 
			agenda.contains("Agenda for 5"));
		// Should not contain any meeting details
		assertFalse("Empty agenda should not contain meeting details", 
			agenda.contains("Test Meeting"));
	}
	
	@Test
	public void testPrintAgenda_emptyDay() {
		String agenda = person.printAgenda(5, 15);
		assertTrue("Empty daily agenda should contain date header", 
			agenda.contains("5/15"));
		// Should not contain any meeting details
		assertFalse("Empty agenda should not contain meeting details", 
			agenda.contains("Test Meeting"));
	}
	
	// ========== MEETING RETRIEVAL TESTS ==========
	
	@Test
	public void testGetMeeting() {
		try {
			person.addMeeting(testMeeting);
			Meeting retrieved = person.getMeeting(5, 15, 0);
			assertEquals("Retrieved meeting should match added meeting", 
				testMeeting.getDescription(), retrieved.getDescription());
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	// ========== EDGE CASE TESTS ==========
	
	@Test
	public void testPerson_emptyName() {
		Person emptyNamePerson = new Person("");
		assertEquals("Should handle empty name", "", emptyNamePerson.getName());
	}
	
	@Test
	public void testPerson_nullName() {
		Person nullNamePerson = new Person(null);
		assertNull("Should handle null name", nullNamePerson.getName());
	}
	
	@Test
	public void testPerson_longName() {
		String longName = "Dr. John Michael Smith-Wilson Jr. III Esq.";
		Person longNamePerson = new Person(longName);
		assertEquals("Should handle long names", longName, longNamePerson.getName());
	}
	
	@Test
	public void testPerson_specialCharactersInName() {
		String specialName = "José María O'Connor-Smith";
		Person specialPerson = new Person(specialName);
		assertEquals("Should handle special characters in names", specialName, specialPerson.getName());
	}
	
	// ========== MULTIPLE MEETINGS TESTS ==========
	
	@Test
	public void testMultipleMeetings_sameDay() {
		try {
			Meeting morning = new Meeting(5, 15, 9, 10, attendees, testRoom, "Morning Meeting");
			Meeting afternoon = new Meeting(5, 15, 14, 15, attendees, testRoom, "Afternoon Meeting");
			
			person.addMeeting(morning);
			person.addMeeting(afternoon);
			
			assertTrue("Should be busy during morning meeting", 
				person.isBusy(5, 15, 9, 10));
			assertTrue("Should be busy during afternoon meeting", 
				person.isBusy(5, 15, 14, 15));
			assertFalse("Should not be busy between meetings", 
				person.isBusy(5, 15, 11, 13));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for non-overlapping meetings: " + e.getMessage());
		}
	}
	
	@Test
	public void testMultipleMeetings_differentDays() {
		try {
			Meeting day1 = new Meeting(5, 15, 9, 10, attendees, testRoom, "Day 1 Meeting");
			Meeting day2 = new Meeting(5, 16, 9, 10, attendees, testRoom, "Day 2 Meeting");
			
			person.addMeeting(day1);
			person.addMeeting(day2);
			
			assertTrue("Should be busy on day 1", person.isBusy(5, 15, 9, 10));
			assertTrue("Should be busy on day 2", person.isBusy(5, 16, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for meetings on different days: " + e.getMessage());
		}
	}
	
	// ========== VACATION TESTS ==========
	
	@Test
	public void testVacation_fullDay() {
		try {
			Meeting vacation = new Meeting(7, 4, 0, 23, attendees, new Room(), "Independence Day Vacation");
			person.addMeeting(vacation);
			
			assertTrue("Should be busy all day during vacation", 
				person.isBusy(7, 4, 9, 17));
			assertTrue("Should be busy early morning during vacation", 
				person.isBusy(7, 4, 0, 1));
			assertTrue("Should be busy late evening during vacation", 
				person.isBusy(7, 4, 22, 23));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for vacation: " + e.getMessage());
		}
	}
}

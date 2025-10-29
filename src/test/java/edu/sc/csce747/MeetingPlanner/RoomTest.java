package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class RoomTest {
	private Room room;
	private Meeting testMeeting;
	private ArrayList<Person> attendees;
	
	@Before
	public void setUp() {
		room = new Room("CONF01");
		attendees = new ArrayList<Person>();
		attendees.add(new Person("John Doe"));
		testMeeting = new Meeting(5, 15, 9, 10, attendees, room, "Test Meeting");
	}
	
	// ========== CONSTRUCTOR TESTS ==========
	
	@Test
	public void testDefaultConstructor() {
		Room defaultRoom = new Room();
		assertNotNull("Default constructor should create room", defaultRoom);
		assertEquals("Default ID should be empty string", "", defaultRoom.getID());
	}
	
	@Test
	public void testConstructor_withID() {
		Room namedRoom = new Room("CONF02");
		assertEquals("ID should be set correctly", "CONF02", namedRoom.getID());
	}
	
	// ========== MEETING MANAGEMENT TESTS ==========
	
	@Test
	public void testAddMeeting_valid() {
		try {
			room.addMeeting(testMeeting);
			assertTrue("Room should be busy during meeting time", 
				room.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for valid meeting: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_conflict() {
		try {
			room.addMeeting(testMeeting);
			
			// Try to add conflicting meeting
			Meeting conflictMeeting = new Meeting(5, 15, 9, 11, attendees, room, "Conflict Meeting");
			room.addMeeting(conflictMeeting);
			fail("Should throw TimeConflictException for conflicting meetings");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention room ID", 
				e.getMessage().contains("CONF01"));
			assertTrue("Exception should mention conflict", 
				e.getMessage().contains("Conflict"));
		}
	}
	
	@Test
	public void testRemoveMeeting() {
		try {
			room.addMeeting(testMeeting);
			assertTrue("Room should be busy before removal", 
				room.isBusy(5, 15, 9, 10));
			
			room.removeMeeting(5, 15, 0);
			assertFalse("Room should not be busy after removal", 
				room.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	// ========== AVAILABILITY TESTS ==========
	
	@Test
	public void testIsBusy_notBusy() {
		try {
			assertFalse("Room should not be busy when no meetings scheduled", 
				room.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for valid time check: " + e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_busy() {
		try {
			room.addMeeting(testMeeting);
			assertTrue("Room should be busy during meeting time", 
				room.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_invalidDate() {
		try {
			room.isBusy(13, 15, 9, 10);
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
			room.addMeeting(testMeeting);
			String agenda = room.printAgenda(5);
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
			room.addMeeting(testMeeting);
			String agenda = room.printAgenda(5, 15);
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
		String agenda = room.printAgenda(5);
		assertTrue("Empty monthly agenda should contain month header", 
			agenda.contains("Agenda for 5"));
		assertFalse("Empty agenda should not contain meeting details", 
			agenda.contains("Test Meeting"));
	}
	
	@Test
	public void testPrintAgenda_emptyDay() {
		String agenda = room.printAgenda(5, 15);
		assertTrue("Empty daily agenda should contain date header", 
			agenda.contains("5/15"));
		assertFalse("Empty agenda should not contain meeting details", 
			agenda.contains("Test Meeting"));
	}
	
	// ========== MEETING RETRIEVAL TESTS ==========
	
	@Test
	public void testGetMeeting() {
		try {
			room.addMeeting(testMeeting);
			Meeting retrieved = room.getMeeting(5, 15, 0);
			assertEquals("Retrieved meeting should match added meeting", 
				testMeeting.getDescription(), retrieved.getDescription());
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	// ========== EDGE CASE TESTS ==========
	
	@Test
	public void testRoom_emptyID() {
		Room emptyIDRoom = new Room("");
		assertEquals("Should handle empty ID", "", emptyIDRoom.getID());
	}
	
	@Test
	public void testRoom_nullID() {
		Room nullIDRoom = new Room(null);
		assertNull("Should handle null ID", nullIDRoom.getID());
	}
	
	@Test
	public void testRoom_longID() {
		String longID = "CONFERENCE_ROOM_BUILDING_A_FLOOR_2_EAST_WING_001";
		Room longIDRoom = new Room(longID);
		assertEquals("Should handle long IDs", longID, longIDRoom.getID());
	}
	
	@Test
	public void testRoom_specialCharactersInID() {
		String specialID = "CONF-01_A&B";
		Room specialRoom = new Room(specialID);
		assertEquals("Should handle special characters in IDs", specialID, specialRoom.getID());
	}
	
	// ========== MULTIPLE MEETINGS TESTS ==========
	
	@Test
	public void testMultipleMeetings_sameDay() {
		try {
			Meeting morning = new Meeting(5, 15, 9, 10, attendees, room, "Morning Meeting");
			Meeting afternoon = new Meeting(5, 15, 14, 15, attendees, room, "Afternoon Meeting");
			
			room.addMeeting(morning);
			room.addMeeting(afternoon);
			
			assertTrue("Should be busy during morning meeting", 
				room.isBusy(5, 15, 9, 10));
			assertTrue("Should be busy during afternoon meeting", 
				room.isBusy(5, 15, 14, 15));
			assertFalse("Should not be busy between meetings", 
				room.isBusy(5, 15, 11, 13));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for non-overlapping meetings: " + e.getMessage());
		}
	}
	
	@Test
	public void testMultipleMeetings_differentDays() {
		try {
			Meeting day1 = new Meeting(5, 15, 9, 10, attendees, room, "Day 1 Meeting");
			Meeting day2 = new Meeting(5, 16, 9, 10, attendees, room, "Day 2 Meeting");
			
			room.addMeeting(day1);
			room.addMeeting(day2);
			
			assertTrue("Should be busy on day 1", room.isBusy(5, 15, 9, 10));
			assertTrue("Should be busy on day 2", room.isBusy(5, 16, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for meetings on different days: " + e.getMessage());
		}
	}
	
	// ========== ROOM BOOKING PATTERNS TESTS ==========
	
	@Test
	public void testRoom_allDayBooking() {
		try {
			Meeting allDay = new Meeting(6, 15, 0, 23, attendees, room, "All Day Conference");
			room.addMeeting(allDay);
			
			assertTrue("Should be busy all day", room.isBusy(6, 15, 9, 17));
			assertTrue("Should be busy early morning", room.isBusy(6, 15, 0, 1));
			assertTrue("Should be busy late evening", room.isBusy(6, 15, 22, 23));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for all-day booking: " + e.getMessage());
		}
	}
	
	@Test
	public void testRoom_maintenanceMode() {
		try {
			Meeting maintenance = new Meeting(8, 20, 0, 23, new ArrayList<Person>(), room, "Room Maintenance");
			room.addMeeting(maintenance);
			
			assertTrue("Room should be unavailable during maintenance", 
				room.isBusy(8, 20, 10, 11));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for maintenance booking: " + e.getMessage());
		}
	}
	
	// ========== BUSINESS HOURS TESTS ==========
	
	@Test
	public void testRoom_businessHours() {
		try {
			Meeting businessMeeting = new Meeting(5, 15, 9, 17, attendees, room, "Business Hours Meeting");
			room.addMeeting(businessMeeting);
			
			assertTrue("Should be busy during business hours", 
				room.isBusy(5, 15, 12, 13));
			assertFalse("Should not be busy outside business hours", 
				room.isBusy(5, 15, 18, 19));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testRoom_afterHoursMeeting() {
		try {
			Meeting afterHours = new Meeting(5, 15, 18, 20, attendees, room, "After Hours Meeting");
			room.addMeeting(afterHours);
			
			assertTrue("Should be busy during after-hours meeting", 
				room.isBusy(5, 15, 18, 20));
			assertFalse("Should not be busy during business hours", 
				room.isBusy(5, 15, 9, 17));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
}

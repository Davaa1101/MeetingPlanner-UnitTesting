package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class CalendarTest {
	private Calendar calendar;
	private Meeting testMeeting;
	private ArrayList<Person> attendees;
	private Room testRoom;
	
	@Before
	public void setUp() {
		calendar = new Calendar();
		attendees = new ArrayList<Person>();
		attendees.add(new Person("Test Person"));
		testRoom = new Room("TEST01");
		testMeeting = new Meeting(5, 15, 9, 10, attendees, testRoom, "Test Meeting");
	}
	
	// ========== NORMAL OPERATION TESTS ==========
	
	@Test
	public void testAddMeeting_holiday() {
		// Create Midsommar holiday
		Calendar calendar = new Calendar();
		// Add to calendar object.
		try {
			Meeting midsommar = new Meeting(6, 26, "Midsommar");
			calendar.addMeeting(midsommar);
			// Verify that it was added.
			Boolean added = calendar.isBusy(6, 26, 0, 23);
			assertTrue("Midsommar should be marked as busy on the calendar",added);
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_validMeeting() {
		try {
			calendar.addMeeting(testMeeting);
			assertTrue("Calendar should be busy during meeting time", 
				calendar.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for valid meeting: " + e.getMessage());
		}
	}
	
	@Test
	public void testIsBusy_emptyCalendar() {
		try {
			assertFalse("Empty calendar should not be busy", 
				calendar.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception for valid time check: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_month() {
		try {
			calendar.addMeeting(testMeeting);
			String agenda = calendar.printAgenda(5);
			assertTrue("Agenda should contain meeting description", 
				agenda.contains("Test Meeting"));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testPrintAgenda_day() {
		try {
			calendar.addMeeting(testMeeting);
			String agenda = calendar.printAgenda(5, 15);
			assertTrue("Daily agenda should contain meeting description", 
				agenda.contains("Test Meeting"));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testClearSchedule() {
		try {
			calendar.addMeeting(testMeeting);
			assertTrue("Calendar should be busy before clearing", 
				calendar.isBusy(5, 15, 9, 10));
			calendar.clearSchedule(5, 15);
			assertFalse("Calendar should not be busy after clearing", 
				calendar.isBusy(5, 15, 9, 10));
		} catch(TimeConflictException e) {
			fail("Should not throw exception: " + e.getMessage());
		}
	}
	
	// ========== ERROR CONDITION TESTS ==========
	
	@Test
	public void testAddMeeting_invalidDay_tooHigh() {
		Meeting invalidMeeting = new Meeting(5, 32, 9, 10, attendees, testRoom, "Invalid Day");
		try {
			calendar.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for day 32");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention invalid day", 
				e.getMessage().contains("Day does not exist"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidDay_tooLow() {
		Meeting invalidMeeting = new Meeting(5, 0, 9, 10, attendees, testRoom, "Invalid Day");
		try {
			calendar.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for day 0");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention invalid day", 
				e.getMessage().contains("Day does not exist"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidMonth_tooHigh() {
		Meeting invalidMeeting = new Meeting(13, 15, 9, 10, attendees, testRoom, "Invalid Month");
		try {
			calendar.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for month 13");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention invalid month", 
				e.getMessage().contains("Month does not exist"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidMonth_tooLow() {
		Meeting invalidMeeting = new Meeting(0, 15, 9, 10, attendees, testRoom, "Invalid Month");
		try {
			calendar.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for month 0");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention invalid month", 
				e.getMessage().contains("Month does not exist"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidHour_tooHigh() {
		Meeting invalidMeeting = new Meeting(5, 15, 24, 25, attendees, testRoom, "Invalid Hour");
		try {
			calendar.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for hour 24");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention illegal hour", 
				e.getMessage().contains("Illegal hour"));
		}
	}
	
	@Test
	public void testAddMeeting_invalidHour_negative() {
		Meeting invalidMeeting = new Meeting(5, 15, -1, 10, attendees, testRoom, "Negative Hour");
		try {
			calendar.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException for negative hour");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention illegal hour", 
				e.getMessage().contains("Illegal hour"));
		}
	}
	
	@Test
	public void testAddMeeting_startAfterEnd() {
		Meeting invalidMeeting = new Meeting(5, 15, 15, 10, attendees, testRoom, "Backwards Time");
		try {
			calendar.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException when start time is after end time");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention meeting timing issue", 
				e.getMessage().contains("Meeting starts before it ends"));
		}
	}
	
	@Test
	public void testAddMeeting_startEqualsEnd() {
		Meeting invalidMeeting = new Meeting(5, 15, 10, 10, attendees, testRoom, "Same Start/End");
		try {
			calendar.addMeeting(invalidMeeting);
			fail("Should throw TimeConflictException when start time equals end time");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention meeting timing issue", 
				e.getMessage().contains("Meeting starts before it ends"));
		}
	}
	
	@Test
	public void testAddMeeting_february30() {
		Meeting invalidMeeting = new Meeting(2, 30, 9, 10, attendees, testRoom, "Feb 30");
		try {
			calendar.addMeeting(invalidMeeting);
			assertTrue("February 30 should be blocked as 'Day does not exist'", 
				calendar.isBusy(2, 30, 0, 23));
		} catch(TimeConflictException e) {
			// This should not throw an exception as the day is pre-blocked
			fail("February 30 should be pre-blocked, not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_april31() {
		Meeting invalidMeeting = new Meeting(4, 31, 9, 10, attendees, testRoom, "April 31");
		try {
			calendar.addMeeting(invalidMeeting);
			assertTrue("April 31 should be blocked as 'Day does not exist'", 
				calendar.isBusy(4, 31, 0, 23));
		} catch(TimeConflictException e) {
			fail("April 31 should be pre-blocked, not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_timeConflict() {
		try {
			// Add first meeting
			calendar.addMeeting(testMeeting);
			
			// Try to add conflicting meeting
			Meeting conflictMeeting = new Meeting(5, 15, 9, 11, attendees, testRoom, "Conflict Meeting");
			calendar.addMeeting(conflictMeeting);
			fail("Should throw TimeConflictException for overlapping meetings");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention overlap", 
				e.getMessage().contains("Overlap"));
		}
	}
	
	@Test
	public void testIsBusy_invalidDate() {
		try {
			calendar.isBusy(13, 15, 9, 10);
			fail("Should throw TimeConflictException for invalid month");
		} catch(TimeConflictException e) {
			assertTrue("Exception should mention invalid month", 
				e.getMessage().contains("Month does not exist"));
		}
	}
	
	// ========== BOUNDARY CONDITION TESTS ==========
	
	@Test
	public void testAddMeeting_validBoundaryTimes() {
		try {
			Meeting earlyMeeting = new Meeting(1, 1, 0, 1, attendees, testRoom, "Early Meeting");
			Meeting lateMeeting = new Meeting(12, 31, 22, 23, attendees, testRoom, "Late Meeting");
			
			calendar.addMeeting(earlyMeeting);
			calendar.clearSchedule(1, 1); // Clear to avoid conflict
			calendar.addMeeting(lateMeeting);
			
			assertTrue("Should handle boundary times correctly", true);
		} catch(TimeConflictException e) {
			fail("Should not throw exception for valid boundary times: " + e.getMessage());
		}
	}
	
	@Test
	public void testAddMeeting_adjacentMeetings() {
		try {
			Meeting meeting1 = new Meeting(5, 15, 9, 10, attendees, testRoom, "Meeting 1");
			Meeting meeting2 = new Meeting(5, 15, 10, 11, attendees, testRoom, "Meeting 2");
			
			calendar.addMeeting(meeting1);
			calendar.addMeeting(meeting2);
			fail("Should throw exception for adjacent meetings with same end/start time");
		} catch(TimeConflictException e) {
			assertTrue("Should detect conflict for adjacent meetings sharing a time boundary", 
				e.getMessage().contains("Overlap"));
		}
	}
}

package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class MeetingTest {
	private ArrayList<Person> attendees;
	private Room testRoom;
	private Person person1, person2;
	
	@Before
	public void setUp() {
		attendees = new ArrayList<Person>();
		person1 = new Person("John Doe");
		person2 = new Person("Jane Smith");
		attendees.add(person1);
		attendees.add(person2);
		testRoom = new Room("CONF01");
	}
	
	// ========== CONSTRUCTOR TESTS ==========
	
	@Test
	public void testDefaultConstructor() {
		Meeting meeting = new Meeting();
		assertNotNull("Default constructor should create meeting object", meeting);
	}
	
	@Test
	public void testConstructor_monthDay() {
		Meeting meeting = new Meeting(5, 15);
		assertEquals("Month should be set correctly", 5, meeting.getMonth());
		assertEquals("Day should be set correctly", 15, meeting.getDay());
		assertEquals("Start time should default to 0", 0, meeting.getStartTime());
		assertEquals("End time should default to 23", 23, meeting.getEndTime());
	}
	
	@Test
	public void testConstructor_monthDayDescription() {
		Meeting meeting = new Meeting(6, 20, "Summer Solstice");
		assertEquals("Month should be set correctly", 6, meeting.getMonth());
		assertEquals("Day should be set correctly", 20, meeting.getDay());
		assertEquals("Description should be set correctly", "Summer Solstice", meeting.getDescription());
		assertEquals("Start time should default to 0", 0, meeting.getStartTime());
		assertEquals("End time should default to 23", 23, meeting.getEndTime());
	}
	
	@Test
	public void testConstructor_monthDayStartEnd() {
		Meeting meeting = new Meeting(8, 10, 14, 16);
		assertEquals("Month should be set correctly", 8, meeting.getMonth());
		assertEquals("Day should be set correctly", 10, meeting.getDay());
		assertEquals("Start time should be set correctly", 14, meeting.getStartTime());
		assertEquals("End time should be set correctly", 16, meeting.getEndTime());
	}
	
	@Test
	public void testConstructor_fullDetails() {
		Meeting meeting = new Meeting(3, 25, 10, 12, attendees, testRoom, "Project Review");
		assertEquals("Month should be set correctly", 3, meeting.getMonth());
		assertEquals("Day should be set correctly", 25, meeting.getDay());
		assertEquals("Start time should be set correctly", 10, meeting.getStartTime());
		assertEquals("End time should be set correctly", 12, meeting.getEndTime());
		assertEquals("Attendees should be set correctly", attendees, meeting.getAttendees());
		assertEquals("Room should be set correctly", testRoom, meeting.getRoom());
		assertEquals("Description should be set correctly", "Project Review", meeting.getDescription());
	}
	
	// ========== ATTENDEE MANAGEMENT TESTS ==========
	
	@Test
	public void testAddAttendee() {
		Meeting meeting = new Meeting(4, 15, 9, 10, new ArrayList<Person>(), testRoom, "Team Meeting");
		Person newAttendee = new Person("Bob Wilson");
		
		meeting.addAttendee(newAttendee);
		assertTrue("Attendee should be added to the meeting", 
			meeting.getAttendees().contains(newAttendee));
	}
	
	@Test
	public void testRemoveAttendee() {
		Meeting meeting = new Meeting(4, 15, 9, 10, attendees, testRoom, "Team Meeting");
		
		meeting.removeAttendee(person1);
		assertFalse("Attendee should be removed from the meeting", 
			meeting.getAttendees().contains(person1));
		assertTrue("Other attendees should remain", 
			meeting.getAttendees().contains(person2));
	}
	
	// ========== GETTER/SETTER TESTS ==========
	
	@Test
	public void testSettersAndGetters() {
		Meeting meeting = new Meeting();
		
		meeting.setMonth(7);
		assertEquals("Month setter/getter should work", 7, meeting.getMonth());
		
		meeting.setDay(4);
		assertEquals("Day setter/getter should work", 4, meeting.getDay());
		
		meeting.setStartTime(13);
		assertEquals("Start time setter/getter should work", 13, meeting.getStartTime());
		
		meeting.setEndTime(15);
		assertEquals("End time setter/getter should work", 15, meeting.getEndTime());
		
		meeting.setRoom(testRoom);
		assertEquals("Room setter/getter should work", testRoom, meeting.getRoom());
		
		meeting.setDescription("Independence Day Celebration");
		assertEquals("Description setter/getter should work", 
			"Independence Day Celebration", meeting.getDescription());
	}
	
	// ========== STRING REPRESENTATION TESTS ==========
	
	@Test
	public void testToString_withAttendees() {
		Meeting meeting = new Meeting(5, 15, 14, 16, attendees, testRoom, "Team Standup");
		String result = meeting.toString();
		
		assertTrue("toString should contain date", result.contains("5/15"));
		assertTrue("toString should contain time", result.contains("14 - 16"));
		assertTrue("toString should contain room", result.contains("CONF01"));
		assertTrue("ToString should contain description", result.contains("Team Standup"));
		assertTrue("toString should contain attendee names", result.contains("John Doe"));
		assertTrue("toString should contain attendee names", result.contains("Jane Smith"));
	}
	
	@Test
	public void testToString_emptyAttendees() {
		ArrayList<Person> emptyAttendees = new ArrayList<Person>();
		Meeting meeting = new Meeting(12, 25, 0, 23, emptyAttendees, testRoom, "Christmas Holiday");
		String result = meeting.toString();
		
		assertTrue("toString should contain date", result.contains("12/25"));
		assertTrue("toString should contain description", result.contains("Christmas Holiday"));
		// Should handle empty attendees gracefully (may cause IndexOutOfBoundsException)
	}
	
	// ========== EDGE CASE TESTS ==========
	
	@Test
	public void testMeeting_nullValues() {
		// Test with null attendees
		Meeting meeting1 = new Meeting(1, 1, 9, 10, null, testRoom, "Null Attendees Test");
		assertNull("Attendees can be null", meeting1.getAttendees());
		
		// Test with null room
		Meeting meeting2 = new Meeting(1, 1, 9, 10, attendees, null, "Null Room Test");
		assertNull("Room can be null", meeting2.getRoom());
		
		// Test with null description
		Meeting meeting3 = new Meeting(1, 1, 9, 10, attendees, testRoom, null);
		assertNull("Description can be null", meeting3.getDescription());
	}
	
	@Test
	public void testMeeting_extremeValues() {
		// Test with extreme but potentially valid values
		Meeting meeting = new Meeting(1, 1, 0, 23, attendees, testRoom, "Full Day Meeting");
		assertEquals("Should handle full day meeting", 0, meeting.getStartTime());
		assertEquals("Should handle full day meeting", 23, meeting.getEndTime());
	}
	
	@Test
	public void testMeeting_longDescription() {
		String longDescription = "This is a very long meeting description that contains many words and should test how the meeting class handles longer text inputs for the description field.";
		Meeting meeting = new Meeting(6, 15, 10, 11, attendees, testRoom, longDescription);
		assertEquals("Should handle long descriptions", longDescription, meeting.getDescription());
	}
	
	// ========== BOUNDARY VALUE TESTS ==========
	
	@Test
	public void testMeeting_boundaryDates() {
		// Test first day of year
		Meeting newYear = new Meeting(1, 1, 0, 1, attendees, testRoom, "New Year");
		assertEquals("Should handle first day of year", 1, newYear.getMonth());
		assertEquals("Should handle first day of year", 1, newYear.getDay());
		
		// Test last day of year  
		Meeting newYearEve = new Meeting(12, 31, 23, 23, attendees, testRoom, "New Year's Eve");
		assertEquals("Should handle last day of year", 12, newYearEve.getMonth());
		assertEquals("Should handle last day of year", 31, newYearEve.getDay());
	}
	
	@Test
	public void testMeeting_boundaryTimes() {
		// Test earliest time
		Meeting earlyMeeting = new Meeting(6, 15, 0, 1, attendees, testRoom, "Early Meeting");
		assertEquals("Should handle earliest hour", 0, earlyMeeting.getStartTime());
		
		// Test latest time
		Meeting lateMeeting = new Meeting(6, 15, 22, 23, attendees, testRoom, "Late Meeting");
		assertEquals("Should handle latest hour", 23, lateMeeting.getEndTime());
	}
}

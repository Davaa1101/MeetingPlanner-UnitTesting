package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class OrganizationTest {
	private Organization organization;
	
	@Before
	public void setUp() {
		organization = new Organization();
	}
	
	// ========== CONSTRUCTOR TESTS ==========
	
	@Test
	public void testDefaultConstructor() {
		Organization org = new Organization();
		assertNotNull("Organization should be created", org);
		assertNotNull("Employees list should be initialized", org.getEmployees());
		assertNotNull("Rooms list should be initialized", org.getRooms());
	}
	
	@Test
	public void testDefaultEmployees() {
		ArrayList<Person> employees = organization.getEmployees();
		assertEquals("Should have 5 default employees", 5, employees.size());
		
		// Check for specific default employees
		boolean foundGreg = false, foundManton = false, foundJohn = false, foundRyan = false, foundCsilla = false;
		for(Person employee : employees) {
			String name = employee.getName();
			if("Greg Gay".equals(name)) foundGreg = true;
			if("Manton Matthews".equals(name)) foundManton = true;
			if("John Rose".equals(name)) foundJohn = true;
			if("Ryan Austin".equals(name)) foundRyan = true;
			if("Csilla Farkas".equals(name)) foundCsilla = true;
		}
		
		assertTrue("Should contain Greg Gay", foundGreg);
		assertTrue("Should contain Manton Matthews", foundManton);
		assertTrue("Should contain John Rose", foundJohn);
		assertTrue("Should contain Ryan Austin", foundRyan);
		assertTrue("Should contain Csilla Farkas", foundCsilla);
	}
	
	@Test
	public void testDefaultRooms() {
		ArrayList<Room> rooms = organization.getRooms();
		assertEquals("Should have 5 default rooms", 5, rooms.size());
		
		// Check for specific default rooms
		boolean found2A01 = false, found2A02 = false, found2A03 = false, found2A04 = false, found2A05 = false;
		for(Room room : rooms) {
			String id = room.getID();
			if("2A01".equals(id)) found2A01 = true;
			if("2A02".equals(id)) found2A02 = true;
			if("2A03".equals(id)) found2A03 = true;
			if("2A04".equals(id)) found2A04 = true;
			if("2A05".equals(id)) found2A05 = true;
		}
		
		assertTrue("Should contain room 2A01", found2A01);
		assertTrue("Should contain room 2A02", found2A02);
		assertTrue("Should contain room 2A03", found2A03);
		assertTrue("Should contain room 2A04", found2A04);
		assertTrue("Should contain room 2A05", found2A05);
	}
	
	// ========== EMPLOYEE RETRIEVAL TESTS ==========
	
	@Test
	public void testGetEmployee_validName() {
		try {
			Person employee = organization.getEmployee("Greg Gay");
			assertNotNull("Should find existing employee", employee);
			assertEquals("Employee name should match", "Greg Gay", employee.getName());
		} catch(Exception e) {
			fail("Should not throw exception for valid employee: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_invalidName() {
		try {
			Person employee = organization.getEmployee("Non Existent Person");
			fail("Should throw exception for non-existent employee");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_nullName() {
		try {
			Person employee = organization.getEmployee(null);
			fail("Should throw exception for null employee name");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_emptyName() {
		try {
			Person employee = organization.getEmployee("");
			fail("Should throw exception for empty employee name");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_caseSensitive() {
		try {
			Person employee = organization.getEmployee("greg gay");
			fail("Should throw exception for incorrect case");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested employee does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetEmployee_partialName() {
		try {
			Person employee = organization.getEmployee("Greg");
			fail("Should throw exception for partial name");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested employee does not exist", e.getMessage());
		}
	}
	
	// ========== ROOM RETRIEVAL TESTS ==========
	
	@Test
	public void testGetRoom_validID() {
		try {
			Room room = organization.getRoom("2A01");
			assertNotNull("Should find existing room", room);
			assertEquals("Room ID should match", "2A01", room.getID());
		} catch(Exception e) {
			fail("Should not throw exception for valid room: " + e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_invalidID() {
		try {
			Room room = organization.getRoom("NONEXISTENT");
			fail("Should throw exception for non-existent room");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_nullID() {
		try {
			Room room = organization.getRoom(null);
			fail("Should throw exception for null room ID");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_emptyID() {
		try {
			Room room = organization.getRoom("");
			fail("Should throw exception for empty room ID");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_caseSensitive() {
		try {
			Room room = organization.getRoom("2a01");
			fail("Should throw exception for incorrect case");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested room does not exist", e.getMessage());
		}
	}
	
	@Test
	public void testGetRoom_partialID() {
		try {
			Room room = organization.getRoom("2A");
			fail("Should throw exception for partial ID");
		} catch(Exception e) {
			assertEquals("Exception message should be correct", 
				"Requested room does not exist", e.getMessage());
		}
	}
	
	// ========== COLLECTION TESTS ==========
	
	@Test
	public void testGetEmployees_immutability() {
		ArrayList<Person> employees1 = organization.getEmployees();
		ArrayList<Person> employees2 = organization.getEmployees();
		
		// These should reference the same list
		assertSame("Should return same employees list", employees1, employees2);
	}
	
	@Test
	public void testGetRooms_immutability() {
		ArrayList<Room> rooms1 = organization.getRooms();
		ArrayList<Room> rooms2 = organization.getRooms();
		
		// These should reference the same list
		assertSame("Should return same rooms list", rooms1, rooms2);
	}
	
	// ========== EDGE CASE TESTS ==========
	
	@Test
	public void testGetEmployee_allEmployees() {
		// Test that we can retrieve all default employees
		String[] expectedNames = {"Greg Gay", "Manton Matthews", "John Rose", "Ryan Austin", "Csilla Farkas"};
		
		for(String name : expectedNames) {
			try {
				Person employee = organization.getEmployee(name);
				assertNotNull("Should find employee: " + name, employee);
				assertEquals("Employee name should match", name, employee.getName());
			} catch(Exception e) {
				fail("Should find employee " + name + ": " + e.getMessage());
			}
		}
	}
	
	@Test
	public void testGetRoom_allRooms() {
		// Test that we can retrieve all default rooms
		String[] expectedIDs = {"2A01", "2A02", "2A03", "2A04", "2A05"};
		
		for(String id : expectedIDs) {
			try {
				Room room = organization.getRoom(id);
				assertNotNull("Should find room: " + id, room);
				assertEquals("Room ID should match", id, room.getID());
			} catch(Exception e) {
				fail("Should find room " + id + ": " + e.getMessage());
			}
		}
	}
	
	// ========== INTEGRATION TESTS ==========
	
	@Test
	public void testEmployeeScheduling() {
		try {
			Person employee = organization.getEmployee("Greg Gay");
			Room room = organization.getRoom("2A01");
			
			ArrayList<Person> attendees = new ArrayList<Person>();
			attendees.add(employee);
			
			Meeting meeting = new Meeting(5, 15, 9, 10, attendees, room, "Integration Test Meeting");
			
			employee.addMeeting(meeting);
			room.addMeeting(meeting);
			
			assertTrue("Employee should be busy", employee.isBusy(5, 15, 9, 10));
			assertTrue("Room should be busy", room.isBusy(5, 15, 9, 10));
		} catch(Exception e) {
			fail("Integration test should not throw exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testMultipleEmployeeScheduling() {
		try {
			Person employee1 = organization.getEmployee("Greg Gay");
			Person employee2 = organization.getEmployee("Manton Matthews");
			Room room = organization.getRoom("2A01");
			
			ArrayList<Person> attendees = new ArrayList<Person>();
			attendees.add(employee1);
			attendees.add(employee2);
			
			Meeting meeting = new Meeting(6, 20, 14, 16, attendees, room, "Multi-Person Meeting");
			
			employee1.addMeeting(meeting);
			employee2.addMeeting(meeting);
			room.addMeeting(meeting);
			
			assertTrue("First employee should be busy", employee1.isBusy(6, 20, 14, 16));
			assertTrue("Second employee should be busy", employee2.isBusy(6, 20, 14, 16));
			assertTrue("Room should be busy", room.isBusy(6, 20, 14, 16));
		} catch(Exception e) {
			fail("Multi-employee scheduling should not throw exception: " + e.getMessage());
		}
	}
}

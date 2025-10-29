# Meeting Planner System - Unit Testing Project

A comprehensive Java-based meeting scheduling system with extensive unit testing implementation.

## Project Structure

```
MeetingPlanner/
├── src/
│   ├── main/java/edu/sc/csce747/MeetingPlanner/
│   │   ├── Calendar.java              # Core scheduling logic
│   │   ├── Meeting.java               # Meeting representation
│   │   ├── Organization.java          # Employee/room management
│   │   ├── Person.java                # Employee with calendar
│   │   ├── PlannerInterface.java      # Main UI interface
│   │   ├── Room.java                  # Meeting room with calendar
│   │   └── TimeConflictException.java # Custom exception
│   └── test/java/edu/sc/csce747/MeetingPlanner/
│       ├── CalendarTest.java          # 20 unit tests
│       ├── MeetingTest.java           # 25 unit tests  
│       ├── OrganizationTest.java      # 13 unit tests
│       ├── PersonTest.java            # 21 unit tests
│       └── RoomTest.java              # 20 unit tests
├── build.xml                          # Apache Ant build script
├── build.bat                          # Windows batch build script
├── TEST_REPORT.md                     # Comprehensive test report
└── README.md                          # This file
```

## Features

The Meeting Planner system provides:

- **Meeting Scheduling**: Schedule meetings with multiple attendees
- **Room Management**: Track room availability and bookings
- **Employee Management**: Manage employee schedules and availability
- **Vacation Planning**: Block employee time for vacation periods
- **Conflict Detection**: Prevent double-booking of people and rooms
- **Agenda Printing**: Generate daily/monthly schedules

## Unit Testing

This project includes comprehensive unit testing with **99 test cases** covering:

### Test Categories
- **Normal Operations** (35 tests): Valid scenarios and expected behavior
- **Error Conditions** (42 tests): Invalid inputs and exception handling  
- **Boundary Values** (22 tests): Edge cases and limits

### Key Testing Areas
- Date/time validation (February 30, April 31, Hour 24, etc.)
- Scheduling conflict detection
- Exception handling and error messages
- Boundary conditions and edge cases
- Integration between components

### Bugs Fixed
✅ **Calendar Month Validation**: Fixed critical bug where December was incorrectly rejected as invalid month

## Building and Running

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Apache Ant (optional - batch script provided as fallback)

### Using Apache Ant (Recommended)

```bash
# Show available targets
ant help

# Compile source code
ant compile

# Run all tests
ant test

# Run specific test class
ant test-single -Dtest.class=CalendarTest

# Generate documentation
ant javadoc

# Complete build (compile + test + docs + jar)
ant all

# Clean build artifacts
ant clean
```

### Using Batch Script (Windows)

```cmd
# Show available targets
.\build.bat help

# Compile source code  
.\build.bat compile

# Generate documentation (if javadoc available)
.\build.bat javadoc

# Complete build
.\build.bat all
```

### Using VS Code (Easiest)

1. Open the project in VS Code
2. Install Java Extension Pack
3. Use the Test Explorer to run tests
4. Use Run/Debug buttons for main application

## Running the Application

After building, you can run the meeting planner:

```bash
# Using Ant
ant run

# Using Java directly
java -cp build/classes edu.sc.csce747.MeetingPlanner.PlannerInterface

# Using JAR file
java -jar dist/MeetingPlanner.jar
```

## Test Results

- **Total Tests**: 99
- **Pass Rate**: 100% (99/99)
- **Bugs Found**: 1 (fixed)
- **Coverage**: Excellent coverage of public APIs and edge cases

See `TEST_REPORT.md` for detailed test analysis and results.

## Default Data

The system comes pre-configured with:

**Employees:**
- Greg Gay
- Manton Matthews  
- John Rose
- Ryan Austin
- Csilla Farkas

**Rooms:**
- 2A01, 2A02, 2A03, 2A04, 2A05

## Usage Examples

### Scheduling a Meeting
1. Run the application
2. Choose option 1 (Schedule a meeting)
3. Enter month (1-12), day (1-31), start hour (0-23), end hour (0-23)  
4. Select from available rooms
5. Add attendees from available employees
6. Provide meeting description

### Checking Availability
- Option 3: Check room availability at specific time
- Option 4: Check employee availability at specific time

### Viewing Schedules
- Option 5: Print room agenda (daily or monthly)
- Option 6: Check employee agenda (daily or monthly)

## Key Classes

- **`Calendar`**: Core scheduling engine with conflict detection
- **`Meeting`**: Represents a scheduled meeting with attendees and room
- **`Person`**: Employee with personal calendar
- **`Room`**: Meeting room with booking calendar  
- **`Organization`**: Manages employees and rooms
- **`TimeConflictException`**: Custom exception for scheduling conflicts

## Error Handling

The system provides robust error handling for:
- Invalid dates (Feb 30, Month 13, etc.)
- Invalid times (Hour 24, negative values)
- Scheduling conflicts (double booking)
- Non-existent employees/rooms
- Invalid time ranges (start >= end)

## Contributing

When contributing to this project:

1. Add unit tests for new functionality
2. Ensure all existing tests pass
3. Follow existing code style and patterns
4. Update documentation as needed

## License

This is an educational project developed for CSA313 Software Engineering course.

## Contact

For questions about this testing implementation, please refer to the comprehensive `TEST_REPORT.md` file which documents all testing decisions, methodologies, and results in detail.
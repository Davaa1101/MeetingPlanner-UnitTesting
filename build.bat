@echo off
REM Meeting Planner Build Script (Windows Batch Version)
REM This script provides basic build functionality when Ant is not available

set SRC_DIR=src\main\java
set TEST_SRC_DIR=src\test\java
set BUILD_DIR=build
set CLASSES_DIR=%BUILD_DIR%\classes
set TEST_CLASSES_DIR=%BUILD_DIR%\test-classes
set LIB_DIR=lib
set DOC_DIR=doc
set REPORTS_DIR=reports

if "%1"=="help" goto help
if "%1"=="clean" goto clean
if "%1"=="compile" goto compile
if "%1"=="test" goto test
if "%1"=="javadoc" goto javadoc
if "%1"=="all" goto all
if "%1"=="" goto compile

echo Unknown target: %1
goto help

:help
echo.
echo === MEETING PLANNER BUILD SCRIPT ===
echo Available targets:
echo.
echo   compile   : Compile main source code
echo   test      : Compile and run tests
echo   javadoc   : Generate API documentation
echo   clean     : Clean build artifacts  
echo   all       : Complete build (compile+test+docs)
echo   help      : Show this help message
echo.
echo Usage: build.bat [target]
echo.
goto end

:clean
echo Cleaning build directories...
if exist %BUILD_DIR% rmdir /s /q %BUILD_DIR%
if exist %DOC_DIR% rmdir /s /q %DOC_DIR%
if exist %REPORTS_DIR% rmdir /s /q %REPORTS_DIR%
echo Clean complete.
goto end

:compile
echo Creating build directories...
if not exist %BUILD_DIR% mkdir %BUILD_DIR%
if not exist %CLASSES_DIR% mkdir %CLASSES_DIR%
if not exist %TEST_CLASSES_DIR% mkdir %TEST_CLASSES_DIR%

echo Compiling main source code...
javac -d %CLASSES_DIR% -sourcepath %SRC_DIR% %SRC_DIR%\edu\sc\csce747\MeetingPlanner\*.java
if errorlevel 1 (
    echo Compilation failed!
    goto end
)
echo Main compilation complete.

echo Compiling test source code...
javac -cp %CLASSES_DIR% -d %TEST_CLASSES_DIR% -sourcepath %TEST_SRC_DIR% %TEST_SRC_DIR%\edu\sc\csce747\MeetingPlanner\*.java
if errorlevel 1 (
    echo Test compilation failed!
    goto end
)
echo Test compilation complete.
goto end

:test
call :compile
echo.
echo Running unit tests...
echo Note: This requires JUnit on the classpath. 
echo For full test execution, please use an IDE or install Apache Ant.
goto end

:javadoc
echo Creating build directories...
if not exist %BUILD_DIR% mkdir %BUILD_DIR%
if not exist %CLASSES_DIR% mkdir %CLASSES_DIR%

echo Compiling main source code...
javac -d %CLASSES_DIR% -sourcepath %SRC_DIR% %SRC_DIR%\edu\sc\csce747\MeetingPlanner\*.java
if errorlevel 1 (
    echo Compilation failed!
    goto end
)
echo Main compilation complete.

echo.
echo Generating Javadoc documentation...
if not exist %DOC_DIR% mkdir %DOC_DIR%

javadoc -d %DOC_DIR% -sourcepath %SRC_DIR% -subpackages edu.sc.csce747.MeetingPlanner -windowtitle "Meeting Planner API" -doctitle "Meeting Planner System Documentation"

if errorlevel 1 (
    echo Javadoc generation failed! Please ensure javadoc is available in PATH.
    echo Javadoc is typically included with the JDK installation.
    goto end
)
echo.
echo === JAVADOC GENERATED ===
echo Documentation available at: %DOC_DIR%\index.html
goto end

:all
call :clean
call :compile
call :javadoc
echo.
echo === BUILD COMPLETE ===
echo - Source compiled
echo - Documentation generated
echo.
echo Build artifacts:
echo   Classes: %CLASSES_DIR%
echo   Docs: %DOC_DIR%\index.html
goto end

:end
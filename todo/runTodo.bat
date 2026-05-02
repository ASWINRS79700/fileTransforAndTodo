@echo off

echo Building project...
call mvn clean install -DskipTests

IF %ERRORLEVEL% NEQ 0 (
    echo Build failed!
    pause
    exit /b %ERRORLEVEL%
)

echo Running application...

set JAVA_OPTS=-Xms512m -Xmx1024m

java -jar target\todo-0.0.1-SNAPSHOT.jar

:end
pause
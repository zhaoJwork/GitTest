
REM check JAVA_HOME & java
set "JAVA_CMD=%JAVA_HOME%/bin/java"
if "%JAVA_HOME%" == "" goto noJavaHome
if exist "%JAVA_HOME%\bin\java.exe" goto mainEntry
:noJavaHome
echo ---------------------------------------------------
echo WARN: JAVA_HOME environment variable is not set. 
echo ---------------------------------------------------
set "JAVA_CMD=java"
:mainEntry
REM set HOME_DIR
set "CURR_DIR=%cd%"
cd ..
set "appAddresslistAPI_HOME=%cd%"
cd %CURR_DIR%
"%JAVA_CMD%" -DappName=appAddresslistAPI -DappAddresslistAPI_HOME=%appAddresslistAPI_HOME% -cp "..\lib\*;..\conf" -server -Xms1024m -Xmx1024m -XX:MaxPermSize=256M  -XX:+AggressiveOpts -XX:MaxDirectMemorySize=1024m  cn.com.jamboree.search.Application >> "../logs/console.log" 2>&1 &"
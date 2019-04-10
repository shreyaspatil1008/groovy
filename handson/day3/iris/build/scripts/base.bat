@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  base startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and BASE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\base.jar;%APP_HOME%\lib\groovy-all-2.4.11.jar;%APP_HOME%\lib\sqlite-jdbc-3.7.2.jar;%APP_HOME%\lib\groovycsv-1.3.jar;%APP_HOME%\lib\grails-datastore-gorm-hibernate5-6.1.5.RELEASE.jar;%APP_HOME%\lib\h2-1.4.192.jar;%APP_HOME%\lib\tomcat-jdbc-8.5.0.jar;%APP_HOME%\lib\tomcat-embed-logging-log4j-8.5.0.jar;%APP_HOME%\lib\slf4j-log4j12-1.7.10.jar;%APP_HOME%\lib\grails-datastore-gorm-hibernate-core-6.1.5.RELEASE.jar;%APP_HOME%\lib\grails-datastore-gorm-6.1.5.RELEASE.jar;%APP_HOME%\lib\grails-datastore-gorm-validation-6.1.5.RELEASE.jar;%APP_HOME%\lib\grails-datastore-core-6.1.5.RELEASE.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.22.jar;%APP_HOME%\lib\slf4j-api-1.7.22.jar;%APP_HOME%\lib\opencsv-4.0.jar;%APP_HOME%\lib\spring-orm-4.3.9.RELEASE.jar;%APP_HOME%\lib\hibernate-core-5.1.5.Final.jar;%APP_HOME%\lib\hibernate-validator-5.2.5.Final.jar;%APP_HOME%\lib\javax.el-api-2.2.4.jar;%APP_HOME%\lib\tomcat-juli-8.5.0.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\commons-text-1.1.jar;%APP_HOME%\lib\commons-lang3-3.6.jar;%APP_HOME%\lib\commons-beanutils-1.9.3.jar;%APP_HOME%\lib\groovy-2.4.11.jar;%APP_HOME%\lib\javassist-3.21.0-GA.jar;%APP_HOME%\lib\spring-jdbc-4.3.9.RELEASE.jar;%APP_HOME%\lib\jta-1.1.jar;%APP_HOME%\lib\spring-tx-4.3.9.RELEASE.jar;%APP_HOME%\lib\spring-beans-4.3.9.RELEASE.jar;%APP_HOME%\lib\spring-core-4.3.9.RELEASE.jar;%APP_HOME%\lib\hibernate-commons-annotations-5.0.1.Final.jar;%APP_HOME%\lib\jboss-logging-3.3.0.Final.jar;%APP_HOME%\lib\hibernate-jpa-2.1-api-1.0.0.Final.jar;%APP_HOME%\lib\antlr-2.7.7.jar;%APP_HOME%\lib\geronimo-jta_1.1_spec-1.1.1.jar;%APP_HOME%\lib\jandex-2.0.3.Final.jar;%APP_HOME%\lib\classmate-1.3.0.jar;%APP_HOME%\lib\dom4j-1.6.1.jar;%APP_HOME%\lib\validation-api-1.1.0.Final.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\commons-validator-1.5.1.jar;%APP_HOME%\lib\commons-collections-3.2.2.jar;%APP_HOME%\lib\spring-context-4.3.7.RELEASE.jar;%APP_HOME%\lib\concurrentlinkedhashmap-lru-1.4.2.jar

@rem Execute base
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %BASE_OPTS%  -classpath "%CLASSPATH%" examples.Main %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable BASE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%BASE_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

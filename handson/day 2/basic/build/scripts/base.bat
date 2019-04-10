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

set CLASSPATH=%APP_HOME%\lib\base.jar;%APP_HOME%\lib\groovy-all-2.4.11.jar;%APP_HOME%\lib\grails-web-2.3.11.jar;%APP_HOME%\lib\commons-collections-3.2.1.jar;%APP_HOME%\lib\standard-1.1.2.jar;%APP_HOME%\lib\commons-fileupload-1.3.1.jar;%APP_HOME%\lib\commons-io-2.2.jar;%APP_HOME%\lib\grails-plugin-domain-class-2.3.11.jar;%APP_HOME%\lib\grails-core-2.3.11.jar;%APP_HOME%\lib\spring-aspects-3.2.9.RELEASE.jar;%APP_HOME%\lib\aspectjweaver-1.7.4.jar;%APP_HOME%\lib\grails-datastore-core-3.1.0.RELEASE.jar;%APP_HOME%\lib\concurrentlinkedhashmap-lru-1.3.1.jar;%APP_HOME%\lib\spring-webmvc-3.2.9.RELEASE.jar;%APP_HOME%\lib\grails-databinding-2.3.11.jar;%APP_HOME%\lib\sitemesh-2.4.jar;%APP_HOME%\lib\aspectjrt-1.7.4.jar;%APP_HOME%\lib\spring-context-support-3.2.9.RELEASE.jar;%APP_HOME%\lib\grails-async-2.3.11.jar;%APP_HOME%\lib\grails-spring-2.3.11.jar;%APP_HOME%\lib\grails-bootstrap-2.3.11.jar;%APP_HOME%\lib\jcl-over-slf4j-1.7.5.jar;%APP_HOME%\lib\slf4j-api-1.7.5.jar;%APP_HOME%\lib\xpp3_min-1.1.4c.jar;%APP_HOME%\lib\jstl-1.1.2.jar;%APP_HOME%\lib\commons-el-1.0.jar;%APP_HOME%\lib\commons-beanutils-1.8.3.jar;%APP_HOME%\lib\objenesis-1.4.jar;%APP_HOME%\lib\gson-2.2.4.jar;%APP_HOME%\lib\commons-lang-2.6.jar;%APP_HOME%\lib\oro-2.0.8.jar;%APP_HOME%\lib\serializer-2.7.1.jar;%APP_HOME%\lib\spring-tx-3.2.9.RELEASE.jar;%APP_HOME%\lib\spring-web-3.2.9.RELEASE.jar;%APP_HOME%\lib\cglib-2.2.2.jar;%APP_HOME%\lib\asm-3.3.1.jar;%APP_HOME%\lib\hibernate-jpa-2.1-api-1.0.0.Final.jar;%APP_HOME%\lib\spring-context-3.2.9.RELEASE.jar;%APP_HOME%\lib\spring-aop-3.2.9.RELEASE.jar;%APP_HOME%\lib\spring-beans-3.2.9.RELEASE.jar;%APP_HOME%\lib\spring-expression-3.2.9.RELEASE.jar;%APP_HOME%\lib\spring-core-3.2.9.RELEASE.jar;%APP_HOME%\lib\aopalliance-1.0.jar;%APP_HOME%\lib\commons-validator-1.3.1.jar;%APP_HOME%\lib\grails-datastore-simple-3.1.0.RELEASE.jar;%APP_HOME%\lib\grails-datastore-gorm-3.1.0.RELEASE.jar;%APP_HOME%\lib\gpars-1.1.0.jar;%APP_HOME%\lib\jta-1.1.jar;%APP_HOME%\lib\multiverse-core-0.7.0.jar;%APP_HOME%\lib\jsr166y-1.7.0.jar

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

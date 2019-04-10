
///**** Gradle : gradle as build tool 
//Dir structure 
.
├── build.gradle
├── src
│   ├── main
│   │   ├── groovy
│   │   │   ├── examples
│   │   │   │   ├── Main.groovy
│   │   │   │   ├── model.groovy
│   │   │   │   └── refresh.groovy
│   │   │   ├── jxrs
│   │   │   │   └── Service.groovy
│   │   │   ├── org
│   │   │   │   └── hibernate
│   │   │   │       └── dialect
│   │   │   │           ├── SQLiteDialect.java
│   │   │   │           └── identity
│   │   │   │               └── SQLiteDialectIdentityColumnSupport.java
│   │   │   └── support
│   │   │       └── Person.groovy
│   │   ├── java
│   │   │   ├── examples
│   │   │   │   ├── GreetingService.java
│   │   │   │   └── JavaGreetingImpl.java
│   │   │   └── jsrx
│   │   │       ├── Book.java
│   │   │       └── BookService.java
│   │   └── resources
│   │       ├── Messenger.groovy
│   │       ├── beans.xml
│   │       └── log4j.properties
│   └── test
│       ├── groovy
│       │   └── support
│       │       └── test.groovy
│       └── java
└── anyOtherFiles 
//build.gradle 
//note groovy plugin compiles java as well 

apply plugin: 'groovy'
apply plugin:'application'
mainClassName = "examples.Main"   //gradle run 

repositories {
          mavenCentral()	
          jcenter()
        }

dependencies {    
    compile 'org.codehaus.groovy:groovy-all:2.5.0'    
    //for xls 
    compile 'builders.dsl:spreadsheet-builder-poi:1.0.5'
    // for groovy support
    compile 'builders.dsl:spreadsheet-builder-groovy:1.0.5'	
    //for GORM 
    compile "org.grails:grails-datastore-gorm-hibernate5:6.0.11.RELEASE"
    compile group:'org.grails', name:'grails-web', version:'2.3.11'
    compile 'org.springframework:spring-context:4.0.0.RELEASE'
    compile 'com.github.groovy-wslite:groovy-wslite:1.1.2'
    compile 'io.ratpack:ratpack-groovy:1.5.4'
    //compile 'org.codehaus.groovy.modules:groovyws:0.5.2'
    compile 'org.codehaus.gpars:gpars:1.2.1'    
    compile 'io.github.http-builder-ng:http-builder-ng-core:1.0.3'    
    compile 'org.jsoup:jsoup:1.9.2'
    compile group:'org.ccil.cowan.tagsoup', name:'tagsoup', version:'0.9.7'
    runtime "com.h2database:h2:1.4.192"
    runtime "org.xerial:sqlite-jdbc:3.7.2"
    runtime "org.apache.tomcat:tomcat-jdbc:8.5.0"
    runtime "org.apache.tomcat.embed:tomcat-embed-logging-log4j:8.5.0"
    runtime "org.slf4j:slf4j-api:1.7.10"
    runtime group: 'org.slf4j', name: 'slf4j-log4j12', version: '1.7.10'
    //testing
	testCompile 'junit:junit:4.10'
}

//gradle build and gradle -PmainClass=foo runApp
task runApp(type:JavaExec) {
    classpath = sourceSets.main.runtimeClasspath
    main = project.hasProperty("mainClass") ? project.getProperty("mainClass") : "examples.Main"
}

// check task list
$ gradle tasks
// execute as, it runs test as well
$ gradle build
$ gradle run   //runs mainClassName
$ gradle -q run   //runs mainClassName and only main class outputs 
$ gradle -PmainClass=foo runApp  //for multiple class , runs 'foo'
$ gradle -i test                 //if test code has any println
// check test report in build/reports/tests/index.html
//with java
$ java -cp "build\libs\*;%GROOVY_HOME%\lib\*" support.Person 123

///Level        Level Used for                      Option(that level and higher)
//By default, Gradle redirects standard output to the QUIET log level and standard error to the ERROR level
//higher to lower                           
ERROR           Error messages
QUIET           Important information messages      -q or --quiet
WARNING         Warning messages                    -w or --warn
LIFECYCLE       Progress information messages        no logging options
INFO            Information messages                -i or --info
DEBUG           Debug messages(all messages)        -d or --debug
 

 


///* Gradle : Build script structure

//There is a one-to-one relationship between a Project and a build.gradle file. During build initialisation, 

//Gradle assembles a Project object for each project which is to participate in the build, as follows:
•Create a Settings instance for the build.
•Evaluate the settings.gradle script, if present, against the Settings object to configure it.
•Use the configured Settings object to create the hierarchy of Project instances.
•Finally, evaluate each Project by executing its build.gradle file, if present, against the project. 
 The projects are evaluated in breadth-wise order, such that a project is evaluated before its child projects. 
 This order can be overridden by calling Project.evaluationDependsOnChildren() or by adding an explicit evaluation dependency using Project.evaluationDependsOn(java.lang.String).


///Tasks
//A project is essentially a collection of Task objects. 
//Each task performs some basic piece of work, such as compiling classes, or running unit tests, or zipping up a WAR 



///Project Properties
//Gradle executes the project's build file against the Project instance to configure the project. 
//Any property or method which script uses is delegated through to the associated Project object. 

//This means, use any of the methods and properties on the Project interface directly in build script. 
defaultTasks('some-task')  // Delegates to Project.defaultTasks()
reportsDir = file('reports') // Delegates to Project.file() and the Java Plugin

//You can also access the Project instance using the 'project' property. 
//For example, you could use project.name rather than name to access the project's name.

 
//A build script is made up of zero or more statements and script blocks. 
//Statements can include method calls, property assignments, and local variable definitions. 

//A script block is a method call which takes a closure as a parameter. 
//The closure is treated as a configuration closure which configures it's delegate object (can access it properties and methods)

//The top level script blocks are listed below.
allprojects { } 
    Configures this project and each of its sub-projects. 
artifacts { } 
    Configures the published artifacts for this project.  
buildscript { } 
    Configures the build script classpath for this project.  
configurations { } 
    Configures the dependency configurations for this project.  
dependencies { } 
    Configures the dependencies for this project.  
repositories { } 
    Configures the repositories for this project.  
sourceSets { } 
    Configures the source sets of this project.  
subprojects { } 
    Configures the sub-projects of this project. 
publishing { } 
    Configures the PublishingExtension added by the publishing plugin.
    
    


//To access enviornment variable 
def home = "$System.env.HOME"

//Gradle uses a directed acyclic graph (DAG) to build a dependency graph of tasks and then executes
//Gradle scripts are configuration scripts. 
//As the script executes, it configures an object of a particular type. 

//For example, as a build script executes, it configures an object of type Project. 
//This object is called the delegate object of the script
//The properties and methods of the delegate object are available  to use in the script.

///Type of script                               Delegates to instance of 
Build script                                    Project  
Init script(USER_HOME/.gradle/init.gradle)      Gradle      (also from Project.getGradle()) for build customizations
Settings(settings.gradle)                       Settings    for multiproject settings 


//Also each Gradle script implements the Script interface. 

//Check reference for all types 
//https://docs.gradle.org/4.8/dsl/

///The Project object provides some standard properties, which are available in  build script
allprojects 
	The set containing this project and its subprojects.
ant 
	The AntBuilder for this project. You can use this in your build file to execute ant tasks. See example below.
artifacts 
	Returns a handler for assigning artifacts produced by the project to configurations. 
buildDir 
	The build directory of this project. The build directory is the directory which all artifacts are generated into. The default value for the build directory is projectDir/build
buildFile 
	The build script for this project. 
buildscript 
	The build script handler for this project. You can use this handler to query details about the build script for this project, and manage the classpath used to compile and execute the project build script.
childProjects 
	The direct children of this project.
configurations 
	The configurations of this project. 
convention 
	The Convention for this project.
defaultTasks 
	The names of the default tasks of this project. These are used when no tasks names are provided when starting the build.
dependencies 
	The dependency handler of this project. The returned dependency handler instance can be used for adding new dependencies. For accessing already declared dependencies, the configurations can be used. 
description 
	The description of this project, if any.
extensions 
	Allows adding DSL extensions to the project. Useful for plugin authors.
gradle 
	The Gradle invocation which this project belongs to.
group 
	The group of this project. Gradle always uses the toString() value of the group. The group defaults to the path with dots as separators.
logger 
	The logger for this project. You can use this in your build file to write log messages.
logging 
	The LoggingManager which can be used to receive logging and to control the standard output/error capture for this project build script. By default, System.out is redirected to the Gradle logging system at the QUIET log level, and System.err is redirected at the ERROR log level.
name 
	The name of this project. The project name is not necessarily unique within a project hierarchy. You should use the Project.getPath() method for a unique identifier for the project.
parent 
	The parent project of this project, if any.
path 
	The path of this project. 
    The path is the fully qualified name of the project.
plugins 
	The container of plugins that have been applied to this object. 
project 
	Returns this project. This method is useful in build files to explicitly access project properties and methods. For example, using project.name can express your intent better than using name. This method also allows you to access project properties from a scope where the property may be hidden, such as, for example, from a method or closure. 
projectDir 
	The directory containing the project build file.
properties 
	The properties of this project. 
repositories 
	Returns a handler to create repositories which are used for retrieving dependencies and uploading artifacts produced by the project.
resources 
	Provides access to resource-specific utility methods, for example factory methods that create various resources.
rootDir 
	The root directory of this project. The root directory is the project directory of the root project.
rootProject 
	The root project for the hierarchy that this project belongs to. In the case of a single-project build, this method returns this project.
state 
	The evaluation state of this project. 
    You can use this to access information about the evaluation of this project, such as whether it has failed.
status 
	The status of this project. Gradle always uses the toString() value of the status. 
    The status defaults to release.
subprojects 
	The set containing the subprojects of this project.
tasks 
	The tasks of this project.
version 
	The version of this project. 
//Few project methods 
absoluteProjectPath(path) 
	Converts a name to an absolute project path, resolving names relative to this project.
afterEvaluate(closure) 
	Adds a closure to be called immediately after this project has been evaluated. 
    The project is passed to the closure as a parameter. 
    Such a listener gets notified when the build file belonging to this project has been executed. 
    A parent project may for example add such a listener to its child project. 
    Such a listener can further configure those child projects based on the state of the child projects after their build files have been run.
apply(closure) 
	Applies zero or more plugins or scripts. 
apply(options) 
	Applies a plugin or script, using the given options provided as a map. 
    Does nothing if the plugin has already been applied. 

artifacts(configureAction) 
	Configures the published artifacts for this project. 
beforeEvaluate(closure) 
	Adds a closure to be called immediately before this project is evaluated. 
    The project is passed to the closure as a parameter.
    
configure(objects, configureClosure) 
	Configures a collection of objects via a closure. 
    This is equivalent to calling Project.configure(java.lang.Object, groovy.lang.Closure) for each of the given objects.
copy(closure) 
	Copies the specified files. 
    The given closure is used to configure a CopySpec, which is then used to copy the files. 
    copy {
       from configurations.runtime
       into 'build/deploy/lib'
    }
    copy {
       into 'build/webroot'
       exclude '**/.svn/**'
       from('src/main/webapp') {
          include '**/*.jsp'
          filter(ReplaceTokens, tokens:[copyright:'2009', version:'2.3.1'])
       }
       from('src/main/js') {
          include '**/*.js'
       }
    }
delete(paths) 
	Deletes files and directories. 
    project.delete {
        delete 'somefile'
        followSymlinks = true
    }

exec(closure) 
	Executes an external command. The closure configures a ExecSpec.

file(path) 
	Resolves a file path relative to the project directory of this project. This method converts the supplied path based on its type:
file(path, validation) 
	Resolves a file path relative to the project directory of this project and validates it using the given scheme. See PathValidation for the list of possible validations.
fileTree(baseDir) 
	Creates a new ConfigurableFileTree using the given base directory. 
    The given baseDir path is evaluated as per Project.file(java.lang.Object).
    //Example 
    def myTree = fileTree("src")
    myTree.include "**/*.java"
    myTree.builtBy "someTask"

    task copy(type: Copy) {
       from myTree
    }
fileTree(baseDir, configureClosure) 
	Creates a new ConfigurableFileTree using the given base directory. The given baseDir path is evaluated as per Project.file(java.lang.Object). 
    The closure will be used to configure the new file tree. The file tree is passed to the closure as its delegate. 
    Example 
    def myTree = fileTree('src') {
       exclude '**/.data/**'
       builtBy 'someTask'
    }
fileTree(args) 
	Creates a new ConfigurableFileTree using the provided map of arguments. 
    The map will be applied as properties on the new file tree.

files(paths, configureClosure) 
	Creates a new ConfigurableFileCollection using the given paths. The paths are evaluated as per Project.files(java.lang.Object[]). The file collection is configured using the given closure. The file collection is passed to the closure as its delegate. Example:
files(paths) 
	Returns a ConfigurableFileCollection containing the given files. You can pass any of the following types to this method:

findProperty(propertyName) 
	Returns the value of the given property or null if not found. This method locates a property as follows:
getAllTasks(recursive) 
	Returns a map of the tasks contained in this project, and optionally its subprojects.
getTasksByName(name, recursive) 
	Returns the set of tasks with the given name contained in this project, and optionally its subprojects.
hasProperty(propertyName) 
	Determines if this project has the given property. See here for details of the properties which are available for a project.
javaexec(closure) 
	Executes a Java main class. The closure configures a JavaExecSpec.
    
mkdir(path) 
	Creates a directory and returns a file pointing to it.

project(path) 
	Locates a project by path. If the path is relative, it is interpreted relative to this project.
project(path, configureClosure) 
	Locates a project by path and configures it using the given closure. If the path is relative, it is interpreted relative to this project. The target project is passed to the closure as the closure delegate.
property(propertyName) 
	Returns the value of the given property. 
    
relativePath(path) 
	Returns the relative path from the project directory to the given path. The given path object is (logically) resolved as described for Project.file(java.lang.Object), from which a relative path is calculated.
relativeProjectPath(path) 
	Converts a name to a project path relative to this project.
setProperty(name, value) 
	Sets a property of this project. This method searches for a property with the given name in the following locations, and sets the property on the first location where it finds the property.

tarTree(tarPath) 
	Creates a new FileTree which contains the contents of the given TAR file. 
    
task(name) 
	Creates a Task with the given name and adds it to this project. Calling this method is equivalent to calling Project.task(java.util.Map, java.lang.String) with an empty options map.
task(name, configureClosure) 
	Creates a Task with the given name and adds it to this project. 
    Before the task is returned, the given closure is executed to configure the task.
task(args, name) 
	Creates a Task with the given name and adds it to this project. 
    A map of creation options can be passed to this method to control how the task is created. 

task(args, name, configureClosure) 
	Creates a Task with the given name and adds it to this project. Before the task is returned, the given closure is executed to configure the task. A map of creation options can be passed to this method to control how the task is created. See Project.task(java.util.Map, java.lang.String) for the available options.

uri(path) 
	Resolves a file path to a URI, relative to the project directory of this project. Evaluates the provided path object as described for Project.file(java.lang.Object), with the exception that any URI scheme is supported, not just 'file:' URIs.
zipTree(zipPath) 
	Creates a new FileTree which contains the contents of the given ZIP file. 
    The given zipPath path is evaluated as per Project.file(java.lang.Object). 
    ou can combine this method with the Project.copy(groovy.lang.Closure) method to unzip a ZIP file.

    
 
///* Gradle : Creating new build 
$ mkdir basic-demo
$ cd basic-demo
$ touch build.gradle 

//To see what tasks are available 
$ gradle tasks

//To see help on one task 
$ gradle help --task wrapper

//Run wrapper to get wrapper 
$ gradle wrapper

//all project properties 
$ gradle properties

//dependencies 
$ gradle dependencies

//projects 
$ gradle projects 

//Add few properties 
//build.gradle 
description = 'A trivial Gradle build'
version = '1.0'
$ gradle properties

//One example task 
//create dir src/in.txt 
//build.gradle 
task copy(type: Copy) {
    from 'src'
    into 'dest'
}

//check tasks 
$ gradle tasks --all

//execute 
$ gradle copy 

//Using plugins
//Many pluins are available , check - http://plugins.gradle.org/
//Many plugin are created on top the 'base' plugin, eg Zip task 
//plugins {} must be first block of build.gradle 
//build.gradle 
plugins {
    id 'base'
}

task zip(type: Zip) {
    from 'src'
}

$ gradle zip  //creates project_name-version.zip file under build\distributions\libraries

//running multiple task , also can specify any prefix to uniquely identify 
$ gradle z c 

//to exclude use -x 
$ gradle zip -x copy

//Forcing tasks to run
$ gradle --rerun-tasks zip

//Continue even if failure of other tasks 
$ gradle --continue zip 

//Obtaining information about projects //-q means quite, only log errors 
$ gradle -q projects

//Obtaining information about tasks
$ gradle -q tasks

//By default, this report shows only those tasks which have been assigned to a task group, visible tasks.
//to assign group , then zip would be shown under Build tasks 
zip {
    description = 'Builds the distribution'
    group = 'build'
}

//--all : show tasks which have not been assigned to a task group, so-called hidden tasks. 
$ gradle -q tasks --all

//Getting detailed help 
$ gradle -q help --task clean 


//Profiling a build
$ gradle build --profile  //build/reports/profile 


///* Gradle: Task 
//Everything in Gradle sits on top of two basic concepts: projects and tasks.
//Every Gradle build is made up of one or more projects. 
//Each project is made up of one or more tasks

///Few important properties/methods of TASK 
String name (read-only)
    The name of this task. The name uniquely identifies the task within its Project.
TaskOutputs outputs (read-only)
    The outputs of this task.
String path (read-only)
    The path of the task, which is a fully qualified name for the task. 
    The path of a task is the path of its Project plus the name of the task, separated by :
Project project (read-only)
    The Project which this task belongs to.
TaskState state (read-only)
    The execution state of this task. 
    This provides information about the execution of this task, such as whether it has executed, been skipped, has failed, etc.
TaskDependency taskDependencies (read-only)
    Returns a TaskDependency which contains all the tasks that this task depends on.
File temporaryDir (read-only)
    Returns a directory which this task can use to write temporary files to
Logger logger (read-only)
    The logger for this task. Use for  writing log messages.
LoggingManager logging (read-only)
    The LoggingManager which can be used to control the logging level and standard output/error capture for this task. By default, System.out is redirected to the Gradle logging system at the QUIET log level, and System.err is redirected at the ERROR log level.
String group
    The task group which this task belongs to

///Few methods of Task 
Task doFirst(Closure action)
    Adds the given closure to the beginning of this task action list, 
    The closure is passed this task as a parameter when executed.
Task doLast(Closure action)
    Adds the given closure to the end of this task action list. 
    The closure is passed this task as a parameter when executed.
Task leftShift(Closure action)
    Note: This method is deprecated and will be removed in the next major version of Gradle.
    Adds the given closure to the end of this tasks action list
void onlyIf(Closure onlyIfClosure)
    Execute the task only if the given closure returns true. 
    The closure will be evaluated at task execution time, not during configuration. 
    The closure will be passed a single parameter, this task. 
    If the closure returns false, the task will be skipped

///Various ways to define tasks
task(hello) {                //Task 
    doLast {
        println "hello"
    }
}

task('copy', type: Copy) {   //taking string
    from(file('srcDir'))
    into(buildDir)
}


tasks.create(name: 'copy', type: Copy) {
    from(file('srcDir'))
    into(buildDir)
}

///Accessing tasks as properties

task hello

println hello.name
println project.hello.name

///Accessing tasks via' tasks' collection
task hello

println tasks.hello.name
println tasks['hello'].name

///Accessing tasks by path
project(':projectA') {
    task hello
}

task hello

println tasks.getByPath('hello').path
println tasks.getByPath(':hello').path
println tasks.getByPath('projectA:hello').path
println tasks.getByPath(':projectA:hello').path

///Configuring a task - various ways
Copy myCopy = task(myCopy, type: Copy)
myCopy.from 'resources'
myCopy.into 'target'
myCopy.include('**/*.txt', '**/*.xml', '**/*.properties')

///Configuring a task - with closure
task myCopy(type: Copy)

myCopy {
   from 'resources'
   into 'target'
   include('**/*.txt', '**/*.xml', '**/*.properties')
}


///Defining and configuring  a task with closure
task copy(type: Copy) {
   from 'resources'
   into 'target'
   include('**/*.txt', '**/*.xml', '**/*.properties')
}



///doLast and doFirst 
//The calls doFirst and doLast can be executed multiple times. 
//When the task executes, these actions in the action list are executed in order. 

task hello {
    doLast {
        println 'Hello Earth'
    }
}
hello.doFirst {
    println 'Hello Venus'
}
hello.doLast {
    println 'Hello Mars'
}
hello {
    doLast {
        println 'Hello Jupiter'
    }
}


///Passing external properties to task 
task printProp  {
    println customProp
}


$ gradle -PcustomProp=myProp printProp
:printProp
myProp

///Tasks passing variables among them - use task.ext 
task myTask {
    ext.myProperty = "myValue"
}

task printTaskProperties {
    doLast {
        println myTask.myProperty
    }
}



///Dependencies to a task are controlled using 
//property 
dependsOn                value is path 
//methods 
Task dependsOn(paths)    
    Adds the given dependencies to this task
Task mustRunAfter(paths) 
    Specifies that this task must run after all of the supplied tasks.
    task taskY {
        mustRunAfter "taskX"
    }
//Note - paths maybe 
•A String, CharSequence or groovy.lang.GString task path(path syntax project:task) or name. 
 A relative path is interpreted relative to the tasks Project
•A Task.
•A closure. The closure may take a Task as parameter. 
 It may return any of the types listed here
•A Iterable, Collection, Map or array. May contain any of the types listed here, 
 Its return value is recursively converted to tasks
•A Callable. The call() method may return any of the types listed here, 
 Its return value is recursively converted to tasks

//Example 
//build.gradle 

task compile {
    doLast {
        println 'compiling source'
    }
}

task compileTest(dependsOn: compile) {
    doLast {
        println 'compiling unit tests'
    }
}

task test(dependsOn: [compile, compileTest]) {
    doLast {
        println 'running unit tests'
    }
}

task dist(dependsOn: [compile, test]) {
    doLast {
        println 'building the distribution'
    }
}

$ gradle dist test


///Adding dependency on task from another project
project('projectA') {
    task taskX(dependsOn: ':projectB:taskY') {
        doLast {
            println 'taskX'
        }
    }
}

project('projectB') {
    task taskY {
        doLast {
            println 'taskY'
        }
    }
}


///Adding dependency using task object

task taskX {
    doLast {
        println 'taskX'
    }
}

task taskY {
    doLast {
        println 'taskY'
    }
}

taskX.dependsOn taskY


///Adding dependency using closure

task taskX {
    doLast {
        println 'taskX'
    }
}

taskX.dependsOn {
    tasks.findAll { task -> task.name.startsWith('lib') }
}

task lib1 {
    doLast {
        println 'lib1'
    }
}

task lib2 {
    doLast {
        println 'lib2'
    }
}

task notALib {
    doLast {
        println 'notALib'
    }
}


///Defining a default task
defaultTasks 'clean', 'run'

task clean {
    doLast {
        println 'Default Cleaning!'
    }
}

task run {
    doLast {
        println 'Default Running!'
    }
}

task other {
    doLast {
        println "I'm not a default task!"
    }
}


/// Using local variables

def dest = "dest"

task copy(type: Copy) {
    from "source"
    into dest
}

///Extra properties
//All objects in Gradle domain model can hold extra user-defined properties by 'ext' property 
apply plugin: "java"

ext {  //these are Project's extra 
    springVersion = "3.1.0.RELEASE"
    emailNotification = "build@master.org"
}

sourceSets.all { ext.purpose = null }

sourceSets {
    main {
        purpose = "production"
    }
    test {
        purpose = "test"
    }
    plugin {
        purpose = "production"
    }
}

task printProperties {
    doLast {
        println springVersion
        println emailNotification
        sourceSets.matching { it.purpose == "production" }.each { println it.name }
    }
}


$ gradle -q printProperties



///Task Ordering 
//Task.mustRunAfter(args) , Task.shouldRunAfter(args) 
//args is  a task instance, a task name or any other input accepted by Task.dependsOn(java.lang.Object[]). 

//Note that B.mustRunAfter(A) or B.shouldRunAfter(A) does not imply any execution dependency between the tasks
•It is possible to execute tasks A and B independently. 
 The ordering rule only has an effect when both tasks are scheduled for execution.
•When run with --continue, it is possible for B to execute in the event that A fails.

//A 'should run after' task ordering is ignored if it introduces an ordering cycle

///Adding a 'must run after' task ordering

task taskX {
    doLast {
        println 'taskX'
    }
}
task taskY {
    doLast {
        println 'taskY'
    }
}
taskY.mustRunAfter taskX



> gradle -q taskY taskX
taskX
taskY


///Adding a 'should run after' task ordering

task taskX {
    doLast {
        println 'taskX'
    }
}
task taskY {
    doLast {
        println 'taskY'
    }
}
taskY.shouldRunAfter taskX


> gradle -q taskY taskX
taskX
taskY

//Task ordering does not imply task execution
> gradle -q taskY
taskY





///Adding a description to a task
//This description is displayed when executing gradle tasks. 

task copy(type: Copy) {
   description 'Copies the resource directory to the target directory.'
   from 'resources'
   into 'target'
   include('**/*.txt', '**/*.xml', '**/*.properties')
}


///Replacing tasks /Overwriting a task

task copy(type: Copy)

task copy(overwrite: true) {
    doLast {
        println('I am the new one.')
    }
}


///Skipping a task using a predicate
task hello {
    doLast {
        println 'hello world'
    }
}

hello.onlyIf { !project.hasProperty('skipHello') }


$ gradle hello -PskipHello


///Skipping tasks with StopExecutionException
task compile {
    doLast {
        println 'We are doing the compile.'
    }
}

compile.doFirst {
    // Here you would put arbitrary conditions in real life.
    // But this is used in an integration test so we want defined behavior.
    if (true) { throw new StopExecutionException() }
}
task myTask(dependsOn: 'compile') {
    doLast {
        println 'I am not affected'
    }
}


$ gradle -q myTask

 
///Enabling and disabling tasks
task disableMe {
    doLast {
        println 'This should not be printed if the task is disabled.'
    }
}
disableMe.enabled = false




///* Gradle: dependencies, Repositeris and plugins 

//build.gradle
apply plugin: 'java'

repositories {
    mavenCentral()
}

dependencies {
    compile group: 'org.hibernate', name: 'hibernate-core', version: '3.6.7.Final'
    testCompile group: 'junit', name: 'junit', version: '4.+'
}

//Listing project dependencies
$ gradle dependencies

//to a particular configuration - Note testCompile must be there 
$ gradle dependencies --configuration testCompile

//To get insight 
$ gradle dependencyInsight --dependency java --configuration compile


//In Gradle dependencies are grouped into configurations(eg configurations.compile from java plugin)
//Configurations have a name, a number of other properties, and they can extend each other. 

//Many Gradle plugins add pre-defined configurations to a project.
//for example java(groovy) plugin brings below configurations 
compile runtime testCompile testRuntime

 
//To define and the re-configure 
configurations {
    my_compile {
        description = 'compile classpath'
        transitive = true
    }
    my_runtime {
        extendsFrom compile
    }
}
configurations.my_compile {
    description = 'compile classpath'
}

//accessing 
println configurations.my_compile.name
println configurations['my_compile'].name

//Example - Iterating over a configuration
task listJars {
    doLast {
        configurations.compile.each { File file -> println file.name }
    }
}

$ gradle -q listJars


//local Files dependencies
dependencies {
    runtime files('libs/a.jar', 'libs/b.jar')
    runtime fileTree(dir: 'libs', include: '*.jar')
}


//Dependencies on generated files - Note $ is GSTRING , buildDir is Project properties 
dependencies {
    compile files("$buildDir/classes") {
        builtBy 'compile'
    }
}

task compile {
    doLast {
        println 'compiling classes'
    }
}

task list(dependsOn: configurations.compile) {
    doLast {
        println "classpath = ${configurations.compile.collect { File file -> file.name }}"
    }
}

$ gradle -q list

//Gradle's Groovy dependencies
dependencies {
    compile localGroovy()
}

///Artifact (ie jar )only notation
//Gradle looks for a module descriptor file (pom.xml or ivy.xml) in the repositories
//it is parsed and downloaded 

//In Maven, a module can have one and only one artifact(jar). 
//In Gradle and Ivy, a module can have multiple artifacts.
//Each artifact can have a different set of dependencies. 

//In Gradle, when you declare a dependency on an Ivy module, 
//you actually declare a dependency on the default configuration of that module

dependencies {
    runtime "org.groovy:groovy:2.2.0@jar"
    runtime group: 'org.groovy', name: 'groovy', version: '2.2.0', ext: 'jar'
}
//Dependency with classifier(maven has a notion of classifier)
compile "org.gradle.test.classifiers:service:1.0:jdk15@jar"
otherConf group: 'org.gradle.test.classifiers', name: 'service', version: '1.0', classifier: 'jdk14'

//Gradle API dependencies
dependencies {
    compile gradleApi()
}

///local groovy dependencies 
dependencies {
    compile localGroovy()
}

///Excluding transitive dependencies- Check https://docs.gradle.org/4.8/userguide/dependency_management.html
//one can exclude transitive dependencies that are either not required by runtime 
//or that are guaranteed to be available on the target environment/platform

//note  All attributes for a dependency are optional, except the name


dependencies {
    runtime group: 'org.springframework', name: 'spring-core', version: '2.5'
    runtime 'org.springframework:spring-core:2.5',
            'org.springframework:spring-aop:2.5'
    runtime(
        [group: 'org.springframework', name: 'spring-core', version: '2.5'],
        [group: 'org.springframework', name: 'spring-aop', version: '2.5']
    )
    runtime('org.hibernate:hibernate:3.0.5') {
        transitive = true
    }
    runtime group: 'org.hibernate', name: 'hibernate', version: '3.0.5', transitive: false
    runtime(group: 'org.hibernate', name: 'hibernate', version: '3.0.5') {
        transitive = false
    }
}
//or 
configurations {
    compile.exclude module: 'commons'   // exclude 'commons' module for 'compile' configuration
    all*.exclude group: 'org.gradle.test.excludes', module: 'reports'  //to exclude  transitive dependency from all configurations using spread notation
}

dependencies {
    compile("org.gradle.test.excludes:api:1.0") {
        exclude module: 'shared'
    }
}


//Maven central repository
repositories {
    mavenCentral()
}

//Local file repositories along with maven central and local maven 
repositories {
   mavenCentral()
   flatDir {
       dirs 'libs1','libs2','libs3'
   }
   mavenLocal()  
}
dependencies {
   compile name: 'gson-2.2.4'
   compile ("com.company:utility:0.0.1") //from local maven 
}
//Note to install a file in local maven 
$ mvn install:install-file -Dfile=utility.jar -DgroupId=com.company -DartifactId=utility -Dversion=0.0.1 -Dpackaging=jar

//OR directly
compile fileTree(dir: 'libs', includes: ['*.jar'])
compile fileTree(dir: 'libs', include: '*.jar')


//Bintray's JCenter:
repositories {
    jcenter()
}

//a remote Maven repository
repositories {
    maven {
        url "http://repo.mycompany.com/maven2"
    }
}

//a remote Ivy repository:
repositories {
    ivy {
        url "http://repo.mycompany.com/repo"
    }
}

//a local Ivy directory
repositories {
    ivy {
        // URL can refer to a local directory
        url "../local-repo"
    }
}

//Publishing to an Ivy repository
uploadArchives {
    repositories {
        ivy {
            credentials {
                username "username"
                password "pw"
            }
            url "http://repo.mycompany.com"
        }
    }
}

$ gradle uploadArchives

//Publishing to a Maven repository
apply plugin: 'maven'

uploadArchives {
    repositories {
        mavenDeployer {
            repository(url: "file://localhost/tmp/myRepo/")
        }
    }
}





///* Gradle: Standard Task : Exec - Executes a command line process
//check https://docs.gradle.org/4.8/dsl/

task stopTomcat(type:Exec) {
  workingDir '../tomcat/bin'

  //on windows:
  commandLine 'cmd', '/c', 'stop.bat'

  //on linux
  commandLine './stop.sh'

  //store the output instead of printing to the console:
  standardOutput = new ByteArrayOutputStream()

  //extension method stopTomcat.output() can be used to obtain the output:
  ext.output = {
    return standardOutput.toString()
  }
}


///* Gradle: Standard Task : Copy - Copies files into a destination directory


task copyDocs(type: Copy) {
    from 'src/main/doc'
    into 'build/target/doc'
}

//for Ant filter
import org.apache.tools.ant.filters.ReplaceTokens

//for including in the copy task
def dataContent = copySpec {
    from 'src/data'
    include '*.data'
}

task initConfig(type: Copy) {
    from('src/main/config') {
        include '**/*.properties'
        include '**/*.xml'
        filter(ReplaceTokens, tokens: [version: '2.3.1'])
    }
    from('src/main/config') {
        exclude '**/*.properties', '**/*.xml'
    }
    from('src/main/languages') {
        rename 'EN_US_(.*)', '$1'
    }
    into 'build/target/config'
    exclude '**/*.bak'

    includeEmptyDirs = false

    with dataContent
}


///* Gradle: Standard Task : Delete - Deletes files or directories
task makePretty(type: Delete) {
  delete 'uglyFolder', 'uglyFile'
  followSymlinks = true
}


///* Gradle: Standard Task : test - Executes JUnit (3.8.x or 4.x) or TestNG tests. 
//Test are always run in (one or more) separate JVMs. 

apply plugin: 'java' // adds 'test' task

test {
  // enable TestNG support (default is JUnit)
  useTestNG()

  // set a system property for the test JVM(s)
  systemProperty 'some.prop', 'value'

  // explicitly include or exclude tests
  include 'org/foo/**'
  exclude 'org/boo/**'

  // show standard out and standard error of the test JVM(s) on the console
  testLogging.showStandardStreams = true

  // set heap size for the test JVM(s)
  minHeapSize = "128m"
  maxHeapSize = "512m"

  // set JVM arguments for the test JVM(s)
  jvmArgs '-XX:MaxPermSize=256m'

  // listen to events in the test execution lifecycle
  beforeTest { descriptor ->
     logger.lifecycle("Running test: " + descriptor)
  }

  // listen to standard out and standard error of the test JVM(s)
  onOutput { descriptor, event ->
     logger.lifecycle("Test: " + descriptor + " produced standard out/err: " + event.message )
  }
}


//to start in  in debug mode 
$ gradle test --debug-jvm




///* Gradle: Standard Task : Sync - like copy, but dest would only contain filed copied, other fies are deleted 


// Sync can be used like a Copy task
// See the Copy documentation for more examples
task syncDependencies(type: Sync) {
    from 'my/shared/dependencyDir'
    into 'build/deps/compile'
}

// You can preserve output that already exists in the
// destination directory. Files matching the preserve
// filter will not be deleted.
task sync(type: Sync) {
    from 'source'
    into 'dest'
    preserve {
        include 'extraDir/**'
        include 'dir1/**'
        exclude 'dir1/extra.txt'
    }
}




///* Gradle: Standard Task : JavaExec - Executes a Java application in a child process. 

apply plugin: 'java'

task runApp(type: JavaExec) {
  classpath = sourceSets.main.runtimeClasspath

  main = 'package.Main'

  // arguments to pass to the application
  args 'appArg1'
}

//debug mode 
$ gradle someJavaExecTask --debug-jvm



///* Gradle : Plugin 
//Note plugins{} must first block in build.gradle 


//To apply a core plugin, the short name can be used:
//build.gradle
plugins {
    id 'java'
}
//or legacy 
apply plugin: 'java'


//To apply a community plugin from the portal, 
//the fully qualified plugin id must be used:
//build.gradle
plugins {
    id 'com.jfrog.bintray' version '0.4.1'
}


//The pluginManagement {} block may only appear in either the settings.gradle file, 
//where it must be the first block in the file, or in an Initialization Script.

//settings.gradle
pluginManagement {
    resolutionStrategy {
    }
    repositories {
    }
}

//.gradle/init.gradle
settingsEvaluated { settings ->
    settings.pluginManagement {
        resolutionStrategy {
        }
        repositories {
        }
    }
}

//By default, the plugins {} DSL resolves plugins from the public Gradle Plugin Portal
//OR use settings.gradle


//Plugin resolution rules allow  to modify plugin requests made in plugins {} blocks, 
//e.g. changing the requested version or explicitly specifying the implementation artifact coordinates
//settings.gradle
pluginManagement {
  resolutionStrategy {
      eachPlugin {
          if (requested.id.namespace == 'org.gradle.sample') {
              useModule('org.gradle.sample:sample-plugins:1.0.0')
          }
      }
  }
  repositories {
      maven {
        url 'maven-repo'
      }
      gradlePluginPortal()
      ivy {
        url 'ivy-repo'
      }
  }
}




///Applying plugins with the buildscript block
//Binary plugins that have been published as external jar files can be added to a project 
//by adding the plugin to the build script classpath and then applying the plugin. 

//External jars can be added to the build script classpath using the buildscript {} block 
//build.gradle
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath "com.jfrog.bintray.gradle:gradle-bintray-plugin:0.4.1"
    }
}

apply plugin: "com.jfrog.bintray"





///* Gradle: Standard plugin : init-build plugin
//https://docs.gradle.org/4.8/userguide/build_init_plugin.html
//To use the plugin, execute the task named init where you would like to create the Gradle build. 
 

 
$ gradle init --type ...

--type     Set type of build to create.
   Available values are:
        basic               creates a sample build.gradle file, with comments and links to help get started. 
        groovy-library      Uses Spock testing framework for testing
        java-application    Uses the “application” plugin to produce a command-line application implemented using Java
        java-library 
        pom                 convert an Apache Maven build to a Gradle build
        scala-library       Scala2.10, Uses ScalaTest for testing

 
///it Uses JUnit for testing
//or change by  --test-framework argument value.

$ gradle init --type java-application --test-framework spock      //Uses Spock for testing instead of JUnit
$ gradle init --type java-application --test-framework testng    //Uses TestNG for testing instead of JUnit



///* Gradle: Standard plugin : java plugin 
// It serves as the basis for many of the other Gradle plugins. 

//build.gradle
apply plugin: 'java'

//A source set is a group of source files which are compiled and executed together
//A source set has an associated compile classpath, and runtime classpath. 



///Details 
Tasks
    compileJava(type: JavaCompile)
    processResources(type: Copy)
    classes(type: Task)
    compileTestJava(type: JavaCompile)
    processTestResources(type: Copy)
    testClasses(type: Task)
    jar(type: Jar)
    javadoc(type: Javadoc)
    test(type: Test)
    uploadArchives(type: Upload)
    clean(type: Delete)
    cleanTaskName(type: Delete)
SourceSet Tasks
    compileSourceSetJava(type: JavaCompile)
    processSourceSetResources(type: Copy)
    sourceSetClasses(type: Task)
Lifecycle Tasks
    assemble(type: Task)
    check(type: Task)
    build(type: Task)
    buildNeeded(type: Task)
    buildDependents(type: Task)
    buildConfigName(type: Task)
    uploadConfigName(type: Upload)
Project layout 
    src/main/java           Production Java source 
    src/main/resources      Production resources 
    src/test/java           Test Java source 
    src/test/resources      Test resources 
    src/sourceSet/java      Java source for the given source set 
    src/sourceSet/resources Resources for the given source set 
Name of dependency configurations
    compile
    implementation extends compile
    compileOnly
    compileClasspath extends compile, compileOnly, implementation
    annotationProcessor
    runtime extends compile
    runtimeOnly
    runtimeClasspath extends runtimeOnly, runtime, implementation
    testCompile(Deprecated) extends compile
    testImplementation extends testCompile, implementation
    testCompileOnly
    testCompileClasspath extends testCompile, testCompileOnly, testImplementation
    testRuntime(Deprecated) extends runtime, testCompile
    testRuntimeOnly extends runtimeOnly
    testRuntimeClasspath extends testRuntimeOnly, testRuntime, testImplementation
    archives
    default extends runtime
Name of source set dependency configurations
    sourceSetCompile
    sourceSetImplementation extends sourceSetCompile
    sourceSetCompileOnly
    sourceSetCompileClasspath extends compileSourceSetJava
    sourceSetAnnotationProcessor
    sourceSetRuntime
    sourceSetRuntimeOnly
    sourceSetRuntimeClasspath extends sourceSetRuntimeOnly, sourceSetRuntime, sourceSetImplementation
Convention properties
    Use these properties in build script as though they were properties of the project object.
    (read-only) SourceSetContainer sourceSets
        Contains the project’s source sets. Default value: Not null SourceSetContainer
    JavaVersion sourceCompatibility
        Java version compatibility to use when compiling Java source. 
        Default value: version of the current JVM in use JavaVersion. 
        Can also set using a String or a Number, e.g. '1.5' or 1.5.
    JavaVersion targetCompatibility
        Java version to generate classes for. Default value: sourceCompatibility. 
        Can also set using a String or Number, e.g. '1.5' or 1.5.
    String archivesBaseName
        The basename to use for archives, such as JAR or ZIP files. Default value: projectName
    Manifest manifest
        The manifest to include in all JAR files. Default value: an empty manifest.
    String reporting.baseDir
        The name of the directory to generate reports into, relative to the build directory. Default value: reports
    (read-only) File reportsDir
        The directory to generate reports into. Default value: buildDir/reporting.baseDir
    String testResultsDirName
        The name of the directory to generate test result .xml files into, relative to the build directory. Default value: test-results
    (read-only) File testResultsDir
        The directory to generate test result .xml files into. Default value: buildDir/testResultsDirName
    String testReportDirName
        The name of the directory to generate the test report into, relative to the reports directory. Default value: tests
    (read-only) File testReportDir
        The directory to generate the test report into. Default value: reportsDir/testReportDirName
    String libsDirName
        The name of the directory to generate libraries into, relative to the build directory. Default value: libs
    (read-only) File libsDir
        The directory to generate libraries into. Default value: buildDir/libsDirName
    String distsDirName
        The name of the directory to generate distributions into, relative to the build directory. Default value: distributions
    (read-only) File distsDir
        The directory to generate distributions into. Default value: buildDir/distsDirName
    String docsDirName
        The name of the directory to generate documentation into, relative to the build directory._ Default value: docs
    (read-only) File docsDir
        The directory to generate documentation into. Default value: buildDir/docsDirName
    String dependencyCacheDirName
        The name of the directory to use to cache source dependency information, relative to the build directory. Default value: dependency-cache
Java plugin SourceSet 
    main
    test
Source set properties
    (read-only) String name
    (read-only) SourceSetOutput output
    FileCollection output.classesDirs
    File output.resourcesDir
    FileCollection compileClasspath
    FileCollection annotationProcessorPath
    FileCollection runtimeClasspath
    (read-only) SourceDirectorySet java
    Set<File> java.srcDirs
    File java.outputDir
    (read-only) SourceDirectorySet resources
    Set<File> resources.srcDirs
    (read-only) SourceDirectorySet allJava
    (read-only) SourceDirectorySet allSource
JavaCompile task  properties 
    classpath 
    destinationDir 
    excludes 
    includes 
    options 
    source 
    sourceCompatibility 
    targetCompatibility 
    toolChain 
    exclude(excludeSpec) 
    exclude(excludes) 
    exclude(excludes) 
    exclude(excludeSpec) 
    include(includeSpec) 
    include(includes) 
    include(includes) 
    include(includeSpec) 
    source(sources) 
Jar type Properties 
    appendix 
    archiveName 
    archivePath 
    baseName 
    caseSensitive 
    classifier 
    destinationDir 
    dirMode 
    duplicatesStrategy 
    entryCompression 
    excludes 
    extension 
    fileMode 
    includeEmptyDirs 
    includes 
    manifest 
    metadataCharset 
    preserveFileTimestamps 
    reproducibleFileOrder 
    source 
    version 
    zip64 
    //Methods 
    eachFile(closure) 
    eachFile(action) 
    exclude(excludeSpec) 
    exclude(excludes) 
    exclude(excludes) 
    exclude(excludeSpec) 
    expand(properties) 
    filesMatching(patterns, action) 
    filesMatching(pattern, action) 
    filesNotMatching(patterns, action) 
    filesNotMatching(pattern, action) 
    filter(closure) 
    filter(filterType) 
    filter(properties, filterType) 
    filter(transformer) 
    from(sourcePath, c) 
    from(sourcePath, configureAction) 
    from(sourcePaths) 
    include(includeSpec) 
    include(includes) 
    include(includes) 
    include(includeSpec) 
    into(destPath) 
    into(destPath, configureClosure) 
    into(destPath, copySpec) 
    manifest(configureClosure) Incubating
    manifest(configureAction) Incubating
    metaInf(configureClosure) Incubating
    metaInf(configureAction) Incubating
    rename(closure) 
    rename(sourceRegEx, replaceWith) 
    rename(sourceRegEx, replaceWith) 
    rename(renamer) 
    with(sourceSpecs) 
JavExec type property
    Executes a Java application in a child process. 
    Similar to Exec, but starts a JVM with the given classpath and application class. 
    apply plugin: 'java'
    task runApp(type: JavaExec) {
      classpath = sourceSets.main.runtimeClasspath
      main = 'package.Main'
      // arguments to pass to the application
      args 'appArg1'
    }
    The process can be started in debug mode (see JavaExec.getDebug()) in an ad-hoc manner 
    by supplying the `--debug-jvm` switch when invoking the build. 
    gradle runApp --debug-jvm
    //Properties 
    allJvmArgs 
    args 
    argumentProviders 
    bootstrapClasspath 
    classpath 
    commandLine 
    debug 
    enableAssertions 
    environment 
    errorOutput 
    executable 
    ignoreExitValue 
    jvmArgs 
    jvmArgumentProviders 
    main 
    maxHeapSize 
    standardInput 
    standardOutput 
    systemProperties 
    workingDir 
    //Methods 
    args(args) 
    args(args) 
    bootstrapClasspath(classpath) 
    classpath(paths) 
    copyTo(options) 
    copyTo(target) 
    environment(name, value) 
    environment(environmentVariables) 
    executable(executable) 
    jvmArgs(arguments) 
    jvmArgs(arguments) 
    systemProperties(properties) 
    workingDir(dir)  

//example 
jar.doFirst{
      manifest {
            attributes("Manifest-Version"   : "1.0",
                "Created-By"         : vendor,
                "Specification-Title" : appName,
                "Specification-Version": version,
                "Specification-Vendor" : vendor,
                "Implementation-Title" : appName,
                "Implementation-Version" : version,
                "Implementation-Vendor": vendor,
                "Main-Class"           : "com.dcx.epep.Start",
                "Class-Path"           : configurations.compile.collect { it.getName() }.join(' ') )
      }
}

///Creating Fat  Jar 
//https://docs.gradle.org/current/dsl/org.gradle.api.tasks.bundling.Jar.html
mainClassName = "com.company.application.Main"

jar {
  manifest { 
    attributes "Main-Class": "$mainClassName"
  }  
  zip64 = true //if dependencies are big 
  from {
    configurations.runtime.collect { it.isDirectory() ? it : zipTree(it) }
  }
}
//Or addl task fatJar 
jar {
  manifest {
    attributes(
      'Main-Class': 'my.project.main',
    )
  }
}

task fatJar(type: Jar) {
  manifest.from jar.manifest
  //classifier = 'all'
  //or 
  baseName = project.name + '-all'
  from {
    configurations.runtime.collect { it.isDirectory() ? it : zipTree(it) }
  } {
        exclude "META-INF/*.SF"
        exclude "META-INF/*.DSA"
        exclude "META-INF/*.RSA"
  }
  with jar
}

//To add this to the standard assemble or build task, add:
artifacts {
    archives fatJar
}


///Accessing source set 
//Example - configuring sourceset - Custom Java source layout
sourceSets {
    main {
        java {
            srcDirs = ['src/java']
        }
        resources {
            srcDirs = ['src/resources']
        }
    }
}



/// Various ways to access the main source set
println sourceSets.main.output.classesDir
println sourceSets['main'].output.classesDir
sourceSets {
    println main.output.classesDir
}
sourceSets {
    main {
        println output.classesDir
    }
}

///Iterate over the source sets
sourceSets.all {
    println name
}

//The Java plugin adds a JavaCompile instance for each source set in the project. 
//Some of the most common configuration options are shown below. 
classpath  source destinationDir

///Defining a source set
sourceSets {
    intTest
}

dependencies {
    intTestCompile 'junit:junit:4.12'
    intTestRuntime 'org.ow2.asm:asm-all:4.0'
}

> gradle intTestClasses

// Assembling a JAR for a source set
task intTestJar(type: Jar) {
    from sourceSets.intTest.output
}
//creating javadoc 
task intTestJavadoc(type: Javadoc) {
    source sourceSets.intTest.allJava
}

//running test in source set 
task intTest(type: Test) {
    testClassesDir = sourceSets.intTest.output.classesDir
    classpath = sourceSets.intTest.runtimeClasspath
}

///* Gradle: plugin : application plugin 
//build.gradle
apply plugin: 'application'

//The Application plugin facilitates creating an executable JVM application. 
//It makes it easy to start the application locally during development, 
//and to package the application as a TAR and/or ZIP including operating system specific start scripts.

//Applying the Application plugin also implicitly applies the Java plugin. 
//The main source set is “application”.

//Applying the Application plugin also implicitly applies the Distribution plugin.
// A 'main' distribution is created that packages up the application, including code dependencies 

///Configure the application main class
mainClassName = "org.gradle.sample.Main"

$ gradle run 
$ gradle run --debug-jvm 

//Configure default JVM settings
applicationDefaultJvmArgs = ["-Dgreeting.language=en"]

//Configure custom directory for start scripts
executableDir = "custom_bin_dir"

//A main distribution is created with the following content:
//Distribution content          Location Content 
(root dir)                      src/dist
lib                             All runtime dependencies and main source set class files.
bin                             Start scripts (generated by createStartScripts task).
 

//Include output from other tasks in the application distribution
task createDocs {
    def docs = file("$buildDir/docs")
    outputs.dir docs
    doLast {
        docs.mkdirs()
        new File(docs, "readme.txt").write("Read me!")
    }
}

distributions {
    main {
        contents {
            from(createDocs) {
                into "docs"
            }
        }
    }
}
//Creates OS specific scripts to run the project as a JVM application.
$ gradle startScripts
 
 
//Automatically creating files for distribution
$ gradle installDist 
$ gradle distTar 
$ gradle assemble 
$ gradle distZip
> Task :createDocs
> Task :compileJava
> Task :processResources NO-SOURCE
> Task :classes
> Task :jar
> Task :startScripts
> Task :distZip






///*Gradle :init build : Building Java Application (application means command line application)
//check reference - https://docs.gradle.org/4.8/userguide/application_plugin.html

$ mkdir java-demo
$ cd java-demo
$ gradle init --type java-application


//dir structure 
+-- build.gradle
+-- gradle    
¦   +-- wrapper
¦       +-- gradle-wrapper.jar
¦       +-- gradle-wrapper.properties
+-- gradlew
+-- gradlew.bat
+-- settings.gradle
+-- src
    +-- main
    ¦   +-- java  
    ¦       +-- App.java
    +-- test      
        +-- java
            +-- AppTest.java

//Check settings.gradle 
rootProject.name = 'java-demo'

//check build.gradle 
apply plugin: 'java'
apply plugin: 'application'

repositories {
    jcenter()  //public Bintray Artifactory repository
}

dependencies {
    compile 'com.google.guava:guava:20.0'  
    testCompile 'junit:junit:4.12'         
}

mainClassName = 'App'  

//application plugin designates one class as having a main method, 
//which can be executed by the build from the command line


//src/main/java/App.java
public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {  
        System.out.println(new App().getGreeting());
    }
}

//src/test/java/AppTest.java 
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
    @Test public void testAppHasAGreeting() {
        App classUnderTest = new App();
        assertNotNull("app should have a greeting",
                       classUnderTest.getGreeting());
    }
}
//build 
$ gradle build 
//run the main class 
$ gradle run 
//test 
$ gradle -i test    // include -i (loglevel=info) to get any stdout(println) output from Test script
//Creates a full distribution ZIP archive including runtime libraries and OS specific scripts.
$ gradle distZip  //distTar
//Creates OS specific scripts to run the project as a JVM application.
$ gradle startScripts 








///*Gradle :init build : Building java Library  - https://docs.gradle.org/4.8/userguide/java_library_plugin.html
//Note - Other plugins, such as the Groovy plugin, may not behave correctly with this plugin 
$ mkdir building-java-libraries
$ cd building-java-libraries

$ gradle init --type java-library

//settings.gradle
rootProject.name = 'building-java-libraries' 1

//build.gradle 
apply plugin: 'java-library'

repositories {
    jcenter() 1
}

dependencies {
    api 'org.apache.commons:commons-math3:3.6.1' //a dependency which is exported to consumers, that is to say found on their compile classpath. 
    implementation 'com.google.guava:guava:21.0' //a dependency which is used internally, and not exposed to consumers on their own compile classpath. 
    testImplementation 'junit:junit:4.12' 
}

//src/main/java/Library.java 
public class Library {
    public boolean someLibraryMethod() {
        return true;
    }
}
//src/test/java/LibraryTest.java
import org.junit.Test;

public class LibraryTest {
    @Test public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
    }
}

//build 
$ gradle build 

//test report at build/reports/tests/test/index.html.
//jar at build/libs 


//To customize the build 
//build.gradle
version = '0.1.0'

//to customise generated MANIFEST.MF 
//build.gradle
jar {
    manifest {
        attributes('Implementation-Title': project.name,
                   'Implementation-Version': project.version)
    }
}

$ gradle jar 

//For adding distribution 
apply plugin: 'java-library-distribution'

distributions {
    main{
        baseName = 'my-name'
    }
}


$ gradle distZip   //Creates a full distribution ZIP archive including runtime libraries including all files under src/main/dist added to root of zip 


//The java-library plugin has built-in support for Java’s API documentation tool via the javadoc task.
//Change javadoc comment 
/** This java source file was generated by the Gradle 'init' task.
 */
public class Library {
    public boolean someLibraryMethod() {
        return true;
    }
}
$ gradle javadoc 



///* Gradle: Plugin : Using Groovy Plugin 
//The Groovy plugin extends the Java plugin to add support for Groovy projects. 
//It can deal with Groovy code, mixed Groovy and Java code, and even pure Java code

//The Groovy compiler will always be executed with the same version of Java that was used to start Gradle

//Using the Groovy plugin
apply plugin: 'groovy'

///Details 
project layout
    src/main/java           Production Java source 
    src/main/resources      Production resources 
    src/main/groovy         Production Groovy sources. May also contain Java sources for joint compilation. 
    src/test/java           Test Java source 
    src/test/resources      Test resources 
    src/test/groovy         Test Groovy sources. May also contain Java sources for joint compilation. 
    src/sourceSet/java      Java source for the given source set 
    src/sourceSet/resources Resources for the given source set 
    src/sourceSet/groovy    Groovy sources for the given source set. May also contain Java sources for joint compilation. 
Groovy plugin - tasks
    Since extends 'java' plugin , all java tasks are available 
    compileGroovy
        dependsOn:compileJava
        type:GroovyCompile
        Compiles production Groovy source files.
    compileTestGroovy
        dependsOn:compileTestJava 
        type:GroovyCompile 
        Compiles test Groovy source files.
    compileSourceSetGroovy 
        dependsOn:compileSourceSetJava 
        type:GroovyCompile 
        Compiles the given source set’s Groovy source files.
    groovydoc 
        type:Groovydoc
        Generates API documentation for the production Groovy source files. 
Dependencies to tasks added by the Java plugin
    classes  dependsOn:compileGroovy
    testClasses  dependsOn:compileTestGroovy
    sourceSetClasses  dependsOn:compileSourceSetGroovy
Source set properties
    The Groovy plugin adds the following convention properties to each source set in the project. 
    Use these properties in  build script as though they were properties of the source set object.
    groovy          :SourceDirectorySet (read-only)
    groovy.srcDirs  :Set<File>
    allGroovy       :FileTree (read-only)
    allJava 
    allSource
GroovyCompile properties 
    classpath 
    destinationDir 
    excludes 
    groovyClasspath 
    groovyOptions 
    includes 
    options 
    source 
    sourceCompatibility 
    targetCompatibility 
    //Methods 
    exclude(excludeSpec) 
    exclude(excludes) 
    exclude(excludes) 
    exclude(excludeSpec) 
    include(includeSpec) 
    include(includes) 
    include(includes) 
    include(includeSpec) 
    source(sources) 


//Configuration of Groovy dependency
repositories {
    mavenCentral()
}

dependencies {
    compile 'org.codehaus.groovy:groovy-all:2.4.10'
    testCompile 'org.codehaus.groovy:groovy-all:2.4.10'

}





///*Gradle :init build : Building Groovy Library 
$ mkdir building-groovy-libraries
$ cd building-groovy-libraries
$ gradle init --type groovy-library


//settings.gradle
rootProject.name = 'building-groovy-libraries' 

//build.gradle 
apply plugin: 'groovy'

repositories {
    jcenter() 1
}

dependencies {
    compile 'org.codehaus.groovy:groovy-all:2.4.10' 
    testCompile 'org.spockframework:spock-core:1.0-groovy-2.4' 
    testCompile 'junit:junit:4.12' 
}


//src/main/groovy/Library.groovy
class Library {
    boolean someLibraryMethod() {
        true
    }
}


//src/test/groovy/LibraryTest.groovy 
import spock.lang.Specification

class LibraryTest extends Specification {
    def "someLibraryMethod returns true"() {
        setup:
        def lib = new Library()

        when:
        def result = lib.someLibraryMethod()

        then:
        result == true
    }
}


$ gradle build 

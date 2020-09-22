# ComponentTree

## What this tool is

This is a tool to check dependencies between Angular components recursively. (i.e. Component A is used by Component B, C, and D, and Component B is in turn used by ....)

The dependency is expressed as json. If it's not clear what I mean by that, refer to `sample.json`, which is the result of running this tool against CompAComponent in `src/test/resources/component-node-spec`.

## Runtime Environment

This tool is tested in the enviroment below. It should work if you have Gradle and JDK installed in your machine.

```terminal
mac@macnoMacBook-Pro ComponentTree % sw_vers
ProductName:    Mac OS X
ProductVersion: 10.15.6
BuildVersion:   19G2021
mac@macnoMacBook-Pro ComponentTree % gradle -v

------------------------------------------------------------
Gradle 6.5
------------------------------------------------------------

Build time:   2020-06-02 20:46:21 UTC
Revision:     a27f41e4ae5e8a41ab9b19f8dd6d86d7b384dad4

Kotlin:       1.3.72
Groovy:       2.5.11
Ant:          Apache Ant(TM) version 1.10.7 compiled on September 1 2019
JVM:          13.0.2 (Oracle Corporation 13.0.2+8)
OS:           Mac OS X 10.15.6 x86_64

mac@macnoMacBook-Pro ComponentTree % java --version
openjdk 13.0.2 2020-01-14
OpenJDK Runtime Environment (build 13.0.2+8)
OpenJDK 64-Bit Server VM (build 13.0.2+8, mixed mode, sharing)
```

## How to use this project

Clone this project and run commands below.
Replace 'path/to/your/angular/project' and 'app-your-component' with your own path and component name.
You can use tag name (e.g. 'app-your-component') or class name (e.g 'YourComponent') to specify your component.

```terminal
./gradlew build
java -jar build/libs/ComponentTree-1.0-SNAPSHOT.jar path/to/your/angular/project app-your-component
```

## Note

This tool is based on the assumption that your project structure is same as the one created by Angular CLI. More specifically, this tool doesn't work if your component file (xxx.component.ts) is not in the same directory with template file (xxx.component.html). You don't need template file if you directly define template in ts file.

## TODO

- Introduce Mockito for testing
- Increase test coverage
- Support other frameworks such as React

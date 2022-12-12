# JavaCard Template project with Gradle

[![Build Status](https://travis-ci.org/ph4r05/javacard-gradle-template.svg?branch=master)](https://travis-ci.org/ph4r05/javacard-gradle-template)

This is simple JavaCard project template using Gradle build system.

You can develop your JavaCard applets and build cap files with the Gradle!
Moreover the project template enables you to test the applet with [JCardSim] or on the physical cards.

Gradle project contains one module:

- `applet`: contains the javacard applet. Can be used both for testing and building CAP

Features:
 - Gradle build (CLI / IntelliJ Idea)
 - Build CAP for applets
 - Test applet code in [JCardSim] / physical cards
 - IntelliJ Idea: Coverage
 - Travis support 

### Template

The template contains simple Hello World applet generating random bytes on any APDU message received.
There is also implemented very simple test that sends static APDU command to this applet - in JCardSim.

The Gradle project can be opened and run in the IntelliJ Idea.

Running in IntelliJ Idea gives you a nice benefit: *Coverage*!

## How to use

- Clone this template repository:

```bash
git clone --recursive https://github.com/ph4r05/javacard-gradle-template.git
```

- Implement your applet in the `applet` module.

- Run Gradle wrapper `./gradlew` on Unix-like system or `./gradlew.bat` on Windows
to build the project for the first time (Gradle will be downloaded if not installed).

## Building cap

- Setup your Applet ID (`AID`) in the `./applet/build.gradle`.

- Run the `buildJavaCard` task:

```bash
./gradlew buildJavaCard  --info --rerun-tasks
```

Generates a new cap file `./applet/out/cap/applet.cap`

Note: `--rerun-tasks` is to force re-run the task even though the cached input/output seems to be up to date.

Typical output:

```
[ant:cap] [ INFO: ] Converter [v3.0.5]
[ant:cap] [ INFO: ]     Copyright (c) 1998, 2015, Oracle and/or its affiliates. All rights reserved.
[ant:cap]     
[ant:cap]     
[ant:cap] [ INFO: ] conversion completed with 0 errors and 0 warnings.
[ant:verify] XII 10, 2017 10:45:05 ODP.  
[ant:verify] INFO: Verifier [v3.0.5]
[ant:verify] XII 10, 2017 10:45:05 ODP.  
[ant:verify] INFO:     Copyright (c) 1998, 2015, Oracle and/or its affiliates. All rights reserved.
[ant:verify]     
[ant:verify]     
[ant:verify] XII 10, 2017 10:45:05 ODP.  
[ant:verify] INFO: Verifying CAP file /Users/dusanklinec/workspace/jcard/applet/out/cap/applet.cap
[ant:verify] javacard/framework/Applet
[ant:verify] XII 10, 2017 10:45:05 ODP.  
[ant:verify] INFO: Verification completed with 0 warnings and 0 errors.
```
## Parsing PSBTv0
```aidl
./gradlew run --args="70736274ff0100750200000001268171371edff285e937adeea4b37b78000c0566cbb3ad64641713ca42171bf60000000000feffffff02d3dff505000000001976a914d0c59903c5bac2868760e90fd521a4665aa7652088ac00e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787b32e1300000100fda5010100000000010289a3c71eab4d20e0371bbba4cc698fa295c9463afa2e397f8533ccb62f9567e50100000017160014be18d152a9b012039daf3da7de4f53349eecb985ffffffff86f8aa43a71dff1448893a530a7237ef6b4608bbb2dd2d0171e63aec6a4890b40100000017160014fe3e9ef1a745e974d902c4355943abcb34bd5353ffffffff0200c2eb0b000000001976a91485cff1097fd9e008bb34af709c62197b38978a4888ac72fef84e2c00000017a914339725ba21efd62ac753a9bcd067d6c7a6a39d05870247304402202712be22e0270f394f568311dc7ca9a68970b8025fdd3b240229f07f8a5f3a240220018b38d7dcd314e734c9276bd6fb40f673325bc4baa144c800d2f2f02db2765c012103d2e15674941bad4a996372cb87e1856d3652606d98562fe39c5e9e7e413f210502483045022100d12b852d85dcd961d2f5f4ab660654df6eedcc794c0c33ce5cc309ffb5fce58d022067338a8e0e1725c197fb1a88af59f51e44e4255b20167c8684031c05d1f2592a01210223b72beef0965d10be0778efecd61fcac6f79a4ea169393380734464f84f2ab300000000000000"
```

## Parsing PSBTv2

```aidl
./gradlew run --args="70736274ff01020402000000010401010105010201fb040200000000010e200b0ad921419c1c8719735d72dc739f9ea9e0638d1fe4c1eef0f9944084815fc8010f0400000000000103080008af2f000000000104160014c430f64c4756da310dbd1a085572ef299926272c000103088bbdeb0b0000000001041600144dd193ac964a56ac1b9e1cca8454fe2f474f851300"
```

## Installation on a (physical) card

```bash
./gradlew installJavaCard
```

Or inspect already installed applets:

```bash
./gradlew listJavaCard
```

## Running on simulator (jCardSim)

As simple as:

```bash
./gradlew run
```

By default the run task will run the main Java application implemented at: `main/java/main/Run.java`, using the `HelloWorldApplet` applet.

## Running tests

```
./gradlew test --info --rerun-tasks
```

Output:

```
Running test: Test method hello(AppletTest)

Gradle suite > Gradle test > AppletTest.hello STANDARD_OUT
    Connecting to card... Done.
    --> [00C00000080000000000000000] 13
    <-- 51373E8B6FDEC284DB569204CA13D2CAA23BD1D85DCAB02A0E3D50461E73F1BB 9000 (32)
    ResponseAPDU: 34 bytes, SW=9000
```

## Dependencies

This project uses mainly:

- https://github.com/bertrandmartel/javacard-gradle-plugin
- https://github.com/martinpaljak/ant-javacard
- https://github.com/martinpaljak/oracle_javacard_sdks
- https://github.com/licel/jcardsim
- Petr Svenda scripts 

Kudos for a great work!

### JavaCard support

Thanks to Martin Paljak's [ant-javacard] and [oracle_javacard_sdks] we support:

- JavaCard 2.1.2
- JavaCard 2.2.1
- JavaCard 2.2.2
- JavaCard 3.0.3
- JavaCard 3.0.4
- JavaCard 3.0.5u1
- JavaCard 3.1.0b43

## Supported Java versions

Java 8-u271 is the minimal version supported. 

Make sure you have up to date java version (`-u` version) as older java 8 versions
have problems with recognizing some certificates as valid.

Only some Java versions are supported by the JavaCard SDKs.
Check the following compatibility table for more info: 
https://github.com/martinpaljak/ant-javacard/wiki/Version-compatibility

## Coverage

This is a nice benefit of the IntelliJ Idea - gives you coverage 
results out of the box. 

You can see the test coverage on your applet code.

- Go to Gradle plugin in IntelliJ Idea
- Tasks -> verification -> test
- Right click - run with coverage.

Coverage summary:
![coverage summary](https://raw.githubusercontent.com/ph4r05/javacard-gradle-template/master/.github/image/coverage_summary.png)

Coverage code:
![coverage code](https://raw.githubusercontent.com/ph4r05/javacard-gradle-template/master/.github/image/coverage_class.png)

## Troubleshooting

If you experience the following error: 

```
java.lang.VerifyError: Expecting a stackmap frame at branch target 19
    Exception Details:
      Location:
        javacard/framework/APDU.<init>(Z)V @11: ifeq
      Reason:
        Expected stackmap frame at this location.
```

Then try running JVM with `-noverify` option.

In the IntelliJ Idea this can be configured in the top tool bar
with run configurations combo box -> click -> Edit Configurations -> VM Options.

However, the `com.klinec:jcardsim:3.0.5.11` should not need the `-noverify`.

### Invalid APDU loaded

You may experience error like this: `Invalid APDU loaded. You may have JC API in your classpath before JCardSim. Classpath:`

This error is thrown by JCardSim which tries to load APDU class augmented with another methods. The augmented APDU version is contained in the JCardSim JAR.
However, if `api_class.jar` from the JavaCard SDK is on the classpath before the JCardSim, this problem occurs. The classpath ordering causes non-augmented version is loaded which prevents JCardSim from correct function.

gradle-javacard-plugin v1.7.4 should fix this error.

If you still experience this in IntelliJ Idea try: open project structure settings -> modules -> applet_test and move JCardSim to the top so it appears first on the classpath.
This has to be done with each project reload from the Gradle. 

## Roadmap

TODOs for this project:

- Polish Gradle build scripts
- Add basic libraries as maven dependency.

## Contributions

Community feedback is highly appreciated - pull requests are welcome!



[JCardSim]: https://jcardsim.org/
[ant-javacard]: https://github.com/martinpaljak/ant-javacard
[oracle_javacard_sdks]: https://github.com/martinpaljak/oracle_javacard_sdks


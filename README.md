# Policy-Based Validation of Bitcoin Transactions on Cryptographic Smartcards using JavaCard Template project with Gradle

## Showcases 
```aidl
./gradlew run 
```
Runs a showcase. I recommend to look into the code base for better understanding.

## How to use

- Clone this template repository:

```bash
git clone --recursive https://github.com/ph4r05/javacard-gradle-template.git
```

- Run Gradle wrapper `./gradlew` on Unix-like system or `./gradlew.bat` on Windows
to build the project for the first time (Gradle will be downloaded if not installed).

## Building cap

- Run the `buildJavaCard` task:

```bash
./gradlew buildJavaCard  --info --rerun-tasks
```

Generates a cap file `./applet/out/cap/PSBTPolicyValidator.cap`

Note: `--rerun-tasks` is to force re-run the task even though the cached input/output seems to be up to date.


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

## Running tests
```
./gradlew test --info --rerun-tasks
```

## Dependencies

This project uses mainly:

- https://github.com/bertrandmartel/javacard-gradle-plugin
- https://github.com/martinpaljak/ant-javacard
- https://github.com/martinpaljak/oracle_javacard_sdks
- https://github.com/licel/jcardsim
- Petr Svenda scripts 

Kudos for a great work!

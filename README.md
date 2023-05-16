# Policy-Based Validation of Bitcoin Transactions on Cryptographic Smartcards using JavaCard Template project with Gradle

With the growth and global acceptance of Bitcoin as an electronic means of trade and the improvement of secure hardware in the last few decades, new opportunities opened in combining these technologies. This thesis presents such a new technology enhancing previous inventions with a policy-based approach. With the right policy palette available, this work is a first step to allowing an owner better control of his wealth. In this thesis, we managed to create a wide system of rules that accomplishes the goal set. The Smart Card is able to store a given rule (policy) and then validate whether the rules are satisfied based on uploaded Partially Signed Bitcoin Transactions and other data according to the given policy.

## Showcase 
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

## Running tests
```
./gradlew test --info --rerun-tasks
```


## Future Work
I would like to implement a transaction signing and
raw transaction serializing, with the possibility of using a key pair
generated directly on the Card and the applet inscribing its own re-
turn addresses. Adding more new Atoms is something I see as the
most probable as new ones are already coming to mind, for example,
extended Signed Time Atom, which would increase the time needed
by a given value each time validation would be satisfied. I could also
imagine combining this applet with other secure hardware or bio-metric devices, if possible.

## Dependencies
This project uses mainly:

- https://github.com/bertrandmartel/javacard-gradle-plugin
- https://github.com/martinpaljak/ant-javacard
- https://github.com/martinpaljak/oracle_javacard_sdks
- https://github.com/licel/jcardsim
- Petr Svenda scripts 

Kudos for a great work!

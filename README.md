# Policy-Based Validation of Bitcoin Transactions on Cryptographic Smartcards 
using JavaCard Template project with Gradle

With the growth and global acceptance of Bitcoin as an electronic means of trade and the improvement of secure hardware in the last few decades, new opportunities opened in combining these technologies. This thesis presents a new technology enhancing previous approaches with a policy-based validation. The focus is designing and implementing a simple yet powerful way to express conditions for validating and confirming cryptocurrency transactions performed by cryptographic smart cards. Therefore a comprehensive system of rules can be created and uploaded to the smart card. The smart card stores a given scheme, and afterward, it validates whether the rules are satisfied based on uploaded transactions and other data according to the given policy.
## Showcase 
```aidl
./gradlew run 
```
Runs a showcase. I recommend to look into the code base for better understanding.

## How to use

- Clone this template repository:

```bash
git clone --recursive https://github.com/jan-sekanina/PSBTPolicyValidatorApplet.git
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
```bash
./gradlew test --info --rerun-tasks
```


## Future Work
The following steps building on this work could include implementing a
transaction signing and raw transaction serializing, with the possibility
of using a key pair generated directly on the Card and the applet
inscribing its return addresses. Adding more new Atoms is something
I see as the most probable as new ones are already coming to mind,
for example, extended Signed Time Atom, which would increase the
time needed by a given value each time validation would be satisfied. I
could also imagine combining this applet with other secure hardware
or bio-metric devices.
## Dependencies
This project uses mainly:

- https://github.com/bertrandmartel/javacard-gradle-plugin
- https://github.com/martinpaljak/ant-javacard
- https://github.com/martinpaljak/oracle_javacard_sdks
- https://github.com/licel/jcardsim
- Petr Svenda scripts 

Kudos for a great work!

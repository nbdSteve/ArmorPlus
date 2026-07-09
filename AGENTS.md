# Project agent memory

This file is the project's committed home for project-intrinsic agent knowledge: build, test, release, architecture, and sharp-edge notes that should travel with the code.

- Build with JDK 25 or newer - `maven-enforcer-plugin` (`requireJavaVersion [25,)`) fails `validate` on anything older. Emitted plugin bytecode is intentionally Java 8 (major version 52) via `<maven.compiler.release>8</maven.compiler.release>` in `pom.xml`; the target runtime is a Java 17+ Spigot server. Do not "modernize" the bytecode level. Build/verify: `JAVA_HOME=<jdk25> mvn -B clean verify`.
- `de.tr7zw:functional-annotations` is pinned to `0.1-SNAPSHOT` because no released version has ever been published (CodeMC metadata lists only the SNAPSHOT). It is a real compile dependency (`de.tr7zw.annotations.FAUtil`, used by the `nbt` package), so it cannot be dropped. Revisit if upstream ever cuts a release.
- Test harness: JUnit 5 + MockBukkit + Mockito + JaCoCo. `mvn verify` runs tests and produces `target/site/jacoco/` coverage report. JaCoCo excludes `**/nbt/**` (vendored) and `**/ArmorPlus.class` (bootstrap).
- Coverage gate (CORE set): JaCoCo `check-core` execution enforces 100% line AND branch on these pure-logic classes: `PlayerDirectionUtil`, `ColorUtil`, `Piece`, `SetType`, `SetDataType`. Build fails if core coverage drops below 100%. Everything else remains report-only. Excluded from core: `LogUtil` (calls `ArmorPlus.get()` singleton), `nbt.NBTType` (vendored `**/nbt/**` exclusion). When modifying a core class, add tests in the same commit.
- JDK/bytecode split: main code compiles to Java 8 (`maven.compiler.release=8`), test code compiles to Java 17 (`maven.compiler.testRelease=17`). The `testRelease` property is picked up automatically by maven-compiler-plugin's default-testCompile execution - no custom execution block needed. Both compile under the same JDK 25 build. MockBukkit + paper-api are Java 17 bytecode, so tests cannot target 8.
- MockBukkit limitation: the plugin cannot be loaded via `MockBukkit.load(ArmorPlus.class)` because the vendored NBT-API performs reflection at enable time that MockBukkit does not model. Use pure-logic unit tests or Mockito for testing pieces that interact with Bukkit APIs.

## Maintaining this file

Keep this file for knowledge useful to almost every future agent session in this project.
Do not repeat what the codebase already shows; point to the authoritative file or command instead.
Prefer rewriting or pruning existing entries over appending new ones.
When updating this file, preserve this bar for all agents and keep entries concise.

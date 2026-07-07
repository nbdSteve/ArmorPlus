# Project agent memory

This file is the project's committed home for project-intrinsic agent knowledge: build, test, release, architecture, and sharp-edge notes that should travel with the code.

- Build with a modern JDK (>= 17; JDK 25 recommended) - `maven-enforcer-plugin` fails `validate` on anything older. Emitted plugin bytecode is intentionally Java 8 (major version 52) via `<maven.compiler.release>8</maven.compiler.release>` in `pom.xml`; the target runtime is a Java 17+ Spigot server. Do not "modernize" the bytecode level. Build/verify: `JAVA_HOME=<jdk25> mvn -B clean package`.
- `de.tr7zw:functional-annotations` is pinned to `0.1-SNAPSHOT` because no released version has ever been published (CodeMC metadata lists only the SNAPSHOT). It is a real compile dependency (`de.tr7zw.annotations.FAUtil`, used by the `nbt` package), so it cannot be dropped. Revisit if upstream ever cuts a release.

## Maintaining this file

Keep this file for knowledge useful to almost every future agent session in this project.
Do not repeat what the codebase already shows; point to the authoritative file or command instead.
Prefer rewriting or pruning existing entries over appending new ones.
When updating this file, preserve this bar for all agents and keep entries concise.

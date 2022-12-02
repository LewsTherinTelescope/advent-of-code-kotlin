# Advent of Code in Kotlin Scripts
Unlike the official Kotlin template, this uses Kotlin's experimental scripting support, which allows each file to be standalone (besides needing Kotlin itself and the JVM, of course).

## Usage
First, you'll need to grab your session cookie from the [Advent of Code site](https://adventofcode.com/) by going to `Developer Tools > Storage > Cookies > https://adventofcode.com` and selecting the value labeled `session`, and store it in a file named `.session` in the project root folder.

Then, for each year simply copy the `year0000` folder and rename it, and do the same for each day with `day00`.

Next up is to implement the solution in the `solve.main.kts` file. The inputs (test and real) will be fetched and saved automatically, but you'll need to manually set the expected answer for the tests in the `testAnswerPartX` variables next to the functions.

Lastly, to run, simply make sure you have Kotlin and Java installed and execute `yearYYYY/dayDD/solve.main.kts`. (At least on *nix; on Windows, you may need to run `kotlin yearYYYY\dayDD\solve.main.kts`, I haven't tested it.)

## Dependencies
Unlike a normal Kotlin project, you don't use `build.gradle.kts`, because these files are standalone scripts of their own. Instead, you'll need to use file-level annotations if you want to add dependencies:
- `@file:Repository(repositoriesCoordinates)`: Extra Maven repositories to use.
- `@file:DependsOn(artifactsCoordinates)`: Maven artifacts to resolve.
- `@file:Import(paths)`: Other script files to import.
- `@file:CompilerOptions(options)`: Options to pass to the Kotlin compiler (such as `-xopt-in`).

## Why use this over the official template?
Uh... not sure, it's not *that* much of a benefit for this particular use case, maybe even a detriment. Honestly, I just wanted an excuse to play around with the scripting support a bit.

# Advent of Code in Kotlin
Pretty much what the name says. 
## Usage
First, you'll need to grab your session cookie from the [Advent of Code site](https://adventofcode.com/) by going to `Developer Tools > Storage > Cookies > https://adventofcode.com` and selecting the value labeled `session`, and store it in a file named `.session` in the project root folder. This allows inputs to be downloaded automatically, but must be refreshed after a month.

Then, for each year open `src` and simply copy the `year0000` folder and rename it, and do the same for each day with `day00`. Change the package name as well to prevent conflicts, and ensure it starts with `yearYYYY.dayDD`, as this is required for the input downloader to correctly autodetect the correct date.

From there, open up the day's `Main.kt` file, and solve! Run the `main()` function from your IDE when you're done. (If you are using VSCode or anything else whose Kotlin support is based on the [Kotlin Debug Adapter](https://github.com/fwcd/kotlin-debug-adapter/), see [this issue](https://github.com/fwcd/vscode-kotlin/issues/112).)

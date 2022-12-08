package year2022.day07

import utils.runIt
import java.util.LinkedList

fun main() = runIt(
	testInput = """
		$ cd /
		$ ls
		dir a
		14848514 b.txt
		8504156 c.dat
		dir d
		$ cd a
		$ ls
		dir e
		29116 f
		2557 g
		62596 h.lst
		$ cd e
		$ ls
		584 i
		$ cd ..
		$ cd ..
		$ cd d
		$ ls
		4060174 j
		8033020 d.log
		5626152 d.ext
		7214296 k
	""".trimIndent(),
	::part1,
	testAnswerPart1 = 95437,
	::part2,
	testAnswerPart2 = 24933642,
)

fun part1(input: String) = ConsoleLog(input).parseFolderTree().second
	.filter { it.size < 100000 }
	.sumOf { it.size }

fun part2(input: String) = ConsoleLog(input)
	.suggestFolderToClear(targetSize = 30000000, diskSize = 70000000)!!
	.size

sealed interface FileLike {
	val name: String
	val parent: Folder?
	val size: Int
}

class File(
	override val name: String,
	override val parent: Folder?,
	override val size: Int,
) : FileLike

class Folder(
	override val name: String,
	override val parent: Folder?,
	val children: MutableList<FileLike>,
) : FileLike {
	override val size: Int by lazy { children.sumOf { it.size } }
}

@JvmInline
value class ConsoleLog(val text: String)

/** returns root + flat set of all folders */
fun ConsoleLog.parseFolderTree(): Pair<Folder, List<Folder>> {
	val root = Folder("", parent = null, LinkedList())
	val all = LinkedList<Folder>().apply { add(root) }
	// drop "cd /" because we always start from root
	text.lines().drop(1).fold(root) { oldCwd, line ->
		val parts = line.split(' ')
		val (first, second) = parts
		
		var cwd = oldCwd
		when (first) {
			"$" -> when (second) {
				"cd" -> cwd = when (val target = parts[2]) {
					".." -> cwd.parent!!
					else -> cwd.children.first { it.name == target } as Folder
				}
			}

			"dir" -> {
				val newFolder = Folder(second, cwd, LinkedList())
				cwd.children += newFolder
				all += newFolder
			}

			else -> {
				cwd.children += File(second, cwd, first.toInt())
			}
		}
		return@fold cwd
	}
	return root to all
}

fun ConsoleLog.suggestFolderToClear(
	targetSize: Int,
	diskSize: Int,
) = parseFolderTree().let { (root, all) ->
	val freeDiskSize = diskSize - root.size
	val sizeToClear = targetSize - freeDiskSize
	all.filter { it.size > sizeToClear }.minByOrNull { it.size }
}

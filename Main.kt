package search

import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val list = File(args.last()).readLines()
    SimpleSearch(list)
}

class SimpleSearch(private val listOfLines: List<String>) {
    init {
        menu()
    }
    private fun menu() {
        println("""
            === Menu ===
            1. Find a line
            2. Print all lines
            0. Exit
            
            """.trimIndent()
        )
        when (readln()) {
            "1" -> {
                searchingInTheList().let {
                    println("${ it.size } lines found:")
                    it.forEach { line -> println(line) }
                }
                menu()
            }
            "2" -> printAll().also { menu() }
            "0" -> println("Bye!").also { exitProcess(0) }
            else -> println("Incorrect option! Try again.\n").also { menu() }
        }
    }
    private fun strategy(): String {
        println("Select a matching strategy: ALL, ANY, NONE")
        return readln().uppercase().let { str ->
            if (str in listOf("ANY", "ALL", "NONE")) str else strategy()
        }
    }
    private fun searchingInTheList(strategy: String = strategy()): List<String> {
        println("Enter data to search all suitable lines:")
        val input = readln().lowercase().split(' ')
        val mapInd = listOfLines.flatMapIndexed { index, value ->
            value.split(" ").map { index to it }
        }.groupBy({ (_, second) -> second }, { (first, _) -> first })
        val key = mapInd.keys.filter { it.lowercase() in input }.let {
            if (it.isEmpty()) {
                println("No matching lines found.")
                menu()
            }
            it
        }
        return when (strategy) {
            "ALL" -> {
                listOfLines.filter { it.contains(key.joinToString(" ")) }
            }
            "ANY" -> {
                mutableListOf<String>().apply {
                    for (el in key) {
                        mapInd[el]?.forEach { this.add(listOfLines[it]) }
                    }
                }
            }
            else -> {
                listOfLines.filterIndexed { index, _ -> index !in  key.flatMap { mapInd[it]!! } }
            }
        }
    }
    private fun printAll() {
        println("=== List of lines ===")
        listOfLines.forEach { println(it) }
    }
}
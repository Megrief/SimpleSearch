package search

import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val list = File(args.last()).readLines()
    SimpleSearch(list)
}

class SimpleSearch(private val listOfLines: List<String>) : SearchLogic {
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
                searchInTheList(listOfLines = listOfLines).let {
                    if(it.isNullOrEmpty()) {
                        /* TODO */
                    } else {
                        println("${ it.size } lines found:")
                        it.forEach { line -> println(line) }
                    }
                }
                menu()
            }
            "2" -> printAll(listOfLines).also { menu() }
            "0" -> println("Bye!").also { exitProcess(0) }
            else -> println("Incorrect option! Try again.\n").also { menu() }
        }
    }
}
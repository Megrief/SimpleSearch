package search

interface SearchLogic {
    private fun matchStrategy(): String {
        println("Select a matching strategy: ALL, ANY, NONE")
        return readln().uppercase().let { str ->
            if (str in listOf("ANY", "ALL", "NONE")) str else matchStrategy()
        }
    }

    fun searchInTheList(strategy: String = matchStrategy(), listOfLines: List<String>): List<String>? {
        println("Enter data to search all suitable lines:")
        val input = readln().lowercase().split(' ')
        val mapInd = listOfLines.flatMapIndexed { index, value ->
            value.split(" ").map { index to it }
        }.groupBy({ (_, second) -> second }, { (first, _) -> first })
        val key = mapInd.keys.filter { it.lowercase() in input }.let {
            if (it.isEmpty()) {
                println("No matching lines found.")
                return null
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

    fun printAll(listOfLines: List<String>) {
        println("=== List of lines ===")
        listOfLines.forEach { println(it) }
    }
}
package com.example.leitner.database

val _questions =
    listOf("contrived", "pat (adjective)", "mastered", "saucepan", "intermixture", "aught", "Jainism", "observation tower", "numbfish")

val _answers =
    listOf(
        "deliberately created rather than arising naturally or spontaneously.",
        "exactly suited to the purpose or occasion\n\nsuspiciously appropriate",
        "To gain a thorough understanding of",
        "A deep pan with a handle; used for stewing or boiling",
        "An additional ingredient that is added by mixing with the base",
        "No amount at all; something of no value or importance",
        "Sect founded in the 6th century BC as a revolt against Hinduism",
        "A structure commanding a wide view of its surroundings",
        "Any sluggish bottom-dwelling ray of the order Torpediniformes having a rounded body and electric organs on each side of the head capable of emitting strong electric discharges"
    )

fun getQuestion(index: Int) : String {
    return _questions.get(index)
}

fun getAnswer(index: Int) : String {
    return _answers.get(index)
}

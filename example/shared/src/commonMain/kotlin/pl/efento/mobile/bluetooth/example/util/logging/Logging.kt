package pl.efento.mobile.bluetooth.example.util.logging

data class Logger(val tag: String) {
    fun error(t: Throwable? = null, message: () -> String) {
        val errorMsg = if (t != null) "\n${t.stackTraceToString()}" else ""
        println("$tag E: ${message.invoke()}$errorMsg")
    }

    fun info(message: () -> String) {
        println("$tag I: ${message.invoke()}")
    }

    fun debug(message: () -> String) {
        println("$tag D: ${message.invoke()}")
    }

    fun warn(message: () -> String) {
        println("$tag W: ${message.invoke()}")
    }
}

fun logging(tag: String = ""): Logger {
    return Logger(tag)
}
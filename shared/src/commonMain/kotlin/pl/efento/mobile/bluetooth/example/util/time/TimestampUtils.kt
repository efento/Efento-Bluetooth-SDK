package pl.efento.mobile.bluetooth.example.util.time

fun timestampToString(seconds: Long): String {
    val daysOfMonth = arrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)

    var currYear = 1970
    var daysTillNow = seconds / (24 * 60 * 60)
    var extraTime = seconds % (24 * 60 * 60)
    var flag = 0 // Flag to check if it's a leap year

    while (true) {
        val isLeapYear = currYear % 400 == 0 || (currYear % 4 == 0 && currYear % 100 != 0)
        val daysInYear = if (isLeapYear) 366 else 365

        if (daysTillNow < daysInYear) {
            break
        }

        daysTillNow -= daysInYear
        currYear++
    }

    var extraDays = daysTillNow + 1

    flag = if (currYear % 400 == 0 || (currYear % 4 == 0 && currYear % 100 != 0)) 1 else 0

    var month = 0
    var index = 0

    if (flag == 1) {
        while (true) {
            if (index == 1) { // February in a leap year
                if (extraDays - 29 < 0) break
                month++
                extraDays -= 29
            } else {
                if (extraDays - daysOfMonth[index] < 0) break
                month++
                extraDays -= daysOfMonth[index]
            }
            index++
        }
    } else {
        while (true) {
            if (extraDays - daysOfMonth[index] < 0) break
            month++
            extraDays -= daysOfMonth[index]
            index++
        }
    }

    val date = if (extraDays > 0) {
        extraDays.toInt()
    } else {
        if (month == 2 && flag == 1) 29 else daysOfMonth[month - 1]
    }

    val hours = extraTime / 3600
    val minutes = (extraTime % 3600) / 60
    val secondsRemaining = (extraTime % 3600) % 60

    return "$date/${month + 1}/${currYear} ${hours}:${minutes}:${secondsRemaining}"
}
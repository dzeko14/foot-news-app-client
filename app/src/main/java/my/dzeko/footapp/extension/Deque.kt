package my.dzeko.footapp.extension

import java.util.*

fun <T> Deque<T>.popUpTo(item: T) {
    if (!contains(item)) return

    while (item != peek()) {
        pop()
    }
}
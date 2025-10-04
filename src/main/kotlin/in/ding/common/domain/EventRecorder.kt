package `in`.ding.common.domain

class EventRecorder<T> {
    private val events = mutableListOf<T>()

    fun add(event: T) = events.add(event)

    fun toList(): List<T> = events.toList()

    fun clear() = events.clear()
}

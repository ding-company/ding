package `in`.ding.common.domain.event

class EventRecorder<T> {
    private val events = mutableListOf<T>()

    fun add(event: T) = events.add(event)

    fun toList(): List<T> = events.toList()
    fun clear() = events.clear()

    /**
     * 꺼내고 내부 리스트는 비우기 — ApplicationService에서 사용
     */
    fun drain(): List<T> {
        val copy = events.toList()
        events.clear()
        return copy
    }
}

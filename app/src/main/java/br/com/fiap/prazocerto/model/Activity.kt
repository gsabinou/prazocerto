package br.com.fiap.prazocerto.model

data class Activity(
    var id: String= "",
    val title: String = "",
    val discipline: String = "",
    val deliverDate: String = "",
    val wasDelivered: Boolean = false
) {
    fun toJson(): Map<String, Any> =
        mapOf(
            "id" to id,
            "title" to title,
            "discipline" to discipline,
            "deliverDate" to deliverDate,
            "wasDelivered" to wasDelivered
        )
}

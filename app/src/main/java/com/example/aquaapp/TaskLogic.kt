package com.example.aquaapp

/** Одна задача в списке дел. */
data class Task(val id: Long, val title: String, val done: Boolean = false)

/** Название допустимо, если после обрезки пробелов оно не пустое. */
fun isValidTitle(title: String): Boolean = title.trim().isNotEmpty()

/** Добавляет задачу в конец списка. Пустое название игнорируется. */
fun addTask(tasks: List<Task>, title: String): List<Task> {
    if (!isValidTitle(title)) return tasks
    val nextId = (tasks.maxOfOrNull { it.id } ?: 0L) + 1L
    return tasks + Task(id = nextId, title = title.trim())
}

/** Переключает отметку «выполнено» у задачи с указанным id. */
fun toggleTask(tasks: List<Task>, id: Long): List<Task> =
    tasks.map { if (it.id == id) it.copy(done = !it.done) else it }

/** Удаляет задачу с указанным id. */
fun removeTask(tasks: List<Task>, id: Long): List<Task> =
    tasks.filterNot { it.id == id }

/** Доля выполненных задач от 0 до 1. */
fun progressFraction(tasks: List<Task>): Float =
    if (tasks.isEmpty()) 0f else tasks.count { it.done }.toFloat() / tasks.size

/** Строка прогресса: «Выполнено 2 из 5» или подсказка, если задач нет. */
fun progressText(tasks: List<Task>): String =
    if (tasks.isEmpty()) "Задач пока нет"
    else "Выполнено ${tasks.count { it.done }} из ${tasks.size}"

/** Строка версии для экрана: «1.0.0 (1)». */
fun formatVersion(name: String, code: Long): String = "$name ($code)"

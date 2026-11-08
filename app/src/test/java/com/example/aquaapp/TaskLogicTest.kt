package com.example.aquaapp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TaskLogicTest {
    @Test
    fun addTask_trimsTitleAndAssignsId() {
        val result = addTask(emptyList(), "  Купить воду  ")
        assertEquals(1, result.size)
        assertEquals("Купить воду", result[0].title)
        assertEquals(1L, result[0].id)
        assertFalse(result[0].done)
    }

    @Test
    fun addTask_ignoresBlankTitle() {
        val start = listOf(Task(1, "Первая"))
        assertEquals(start, addTask(start, "   "))
        assertFalse(isValidTitle(""))
        assertTrue(isValidTitle(" a "))
    }

    @Test
    fun toggleTask_switchesDoneFlag() {
        val start = listOf(Task(1, "A"), Task(2, "B"))
        val result = toggleTask(start, 2)
        assertFalse(result[0].done)
        assertTrue(result[1].done)
        assertFalse(toggleTask(result, 2)[1].done)
    }

    @Test
    fun removeTask_deletesOnlyChosenTask() {
        val start = listOf(Task(1, "A"), Task(2, "B"))
        assertEquals(listOf(Task(2, "B")), removeTask(start, 1))
    }

    @Test
    fun progress_textAndFraction() {
        assertEquals("Задач пока нет", progressText(emptyList()))
        val tasks = listOf(Task(1, "A", true), Task(2, "B"), Task(3, "C"), Task(4, "D", true))
        assertEquals("Выполнено 2 из 4", progressText(tasks))
        assertEquals(0.5f, progressFraction(tasks), 0.0001f)
        assertEquals(0f, progressFraction(emptyList()), 0.0001f)
    }

    @Test
    fun formatVersion_joinsNameAndCode() {
        assertEquals("1.0.0 (7)", formatVersion("1.0.0", 7L))
    }
}

package com.juandgaines.todoapp

import com.juandgaines.todoapp.domain.Task
import com.juandgaines.todoapp.domain.TaskLocalDataSource
import com.juandgaines.todoapp.presentation.screens.home.providers.completedTask
import com.juandgaines.todoapp.presentation.screens.home.providers.pendingTask
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object FakeTaskLocalDataSource: TaskLocalDataSource {
    private val _tasksFlow = MutableStateFlow<List<Task>>(emptyList())


    override val tasksFlow: Flow<List<Task>>
        get() = _tasksFlow

    override suspend fun addTask(task: Task) {
        val tasks = _tasksFlow.value.toMutableList()
        tasks.add(task)
        delay(10)
        _tasksFlow.value = tasks
    }

    override suspend fun updateTask(updatedTask: Task) {
        val tasks = _tasksFlow.value.toMutableList()
        val taskIndex = tasks.indexOfFirst { it.id == updatedTask.id }
        if (taskIndex != -1) {
            tasks[taskIndex] = updatedTask
            delay(10)
            _tasksFlow.value = tasks
        }
    }

    override suspend fun removeTask(task: Task) {
        val tasks = _tasksFlow.value.toMutableList()
        tasks.remove(task)
        delay(10)
        _tasksFlow.value = tasks
    }

    override suspend fun deleteAllTasks() {
        delay(10)
        _tasksFlow.value = emptyList()
    }

    override suspend fun getTaskById(taskId: String): Task? {
        delay(10)
        return _tasksFlow.value.find { it.id == taskId }
    }

    override suspend fun removeAllTasks() {
        delay(10)
        _tasksFlow.value = emptyList()
    }
}
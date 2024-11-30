package com.example.todolist.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.db.TodoDao
import com.example.todolist.db.TodoEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoDao: TodoDao
) : ViewModel() {
    private val _todoList = mutableStateOf<List<TodoEntity>>(emptyList())
    val todoList: State<List<TodoEntity>> = _todoList

    // Todo 추가
    fun addTodo(newTodo: TodoEntity) {
        viewModelScope.launch {
            todoDao.insertTodo(newTodo)
            _todoList.value = todoDao.getAllTodo() // 새로 Todo 리스트 갱신
        }
    }

    // 초기 데이터 로드
    fun loadTodos() {
        viewModelScope.launch {
            _todoList.value = todoDao.getAllTodo()
        }
    }
}



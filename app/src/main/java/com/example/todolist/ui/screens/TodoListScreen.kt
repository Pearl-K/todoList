package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.db.TodoEntity
import com.example.todolist.ui.components.TodoItem
import com.example.todolist.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(todoViewModel: TodoViewModel) {

    // ViewModel에서 상태 가져오기
    val todoList by todoViewModel.todoList

    TodoListContent(
        todoList = todoList,
        onAddTodo = { title, description, importance ->
            val newTodo = TodoEntity(
                title = title,
                description = description,
                importance = importance
            )
            todoViewModel.addTodo(newTodo)
        },
        onRefreshTodos = { todoViewModel.loadTodos() }
    )
}

@Composable
fun TodoListContent(
    todoList: List<TodoEntity>,
    onAddTodo: (String, String, Int) -> Unit,
    onRefreshTodos: () -> Unit
) {
    // 새로운 Todo 아이템 추가 상태 관리
    var newTodoTitle by remember { mutableStateOf("") }
    var newTodoDescription by remember { mutableStateOf("") }
    var newTodoImportance by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Todo 리스트 표시
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todoList) { todo ->
                TodoItem(todo)
            }
        }

        // 새로운 Todo 추가 입력 필드
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            TextField(
                value = newTodoTitle,
                onValueChange = { newTodoTitle = it },
                label = { Text("할 일 제목") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = newTodoDescription,
                onValueChange = { newTodoDescription = it },
                label = { Text("할 일 설명") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            TextField(
                value = newTodoImportance.toString(),
                onValueChange = { newTodoImportance = it.toIntOrNull() ?: 1 },
                label = { Text("중요도 (1-5)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    if (newTodoTitle.isNotBlank()) {
                        onAddTodo(newTodoTitle, newTodoDescription, newTodoImportance)
                        newTodoTitle = ""
                        newTodoDescription = ""
                        newTodoImportance = 1
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "할 일 추가하기")
            }
        }
    }
}

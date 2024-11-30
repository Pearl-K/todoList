package com.example.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.db.TodoEntity
import com.example.todolist.ui.components.TodoItem
import com.example.todolist.viewmodel.TodoViewModel

@Composable
fun TodoListScreen(todoViewModel: TodoViewModel) {
    // Todo 리스트 상태 관리
    val todoList by todoViewModel.todoList.observeAsState(emptyList())

    // 새로운 Todo 아이템을 추가할 때 사용될 상태
    var newTodoTitle by remember { mutableStateOf("") }
    var newTodoDescription by remember { mutableStateOf("") }
    var newTodoImportance by remember { mutableStateOf(1) }

    // 데이터 로드
    LaunchedEffect(Unit) {
        todoViewModel.loadTodos()
    }

    // 새로운 Todo 추가 버튼 클릭 시
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // 리스트 표시
        LazyColumn(
            modifier = Modifier.weight(1f), // 화면의 상단 공간 차지
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(todoList) { todo ->
                TodoItem(todo) // 개별 TodoItem 표시
            }
        }

        // 새로운 Todo 추가를 위한 입력 필드
        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            TextField(
                value = newTodoTitle,
                onValueChange = { newTodoTitle = it },
                label = { Text("할 일 제목") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            TextField(
                value = newTodoDescription,
                onValueChange = { newTodoDescription = it },
                label = { Text("할 일 설명") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )

            // 중요도 선택을 위한 텍스트 필드
            TextField(
                value = newTodoImportance.toString(),
                onValueChange = { newTodoImportance = it.toIntOrNull() ?: 1 },
                label = { Text("중요도 (1-5)") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            Button(
                onClick = {
                    if (newTodoTitle.isNotBlank()) {
                        val newTodo = TodoEntity(
                            title = newTodoTitle,
                            description = newTodoDescription,
                            importance = newTodoImportance
                        )
                        todoViewModel.addTodo(newTodo)
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
package com.sharath070.todoapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.sharath070.todoapp.db.Repository
import com.sharath070.todoapp.db.Todo
import com.sharath070.todoapp.db.TodoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application){

    val todos: LiveData<List<Todo>>
    private val repository: Repository
    init {
        val todoDao = TodoDatabase.getDatabase(application).todoDao()
        repository = Repository(todoDao)
        todos = repository.todos
    }

    fun insert(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(todo)
        }
    }

    fun delete(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(todo)
        }
    }
    fun update(todo: Todo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(todo)
        }
    }

}
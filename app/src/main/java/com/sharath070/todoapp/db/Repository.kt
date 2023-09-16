package com.sharath070.todoapp.db

class Repository(private val dao: TodoDao) {

    val todos = dao.getAllData()

    fun insert(todo: Todo){
        return dao.insert(todo)
    }

    fun update(todo: Todo){
        return dao.update(todo)
    }

    fun delete(todo: Todo){
        return dao.delete(todo)
    }


}
package ro.craciungabriel.myapplication.repository

import ro.craciungabriel.myapplication.domain.Employee

interface Repository {

    fun findAll():List<Employee>

    fun remove(position:Int)

    fun add(employee: Employee):Int

    fun modify(employee: Employee,position:Int)

    fun getPicture(position: Int): String
}

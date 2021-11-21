package ro.craciungabriel.myapplication.domain

class Employee(
    val name: String,
    val startDate: String,
    val employeeNumber: Int,
    val role: String,
    val skill: String,
    val norm: String,
    val picture: String
) {
    constructor(
        name: String,
        startDate: String,
        role: String,
        skill: String,
        norm: String,
        picture: String
    ) :
            this(name, startDate, 0, role, skill, norm, picture)

    companion object {
        const val NAME = "name"
        const val START_DATE = "startDate"
        const val EMPLOYEE_NUMBER = "employeeNumber"
        const val ROLE = "role"
        const val SKILL = "skill"
        const val NORM = "norm"
        const val PICTURE = "picture"
    }
}
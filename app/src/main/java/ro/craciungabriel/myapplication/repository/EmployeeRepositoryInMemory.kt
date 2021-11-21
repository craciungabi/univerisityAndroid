package ro.craciungabriel.myapplication.repository

import ro.craciungabriel.myapplication.domain.Employee

class EmployeeRepositoryInMemory : Repository {

    private var employees: ArrayList<Employee> = ArrayList(
        listOf(
            Employee(
                "Gabi",
                "21.11.2021",
                1,
                "Android Developer",
                "Kotlin",
                "Full time",
                "https://i0.wp.com/cdn131.picsart.com/312884292074201.png?type=webp&amp;to=min&amp;r=640"
            ),
            Employee(
                "Andrei",
                "21.11.2020",
                2,
                "QA",
                "Apple",
                "Part time",
                "https://campussafetyconference.com/wp-content/uploads/2020/08/iStock-476085198.jpg"
            ),
            Employee(
                "Maria",
                "21.11.2019",
                3,
                "iOS Developer",
                "Swift",
                "Full time",
                "https://campussafetyconference.com/wp-content/uploads/2020/08/iStock-476085198.jpg"
            ),
            Employee(
                "Gabi",
                "21.11.2021",
                4,
                "Android Developer",
                "Kotlin",
                "Full time",
                "https://campussafetyconference.com/wp-content/uploads/2020/08/iStock-476085198.jpg"
            )
        )
    )

    override fun findAll(): List<Employee> {
        return employees
    }

    override fun remove(position: Int) {
        employees.removeAt(position)
    }

    override fun add(apparel: Employee): Int {
        employees.add(apparel)
        return employees.size
    }

    override fun modify(apparel: Employee, position: Int) {
        employees[position] = apparel
    }

    override fun getPicture(position: Int): String {
        return employees[position].picture
    }
}
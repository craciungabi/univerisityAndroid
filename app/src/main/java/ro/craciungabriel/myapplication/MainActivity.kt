package ro.craciungabriel.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ro.craciungabriel.myapplication.applicationUtility.ApplicationContainer
import ro.craciungabriel.myapplication.databinding.ActivityMainBinding
import ro.craciungabriel.myapplication.databinding.EmployeeCardBinding
import ro.craciungabriel.myapplication.domain.Employee
import ro.craciungabriel.myapplication.domain.Employee.Companion.EMPLOYEE_NUMBER
import ro.craciungabriel.myapplication.domain.Employee.Companion.NAME
import ro.craciungabriel.myapplication.domain.Employee.Companion.NORM
import ro.craciungabriel.myapplication.domain.Employee.Companion.PICTURE
import ro.craciungabriel.myapplication.domain.Employee.Companion.ROLE
import ro.craciungabriel.myapplication.domain.Employee.Companion.SKILL
import ro.craciungabriel.myapplication.domain.Employee.Companion.START_DATE
import ro.craciungabriel.myapplication.repository.Repository


class MainActivity : AppCompatActivity() {
    private lateinit var bindingMain: ActivityMainBinding
    private val applicationContainer = ApplicationContainer()

    // !! - assure value is never null
    private val employeeRepository: Repository =
        applicationContainer.getSingletonEmployeeRepository()!!
    private val adapter = MainAdapter()
    private val addEmployeeActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val intentBundle = intent.extras
                    if (intentBundle != null) {
                        val name = intentBundle.getString(NAME)
                        val startDate = intentBundle.getString(START_DATE)
                        val employeeNumber = intentBundle.getInt(EMPLOYEE_NUMBER)
                        val role = intentBundle.getString(ROLE)
                        val skill = intentBundle.getString(SKILL)
                        val norm = intentBundle.getString(NORM)
                        val picture = intentBundle.getString(PICTURE)

                        // ?: if null return ""
                        adapter.addEmployee(
                            Employee(
                                name ?: "",
                                startDate ?: "",
                                employeeNumber,
                                role ?: "",
                                skill ?: "",
                                norm ?: "",
                                picture ?: ""
                            )
                        )
                    }
                }
            }
        }

    private val modifyApparelActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("MainActivity", "Modify apparel response: ${result.resultCode}")
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null) {
                    val intentBundle = intent.extras
                    if (intentBundle != null) {
                        val position = intentBundle.getInt(ModifyActivity.POSITION)
                        val name = intentBundle.getString(NAME)
                        val startDate = intentBundle.getString(START_DATE)
                        val employeeNumber = intentBundle.getInt(EMPLOYEE_NUMBER)
                        val role = intentBundle.getString(ROLE)
                        val skill = intentBundle.getString(SKILL)
                        val norm = intentBundle.getString(NORM)
                        val picture = intentBundle.getString(PICTURE)

                        adapter.modifyEmployee(
                            Employee(
                                name ?: "",
                                startDate ?: "",
                                employeeNumber,
                                role ?: "",
                                skill ?: "",
                                norm ?: "",
                                picture ?: ""
                            ), position
                        )
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMain = ActivityMainBinding.inflate(layoutInflater)
        val view = bindingMain.root
        setContentView(view)

        bindingMain.recyclerView.adapter = adapter

        adapter.setEmployeeList(
            employeeRepository.findAll()
        )

        bindingMain.addEmployee.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            addEmployeeActivityLauncher.launch(intent)
        }
    }

    inner class MainAdapter : RecyclerView.Adapter<MainViewHolder>() {

        private var employees = mutableListOf<Employee>()

        @SuppressLint("NotifyDataSetChanged")
        fun setEmployeeList(employees: List<Employee>) {
            this.employees = employees.toMutableList()
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val inflater = LayoutInflater.from(parent.context)

            val binding = EmployeeCardBinding.inflate(inflater, parent, false)
            return MainViewHolder(binding)
        }

        @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val employee = employees[position]
            holder.binding.employeeName.text = employee.name
            holder.binding.employeeSkill.text = employee.skill
            holder.binding.employeeRole.text = employee.role
            holder.binding.employeeNumber.text = employee.employeeNumber.toString()
            holder.binding.imageFromCard.load(employee.picture)

            holder.binding.cardId.setOnClickListener {
                val intent = Intent(this@MainActivity, ModifyActivity::class.java)
                intent.putExtra(ModifyActivity.POSITION, position)
                intent.putExtra(NAME, employee.name)
                intent.putExtra(START_DATE, employee.startDate)
                intent.putExtra(EMPLOYEE_NUMBER, employee.employeeNumber)
                intent.putExtra(ROLE, employee.role)
                intent.putExtra(SKILL, employee.skill)
                intent.putExtra(NORM, employee.norm)
                intent.putExtra(PICTURE, employee.picture)

                modifyApparelActivityLauncher.launch(intent)
            }

            holder.binding.buttonDelete.setOnClickListener {
                employees.removeAt(position)
                employeeRepository.remove(position)
                notifyDataSetChanged()
                Toast.makeText(
                    applicationContext,
                    "Employee has been deleted successfully!",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }

        override fun getItemCount(): Int {
            return employees.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun modifyEmployee(apparel: Employee, position: Int) {
            this.employees[position] = apparel
            notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        fun addEmployee(apparel: Employee) {
            this.employees.add(apparel)
            notifyDataSetChanged()
        }
    }

    class MainViewHolder(val binding: EmployeeCardBinding) : RecyclerView.ViewHolder(binding.root)
}

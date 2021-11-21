package ro.craciungabriel.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import ro.craciungabriel.myapplication.applicationUtility.ApplicationContainer
import ro.craciungabriel.myapplication.databinding.ModifiyEmployeeBinding
import ro.craciungabriel.myapplication.domain.Employee
import ro.craciungabriel.myapplication.repository.Repository

class ModifyActivity : AppCompatActivity() {

    private lateinit var bindingModify: ModifiyEmployeeBinding
    private val applicationContainer = ApplicationContainer()
    private val employeeRepository: Repository =
        applicationContainer.getSingletonEmployeeRepository()!!

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingModify = ModifiyEmployeeBinding.inflate(layoutInflater)
        val view = bindingModify.root
        setContentView(view)

        val bundle: Bundle? = intent.extras
        var employeeNumber: Int = -1
        var position = 0
        if (bundle != null) {
            bindingModify.imageModify.load(bundle.getString(Employee.PICTURE))
            bindingModify.nameEditText.setText(bundle.getString(Employee.NAME))
            bindingModify.startDateEditText.setText(bundle.getString(Employee.START_DATE))
            bindingModify.roleEditText.setText(bundle.getString(Employee.ROLE))
            bindingModify.skillEditText.setText(bundle.getString(Employee.SKILL))
            bindingModify.normEditText.setText(bundle.getString(Employee.NORM))
            employeeNumber = bundle.getInt(Employee.EMPLOYEE_NUMBER)
            position = bundle.getInt(POSITION)
        }

        bindingModify.buttonModify.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Employee has been modified successfully!",
                Toast.LENGTH_SHORT
            ).show()
            val picture = employeeRepository.getPicture(position)

            val name = bindingModify.nameEditText.text.toString()
            val startDate = bindingModify.startDateEditText.text.toString()
            val role = bindingModify.roleEditText.text.toString()
            val skill = bindingModify.skillEditText.text.toString()
            val norm = bindingModify.normEditText.text.toString()

            val employee =
                Employee(
                    name,
                    startDate,
                    role,
                    skill,
                    norm,
                    picture
                )

            employeeRepository.modify(employee, position)

            val response = Intent()
            response.putExtra(Employee.EMPLOYEE_NUMBER, employeeNumber)
            response.putExtra(POSITION, position)
            response.putExtra(Employee.NAME, name)
            response.putExtra(Employee.START_DATE, startDate)
            response.putExtra(Employee.ROLE, role)
            response.putExtra(Employee.SKILL, skill)
            response.putExtra(Employee.NORM, norm)
            response.putExtra(Employee.PICTURE, picture)

            setResult(Activity.RESULT_OK, response)
            finish()
        }
    }

    companion object {
        const val POSITION = "position"
    }
}
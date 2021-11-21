package ro.craciungabriel.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import ro.craciungabriel.myapplication.applicationUtility.ApplicationContainer
import ro.craciungabriel.myapplication.databinding.AddEmployeeBinding
import ro.craciungabriel.myapplication.domain.Employee
import ro.craciungabriel.myapplication.domain.Employee.Companion.EMPLOYEE_NUMBER
import ro.craciungabriel.myapplication.domain.Employee.Companion.NAME
import ro.craciungabriel.myapplication.domain.Employee.Companion.NORM
import ro.craciungabriel.myapplication.domain.Employee.Companion.PICTURE
import ro.craciungabriel.myapplication.domain.Employee.Companion.ROLE
import ro.craciungabriel.myapplication.domain.Employee.Companion.SKILL
import ro.craciungabriel.myapplication.domain.Employee.Companion.START_DATE
import ro.craciungabriel.myapplication.repository.Repository


class AddActivity : AppCompatActivity() {

    private lateinit var bindingAdd: AddEmployeeBinding
    private val applicationContainer = ApplicationContainer()
    private val employeeRepository: Repository =
        applicationContainer.getSingletonEmployeeRepository()!!

    private val pickImage = 100
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAdd = AddEmployeeBinding.inflate(layoutInflater)
        val view = bindingAdd.root
        setContentView(view)

        bindingAdd.addEmployeeButton.setOnClickListener {

            val name = bindingAdd.nameEditText.text.toString()
            val startDate = bindingAdd.startDateEditText.text.toString()
            val role = bindingAdd.roleEditText.text.toString()
            val skill = bindingAdd.skillEditText.text.toString()
            val norm = bindingAdd.normEditText.text.toString()
            val picture = bindingAdd.urlEditText.text.toString()

            val employee =
                Employee(name, startDate, role, skill, norm, picture)


            val employeeNumber = employeeRepository.add(employee)

            val response = Intent()
            response.putExtra(NAME, name)
            response.putExtra(START_DATE, startDate)
            response.putExtra(EMPLOYEE_NUMBER, employeeNumber)
            response.putExtra(ROLE, role)
            response.putExtra(SKILL, skill)
            response.putExtra(NORM, norm)
            response.putExtra(PICTURE, picture)

            setResult(Activity.RESULT_OK, response)
            finish()
        }

        bindingAdd.imageAdd.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            bindingAdd.imageAdd.setImageURI(imageUri)
        }
    }
}
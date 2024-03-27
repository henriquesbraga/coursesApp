package dev.henriquebraga.myrealmapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.henriquebraga.myrealmapplication.models.Address
import dev.henriquebraga.myrealmapplication.models.Course
import dev.henriquebraga.myrealmapplication.models.Student
import dev.henriquebraga.myrealmapplication.models.Teacher
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.realmListOf
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val realm = MyApp.realm

    val courses = realm
        .query<Course>()
        .asFlow()
        .map { results ->
            results.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    val students = realm
        .query<Student>().distinct("name")
        .asFlow()
        .map { results ->
            results.list.toList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )




    var courseDetails: Course? by mutableStateOf(null)
        private set

    var newCourseDialogVisible by mutableStateOf(false)
        private set



    init {

        //Verificar se existem dados. se não, criar.
        createSampleEntries()
    }

    fun showNewCourseDialog() {
        newCourseDialogVisible = true
    }

    fun hideNewCourseDialog() {
        newCourseDialogVisible = false
    }

    fun showCourseDetails(course: Course) {
        courseDetails = course
    }

    fun hideCourseDetails() {
        courseDetails = null
    }

    fun createEntry(
        courseName: String,
        student: String,
        addressName: String,
        fullname: String,
        cityname: String,
        housenumber: Int,
        zipcode: Int
    ) {
        viewModelScope.launch {
            realm.write {
                var address1 = Address().apply {
                    fullName = fullname
                    street = addressName
                    houseNumber = housenumber
                    zip = zipcode
                    city = cityname
                }

                var course1 = Course().apply {
                    name = courseName
                }

                var teacher1 = Teacher().apply {
                    address = address1
                    courses = realmListOf(course1)
                }

                course1.teacher = teacher1
                address1.teacher = teacher1

                val student1 = Student().apply {
                    name = student
                }

                course1.enrolledStudents.add(student1)
                copyToRealm(teacher1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student1, updatePolicy = UpdatePolicy.ALL)
            }
        }
        hideNewCourseDialog()
    }

    private fun createSampleEntries() {
        viewModelScope.launch {
            realm.write {
                var address1 = Address().apply {
                    fullName = "John Doe"
                    street = "John Doe Street"
                    houseNumber = 24
                    zip = 1234
                    city = "John City"
                }
                var address2 = Address().apply {
                    fullName = "Jane Doe"
                    street = "Jane Doe Street"
                    houseNumber = 25
                    zip = 1234
                    city = "Jane City"
                }
                var course1 = Course().apply {
                    name = "Kotlin Programmin made easy"
                }
                var course2 = Course().apply {
                    name = "Android Basics"
                }
                var course3 = Course().apply {
                    name = "Asynchronous Programming with coroutines"
                }
                var teacher1 = Teacher().apply {
                    address = address1
                    courses = realmListOf(course1, course2)
                }
                var teacher2 = Teacher().apply {
                    address = address2
                    courses = realmListOf(course3)
                }

                course1.teacher = teacher1
                course2.teacher = teacher1
                course3.teacher = teacher2

                address1.teacher = teacher1
                address2.teacher = teacher2

                val student1 = Student().apply {
                    name = "John Junior"
                }

                val student2 = Student().apply {
                    name = "Jane Junior"
                }

                course1.enrolledStudents.add(student1)
                course2.enrolledStudents.add(student2)
                course3.enrolledStudents.addAll(listOf(student1, student2))

                copyToRealm(teacher1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(teacher2, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(course1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course2, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(course3, updatePolicy = UpdatePolicy.ALL)

                copyToRealm(student1, updatePolicy = UpdatePolicy.ALL)
                copyToRealm(student2, updatePolicy = UpdatePolicy.ALL)
            }
        }
    }

    fun deleteCourse() {
        viewModelScope.launch {
            realm.write {
                val course = courseDetails ?: return@write
                val latestCourse = findLatest(course) ?: return@write
                delete(latestCourse)
                courseDetails = null
            }
        }
    }
}
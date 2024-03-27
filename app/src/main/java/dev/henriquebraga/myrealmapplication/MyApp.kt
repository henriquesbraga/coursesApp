package dev.henriquebraga.myrealmapplication

import android.app.Application
import dev.henriquebraga.myrealmapplication.models.Address
import dev.henriquebraga.myrealmapplication.models.Course
import dev.henriquebraga.myrealmapplication.models.Student
import dev.henriquebraga.myrealmapplication.models.Teacher
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MyApp: Application() {

    companion object {
        lateinit var realm: Realm
    }

    override fun onCreate() {
        super.onCreate()
        realm = Realm.open(
            configuration = RealmConfiguration.create(
                schema = setOf(
                    Address::class,
                    Teacher::class,
                    Course::class,
                    Student::class
                )
            )
        )
    }
}
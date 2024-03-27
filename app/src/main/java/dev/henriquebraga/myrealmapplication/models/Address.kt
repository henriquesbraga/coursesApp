package dev.henriquebraga.myrealmapplication.models

import io.realm.kotlin.types.EmbeddedRealmObject
import io.realm.kotlin.types.RealmObject

// Teacher 1-to-1 Address
// Teacher 1-to-many Course
// Student many-to-many Course

class Address: EmbeddedRealmObject {
    var fullName: String = ""
    var street: String = ""
    var houseNumber: Int = 0
    var zip: Int = 0
    var city: String = ""
    var teacher: Teacher? = null
}
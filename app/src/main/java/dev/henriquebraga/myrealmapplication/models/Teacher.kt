package dev.henriquebraga.myrealmapplication.models

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.realmSetOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class Teacher: RealmObject {
    @PrimaryKey var _id: ObjectId = ObjectId()
    var address: Address? = null
    var courses: RealmList<Course> = realmListOf()
}
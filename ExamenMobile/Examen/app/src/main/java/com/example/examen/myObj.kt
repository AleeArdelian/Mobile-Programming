package com.example.examen

class myObj(idd: Int, nr:Int) {
    var id =idd
    var ord =nr
        get() = field
        set(value) {
            field += value
        }
}
import 'dart:async';

import 'package:flutter_app/doctor.dart';
import 'package:path/path.dart';
import 'package:sqflite/sqflite.dart';
import 'package:sqflite/sqlite_api.dart';


class DatabaseProvider {
  Future<Database> getDatabase() async {
    return openDatabase(
      join(await getDatabasesPath(), "doctors.db"),
      onCreate: (db, version) async {
        await db.execute(
          "CREATE TABLE doctor(id INTEGER PRIMARY KEY, name TEXT, speciality TEXT)"
        );
      },
      version: 1
    );
  }

  Future<List<Doctor>> getDoctors() async {
    final Database db = await getDatabase();
    final List<Map<String, dynamic>> maps = await db.query("doctor");
    return List.generate(maps.length, (i) {
      return Doctor(
        id: maps[i]["id"],
        name: maps[i]["name"],
        speciality: maps[i]["speciality"]
      );
    });
  }

  Future<int> insertDoctor(Doctor doc) async {
    final Database db = await getDatabase();
    doc.id = await db.insert(
        "doctor",
        doc.toMap(),
        conflictAlgorithm: ConflictAlgorithm.replace
    );
    return doc.id;
  }

  Future<int> updateDoctor(Doctor doc) async {
    final Database db = await getDatabase();
    return await db.update(
      "doctor",
        doc.toMap(),
      where: "id = ?",
      whereArgs: [doc.id]
    );
  }

  Future<int> deleteDoctor(int id) async {
    final Database db = await getDatabase();
    return await db.delete(
      "doctor",
      where: "id = ?",
      whereArgs: [id]
    );
  }

  Future<void> deleteAll() async {
    final Database db = await getDatabase();
    await db.delete(
        "doctor"
    );
  }
}
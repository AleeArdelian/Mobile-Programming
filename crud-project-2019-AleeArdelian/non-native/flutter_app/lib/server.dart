import 'package:flutter_app/doctor.dart';

import 'database.dart';
import 'dart:convert';
import 'package:http/http.dart' as httpClient;

class ServerHelper {

  static ServerHelper server;
  String url = "http://10.0.2.2:8080/vethouse/api/v1/doctors";
  ServerHelper._createInstance();
  DatabaseProvider localDB = new DatabaseProvider();

  factory ServerHelper(){
    if(server == null){
      ServerHelper._createInstance();
    }
    return server;
  }

  Future<List<Doctor>> getDoctorsSrv() async {
    final response = await httpClient.get(this.url);
    var doctorList = await json.decode(response.body);
    int count = doctorList.length;

    List<Doctor> docList = List<Doctor>();
    for (int i = 0; i < count; i++) {
      docList.add(Doctor.fromMap(doctorList[i]));
    }
    return docList;
  }

  Future<int> insertSrv(Doctor d) async {
      var response = await httpClient.post('${this.url}',
          body: json.encode(d.toMap()), headers: {"Content-Type" : "application/json"});
      int id = json.decode(response.body);
      d.id = id;
      this.localDB.insertDoctor(d);
      return d.id;
  }

  Future<int> deleteSrv(int id) async {
      var response = await httpClient.delete('${this.url}/$id');
      if (response.statusCode != 200) {
        return -1;
      } else {
        this.localDB.deleteDoctor(id);
        return id;
    }
  }

  Future<int> updateSrv(Doctor d) async{
    var id = d.id;
    var response = await httpClient.put('${this.url}/$id',
        body: json.encode(d.toMap()), headers: {"Content-Type" : "application/json"});
    if(response.statusCode !=200)
      return -1;
    this.localDB.updateDoctor(d);
    return d.id;
  }

}

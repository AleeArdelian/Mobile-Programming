class Doctor {
  int id;
  String name;
  String speciality;

  Doctor({ this.id, this.name, this.speciality});

  factory Doctor.fromMap(Map<String, dynamic> json) => new Doctor(
    id: json["id"],
    name: json["name"],
      speciality : json["speciality"]
  );

  Doctor.map(dynamic obj) {
    this.name = obj["name"];
    this.id = obj["id"];
    this.speciality = obj["speciality"];
  }
  Map<String, dynamic> toMap() => {
    "id": id,
    "name": name,
    "speciality" : speciality
  };

  Map<String, dynamic> toMapNoId() => {
    "name": name,
    "speciality" : speciality
  };
}
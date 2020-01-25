import 'package:flutter/material.dart';
import 'package:flutter_app/doctor.dart';
import 'package:flutter_app/server.dart';
import 'database.dart';
import 'package:connectivity/connectivity.dart';

void main() => runApp(ToDoApp());

class ToDoApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Vet house',
      theme: ThemeData(
        backgroundColor: Colors.greenAccent[500],
        primarySwatch: Colors.greenAccent[500],
      ),
      home: new Doctors()
    );
  }
}

class Doctors extends StatefulWidget {
  @override
  createState() => new DoctorsState();
}

class DoctorsState extends State<Doctors> {

  DatabaseProvider dbProvider = new DatabaseProvider();
  final ServerHelper serverHelper = new ServerHelper();

  TextEditingController controller = TextEditingController();
  TextEditingController controller2 = TextEditingController();
  TextEditingController controller3 = TextEditingController();
  TextEditingController controller4 = TextEditingController();

  List<Doctor> docs = <Doctor>[];

  Future<List<Doctor>> getItems() async {
    //List<Doctor> doctors = await serverHelper.getItems();
    return await serverHelper.localDB.getDoctors();
  }

  void addItem(Doctor doc) async {
    if (doc.name.length > 0 && doc.speciality.length > 0) {
      //int res = await dbProvider.insertDoctor(doc);
      await serverHelper.insertSrv(doc);
        setState((){});
      }
  }

  void removeItem(int id) async {
    //int res = await dbProvider.deleteDoctor(id);
    await serverHelper.deleteSrv(id);
    setState((){});
  }


  void updateItem(Doctor doc) async {
    if (doc.name.length > 0 && doc.speciality.length > 0) {
      //var res = await dbProvider.updateDoctor(doc);
      await serverHelper.updateSrv(doc);
      setState(() {});
    }
  }

  void removeScreen(Doctor doc) {
    showDialog(
        context: context,
        builder: (BuildContext context) {
          return new AlertDialog(
              title: new Text("Delete doctor"),
              actions: <Widget>[
                new FlatButton(
                    child: new Text("CANCEL"),
                    onPressed: () => Navigator.of(context).pop()
                ),
                new FlatButton(
                    child: new Text("OK"),
                    onPressed: () {
                      removeItem(doc.id);
                      Navigator.of(context).pop();
                    }
                )
              ]
          );
        }
    );
  }
///////////////// SA NU REINCARCE TOATA LISTA


  Widget buildList() {
    return FutureBuilder(
        builder: (builder, snapshot) {
          if (!snapshot.hasData) {
            return Center(child: CircularProgressIndicator());
          }
          List<Doctor> items = snapshot.data;
          return new ListView.builder(
              itemBuilder: (context, index) {
                if (index < items.length) {
                  return buildItem(items[index]);
                } else {
                  return null;
                }
              }
          );

        },
      future: this.getItems()
    );
  }

  Widget buildItem(Doctor doc) {
    return new ListTile(
        title: Column(
            children : [
            new Text(doc.name),
            new Text(doc.speciality),
            ]
        ),
        onTap: () => removeScreen(doc),
        onLongPress: () => updateScreen(doc),
    );
  }

  @override
  Widget build(BuildContext context) {
    return new Scaffold(
        appBar: new AppBar(
            title: new Text("Our doctors"),
            backgroundColor: Colors.greenAccent[100],
        ),
        body: buildList(),
        floatingActionButton: new FloatingActionButton(
            onPressed: addScreen,
            tooltip: "Add new doctor",
            backgroundColor: Colors.greenAccent[100],
            child: new Icon(Icons.add)
        )
    );
  }

  void addScreen() {
    Navigator.of(context).push(
        new MaterialPageRoute(
            builder: (context) {
              return new Scaffold(

                  appBar: new AppBar(
                      title: new Text("Add new doctor"),
                    backgroundColor: Colors.greenAccent[100],
                  ),
                  body: Column(
                    children: [
                      new TextField(
                      autofocus: true,
                      controller: controller,
                      decoration: new InputDecoration(
                        hintText: "Enter the new name",
                        contentPadding: const EdgeInsets.all(16.0)
                        ),
                      ),
                      SizedBox(height: 20.0),

                      new TextField(
                        autofocus: true,
                      controller: controller2,
                        decoration: new InputDecoration(
                            hintText: "Enter the new speciality",
                            contentPadding: const EdgeInsets.all(16.0)
                        )
                      ),
                      SizedBox(height: 10.0),

                      new FlatButton(
                        onPressed: (){
                          addItem(new Doctor(id: -1, name: controller.text,speciality: controller2.text));

                          Navigator.pop(context);
                          controller.clear();
                          controller2.clear();
                        },
                        child: Text("Add doctor")
                  )
                ])
              );
            }
        )
    );
  }

  void updateScreen(Doctor doc) async {
    Navigator.of(context).push(
        new MaterialPageRoute(
            builder: (context) {
              return new Scaffold(
                  appBar: new AppBar(
                      title: new Text("Update doctor"),
                    backgroundColor: Colors.greenAccent[100],
                  ),
                  body: Column(
                      children: [
                        new TextField(
                        autofocus: true,
                        controller: controller3,
                        decoration: new InputDecoration(
                            hintText: "Enter the new name",
                            contentPadding: const EdgeInsets.all(16.0)
                          ),
                        ),
                         SizedBox(height: 20.0),

                        new TextField(
                        autofocus: true,
                        controller: controller4,
                        decoration: new InputDecoration(
                            hintText: "Enter the new speciality",
                            contentPadding: const EdgeInsets.all(16.0)
                        )
                      ),
                        SizedBox(height: 10.0),

                        new FlatButton(
                          onPressed: (){
                              updateItem(new Doctor(id: doc.id, name: controller3.text,speciality: controller4.text));
                              Navigator.pop(context);
//                              controller3.clear();
//                              controller4.clear();
                          },
                            child: Text("Update doctor")
                        )
                  ])
              );
            }
        )
    );
  }
}
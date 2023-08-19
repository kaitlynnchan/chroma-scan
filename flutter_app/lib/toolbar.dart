import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyToolBarApp());
}

class MyToolBarApp extends StatelessWidget {
  const MyToolBarApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Chroma Scan',
      theme: ThemeData(
        // This is the theme of your application.
        colorScheme: ColorScheme.dark(),
        useMaterial3: true,
      ),
      home: const MyToolBarPage(title: 'ChromaScan'),
    );
  }
}

class MyToolBarPage extends StatefulWidget {
    const MyToolBarPage({super.key, required this.title});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MyToolBarPage> createState() => _MyToolBarPage();

}

class _MyToolBarPage extends State<MyToolBarPage> {

  void _onPressed(){
  }

  @override
  Widget build(BuildContext context) {
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      body: Container(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent
        padding: EdgeInsets.all(10),
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: <Widget>[
            IconTextButton(onPressed: _onPressed, icon: Icons.search, buttonLabel: "Select",),
            IconTextButton(onPressed: _onPressed, icon: Icons.scatter_plot, buttonLabel: "Select Points",),
            IconTextButton(onPressed: _onPressed, icon: Icons.photo_size_select_large, buttonLabel: "Select Image",),
            IconTextButton(onPressed: _onPressed, icon: Icons.upload_file, buttonLabel: "Upload File",),
          ],
        ),
      ),
    );
  }
}

class IconTextButton extends StatelessWidget {
  const IconTextButton({
    super.key,
    required this.onPressed,
    required this.icon,
    required this.buttonLabel,
  });

  final void Function() onPressed;
  final IconData icon;
  final String buttonLabel;

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Container(
              child: Column(
                children: [
                  IconButton(
                    onPressed: onPressed,
                    icon: Icon(icon),
                    iconSize: 30,
                  ),
                  Text(buttonLabel, style: TextStyle(fontSize: 12),)
                ],
              ),
            );
  }
}
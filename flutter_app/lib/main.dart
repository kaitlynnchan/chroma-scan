import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:sidebarx/sidebarx.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Chroma Scan',
      theme: ThemeData(
        // This is the theme of your application.
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.redAccent),
        useMaterial3: true,
      ),
      home: Builder(builder: (context) { return MyHomePage(title: 'ChromaScan'); },),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  final _controller = SidebarXController(selectedIndex: 0, extended: true);

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
        color: lightYellow,
        child: Row(
          children: [
            _SidebarX(controller: _controller),
            Expanded(
              // Center is a layout widget. It takes a single child and positions it
              // in the middle of the parent
              child: AnimatedBuilder(
                animation: _controller,
                builder: (context, child) {
                  // final pageTitle = _getTitleByIndex(_controller.selectedIndex);
                  switch (_controller.selectedIndex) {
                    case 0:
                      return _HomeScreen(title: "ChromaScan");
                    case 2:
                      return UploadScreen(title: "Upload Image");
                    default:
                      return Text("data");
                  }
                }
              ),
            ),
          ],
        ),
      ),
    );
  }
}

class _SidebarX extends StatelessWidget {
  const _SidebarX({
    Key? key,
    required SidebarXController controller,
  })  : _controller = controller,
        super(key: key);

  final SidebarXController _controller;

  @override
  Widget build(BuildContext context) {
    return SidebarX(
      controller: _controller,
      theme: SidebarXTheme(
        margin: const EdgeInsets.all(10),
        width: 90,
        decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          color: canvasColor,
        ),
        hoverColor: scaffoldBackgroundColor,
        textStyle: TextStyle(color: navyBlue.withOpacity(0.7)),
        selectedTextStyle: const TextStyle(color: navyBlue),
        itemTextPadding: const EdgeInsets.only(left: 30),
        selectedItemTextPadding: const EdgeInsets.only(left: 30),
        itemDecoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10),
          border: Border.all(color: canvasColor),
        ),
        hoverTextStyle: TextStyle(color: navyBlue,),
        selectedItemDecoration: BoxDecoration(
          borderRadius: BorderRadius.circular(10),
          color: accentCanvasColor,
        ),
        iconTheme: IconThemeData(
          color: navyBlue.withOpacity(0.7),
          size: 20,
        ),
        selectedIconTheme: const IconThemeData(
          color: navyBlue,
          size: 20,
        ),
      ),
      extendedTheme: const SidebarXTheme(
        width: 150,
        decoration: BoxDecoration(
          color: canvasColor,
        ),
      ),
      footerDivider: divider,
      headerBuilder: (context, extended) {
        return const SizedBox(
          height: 100,
          child: Padding(
            padding: EdgeInsets.all(16.0),
            child: Text("Chroma\nScan", 
              style: TextStyle(
                  fontWeight: FontWeight.bold, 
                  color: navyBlue,
                ),
              textAlign: TextAlign.center,
            ),
          ),
        );
      },
      items: [
        SidebarXItem(
          icon: Icons.home,
          label: 'Home',
          onTap: () {
            debugPrint('ChromaScan');
          },
        ),
        const SidebarXItem(
          icon: Icons.photo_size_select_large,
          label: 'Select',
        ),
        const SidebarXItem(
          icon: Icons.upload_file,
          label: 'Upload',
        ),
      ],
    );
  }
}

class _HomeScreen extends StatelessWidget {
  const _HomeScreen({
    Key? key,
    required String this.title,
  }) : super(key: key);

  final String title;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      height: 400,
      margin: EdgeInsets.fromLTRB(20, 10, 20, 20),
      child: Column(
        children: <Widget>[
          Align(
            alignment: Alignment.topLeft,
            child: Text(
              title,
              style: const TextStyle(
                color: canvasColor,
                fontSize: 32,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.only(top: 10),
            child: LabelBox(label: "Name", text: "White wonderland",)
          ),
          Container(
            margin: EdgeInsets.only(top: 10),
            child: const Row(
              children: [
                // hex of the colour
                Expanded(
                  child: Padding(
                    padding: EdgeInsets.only(right: 10),
                    child: LabelBox(label: "HEX", text: "#FFFFFF"),
                  )
                ),
                // rgb of the colour
                Expanded(child: LabelBox(label: "RGB", text: "255, 255, 255")),
              ],
            ),
          ),
          Flexible(
            flex: 1,
            child: Container(
              margin: EdgeInsets.only(top: 20),
              child: SizedBox.expand(
                child: DecoratedBox(
                  decoration: BoxDecoration(
                    // visible colour
                    color: Color.fromRGBO(21, 243, 234, 1),
                    border: Border.all(
                      color: Colors.black,
                      width: 2,
                    ),
                    borderRadius: BorderRadius.circular(10)
                  ),
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}

String _getTitleByIndex(int index) {
  switch (index) {
    case 0:
      return 'Home';
    case 1:
      return 'Select';
    case 2:
      return 'Upload';
    default:
      return 'Not found page';
  }
}

class LabelBox extends StatelessWidget {
  const LabelBox({
    super.key,
    required this.label,
    required this.text,
  });

  final String label;
  final String text;

  void _onPressed(){
    print(text);
    Clipboard.setData(ClipboardData(text: text));
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(label),
          Container(
            margin: EdgeInsets.only(top: 5),
            padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
            decoration: BoxDecoration(
              border: Border.all(
                color: Colors.black,
                width: 2
              ),
              borderRadius: BorderRadius.circular(10),
            ),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(text),
                IconButton(
                  onPressed: _onPressed,
                  icon: const Icon(Icons.content_copy),
                  iconSize: 15,
                ),
              ]
            ),
          ),
        ],
      ),
    );
  }
}

class UploadScreen extends StatefulWidget {
  const UploadScreen({super.key, required this.title});

  final String title;

  @override
  State<UploadScreen> createState() => _UploadScreen();
}

class _UploadScreen extends State<UploadScreen> {
  String url = "";

  Future<void> _filePicker() async {
    try{
      FilePickerResult? result = await FilePicker.platform.pickFiles(
        type: FileType.custom,
        allowedExtensions: ['jpg', 'png'],
      );

      if (result != null) {
        String filePath = result.files.single.path!;
        File file = File(filePath);
        print(filePath);
        _updateFile(filePath);
      } else {
        // User canceled the picker
      }
    } catch(_){

    }
  }

  void _updateFile(String filePath) {
    setState(() {
        url = filePath;
    });
  }

  void _uploadFile(){

  }
  
  @override
  Widget build(BuildContext context) {
    return Container(
      width: double.infinity,
      height: 400,
      margin: EdgeInsets.fromLTRB(20, 10, 20, 20),
      child: Column(
        children: <Widget>[
          Align(
            alignment: Alignment.topLeft,
            child: Text(
              widget.title,
              style: const TextStyle(
                color: canvasColor,
                fontSize: 32,
                fontWeight: FontWeight.bold,
              ),
            ),
          ),
          Container(
            margin: EdgeInsets.fromLTRB(0, 20, 0, 20),
            child: Row(
              children: [
                Expanded(
                  child: Container(
                    margin: EdgeInsets.only(right: 10),
                    child: OutlinedButton(
                      onPressed: _filePicker,
                      child: Text(url, style: TextStyle(color: navyBlue),),
                      style: OutlinedButton.styleFrom(
                        side: BorderSide(width: 2.0, color: Colors.black,),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(10),
                        ),
                      ),
                    ),
                  ),
                ),
                FilledButton(onPressed: _uploadFile, child: const Text('Upload'),
                  style: FilledButton.styleFrom(
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(10),
                    ),
                    backgroundColor: canvasColor,
                  ),
                ),
                
              ],
            ),
          ),
          Container(
            height: 200,
            child: !url.isEmpty ? Image(fit: BoxFit.contain, image: FileImage(File(url)),) : Container(),
          ),
        ],
      ),
    );
  }
}

const primaryColor = Color.fromRGBO(243, 191, 3, 1);
const canvasColor = Color.fromRGBO(246, 193, 3, 1);
const scaffoldBackgroundColor = Color.fromRGBO(255, 219, 91, 1);
const accentCanvasColor = Color.fromRGBO(210, 165, 0, 1);
const white = Colors.white;
const navyBlue = Color.fromRGBO(31, 38, 46, 1);
const lightYellow = Color.fromRGBO(255, 246, 225, 1);
final actionColor = const Color.fromRGBO(246, 193, 3, 1).withOpacity(0.6);
final divider = Divider(color: navyBlue.withOpacity(0.3), height: 1);
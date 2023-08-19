import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

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
      home: const MyHomePage(title: 'ChromaScan'),
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
  int _counter = 0;

  void _incrementCounter() {
    setState(() {
      // This call to setState tells the Flutter framework that something has
      // changed in this State, which causes it to rerun the build method below
      // so that the display can reflect the updated values. If we changed
      // _counter without calling setState(), then the build method would not be
      // called again, and so nothing would appear to happen.
      _counter++;
    });
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
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Container(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent
        padding: EdgeInsets.all(20),
        child: Column(
          children: <Widget>[
            Flexible(
              // name of the colour
              flex: 1,
              child: Container(
                margin: EdgeInsets.only(bottom: 10),
                child: LabelBox(label: "Name", text: "White wonderland",)),
            ),
            Flexible(
              flex: 1,
              child: Container(
                margin: EdgeInsets.only(bottom: 10),
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
            ),
            Flexible(
              flex: 1,
              child: Container(
                child: SizedBox.expand(
                  child: DecoratedBox(
                    decoration: BoxDecoration(
                      // visible colour
                      color: Color.fromRGBO(21, 243, 234, 1),
                      border: Border.all(
                        color: Colors.black
                      ),
                      borderRadius: BorderRadius.circular(10)
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
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
            padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
            decoration: BoxDecoration(
              border: Border.all(
                color: Colors.black,
              ),
              borderRadius: BorderRadius.circular(10)
            ),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: [
                Text(text),
                IconButton(
                  onPressed: _onPressed,
                  icon: Icon(Icons.content_copy),
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

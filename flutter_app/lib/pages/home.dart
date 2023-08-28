import 'package:flutter/material.dart';
import 'package:flutter_app/api_service.dart';
import 'package:flutter_app/colour_model.dart';

import '../main.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key, required this.title});

  final String title;

  @override
  State<HomeScreen> createState() => _HomeScreen();
}

class _HomeScreen extends State<HomeScreen> {
  late List<ColourModel>? _colourModel = [];

  String? name;
  String? hex;
  Rgb? rgb;

  void setData() async{
    _colourModel = (await ApiService().getDominantColour())!;
    print(_colourModel);
  }

  @override
  Widget build(BuildContext context) {
    print(url);
    if(!url.isEmpty) setData();
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
            margin: EdgeInsets.only(top: 10),
            child: LabelBox(label: "Name", text: name ?? "",)
          ),
          Container(
            margin: EdgeInsets.only(top: 10),
            child: Row(
              children: [
                // hex of the colour
                Expanded(
                  child: Padding(
                    padding: EdgeInsets.only(right: 10),
                    child: LabelBox(label: "HEX", text: hex ?? "",),
                  )
                ),
                // rgb of the colour
                Expanded(child: LabelBox(label: "RGB", text: rgb?.toString() ?? "")),
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
                    color: Color.fromRGBO(rgb?.red ?? 255, rgb?.green ?? 255, rgb?.blue ?? 255, 1),
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

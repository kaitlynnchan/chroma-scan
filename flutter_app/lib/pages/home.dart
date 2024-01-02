import 'dart:async';
import 'dart:io';

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
  late ColourModel? _colourModel = ColourModel.init();
  late Timer timer;

  @override
  void initState() {
    timer = Timer.periodic(Duration(seconds: 1), (_) {
      setState(() {});
    });
  }

  @override
  void dispose() {
    timer?.cancel();
  }
  
  Future<void> setData() async{
    _colourModel = await ApiService().getDominantColour();
    print(_colourModel);
  }

  @override
  Widget build(BuildContext context) {
    print(url);
    if(!url.isEmpty) setData();
    return Container(
      width: double.infinity,
      height: 450,
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
            child: LabelBox(label: "Name", text: _colourModel!.name,)
          ),
          Container(
            margin: EdgeInsets.only(top: 10),
            child: Row(
              children: [
                // hex of the colour
                Expanded(
                  child: Padding(
                    padding: EdgeInsets.only(right: 10),
                    child: LabelBox(label: "HEX", text: _colourModel!.hex,),
                  )
                ),
                // rgb of the colour
                Expanded(child: LabelBox(label: "RGB", text: _colourModel!.rgb.toString())),
              ],
            ),
          ),

          
          Flexible(
            flex: 2,
            fit: FlexFit.tight,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: <Widget>[
                FlexBox(label: "Source", imgUrl: url, color: Color.fromRGBO(255,255,255, 0),),
                const SizedBox(width: 10,),
                FlexBox(label: "Result", imgUrl: "", color: 
                  Color.fromRGBO(_colourModel!.rgb.red, _colourModel!.rgb.green, _colourModel!.rgb.blue, 1),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}

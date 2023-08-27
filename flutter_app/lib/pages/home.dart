import 'package:flutter/material.dart';

import '../main.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({
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

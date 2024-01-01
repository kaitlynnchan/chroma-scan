import 'dart:io';

import 'package:file_picker/file_picker.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/pages/home.dart';
import 'package:path_provider/path_provider.dart';
import 'package:screen_capturer/screen_capturer.dart';

import '../main.dart';
import '../colour_model.dart';
import '../api_service.dart';

class UploadScreen extends StatefulWidget {
  const UploadScreen({super.key, required this.title});

  final String title;

  @override
  State<UploadScreen> createState() => _UploadScreen();
}

class _UploadScreen extends State<UploadScreen> {
  late List<ColourModel>? _colourModel = [];

  Future<void> _screenCapture() async{
    Directory directory = await getApplicationDocumentsDirectory();
    String imageName = 'Screenshot-${DateTime.now().millisecondsSinceEpoch}.png';
    String imagePath = '${directory.path}/screen_capturer_example/Screenshots/$imageName';
    CapturedData? capturedData = await screenCapturer.capture(
      mode: CaptureMode.region, // screen, window
      imagePath: imagePath,
      copyToClipboard: true,
      silent: true,
    );
    print(capturedData);
    if(capturedData != null){
      File f = new File(imagePath);
      _updateFile(capturedData.imagePath ?? "", f.path.split(Platform.pathSeparator).last);
    }
  }

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
        _updateFile(filePath, result.files.single.name);
      } else {
        // User canceled the picker
      }
    } catch(_){

    }
  }

  void _updateFile(String filePath, String name) {
    setState(() {
        url = filePath;
        fileName = name;
    });
  }

  Future<void> _uploadFile() async {
    final upload = ApiService().uploadFile(url);
    upload.then(
      (value) => controller.selectIndex(0),
    );
  }
  
  @override
  Widget build(BuildContext context) {
    if(widget.title.contains("Select")){
      _screenCapture();
    }
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

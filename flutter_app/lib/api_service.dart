import 'dart:developer';
import 'dart:io';

import 'package:flutter_app/main.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';
import 'constants.dart';
import 'color_model.dart';

class ApiService {
  Future<ColorModel>? getDominantColor() async {
    try {
      String fileUrl = ApiConstants.baseApiCall + fileName + ApiConstants.dominantColor;
      print(fileUrl);
      var url = Uri.parse(ApiConstants.baseUrl + fileUrl);
      var response = await http.get(url);
      if (response.statusCode == 200) {
        print("Successfully gotten data");
        ColorModel _model = colorModelFromJson(response.body);
        return _model;
      }
      throw Error();
    } catch (e) {
      log("Error: " + e.toString());
      throw Error();
    }
  }

  Future<void> uploadFile(String filePath) async {
    try {
      var url = Uri.parse(ApiConstants.baseUrl + ApiConstants.upload);

      var request = http.MultipartRequest("POST", url);
      print(request);
      print(filePath);
      // request.fields['user'] = 'someone@somewhere.com';
      request.files.add(await http.MultipartFile.fromPath(
          'file', 
          filePath,
          filename: fileName,
          contentType: MediaType('multipart', 'form-data'),
      ));
      request.send().then((response) {
        print(response.statusCode);
        if (response.statusCode == 302) print("Uploaded!");
      });
    } catch (e) {
      log("Error:" + e.toString());
    }
  }
}

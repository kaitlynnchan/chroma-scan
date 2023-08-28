import 'dart:convert';

List<ColourModel> colourModelFromJson(String str) =>
    List<ColourModel>.from(json.decode(str).map((x) => ColourModel.fromJson(x)));

String colourModelToJson(List<ColourModel> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class ColourModel {
  ColourModel({
    required this.rgb,
    required this.hex,
    required this.name,
  });

  Rgb rgb;
  String hex;
  String name;

  factory ColourModel.fromJson(Map<String, dynamic> json) => ColourModel(
        rgb: Rgb.fromJson(json["rgb"]),
        hex: json["hex"],
        name: json["name"],
      );

  Map<String, dynamic> toJson() => {
        "rgb": rgb.toJson(),
        "hex": hex,
        "name": name,
      };
}

class Rgb {
  Rgb({
    required this.red,
    required this.green,
    required this.blue,
  });

  int red;
  int green;
  int blue;

  factory Rgb.fromJson(Map<String, dynamic> json) => Rgb(
        red: json["red"],
        green: json["green"],
        blue: json["blue"],
      );

  Map<String, dynamic> toJson() => {
        "red": red,
        "green": green,
        "blue": blue,
      };
}
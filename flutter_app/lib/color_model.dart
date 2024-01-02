import 'dart:convert';
    
ColorModel colorModelFromJson(String str) =>
    ColorModel.fromJson((json.decode(str) as Map<String, dynamic>));

String colorModelToJson(List<ColorModel> data) =>
    json.encode(List<dynamic>.from(data.map((x) => x.toJson())));

class ColorModel {
  ColorModel({
    required this.rgb,
    required this.hex,
    required this.name,
  });

  Rgb rgb;
  String hex;
  String name;

  factory ColorModel.init() => ColorModel(
        rgb: Rgb.init(),
        hex: "",
        name: "",
      );

  factory ColorModel.fromJson(Map<String, dynamic> json) => ColorModel(
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

  factory Rgb.init() => Rgb(
        red: 255,
        green: 255,
        blue: 255,
      );

  factory Rgb.fromJson(Map<String, dynamic> json) => Rgb(
        red: json["red"],
        green: json["green"],
        blue: json["blue"],
      );

  @override
  String toString() {
    return red.toString() + "," + green.toString() + "," + blue.toString();
  }

  Map<String, dynamic> toJson() => {
        "red": red,
        "green": green,
        "blue": blue,
      };
}
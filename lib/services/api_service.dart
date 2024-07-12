import 'dart:convert';
import 'package:assignment_post_api/model/item.dart';
import 'package:http/http.dart' as http;

class ApiService {
  static const String apiUrl = 'http://admin.meapps.xyz/api/v1/app/taskfetch';

  static Future<List<Item>> fetchItems() async {
    final response = await http.post(
      Uri.parse(apiUrl),
      headers: {'Content-Type': 'application/json'},
    );

    if (response.statusCode == 200) {
      final List<dynamic> responseData = json.decode(response.body);
      print('API Response: $responseData');
      return responseData.map((data) => Item.fromJson(data)).toList();
    } else {
      throw Exception('Failed to load data: ${response.statusCode}');
    }
  }
}

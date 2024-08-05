// import 'package:assignment_post_api/view/home_page.dart';
// import 'package:flutter/material.dart';

// void main() {
//   runApp(MyApp());
// }

// class MyApp extends StatelessWidget {
//   @override
//   Widget build(BuildContext context) {
//     return MaterialApp(
//       debugShowCheckedModeBanner: false,
//       title: 'Demo App',
//       theme: ThemeData(
//         primarySwatch: Colors.blue,
//       ),
//       home: HomePage(),
//     );
//   }
// }

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  static const MethodChannel _channel = MethodChannel('offerwall_plugin');

  void _showOfferwall() async {
    try {
      final result = await _channel.invokeMethod('showOfferwall');
      print(result);
    } on PlatformException catch (e) {
      print("Failed to load offerwall: '${e.message}'.");
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Offerwall Integration'),
        ),
        body: Center(
          child: ElevatedButton(
            onPressed: _showOfferwall,
            child: Text('Show Offerwall'),
          ),
        ),
      ),
    );
  }
}

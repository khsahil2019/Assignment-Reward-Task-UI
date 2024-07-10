import 'package:flutter/material.dart';
import 'package:assignment_post_api/model/item.dart';
import 'package:assignment_post_api/services/api_service.dart';
import 'package:assignment_post_api/widgets/item_card.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  List<Item> items = [];
  bool isLoading = true;

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  Future<void> fetchData() async {
    try {
      setState(() {
        isLoading = true;
      });

      List<Item> fetchedItems = await ApiService.fetchItems();

      setState(() {
        items = fetchedItems;
        isLoading = false;
      });
    } catch (error) {
      print('Error fetching data: $error');
      setState(() {
        isLoading = false;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color.fromARGB(255, 5, 21, 35),
      appBar: AppBar(
        title: const Text(
          'Demo App',
          style: TextStyle(color: Colors.white),
        ),
        backgroundColor: const Color.fromARGB(255, 16, 29, 44),
      ),
      body: isLoading
          ? const Center(child: CircularProgressIndicator()) // Show loader
          : ListView.builder(
              padding: const EdgeInsets.all(8),
              itemCount: items.length,
              itemBuilder: (context, index) {
                return RewardCard(item: items[index]);
              },
            ),
    );
  }
}

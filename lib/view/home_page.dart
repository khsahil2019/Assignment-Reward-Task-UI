import 'package:flutter/material.dart';
import 'package:assignment_post_api/model/item.dart';
import 'package:assignment_post_api/services/api_service.dart';
import 'package:assignment_post_api/widgets/item_card.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  List<Item> items = [];
  bool isLoading = true;
  String errorMessage = '';

  @override
  void initState() {
    super.initState();
    fetchData();
  }

  Future<void> fetchData() async {
    try {
      setState(() {
        isLoading = true;
        errorMessage = '';
      });

      List<Item> fetchedItems = await ApiService.fetchItems();

      setState(() {
        items = fetchedItems;
        isLoading = false;
      });
    } catch (error) {
      //  print('Error fetching data: $error');
      setState(() {
        isLoading = false;
        errorMessage = 'Failed to fetch data. Please try again later.';
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
          : errorMessage.isNotEmpty
              ? Center(
                  child: Text(
                    errorMessage,
                    style: const TextStyle(color: Colors.red),
                  ),
                )
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

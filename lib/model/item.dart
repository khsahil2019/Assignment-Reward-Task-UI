class Item {
  final String campaignName;
  final String logo;
  final String shortDescription;
  final int rewards;

  Item({
    required this.campaignName,
    required this.logo,
    required this.shortDescription,
    required this.rewards,
  });

  factory Item.fromJson(Map<String, dynamic> json) {
    return Item(
      campaignName: json['campaginName'] ?? '',
      logo: json['logo'] ?? '',
      shortDescription: json['shortDescription'] ?? '',
      rewards: json['rewards'] ?? 0,
    );
  }
}

import 'package:flutter/material.dart';

class CategoryBottomSheet extends StatelessWidget {
  const CategoryBottomSheet({
    super.key,
    required this.selected,
    required this.categories,
  });

  final String? selected;
  final List<String> categories;

  @override
  Widget build(BuildContext context) {
    final items = <String?>[null, ...categories];
    return SafeArea(
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: items
              .map(
                (category) => ListTile(
                  title: Text(category == null
                      ? 'All'
                      : category[0].toUpperCase() + category.substring(1)),
                  trailing: selected == category
                      ? const Icon(Icons.check, color: Colors.blue)
                      : null,
                  onTap: () => Navigator.of(context).pop(category),
                ),
              )
              .toList(),
        ),
      ),
    );
  }
}

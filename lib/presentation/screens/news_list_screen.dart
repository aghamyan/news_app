import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../core/constants.dart';
import '../../domain/models/article.dart';
import '../viewmodels/news_view_model.dart';
import '../widgets/article_card.dart';
import '../widgets/category_bottom_sheet.dart';
import 'news_detail_screen.dart';

class NewsListScreen extends StatelessWidget {
  const NewsListScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Top Headlines'),
        actions: [
          IconButton(
            icon: const Icon(Icons.filter_list),
            onPressed: () async {
              final viewModel = context.read<NewsViewModel>();
              final result = await showModalBottomSheet<String?>(
                context: context,
                builder: (_) => CategoryBottomSheet(
                  selected: viewModel.state.selectedCategory,
                  categories: Constants.categories,
                ),
              );
              viewModel.setCategory(result);
            },
          )
        ],
      ),
      body: const _NewsListBody(),
    );
  }
}

class _NewsListBody extends StatefulWidget {
  const _NewsListBody();

  @override
  State<_NewsListBody> createState() => _NewsListBodyState();
}

class _NewsListBodyState extends State<_NewsListBody> {
  final TextEditingController _controller = TextEditingController();

  @override
  void initState() {
    super.initState();
    _controller.addListener(() => setState(() {}));
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Consumer<NewsViewModel>(
      builder: (context, viewModel, _) {
        final state = viewModel.state;
        return RefreshIndicator(
          onRefresh: () => viewModel.loadHeadlines(category: state.selectedCategory, refresh: true),
          child: ListView(
            padding: const EdgeInsets.all(16),
            children: [
              _SearchField(
                controller: _controller,
                onChanged: viewModel.search,
                onClear: () {
                  _controller.clear();
                  viewModel.clearQuery();
                },
              ),
              const SizedBox(height: 12),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    'Category: ${viewModel.categoryLabel(state.selectedCategory)}',
                    style: Theme.of(context).textTheme.labelLarge,
                  ),
                  if (state.isLoading)
                    const SizedBox(
                      height: 20,
                      width: 20,
                      child: CircularProgressIndicator(strokeWidth: 2),
                    ),
                ],
              ),
              const SizedBox(height: 12),
              if (state.errorMessage != null)
                Padding(
                  padding: const EdgeInsets.symmetric(vertical: 12),
                  child: Text(
                    state.errorMessage!,
                    style: TextStyle(color: Theme.of(context).colorScheme.error),
                  ),
                ),
              if (!state.isLoading && state.articles.isEmpty)
                const Padding(
                  padding: EdgeInsets.symmetric(vertical: 32),
                  child: Center(child: Text('No Results Found')),
                ),
              ...state.articles
                  .map((article) => ArticleCard(
                        article: article,
                        onTap: () => Navigator.of(context).pushNamed(
                          NewsDetailScreen.routeName,
                          arguments: article,
                        ),
                      ))
                  .toList(),
            ],
          ),
        );
      },
    );
  }
}

class _SearchField extends StatelessWidget {
  const _SearchField({
    required this.controller,
    required this.onChanged,
    required this.onClear,
  });

  final TextEditingController controller;
  final ValueChanged<String> onChanged;
  final VoidCallback onClear;

  @override
  Widget build(BuildContext context) {
    return TextField(
      controller: controller,
      onChanged: onChanged,
      decoration: InputDecoration(
        hintText: 'Search articles',
        prefixIcon: const Icon(Icons.search),
        suffixIcon: controller.text.isEmpty
            ? null
            : IconButton(
                icon: const Icon(Icons.clear),
                onPressed: onClear,
              ),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(12),
        ),
      ),
    );
  }
}

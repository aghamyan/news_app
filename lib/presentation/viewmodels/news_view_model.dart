import 'package:flutter/foundation.dart';

import '../../core/constants.dart';
import '../../domain/usecases/get_top_headlines.dart';
import '../../domain/usecases/search_headlines.dart';
import '../state/news_state.dart';

class NewsViewModel extends ChangeNotifier {
  NewsViewModel({
    required GetTopHeadlines getTopHeadlines,
    required SearchHeadlines searchHeadlines,
  })  : _getTopHeadlines = getTopHeadlines,
        _searchHeadlines = searchHeadlines,
        _state = NewsState.initial();

  final GetTopHeadlines _getTopHeadlines;
  final SearchHeadlines _searchHeadlines;

  NewsState get state => _state;
  NewsState _state;

  Future<void> loadHeadlines({String? category, bool refresh = false}) async {
    _state = _state.copyWith(isLoading: true, errorMessage: null);
    notifyListeners();
    try {
      final articles = await _getTopHeadlines(category: category);
      _state = _state.copyWith(
        articles: articles,
        isLoading: false,
        selectedCategory: category,
      );
    } catch (e) {
      _state = _state.copyWith(
        isLoading: false,
        errorMessage: 'Failed to load headlines',
      );
    }
    notifyListeners();
  }

  Future<void> search(String query) async {
    _state = _state.copyWith(query: query, isLoading: true, errorMessage: null);
    notifyListeners();
    try {
      final results = await _searchHeadlines(query);
      _state = _state.copyWith(articles: results, isLoading: false);
    } catch (e) {
      _state = _state.copyWith(
        isLoading: false,
        errorMessage: 'Search failed',
      );
    }
    notifyListeners();
  }

  void clearQuery() {
    _state = _state.copyWith(query: '', errorMessage: null);
    notifyListeners();
    loadHeadlines(category: _state.selectedCategory);
  }

  void setCategory(String? category) {
    if (_state.selectedCategory == category) return;
    _state = _state.copyWith(selectedCategory: category, query: '');
    notifyListeners();
    loadHeadlines(category: category);
  }

  String categoryLabel(String? category) {
    if (category == null || category.isEmpty) return 'All';
    if (Constants.categories.contains(category)) {
      return category[0].toUpperCase() + category.substring(1);
    }
    return category;
  }
}

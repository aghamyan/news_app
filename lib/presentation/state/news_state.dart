import '../../domain/models/article.dart';

class NewsState {
  const NewsState({
    required this.articles,
    required this.isLoading,
    required this.errorMessage,
    required this.selectedCategory,
    required this.query,
  });

  factory NewsState.initial() => const NewsState(
        articles: [],
        isLoading: false,
        errorMessage: null,
        selectedCategory: null,
        query: '',
      );

  final List<Article> articles;
  final bool isLoading;
  final String? errorMessage;
  final String? selectedCategory;
  final String query;

  NewsState copyWith({
    List<Article>? articles,
    bool? isLoading,
    String? errorMessage,
    String? selectedCategory,
    String? query,
  }) {
    return NewsState(
      articles: articles ?? this.articles,
      isLoading: isLoading ?? this.isLoading,
      errorMessage: errorMessage,
      selectedCategory: selectedCategory ?? this.selectedCategory,
      query: query ?? this.query,
    );
  }
}

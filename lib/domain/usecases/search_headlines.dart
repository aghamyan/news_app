import '../models/article.dart';
import '../repository/news_repository.dart';

class SearchHeadlines {
  const SearchHeadlines(this.repository);

  final NewsRepository repository;

  Future<List<Article>> call(String query) {
    return repository.searchHeadlines(query);
  }
}

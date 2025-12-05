import '../models/article.dart';
import '../repository/news_repository.dart';

class GetTopHeadlines {
  const GetTopHeadlines(this.repository);

  final NewsRepository repository;

  Future<List<Article>> call({String? category}) {
    return repository.getTopHeadlines(category: category);
  }
}

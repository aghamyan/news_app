import '../../domain/models/article.dart';
import '../../domain/repository/news_repository.dart';
import '../models/article_dto.dart';
import '../remote/news_api_service.dart';

class NewsRepositoryImpl implements NewsRepository {
  NewsRepositoryImpl(this._service);

  final NewsApiService _service;
  List<Article> _cached = [];

  @override
  Future<List<Article>> getTopHeadlines({String? category}) async {
    final response = await _service.fetchTopHeadlines(category: category);
    _cached = response.articles.map(_mapArticle).toList();
    return _cached;
  }

  @override
  Future<List<Article>> searchHeadlines(String query) async {
    if (query.isEmpty) {
      return _cached;
    }
    try {
      final response = await _service.fetchTopHeadlines(query: query);
      final articles = response.articles.map(_mapArticle).toList();
      _cached = articles;
      return articles;
    } catch (_) {
      return _cached
          .where((article) =>
              article.title.toLowerCase().contains(query.toLowerCase()) ||
              article.description.toLowerCase().contains(query.toLowerCase()))
          .toList();
    }
  }

  @override
  List<Article> cachedHeadlines() => List.unmodifiable(_cached);

  Article _mapArticle(ArticleDto dto) => Article(
        title: dto.title,
        author: dto.author,
        description: dto.description,
        imageUrl: dto.urlToImage,
        source: dto.source,
      );
}

import '../models/article.dart';

abstract class NewsRepository {
  Future<List<Article>> getTopHeadlines({String? category});

  Future<List<Article>> searchHeadlines(String query);

  List<Article> cachedHeadlines();
}

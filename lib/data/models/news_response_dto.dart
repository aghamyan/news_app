import 'article_dto.dart';

class NewsResponseDto {
  const NewsResponseDto({required this.articles});

  factory NewsResponseDto.fromJson(Map<String, dynamic> json) {
    final rawArticles = json['articles'] as List<dynamic>? ?? [];
    final items = rawArticles
        .map((item) => ArticleDto.fromJson(item as Map<String, dynamic>))
        .toList();
    return NewsResponseDto(articles: items);
  }

  final List<ArticleDto> articles;
}

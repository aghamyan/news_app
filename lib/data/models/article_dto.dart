class ArticleDto {
  const ArticleDto({
    required this.title,
    required this.author,
    required this.description,
    required this.urlToImage,
    required this.source,
  });

  factory ArticleDto.fromJson(Map<String, dynamic> json) {
    final sourceMap = json['source'] as Map<String, dynamic>?;
    return ArticleDto(
      title: json['title'] as String? ?? '',
      author: json['author'] as String? ?? 'Unknown',
      description: json['description'] as String? ?? '',
      urlToImage: json['urlToImage'] as String?,
      source: sourceMap?['name'] as String? ?? 'Unknown',
    );
  }

  final String title;
  final String author;
  final String description;
  final String? urlToImage;
  final String source;
}

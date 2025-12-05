class Article {
  const Article({
    required this.title,
    required this.author,
    required this.description,
    required this.imageUrl,
    required this.source,
  });

  final String title;
  final String author;
  final String description;
  final String? imageUrl;
  final String source;
}

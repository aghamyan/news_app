import 'dart:convert';
import 'package:http/http.dart' as http;

import '../../core/constants.dart';
import '../models/news_response_dto.dart';

class NewsApiService {
  NewsApiService({http.Client? client}) : _client = client ?? http.Client();

  final http.Client _client;

  Future<NewsResponseDto> fetchTopHeadlines({String? category, String? query}) async {
    final buffer = StringBuffer('${Constants.baseUrl}/top-headlines?country=us');
    if (category != null && category.isNotEmpty) {
      buffer.write('&category=$category');
    }
    if (query != null && query.isNotEmpty) {
      buffer.write('&q=$query');
    }
    buffer.write('&apiKey=${Constants.apiKey}');

    final uri = Uri.parse(buffer.toString());
    final response = await _client.get(uri);
    if (response.statusCode != 200) {
      throw Exception('Failed to load headlines');
    }
    final Map<String, dynamic> data = jsonDecode(response.body) as Map<String, dynamic>;
    return NewsResponseDto.fromJson(data);
  }
}

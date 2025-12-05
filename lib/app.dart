import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'data/remote/news_api_service.dart';
import 'data/repository/news_repository_impl.dart';
import 'domain/usecases/get_top_headlines.dart';
import 'domain/usecases/search_headlines.dart';
import 'presentation/screens/news_detail_screen.dart';
import 'presentation/screens/news_list_screen.dart';
import 'presentation/viewmodels/news_view_model.dart';

class NewsApp extends StatelessWidget {
  const NewsApp({super.key});

  @override
  Widget build(BuildContext context) {
    final repository = NewsRepositoryImpl(NewsApiService());
    return MultiProvider(
      providers: [
        ChangeNotifierProvider(
          create: (_) => NewsViewModel(
            getTopHeadlines: GetTopHeadlines(repository),
            searchHeadlines: SearchHeadlines(repository),
          )..loadHeadlines(),
        ),
      ],
      child: MaterialApp(
        title: 'News App',
        theme: ThemeData(
          colorScheme: ColorScheme.fromSeed(seedColor: Colors.blueGrey),
          useMaterial3: true,
        ),
        routes: {
          '/': (_) => const NewsListScreen(),
          NewsDetailScreen.routeName: (context) {
            final article = ModalRoute.of(context)!.settings.arguments;
            return NewsDetailScreen(article: article);
          },
        },
      ),
    );
  }
}

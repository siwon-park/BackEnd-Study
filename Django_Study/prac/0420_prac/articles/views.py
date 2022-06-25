from django.shortcuts import get_list_or_404, get_object_or_404
from .models import Article
from .serializers import ArticleListSerializer, ArticleSerializer
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status

@api_view(['GET', 'POST'])
def article_list(request):
    if request.method == "GET":
        articles = get_list_or_404(Article)
        serializers = ArticleListSerializer(articles, many=True)
        return Response(serializers.data)
    elif request.method == "POST":
        serializer = ArticleSerializer(data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)


@api_view(['GET', 'DELETE', 'PUT'])
def article_detail(request, article_pk):
    article = get_object_or_404(Article, pk=article_pk)
    if request.method == 'GET':
        serializer = ArticleSerializer(article)
        return Response(serializer.data)
    elif request.method == 'DELETE':
        article.delete()
        data = {
            'deleted': f'{article_pk}번 글이 제거되었습니다.'
        }
        return Response(data)
    elif request.method == 'PUT':
        serializer = ArticleSerializer(article, data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response(serializer.data)


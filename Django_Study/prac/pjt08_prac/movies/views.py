from django.shortcuts import get_list_or_404, get_object_or_404
from rest_framework import status
from rest_framework.response import Response
from rest_framework.decorators import api_view
from movies.serializers.actor import ActorListSerializer, ActorSerializer
from movies.serializers.movie import MovieListSerializer, MovieSerializer
from movies.serializers.review import ReviewListSerializer, ReviewSerializer
from .models import Actor, Movie, Review


@api_view(['GET'])
def actor_list(request):
    actors = get_list_or_404(Actor)
    serializers = ActorListSerializer(actors, many=True)
    return Response(serializers.data)


@api_view(['GET'])
def actor_detail(request, actor_pk):
    actor = get_object_or_404(Actor, pk=actor_pk)
    serializer = ActorSerializer(actor)
    return Response(serializer.data)


@api_view(['GET'])
def movie_list(request):
    movies = get_list_or_404(Movie)
    serializers = MovieListSerializer(movies, many=True)
    return Response(serializers.data)


@api_view(['GET'])
def movie_detail(request, movie_pk):
    movie = get_object_or_404(Movie, pk=movie_pk)
    serializer = MovieSerializer(movie)
    return Response(serializer.data)


@api_view(['GET'])   
def review_list(request):
    reviews = get_list_or_404(Review)
    serializers = ReviewListSerializer(reviews, many=True)
    return Response(serializers.data)


@api_view(['GET', 'DELETE', 'PUT'])   
def review_detail(request, review_pk):
    review = get_object_or_404(Review, pk=review_pk)
    if request.method == 'GET':
        serializer = ReviewSerializer(review)
        return Response(serializer.data)
    
    elif request.method == 'DELETE':
       review.delete()
       data = {
           'deleted': f'{review_pk}번 리뷰가 삭제되었습니다.'
       }
       return Response(data, status=status.HTTP_204_NO_CONTENT)

    elif request.method == 'PUT':
        serializer = ReviewSerializer(review, data=request.data)
        if serializer.is_valid(raise_exception=True):
            serializer.save()
            return Response(serializer.data)


@api_view(['POST'])   
def create_review(request, movie_pk):
    movie = get_object_or_404(Movie, pk=movie_pk)
    serializer = ReviewSerializer(data=request.data)
    if serializer.is_valid(raise_exception=True):
        serializer.save(movie=movie)
        return Response(serializer.data, status=status.HTTP_201_CREATED)


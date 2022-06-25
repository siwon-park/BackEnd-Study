# review serializer
from rest_framework import serializers
from movies.models import Review
from movies.serializers.movie import MovieListSerializer


class ReviewListSerializer(serializers.ModelSerializer):

    class Meta:
        model = Review
        fields = ('title', 'content',)

class ReviewSerializer(serializers.ModelSerializer):
    movie = MovieListSerializer(read_only=True)

    class Meta:
        model = Review
        fields = '__all__'
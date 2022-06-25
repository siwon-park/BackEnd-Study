# movie serializer
from rest_framework import serializers
from movies.models import Movie, Actor, Review

class MovieListSerializer(serializers.ModelSerializer):

    class Meta:
        model = Movie
        fields = ('title', 'overview',)


class MovieSerializer(serializers.ModelSerializer):
    class ActorListSerializer(serializers.ModelSerializer):

        class Meta:
            model = Actor
            fields = ('name',)

    class ReviewListSerializer(serializers.ModelSerializer):

        class Meta:
            model = Review
            fields = ('title', 'content',)

    actors = ActorListSerializer(many=True, read_only=True)
    review_set = ReviewListSerializer(many=True, read_only=True)

    class Meta:
        model = Movie
        fields = '__all__'
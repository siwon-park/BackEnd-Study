# actor serializer
from rest_framework import serializers
from movies.models import Actor
from movies.serializers.movie import MovieListSerializer

class ActorListSerializer(serializers.ModelSerializer):

    class Meta:
        model = Actor
        fields = ('id', 'name',)


class ActorSerializer(serializers.ModelSerializer):
    movies = MovieListSerializer(many=True, read_only=True)

    class Meta:
        model = Actor
        fields = '__all__'


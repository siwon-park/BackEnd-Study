from django.db import models

class Movie(models.Model):
    title = models.CharField(max_length=100)
    overview = models.TextField()
    release_date = models.DateTimeField()
    poster_path = models.TextField()

class Actor(models.Model):
    movies = models.ManyToManyField(Movie, related_name='actors')
    name = models.CharField(max_length=100)

class Review(models.Model):
    movie = models.ForeignKey(Movie, on_delete=models.CASCADE)
    title = models.CharField(max_length=100)
    content = models.TextField()
